package de.unikassel.vs.alica.planDesigner.controller;

import de.unikassel.vs.alica.generator.Codegenerator;
import de.unikassel.vs.alica.generator.GeneratedSourcesManager;
import de.unikassel.vs.alica.generator.plugin.PluginManager;
import de.unikassel.vs.alica.planDesigner.ViewModelManagement.ViewModelManager;
import de.unikassel.vs.alica.planDesigner.alicamodel.AbstractPlan;
import de.unikassel.vs.alica.planDesigner.alicamodel.Behaviour;
import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.alicamodel.PlanElement;
import de.unikassel.vs.alica.planDesigner.configuration.Configuration;
import de.unikassel.vs.alica.planDesigner.configuration.ConfigurationEventHandler;
import de.unikassel.vs.alica.planDesigner.configuration.ConfigurationManager;
import de.unikassel.vs.alica.planDesigner.converter.CustomLongConverter;
import de.unikassel.vs.alica.planDesigner.converter.CustomPlanElementConverter;
import de.unikassel.vs.alica.planDesigner.converter.CustomStringConverter;
import de.unikassel.vs.alica.planDesigner.events.*;
import de.unikassel.vs.alica.planDesigner.filebrowser.FileSystemEventHandler;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiStatusHandler;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.plugin.PluginEventHandler;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.BendPoint;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.AbstractPlanTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.EditorTabPane;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.taskRepoTab.TaskRepositoryTab;
import de.unikassel.vs.alica.planDesigner.view.menu.FileTreeViewContextMenu;
import de.unikassel.vs.alica.planDesigner.view.model.*;
import de.unikassel.vs.alica.planDesigner.view.repo.RepositoryTabPane;
import de.unikassel.vs.alica.planDesigner.view.repo.RepositoryViewModel;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private ViewModelManager viewModelManager;

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

        viewModelManager = new ViewModelManager(modelManager, this);

        repoViewModel = viewModelManager.createRepositoryViewModel();

        setupBeanConverters();
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

    private void setupBeanConverters() {
        CustomStringConverter customStringConverter = new CustomStringConverter();
        CustomLongConverter customLongConverter = new CustomLongConverter();
        CustomPlanElementConverter customPlanElementConverter = new CustomPlanElementConverter(this.modelManager);

        // Temporarily setting the log-level to prevent unnecessary output
        Level logLevel = Logger.getRootLogger().getLevel();
        Logger.getRootLogger().setLevel(Level.WARN);

        ConvertUtils.register(customStringConverter, String.class);
        ConvertUtils.register(customLongConverter, Long.class);
        ConvertUtils.register(customPlanElementConverter, PlanElement.class);

        // Setting the log-level to its previous level
        Logger.getRootLogger().setLevel(logLevel);
    }

    // HANDLER EVENT METHODS

    /**
     * Handles Codegeneration events
     *
     * @param event
     */
    public void generateCode(GuiModificationEvent event, Text generatingText) {
        Codegenerator codegenerator = new Codegenerator(
                modelManager.getPlans(),
                modelManager.getBehaviours(),
                modelManager.getConditions(),
                configurationManager.getClangFormatPath(),
                configurationManager.getActiveConfiguration().getGenSrcPath(),
                generatedSourcesManager);
        Platform.runLater(() -> generatingText.textProperty().bind(codegenerator.currentFile));
        switch (event.getEventType()) {
            case GENERATE_ELEMENT:
                codegenerator.generate((AbstractPlan) modelManager.getPlanElement(event.getElementId()));
                break;
            case GENERATE_ALL_ELEMENTS:
                codegenerator.generate();
                break;
            default:
                System.out.println("Controller.generateCode(): Event type " + event.getEventType() + " is not handled.");
                break;
        }
    }

    /**
     * Handles events fired by the model manager, when the model has changed.
     *
     * @param event Object that describes the purpose/context of the fired event.
     */
    public void handleModelEvent(ModelEvent event) {
        PlanElement modelElement = event.getElement();
        ViewModelElement viewModelElement = viewModelManager.getViewModelElement(modelElement);

        switch (event.getElementType()) {
            case Types.MASTERPLAN:
            case Types.PLAN:
            case Types.PLANTYPE:
            case Types.BEHAVIOUR:
            case Types.ROLESET:
            case Types.TASKREPOSITORY:
                updateRepos(event.getEventType(), viewModelElement);
                updateFileTreeView(event, viewModelElement);
                break;
            case Types.TASK:
            case Types.ROLE:
                updateRepos(event.getEventType(), viewModelElement);
                break;
        }
        // Generate files for moved code
        if(event.getEventType() == ModelEventType.ELEMENT_ATTRIBUTE_CHANGED  && event.getChangedAttribute().equals("relativeDirectory")) {
            mainWindowController.waitOnProgressLabel(() -> generateCode(new GuiModificationEvent(GuiEventType.GENERATE_ALL_ELEMENTS, event.getElementType(),
                    modelElement.getName()), mainWindowController.getStatusText()));
        }
        updateViewModel(event, viewModelElement, modelElement);
    }

    /**
     * Handles the model event for the repository view.
     *
     * @param eventType
     * @param viewModelElement
     */
    private void updateRepos(ModelEventType eventType, ViewModelElement viewModelElement) {
        switch (eventType) {
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
     *
     * @param event
     * @param viewModelElement
     */
    private void updateFileTreeView(ModelEvent event, ViewModelElement viewModelElement) {
        switch (event.getEventType()) {
            case ELEMENT_PARSED:
            case ELEMENT_CREATED:
                mainWindowController.getFileTreeView().addViewModelElement(viewModelElement);
                break;
            case ELEMENT_DELETED:
                mainWindowController.getFileTreeView().removeViewModelElement(viewModelElement);
                break;
            case ELEMENT_ATTRIBUTE_CHANGED:
                if(event.getChangedAttribute().equals("relativeDirectory")) {
                    mainWindowController.getFileTreeView().removeViewModelElement(viewModelElement);
                    mainWindowController.getFileTreeView().addViewModelElement(viewModelElement);
                }
                break;
        }
    }

    /**
     * Handles the model event for the the view model elements.
     *
     * @param event
     * @param viewModelElement
     * @param planElement
     */
    private void updateViewModel(ModelEvent event, ViewModelElement viewModelElement, PlanElement planElement) {
        switch (event.getEventType()) {
            case ELEMENT_DELETED:
            case ELEMENT_REMOVED:
                viewModelManager.removeElement(event.getParentId(), viewModelElement);
                break;
            case ELEMENT_ATTRIBUTE_CHANGED:
                viewModelManager.changeElementAttribute(viewModelElement, event.getChangedAttribute(), event.getNewValue());
                break;
            case ELEMENT_PARSED:
            case ELEMENT_CREATED:
            case ELEMENT_ADDED:
                viewModelManager.addElement(event);
                break;
            case ELEMENT_CONNECTED:
                viewModelManager.connectElement(event);
                break;
            case ELEMENT_DISCONNECTED:
                viewModelManager.disconnectElement(event);
                break;
            case ELEMENT_CHANGED_POSITION:
                viewModelManager.changePosition((PlanElementViewModel) viewModelElement, event);
            default:
                System.out.println("Controller.updateViewModel(): Event type " + event.getEventType() + " is not handled.");
                break;
        }

        if (event.getUiElement() != null && event.getUiElement().getBendPoints().size() != 0) {
            TransitionViewModel transition = (TransitionViewModel) viewModelElement;
            transition.getBendpoints().clear();
            for (BendPoint bendPoint : event.getUiElement().getBendPoints()) {
                transition.addBendpoint((BendPointViewModel) viewModelManager.getViewModelElement(bendPoint));
            }
            ModelEvent modelEvent = new ModelEvent(ModelEventType.ELEMENT_CREATED, planElement, Types.BENDPOINT);
            updateViewModel(modelEvent, viewModelElement, planElement);
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
                usageViewModelElements.add(viewModelManager.getViewModelElement(planElement));
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
        ((FileTreeViewContextMenu) mainWindowController.getFileTreeView().getContextMenu()).showTaskrepositoryItem(true);
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
        editorTabPane = mainWindowController.getEditorTabPane();
        Configuration activeConfiguration = configurationManager.getActiveConfiguration();
        if (activeConfiguration != null) {
            mainWindowController.setUpFileTreeView(activeConfiguration.getPlansPath(), activeConfiguration.getRolesPath(), activeConfiguration.getTasksPath());
            new Thread(fileSystemEventHandler).start(); // <- will be stopped by the PlanDesigner.isRunning() flag
            modelManager.loadModelFromDisk();
        }
        repoTabPane = mainWindowController.getRepositoryTabPane();
        repoViewModel.setRepositoryTabPane(repoTabPane);
        repoViewModel.initGuiContent();
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
                mmq.setComment(event.getComment());
                mmq.setRelatedObjects(event.getRelatedObjects());
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
                mmq.setName(event.getName());
                mmq.setComment(event.getComment());
                mmq.setX(event.getX());
                mmq.setY(event.getY());
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
                mmq.setElementId(event.getElementId());
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
                mmq.setRelatedObjects(event.getRelatedObjects());
                mmq.setAttributeName(event.getName());
                if (event instanceof GuiChangeAttributeEvent) {
                    GuiChangeAttributeEvent guiChangeAttributeEvent = (GuiChangeAttributeEvent) event;
                    mmq.setAttributeName(guiChangeAttributeEvent.getAttributeName());
                    mmq.setAttributeType(guiChangeAttributeEvent.getAttributeType());
                    mmq.setNewValue(guiChangeAttributeEvent.getNewValue());
                }
                break;
            case CHANGE_POSITION:
                mmq = new ModelModificationQuery(ModelQueryType.CHANGE_POSITION);
                mmq.setElementType(event.getElementType());
                mmq.setElementId(event.getElementId());
                mmq.setParentId(event.getParentId());
                mmq.setX(event.getX());
                mmq.setY(event.getY());
                break;
            case MOVE_FILE:
                mmq = new ModelModificationQuery(ModelQueryType.MOVE_FILE, event.getAbsoluteDirectory(), event.getElementType(), event.getName());
                mmq.setElementId(event.getElementId());
                break;
            case GENERATE_ELEMENT:
            case GENERATE_ALL_ELEMENTS:
                mmq = null;
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
        // A change in a sub-directory also creates an event for the parent-directory. This event must be ignored,
        // because its filename is only the name of the subdirectory
        /*if (!path.toFile().isFile()) {
            return;
        }*/

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
        mainWindowController.getFileTreeView().updateDirectories(path);
        this.modelManager.handleModelModificationQuery(mmq);
    }

    public void handleNoTaskRepositoryNotification() {
        HashMap<String, Double> params = configEventHandler.getPreferredWindowSettings();
        I18NRepo i18NRepo = I18NRepo.getInstance();
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(i18NRepo.getString("label.warn"));
        alert.setHeaderText(i18NRepo.getString("label.error.missing.taskrepository"));
        alert.setContentText(i18NRepo.getString("label.error.missing.taskrepository.choice"));
        alert.setX(params.get("x") + Screen.getPrimary().getVisualBounds().getWidth() / 2 - alert.getDialogPane().getWidth() * 1.5);
        alert.setY(params.get("y") + Screen.getPrimary().getVisualBounds().getHeight() / 2 - alert.getDialogPane().getHeight() * 1.5);

        ButtonType createTaskRepositoryBtn = new ButtonType(i18NRepo.getString("action.create.taskrepository"));
        ButtonType confirmBtn = new ButtonType(i18NRepo.getString("action.openanyway"));
        ButtonType closeBtn = new ButtonType(i18NRepo.getString("action.close"), ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(createTaskRepositoryBtn, confirmBtn, closeBtn);

        alert.showAndWait();
        if (alert.getResult() == confirmBtn) {
            alert.close();
        } else if (alert.getResult() == closeBtn) {
            Platform.exit();
            System.exit(0);
        } else if (alert.getResult() == createTaskRepositoryBtn) {
            CreateNewDialogController createNewDialogController;
            if (configurationManager.getActiveConfiguration() != null) {
                createNewDialogController = ((FileTreeViewContextMenu) MainWindowController.getInstance().getFileTreeView()
                        .getContextMenu()).getNewResourceMenu().createFileDialog(new File(configurationManager.getActiveConfiguration().getTasksPath()), Types.TASKREPOSITORY);
            } else {
                createNewDialogController = ((FileTreeViewContextMenu) MainWindowController.getInstance().getFileTreeView()
                        .getContextMenu()).getNewResourceMenu().createFileDialog(null, Types.TASKREPOSITORY);
            }
            createNewDialogController.getStage().setX(alert.getX() + createNewDialogController.getMainVBox().getPrefWidth() / 2);
            createNewDialogController.getStage().setY(alert.getY());
            createNewDialogController.showAndWait();
        }
    }

    @Override
    public void handleWrongTaskRepositoryNotification(String planName, long taskID) {
        HashMap<String, Double> params = configEventHandler.getPreferredWindowSettings();
        I18NRepo i18NRepo = I18NRepo.getInstance();
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(i18NRepo.getString("label.warn"));
        alert.setHeaderText(i18NRepo.getString("label.error.wrong.taskrepository") + " " + taskID + "  "
                + i18NRepo.getString("label.error.wrong.taskrepository2") + " " + planName+ ".");
        alert.setX(params.get("x") + Screen.getPrimary().getVisualBounds().getWidth() / 2 - alert.getDialogPane().getWidth());
        alert.setY(params.get("y") + Screen.getPrimary().getVisualBounds().getHeight() / 2 - alert.getDialogPane().getHeight());

        ButtonType closeBtn = new ButtonType(i18NRepo.getString("action.close"), ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(closeBtn);

        alert.showAndWait();
        Platform.exit();
        System.exit(0);
    }

    @Override
    public ViewModelElement getViewModelElement(long id) {
        return viewModelManager.getViewModelElement(modelManager.getPlanElement(id));
    }

    @Override
    public void handleCloseTab(long planElementId) {
        for (Tab tab : editorTabPane.getTabs()) {
            if (tab instanceof AbstractPlanTab) {
                AbstractPlanTab abstractPlanTab = (AbstractPlanTab) tab;
                if (abstractPlanTab.getSerializableViewModel().getId() == planElementId) {
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
                if (taskRepositoryTab.getSerializableViewModel().getId() == planElementId) {
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
    public RepositoryViewModel getRepoViewModel() {
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

    @Override
    public List<File> getGeneratedFilesForAbstractPlan(AbstractPlan abstractPlan) {
        if(abstractPlan instanceof Behaviour) {
            return generatedSourcesManager.getGeneratedFilesForBehaviour((Behaviour) abstractPlan);
        } else if (abstractPlan instanceof Plan) {
            List<File> fileList = generatedSourcesManager.getGeneratedConditionFilesForPlan(abstractPlan);
            fileList.addAll(generatedSourcesManager.getGeneratedConstraintFilesForPlan(abstractPlan));
            return fileList;
        } else {
            return new ArrayList<>();
        }
    }
}
