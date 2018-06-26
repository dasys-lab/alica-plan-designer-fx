package de.uni_kassel.vs.cn.planDesigner.modelmanagement;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.*;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.command.CreatePlan;
import de.uni_kassel.vs.cn.planDesigner.command.ParseAbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.events.IModelEventHandler;
import de.uni_kassel.vs.cn.planDesigner.events.ModelEvent;
import de.uni_kassel.vs.cn.planDesigner.events.ModelOperationType;
import de.uni_kassel.vs.cn.planDesigner.modelMixIns.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ModelManager {

    private static final Logger LOG = LogManager.getLogger(ModelManager.class);

    private String plansPath;
    private String tasksPath;
    private String rolesPath;

    private HashMap<Long, PlanElement> planElementMap;
    private HashMap<Long, Plan> planMap;
    private HashMap<Long, Behaviour> behaviourMap;
    private HashMap<Long, PlanType> planTypeMap;
    private TaskRepository taskRepository;

    private List<IModelEventHandler> eventHandlerList;
    private CommandStack commandStack;
    private ObjectMapper objectMapper;

    private long defaultTaskId;

    public ModelManager() {
        planElementMap = new HashMap<>();
        planMap = new HashMap<>();
        behaviourMap = new HashMap<>();
        planTypeMap = new HashMap<>();
        taskRepository = new TaskRepository();
        eventHandlerList = new ArrayList<IModelEventHandler>();
        commandStack = new CommandStack();

        setupObjectMapper();
    }

    private void setupObjectMapper() {
        objectMapper = new ObjectMapper();

        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        objectMapper.addMixIn(EntryPoint.class, EntryPointMixIn.class);
        objectMapper.addMixIn(Parametrisation.class, ParametrisationMixIn.class);
        objectMapper.addMixIn(PlanType.class, PlanTypeMixIn.class);
        objectMapper.addMixIn(Quantifier.class, QuantifierMixIn.class);
        objectMapper.addMixIn(State.class, StateMixIn.class);
        objectMapper.addMixIn(Synchronization.class, SynchronizationMixIn.class);
        objectMapper.addMixIn(Task.class, TaskMixIn.class);
        objectMapper.addMixIn(TaskRepository.class, TaskRepositoryMixIn.class);
        objectMapper.addMixIn(Transition.class, TransitionMixIn.class);
    }

    public void setPlansPath(String plansPath) {
        this.plansPath = plansPath;
    }

    public void setTasksPath(String tasksPath) {
        this.tasksPath = tasksPath;
    }

    public void setRolesPath(String rolesPath) {
        this.rolesPath = rolesPath;
    }

    public ArrayList<Plan> getPlans() {
        return new ArrayList<>(planMap.values());
    }

    public ArrayList<Behaviour> getBehaviours() {
        return new ArrayList<>(behaviourMap.values());
    }

    public ArrayList<PlanElement> getPlanElements() {
        return new ArrayList<>(planElementMap.values());
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
                if (state instanceof TerminalState) {
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

    public void addListener(IModelEventHandler eventHandler) {
        eventHandlerList.add(eventHandler);
    }

    public void removeListener(IModelEventHandler eventHandler) {
        eventHandlerList.remove(eventHandler);
    }

    public void loadModelFromDisk() {
        loadModelFromDisk(tasksPath);
        loadModelFromDisk(plansPath);
        loadModelFromDisk(rolesPath);
    }

    /**
     * This method could be superfluous, as "loadModelFile" is maybe called by the file watcher.
     * Anyway, temporarily this is a nice method for testing and is therefore called in the constr.
     */
    private void loadModelFromDisk(String directory) {
        File plansDirectory = new File(directory);
        if (!plansDirectory.exists()) {
            return;
        }

        File[] allModelFiles = plansDirectory.listFiles();
        if (allModelFiles == null || allModelFiles.length == 0) {
            return;
        }

        for (File modelFile : allModelFiles) {
            if (modelFile.isDirectory()) {
                loadModelFromDisk(modelFile.getPath());
            }
            loadModelFile(modelFile);
        }
    }

    /**
     * Determines the file ending and loads the model object, accordingly.
     * The constructed object is inserted in the corresponding model element maps, if no
     * duplicated ID was found.
     * <p>
     * This method should only be called through a command or within the model manager itself!
     *
     * @param modelFile the file to be parsed by jackson
     */
    private void loadModelFile(File modelFile) throws RuntimeException {
        if(modelFile.isDirectory()) {
            return;
        }
        
        if(modelFile.length() == 0) {
            return;
        }

        String path = modelFile.toString();
        int pointIdx = path.lastIndexOf('.');
        String ending = "";
        if (pointIdx != -1) {
            ending = path.substring(pointIdx, path.length());
        }

        try {
            switch (ending) {
                case ".pml":
                    Plan plan = objectMapper.readValue(modelFile, Plan.class);
                    if (planElementMap.containsKey(plan.getId())) {
                        throw new RuntimeException("PlanElement ID duplication found! ID is: " + plan.getId());
                    } else {
                        ArrayList<Task> incompleteTasks = ParsedModelReference.getInstance().incompleteTasks;
                        for (EntryPoint ep : plan.getEntryPoints()) {
                            if (incompleteTasks.contains(ep.getTask())) {
                                for (Task task : taskRepository.getTasks()) {
                                    if (task.getId() == ep.getTask().getId()) {
                                        ep.setTask(task);
                                        break;
                                    }
                                }
                            }
                        }
                        planElementMap.put(plan.getId(), plan);
                        planMap.put(plan.getId(), plan);
                        fireCreationEvent(plan);
                    }
                    break;
                case ".beh":
                    Behaviour behaviour = objectMapper.readValue(modelFile, Behaviour.class);
                    if (planElementMap.containsKey(behaviour.getId())) {
                        throw new RuntimeException("PlanElement ID duplication found! ID is: " + behaviour.getId());
                    } else {
                        planElementMap.put(behaviour.getId(), behaviour);
                        behaviourMap.put(behaviour.getId(), behaviour);
                        fireCreationEvent(behaviour);
                    }
                    break;
                case ".pty":
                    PlanType planType = objectMapper.readValue(modelFile, PlanType.class);
                    if (planElementMap.containsKey(planType.getId())) {
                        throw new RuntimeException("PlanElement ID duplication found! ID is: " + planType.getId());
                    } else {
                        planElementMap.put(planType.getId(), planType);
                        planTypeMap.put(planType.getId(), planType);
                        fireCreationEvent(planType);
                    }
                    break;
                case ".tsk":
                    TaskRepository taskRepository = objectMapper.readValue(modelFile, TaskRepository.class);
                    if (planElementMap.containsKey(taskRepository.getId())) {
                        throw new RuntimeException("PlanElement ID duplication found! ID is: " + taskRepository.getId());
                    } else {
                        for (Task task : taskRepository.getTasks()) {
                            task.setTaskRepository(taskRepository);
                        }
                        long defaultTaskId = ParsedModelReference.getInstance().defaultTaskId;
                        for (Task task : taskRepository.getTasks()) {
                            if (task.getId() == defaultTaskId) {
                                taskRepository.setDefaultTask(task);
                                break;
                            }
                        }
                        planElementMap.put(taskRepository.getId(), taskRepository);
                        this.taskRepository = taskRepository;
                        fireCreationEvent(this.taskRepository);
                    }
                    break;
                case ".rset":
                case ".cdefset":
                case ".graph":
                case ".rdefset":
                    LOG.error("Parsing roles not implemented, yet!");
                    break;
                default:
                    LOG.error("Received file with unknown file ending, for parsing. File is: '" + path + "'");
//                    throw new RuntimeException("Received file with unknown file ending, for parsing. File is: '" + path + "'");
            }
        } catch (com.fasterxml.jackson.databind.exc.MismatchedInputException e) {
            System.err.println("PlanDesigner-ModelManager: Unable to parse " + modelFile);
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> T parseFile(File modelFile, Class<T> type) {
        T planElement;
        try {
            planElement = objectMapper.readValue(modelFile, type);
        } catch (com.fasterxml.jackson.databind.exc.MismatchedInputException e) {
            System.err.println("PlanDesigner-ModelManager: Unable to parse " + modelFile);
            System.err.println(e.getMessage());
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return planElement;
    }

    private void fireCreationEvent(PlanElement element) {
        for (IModelEventHandler eventHandler : eventHandlerList) {
            eventHandler.handleModelEvent(new ModelEvent(ModelOperationType.ELEMENT_CREATED, null, element));
        }
    }

    private void fireDeletionEvent(PlanElement element) {
        for (IModelEventHandler eventHandler : eventHandlerList) {
            eventHandler.handleModelEvent(new ModelEvent(ModelOperationType.ELEMENT_DELETED, null, element));
        }
    }

    public PlanElement addPlanElement(PlanElement newElement, String type) {
        switch (type) {
            case Types.PLAN:
                planMap.put(newElement.getId(), (Plan) newElement);
                break;
            default:
                System.err.println("ModelManager: adding or replacing " + type + " not implemented, yet!");
                return null;
        }
        PlanElement oldElement = planElementMap.get(newElement.getId());
        planElementMap.put(newElement.getId(), newElement);
        fireDeletionEvent(oldElement);
        fireCreationEvent(newElement);
        return oldElement;
    }

    public void removePlanElement(PlanElement planElement, String type) {
        switch (type) {
            case Types.PLAN:
                planMap.remove(planElement.getId());
                break;
            default:
                System.err.println("ModelManager: removing " + type + " not implemented, yet!");
        }
        planElementMap.remove(planElement.getId());
        fireDeletionEvent(planElement);
    }

    public void removeAbstractPlan(AbstractPlan abstractPlan) {
        System.err.println("ModelManager: Method removeAbstractPlan(AbstractPlan abstractPlan) should be deleted when commands are corrected. See removePlanElement(..).");
    }

    private PlanElement deletePlanElement(Path path) {
        PlanElement deletedElement = null;
        String pathString = path.toString();
        String ending = pathString.substring(pathString.lastIndexOf('.'), pathString.length());
        try {
            switch (ending) {
                case ".pml":
                    for (Plan plan : planMap.values()) {
                        if (pathString.contains(plan.getName() + ".pml")) {
                            deletedElement = plan;
                            planMap.remove(plan.getId());
                            break;
                        }
                    }
                    break;
                case ".beh":
                    for (Behaviour behaviour : behaviourMap.values()) {
                        if (pathString.contains(behaviour.getName() + ".beh")) {
                            deletedElement = behaviour;
                            behaviourMap.remove(behaviour.getId());
                            break;
                        }
                    }
                    break;
                case ".pty":
                    for (PlanType planType : planTypeMap.values()) {
                        if (pathString.contains(planType.getName() + ".pty")) {
                            deletedElement = planType;
                            planTypeMap.remove(planType.getId());
                            break;
                        }
                    }
                    break;
                case ".tsk":
                    if (pathString.contains(this.taskRepository.getName() + ".tsk")) {
                        deletedElement = this.taskRepository;
                        this.taskRepository = null;
                    }

                    break;
                case ".rset":
                    // TODO: Implement role and stuff parsing with jackson.
                    throw new RuntimeException("Parsing roles not implemented, yet!");
                default:
                    throw new RuntimeException("Received file with unknown file ending, for parsing.");
            }

            if (deletedElement == null) {
                throw new RuntimeException("PlanElement not found! Path is: " + path);
            }

            if (planElementMap.containsKey(deletedElement.getId())) {
                planElementMap.remove(deletedElement.getId());
                fireDeletionEvent(deletedElement);
            } else {
                throw new RuntimeException("PlanElement ID not found! ID is: " + deletedElement.getId() + " Type is " + deletedElement.getClass());
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return deletedElement;
    }

    public ArrayList<PlanElement> getUsages(long modelElementId) {
        ArrayList<PlanElement> usages = new ArrayList<>();

        PlanElement planElement = planElementMap.get(modelElementId);
        if (planElement == null) {
            return null;
        }

        if (planElement instanceof Plan) {
            usages.addAll(getUsagesInStates(planElement));
            usages.addAll(getUsagesInPlanTypes(planElement));
        } else if (planElement instanceof Behaviour) {
            usages.addAll(getUsagesInStates(planElement));
        } else if (planElement instanceof PlanType) {
            usages.addAll(getUsagesInStates(planElement));
        } else if (planElement instanceof Task) {
            usages.addAll(getUsagesInEntryPoints(planElement));
        } else {
            throw new RuntimeException("Usages requested for unhandled modelElementType of element with id  " + modelElementId);
        }
        return usages;
    }

    private ArrayList<Plan> getUsagesInEntryPoints(PlanElement planElement) {
        ArrayList<Plan> usages = new ArrayList<>();
        for (Plan parent : planMap.values()) {
            for (EntryPoint entryPoint : parent.getEntryPoints()) {
                if (entryPoint.getTask().getId() == planElement.getId()) {
                    usages.add(parent);
                }
            }
        }
        return usages;
    }

    private ArrayList<PlanType> getUsagesInPlanTypes(PlanElement planElement) {
        ArrayList<PlanType> usages = new ArrayList<>();
        for (PlanType parent : planTypeMap.values()) {
            for (Plan child : parent.getPlans()) {
                if (child.getId() == planElement.getId()) {
                    usages.add(parent);
                }
            }
        }
        return usages;
    }

    private ArrayList<Plan> getUsagesInStates(PlanElement planElement) {
        ArrayList<Plan> usages = new ArrayList<>();
        for (Plan parent : planMap.values()) {
            for (State state : parent.getStates()) {
                for (AbstractPlan child : state.getPlans()) {
                    if (child.getId() == planElement.getId()) {
                        usages.add(parent);
                    }
                }
            }
        }
        return usages;
    }

    public void handleModelModificationQuery(ModelModificationQuery mmq) {
        AbstractCommand cmd;
        switch (mmq.getOperationType()) {
            case CREATE_ELEMENT:
                cmd = new CreatePlan(this, mmq);
                break;
            case PARSE_ELEMENT:
                cmd = new ParseAbstractPlan(this, mmq);
                break;
            default:
                System.err.println("Unkown model modification query gets ignored!");
                return;
        }
        commandStack.storeAndExecute(cmd);
    }

    /**
     * This method should only be called through the command stack!
     *
     * @param plan
     */
    public void addPlan(Plan plan) {
        planMap.put(plan.getId(), plan);
        planElementMap.put(plan.getId(), plan);
        serializeToDisk(plan);
        fireCreationEvent(plan);
    }

    public String makeRelativePlansDirectory(String absoluteDirectory, String fileName) {
        String relativeDirectory = absoluteDirectory.replace(plansPath, "");
        relativeDirectory = relativeDirectory.replace(fileName, "");
        if (relativeDirectory.startsWith(File.separator)) {
            relativeDirectory = relativeDirectory.substring(1);
        }
        if (relativeDirectory.endsWith(File.separator)) {
            relativeDirectory = relativeDirectory.substring(0, relativeDirectory.length() - 1);
        }
        return relativeDirectory;
    }

    public void setDefaultTaskId(long defaultTaskId) {
        this.defaultTaskId = defaultTaskId;
    }

    public void moveFile(long movedFileId, Path originalPath, Path newPath) {
        try {
            if (originalPath.endsWith("pml")) {
                //TODO implement once pmlex is supported
//                    Files.move(new File(originalPath + "ex").toPath(),
//                            new File(newPath + "ex").toPath());
            }
            Files.move(originalPath, newPath);
            for (long planElementId : planElementMap.keySet()) {
                if (planElementId == movedFileId) {
                    PlanElement planElement = planElementMap.get(planElementId);
                    if (planElement instanceof Plan) {
                        Plan plan = planMap.get(planElementId);
                        String relativeDirectory = makeRelativePlansDirectory(newPath.toString(), plan.getName() + ".pml");
                        plan.setRelativeDirectory(relativeDirectory);
                        ((Plan) planElement).setRelativeDirectory(relativeDirectory);
                        File outfile = new File(newPath.toString());
                        objectMapper.writeValue(outfile, plan);
                    }
                }
            }
        } catch (IOException e1) {
            throw new RuntimeException(e1);
        }
    }

    /**
     * Serializes an AbstractPlan to disk.
     *
     * @param abstractPlan
     */
    public void serializeToDisk(AbstractPlan abstractPlan) {
        try {
            File outfile = Paths.get(plansPath, abstractPlan.getRelativeDirectory(), abstractPlan.getName() + ".pml").toFile();
            objectMapper.writeValue(outfile, abstractPlan);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
