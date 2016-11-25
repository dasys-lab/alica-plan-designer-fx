package de.uni_kassel.vs.cn.planDesigner.common;

import de.uni_kassel.vs.cn.planDesigner.alica.*;
import de.uni_kassel.vs.cn.planDesigner.alica.configuration.Configuration;
import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;
import org.eclipse.emf.ecore.EObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
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

    private List<Plan> plans;

    private List<PlanType> planTypes;

    private List<Behaviour> behaviours;

    private List<Task> tasks;

    public List<Plan> getPlans() {
        return plans;
    }

    public List<PlanType> getPlanTypes() {
        return planTypes;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public List<Behaviour> getBehaviours() {
        return behaviours;
    }

    public void init() throws URISyntaxException, IOException {
        Configuration configuration = new Configuration();
        String plansPath = configuration.getPlansPath();
        plans = getRepositoryOf(plansPath, "pml");

        behaviours = getRepositoryOf(plansPath,"beh");

        planTypes = getRepositoryOf(plansPath, "pty");

        List<TaskRepository> tsk = getRepositoryOf(configuration.getMiscPath(), "tsk");
        tasks = tsk.get(0).getTasks();
    }

    private <T extends EObject> List<T> getRepositoryOf(String plansPath, String filePostfix) throws IOException, URISyntaxException {
        return Files.walk(Paths.get(plansPath))
                    .filter(p -> p.toString().endsWith("." + filePostfix))
                    .map(p -> {
                        try {
                            return (T) EMFModelUtils.loadAlicaFileFromDisk(p.toFile());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toList());
    }
}
