package de.uni_kassel.vs.cn.generator;

import de.uni_kassel.vs.cn.planDesigner.alica.*;
import de.uni_kassel.vs.cn.generator.configuration.Configuration;
import de.uni_kassel.vs.cn.generator.configuration.ConfigurationManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class functions as backend for the repository view.
 * <p>
 * This class contains Lists of all Plans, PlanTypes, Behaviours and Tasks
 */
public class RepoViewBackend {

    // SINGLETON
    private static volatile RepoViewBackend instance;

    public static RepoViewBackend getInstance() {
        if (instance == null) {
            synchronized (RepoViewBackend.class) {
                if (instance == null) {
                    instance = new RepoViewBackend();
                    try {
                        instance.init();
                    } catch (URISyntaxException | IOException e) {
                        LOG.error("Could not initialize all ALICA files, this renders the PlanDesigner unusable", e);
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        return instance;
    }

    /**
     * For testing only, don't call it unless you know what you are doing.
     */
    public static RepoViewBackend getTestInstance() {
        instance = new RepoViewBackend();
        instance.taskRepository = new ArrayList<>();
        instance.plans = FXCollections.observableArrayList(new ArrayList<>());
        instance.planTypes = FXCollections.observableArrayList(new ArrayList<>());
        instance.behaviours = FXCollections.observableArrayList(new ArrayList<>());
        instance.tasks = new Pair<>(new ArrayList<>(), null);
        return instance;
    }

    private static final Logger LOG = LogManager.getLogger(RepoViewBackend.class);

    private ObservableList<Pair<Plan, Path>> plans;
    private ObservableList<Pair<PlanType, Path>> planTypes;
    private ObservableList<Pair<Behaviour, Path>> behaviours;
    private Pair<List<Task>, Path> tasks;
    private List<Pair<TaskRepository, Path>> taskRepository;

    public List<Pair<TaskRepository, Path>> getTaskRepository() {
        return taskRepository;
    }

    public ObservableList<Pair<Plan, Path>> getPlans() {
        return plans;
    }

    public ObservableList<Pair<PlanType, Path>> getPlanTypes() {
        return planTypes;
    }

    public Pair<List<Task>, Path> getTasks() {
        return tasks;
    }

    public ObservableList<Pair<Behaviour, Path>> getBehaviours() {
        return behaviours;
    }

    public void init() throws URISyntaxException, IOException {
        Configuration conf = ConfigurationManager.getInstance().getActiveConfiguration();
        if (conf == null) {
            LOG.info("RepoViewBackend unable to initialize, because of missing active Configuration.");
            return;
        }
        String plansPath = conf.getPlansPath();
        if (plansPath != null && !plansPath.isEmpty()) {
            plans = getRepositoryOf(plansPath, "pml");

            behaviours = getRepositoryOf(plansPath, "beh");

            planTypes = getRepositoryOf(plansPath, "pty");
        }

        if (conf.getTasksPath() != null && !conf.getTasksPath().isEmpty()) {
            taskRepository = getRepositoryOf(conf.getTasksPath(), "tsk");
            if (taskRepository == null || taskRepository.isEmpty()) {
                EMFModelUtils.createAlicaFile(EMFModelUtils.getAlicaFactory().createTaskRepository(), false,
                        new File(conf.getTasksPath() + File.separator + "taskrepository.tsk"));
                taskRepository = getRepositoryOf(conf.getTasksPath(), "tsk");
            }
            tasks = new Pair<>(taskRepository.get(0).getKey().getTasks(), taskRepository.get(0).getValue());
        }

        EcoreUtil.resolveAll(EMFModelUtils.getAlicaResourceSet());
        LOG.info("RepoViewBackend successfully initialized");
    }

    public Path getPathForAbstractPlan(AbstractPlan abstractPlan) {
        if (abstractPlan instanceof Plan) {
            return getPlans()
                    .stream()
                    .filter(e -> e.getKey().equals(abstractPlan))
                    .findFirst().get().getValue();

        }

        if (abstractPlan instanceof Behaviour) {
            return getBehaviours()
                    .stream()
                    .filter(e -> e.getKey().equals(abstractPlan))
                    .findFirst().get().getValue();
        }

        if (abstractPlan instanceof PlanType) {
            return getPlanTypes()
                    .stream()
                    .filter(e -> e.getKey().equals(abstractPlan))
                    .findFirst().get().getValue();
        }
        LOG.warn("No file path found for AbstractPlan " + abstractPlan.getName());
        return null;
    }

    /**
     * Tries to find a list matching the given pair.
     * If none match null is returned
     *
     * @param pathPair the pair which list you are searching for
     * @param <T>      type of the list
     * @return result
     */
    @SuppressWarnings("unchecked")
    public <T extends PlanElement> ObservableList<Pair<T, Path>> findListByType(Pair<T, Path> pathPair) {
        if (pathPair.getKey() instanceof Plan) {
            return (ObservableList<Pair<T, Path>>) (Object) getPlans();
        }

        if (pathPair.getKey() instanceof Behaviour) {
            return (ObservableList<Pair<T, Path>>) (Object) getBehaviours();
        }

        if (pathPair.getKey() instanceof PlanType) {
            return (ObservableList<Pair<T, Path>>) (Object) getPlanTypes();
        }

        if (pathPair.getKey() instanceof TaskRepository) {
            return (ObservableList<Pair<T, Path>>) (Object) getTaskRepository();
        }

        return null;
    }

    private <T extends EObject> ObservableList<Pair<T, Path>> getRepositoryOf(String path, String filePostfix) throws IOException {

        if (Files.notExists(Paths.get(path))) {
            Files.createDirectories(Paths.get(path));
        }
        List<Pair<T, Path>> collectedList = Files.walk(Paths.get(path))
                .filter(p -> p.toString().endsWith("." + filePostfix))
                .map(p -> {
                    try {
                        Pair<T, Path> tPathPair = new Pair<>(EMFModelUtils.<T>loadAlicaFileFromDisk(p.toFile()), p);
                        for (Iterator k = tPathPair.getKey().eCrossReferences().iterator(); k.hasNext(); ) {
                            k.next();
                        }
                        return tPathPair;
                    } catch (IOException e) {
                        LOG.fatal("Could not loadFromDisk repository of " + path + "for file ending " + filePostfix, e);
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
        LOG.info("Completed initial repository list for file ending: " + filePostfix);
        return FXCollections.observableList(collectedList);
    }

    public Optional<Pair<Plan, Path>> getPlanPathPair(File planFile) {
        return plans
                .stream()
                .filter(f -> f.getValue().toFile().equals(planFile))
                .findFirst();
    }

    public Optional<Pair<PlanType, Path>> getPlanTypePathPair(File planTypeFile) {
        return planTypes
                .stream()
                .filter(f -> f.getValue().toFile().equals(planTypeFile))
                .findFirst();
    }

    public Optional<Pair<Behaviour, Path>> getBehaviourPathPair(File behaviourFile) {
        return behaviours
                .stream()
                .filter(f -> f.getValue().toFile().equals(behaviourFile))
                .findFirst();
    }
}
