package de.uni_kassel.vs.cn.planDesigner.common;

import de.uni_kassel.vs.cn.planDesigner.alica.*;
import de.uni_kassel.vs.cn.planDesigner.alica.configuration.Configuration;
import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;
import javafx.util.Pair;
import org.eclipse.emf.ecore.EObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by marci on 25.11.16.
 *
 * This class functions as backend for the repository view.
 * This class contains Lists of all Plans, PlanTypes, Behaviours and Tasks
 */
public class AllAlicaFiles {

    private List<Pair<Plan,Path>> plans;

    private List<Pair<PlanType, Path>> planTypes;

    private List<Pair<Behaviour, Path>> behaviours;

    private Pair<List<Task>, Path> tasks;

    public List<Pair<Plan,Path>> getPlans() {
        return plans;
    }

    public List<Pair<PlanType, Path>> getPlanTypes() {
        return planTypes;
    }

    public Pair<List<Task>, Path> getTasks() {
        return tasks;
    }

    public List<Pair<Behaviour, Path>> getBehaviours() {
        return behaviours;
    }

    public void init() throws URISyntaxException, IOException {
        Configuration configuration = new Configuration();
        String plansPath = configuration.getPlansPath();
        plans = getRepositoryOf(plansPath, "pml");

        behaviours = getRepositoryOf(plansPath,"beh");

        planTypes = getRepositoryOf(plansPath, "pty");

        List<Pair<TaskRepository,Path>> tsk = getRepositoryOf(configuration.getMiscPath(), "tsk");
        tasks = new Pair<>(tsk.get(0).getKey().getTasks(),tsk.get(0).getValue());
    }

    private <T extends EObject> List<Pair<T,Path>> getRepositoryOf(String plansPath, String filePostfix) throws IOException, URISyntaxException {
        return Files.walk(Paths.get(plansPath))
                    .filter(p -> p.toString().endsWith("." + filePostfix))
                    .map(p -> {
                        try {
                            return new Pair<>((T) EMFModelUtils.loadAlicaFileFromDisk(p.toFile()),p);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toList());
    }
}
