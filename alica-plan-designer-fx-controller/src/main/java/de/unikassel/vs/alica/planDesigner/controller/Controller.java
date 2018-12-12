package de.unikassel.vs.alica.planDesigner.controller;

import de.unikassel.vs.alica.generator.GeneratedSourcesManager;
import de.unikassel.vs.alica.generator.plugin.PluginManager;
import de.unikassel.vs.alica.planDesigner.ViewModelFactory.ViewModelFactory;
import de.unikassel.vs.alica.planDesigner.alicamodel.*;
import de.unikassel.vs.alica.planDesigner.configuration.Configuration;
import de.unikassel.vs.alica.planDesigner.configuration.ConfigurationEventHandler;
import de.unikassel.vs.alica.planDesigner.configuration.ConfigurationManager;
import de.unikassel.vs.alica.planDesigner.events.*;
import de.unikassel.vs.alica.planDesigner.filebrowser.FileSystemEventHandler;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiStatusHandler;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.UiExtensionModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.plugin.PluginEventHandler;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.BendPoint;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PmlUiExtension;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.AbstractPlanTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.EditorTabPane;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.behaviourTab.BehaviourTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTypeTab.PlanTypeTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.taskRepoTab.TaskRepositoryTab;
import de.unikassel.vs.alica.planDesigner.view.model.*;
import de.unikassel.vs.alica.planDesigner.view.repo.RepositoryTabPane;
import de.unikassel.vs.alica.planDesigner.view.repo.RepositoryViewModel;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import org.apache.commons.beanutils.BeanUtils;

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
     * Handles events fired by the {@link ModelManager}, when the UiExtensionModel has changed.
     *
     * @param event  contains all information about the changes in the UiExtensionModel
     */
    @Override
    public void handleUiExtensionModelEvent(UiExtensionModelEvent event) {
        PlanElement modelElement = event.getElement();
        ViewModelElement viewModelElement = viewModelFactory.getViewModelElement(modelElement);
        PlanElementViewModel planElementViewModel = (PlanElementViewModel) viewModelElement;

        planElementViewModel.setXPosition(event.getExtension().getXPos());
        planElementViewModel.setYPosition(event.getExtension().getYPos());

        if (event.getExtension().getBendPoints().size() != 0) {
            TransitionViewModel transition = (TransitionViewModel) planElementViewModel;
            transition.getBendpoints().clear();
            for (BendPoint bendPoint:event.getExtension().getBendPoints()) {
                BendPointViewModel bendPointViewModel = new BendPointViewModel(0, "", Types.BENDPOINT);
                bendPointViewModel.setX(bendPoint.getXPos());
                bendPointViewModel.setY(bendPoint.getYPos());
                transition.addBendpoint(bendPointViewModel);
            }
            ModelEvent modelEvent = new ModelEvent(ModelEventType.ELEMENT_CREATED, modelElement, Types.BENDPOINT);
            updateViewModel(event, viewModelElement, modelElement);
        }
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
                    BeanUtils.setProperty(viewModelElement, event.getChangedAttribute(), BeanUtils.getProperty(planElement, event.getChangedAttribute()));
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
                break;
            case ELEMENT_CREATED:
                int x = 0;
                int y = 0;
                if(event instanceof UiExtensionModelEvent){
                    PmlUiExtension extension = ((UiExtensionModelEvent) event).getExtension();
                    x = extension.getXPos();
                    y = extension.getYPos();
                }


                PlanElement plan = modelManager.getPlanElement(event.getParentId());
                PlanViewModel planViewModel = (PlanViewModel) viewModelFactory.getViewModelElement(plan);
                switch(event.getElementType()) {
                    case Types.STATE:
                    case Types.SUCCESSSTATE:
                    case Types.FAILURESTATE:
                        ((StateViewModel) viewModelElement).setXPosition(x);
                        ((StateViewModel) viewModelElement).setYPosition(y);
                        planViewModel.getStates().add((StateViewModel) viewModelElement);
                        break;
                    case Types.TRANSITION: {
                        ((TransitionViewModel) viewModelElement).setInState((StateViewModel) viewModelFactory.getViewModelElement(((Transition)planElement).getInState()));
                        ((TransitionViewModel) viewModelElement).setOutState((StateViewModel) viewModelFactory.getViewModelElement(((Transition)planElement).getOutState()));
                        planViewModel.getTransitions().add((TransitionViewModel) viewModelElement);
                    } break;
                    case Types.ENTRYPOINT:
                        ((EntryPointViewModel) viewModelElement).setXPosition(x);
                        ((EntryPointViewModel) viewModelElement).setYPosition(y);
                        planViewModel.getEntryPoints().add((EntryPointViewModel) viewModelElement);
                        break;
                    case Types.BENDPOINT:
                        TransitionViewModel transitionViewModel = (TransitionViewModel) viewModelElement;
                        planViewModel.getTransitions().remove(transitionViewModel);
                        planViewModel.getTransitions().add(transitionViewModel);
                    case Types.PRECONDITION:
                    case Types.RUNTIMECONDITION:
                    case Types.POSTCONDITION:
                    case Types.SYNCHRONIZATION:
                    case Types.SYNCTRANSITION:
                        //TODO: Handle these cases
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
        // save everything not saved
        EditorTabPane editorTabPane = mainWindowController.getEditorTabPane();
        for (Tab tab : editorTabPane.getTabs()) {
            EventHandler<Event> handler = tab.getOnCloseRequest();
            if (handler != null) {
                handler.handle(null);
            }
        }

        Configuration activeConfiguration = configurationManager.getActiveConfiguration();

        // clear GUI
        editorTabPane.getTabs().clear();
        repoTabPane.clearGuiContent();
        repoViewModel.clear();
        mainWindowController.setUpFileTreeView(activeConfiguration.getPlansPath(), activeConfiguration.getRolesPath(), activeConfiguration.getTasksPath());

        // load model from path
        modelManager.setPlansPath(activeConfiguration.getPlansPath());
        modelManager.setRolesPath(activeConfiguration.getRolesPath());
        modelManager.setTasksPath(activeConfiguration.getTasksPath());

        modelManager.loadModelFromDisk();
        repoViewModel.initGuiContent();
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
                if(event instanceof GuiChangePositionEvent){
                    UiExtensionModelModificationQuery uimmq = new UiExtensionModelModificationQuery(ModelQueryType.ADD_ELEMENT,
                                                                    event.getElementType(),
                                                                    event.getElementId(),
                                                                    event.getParentId());
                    uimmq.setNewX(((GuiChangePositionEvent) event).getNewX());
                    uimmq.setNewY(((GuiChangePositionEvent) event).getNewY());
                    uimmq.setName(event.getName());
                    uimmq.setComment(event.getComment());
                    mmq = uimmq;
                } else {
                    mmq = new ModelModificationQuery(ModelQueryType.ADD_ELEMENT);
                    mmq.setElementId(event.getElementId());
                    mmq.setElementType(event.getElementType());
                    mmq.setParentId(event.getParentId());
                    mmq.setName(event.getName());
                    mmq.setComment(event.getComment());
                }
                mmq.setRelatedObjects(event.getRelatedObjects());
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
     * Called, when an object was moved in the gui.
     *
     * Method is separate from the handle-method, because this event is about a change to the UiExtensionModel, which is
     * separate from the model.
     *
     * @param event  contains information about which object was moved and to which position it was moved
     */
    @Override
    public void handleGuiChangePositionEvent(GuiChangePositionEvent event) {
        UiExtensionModelModificationQuery uimmq =
                new UiExtensionModelModificationQuery(event.getElementType(), event.getElementId(), event.getParentId());
        uimmq.setNewX(event.getNewX());
        uimmq.setNewY(event.getNewY());

        this.modelManager.handleUiExtensionModelModificationQuery(uimmq);
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
        this.modelManager.handleModelModificationQuery(mmq);
    }

    @Override
    public void handleTabOpenedEvent(PlanTypeTab planTypeTab) {
        planTypeTab.setPlanTypeViewModel((PlanTypeViewModel) viewModelFactory.getViewModelElement((PlanType) modelManager.getPlanElement(planTypeTab.getViewModelElement().getId())));
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
            if (((PlanTypeTab) tab).getViewModelElement().getId() != planTypeID) {
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
                if (abstractPlanTab.getViewModelElement().getId() == planElementId) {
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
                if (taskRepositoryTab.getViewModelElement().getId() == planElementId) {
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
    public RepositoryViewModel getRepoViewModel(){
        return repoViewModel;
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
