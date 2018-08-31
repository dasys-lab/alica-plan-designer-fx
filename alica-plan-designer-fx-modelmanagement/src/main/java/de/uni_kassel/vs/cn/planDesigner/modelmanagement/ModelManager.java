package de.uni_kassel.vs.cn.planDesigner.modelmanagement;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.*;
import de.uni_kassel.vs.cn.planDesigner.command.*;
import de.uni_kassel.vs.cn.planDesigner.command.add.AddPlanToPlanType;
import de.uni_kassel.vs.cn.planDesigner.command.change.ChangeAttributeValue;
import de.uni_kassel.vs.cn.planDesigner.command.create.CreateBehaviour;
import de.uni_kassel.vs.cn.planDesigner.command.create.CreatePlan;
import de.uni_kassel.vs.cn.planDesigner.command.create.CreatePlanType;
import de.uni_kassel.vs.cn.planDesigner.command.create.CreateTask;
import de.uni_kassel.vs.cn.planDesigner.command.delete.*;
import de.uni_kassel.vs.cn.planDesigner.events.IModelEventHandler;
import de.uni_kassel.vs.cn.planDesigner.events.ModelEvent;
import de.uni_kassel.vs.cn.planDesigner.events.ModelEventType;
import de.uni_kassel.vs.cn.planDesigner.events.ModelQueryType;
import de.uni_kassel.vs.cn.planDesigner.modelMixIns.*;
import javafx.collections.ListChangeListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class ModelManager implements Observer {

    private static final Logger LOG = LogManager.getLogger(ModelManager.class);

    private String plansPath;
    private String tasksPath;
    private String rolesPath;

    private HashMap<Long, PlanElement> planElementMap;
    private HashMap<Long, Plan> planMap;
    private HashMap<Long, Behaviour> behaviourMap;
    private HashMap<Long, PlanType> planTypeMap;
    private HashMap<Long, AnnotatedPlan> annotatedPlanMap;
    private TaskRepository taskRepository;

    private List<IModelEventHandler> eventHandlerList;
    private CommandStack commandStack;

    /**
     * This list remembers elements that should be saved, in order
     * to ignore filesystem-modification-events created by the
     * save command. Entries gets deleted through the events.
     */
    private HashMap<Long, Integer> elementsSavedMap;

    private HashMap<Long, Integer> elementDeletedMap;

    private ObjectMapper objectMapper;

    public ModelManager() {
        planElementMap = new HashMap<>();
        planMap = new HashMap<>();
        behaviourMap = new HashMap<>();
        planTypeMap = new HashMap<>();
        annotatedPlanMap = new HashMap<>();
        eventHandlerList = new ArrayList<IModelEventHandler>();
        commandStack = new CommandStack();
        commandStack.addObserver(this);
        elementsSavedMap = new HashMap<>();
        elementDeletedMap = new HashMap<>();

        setupObjectMapper();
    }

    private void setupObjectMapper() {
        objectMapper = new ObjectMapper();

        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        objectMapper.addMixIn(EntryPoint.class, EntryPointMixIn.class);
        objectMapper.addMixIn(Parametrisation.class, ParametrisationMixIn.class);
        objectMapper.addMixIn(AnnotatedPlan.class, AnnotatedPlanMixIn.class);
        objectMapper.addMixIn(Plan.class, PlanMixIn.class);
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

    public ArrayList<PlanType> getPlanTypes() {
        return new ArrayList<>(planTypeMap.values());
    }

    public ArrayList<Behaviour> getBehaviours() {
        return new ArrayList<>(behaviourMap.values());
    }

    public ArrayList<PlanElement> getPlanElements() {
        return new ArrayList<>(planElementMap.values());
    }

    public PlanElement getPlanElement(long id) {
        if (planElementMap.containsKey(id)) {
            return planElementMap.get(id);
        } else {
            return null;
        }
    }

    public PlanElement getPlanElement(String absolutePath) {
        String name = absolutePath.substring(absolutePath.lastIndexOf(File.separator) + 1);
        String[] split = name.split("\\.");
        if (split[1].equals(FileSystemUtil.BEHAVIOUR_ENDING)) {
            for (Behaviour beh : behaviourMap.values()) {
                if (beh.getName().equals(split[0])) {
                    return beh;
                }
            }
        } else if (split[1].equals(FileSystemUtil.PLAN_ENDING)) {
            for (Plan plan : planMap.values()) {
                if (plan.getName().equals(split[0])) {
                    return plan;
                }
            }
        } else if (split[1].equals(FileSystemUtil.PLANTYPE_ENDING)) {
            for (PlanType pt : planTypeMap.values()) {
                if (pt.getName().equals(split[0])) {
                    return pt;
                }
            }
        } else if (split[1].equals(FileSystemUtil.TASKREPOSITORY_ENDING)) {
            if (taskRepository.getName().equals(split[0])) {
                return taskRepository;
            }
        } else {
            System.out.println("ModelManager: trying to get PlanElement for unsupported ending: " + split[1]);
        }
        return null;
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

    public void loadModelFromDisk() {
        loadModelFromDisk(tasksPath);
        loadModelFromDisk(plansPath);
        loadModelFromDisk(rolesPath);
        replaceIncompleteReferences();
    }

    private void replaceIncompleteReferences() {
        for (Plan plan : planMap.values()) {
            replaceIncompleteTasksInEntryPoints(plan);
            replaceIncompleteAbstractPlansInStates(plan);
            replaceIncompleteStatesAndSynchronizationsInTransitions(plan);
            if (plan.getMasterPlan()) {
                fireEvent(new ModelEvent(ModelEventType.ELEMENT_PARSED, plan, Types.MASTERPLAN));
            } else {
                fireEvent(new ModelEvent(ModelEventType.ELEMENT_PARSED, plan, Types.PLAN));
            }
        }
        for (PlanType planType : getPlanTypes()) {
            replaceIncompletePlansInPlanType(planType);
            fireEvent(new ModelEvent(ModelEventType.ELEMENT_PARSED, planType, Types.PLANTYPE));
        }
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
                    plan.dirtyProperty().addListener((observable, oldValue, newValue) -> {
                        ModelEvent event = new ModelEvent(ModelEventType.ELEMENT_ATTRIBUTE_CHANGED, taskRepository, Types.TASKREPOSITORY);
                        event.setChangedAttribute("dirty");
                        event.setNewValue(newValue);
                        this.fireEvent(event);
                    });
                    if (planElementMap.containsKey(plan.getId())) {
                        throw new RuntimeException("PlanElement ID duplication found! ID is: " + plan.getId());
                    } else {
                        planElementMap.put(plan.getId(), plan);
                        planMap.put(plan.getId(), plan);
                        for (State state : plan.getStates()) {
                            planElementMap.put(state.getId(), state);
                        }
                        for (Transition transition : plan.getTransitions()) {
                            planElementMap.put(transition.getId(), transition);
                        }
                        for (EntryPoint ep : plan.getEntryPoints()) {
                            planElementMap.put(ep.getId(), ep);
                        }
                        for (Synchronization sync : plan.getSynchronizations()) {
                            planElementMap.put(sync.getId(), sync);
                        }
                        for (Variable variable : plan.getVariables()) {
                            planElementMap.put(variable.getId(), variable);
                        }
                        if (plan.getPreCondition() != null) {
                            planElementMap.put(plan.getPreCondition().getId(), plan.getPreCondition());
                        }
                        if (plan.getRuntimeCondition() != null) {
                            planElementMap.put(plan.getRuntimeCondition().getId(), plan.getRuntimeCondition());
                            if (plan.getMasterPlan()) {
                                fireEvent(new ModelEvent(ModelEventType.ELEMENT_PARSED, plan, Types.MASTERPLAN));
                            } else {
                                fireEvent(new ModelEvent(ModelEventType.ELEMENT_PARSED, plan, Types.PLAN));
                            }
                        }
                    }
                    break;
                case FileSystemUtil.BEHAVIOUR_ENDING:
                    Behaviour behaviour = objectMapper.readValue(modelFile, Behaviour.class);
                    behaviour.dirtyProperty().addListener((observable, oldValue, newValue) -> {
                        ModelEvent event = new ModelEvent(ModelEventType.ELEMENT_ATTRIBUTE_CHANGED, taskRepository, Types.TASKREPOSITORY);
                        event.setChangedAttribute("dirty");
                        event.setNewValue(newValue);
                        this.fireEvent(event);
                    });
                    if (planElementMap.containsKey(behaviour.getId())) {
                        throw new RuntimeException("PlanElement ID duplication found! ID is: " + behaviour.getId());
                    } else {
                        planElementMap.put(behaviour.getId(), behaviour);
                        behaviourMap.put(behaviour.getId(), behaviour);
                        for (Variable variable : behaviour.getVariables()) {
                            planElementMap.put(variable.getId(), variable);
                        }
                        if (behaviour.getPreCondition() != null) {
                            planElementMap.put(behaviour.getPreCondition().getId(), behaviour.getPreCondition());
                        }
                        if (behaviour.getRuntimeCondition() != null) {
                            planElementMap.put(behaviour.getRuntimeCondition().getId(), behaviour.getRuntimeCondition());
                        }
                        if (behaviour.getPostCondition() != null) {
                            planElementMap.put(behaviour.getPostCondition().getId(), behaviour.getPostCondition());
                        }
                        fireEvent(new ModelEvent(ModelEventType.ELEMENT_PARSED, behaviour, Types.BEHAVIOUR));
                    }
                    break;
                case FileSystemUtil.PLANTYPE_ENDING:
                    PlanType planType = objectMapper.readValue(modelFile, PlanType.class);
                    planType.dirtyProperty().addListener((observable, oldValue, newValue) -> {
                        ModelEvent event = new ModelEvent(ModelEventType.ELEMENT_ATTRIBUTE_CHANGED, taskRepository, Types.TASKREPOSITORY);
                        event.setChangedAttribute("dirty");
                        event.setNewValue(newValue);
                        this.fireEvent(event);
                    });
                    if (planElementMap.containsKey(planType.getId())) {
                        throw new RuntimeException("PlanElement ID duplication found! ID is: " + planType.getId());
                    } else {
                        planElementMap.put(planType.getId(), planType);
                        planTypeMap.put(planType.getId(), planType);
                        for (AnnotatedPlan annotatedPlan : planType.getPlans()) {
                            planElementMap.put(annotatedPlan.getId(), annotatedPlan);
                        }
                    }
                    break;
                case FileSystemUtil.TASKREPOSITORY_ENDING:
                    TaskRepository taskRepository = objectMapper.readValue(modelFile, TaskRepository.class);
                    taskRepository.dirtyProperty().addListener((observable, oldValue, newValue) -> {
                        ModelEvent event = new ModelEvent(ModelEventType.ELEMENT_ATTRIBUTE_CHANGED, taskRepository, Types.TASKREPOSITORY);
                        event.setChangedAttribute("dirty");
                        event.setNewValue(newValue);
                        this.fireEvent(event);
                    });
                    taskRepository.getTasks().addListener(new ListChangeListener<Task>() {
                        @Override
                        public void onChanged(Change<? extends Task> change) {
                            while (change.next()) {
                                taskRepository.setDirty(true);
                            }
                        }
                    });
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
                        fireEvent(new ModelEvent(ModelEventType.ELEMENT_PARSED, this.taskRepository, Types.TASKREPOSITORY));
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
     *
     * @param plan
     */
    public void replaceIncompleteTasksInEntryPoints(Plan plan) {
        for (EntryPoint ep : plan.getEntryPoints()) {
            for (Task task : taskRepository.getTasks()) {
                if (task.getId() == ep.getTask().getId()) {
                    ep.setTask(task);
                    break;
                }
            }
        }
    }

    private void replaceIncompleteAbstractPlansInStates(Plan plan) {
        ParsedModelReferences refs = ParsedModelReferences.getInstance();
        for (State state : plan.getStates()) {
            for (int i = 0; i < state.getPlans().size(); i++) {
                if (refs.incompleteAbstractPlansInStates.contains(state.getPlans().get(i).getId())) {
                    state.getPlans().set(i, (AbstractPlan) planElementMap.get(state.getPlans().get(i).getId()));
                }
            }
        }
    }

    private void replaceIncompleteStatesAndSynchronizationsInTransitions(Plan plan) {
        ParsedModelReferences refs = ParsedModelReferences.getInstance();
        for (Transition transition : plan.getTransitions()) {
            if (refs.incompleteAbstractPlansInStates.contains(transition.getInState().getId())) {
                transition.setInState((State) planElementMap.get(transition.getInState().getId()));
            }
            if (refs.incompleteAbstractPlansInStates.contains(transition.getOutState().getId())) {
                transition.setOutState((State) planElementMap.get(transition.getOutState().getId()));
            }
            if (transition.getSynchronization() != null && refs.incompleteSynchronizationsInTransitions.contains(transition.getSynchronization().getId())) {
                transition.setSynchronization((Synchronization) planElementMap.get(transition.getSynchronization().getId()));
            }
        }
    }


    /**
     * Replaces all incomplete Plans in given PlanType by already parsed ones
     */
    public void replaceIncompletePlansInPlanType(PlanType planType) {
        ArrayList<AnnotatedPlan> annotatedPlans = planType.getPlans();
        for (int i = 0; i < annotatedPlans.size(); i++) {
            if (ParsedModelReferences.getInstance().incompletePlansInPlanTypes.contains(annotatedPlans.get(i).getPlan().getId())) {
                annotatedPlans.get(i).setPlan(planMap.get(annotatedPlans.get(i).getPlan().getId()));
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
            while (modelFile.length() == 0) {
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

    public void fireEvent(ModelEvent event) {
        if (event != null) {
            for (IModelEventHandler eventHandler : eventHandlerList) {
                eventHandler.handleModelEvent(event);
            }
        }
    }

    /**
     * This methods should only be called through commands.
     *
     * @param type
     * @param newElement
     * @param parentElement
     * @param serializeToDisk
     * @return
     */
    public PlanElement addPlanElement(String type, PlanElement newElement, PlanElement parentElement, boolean serializeToDisk) {
        /**
         * This is for ignoring file system modified events that come twice after saving a file
         * and once after moving or creating a file
         */
        if (elementsSavedMap.containsKey(newElement.getId())) {
            int counter = elementsSavedMap.get(newElement.getId()) - 1;
            if (counter == 0) {
                // second event arrived, so remove the entry
                elementsSavedMap.remove(newElement.getId());
            } else {
                // first event arrived, so set the reduced counter
                elementsSavedMap.put(newElement.getId(), counter);
            }
            return null;
        }
        switch (type) {
            case Types.PLAN:
            case Types.MASTERPLAN:
                Plan plan = (Plan) newElement;
                planMap.put(plan.getId(), plan);
                if (serializeToDisk) {
                    serializeToDisk(plan, FileSystemUtil.PLAN_ENDING, true);
                }
                break;
            case Types.ANNOTATEDPLAN:
                AnnotatedPlan annotatedPlan = (AnnotatedPlan) newElement;
                annotatedPlanMap.put(annotatedPlan.getId(), annotatedPlan);
                break;
            case Types.PLANTYPE:
                PlanType planType = (PlanType) newElement;
                planTypeMap.put(planType.getId(), planType);
                if (serializeToDisk) {
                    serializeToDisk(planType, FileSystemUtil.PLANTYPE_ENDING, true);
                }
                break;
            case Types.TASK:
                Task task = (Task) newElement;
                taskRepository.getTasks().add(task);
                task.setTaskRepository(taskRepository);
                break;
            case Types.TASKREPOSITORY:
                taskRepository = (TaskRepository) newElement;
                if (serializeToDisk) {
                    serializeToDisk(taskRepository, FileSystemUtil.TASKREPOSITORY_ENDING, true);
                }
                break;
            case Types.BEHAVIOUR:
                Behaviour behaviour = (Behaviour) newElement;
                behaviourMap.put(behaviour.getId(), behaviour);
                if (serializeToDisk) {
                    serializeToDisk(behaviour, FileSystemUtil.BEHAVIOUR_ENDING, true);
                }
            default:
                System.err.println("ModelManager: adding or replacing " + type + " not implemented, yet!");
                return null;
        }
        PlanElement oldElement = planElementMap.get(newElement.getId());
        if (oldElement != null) {
            fireEvent(new ModelEvent(ModelEventType.ELEMENT_DELETED, oldElement, type));
        }
        planElementMap.put(newElement.getId(), newElement);
        if (parentElement != null) {
            ModelEvent event = new ModelEvent(ModelEventType.ELEMENT_CREATED, newElement, type);
            event.setParentId(parentElement.getId());
            fireEvent(event);
        } else {
            fireEvent(new ModelEvent(ModelEventType.ELEMENT_CREATED, newElement, type));
        }
        return oldElement;
    }

    public void removePlanElement(String type, PlanElement planElement, PlanElement parentElement, boolean removeFromDisk) {
        switch (type) {
            case Types.PLAN:
            case Types.MASTERPLAN:
                Plan plan = (Plan) planElement;
                planMap.remove(plan.getId());
                if (removeFromDisk) {
                    removeFromDisk(plan, FileSystemUtil.PLAN_ENDING, true);
                }
                break;
            case Types.ANNOTATEDPLAN:
                AnnotatedPlan annotatedPlan = (AnnotatedPlan) planElement;
                annotatedPlanMap.put(annotatedPlan.getId(), annotatedPlan);
                break;
            case Types.PLANTYPE:
                PlanType planType = (PlanType) planElement;
                planTypeMap.remove(planType.getId());
                if (removeFromDisk) {
                    removeFromDisk(planType, FileSystemUtil.PLANTYPE_ENDING, true);
                }
                break;
            case Types.TASK:
                Task task = (Task) planElement;
                taskRepository.getTasks().remove(task);
                task.setTaskRepository(null);
                break;
            case Types.TASKREPOSITORY:
                taskRepository = null;
                if (removeFromDisk) {
                    removeFromDisk((TaskRepository) planElement, FileSystemUtil.TASKREPOSITORY_ENDING, true);
                }
                break;
            case Types.BEHAVIOUR:
                Behaviour behaviour = (Behaviour) planElement;
                behaviourMap.remove(behaviour.getId());
                if (removeFromDisk) {
                    removeFromDisk(behaviour, FileSystemUtil.BEHAVIOUR_ENDING, true);
                }
                break;
            default:
                System.err.println("ModelManager: removing " + type + " not implemented, yet!");
        }
        for (IModelEventHandler eventHandler : eventHandlerList) {
            eventHandler.handleCloseTab(planElement.getId());
        }
        if (parentElement != null) {
            planElementMap.remove(planElement.getId());

            ModelEvent event = new ModelEvent(ModelEventType.ELEMENT_DELETED, planElement, type);
            event.setParentId(parentElement.getId());
            fireEvent(event);
        } else {
            fireEvent(new ModelEvent(ModelEventType.ELEMENT_DELETED, planElement, type));
        }
    }

    public ArrayList<PlanElement> getUsages(long modelElementId) {
        ArrayList<PlanElement> usages = new ArrayList<>();

        PlanElement planElement = planElementMap.get(modelElementId);
        if (planElement == null) {
            System.err.println("ModelManager: Usages for unknown plan element (ID: " + modelElementId + ") requested!");
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
        } else if (planElement instanceof TaskRepository) {
            // TODO: Why do all plans use the task repository? Actually, they use tasks out of the task repo...
            usages.addAll(getPlans());
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
            for (AnnotatedPlan child : parent.getPlans()) {
                if (child.getPlan().getId() == planElement.getId()) {
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
                        System.err.println("ModelManager: Creation of unknown model element eventType gets ignored!");
                        return;
                }

                break;
            case PARSE_ELEMENT:
                cmd = new ParseAbstractPlan(this, mmq);
                break;
            case DELETE_ELEMENT:
                if (elementDeletedMap.containsKey(mmq.getElementId())) {
                    // TODO change map to list, cause counter is only 1
                    elementDeletedMap.remove(mmq.getElementId());
                    return;
                }
                switch (mmq.getElementType()) {
                    case Types.MASTERPLAN:
                    case Types.PLAN:
                        cmd = new DeletePlan(this, mmq);
                        break;
                    case Types.PLANTYPE:
                        cmd = new DeletePlanType(this, mmq);
                        break;
                    case Types.BEHAVIOUR:
                        cmd = new DeleteBehaviour(this, mmq);
                        break;
                    case Types.TASK:
                        cmd = new DeleteTaskFromRepository(this, mmq);
                        break;
                    case Types.TASKREPOSITORY:
                        cmd = new DeleteTaskRepository(this, mmq);
                        break;
                    default:
                        System.err.println("ModelManager: Deletion of unknown model element eventType " + mmq.getElementType() + " gets ignored!");
                        return;
                }
                break;
            case SAVE_ELEMENT:
                cmd = new SerializePlanElement(this, mmq);
                break;
            case ADD_ELEMENT:
                switch (mmq.getElementType()) {
                    case Types.ANNOTATEDPLAN:
                        cmd = handlePlanModelModificationQuery(mmq);
                        break;
                    default:
                        System.err.println("ModelManager: Unkown model modification query gets ignored!");
                        return;
                }
                break;
            case REMOVE_ELEMENT:
                switch (mmq.getElementType()) {
                    case Types.ANNOTATEDPLAN:
                        cmd = handlePlanModelModificationQuery(mmq);
                        break;
                    default:
                        System.err.println("ModelManager: Unkown model modification query gets ignored!");
                        return;
                }
                break;
            case REMOVE_ALL_ELEMENTS:
                switch (mmq.getElementType()) {
                    case Types.ANNOTATEDPLAN:
                        PlanType planType = planTypeMap.get(mmq.getParentId());
                        cmd = new RemoveAllPlansFromPlanType(this, planType);
                        break;
                    default:
                        System.err.println("ModelManager: Unkown model modification query gets ignored!");
                        return;
                }
                break;
            case RELOAD_ELEMENT:
                switch (mmq.getElementType()) {
                    case Types.PLANTYPE:
                        PlanType planType = planTypeMap.get(mmq.getElementId());
                        mmq.absoluteDirectory = Paths.get(plansPath, planType.getRelativeDirectory()).toString();
                        mmq.name = planType.getName();
                        cmd = new ParseAbstractPlan(this, mmq);
                        break;
                    default:
                        System.err.println("ModelManager: Unkown model modification query gets ignored!");
                        return;
                }
                break;
            case CHANGE_ELEMENT:
                // TODO: Make this a switch case command, like everywhere else in this method, too!
                cmd = new ChangeAttributeValue(this, mmq);
                break;
            case MOVE_ELEMENT:
                cmd = new MoveFile(this, mmq);
                break;
            default:
                System.err.println("ModelManager: Unknown model modification query gets ignored!");
                return;
        }
        commandStack.storeAndExecute(cmd);
    }

    /**
     * Creates a path relative to the plansPath
     *
     * @param absoluteDirectory
     * @param fileName
     * @return
     */
    public String makeRelativeDirectory(String absoluteDirectory, String fileName) {
        String relativeDirectory = absoluteDirectory.replace(plansPath, "");
        relativeDirectory = relativeDirectory.replace(tasksPath, "");
        relativeDirectory = relativeDirectory.replace(rolesPath, "");
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
     * Serializes an SerializablePlanElement to disk.
     *
     * @param planElement
     */
    public void serializeToDisk(SerializablePlanElement planElement, String ending, boolean movedOrCreated) {
        try {
            File outfile = Paths.get(plansPath, planElement.getRelativeDirectory(), planElement.getName() + "." + ending).toFile();
            if (ending.equals(FileSystemUtil.PLAN_ENDING)) {
                objectMapper.writeValue(outfile, (Plan) planElement);
                fireEvent(new ModelEvent(ModelEventType.ELEMENT_SERIALIZED, planElement, Types.PLAN));
            } else if (ending.equals(FileSystemUtil.PLANTYPE_ENDING)) {
                objectMapper.writeValue(outfile, (PlanType) planElement);
                fireEvent(new ModelEvent(ModelEventType.ELEMENT_SERIALIZED, planElement, Types.PLANTYPE));
            } else if (ending.equals(FileSystemUtil.BEHAVIOUR_ENDING)) {
                objectMapper.writeValue(outfile, (Behaviour) planElement);
                fireEvent(new ModelEvent(ModelEventType.ELEMENT_SERIALIZED, planElement, Types.BEHAVIOUR));
            } else if (ending.equals(FileSystemUtil.TASKREPOSITORY_ENDING)) {
                outfile = Paths.get(tasksPath, planElement.getRelativeDirectory(), planElement.getName() + "." + ending).toFile();
                objectMapper.writeValue(outfile, (TaskRepository) planElement);
                fireEvent(new ModelEvent(ModelEventType.ELEMENT_SERIALIZED, planElement, Types.TASKREPOSITORY));
            } else {
                throw new RuntimeException("Modelmanager: Trying to serialize a file with unknow ending: " + ending);
            }
            planElement.setDirty(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!movedOrCreated) {
            // the counter is set to 2 because, saving an element always creates two filesystem modified events
            elementsSavedMap.put(planElement.getId(), 2);
        }
    }

    /**
     * Deletes AbstractPlan from disk.
     *
     * @param planElement
     */
    public void removeFromDisk(SerializablePlanElement planElement, String ending, boolean doneByGUI) {
        if (doneByGUI) {
            elementDeletedMap.put(planElement.getId(), 1);
        }
        File outfile = Paths.get(plansPath, planElement.getRelativeDirectory(), planElement.getName() + "." + ending).toFile();
        outfile.delete();
    }

    public AbstractCommand handlePlanModelModificationQuery(ModelModificationQuery mmq) {
        PlanElement parent = planElementMap.get(mmq.getParentId());
        if (parent == null) {
            return null;
        }
        if (parent instanceof PlanType) {
            Plan plan = planMap.get(mmq.getElementId());
            if (plan == null) {
                return null;
            }
            if (mmq.getQueryType() == ModelQueryType.ADD_ELEMENT) {
                return new AddPlanToPlanType(this, plan, (PlanType) parent);
            } else if (mmq.getQueryType() == ModelQueryType.REMOVE_ELEMENT) {
                return new RemovePlanFromPlanType(this, plan, (PlanType) parent);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public TaskRepository getTaskRepository() {
        return taskRepository;
    }

    public String getAbsoluteDirectory(PlanElement element) {
        if (element instanceof Plan || element instanceof PlanType || element instanceof Behaviour) {
            return Paths.get(plansPath, ((SerializablePlanElement) element).getRelativeDirectory()).toString();
        }
        if (element instanceof TaskRepository) {
            return Paths.get(tasksPath, ((SerializablePlanElement) element).getRelativeDirectory()).toString();
        }
        return null;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof CommandStack) {
            CommandStack cmd = (CommandStack) o;
            for (IModelEventHandler modelEventHandler : eventHandlerList) {
                modelEventHandler.disableRedo(!cmd.isRedoPossible());
                modelEventHandler.disableUndo(!cmd.isUndoPossible());
            }
        }
    }

    public void undo() {
        commandStack.undo();
    }

    public void redo() {
        commandStack.redo();
    }

}
