package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.generator.GeneratedSourcesManager;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.*;
import de.uni_kassel.vs.cn.planDesigner.configuration.Configuration;
import de.uni_kassel.vs.cn.planDesigner.configuration.ConfigurationEventHandler;
import de.uni_kassel.vs.cn.planDesigner.configuration.ConfigurationManager;
import de.uni_kassel.vs.cn.planDesigner.events.GuiModificationEvent;
import de.uni_kassel.vs.cn.planDesigner.events.IModelEventHandler;
import de.uni_kassel.vs.cn.planDesigner.events.ModelEvent;
import de.uni_kassel.vs.cn.planDesigner.events.ModelQueryType;
import de.uni_kassel.vs.cn.planDesigner.filebrowser.FileSystemEventHandler;
import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IGuiStatusHandler;
import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IMoveFileHandler;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.FileSystemUtil;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelModificationQuery;
import de.uni_kassel.vs.cn.planDesigner.view.Types;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.*;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.planTypeTab.PlanTypeTab;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.planTypeTab.PlanTypeViewModel;
import de.uni_kassel.vs.cn.planDesigner.view.filebrowser.FileTreeView;
import de.uni_kassel.vs.cn.planDesigner.common.ViewModelElement;
import de.uni_kassel.vs.cn.planDesigner.view.repo.RepositoryTabPane;
import de.uni_kassel.vs.cn.planDesigner.view.repo.RepositoryViewModel;

import javax.swing.text.View;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.util.ArrayList;

/**
 * Central class that synchronizes model and view.
 * It is THE CONTROLLER regarding the Model-View-Controller pattern,
 * implemented in the Plan Designer.
 */
public final class Controller implements IModelEventHandler, IGuiStatusHandler, IGuiModificationHandler, IMoveFileHandler, ITabEventHandler {

    // Common Objects
    private ConfigurationManager configurationManager;
    private FileSystemEventHandler fileSystemEventHandler;
    private ConfigurationEventHandler configEventHandler;

    // Model Objects
    private ModelManager modelManager;

    // View Objects
    private RepositoryViewModel repoViewModel;
    private TaskRepositoryViewModel taskViewModel;
    private PlanTypeViewModel planTypeViewModel;
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
        planTypeViewModel = new PlanTypeViewModel();

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
                switch (event.getElementType()) {
                    case Types.PLAN:
                        Plan plan = (Plan) planElement;
                        addTreeViewElement(plan, Types.PLAN);
                        repoViewModel.addPlan(new ViewModelElement(plan.getId(), plan.getName(), Types.PLAN));
                        break;
                    case Types.MASTERPLAN:
                        plan = (Plan) planElement;
                        addTreeViewElement(plan, Types.MASTERPLAN);
                        repoViewModel.addPlan(new ViewModelElement(plan.getId(), plan.getName(), Types.MASTERPLAN));
                        break;
                    case Types.PLANTYPE:
                        addTreeViewElement((PlanType) planElement, Types.PLANTYPE);
                        repoViewModel.addPlanType(new ViewModelElement(planElement.getId(), planElement.getName(), Types.PLANTYPE));
                        break;
                    case Types.BEHAVIOUR:
                        addTreeViewElement((Behaviour) planElement, Types.BEHAVIOUR);
                        repoViewModel.addBehaviour(new ViewModelElement(planElement.getId(), planElement.getName(), Types.BEHAVIOUR));
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
                    case Types.PLANTYPE:
                        removeTreeViewElement((AbstractPlan) planElement, Types.PLANTYPE);
                        repoViewModel.removePlanType(new ViewModelElement(planElement.getId(), planElement.getName(), Types.PLANTYPE));
                        break;
                    case Types.BEHAVIOUR:
                        removeTreeViewElement((AbstractPlan) planElement, Types.BEHAVIOUR);
                        repoViewModel.removeBehaviour(new ViewModelElement(planElement.getId(), planElement.getName(), Types.BEHAVIOUR));
                        break;
                    case Types.TASK:
                        ViewModelElement taskViewElement = new ViewModelElement(planElement.getId(), planElement.getName(), Types.TASK);
                        repoViewModel.removeTask(taskViewElement);
                        taskViewModel.removeTask(taskViewElement);
                        break;
                    default:
                        System.err.println("Controller: Creation of unknown type " + event.getElementType() + " gets ignored!");
                        break;
                }
                break;
            case ELEMENT_ATTRIBUTE_CHANGED:
                throw new RuntimeException("Controller: ELEMENT_ATTRIBUTE_CHANGED not implemented, yet!");
            default:
                throw new RuntimeException("Controller:Unknown model event captured!");
        }
    }

    private void addTreeViewElement(AbstractPlan planElement, String type) {
        FileTreeView fileTreeView = mainWindowController.getFileTreeView();
        if (fileTreeView != null) {
            fileTreeView.addViewModelElement(new ViewModelElement(planElement.getId(),
                    planElement.getName(), type, planElement.getRelativeDirectory()));
        }
    }

    private void removeTreeViewElement(AbstractPlan planElement, String type) {
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
        editorTabPane.setTabEventHandler(this);
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
                mmq = new ModelModificationQuery(ModelQueryType.DELETE_ELEMENT);
                mmq.setElementType(event.getElementType());
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
            mmq = new ModelModificationQuery(ModelQueryType.DELETE_ELEMENT, path.toString());
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
        System.err.println("Controller: Opening Behaviours not implemented, yet!");
    }

    @Override
    public void handleTabOpenedEvent(PlanTypeTab planTypeTab) {
        planTypeViewModel.clearAllPlans();
        ArrayList<PlanType> planTypes = modelManager.getPlanTypes();
        PlanType currentPlantype = null;
        for(PlanType planType : planTypes) {
            planTypeViewModel.addPlantypeToAllPlans(new ViewModelElement(planType.getId(), planType.getName(), Types.PLANTYPE));
            if(planTypeTab.getViewModelElement().getId() == planType.getId()) {
                currentPlantype = planType;
            }
        }
        if(currentPlantype != null) {
            planTypeViewModel.clearPlansInPlanType();
            for(Plan plan : currentPlantype.getPlans()) {
                planTypeViewModel.addPlantypeToPlansInPlanType(new ViewModelElement(plan.getId(), plan.getName(), Types.PLAN));
            }
        }

        planTypeViewModel.setPlanTypeTab(planTypeTab);

    }

    @Override
    public ViewModelElement getViewModelElement(long id) {
        PlanElement planElement = modelManager.getPlanElement(id);
        if (planElement != null) {
            if (planElement instanceof Task) {
                return new ViewModelElement(planElement.getId(), planElement.getName(), Types.TASK);
            }
            else if (planElement instanceof TaskRepository) {
                return new ViewModelElement(planElement.getId(), planElement.getName(), Types.TASKREPOSITORY);
            }
            else
            {
                System.err.println("Controller: getViewModelElement for type " + planElement.getClass().toString() + " not implemented!");
            }
        }
        return null;
    }
}
