package de.uni_kassel.vs.cn.planDesigner.modelmanagement;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.*;
import de.uni_kassel.vs.cn.planDesigner.command.*;
import de.uni_kassel.vs.cn.planDesigner.events.IModelEventHandler;
import de.uni_kassel.vs.cn.planDesigner.events.ModelEvent;
import de.uni_kassel.vs.cn.planDesigner.events.ModelQueryType;
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
        if (modelFile.isDirectory()) {
            return;
        }

        if (modelFile.length() == 0) {
            return;
        }

        String path = modelFile.toString();
        int pointIdx = path.lastIndexOf('.');
        String ending = "";
        if (pointIdx != -1) {
            ending = path.substring(pointIdx + 1, path.length());
        }

        boolean correctTopLevelFolder = correctTopLevelFolder(path);
        if (!correctTopLevelFolder) {
            return;
        }

        try {
            switch (ending) {
                case FileSystemUtil.PLAN_ENDING:
                    Plan plan = objectMapper.readValue(modelFile, Plan.class);
                    if (planElementMap.containsKey(plan.getId())) {
                        throw new RuntimeException("PlanElement ID duplication found! ID is: " + plan.getId());
                    } else {
                        replaceIncompleteTasks(plan);
                        planElementMap.put(plan.getId(), plan);
                        planMap.put(plan.getId(), plan);
                        fireCreationEvent(plan);
                    }
                    break;
                case FileSystemUtil.BEHAVIOUR_ENDING:
                    Behaviour behaviour = objectMapper.readValue(modelFile, Behaviour.class);
                    if (planElementMap.containsKey(behaviour.getId())) {
                        throw new RuntimeException("PlanElement ID duplication found! ID is: " + behaviour.getId());
                    } else {
                        planElementMap.put(behaviour.getId(), behaviour);
                        behaviourMap.put(behaviour.getId(), behaviour);
                        fireCreationEvent(behaviour);
                    }
                    break;
                case FileSystemUtil.PLANTYPE_ENDING:
                    PlanType planType = objectMapper.readValue(modelFile, PlanType.class);
                    if (planElementMap.containsKey(planType.getId())) {
                        throw new RuntimeException("PlanElement ID duplication found! ID is: " + planType.getId());
                    } else {
                        planElementMap.put(planType.getId(), planType);
                        planTypeMap.put(planType.getId(), planType);
                        fireCreationEvent(planType);
                    }
                    break;
                case FileSystemUtil.TASKREPOSITORY_ENDING:
                    TaskRepository taskRepository = objectMapper.readValue(modelFile, TaskRepository.class);
                    if (planElementMap.containsKey(taskRepository.getId())) {
                        throw new RuntimeException("PlanElement ID duplication found! ID is: " + taskRepository.getId());
                    } else {
                        long defaultTaskId = ParsedModelReferences.getInstance().defaultTaskId;
                        for (Task task : taskRepository.getTasks()) {
                            task.setTaskRepository(taskRepository);
                            if (task.getId() == defaultTaskId) {
                                taskRepository.setDefaultTask(task);
                            }
                            planElementMap.put(task.getId(), task);
                        }
                        planElementMap.put(taskRepository.getId(), taskRepository);
                        this.taskRepository = taskRepository;
                        fireCreationEvent(this.taskRepository);
                    }
                    break;

                case FileSystemUtil.ROLESET_ENDING:
                case FileSystemUtil.CAPABILITY_DEFINITION_ENDING:
                case FileSystemUtil.ROLESET_GRAPH_ENDING:
                case FileSystemUtil.ROLES_DEFINITION_ENDING:
                    LOG.error("Parsing roles not implemented, yet!");
                    break;
                default:
                    LOG.error("Received file with unknown file ending, for parsing. File is: '" + path + "'");
            }
        } catch (com.fasterxml.jackson.databind.exc.MismatchedInputException e) {
            System.err.println("PlanDesigner-ModelManager: Unable to parse " + modelFile);
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Replaces all incomplete Tasks attached to the plan's entrypoints by already parsed ones
     * @param plan
     */
    public void replaceIncompleteTasks(Plan plan) {
        ArrayList<Task> incompleteTasks = ParsedModelReferences.getInstance().incompleteTasks;
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
    }

    private boolean correctTopLevelFolder(String path) {

        if (path.endsWith(FileSystemUtil.BEHAVIOUR_ENDING) || path.endsWith(FileSystemUtil.PLAN_ENDING) || path.endsWith(FileSystemUtil.PLANTYPE_ENDING)) {
            if (path.contains(rolesPath) || path.contains(tasksPath)) {
                return false;
            }
        } else if (path.endsWith(FileSystemUtil.TASKREPOSITORY_ENDING)) {
            if (path.contains(plansPath) || path.contains(rolesPath)) {
                return false;
            }
        } else if (path.endsWith(FileSystemUtil.CAPABILITY_DEFINITION_ENDING) || path.endsWith(FileSystemUtil.ROLES_DEFINITION_ENDING) || path.endsWith
                (FileSystemUtil.ROLESET_ENDING)
                || path.endsWith(FileSystemUtil.ROLESET_GRAPH_ENDING)) {
            if (path.contains(plansPath) || path.contains(tasksPath)) {
                return false;
            }
        }
        return true;
    }

    public <T> T parseFile(File modelFile, Class<T> type) {
        T planElement;
        try {
            while (!modelFile.canRead()) {
                System.out.println("ModelManager: wait to read file: " + modelFile.toString());
                Thread.sleep(1000);
            }
            planElement = objectMapper.readValue(modelFile, type);
        } catch (com.fasterxml.jackson.databind.exc.MismatchedInputException e) {
            System.err.println("PlanDesigner-ModelManager: Unable to parse " + modelFile);
            System.err.println(e.getMessage());
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        return planElement;
    }

    private void fireCreationEvent(PlanElement element) {
        for (IModelEventHandler eventHandler : eventHandlerList) {
            eventHandler.handleModelEvent(new ModelEvent(ModelQueryType.ELEMENT_CREATED, null, element));
        }
    }

    private void fireDeletionEvent(PlanElement element) {
        for (IModelEventHandler eventHandler : eventHandlerList) {
            eventHandler.handleModelEvent(new ModelEvent(ModelQueryType.ELEMENT_DELETED, null, element));
        }
    }

    /**
     * This methods should only be called through commands.
     * @param newElement
     * @param type
     * @param serializeToDisk
     * @return
     */
    public PlanElement addPlanElement(PlanElement newElement, String type, boolean serializeToDisk) {
        switch (type) {
            case Types.PLAN:
                Plan plan = (Plan) newElement;
                planMap.put(plan.getId(), plan);
                if (serializeToDisk) {
                    serializeToDisk(plan, FileSystemUtil.PLAN_ENDING);
                }
                break;
            case Types.PLANTYPE:
                PlanType planType = (PlanType) newElement;
                planTypeMap.put(planType.getId(), planType);
                if (serializeToDisk) {
                    serializeToDisk(planType, FileSystemUtil.PLANTYPE_ENDING);
                }
                break;
            case Types.TASK:
                Task task = (Task) newElement;
                taskRepository.getTasks().add(task);
                task.setTaskRepository(taskRepository);
                break;
            default:
                System.err.println("ModelManager: adding or replacing " + type + " not implemented, yet!");
                return null;
        }
        PlanElement oldElement = planElementMap.get(newElement.getId());
        if (oldElement != null) {
            fireDeletionEvent(oldElement);
        }
        planElementMap.put(newElement.getId(), newElement);
        fireCreationEvent(newElement);
        return oldElement;
    }

    public void removePlanElement(PlanElement planElement, String type, boolean removeFromDisk) {
        switch (type) {
            case Types.PLAN:
                Plan plan = (Plan) planElement;
                planMap.remove(plan.getId());
                if (removeFromDisk) {
                    removeFromDisk(plan, FileSystemUtil.PLAN_ENDING);
                }
                break;
            case Types.PLANTYPE:
                PlanType planType = (PlanType) planElement;
                planTypeMap.remove(planType.getId());
                if (removeFromDisk) {
                    removeFromDisk(planType, FileSystemUtil.PLANTYPE_ENDING);
                }
                break;
            case Types.TASK:
                Task task = (Task) planElement;
                taskRepository.getTasks().add(task);
                task.setTaskRepository(taskRepository);
                break;
            default:
                System.err.println("ModelManager: removing " + type + " not implemented, yet!");
        }
        planElementMap.remove(planElement.getId());
        fireDeletionEvent(planElement);
    }

    public ArrayList<PlanElement> getUsages(long modelElementId) {
        ArrayList<PlanElement> usages = new ArrayList<>();

        PlanElement planElement = planElementMap.get(modelElementId);
        if (planElement == null) {
            System.err.println("ModelManager: Usages for unkown plan element (ID: " + modelElementId + ") requested!");
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
            throw new RuntimeException("Usages requested for unhandled elementType of element with id  " + modelElementId);
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
        switch (mmq.getQueryType()) {
            case CREATE_ELEMENT:
                switch (mmq.getElementType()) {
                    case Types.PLAN:
                        cmd = new CreatePlan(this, mmq);
                        break;
                    case Types.PLANTYPE:
                        cmd = new CreatePlanType(this, mmq);
                        break;
                    case Types.BEHAVIOUR:
                        cmd = new CreateBehaviour(this, mmq);
                        break;
                    case Types.TASK:
                        cmd = new CreateTask(this, mmq);
                        break;
                    default:
                        System.err.println("ModelManager: Creation of unknown model element type gets ignored!");
                        return;
                }

                break;
            case PARSE_ELEMENT:
                cmd = new ParseAbstractPlan(this, mmq);
                break;
            case DELETE_ELEMENT:
                switch (mmq.getElementType()) {
                    case Types.PLAN:
                        cmd = new DeletePlan(this, mmq);
                        break;
                    case Types.PLANTYPE:
                        cmd = null;
                        break;
                    default:
                        System.err.println("ModelManager: Creation of unkonwn model element type gets ignored!");
                        return;
                }
                break;
            default:
                System.err.println("ModelManager: Unkown model modification query gets ignored!");
                return;
        }
        commandStack.storeAndExecute(cmd);
    }

    /**
     * Creates a path relative to the plansPath
     * @param absoluteDirectory
     * @param fileName
     * @return
     */
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

    /**
     * Creates a move file command and executes it
     * @param movedFileId
     * @param originalPath
     * @param newPath
     */
    public void moveFile(long movedFileId, Path originalPath, Path newPath) {
        PlanElement elementToMove = null;
        for (long elementId : planElementMap.keySet()) {
            if (elementId == movedFileId) {
                elementToMove = planElementMap.get(elementId);
                break;
            }
        }
        if (elementToMove == null) {
            System.out.println("ModelManager: PlanElement " + movedFileId + " to move is not found!");
            return;
        }
        MoveFile moveFileCommand = new MoveFile(this, elementToMove, originalPath, newPath);
        commandStack.storeAndExecute(moveFileCommand);
    }

    /**
     * Serializes an AbstractPlan to disk.
     *
     * @param abstractPlan
     */
    public void serializeToDisk(AbstractPlan abstractPlan, String ending) {
        try {
            File outfile = Paths.get(plansPath, abstractPlan.getRelativeDirectory(), abstractPlan.getName() + "." + ending).toFile();
            if (ending.equals(FileSystemUtil.PLAN_ENDING)) {
                objectMapper.writeValue(outfile, (Plan) abstractPlan);
            } else if (ending.equals(FileSystemUtil.PLANTYPE_ENDING)) {
                objectMapper.writeValue(outfile, (PlanType) abstractPlan);
            } else if (ending.equals(FileSystemUtil.BEHAVIOUR_ENDING)) {
                objectMapper.writeValue(outfile, (Behaviour) abstractPlan);
            } else if (ending.equals(FileSystemUtil.TASKREPOSITORY_ENDING)) {
                objectMapper.writeValue(outfile, (TaskRepository) abstractPlan);
            } else {
                throw new RuntimeException("Modelmanager: Trying to serialize a file with unknow ending: " + ending);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes AbstractPlan from disk.
     *
     * @param abstractPlan
     */
    public void removeFromDisk(AbstractPlan abstractPlan, String ending) {
        File outfile = Paths.get(plansPath, abstractPlan.getRelativeDirectory(), abstractPlan.getName() + "." + ending).toFile();
        outfile.delete();
    }

    public TaskRepository getTaskRepository() {
        return taskRepository;
    }
}
