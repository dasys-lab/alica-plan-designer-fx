package de.uni_kassel.vs.cn.planDesigner.view.repo;

import de.uni_kassel.vs.cn.planDesigner.alica.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * This class functions as backend for the repository view.
 * <p>
 * This class contains Lists of all Plans, PlanTypes, Behaviours and Tasks as Pair<Long,String>
 */
public class RepositoryViewModel {

    // SINGLETON
    private static volatile RepositoryViewModel instance;

    public static RepositoryViewModel getInstance() {
        if (instance == null) {
            synchronized (RepositoryViewModel.class) {
                if (instance == null) {
                    instance = new RepositoryViewModel();
                }
            }
        }
        return instance;
    }

    /**
     * For testing only, don't call it unless you know what you are doing.
     */
    public static RepositoryViewModel getTestInstance() {
        instance = new RepositoryViewModel();
        instance.taskRepository = new ArrayList<>();
        instance.plans = FXCollections.observableArrayList(new ArrayList<>());
        instance.planTypes = FXCollections.observableArrayList(new ArrayList<>());
        instance.behaviours = FXCollections.observableArrayList(new ArrayList<>());
        instance.tasks = new Pair<>(new ArrayList<>(), null);
        return instance;
    }

    private static final Logger LOG = LogManager.getLogger(RepositoryViewModel.class);

    private ObservableList<Pair<Long, String>> plans;
    private ObservableList<Pair<Long, String>> planTypes;
    private ObservableList<Pair<Long, String>> behaviours;

    private Pair<List<Task>, Path> tasks;
    private List<Pair<TaskRepository, Path>> taskRepository;

    public void setPlans(ObservableList<Pair<Long, String>> plans) {
        this.plans = plans;
    }

    public void setPlanTypes(ObservableList<Pair<Long, String>> planTypes) {
        this.planTypes = planTypes;
    }

    public void setBehaviours(ObservableList<Pair<Long, String>> behaviours) {
        this.behaviours = behaviours;
    }

 /*   public Optional<Pair<Plan, Path>> getPlanPathPair(File planFile) {
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

    */
}
