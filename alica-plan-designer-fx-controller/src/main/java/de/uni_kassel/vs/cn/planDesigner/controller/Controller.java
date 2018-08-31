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
import de.uni_kassel.vs.cn.planDesigner.view.model.*;
import de.uni_kassel.vs.cn.planDesigner.view.repo.RepositoryTabPane;
import de.uni_kassel.vs.cn.planDesigner.view.repo.RepositoryViewModel;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import org.apache.commons.beanutils.BeanUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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

    // HANDLER EVENT METHODS

    /**
     * Handles events fired by the model manager, when the model has changed.
     * @param event Object that describes the purpose/context of the fired event.
     */
    public void handleModelEvent(ModelEvent event) {
        PlanElement modelElement = event.getElement();
        ViewModelElement viewModelElement = viewModelFactory.getViewModelElement(modelElement);

        switch(event.getElementType()) {
            case Types.MASTERPLAN:
            case Types.PLAN:
            case Types.PLANTYPE:
            case Types.BEHAVIOUR:
            case Types.TASKREPOSITORY:
                updateRepos(event.getEventType(), viewModelElement);
                updateFileTreeView(event.getEventType(), viewModelElement);
                break;
            case Types.TASK:
                updateRepos(event.getEventType(), viewModelElement);
                break;
        }

        updateViewModel(event, viewModelElement, modelElement);
    }

    /**
     * Handles the model event for the repository view.
     * @param eventType
     * @param viewModelElement
     */
    private void updateRepos(ModelEventType eventType, ViewModelElement viewModelElement) {
        switch(eventType) {
            case ELEMENT_PARSED:
            case ELEMENT_CREATED:
                repoViewModel.addElement(viewModelElement);
                break;
            case ELEMENT_DELETED:
                repoViewModel.removeElement(viewModelElement);
                break;
        }
    }

    /**
     * Handles the model event for the file tree view.
     * @param eventType
     * @param viewModelElement
     */
    private void updateFileTreeView(ModelEventType eventType, ViewModelElement viewModelElement) {
        switch(eventType) {
            case ELEMENT_PARSED:
            case ELEMENT_CREATED:
                mainWindowController.getFileTreeView().addViewModelElement(viewModelElement);
                break;
            case ELEMENT_DELETED:
                mainWindowController.getFileTreeView().removeViewModelElement(viewModelElement);
                break;
        }
    }

    /**
     * Handles the model event for the the view model elements.
     * @param event
     * @param viewModelElement
     * @param planElement
     */
    private void updateViewModel(ModelEvent event, ViewModelElement viewModelElement, PlanElement planElement) {
        switch(event.getEventType()) {
            case ELEMENT_DELETED:
                viewModelFactory.removeElement(viewModelElement);
                break;
            case ELEMENT_ATTRIBUTE_CHANGED:
                try {
                    BeanUtils.setProperty((ViewModelElement) viewModelElement, event.getChangedAttribute(), BeanUtils.getProperty(planElement, event.getChangedAttribute()));
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
                break;
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
                usageViewModelElements.add(viewModelFactory.getViewModelElement(planElement));
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
                mmq.setParentId(event.getParentId());
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
    public void handleTabOpenedEvent(PlanTab planTab) {
        planTab.setPlanViewModel((PlanViewModel) viewModelFactory.getViewModelElement((Plan) modelManager.getPlanElement(planTab.getPresentedViewModelElement().getId())));

    }

    @Override
    public void handleTabOpenedEvent(BehaviourTab behaviourTab) {
        System.err.println("Controller: Opening BehaviourTabs not implemented, yet!");
    }

    @Override
    public void handleTabOpenedEvent(PlanTypeTab planTypeTab) {
        planTypeTab.setPlanTypeViewModel((PlanTypeViewModel) viewModelFactory.getViewModelElement((PlanType) modelManager.getPlanElement(planTypeTab.getPresentedViewModelElement().getId())));
    }

    @Override
    public ViewModelElement getViewModelElement(long id) {
        return viewModelFactory.getViewModelElement(modelManager.getPlanElement(id));
    }

    protected void updatePlansInPlanTypeTabs(PlanViewModel planViewModel) {
        ObservableList<Tab> tabs = editorTabPane.getTabs();
        for (Tab tab : tabs) {
            if (tab instanceof PlanTypeTab) {
                PlanTypeTab planTypeTab = (PlanTypeTab) tab;
                planTypeTab.getPlanTypeWindowController().getPlanTypeViewModel().addPlanToAllPlans(planViewModel);
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
                planTypeTab.getPlanTypeWindowController().getPlanTypeViewModel().addPlanToPlansInPlanType((AnnotatedPlanView) viewModelFactory.getViewModelElement(annotatedPlan));
            } else {
                planTypeTab.getPlanTypeWindowController().getPlanTypeViewModel().removePlanFromPlansInPlanType(annotatedPlan.getPlan().getId());
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
