package de.uni_kassel.vs.cn.planDesigner.modelhandling;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.*;
import de.uni_kassel.vs.cn.planDesigner.configuration.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.util.*;

public class ModelManager {
    private Configuration activeConf;
    private HashMap<Long, PlanElement> planElementMap;

    private HashMap<Long, Plan> planMap;
    private HashMap<Long, Behaviour> behaviourMap;
    private HashMap<Long, PlanType> planTypeMap;
    private HashMap<Long, Task> taskMap;

    private List<IModelEventHandler> eventHandlerList;

    public ModelManager(Configuration conf) {
        activeConf = conf;

        planElementMap = new HashMap<>();
        planMap = new HashMap<>();
        behaviourMap = new HashMap<>();
        planTypeMap = new HashMap<>();
        taskMap = new HashMap<>();
        eventHandlerList = new ArrayList<IModelEventHandler>();
    }

    public void addListener(IModelEventHandler eventHandler) {
        eventHandlerList.add(eventHandler);
    }

    public void removeListener(IModelEventHandler eventHandler) {
        eventHandlerList.remove(eventHandler);
    }

    public void loadModelFromDisk() {
        loadModelFromDisk(activeConf.getPlansPath());
        loadModelFromDisk(activeConf.getTasksPath());
        loadModelFromDisk(activeConf.getRolesPath());
    }

    /**
     * This method could be superfluous, as "loadModelFile" is maybe called by the file watcher.
     * Anyway, temporarily this is a nice method for testing and is therefore called in the constr.
     */
    private void loadModelFromDisk(String path) {
        if (activeConf == null) {
            return;
        }

        File plansDirectory = new File(path);
        if (!plansDirectory.exists()) {
            return;
        }

        File[] allModelFiles = plansDirectory.listFiles();
        if (allModelFiles == null || allModelFiles.length == 0) {
            return;
        }

        for (File modelFile : allModelFiles) {
            loadModelFile(modelFile);
        }
    }

    /**
     * Determines the file ending and loads the model object, accordingly.
     * The constructed object is inserted in the corresponding model element maps, if no
     * duplicated ID was found.
     *
     * @param modelFile the file to be parsed by jackson
     */
    private void loadModelFile(File modelFile) {
        String path = modelFile.toString();
        String ending = path.substring(path.lastIndexOf('.'), path.length());

        ObjectMapper mapper = new ObjectMapper();
        try {
            // TODO: Test parsing model objects with jackson.
            switch (ending) {
                case ".pml":
                    Plan plan = mapper.readValue(modelFile, Plan.class);
                    if (planElementMap.containsKey(plan.getId())) {
                        throw new RuntimeException("PlanElement ID duplication found! ID is: " + plan.getId());
                    } else {
                        planElementMap.put(plan.getId(), plan);
                        planMap.put(plan.getId(), plan);
                        fireCreationEvent(plan);
                    }
                    break;
                case ".beh":
                    Behaviour behaviour = mapper.readValue(modelFile, Behaviour.class);
                    if (planElementMap.containsKey(behaviour.getId())) {
                        throw new RuntimeException("PlanElement ID duplication found! ID is: " + behaviour.getId());
                    } else {
                        planElementMap.put(behaviour.getId(), behaviour);
                        behaviourMap.put(behaviour.getId(), behaviour);
                        fireCreationEvent(behaviour);
                    }
                    break;
                case ".pty":
                    PlanType planType = mapper.readValue(modelFile, PlanType.class);
                    if (planElementMap.containsKey(planType.getId())) {
                        throw new RuntimeException("PlanElement ID duplication found! ID is: " + planType.getId());
                    } else {
                        planElementMap.put(planType.getId(), planType);
                        planTypeMap.put(planType.getId(), planType);
                        fireCreationEvent(planType);
                    }
                    break;
                case ".tsk":
                    Task task = mapper.readValue(modelFile, Task.class);
                    if (planElementMap.containsKey(task.getId())) {
                        throw new RuntimeException("PlanElement ID duplication found! ID is: " + task.getId());
                    } else {
                        planElementMap.put(task.getId(), task);
                        taskMap.put(task.getId(), task);
                        fireCreationEvent(task);
                    }
                    break;
                case ".rset":
                    // TODO: Implement role and stuff parsing with jackson.
                    throw new RuntimeException("Parsing roles not implemented, yet!");
                default:
                    throw new RuntimeException("Received file with unknown file ending, for parsing.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fireCreationEvent(PlanElement element) {
        for (IModelEventHandler eventHandler : eventHandlerList) {
            eventHandler.handleModelEvent(new ModelEvent(ModelEventType.ELEMENT_CREATED, null, element));
        }
    }

    public ArrayList<Plan> getPlans() {
        return new ArrayList<>(planMap.values());
    }

    public ArrayList<Behaviour> getBehaviours() {
        return new ArrayList<>(behaviourMap.values());
    }

    public ArrayList<Condition> getConditions() {
        ArrayList<Condition> conditions = new ArrayList<>();
        for (Plan plan : planMap.values()) {
            conditions.add(plan.getPreCondition());
            conditions.add(plan.getRuntimeCondition());
            for (Transition transition : plan.getTransitions()) {
                    conditions.add(transition.getPreCondition());
            }
            for (State state : plan.getStates()) {
                if (state instanceof TerminalState)
                {
                    conditions.add(((TerminalState) state).getPostCondition());
                }
            }
        }
        for (Behaviour behaviour : behaviourMap.values()) {
            conditions.add(behaviour.getPreCondition());
            conditions.add(behaviour.getRuntimeCondition());
            conditions.add(behaviour.getPostCondition());
        }

        // remove all null values inserted before
        conditions.removeIf(Objects::isNull);
        return conditions;
    }

    public void handleFileSystemEvent(WatchEvent.Kind kind, Path path) {
        //TODO implement
        if (kind.equals(StandardWatchEventKinds.ENTRY_CREATE)) {
            loadModelFile(new File(path.toUri()));
        } else if (kind.equals(StandardWatchEventKinds.ENTRY_DELETE)) {
        } else if (kind.equals((StandardWatchEventKinds.ENTRY_MODIFY))) {

        }
    }
}
