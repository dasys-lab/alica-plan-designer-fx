package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.generator.GeneratedSourcesManager;
import de.uni_kassel.vs.cn.generator.plugin.PluginManager;
import de.uni_kassel.vs.cn.planDesigner.ViewModelFactory.ViewModelFactory;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.*;
import de.uni_kassel.vs.cn.planDesigner.configuration.Configuration;
import de.uni_kassel.vs.cn.planDesigner.configuration.ConfigurationEventHandler;
import de.uni_kassel.vs.cn.planDesigner.configuration.ConfigurationManager;
import de.uni_kassel.vs.cn.planDesigner.events.*;
import de.uni_kassel.vs.cn.planDesigner.filebrowser.FileSystemEventHandler;
import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IGuiStatusHandler;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.FileSystemUtil;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelModificationQuery;
import de.uni_kassel.vs.cn.planDesigner.plugin.PluginEventHandler;
import de.uni_kassel.vs.cn.planDesigner.view.Types;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.AbstractPlanTab;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.EditorTabPane;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.behaviourTab.BehaviourTab;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.planTab.PlanTab;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.planTypeTab.PlanTypeTab;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.taskRepoTab.TaskRepositoryTab;
import de.uni_kassel.vs.cn.planDesigner.view.filebrowser.FileTreeView;
import de.uni_kassel.vs.cn.planDesigner.view.model.*;
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
public final class Controller implements IModelEventHandler, IGuiStatusHandler, IGuiModificationHandler {

    // Common Objects
    private ConfigurationManager configurationManager;
    private FileSystemEventHandler fileSystemEventHandler;
    private ConfigurationEventHandler configEventHandler;
    private PluginManager pluginManager;
    private PluginEventHandler pluginEventHandler;

    // Model Objects
    private ModelManager modelManager;

    // View Objects
    private RepositoryViewModel repoViewModel;
    private TaskRepositoryViewModel taskRepositoryViewModel;
    private MainWindowController mainWindowController;
    private ConfigurationWindowController configWindowController;
    private RepositoryTabPane repoTabPane;
    private EditorTabPane editorTabPane;
    private ViewModelFactory viewModelFactory;

    // Code Generation Objects
    private GeneratedSourcesManager generatedSourcesManager;

    public Controller() {
        configurationManager = ConfigurationManager.getInstance();
        configurationManager.setController(this);

        pluginManager = PluginManager.getInstance();
        //pluginManager.updateAvailablePlugins(configurationManager.getActiveConfiguration().getPluginsPath());

        mainWindowController = MainWindowController.getInstance();
        mainWindowController.setGuiStatusHandler(this);
        mainWindowController.setGuiModificationHandler(this);

        setupConfigGuiStuff();

        fileSystemEventHandler = new FileSystemEventHandler(this);
        new Thread(fileSystemEventHandler).start(); // <- will be stopped by the PlanDesigner.isRunning() flag

        setupModelManager();
        setupGeneratedSourcesManager();

        viewModelFactory = new ViewModelFactory(modelManager);

        repoViewModel = viewModelFactory.createRepositoryViewModel();
        taskRepositoryViewModel = viewModelFactory.createTaskRepositoryViewModel();
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

        pluginEventHandler = new PluginEventHandler(configWindowController, pluginManager);
        configWindowController.setPluginEventHandler(pluginEventHandler);

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
                        updatePlansInPlanTypeTabs(plan);
                        repoViewModel.addPlan(viewModelFactory.createViewModelElement(planElement, Types.PLAN));
                        break;
                    case Types.MASTERPLAN:
                        Plan masterPlan = (Plan) planElement;
                        addTreeViewElement(masterPlan, Types.MASTERPLAN);
                        updatePlansInPlanTypeTabs(masterPlan);
                        repoViewModel.addPlan(viewModelFactory.createViewModelElement(planElement, Types.MASTERPLAN));
                        break;
                    case Types.ANNOTATEDPLAN:
                        updateAnnotatedPlansInPlanTypeTabs(event.getParentId(), (AnnotatedPlan) planElement, true);
                        break;
                    case Types.PLANTYPE:
                        addTreeViewElement((PlanType) planElement, Types.PLANTYPE);
                        repoViewModel.addPlanType(viewModelFactory.createViewModelElement(planElement, Types.PLANTYPE));
                        break;
                    case Types.BEHAVIOUR:
                        addTreeViewElement((Behaviour) planElement, Types.BEHAVIOUR);
                        repoViewModel.addBehaviour(viewModelFactory.createViewModelElement(planElement, Types.BEHAVIOUR));
                        break;
                    case Types.TASKREPOSITORY:
                        addTreeViewElement((TaskRepository) planElement, Types.TASKREPOSITORY);
                        taskRepositoryViewModel.clearTasks();
                        for (Task task : ((TaskRepository) planElement).getTasks()) {
                            ViewModelElement taskElement = viewModelFactory.createViewModelElement(planElement, Types.TASK, planElement.getId());
                            repoViewModel.removeTask(task.getId());
                            repoViewModel.addTask(taskElement);
                            taskRepositoryViewModel.addTask(taskElement);
                        }
                        break;
                    case Types.TASK:
                        ViewModelElement element = viewModelFactory.createViewModelElement(planElement, Types.TASK, planElement.getId());
                        repoViewModel.addTask(element);
                        taskRepositoryViewModel.addTask(element);
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
                        removeTreeViewElement((SerializablePlanElement) planElement, Types.PLAN);
                        repoViewModel.removePlan(planElement.getId());
                        break;
                    case Types.MASTERPLAN:
                        removeTreeViewElement((SerializablePlanElement) planElement, Types.MASTERPLAN);
                        repoViewModel.removePlan(planElement.getId());
                        break;
                    case Types.ANNOTATEDPLAN:
                        updateAnnotatedPlansInPlanTypeTabs(event.getParentId(), (AnnotatedPlan) planElement, false);
                        break;
                    case Types.PLANTYPE:
                        removeTreeViewElement((SerializablePlanElement) planElement, Types.PLANTYPE);
                        repoViewModel.removePlanType(planElement.getId());
                        break;
                    case Types.BEHAVIOUR:
                        removeTreeViewElement((SerializablePlanElement) planElement, Types.BEHAVIOUR);
                        repoViewModel.removeBehaviour(planElement.getId());
                        break;
                    case Types.TASKREPOSITORY:
                        repoViewModel.clearTasks();
                        taskRepositoryViewModel.clearTasks();
                        break;
                    case Types.TASK:
                        repoViewModel.removeTask(planElement.getId());
                        taskRepositoryViewModel.removeTask(planElement.getId());
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
                        addTreeViewElement((SerializablePlanElement) planElement, Types.PLAN);
                        repoViewModel.addPlan(viewModelFactory.createViewModelElement(planElement, Types.PLAN));
                        break;
                    case Types.MASTERPLAN:
                        addTreeViewElement((SerializablePlanElement) planElement, Types.MASTERPLAN);
                        repoViewModel.addPlan(viewModelFactory.createViewModelElement(planElement, Types.MASTERPLAN));
                        break;
                    case Types.PLANTYPE:
                        addTreeViewElement((SerializablePlanElement) planElement, Types.PLANTYPE);
                        repoViewModel.addPlanType(viewModelFactory.createViewModelElement(planElement, Types.PLANTYPE));
                        break;
                    case Types.BEHAVIOUR:
                        addTreeViewElement((SerializablePlanElement) planElement, Types.BEHAVIOUR);
                        repoViewModel.addBehaviour(viewModelFactory.createViewModelElement(planElement, Types.BEHAVIOUR));
                        break;
                    case Types.TASKREPOSITORY:
                        TaskRepository taskRepository = (TaskRepository) planElement;
                        addTreeViewElement(taskRepository, Types.TASKREPOSITORY);
                        for (Task task : taskRepository.getTasks()) {
                            repoViewModel.addTask(viewModelFactory.createViewModelElement(task, Types.TASK, taskRepository.getId()));
                        }
                        break;
                    case Types.TASK:
                        Task task = (Task) planElement;
                        ViewModelElement element = viewModelFactory.createViewModelElement(task, Types.TASK, task.getTaskRepository().getId());
                        repoViewModel.addTask(element);
                        taskRepositoryViewModel.addTask(element);
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
                        taskRepositoryViewModel.setDirty(false);
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
                /**
                 * Special treatment for plan name since it affects other plan elements
                 */
                if (event.getChangedAttribute().equals("name")) {
                    handleChangedName(event);
                }
                break;
            default:
                throw new RuntimeException("Controller:Unknown model event captured!");
        }
    }

    private void handleChangedName(ModelEvent event) {
        PlanElement planElement = event.getNewElement();

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
                viewModelElement = viewModelFactory.createViewModelElement(planElement, Types.MASTERPLAN);
                repoViewModel.addPlan(viewModelElement);
            } else {
                viewModelElement = viewModelFactory.createViewModelElement(planElement, Types.PLAN);
                repoViewModel.addPlan(viewModelElement);
            }
            replaceFileTreeItem(viewModelElement);
        } else if (planElement instanceof Behaviour) {
            viewModelElement = viewModelFactory.createViewModelElement(planElement, Types.BEHAVIOUR);
            repoViewModel.removeBehaviour(planElement.getId());
            repoViewModel.addBehaviour(viewModelElement);
            replaceFileTreeItem(viewModelElement);
        } else if (planElement instanceof PlanType) {
            viewModelElement = viewModelFactory.createViewModelElement(planElement, Types.PLANTYPE);
            repoViewModel.removePlanType(planElement.getId());
            repoViewModel.addPlanType(viewModelElement);
            replaceFileTreeItem(viewModelElement);
        } else if (planElement instanceof TaskRepository) {
            viewModelElement = viewModelFactory.createViewModelElement(planElement, Types.TASKREPOSITORY);
            replaceFileTreeItem(viewModelElement);
        } else if (planElement instanceof Task) {
            viewModelElement = viewModelFactory.createViewModelElement(planElement, Types.TASK, ((Task) planElement).getTaskRepository().getId());
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
            fileTreeView.addViewModelElement(viewModelFactory.createViewModelElement(planElement, type));
        }
    }

    private void removeTreeViewElement(SerializablePlanElement planElement, String type) {
        FileTreeView fileTreeView = mainWindowController.getFileTreeView();
        if (fileTreeView != null) {
            fileTreeView.removeViewModelElement(viewModelFactory.createViewModelElement(planElement, type));
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
                usageViewModelElements.add(viewModelFactory.createViewModelElement(planElement, FileSystemUtil.getTypeString(planElement)));
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
            case MOVE_ELEMENT:
                mmq = new ModelModificationQuery(ModelQueryType.MOVE_ELEMENT, event.getAbsoluteDirectory(), event.getElementType(), event.getName());
                mmq.setElementId(event.getElementId());
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
            PlanElement element = modelManager.getPlanElement(path.toString());
            if (element == null) {
                return;
            }
            mmq = new ModelModificationQuery(ModelQueryType.DELETE_ELEMENT, path.toString());
            mmq.setElementId(element.getId());
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

    @Override
    public void handleTabOpenedEvent(TaskRepositoryTab taskRepositoryTab) {
        taskRepositoryViewModel.clearTasks();
        TaskRepository taskRepo = modelManager.getTaskRepository();
        if (taskRepo != null) {
            for (Task task : taskRepo.getTasks()) {
                taskRepositoryViewModel.addTask(viewModelFactory.createViewModelElement(task, Types.TASK, task.getTaskRepository().getId()));
            }
        }
        taskRepositoryViewModel.setTaskRepositoryTab(taskRepositoryTab);
        taskRepositoryViewModel.setDirty(false);
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
        planTypeTab.setPlanTypeViewModel(viewModelFactory.createPlanTypeViewModel(planTypeTab.getPresentedViewModelElement(),
                modelManager.getPlans()));
    }

    @Override
    public ViewModelElement getViewModelElement(long id) {
        return viewModelFactory.getViewModelElement(modelManager.getPlanElement(id));
    }

    @Override
    public BehaviourViewModel getBehaviourViewModel(long id) {
        return viewModelFactory.createBehaviourViewModel(modelManager.getPlanElement(id));
    }

    protected void updatePlansInPlanTypeTabs(Plan plan) {
        ObservableList<Tab> tabs = editorTabPane.getTabs();
        for (Tab tab : tabs) {
            if (tab instanceof PlanTypeTab) {
                PlanTypeTab planTypeTab = (PlanTypeTab) tab;
                if (plan.getMasterPlan()) {
                    planTypeTab.getController().getPlanTypeViewModel().addPlanToAllPlans(viewModelFactory.createViewModelElement(plan, Types.MASTERPLAN));
                } else {
                    planTypeTab.getController().getPlanTypeViewModel().addPlanToAllPlans(viewModelFactory.createViewModelElement(plan, Types.PLAN));
                }
            }
        }
    }

    protected void updateAnnotatedPlansInPlanTypeTabs(long planTypeID, AnnotatedPlan annotatedPlan, boolean add) {
        ObservableList<Tab> tabs = editorTabPane.getTabs();
        for (Tab tab : tabs) {
            if (!(tab instanceof PlanTypeTab)) {
                continue;
            }
            if (((PlanTypeTab) tab).getPresentedViewModelElement().getId() != planTypeID) {
                continue;
            }
            PlanTypeTab planTypeTab = (PlanTypeTab) tab;
            if (add) {
                planTypeTab.getController().getPlanTypeViewModel().addPlanToPlansInPlanType(viewModelFactory.createAnnotatedPlanView(annotatedPlan));
            } else {
                planTypeTab.getController().getPlanTypeViewModel().removePlanFromPlansInPlanType(annotatedPlan.getPlan().getId());
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
