package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.generator.GeneratedSourcesManager;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.*;
import de.uni_kassel.vs.cn.planDesigner.configuration.Configuration;
import de.uni_kassel.vs.cn.planDesigner.configuration.ConfigurationEventHandler;
import de.uni_kassel.vs.cn.planDesigner.configuration.ConfigurationManager;
import de.uni_kassel.vs.cn.planDesigner.events.*;
import de.uni_kassel.vs.cn.planDesigner.filebrowser.FileSystemEventHandler;
import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IGuiStatusHandler;
import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IMoveFileHandler;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.FileSystemUtil;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelModificationQuery;
import de.uni_kassel.vs.cn.planDesigner.view.Types;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.*;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.behaviourTab.BehaviourTab;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.planTab.PlanTab;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.planTypeTab.PlanTypeTab;
import de.uni_kassel.vs.cn.planDesigner.view.model.*;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.taskRepoTab.TaskRepositoryTab;
import de.uni_kassel.vs.cn.planDesigner.view.filebrowser.FileTreeView;
import de.uni_kassel.vs.cn.planDesigner.view.repo.RepositoryTabPane;
import de.uni_kassel.vs.cn.planDesigner.view.repo.RepositoryViewModel;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;

import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.util.ArrayList;

/**
 * Central class that synchronizes model and view.
 * It is THE CONTROLLER regarding the Model-View-Controller pattern,
 * implemented in the Plan Designer.
 */
public final class Controller implements IModelEventHandler, IGuiStatusHandler, IGuiModificationHandler, IMoveFileHandler {

    // Common Objects
    private ConfigurationManager configurationManager;
    private FileSystemEventHandler fileSystemEventHandler;
    private ConfigurationEventHandler configEventHandler;

    // Model Objects
    private ModelManager modelManager;

    // View Objects
    private RepositoryViewModel repoViewModel;
    private TaskRepositoryViewModel taskViewModel;
    private MainWindowController mainWindowController;
    private ConfigurationWindowController configWindowController;
    private RepositoryTabPane repoTabPane;
    private EditorTabPane editorTabPane;

    // Code Generation Objects
    GeneratedSourcesManager generatedSourcesManager;

    public Controller() {
        configurationManager = ConfigurationManager.getInstance();
        configurationManager.setController(this);

        mainWindowController = MainWindowController.getInstance();
        mainWindowController.setGuiStatusHandler(this);
        mainWindowController.setGuiModificationHandler(this);
        mainWindowController.setMoveFileHandler(this);

        setupConfigGuiStuff();

        repoViewModel = new RepositoryViewModel();
        taskViewModel = new TaskRepositoryViewModel();

        fileSystemEventHandler = new FileSystemEventHandler(this);
        new Thread(fileSystemEventHandler).start(); // <- will be stopped by the PlanDesigner.isRunning() flag

        setupModelManager();
        setupGeneratedSourcesManager();
    }

    protected void setupGeneratedSourcesManager() {
        generatedSourcesManager = new GeneratedSourcesManager();
        generatedSourcesManager.setEditorExecutablePath(configurationManager.getEditorExecutablePath());
        Configuration activeConfiguration = configurationManager.getActiveConfiguration();
        if (activeConfiguration != null) {
            generatedSourcesManager.setGenSrcPath(activeConfiguration.getGenSrcPath());
        }
    }

    protected void setupModelManager() {
        modelManager = new ModelManager();
        modelManager.addListener(this);
        Configuration activeConfiguration = configurationManager.getActiveConfiguration();
        if (activeConfiguration != null) {
            modelManager.setPlansPath(activeConfiguration.getPlansPath());
            modelManager.setTasksPath(activeConfiguration.getTasksPath());
            modelManager.setRolesPath(activeConfiguration.getRolesPath());
        }

    }

    protected void setupConfigGuiStuff() {
        configWindowController = new ConfigurationWindowController();
        configEventHandler = new ConfigurationEventHandler(configWindowController, configurationManager);
        configWindowController.setHandler(configEventHandler);
        mainWindowController.setConfigWindowController(configWindowController);
    }

    // Handler Event Methods


    /**
     * Handles events fired by the model manager, when the model has changed.
     *
     * @param event Object that describes the purpose/context of the fired event.
     */
    public void handleModelEvent(ModelEvent event) {
        if (repoViewModel == null) {
            return;
        }

        PlanElement planElement;
        switch (event.getEventType()) {
            case ELEMENT_CREATED:
                planElement = event.getNewElement();
                ObservableList<Tab> tabs;
                switch (event.getElementType()) {
                    case Types.PLAN:
                        Plan plan = (Plan) planElement;
                        addTreeViewElement(plan, Types.PLAN);
                        ViewModelElement element = new ViewModelElement(plan.getId(), plan.getName(), Types.PLAN, plan.getRelativeDirectory());
                        repoViewModel.addPlan(element);
                        updatePlansInPlanTypeTabs(plan);
                        break;
                    case Types.MASTERPLAN:
                        plan = (Plan) planElement;
                        addTreeViewElement(plan, Types.MASTERPLAN);
                        element = new ViewModelElement(plan.getId(), plan.getName(), Types.MASTERPLAN, plan.getRelativeDirectory());
                        repoViewModel.addPlan(element);
                        break;
                    case Types.PLANTYPE:
                        addTreeViewElement((PlanType) planElement, Types.PLANTYPE);
                        repoViewModel.addPlanType(new ViewModelElement(planElement.getId(), planElement.getName(), Types.PLANTYPE, ((PlanType) planElement)
                                .getRelativeDirectory()));
                        break;
                    case Types.BEHAVIOUR:
                        addTreeViewElement((Behaviour) planElement, Types.BEHAVIOUR);
                        repoViewModel.addBehaviour(new ViewModelElement(planElement.getId(), planElement.getName(), Types.BEHAVIOUR, ((Behaviour)
                                planElement).getRelativeDirectory()));
                        break;
                    case Types.TASKREPOSITORY:
                        addTreeViewElement((TaskRepository) planElement, Types.TASKREPOSITORY);
                        taskViewModel.clearTasks();
                        for (Task task : ((TaskRepository) planElement).getTasks()) {
                            ViewModelElement taskElement = new ViewModelElement(task.getId(), task.getName(), Types.TASK);
                            taskElement.setParentId(planElement.getId());
                            repoViewModel.removeTask(task.getId());
                            repoViewModel.addTask(taskElement);
                            taskViewModel.addTask(taskElement);
                        }
                        break;
                    case Types.TASK:
                        Task task = (Task) planElement;
                        element = new ViewModelElement(planElement.getId(), planElement.getName(), Types.TASK);
                        element.setParentId(task.getTaskRepository().getId());
                        repoViewModel.addTask(element);
                        taskViewModel.addTask(element);
                        break;
                    default:
                        System.err.println("Controller: Creation of unknown type " + event.getElementType() + " gets ignored!");
                        break;
                }
                break;
            case ELEMENT_DELETED:
                planElement = event.getOldElement();
                switch (event.getElementType()) {
                    case Types.PLAN:
                        removeTreeViewElement((AbstractPlan) planElement, Types.PLAN);
                        repoViewModel.removePlan(new ViewModelElement(planElement.getId(), planElement.getName(), Types.PLAN));
                        break;
                    case Types.MASTERPLAN:
                        removeTreeViewElement((AbstractPlan) planElement, Types.MASTERPLAN);
                        repoViewModel.removePlan(new ViewModelElement(planElement.getId(), planElement.getName(), Types.PLAN));
                        break;
                    case Types.PLANTYPE:
                        removeTreeViewElement((AbstractPlan) planElement, Types.PLANTYPE);
                        repoViewModel.removePlanType(new ViewModelElement(planElement.getId(), planElement.getName(), Types.PLANTYPE));
                        break;
                    case Types.BEHAVIOUR:
                        removeTreeViewElement((AbstractPlan) planElement, Types.BEHAVIOUR);
                        repoViewModel.removeBehaviour(new ViewModelElement(planElement.getId(), planElement.getName(), Types.BEHAVIOUR));
                        break;
                    case Types.TASKREPOSITORY:
                        repoViewModel.clearTasks();
                        taskViewModel.clearTasks();
                        break;
                    default:
                        System.err.println("Controller: Deletion of unknown type " + event.getElementType() + " gets ignored!");
                        break;
                }
                break;
            case ELEMENT_PARSED:
                planElement = event.getNewElement();
                switch (event.getElementType()) {
                    case Types.PLAN:
                        Plan plan = (Plan) planElement;
                        addTreeViewElement(plan, Types.PLAN);
                        repoViewModel.addPlan(new ViewModelElement(plan.getId(), plan.getName(), Types.PLAN, plan.getRelativeDirectory()));
                        break;
                    case Types.MASTERPLAN:
                        plan = (Plan) planElement;
                        addTreeViewElement(plan, Types.MASTERPLAN);
                        repoViewModel.addPlan(new ViewModelElement(plan.getId(), plan.getName(), Types.MASTERPLAN, plan.getRelativeDirectory()));
                        break;
                    case Types.PLANTYPE:
                        addTreeViewElement((PlanType) planElement, Types.PLANTYPE);
                        repoViewModel.addPlanType(new ViewModelElement(planElement.getId(), planElement.getName(), Types.PLANTYPE, ((PlanType) planElement)
                                .getRelativeDirectory()));
                        break;
                    case Types.BEHAVIOUR:
                        addTreeViewElement((Behaviour) planElement, Types.BEHAVIOUR);
                        repoViewModel.addBehaviour(new ViewModelElement(planElement.getId(), planElement.getName(), Types.BEHAVIOUR, ((Behaviour)
                                planElement).getRelativeDirectory()));
                        break;
                    case Types.TASKREPOSITORY:
                        addTreeViewElement((TaskRepository) planElement, Types.TASKREPOSITORY);
                        for (Task task : ((TaskRepository) planElement).getTasks()) {
                            ViewModelElement element = new ViewModelElement(task.getId(), task.getName(), Types.TASK);
                            element.setParentId(task.getTaskRepository().getId());
                            repoViewModel.addTask(element);
                        }
                        break;
                    case Types.TASK:
                        Task task = (Task) planElement;
                        ViewModelElement element = new ViewModelElement(planElement.getId(), planElement.getName(), Types.TASK);
                        element.setParentId(task.getTaskRepository().getId());
                        repoViewModel.addTask(element);
                        taskViewModel.addTask(element);
                        break;
                    default:
                        System.err.println("Controller: Parsing of unknown type " + event.getElementType() + " gets ignored!");
                        break;
                }
                break;
            case ELEMENT_SERIALIZED:
                tabs = editorTabPane.getTabs();
                switch (event.getElementType()) {
                    case Types.TASKREPOSITORY:
                        taskViewModel.setDirty(false);
                        break;
                    case Types.PLANTYPE:
                        for (Tab tab : tabs) {
                            if (!(tab instanceof PlanTypeTab)) {
                                continue;
                            }
                            PlanTypeTab planTypeTab = (PlanTypeTab) tab;
                            if (planTypeTab.getPresentedViewModelElement().getId() == event.getNewElement().getId()) {
                                planTypeTab.setDirty(false);
                                break;
                            }
                        }
                        break;
                    case Types.BEHAVIOUR:
                        for (Tab tab : tabs) {
                            if (!(tab instanceof BehaviourTab)) {
                                continue;
                            }
                            BehaviourTab behaviourTab = (BehaviourTab) tab;
                            if (behaviourTab.getPresentedViewModelElement().getId() == event.getNewElement().getId()) {
                                behaviourTab.setDirty(false);
                                break;
                            }
                        }
                        break;
                    case Types.PLAN:
                        for (Tab tab : tabs) {
                            if (!(tab instanceof PlanTab)) {
                                continue;
                            }
                            PlanTab planTab = (PlanTab) tab;
                            if (planTab.getPresentedViewModelElement().getId() == event.getNewElement().getId()) {
                                planTab.setDirty(false);
                                break;
                            }
                        }
                        break;
                    default:
                        System.err.println("Controller: Serialization of unknown type " + event.getElementType() + " gets ignored!");
                        break;
                }
                break;
            case ELEMENT_ATTRIBUTE_CHANGED:
                planElement = event.getNewElement();
                /**
                 * Special treatment for plan name since it affects other plan elements
                 */
                if (event.getChangedAttribute().equals("name")) {
                    handleChangedName(event, planElement);
                }
                break;
            default:
                throw new RuntimeException("Controller:Unknown model event captured!");
        }
    }

    private void handleChangedName(ModelEvent event, PlanElement planElement) {
        ObservableList<Tab> tabs = editorTabPane.getTabs();
        for (Tab tab : tabs) {
            if (tab instanceof AbstractPlanTab) {
                AbstractPlanTab abstractPlanTab = (AbstractPlanTab) tab;
                if (abstractPlanTab.getPresentedViewModelElement().getId() == planElement.getId()) {
                    abstractPlanTab.updateText(planElement.getName());
                    break;
                }
            }
            if (tab instanceof TaskRepositoryTab) {
                TaskRepositoryTab taskRepositoryTab = (TaskRepositoryTab) tab;
                if (planElement instanceof Task) {
                    taskRepositoryTab.updateText(((Task) planElement).getTaskRepository().getName());
                } else {
                    taskRepositoryTab.updateText(planElement.getName());
                }
                break;
            }
        }

        ViewModelElement viewModelElement = null;
        if (planElement instanceof Plan) {
            repoViewModel.removePlan(planElement.getId());
            if (((Plan) planElement).getMasterPlan()) {
                viewModelElement = new ViewModelElement(planElement.getId(), planElement.getName(), Types.MASTERPLAN, ((Plan)
                        planElement).getRelativeDirectory());
                repoViewModel.addPlan(viewModelElement);
            } else {
                viewModelElement = new ViewModelElement(planElement.getId(), planElement.getName(), Types.PLAN, ((Plan)
                        planElement).getRelativeDirectory());
                repoViewModel.addPlan(viewModelElement);
            }
            replaceFileTreeItem(viewModelElement);
        } else if (planElement instanceof Behaviour) {
            viewModelElement = new ViewModelElement(planElement.getId(), planElement.getName(), Types.BEHAVIOUR, ((Behaviour)
                    planElement).getRelativeDirectory());
            repoViewModel.removeBehaviour(planElement.getId());
            repoViewModel.addBehaviour(viewModelElement);
            replaceFileTreeItem(viewModelElement);
        } else if (planElement instanceof PlanType) {
            viewModelElement = new ViewModelElement(planElement.getId(), planElement.getName(), Types.PLANTYPE, ((PlanType)
                    planElement).getRelativeDirectory());
            repoViewModel.removePlanType(planElement.getId());
            repoViewModel.addPlanType(viewModelElement);
            replaceFileTreeItem(viewModelElement);
        } else if (planElement instanceof TaskRepository) {
            viewModelElement = new ViewModelElement(planElement.getId(), planElement.getName(), Types.TASKREPOSITORY, ((TaskRepository)
                    planElement).getRelativeDirectory());
            replaceFileTreeItem(viewModelElement);
        } else if (planElement instanceof Task) {
            viewModelElement = new ViewModelElement(planElement.getId(), planElement.getName(), Types.TASK, ((Task)
                    planElement).getTaskRepository().getRelativeDirectory());
            repoViewModel.removeTask(planElement.getId());
            repoViewModel.addTask(viewModelElement);
        }

        ArrayList<PlanElement> usages = modelManager.getUsages(event.getNewElement().getId());
        for (PlanElement element : usages) {
            if (element instanceof PlanType) {
                for (AnnotatedPlan annotatedPlan : ((PlanType) element).getPlans()) {
                    if (annotatedPlan.getPlan().getId() == planElement.getId()) {
                        annotatedPlan.setPlan((Plan) planElement);
                        break;
                    }
                }
            }
            fireSavePlanElementQuery(element);
        }
        fireSavePlanElementQuery(planElement);
    }

    private void replaceFileTreeItem(ViewModelElement viewModelElement) {
        mainWindowController.getFileTreeView().removeViewModelElement(viewModelElement);
        mainWindowController.getFileTreeView().addViewModelElement(viewModelElement);
    }

    private void fireSavePlanElementQuery(PlanElement planElement) {
        if (planElement instanceof Plan) {
            ModelModificationQuery query = new ModelModificationQuery(ModelQueryType.SAVE_ELEMENT, Types.PLAN,
                    planElement.getName());
            query.setElementId(planElement.getId());
            modelManager.handleModelModificationQuery(query);
        } else if (planElement instanceof PlanType) {
            ModelModificationQuery query = new ModelModificationQuery(ModelQueryType.SAVE_ELEMENT, Types.PLANTYPE,
                    planElement.getName());
            query.setElementId(planElement.getId());
            modelManager.handleModelModificationQuery(query);
        } else if (planElement instanceof Behaviour) {
            ModelModificationQuery query = new ModelModificationQuery(ModelQueryType.SAVE_ELEMENT, Types.BEHAVIOUR,
                    planElement.getName());
            query.setElementId(planElement.getId());
            modelManager.handleModelModificationQuery(query);
        } else if (planElement instanceof TaskRepository) {
            ModelModificationQuery query = new ModelModificationQuery(ModelQueryType.SAVE_ELEMENT, Types.TASKREPOSITORY,
                    planElement.getName());
            query.setElementId(planElement.getId());
            modelManager.handleModelModificationQuery(query);
        } else if (planElement instanceof Task) {
            ModelModificationQuery query = new ModelModificationQuery(ModelQueryType.SAVE_ELEMENT, Types.TASK,
                    planElement.getName());
            query.setElementId(((Task) planElement).getTaskRepository().getId());
            modelManager.handleModelModificationQuery(query);
        } else {
            throw new RuntimeException("Controller: trying to serialize a PlanElement of unknown type! PlanElement is: " + planElement.toString());
        }
    }

    private void addTreeViewElement(SerializablePlanElement planElement, String type) {
        FileTreeView fileTreeView = mainWindowController.getFileTreeView();
        if (fileTreeView != null) {
            fileTreeView.addViewModelElement(new ViewModelElement(planElement.getId(),
                    planElement.getName(), type, planElement.getRelativeDirectory()));
        }
    }

    private void removeTreeViewElement(SerializablePlanElement planElement, String type) {
        FileTreeView fileTreeView = mainWindowController.getFileTreeView();
        if (fileTreeView != null) {
            fileTreeView.removeViewModelElement(new ViewModelElement(planElement.getId(),
                    planElement.getName(), type, planElement.getRelativeDirectory()));
        }
    }

    /**
     * Called by the 'ShowUsage'-ContextMenu of RepositoryHBoxes
     *
     * @param viewModelElement
     * @return
     */
    @Override
    public ArrayList<ViewModelElement> getUsages(ViewModelElement viewModelElement) {
        ArrayList<ViewModelElement> usageViewModelElements = new ArrayList<>();
        ArrayList<PlanElement> usagePlanElements = this.modelManager.getUsages(viewModelElement.getId());
        if (usagePlanElements != null) {
            for (PlanElement planElement : usagePlanElements) {
                usageViewModelElements.add(new ViewModelElement(planElement.getId(), planElement.getName(), FileSystemUtil.getTypeString(planElement)));
            }
        }
        return usageViewModelElements;
    }

    /**
     * Called by the configuration manager, if the active configuration has changed.
     */
    public void handleConfigurationChanged() {
        Configuration activeConfiguration = configurationManager.getActiveConfiguration();
        mainWindowController.setUpFileTreeView(activeConfiguration.getPlansPath(), activeConfiguration.getRolesPath(), activeConfiguration.getTasksPath());
    }

    /**
     * Called by the main window controlled at the end of its initialized method.
     */
    @Override
    public void handleGuiInitialized() {
        mainWindowController.enableMenuBar();
        Configuration activeConfiguration = configurationManager.getActiveConfiguration();
        if (activeConfiguration != null) {
            mainWindowController.setUpFileTreeView(activeConfiguration.getPlansPath(), activeConfiguration.getRolesPath(), activeConfiguration.getTasksPath());
            new Thread(fileSystemEventHandler).start(); // <- will be stopped by the PlanDesigner.isRunning() flag
            modelManager.loadModelFromDisk();
        }
        repoTabPane = mainWindowController.getRepositoryTabPane();
        repoViewModel.setRepositoryTabPane(repoTabPane);
        repoViewModel.initGuiContent();

        editorTabPane = mainWindowController.getEditorTabPane();
        editorTabPane.setGuiModificationHandler(this);
    }

    /**
     * Called by the context menu for creating plans, behaviours etc.
     *
     * @param event
     */
    @Override
    public void handle(GuiModificationEvent event) {
        ModelModificationQuery mmq;
        switch (event.getEventType()) {
            case CREATE_ELEMENT:
                mmq = new ModelModificationQuery(ModelQueryType.CREATE_ELEMENT, event.getAbsoluteDirectory(), event.getElementType(), event.getName());
                mmq.setParentId(event.getParentId());
                break;
            case DELETE_ELEMENT:
                mmq = new ModelModificationQuery(ModelQueryType.DELETE_ELEMENT, event.getAbsoluteDirectory(), event.getElementType(), event.getName());
                mmq.setElementId(event.getElementId());
                break;
            case SAVE_ELEMENT:
                mmq = new ModelModificationQuery(ModelQueryType.SAVE_ELEMENT, event.getAbsoluteDirectory(), event.getElementType(), event.getName());
                mmq.setElementId(event.getElementId());
                break;
            case ADD_ELEMENT:
                mmq = new ModelModificationQuery(ModelQueryType.ADD_ELEMENT);
                mmq.setElementId(event.getElementId());
                mmq.setElementType(event.getElementType());
                mmq.setParentId(event.getParentId());
                break;
            case REMOVE_ELEMENT:
                mmq = new ModelModificationQuery(ModelQueryType.REMOVE_ELEMENT);
                mmq.setElementId(event.getElementId());
                mmq.setElementType(event.getElementType());
                mmq.setParentId(event.getParentId());
                break;
            case REMOVE_ALL_ELEMENTS:
                mmq = new ModelModificationQuery(ModelQueryType.REMOVE_ALL_ELEMENTS);
                mmq.setElementType(event.getElementType());
                mmq.setParentId(event.getParentId());
                break;
            case RELOAD_ELEMENT:
                mmq = new ModelModificationQuery(ModelQueryType.RELOAD_ELEMENT);
                mmq.setElementId(event.getElementId());
                mmq.setElementType(event.getElementType());
                break;
            case CHANGE_ELEMENT:
                mmq = new ModelModificationQuery(ModelQueryType.CHANGE_ELEMENT);
                mmq.setElementType(event.getElementType());
                mmq.setParentId(event.getParentId());
                mmq.setElementId(event.getElementId());
                if (event instanceof GuiChangeAttributeEvent) {
                    GuiChangeAttributeEvent guiChangeAttributeEvent = (GuiChangeAttributeEvent) event;
                    mmq.setAttributeName(guiChangeAttributeEvent.getAttributeName());
                    mmq.setAttributeType(guiChangeAttributeEvent.getAttributeType());
                    mmq.setNewValue(guiChangeAttributeEvent.getNewValue());
                }
                break;
            default:
                mmq = null;
        }
        this.modelManager.handleModelModificationQuery(mmq);
    }

    /**
     * Called when something relevant in the filesystem has changed.
     *
     * @param event
     * @param path
     */
    public void handleFileSystemEvent(WatchEvent event, Path path) {
        WatchEvent.Kind kind = event.kind();
        ModelModificationQuery mmq;
        if (kind.equals((StandardWatchEventKinds.ENTRY_MODIFY))) {
            mmq = new ModelModificationQuery(ModelQueryType.PARSE_ELEMENT, path.toString());
        } else if (kind.equals(StandardWatchEventKinds.ENTRY_DELETE)) {
            mmq = new ModelModificationQuery(ModelQueryType.DELETE_ELEMENT, path.toString());
            mainWindowController.getFileTreeView().updateDirectories(path);
        } else if (kind.equals((StandardWatchEventKinds.ENTRY_CREATE))) {
            if (path.toFile().isDirectory()) {
                mainWindowController.getFileTreeView().updateDirectories(path);
            } else {
                System.out.println("Controller: ENTRY_CREATE filesystem event is ignored!");
            }
            return;
        } else {
            System.err.println("Controller: Unknown filesystem event elementType received that gets ignored!");
            return;
        }
        modelManager.handleModelModificationQuery(mmq);
    }

    /**
     * Called by the FileTreeView when moving files
     */
    @Override
    public void moveFile(long id, Path originalPath, Path newPath) {
        modelManager.moveFile(id, originalPath, newPath);
    }

    @Override
    public void handleTabOpenedEvent(TaskRepositoryTab taskRepositoryTab) {
        taskViewModel.clearTasks();
        TaskRepository taskRepo = modelManager.getTaskRepository();
        if (taskRepo != null) {
            for (Task task : taskRepo.getTasks()) {
                ViewModelElement element = new ViewModelElement(task.getId(), task.getName(), Types.TASK);
                element.setParentId(taskRepo.getId());
                taskViewModel.addTask(element);
            }
        }
        taskViewModel.setTaskRepositoryTab(taskRepositoryTab);
    }

    @Override
    public void handleTabOpenedEvent(PlanTab planTab) {
        System.err.println("Controller: Opening Plans not implemented, yet!");
    }

    @Override
    public void handleTabOpenedEvent(BehaviourTab behaviourTab) {
        System.err.println("Controller: Opening BehaviourTabs not implemented, yet!");
    }

    @Override
    public void handleTabOpenedEvent(PlanTypeTab planTypeTab) {
        PlanElement planElement = modelManager.getPlanElement(planTypeTab.getPresentedViewModelElement().getId());
        if (planElement == null || !(planElement instanceof PlanType)) {
            System.err.println("Controller: Opening PlanTypeTab for unknown Id or not presented element is not a PlanType!");
            return;
        }

        PlanType planType = (PlanType) planElement;
        PlanTypeViewModel planTypeViewModel = new PlanTypeViewModel(planType.getId(), planType.getName(),
                Types.PLANTYPE);
        planTypeViewModel.setRelativeDirectory(planType.getRelativeDirectory());
        planTypeViewModel.setComment(planType.getComment());


        for (AnnotatedPlan annotatedPlan : planType.getPlans()) {
            Plan plan = annotatedPlan.getPlan();
            if (plan.getMasterPlan()) {
                planTypeViewModel.addPlanToPlansInPlanType(new PlanViewModelElement(plan.getId(), plan.getName(), Types.MASTERPLAN, annotatedPlan
                        .isActivated()));
            } else {
                planTypeViewModel.addPlanToPlansInPlanType(new PlanViewModelElement(plan.getId(), plan.getName(), Types.PLAN, annotatedPlan
                        .isActivated()));
            }
        }

        ArrayList<Plan> plans = modelManager.getPlans();
        for (Plan plan : plans) {
            if (plan.getMasterPlan()) {
                planTypeViewModel.addPlanToAllPlans(new ViewModelElement(plan.getId(), plan.getName(), Types.MASTERPLAN));
            } else {
                planTypeViewModel.addPlanToAllPlans(new ViewModelElement(plan.getId(), plan.getName(), Types.PLAN));
            }
        }

        planTypeTab.setPlanTypeViewModel(planTypeViewModel);
    }

    @Override
    public ViewModelElement getViewModelElement(long id) {
        PlanElement planElement = modelManager.getPlanElement(id);
        if (planElement != null) {
            if (planElement instanceof Task) {
                ViewModelElement task = new ViewModelElement(planElement.getId(), planElement.getName(), Types.TASK);
                task.setParentId(modelManager.getTaskRepository().getId());
                return task;
            } else if (planElement instanceof TaskRepository) {
                PlanElementViewModel taskRepo = new PlanElementViewModel(planElement.getId(), planElement.getName(), Types.TASKREPOSITORY);
                taskRepo.setComment(planElement.getComment());
                taskRepo.setRelativeDirectory(((TaskRepository) planElement).getRelativeDirectory());
                return taskRepo;
            } else if (planElement instanceof Plan) {
                PlanViewModel element = null;
                Plan plan = (Plan) planElement;
                if (plan.getMasterPlan()) {
                    element = new PlanViewModel(plan.getId(), plan.getName(), Types.MASTERPLAN);
                } else {
                    element = new PlanViewModel(plan.getId(), plan.getName(), Types.PLAN);
                }
                element.setComment(plan.getComment());
                element.setRelativeDirectory(plan.getRelativeDirectory());
                element.setUtilityThreshold(plan.getUtilityThreshold());
                element.setMasterPlan(plan.getMasterPlan());
                return element;
            } else if (planElement instanceof TaskRepository) {
                PlanElementViewModel element = new PlanViewModel(planElement.getId(), planElement.getName(), Types.TASKREPOSITORY);
                TaskRepository taskRepository = (TaskRepository) planElement;
                element.setComment(taskRepository.getComment());
                element.setRelativeDirectory(taskRepository.getRelativeDirectory());
                return element;
            } else {
                System.err.println("Controller: getViewModelElement for type " + planElement.getClass().toString() + " not implemented!");
            }
        }
        return null;
    }

    @Override
    public BehaviourViewModel getBehaviourViewModel(long id) {
        PlanElement planElement = modelManager.getPlanElement(id);
        if (planElement != null) {
            Behaviour behaviour = (Behaviour) planElement;
            BehaviourViewModel behaviourViewModel = new BehaviourViewModel(behaviour.getId(), behaviour.getName(), Types.BEHAVIOUR);
            behaviourViewModel.setComment(behaviour.getComment());
            behaviourViewModel.setRelativeDirectory(behaviour.getRelativeDirectory());
            behaviourViewModel.setFrequency(behaviour.getFrequency());
            behaviourViewModel.setDeferring(behaviour.getDeferring());
            for (Variable variable : behaviour.getVariables()) {
                VariableViewModel variableViewModel = new VariableViewModel(variable.getId(), variable.getName(), Types.VARIABLE);
                variableViewModel.setVariableType(variable.getType());
                behaviourViewModel.getVariables().add(variableViewModel);
            }
            behaviourViewModel.setPreCondition(getConditionViewModel(behaviour.getPreCondition(), Types.PRECONDITION, behaviour.getId()));
            behaviourViewModel.setRuntimeCondition(getConditionViewModel(behaviour.getRuntimeCondition(), Types.RUNTIMECONDITION, behaviour.getId()));
            behaviourViewModel.setPostCondition(getConditionViewModel(behaviour.getPostCondition(), Types.POSTCONDITION, behaviour.getId()));
            return behaviourViewModel;
        } else {
            return null;
        }
    }

    public ConditionViewModel getConditionViewModel(Condition condition, String type, long parentId) {
        if (condition == null) {
            return null;
        }
        ConditionViewModel conditionViewModel = new ConditionViewModel(condition.getId(), condition.getName(), type);
        conditionViewModel.setConditionString(condition.getConditionString());
        conditionViewModel.setEnabled(condition.getEnabled());
        conditionViewModel.setPluginName(condition.getPluginName());
        conditionViewModel.setComment(condition.getComment());
        for (Variable var : condition.getVariables()) {
            conditionViewModel.getVars().add(new VariableViewModel(var.getId(), var.getName(), var.getType()));
        }
        for (Quantifier quantifier : condition.getQuantifiers()) {
            // TODO: Quantifier is not very clean or fully implemented, yet.
            conditionViewModel.getQuantifier().add(new QuantifierViewModel(quantifier.getId(), quantifier.getName(), Types.QUANTIFIER));
        }
        conditionViewModel.setParentId(parentId);
        return conditionViewModel;
    }

    public void updatePlansInPlanTypeTabs(Plan plan) {
        ObservableList<Tab> tabs = editorTabPane.getTabs();
        for (Tab tab : tabs) {
            if (tab instanceof PlanTypeTab) {
                PlanTypeTab planTypeTab = (PlanTypeTab) tab;
                if (plan.getMasterPlan()) {
                    planTypeTab.getController().getPlanTypeViewModel().addPlanToAllPlans(new ViewModelElement(plan.getId(), plan.getName(), Types.MASTERPLAN,
                            plan.getRelativeDirectory()));
                } else {
                    planTypeTab.getController().getPlanTypeViewModel().addPlanToAllPlans(new ViewModelElement(plan.getId(), plan.getName(), Types.PLAN,
                            plan.getRelativeDirectory()));
                }
            }
        }
    }

    @Override
    public void handleCloseTab(long planElementId) {
        for (Tab tab : editorTabPane.getTabs()) {
            if (tab instanceof AbstractPlanTab) {
                AbstractPlanTab abstractPlanTab = (AbstractPlanTab) tab;
                if (abstractPlanTab.getPresentedViewModelElement().getId() == planElementId) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            editorTabPane.getTabs().remove(tab);
                        }
                    });
                    break;
                }
            }
            if (tab instanceof TaskRepositoryTab) {
                TaskRepositoryTab taskRepositoryTab = (TaskRepositoryTab) tab;
                if (taskRepositoryTab.getPresentedViewModelElement().getId() == planElementId) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            editorTabPane.getTabs().remove(tab);
                        }
                    });
                    break;
                }
            }
        }
    }

    @Override
    public void disableUndo(boolean disable) {
        mainWindowController.disableUndo(disable);
    }

    @Override
    public void disableRedo(boolean disable) {
        mainWindowController.disableRedo(disable);
    }

    @Override
    public void handleUndo() {
        modelManager.undo();
    }

    @Override
    public void handleRedo() {
        modelManager.redo();
    }
}
