package de.unikassel.vs.alica.planDesigner.modelmanagement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.kjetland.jackson.jsonSchema.JsonSchemaGenerator;
import de.unikassel.vs.alica.planDesigner.alicamodel.*;
import de.unikassel.vs.alica.planDesigner.command.*;
import de.unikassel.vs.alica.planDesigner.command.add.AddAbstractPlan;
import de.unikassel.vs.alica.planDesigner.command.add.AddTaskToEntryPoint;
import de.unikassel.vs.alica.planDesigner.command.add.AddVariableToCondition;
import de.unikassel.vs.alica.planDesigner.command.change.*;
import de.unikassel.vs.alica.planDesigner.command.copy.CopyBehaviour;
import de.unikassel.vs.alica.planDesigner.command.copy.CopyPlan;
import de.unikassel.vs.alica.planDesigner.command.copy.CopyPlanType;
import de.unikassel.vs.alica.planDesigner.command.create.*;
import de.unikassel.vs.alica.planDesigner.command.delete.*;
import de.unikassel.vs.alica.planDesigner.command.remove.RemoveAbstractPlanFromState;
import de.unikassel.vs.alica.planDesigner.command.remove.RemoveVariableFromCondition;
import de.unikassel.vs.alica.planDesigner.events.IModelEventHandler;
import de.unikassel.vs.alica.planDesigner.events.ModelEvent;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelMixIns.*;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.BendPoint;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.UiExtension;
import org.apache.commons.beanutils.BeanUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.file.StandardCopyOption.ATOMIC_MOVE;

public class ModelManager implements Observer {

    public static final int IGNORE_DELETE_PLAN_COUNTER = 2;
    public static final int IGNORE_DELETE_BEH_PT_COUNTER = 1;
    private String plansPath;
    private String tasksPath;
    private String rolesPath;

    private HashMap<Long, PlanElement> planElementMap;
    private HashMap<Long, Plan> planMap;
    private HashMap<Long, Behaviour> behaviourMap;
    private HashMap<Long, PlanType> planTypeMap;
    private TaskRepository taskRepository;
    private RoleSet roleSet;

    private List<IModelEventHandler> eventHandlerList;
    private CommandStack commandStack;

    /**
     * This list remembers elements that should be saved, in order
     * to ignore filesystem-modification-events created by the
     * save command. Entries gets deleted through the events.
     */
    private HashMap<Long, Integer> elementsSavedMap;

    private HashMap<Long, Integer> elementDeletedMap;

    /**
     * In this map all necessary information about the visualisation
     * of a {@link Plan} is saved and can be accessed by the id of the
     * corresponding Plan
     */
    private HashMap<Long, UiExtension> uiExtensionMap;

    private ObjectMapper objectMapper;

    public ModelManager() {
        planElementMap = new HashMap<>();
        planMap = new HashMap<>();
        behaviourMap = new HashMap<>();
        planTypeMap = new HashMap<>();
        eventHandlerList = new ArrayList<>();
        commandStack = new CommandStack();
        commandStack.addObserver(this);
        elementsSavedMap = new HashMap<>();
        elementDeletedMap = new HashMap<>();
        uiExtensionMap = new HashMap<>();

        setupObjectMapper();
    }

    private void setupObjectMapper() {
        objectMapper = new ObjectMapper();

        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        objectMapper.addMixIn(EntryPoint.class, EntryPointMixIn.class);
        objectMapper.addMixIn(VariableBinding.class, VariableBindingMixIn.class);
        objectMapper.addMixIn(AnnotatedPlan.class, AnnotatedPlanMixIn.class);
        objectMapper.addMixIn(Plan.class, PlanMixIn.class);
        objectMapper.addMixIn(Quantifier.class, QuantifierMixIn.class);
        objectMapper.addMixIn(State.class, StateMixIn.class);
        objectMapper.addMixIn(Synchronisation.class, SynchronizationMixIn.class);
        objectMapper.addMixIn(Task.class, TaskMixIn.class);
        objectMapper.addMixIn(Role.class, RoleMixIn.class);
        objectMapper.addMixIn(Transition.class, TransitionMixIn.class);
        objectMapper.addMixIn(UiExtension.class, UiExtensionMixIn.class);
        objectMapper.addMixIn(BendPoint.class, BendPointMixIn.class);
        objectMapper.addMixIn(Behaviour.class, BehaviourMixIn.class);
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

    public List<Task> getTasks() {
        return this.taskRepository.getTasks();
    }

    public ArrayList<Behaviour> getBehaviours() {
        return new ArrayList<>(behaviourMap.values());
    }

    public ArrayList<PlanType> getPlanTypes() {
        return new ArrayList<>(planTypeMap.values());
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
        if (split[split.length - 1].equals(Extensions.BEHAVIOUR)) {
            for (Behaviour beh : behaviourMap.values()) {
                if (beh.getName().equals(split[0])) {
                    return beh;
                }
            }
        } else if (split[split.length - 1].equals(Extensions.PLAN)
                || split[split.length - 1].equals(Extensions.PLAN_EXTENSION)) {
            for (Plan plan : planMap.values()) {
                if (plan.getName().equals(split[0])) {
                    return plan;
                }
            }
        } else if (split[split.length - 1].equals(Extensions.PLANTYPE)) {
            for (PlanType pt : planTypeMap.values()) {
                if (pt.getName().equals(split[0])) {
                    return pt;
                }
            }
        } else if (split[split.length - 1].equals(Extensions.TASKREPOSITORY)) {
            if (taskRepository == null) {
                return null;
            }
            if (taskRepository.getName().equals(split[0])) {
                return taskRepository;
            }
        } else if (split[split.length - 1].equals(Extensions.ROLESET)) {
            if (roleSet != null && roleSet.getName().equals(split[0])) {
                return roleSet;
            }
        } else {
            System.out.println("ModelManager: trying to get PlanElement for unsupported ending: " + split[split.length - 1]);
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

    public HashMap<Long, UiExtension> getUiExtensionMap() {
        return uiExtensionMap;
    }

    public void addListener(IModelEventHandler eventHandler) {
        eventHandlerList.add(eventHandler);
    }

    public List<File> getGeneratedFilesForAbstractPlan(AbstractPlan abstractPlan){
        List<File> path = null;
        for(IModelEventHandler handler : eventHandlerList) {
            path = handler.getGeneratedFilesForAbstractPlan(abstractPlan);
        }
        return path;
    }
    public void generateAutoGeneratedFilesForAbstractPlan(AbstractPlan abstractPlan) {
        for(IModelEventHandler handler : eventHandlerList) {
            handler.generateAutoGeneratedFilesForAbstractPlan(abstractPlan);
        }
    }

    public void loadModelFromDisk() {
        unloadModel();
        loadModelFromDisk(tasksPath);
        if(taskRepository == null && !this.tasksPath.isEmpty()) {
            for(IModelEventHandler handler : eventHandlerList) {
                handler.handleNoTaskRepositoryNotification();
            }
        } else {
            fireEvent(new ModelEvent(ModelEventType.ELEMENT_PARSED, taskRepository, Types.TASKREPOSITORY));
        }
        loadModelFromDisk(plansPath);
        loadModelFromDisk(rolesPath);
        resolveReferences();

        for (Plan plan : planMap.values()) {
            if (plan.getMasterPlan()) {
                fireEvent(new ModelEvent(ModelEventType.ELEMENT_PARSED, plan, Types.MASTERPLAN));
            } else {
                fireEvent(new ModelEvent(ModelEventType.ELEMENT_PARSED, plan, Types.PLAN));
            }
        }

        for (PlanType planType : planTypeMap.values()) {
            fireEvent(new ModelEvent(ModelEventType.ELEMENT_PARSED, planType, Types.PLANTYPE));
        }

        for (Behaviour behaviour : behaviourMap.values()) {
            fireEvent(new ModelEvent(ModelEventType.ELEMENT_PARSED, behaviour, Types.BEHAVIOUR));
        }

        for (UiExtension uiExtension : uiExtensionMap.values()) {
            uiExtension.registerDirtyListeners();
        }

        if (taskRepository != null) {
        }

        if (roleSet != null) {
            fireEvent(new ModelEvent(ModelEventType.ELEMENT_PARSED, roleSet, Types.ROLESET));
        }
    }

    private void unloadModel() {
        planElementMap.clear();
        planMap.clear();
        behaviourMap.clear();
        planTypeMap.clear();
        taskRepository = null;
        commandStack.getRedoStack().clear();
        commandStack.getUndoStack().clear();
        elementsSavedMap.clear();
        elementDeletedMap.clear();
        uiExtensionMap.clear();
    }

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
            } else {
                loadModelFile(modelFile, false);
            }
        }
    }

    /**
     * Please resolves dummy references, only if all referenced elements are parsed already.
     * That is not the case during loading the initial model from disk.
     */
    private void loadModelFile(File modelFile, boolean resolveReferences) {
        // 0. check if valid plan ending
        String stringType = FileSystemUtil.getExtension(modelFile);
        if((stringType == Types.UNSUPPORTED)) {
            return;
        }

        // 1. parse plan element from disk
        Class classType = FileSystemUtil.getClassType(modelFile);
        Object parsedObject = parseFile(modelFile, classType);
        if (parsedObject == null) {
            return;
        }

        // Special Case for UIExtension Files
        if (stringType == Types.UIEXTENSION) {
            UiExtension uiExtension = (UiExtension) parsedObject;
            uiExtensionMap.put(uiExtension.getPlan().getId(), uiExtension);
            if (resolveReferences) {
                uiExtension.setPlan(planMap.get(uiExtension.getPlan().getId()));
            }
            return;
        }

        // 2. add plan element and its children into to maps of the model manager
        SerializablePlanElement planElement = (SerializablePlanElement) parsedObject;
        storePlanElement(FileSystemUtil.getExtension(modelFile), (PlanElement) parsedObject, false);

        // 3. resolve references
        if (resolveReferences) {
            if (Types.PLAN.equals(stringType)) {
                resolveReferences((Plan) parsedObject);
            } else if (Types.PLANTYPE.equals(stringType)) {
                resolveReferences((PlanType) parsedObject);
            }
        }

        // 4. add listener to dirty flag
        planElement.dirtyProperty().addListener((observable, oldValue, newValue) -> {
            ModelEvent event = new ModelEvent(ModelEventType.ELEMENT_ATTRIBUTE_CHANGED, planElement, stringType);
            event.setChangedAttribute("dirty");
            event.setNewValue(newValue);
            this.fireEvent(event);
        });

        // 5. register dirty flags
        planElement.registerDirtyFlag();

        // 6. Fire Event towards the UI, that the element was added/parsed ...
        if (resolveReferences) {
            if (parsedObject instanceof Plan && ((Plan) parsedObject).getMasterPlan()) {
                fireEvent(new ModelEvent(ModelEventType.ELEMENT_PARSED, planElement, Types.MASTERPLAN));
            } else {
                fireEvent(new ModelEvent(ModelEventType.ELEMENT_PARSED, planElement, stringType));
            }
        }
    }

    public <T> T parseFile(File modelFile, Class<T> type) {
        if (modelFile == null || modelFile.isDirectory() || !modelFile.exists() || !fileMatchesFolder(modelFile)) {
            throw new RuntimeException("ModelManager: The file " + modelFile + " does not exist!");
        }
        T planElement;
        try {
            while (!modelFile.canRead()) {
                System.out.println("ModelManager: wait to read file: " + modelFile.toString());
                Thread.sleep(1000);
            }
            while (modelFile.length() == 0) {
                Thread.sleep(1000);
            }
            System.out.println("MM: " + modelFile.getName() +"  "+ type);
            planElement = objectMapper.readValue(modelFile, type);
        } catch (com.fasterxml.jackson.databind.exc.MismatchedInputException
                | com.fasterxml.jackson.databind.deser.UnresolvedForwardReference e) {
            System.err.println("PlanDesigner-ModelManager: Unable to parse " + modelFile);
            System.err.println(e.getMessage());
            return null;
        } catch (IOException | InterruptedException | IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
        return planElement;
    }

    /**
     * Helps to ignore, e.g., plans in the task-folder.
     * @param file
     * @return
     */
    private boolean fileMatchesFolder(File file) {
        String path = file.toString();
        if (path.endsWith(Extensions.BEHAVIOUR) || path.endsWith(Extensions.PLAN) || path.endsWith(Extensions.PLANTYPE)) {
            if (path.contains(rolesPath) || path.contains(tasksPath)) {
                throw new RuntimeException("ModelManager: The file " + file + " is located in the wrong directory!");
            }
        } else if (path.endsWith(Extensions.TASKREPOSITORY)) {
            if (path.contains(plansPath) || path.contains(rolesPath)) {
                throw new RuntimeException("ModelManager: The file " + file + " is located in the wrong directory!");
            }
        } else if (path.endsWith(Extensions.CAPABILITY_DEFINITION) || path.endsWith(Extensions.ROLES_DEFINITION)
                || path.endsWith(Extensions.ROLESET) || path.endsWith(Extensions.ROLESET_GRAPH)) {
            if (path.contains(plansPath) || path.contains(tasksPath)) {
                throw new RuntimeException("ModelManager: The file " + file + " is located in the wrong directory!");
            }
        }
        return true;
    }

    private void resolveReferences() {
        for (Plan plan : planMap.values()) {
            resolveReferences(plan);
        }
        for (PlanType planType : planTypeMap.values()) {
            resolveReferences(planType);
        }
        for (Behaviour behaviour : behaviourMap.values()) {
            resolveReferences(behaviour);
        }
        for (UiExtension uiExtension : uiExtensionMap.values()) {
            uiExtension.setPlan(planMap.get(uiExtension.getPlan().getId()));
        }

        if (roleSet != null) {
            roleSet.getRoles().forEach(role -> resolveReferences(role));
        }
    }

    private void resolveReferences(Role role) {
        HashMap<Task, Float> taskPriorities = new HashMap<>();

        role.getTaskPriorities().forEach((t, p) -> {
            Task task = taskRepository.getTask(t.getId());
            taskPriorities.put(task, p);
        });
        role.setTaskPriorities(taskPriorities);
    }

    private void resolveReferences(Plan plan) {
        for (EntryPoint ep : plan.getEntryPoints()) {
            Task task = taskRepository.getTask(ep.getTask().getId());
            if (task == null) {
                for(IModelEventHandler handler : eventHandlerList) {
                    handler.handleWrongTaskRepositoryNotification(plan.getName(), task.getId());
                }
                return;
            }
            ep.setTask(task);
        }

        for (State state : plan.getStates()) {
            // need to copy temporarily, because "state.removeAbstractPlan" does also remove bindings
            ArrayList<VariableBinding> bindings = new ArrayList<>(state.getVariableBindings());
            for (int i = 0; i < bindings.size(); i++) {
                VariableBinding binding = bindings.get(i);
                binding.setSubPlan((AbstractPlan) getPlanElement(binding.getSubPlan().getId()));
                binding.setSubVariable((Variable) getPlanElement(binding.getSubVariable().getId()));
                binding.setVariable((Variable) getPlanElement(binding.getVariable().getId()));
            }

            // Iterating over a List while modifying it results in an IllegalAccessException. By copying the list
            // beforehand this can be prevented
            for (AbstractPlan abstractPlan : new ArrayList<>(state.getAbstractPlans())) {
                // Adding the real plan before removing the dummy
                // This will (through listeners) trigger the creation of the corresponding ViewModelElement
                // Would the dummy be removed first, the State would not contain this AbstractPlan
                // The fact, that the dummy is still referenced within the State at this point is irrelevant, because it
                // has the same id as the real one
                state.addAbstractPlan((AbstractPlan) planElementMap.get(abstractPlan.getId()));
                state.removeAbstractPlan(abstractPlan);
            }

            // here they are inserted again
            for (int i = 0; i < bindings.size(); i++) {
                state.addVariableBinding(bindings.get(i));
            }

            if(state instanceof TerminalState) {
                resolveQuantifierScopes(((TerminalState) state).getPostCondition());
            }
        }

        for (Transition transition : plan.getTransitions()) {
            transition.setInState((State) planElementMap.get(transition.getInState().getId()));
            transition.setOutState((State) planElementMap.get(transition.getOutState().getId()));
            if (transition.getSynchronisation() != null) {
                transition.setSynchronisation((Synchronisation) planElementMap.get(transition.getSynchronisation().getId()));
            }
            UiExtension visualisationObject = getPlanUIExtensionPair(plan.getId());
            for (Long planElementId : visualisationObject.getKeys()) {
                if (transition.getId() == planElementId) {
                    for (BendPoint bendPoint : visualisationObject.getUiElement(planElementId).getBendPoints()) {
                        bendPoint.setTransition(transition);
                    }
                    break;
                }
            }

            resolveQuantifierScopes(transition.getPreCondition());
        }

        resolveQuantifierScopes(plan.getPreCondition());
        resolveQuantifierScopes(plan.getRuntimeCondition());

        plan.setDirty(false);
    }

    /**
     * Replaces all incomplete Plans in given PlanType by already parsed ones
     */
    public void resolveReferences(PlanType planType) {
        List<AnnotatedPlan> annotatedPlans = planType.getAnnotatedPlans();
        for (int i = 0; i < annotatedPlans.size(); i++) {
            annotatedPlans.get(i).setPlan(planMap.get(annotatedPlans.get(i).getPlan().getId()));
        }

        for (int i = 0; i < planType.getVariableBindings().size(); i++) {
            VariableBinding variableBinding = planType.getVariableBindings().get(i);
            variableBinding.setSubPlan((AbstractPlan) getPlanElement(variableBinding.getSubPlan().getId()));
            variableBinding.setSubVariable((Variable) getPlanElement(variableBinding.getSubVariable().getId()));
            variableBinding.setVariable((Variable) getPlanElement(variableBinding.getVariable().getId()));
        }
    }

    private void resolveReferences(Behaviour behaviour) {
        resolveQuantifierScopes(behaviour.getPreCondition());
        resolveQuantifierScopes(behaviour.getRuntimeCondition());
        resolveQuantifierScopes(behaviour.getPostCondition());

        behaviour.setDirty(false);
    }

    private void resolveQuantifierScopes(Condition condition) {
        if(condition != null) {
            for(Quantifier quantifier : condition.getQuantifiers()) {
                if(quantifier.getScope() != null) {
                    quantifier.setScope(getPlanElement(quantifier.getScope().getId()));
                }
            }
        }
    }

    /**
     * Stores the given {@link PlanElement} in the corresponding maps of the {@link ModelManager}.
     *
     * @param type
     * @param planElement
     * @param serializeToDisk
     * @return Old PlanElement with the same ID, if there already existed.
     */
    public PlanElement storePlanElement(String type, PlanElement planElement, boolean serializeToDisk) {
        if (planElement == null) {
            return null;
        }

        PlanElement oldElement = planElementMap.get(planElement.getId());
        planElementMap.put(planElement.getId(), planElement);

        // early return in case of a non-serializable plan element
        if (!(planElement instanceof SerializablePlanElement)) {
            return oldElement;
        }

        switch (type) {
            case Types.PLAN:
            case Types.MASTERPLAN:
                Plan plan = (Plan) planElement;
                planMap.put(planElement.getId(), plan);
                for(Transition transition : plan.getTransitions()) {
                    planElementMap.put(transition.getId(), transition);
                    storeCondition(transition.getPreCondition());
                }
                for(EntryPoint entryPoint: plan.getEntryPoints()) {
                    planElementMap.put(entryPoint.getId(), entryPoint);
                }
                for(State state : plan.getStates()) {
                    planElementMap.put(state.getId(), state);
                    for (VariableBinding variableBinding : state.getVariableBindings()) {
                        planElementMap.put(variableBinding.getId(), variableBinding);
                    }
                    if(state instanceof TerminalState){
                        TerminalState terminalState = (TerminalState) state;
                        storeCondition(terminalState.getPostCondition());
                    }
                }
                for(Variable variable: plan.getVariables()) {
                    planElementMap.put(variable.getId(), variable);
                }
                for(Synchronisation synchronisation: plan.getSynchronisations()) {
                    planElementMap.put(synchronisation.getId(), synchronisation);
                }
                storeCondition(plan.getPreCondition());
                storeCondition(plan.getRuntimeCondition());
                break;
            case Types.PLANTYPE:
                PlanType planType = (PlanType) planElement;
                planTypeMap.put(planElement.getId(), planType);
                for(Variable variable: planType.getVariables()) {
                    planElementMap.put(variable.getId(), variable);
                }
                for(AnnotatedPlan annotatedPlan: planType.getAnnotatedPlans()) {
                    planElementMap.put(annotatedPlan.getId(), annotatedPlan);
                }
                for(VariableBinding variableBinding : planType.getVariableBindings()) {
                    planElementMap.put(variableBinding.getId(), variableBinding);
                }
                break;
            case Types.BEHAVIOUR:
                Behaviour behaviour = (Behaviour) planElement;
                behaviourMap.put(planElement.getId(), behaviour);
                for(Variable variable: behaviour.getVariables()) {
                    planElementMap.put(variable.getId(), variable);
                }
                storeCondition(behaviour.getPreCondition());
                storeCondition(behaviour.getRuntimeCondition());
                storeCondition(behaviour.getPostCondition());

                break;
            case Types.TASKREPOSITORY:
                taskRepository = (TaskRepository) planElement;
                for(Task task : taskRepository.getTasks()) {
                    planElementMap.put(task.getId(), task);
                }
                break;
            case Types.ROLESET:
                roleSet = (RoleSet) planElement;
                for(Role role : roleSet.getRoles()) {
                    planElementMap.put(role.getId(), role);
                    for (Characteristic characteristic:role.getCharacteristics()) {
                        planElementMap.put(characteristic.getId(), characteristic);
                    }
                }
                break;
            default:
                throw new RuntimeException("ModelManager: Storing " + type + " not implemented, yet!");
        }

        SerializablePlanElement serializablePlanElement = (SerializablePlanElement) planElement;
        if (serializeToDisk) {
            serializeToDisk(serializablePlanElement,true);
        }

        return oldElement;
    }

    private void storeCondition (Condition condition) {
        if(condition != null) {
            planElementMap.put(condition.getId(), condition);
            for (Quantifier quantifier : condition.getQuantifiers()) {
                planElementMap.put(quantifier.getId(), quantifier);
            }
        }
    }

    /**
     * Removes the given {@link PlanElement} from the corresponding maps in the {@link ModelManager}.
     * @param type
     * @param planElement
     * @param removeFromDisk
     */
    public void dropPlanElement(String type, PlanElement planElement, boolean removeFromDisk) {
        if(planElement == null) {
            return;
        }

        planElementMap.remove(planElement.getId());

        if (!(planElement instanceof SerializablePlanElement)) {
            return;
        }

        SerializablePlanElement serializablePlanElement = (SerializablePlanElement) planElement;
        if (removeFromDisk) {
            removeFromDisk(serializablePlanElement, 1);
        }

        switch (type) {
            case Types.PLAN:
            case Types.MASTERPLAN:
                planMap.remove(planElement.getId());
                uiExtensionMap.remove(planElement.getId());
                break;
            case Types.PLANTYPE:
                planTypeMap.remove(planElement.getId());
                break;
            case Types.TASKREPOSITORY:
                taskRepository = null;
                break;
            case Types.ROLESET:
                roleSet = null;
                break;
            case Types.BEHAVIOUR:
                behaviourMap.remove(planElement.getId());
                break;
            default:
                throw new RuntimeException("ModelManager: ");
        }
    }

    public void changeAttribute(PlanElement planElement, String elementType, String attribute, Object newValue, Object oldValue) {
        try {
            if (planElement instanceof Behaviour && attribute.equals("parameters")) {
                Behaviour behaviour = (Behaviour) planElement;
                behaviour.modifyParameter((Map.Entry<String, String>) newValue, (Map.Entry<String, String>) oldValue);
            } else {
                BeanUtils.setProperty(planElement, attribute, newValue);
            }

            if (attribute.equals("name")) {
                String ending = "";
                switch (elementType) {
                    case Types.MASTERPLAN:
                    case Types.PLAN:
                        ending = Extensions.PLAN;
                        break;
                    case Types.PLANTYPE:
                        ending = Extensions.PLANTYPE;
                        break;
                    case Types.BEHAVIOUR:
                        ending = Extensions.BEHAVIOUR;
                        break;
                    case Types.TASKREPOSITORY:
                        ending = Extensions.TASKREPOSITORY;
                        break;
                    case Types.ROLESET:
                        ending = Extensions.ROLESET;
                        break;
                }

                if (!ending.equals("")) {
                    String dir = getAbsoluteDirectory(planElement);
                    String newName = (String) newValue;
                    String oldName = (String) oldValue;
                    renameFile(dir, newName, oldName, ending);

                    // If the renamed element is a Plan and has a .pmlex-file, the .pmlex-file is also renamed
                    if(ending.equals(Extensions.PLAN) && FileSystemUtil.getFile(dir, oldName, Extensions.PLAN_EXTENSION).exists()) {
                        renameFile(dir, newName, oldName, Extensions.PLAN_EXTENSION);
                    }
                    serializeToDisk((SerializablePlanElement) planElement, false);
                    ArrayList<PlanElement> usages = getUsages(planElement.getId());
                    if (usages != null) {
                        for (PlanElement element : usages) {
                            serializeToDisk((SerializablePlanElement) element,false);
                        }
                    }

                    // Rename autoGenerated Files
                    if(oldName != newName) {
                        boolean value = false;
                        planElement.setName(oldValue.toString());
                        List<File> oldFileList = getGeneratedFilesForAbstractPlan((AbstractPlan) planElement);
                        planElement.setName(newName);
                        List<File> newFileList = getGeneratedFilesForAbstractPlan((AbstractPlan) planElement);
                        for (int i = 0; i < oldFileList.size(); i++) {
                            if(oldFileList.get(i).exists()) {
                                oldFileList.get(i).renameTo(newFileList.get(i));
                            }
                        }
                    }
                }
            }
        } catch (IllegalAccessException | InvocationTargetException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void moveFile(SerializablePlanElement elementToMove, String type, String newAbsoluteDirectory, String ending) {
        // 1. Save previous path and previous files
        String previousPath = getAbsoluteDirectory(elementToMove);
        List<File> oldFilesToMove = getGeneratedFilesForAbstractPlan((AbstractPlan) elementToMove);

        // 2. Delete file from file system
        //TODO constant
        if(elementToMove instanceof  Plan) {
            removeFromDisk(elementToMove, IGNORE_DELETE_PLAN_COUNTER);
        } else {
            removeFromDisk(elementToMove, IGNORE_DELETE_BEH_PT_COUNTER);
        }

        // 3. Change relative directory property
        changeAttribute(elementToMove, type, "relativeDirectory",
                makeRelativeDirectory(newAbsoluteDirectory, elementToMove.getName() + "." + ending), previousPath);

        // 4. Serialize file to file system
        serializeToDisk(elementToMove, false);

        // 5. Update external references to file
        ArrayList<PlanElement> usages = getUsages(elementToMove.getId());
        for (PlanElement planElement : usages) {
            if (planElement instanceof SerializablePlanElement) {
                SerializablePlanElement serializablePlanElement = (SerializablePlanElement) planElement;
                serializeToDisk(serializablePlanElement, false);
            }
        }

        // 6. Move generated files
        List<File> filesToMove = getGeneratedFilesForAbstractPlan((AbstractPlan) elementToMove);
        for(int i = 0 ; i < filesToMove.size(); i++) {
            try {
                if(filesToMove.get(i).exists()) {
                    Files.move(oldFilesToMove.get(i).toPath(), filesToMove.get(i).toPath(), ATOMIC_MOVE);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 7. Delete old files
        for (File file: oldFilesToMove) {
            if(file.exists()) {
                file.delete();
            }
        }
    }

    private void renameFile(String absoluteDirectory, String newName, String oldName, String ending) throws IOException {
        if(newName.equals(oldName)) {
            return;
        }
        File oldFile = FileSystemUtil.getFile(absoluteDirectory, oldName, ending);
        File newFile = new File(Paths.get(absoluteDirectory, newName + "." + ending).toString());
        if (newFile.exists()) {
            throw new IOException("ChangeAttributeValue: File " + newFile.toString() + " already exists!");
        }
        if (!oldFile.renameTo(newFile)) {
            throw new IOException("ChangeAttributeValue: Could not rename " + oldFile.toString() + " to " + newFile.toString());
        }
    }

    public ArrayList<PlanElement> getUsages(long modelElementId) {
        ArrayList<PlanElement> usages = new ArrayList<>();

        PlanElement planElement = planElementMap.get(modelElementId);
        if (planElement == null) {
            System.err.println("ModelManager: Usages for unknown plan element (ID: " + modelElementId + ") requested!");
            return null;
        }

        if (planElement instanceof AbstractPlan) {
            usages.addAll(getAbstractPlanUsages(planElement));
        } else if (planElement instanceof Task) {
            usages.addAll(getTaskUsages(planElement));
        } else if (planElement instanceof Variable) {
            usages.addAll(getVariableUsages(planElement));
        } else if (planElement instanceof TaskRepository) {
            usages.addAll(getTaskRepoUsages(planElement));
        } else if (planElement instanceof RoleSet) {
            usages.addAll(getRoleSetUsages(planElement));
        } else if (planElement instanceof State) {
            usages.add(((State) planElement).getParentPlan()); // A State is always used in exactly one Plan
        } else {
            throw new RuntimeException("Usages requested for unhandled elementType of element with id  " + modelElementId);
        }
        return usages;
    }

    private HashSet<Plan> getRoleSetUsages(PlanElement planElement) {
        // TODO: implement functionality
        System.out.println("MM: getRoleSetUsages " + planElement.getName());
        HashSet<Plan> usages = new HashSet<>();
        return usages;
    }

    private HashSet<Plan> getTaskRepoUsages(PlanElement planElement) {
        HashSet<Plan> usages = new HashSet<>();
        TaskRepository taskRepo = (TaskRepository) planElement;
        for (Plan parent : planMap.values()) {
            for (EntryPoint entryPoint : parent.getEntryPoints()) {
                if (taskRepo.contains(entryPoint.getTask())) {
                    usages.add(parent);
                    break;
                }
            }
        }
        return usages;
    }

    private HashSet<AbstractPlan> getVariableUsages(PlanElement planElement) {
        HashSet<AbstractPlan> usages = new HashSet<>();
        for (Plan parent : planMap.values()) {
            if (parent.getVariables().contains(planElement)) {
                usages.add(parent);
                continue;
            }
            if (parent.getPreCondition() != null && parent.getPreCondition().getVariables().contains(planElement)) {
                usages.add(parent);
                continue;
            }
            if (parent.getRuntimeCondition() != null && parent.getRuntimeCondition().getVariables().contains(planElement)) {
                usages.add(parent);
                continue;
            }
            stateLoop:
            for (State state : parent.getStates()) {
                for (VariableBinding param : state.getVariableBindings()) {
                    if (param.getSubVariable() == planElement) {
                        usages.add(parent);
                        break stateLoop;
                    }
                    if (param.getVariable() == planElement) {
                        usages.add(parent);
                        break stateLoop;
                    }
                }
            }
            for (Transition transition : parent.getTransitions()) {
                if (transition.getPreCondition() != null && transition.getPreCondition().getVariables().contains(planElement)) {
                    usages.add(parent);
                    break;
                }
            }
        }
        for (PlanType planType : planTypeMap.values()) {
            if (planType.getVariables().contains(planElement)) {
                usages.add(planType);
                continue;
            }
            for (VariableBinding param : planType.getVariableBindings()) {
                if (param.getSubVariable() == planElement) {
                    usages.add(planType);
                    break;
                }
                if (param.getVariable() == planElement) {
                    usages.add(planType);
                    break;
                }
            }
        }
        for (Behaviour behaviour : behaviourMap.values()) {
            if (behaviour.getVariables().contains(planElement)) {
                usages.add(behaviour);
                continue;
            }
            if (behaviour.getPreCondition() != null && behaviour.getPreCondition().getVariables().contains(planElement)) {
                usages.add(behaviour);
                continue;
            }
            if (behaviour.getRuntimeCondition() != null && behaviour.getRuntimeCondition().getVariables().contains(planElement)) {
                usages.add(behaviour);
                continue;
            }
            if (behaviour.getPostCondition() != null && behaviour.getPostCondition().getVariables().contains(planElement)) {
                usages.add(behaviour);
                continue;
            }
        }
        return usages;
    }

    private HashSet<Plan> getTaskUsages(PlanElement planElement) {
        HashSet<Plan> usages = new HashSet<>();
        for (Plan parent : planMap.values()) {
            for (EntryPoint entryPoint : parent.getEntryPoints()) {
                if (entryPoint.getTask().getId() == planElement.getId()) {
                    usages.add(parent);
                    break;
                }
            }
        }
        return usages;
    }

    /**
     * Finds AbstractPlans in states and returns the set of parent plans
     * of that states.
     *
     * @param planElement
     * @return Set of plans that hold the relevant states, using the given element
     */
    private HashSet<AbstractPlan> getAbstractPlanUsages(PlanElement planElement) {
        HashSet<AbstractPlan> usages = new HashSet<>();
        for (Plan parentPlan : planMap.values()) {
            stateLoop:
            for (State state : parentPlan.getStates()) {
                for (AbstractPlan child : state.getAbstractPlans()) {
                    if (child.getId() == planElement.getId()) {
                        usages.add(parentPlan);
                        break stateLoop;
                    }
                }
            }
        }
        for (PlanType parentPlanType : planTypeMap.values()) {
            for (AnnotatedPlan child : parentPlanType.getAnnotatedPlans()) {
                if (child.getPlan().getId() == planElement.getId()) {
                    usages.add(parentPlanType);
                    break;
                }
            }
        }
        return usages;
    }

    public void handleModelModificationQuery(ModelModificationQuery mmq) {
        Command cmd;
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
                    case Types.VARIABLE:
                        cmd = new CreateVariable(this, mmq);
                        break;
                    case Types.VARIABLEBINDING:
                        cmd = new CreateVariableBinding(this, mmq);
                        break;
                    case Types.QUANTIFIER:
                        cmd = new CreateQuantifier(this, mmq);
                        break;
                    case Types.TASKREPOSITORY:
                        cmd = new CreateTaskRepository(this, mmq);
                        break;
                    case Types.ROLESET:
                        cmd = new CreateRoleSet(this, mmq);
                        break;
                    case Types.ROLE:
                        cmd = new CreateRole(this, mmq);
                        break;
                    case Types.ROLE_CHARACTERISTIC:
                        cmd = new CreateCharacteristic(this, mmq);
                        break;
                    default:
                        System.err.println("ModelManager: Creation of unknown model element eventType '" + mmq.getElementType() + "' gets ignored!");
                        return;
                }

                break;
            case PARSE_ELEMENT:
                File f = FileSystemUtil.getFile(mmq);

                if ( f == null || !f.exists() )  {
                    return;
                }
                PlanElement element = getPlanElement(f.toString());

                if ( element == null || ignoreModifiedEvent(element)) {
                    return;
                }
                cmd = new ParsePlan(this, mmq);
                break;
            case DELETE_ELEMENT:
                //Folder has no ID
                if(!mmq.getElementType().equals(Types.FOLDER)) {
                    if (ignoreDeletedEvent(getPlanElement(mmq.getElementId()))) {
                        return;
                    }
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
                    case Types.VARIABLE:
                        cmd = new DeleteVariableFromPlan(this, mmq);
                        break;
                    case Types.QUANTIFIER:
                        cmd = new DeleteQuantifier(this, mmq);
                        break;
                    case Types.STATE:
                    case Types.SUCCESSSTATE:
                    case Types.FAILURESTATE:
                        cmd = new DeleteStateInPlan(this, mmq);
                        break;
                    case Types.ENTRYPOINT:
                        cmd = new DeleteEntryPoint(this, mmq);
                        break;
                    case Types.VARIABLEBINDING:
                        cmd = new DeleteVariableBinding(this, mmq);
                        break;
                    case Types.BENDPOINT:
                        cmd = new DeleteBendpoint(this, mmq);
                        break;
                    case Types.FOLDER:
                        cmd = new DeleteFolder(this, mmq);
                        break;
                    case Types.TRANSITION:
                        cmd = new DeleteTransitionInPlan(this, mmq);
                        break;
                    case Types.SYNCHRONISATION:
                        cmd = new DeleteSynchronisationFromPlan(this, mmq);
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
                    case Types.PLAN:
                    case Types.MASTERPLAN:
                    case Types.PLANTYPE:
                    case Types.BEHAVIOUR:
                        if(this.getPlanElement(mmq.getParentId()) instanceof State) {
                            cmd = new AddAbstractPlan(this, mmq);
                        } else {
                            // The plan needs to be wrapped in an AnnotatedPlan, therefore the Type
                            // should be Types.ANNOTATEDPLAN
                            mmq.setElementType(Types.ANNOTATEDPLAN);
                            cmd = new CreateAnnotatedPlan(this, mmq);
                        }
                        break;
                    case Types.TASK:
                        cmd =  new AddTaskToEntryPoint(this, mmq);
                        break;
                    case Types.STATE:
                    case Types.SUCCESSSTATE:
                    case Types.FAILURESTATE:
                        cmd = new CreateState(this, mmq);
                        break;
                    case Types.ENTRYPOINT:
                        cmd = new CreateEntryPoint(this, mmq);
                        break;
                    case Types.SYNCHRONISATION:
                        cmd = new CreateSynchronisation(this, mmq);
                        break;
                    case Types.INITSTATECONNECTION:
                        cmd = new ConnectEntryPointsWithState(this, mmq);
                        break;
                    case Types.TRANSITION:
                        cmd = new CreateTransition(this, mmq);
                        break;
                    case Types.BENDPOINT:
                        cmd = new CreateBendpoint(this, mmq);
                        break;
                    case Types.SYNCTRANSITION:
                        cmd = new ConnectSynchronizationWithTransition(this, mmq);
                        break;
                    case Types.PRECONDITION:
                    case Types.RUNTIMECONDITION:
                    case Types.POSTCONDITION:
                        cmd = new CreateCondition(this, mmq);
                        break;
                    case Types.VARIABLE:
                        cmd = new AddVariableToCondition(this, mmq);
                        break;
                    case Types.VARIABLEBINDING:
                        cmd = new CreateVariableBinding(this, mmq);
                        break;
                    default:
                        System.err.println("ModelManager: Unknown model modification query gets ignored!");
                        return;
                }
                break;
            case COPY_ELEMENT:
                switch (mmq.getElementType()) {
                    case Types.BEHAVIOUR:
                        cmd = new CopyBehaviour(this, mmq);
                        break;
                    case Types.MASTERPLAN:
                    case Types.PLAN:
                        cmd = new CopyPlan(this, mmq);
                        break;
                    case Types.PLANTYPE:
                        cmd = new CopyPlanType(this, mmq);
                        break;
                    default:
                        System.err.println("ModelManager: Unknown model modification query element gets ignored!");
                        return;
                }
                break;
            case REMOVE_ELEMENT:
                switch (mmq.getElementType()) {
                    case Types.ANNOTATEDPLAN:
                        cmd = new DeleteAnnotatedPlan(this, mmq);
                        break;
                    case Types.PRECONDITION:
                    case Types.RUNTIMECONDITION:
                    case Types.POSTCONDITION:
                        cmd = new DeleteCondition(this, mmq);
                        break;
                    case Types.VARIABLE:
                        cmd = new RemoveVariableFromCondition(this, mmq);
                        break;
                    case Types.BEHAVIOUR:
                    case Types.MASTERPLAN:
                    case Types.PLAN:
                    case Types.PLANTYPE:
                        cmd = new RemoveAbstractPlanFromState(this, mmq);
                        break;
                    default:
                        System.err.println("ModelManager: Unknown model modification query element gets ignored!");
                        return;
                }
                break;
            case REMOVE_ALL_ELEMENTS:
                cmd = new DeleteAllAnnotatedPlans(this, mmq);
                break;
            case RELOAD_ELEMENT:
                SerializablePlanElement serializablePlanElement = (SerializablePlanElement) getPlanElement(mmq.getElementId());
                mmq.absoluteDirectory = Paths.get(plansPath, serializablePlanElement.getRelativeDirectory()).toString();
                mmq.name = serializablePlanElement.getName();
                cmd = new ParsePlan(this, mmq);
                break;
            case CHANGE_ELEMENT:
                switch (mmq.getElementType()) {
                    // TODO probably does not work, if you change the comment of a sync transition
                    case Types.SYNCTRANSITION:
                        cmd = new ConnectSynchronizationWithTransition(this, mmq);
                        break;
                    case Types.ROLE_TASK_PROPERTY:
                        cmd = new ChangeTaskPriority(this, mmq);
                        break;
                    case Types.ROLE_CHARACTERISTIC:
                        cmd = new ChangeRoleCharacteristic(this, mmq);
                        break;
                    case Types.FOLDER:
                        cmd = new RenameFileTreeFolder(this, mmq);
                        break;
                    default:
                        cmd = new ChangeAttributeValue(this, mmq);
                        break;
                }
                break;
            case CHANGE_POSITION:
                cmd = new ChangePosition(this, mmq);
                break;
            case MOVE_FILE:
                if(!mmq.elementType.equals(Types.FOLDER)){
                    cmd = new MoveFile(this, mmq);
                } else {
                    cmd = new MoveFolder(this, mmq);
                }
                break;
            default:
                System.err.println("ModelManager: Unknown model modification query gets ignored!");
                return;
        }
        commandStack.storeAndExecute(cmd);
    }

    /**
     * This method is just for the SerializePlanElement Command. Nobody else should call it ... :P
     *
     * @param planElement
     * @param type
     */
    public void serialize(SerializablePlanElement planElement, String type) {
        switch (type) {
            case Types.TASK:
            case Types.TASKREPOSITORY:
                serializeToDisk(planElement, false);
                break;
            case Types.ROLE:
            case Types.ROLESET:
                serializeToDisk(planElement, false);
                break;
            case Types.PLANTYPE:
                serializeToDisk(planElement, false);
                break;
            case Types.PLAN:
            case Types.MASTERPLAN:
                // Why should we automatically store behaviours in a plan? No reason? Then delete it
//                for (State state : ((Plan) planElement).getStates()) {
//                    for (AbstractPlan abstractPlan: state.getAbstractPlans()) {
//                        if (abstractPlan instanceof Behaviour) {
//                            Behaviour behaviour = (Behaviour) abstractPlan;
//                            if (behaviour.getDirty()) {
//                                serialize(behaviour, Types.BEHAVIOUR);
//                            }
//                        }
//                    }
//                }
                serializeToDisk(planElement, false);
                break;
            case Types.BEHAVIOUR:
                serializeToDisk(planElement, false);
                break;
            default:
                System.err.println("ModelManager: Serialization of type " + type + " not implemented, yet!");
                break;
        }
    }

    /**
     * This is used to generate a JSON Schema for each of the classes that are derived from
     * the SerializablePlanElement class: TaskRepository, Behaviour, Plan, PlanType, RoleSet
     */
    private void generateJsonSchema() {
        JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator(objectMapper);
        JsonNode jsonSchema = jsonSchemaGenerator.generateJsonSchema(TaskRepository.class);

        try {
            String jsonSchemaAsString = objectMapper.writeValueAsString(jsonSchema);
            System.out.println(jsonSchemaAsString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Serializes an SerializablePlanElement to disk.
     *
     * @param planElement
     */
    private void serializeToDisk(SerializablePlanElement planElement, boolean movedOrCreated) {
        // for testing, delete if you see this uncommented
//        generateJsonSchema();

        String fileExtension = FileSystemUtil.getExtension(planElement);
        try {
            // Setting the values in the elementsSaved map at the beginning,
            // because otherwise listeners may react before values are updated
            if (!movedOrCreated) {
                //TODO: Refactoring

                // the counter is set to 2 because, saving an element always creates two filesystem modified events
                int counter = 2;
                // when a plan is saved it needs to be 4 however, because the stateUiElement is saved as well
                if (fileExtension.equals(Extensions.PLAN)) {
                    counter = 4;
                }
                elementsSavedMap.put(planElement.getId(), counter);
            }

            if (Extensions.PLAN.equals(fileExtension)) {
                File outdir = Paths.get(plansPath, planElement.getRelativeDirectory()).toFile();
                if (!outdir.exists()) {
                    outdir.mkdirs();
                }
                File outfile = Paths.get(outdir.toString(), planElement.getName() + "." + fileExtension).toFile();
                objectMapper.writeValue(outfile, (Plan) planElement);
                fireEvent(new ModelEvent(ModelEventType.ELEMENT_SERIALIZED, planElement, Types.PLAN));

                //Save the corresponding UiExtension
                UiExtension uiExtension = uiExtensionMap.get(planElement.getId());
                if (uiExtension != null) {
                    File visualisationFile = Paths.get(plansPath, planElement.getRelativeDirectory()
                            , planElement.getName() + "." + Extensions.PLAN_EXTENSION).toFile();
                    objectMapper.writeValue(visualisationFile, uiExtension);
                }
            } else if (Extensions.PLANTYPE.equals(fileExtension)) {
                File outdir = Paths.get(plansPath, planElement.getRelativeDirectory()).toFile();
                if (!outdir.exists()) {
                    outdir.mkdirs();
                }
                File outfile = Paths.get(outdir.toString(), planElement.getName() + "." + fileExtension).toFile();
                objectMapper.writeValue(outfile, (PlanType) planElement);
                fireEvent(new ModelEvent(ModelEventType.ELEMENT_SERIALIZED, planElement, Types.PLANTYPE));
            } else if (Extensions.BEHAVIOUR.equals(fileExtension)) {
                File outdir = Paths.get(plansPath, planElement.getRelativeDirectory()).toFile();
                if (!outdir.exists()) {
                    outdir.mkdirs();
                }
                File outfile = Paths.get(outdir.toString(), planElement.getName() + "." + fileExtension).toFile();
                objectMapper.writeValue(outfile, (Behaviour) planElement);
                fireEvent(new ModelEvent(ModelEventType.ELEMENT_SERIALIZED, planElement, Types.BEHAVIOUR));
            } else if (Extensions.TASKREPOSITORY.equals(fileExtension)) {
                File outdir = Paths.get(tasksPath, planElement.getRelativeDirectory()).toFile();
                if (!outdir.exists()) {
                    outdir.mkdirs();
                }
                File outfile = Paths.get(outdir.toString(), planElement.getName() + "." + fileExtension).toFile();
                objectMapper.writeValue(outfile, (TaskRepository) planElement);
                fireEvent(new ModelEvent(ModelEventType.ELEMENT_SERIALIZED, planElement, Types.TASKREPOSITORY));
            } else if (Extensions.ROLESET.equals(fileExtension)) {
                File outdir = Paths.get(rolesPath, planElement.getRelativeDirectory()).toFile();
                if (!outdir.exists()) {
                    outdir.mkdirs();
                }
                File outfile = Paths.get(outdir.toString(), planElement.getName() + "." + fileExtension).toFile();
                objectMapper.writeValue(outfile, (RoleSet) planElement);
                fireEvent(new ModelEvent(ModelEventType.ELEMENT_SERIALIZED, planElement, Types.ROLESET));
            } else {
                throw new RuntimeException("Modelmanager: Trying to serialize a file with unknown ending: " + fileExtension);
            }
            planElement.setDirty(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes AbstractPlan from disk.
     *
     * @param planElement
     */
    public void removeFromDisk(SerializablePlanElement planElement, int ignoreEventCounter) {
        String extension = FileSystemUtil.getExtension(planElement);
        if (ignoreEventCounter > 0 ) {
            elementDeletedMap.put(planElement.getId(), ignoreEventCounter);
        }
        File outfile;
        if (planElement instanceof TaskRepository) {
             outfile = Paths.get(tasksPath, planElement.getRelativeDirectory(), planElement.getName() + "." + extension).toFile();
        } else {
             outfile = Paths.get(plansPath, planElement.getRelativeDirectory(), planElement.getName() + "." + extension).toFile();
        }
        outfile.delete();

        // A plans uiExtension has to be deleted as well
        if(planElement instanceof Plan) {
            File extensionFile = Paths.get(plansPath, planElement.getRelativeDirectory()
                    , planElement.getName() + "." + Extensions.PLAN_EXTENSION).toFile();
            extensionFile.delete();
        }
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

    public String getAbsoluteDirectory(PlanElement element) {
        if (element instanceof Plan || element instanceof PlanType || element instanceof Behaviour) {
            return Paths.get(plansPath, ((SerializablePlanElement) element).getRelativeDirectory()).toString();
        }
        if (element instanceof TaskRepository) {
            return Paths.get(tasksPath, ((SerializablePlanElement) element).getRelativeDirectory()).toString();
        }
        if (element instanceof RoleSet) {
            return Paths.get(rolesPath, ((SerializablePlanElement) element).getRelativeDirectory()).toString();
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

    /**
     * Finding a {@link UiExtension} by the id of its {@link Plan}.
     * <p>
     * If no such {@link UiExtension} exits, a new one is created and stored.
     *
     * @param id the id of the {@link Plan} a {@link UiExtension} is required for
     * @return the {@link UiExtension} corresponding to the given id
     */
    public UiExtension getPlanUIExtensionPair(long id) {
        UiExtension pmvo = uiExtensionMap.get(id);
        if (pmvo == null) {
            Plan plan = planMap.get(id);
            if (plan == null) {
                throw new RuntimeException("ModelManager: Cannot create UiExtension, because no plan with id " + id + "exists");
            }
            pmvo = new UiExtension(plan);
            uiExtensionMap.put(id, pmvo);
        }
        return pmvo;
    }

    /**
     * Check, whether to ignore the modification of the given {@link PlanElement}
     *
     * @param newElement the {@link PlanElement} to check
     * @return true, if should be ignored
     */
    private boolean ignoreModifiedEvent(PlanElement newElement) {
        if (elementsSavedMap.containsKey(newElement.getId())) {
            int counter = elementsSavedMap.get(newElement.getId()) - 1;
            if (counter == 0) {
                // second event arrived, so remove the entry
                elementsSavedMap.remove(newElement.getId());
            } else {
                // first event arrived, so set the reduced counter
                elementsSavedMap.put(newElement.getId(), counter);
            }
            return true;
        }
        return false;
    }

    /**
     * Check, whether to ignore the deletion of the given {@link PlanElement}
     *
     * @param newElement the {@link PlanElement} to check
     * @return true, if should be ignored
     */
    private boolean ignoreDeletedEvent(PlanElement newElement) {
        if (elementDeletedMap.containsKey(newElement.getId())) {
            int counter = elementDeletedMap.get(newElement.getId()) - 1;
            if (counter == 0) {
                // second event arrived, so remove the entry
                elementDeletedMap.remove(newElement.getId());
            } else {
                // first event arrived, so set the reduced counter
                elementDeletedMap.put(newElement.getId(), counter);
            }
            return true;
        }
        return false;
    }


    public void fireEvent(ModelEvent event) {
        if (event != null) {
            for (IModelEventHandler eventHandler : eventHandlerList) {
                eventHandler.handleModelEvent(event);
            }
        }
    }

    /**
     * Check, whether adding the second element to the first element would create a loop in the model.
     *
     * @param addedTo  the {@link PlanElement} to which the other element should be added
     * @param added  the {@link PlanElement} which should be added
     * @return  true, if the added {@link PlanElement} is using the {@link PlanElement} it is added to
     *          (directly or indirectly), false otherwise
     */
    public boolean checkForInclusionLoop(PlanElement addedTo, PlanElement added){
        List<PlanElement> elementsToCheck = new ArrayList<>();
        Set<PlanElement> checkedElements = new HashSet<>();

        elementsToCheck.add(addedTo);
        while(!elementsToCheck.isEmpty()) {
            // Using the first element, therefore it's a Breadth-first search
            PlanElement current = elementsToCheck.get(0);
            elementsToCheck.remove(current);
            checkedElements.add(current);

            List<PlanElement> usages = getUsages(current.getId());
            if(usages.contains(added)) {
                // The element is used in the added element => there is a loop
                return true;
            }

            // Add all elements that have not been checked already to be checked
            usages.removeAll(checkedElements);
            elementsToCheck.addAll(usages);
        }
        return false;
    }

    public boolean hasTaskRepository() {
        return taskRepository != null;
    }

    public TaskRepository getTaskRepository() {
        return taskRepository;
    }

    public RoleSet getRoleSet() {
        return roleSet;
    }
}
