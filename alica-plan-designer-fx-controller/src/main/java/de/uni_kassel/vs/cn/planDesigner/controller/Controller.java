package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.generator.GeneratedSourcesManager;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.*;
import de.uni_kassel.vs.cn.planDesigner.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.configuration.Configuration;
import de.uni_kassel.vs.cn.planDesigner.configuration.ConfigurationEventHandler;
import de.uni_kassel.vs.cn.planDesigner.configuration.ConfigurationManager;
import de.uni_kassel.vs.cn.planDesigner.filebrowser.FileSystemEventHandler;
import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IGuiStatusHandler;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.IModelEventHandler;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelEvent;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.view.filebrowser.TreeViewModelElement;
import de.uni_kassel.vs.cn.planDesigner.view.menu.IShowUsageHandler;
import de.uni_kassel.vs.cn.planDesigner.view.repo.RepositoryTabPane;
import de.uni_kassel.vs.cn.planDesigner.view.repo.RepositoryViewModel;
import de.uni_kassel.vs.cn.planDesigner.view.repo.ViewModelElement;

import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.ArrayList;

/**
 * Central class that synchronizes model and view.
 * It is THE CONTROLLER regarding the Model-View-Controller pattern,
 * implemented in the Plan Designer.
 */
public final class Controller implements IModelEventHandler, IShowUsageHandler, IGuiStatusHandler {

    // Common Objects
    private ConfigurationManager configurationManager;
    private FileSystemEventHandler fileSystemEventHandler;
    private ConfigurationEventHandler configEventHandler;

    // Model Objects
    private ModelManager modelManager;
    private CommandStack commandStack;

    // View Objects
    private RepositoryViewModel repoViewModel;
    private MainWindowController mainWindowController;
    private ConfigurationWindowController configWindowController;
    private RepositoryTabPane repoTabPane;

    // Code Generation Objects
    GeneratedSourcesManager generatedSourcesManager;

    public Controller() {
        configurationManager = ConfigurationManager.getInstance();
        configurationManager.setController(this);

        setupModelManager();
        mainWindowController = MainWindowController.getInstance();
        mainWindowController.setGuiStatusHandler(this);

        commandStack = new CommandStack();

        setupConfigGuiStuff();

        mainWindowController.setShowUsageHandler(this);
        repoTabPane = mainWindowController.getRepositoryTabPane();

        repoViewModel = new RepositoryViewModel();
        repoViewModel.setRepositoryTabPane(repoTabPane);

        fileSystemEventHandler = new FileSystemEventHandler(this);
        new Thread(fileSystemEventHandler).start(); // <- will be stopped by the PlanDesigner.isRunning() flag

        setupGeneratedSourcesManager();
    }

    protected void setupGeneratedSourcesManager() {
        generatedSourcesManager = new GeneratedSourcesManager();
        Configuration activeConfiguration = configurationManager.getActiveConfiguration();
        generatedSourcesManager.setEditorExecutablePath(configurationManager.getEditorExecutablePath());
        if (activeConfiguration != null) {
            generatedSourcesManager.setGenSrcPath(configurationManager.getActiveConfiguration().getGenSrcPath());
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
            modelManager.loadModelFromDisk();
        }
    }

    protected void setupConfigGuiStuff() {
        configWindowController = new ConfigurationWindowController();
        configEventHandler = new ConfigurationEventHandler(configWindowController, configurationManager);
        configWindowController.setHandler(configEventHandler);
        mainWindowController.setConfigWindowController(configWindowController);
    }

    public void handleFileSystemEvent(WatchEvent event, Path path) {
        System.out.println("handle file system called");
        modelManager.handleFileSystemEvent(event.kind(), path);
    }

    /**
     * Handles events fired by the model manager, when the model has changed.
     *
     * @param event Object that describes the purpose/context of the fired event.
     */
    public void handleModelEvent(ModelEvent event) {
        if (repoViewModel == null) {
            return;
        }

        switch (event.getType()) {
            case ELEMENT_CREATED:
                PlanElement planElement = event.getNewElement();
                if (planElement instanceof Plan) {
                    Plan plan = (Plan) planElement;
                    repoViewModel.addPlan(new ViewModelElement(plan.getId(), plan.getName(), (plan.getMasterPlan() ? "masterplan" : "plan")));
                } else if (planElement instanceof PlanType) {
                    repoViewModel.addPlanType(new ViewModelElement(planElement.getId(), planElement.getName(), planElement.getClass().toString()));
                } else if (planElement instanceof Behaviour) {
                    mainWindowController.getFileTreeView().addBehaviour(new TreeViewModelElement(planElement.getId(),
                            planElement.getName(), planElement.getClass().toString(), ((Behaviour) planElement).getDestinationPath()));
                    repoViewModel.addBehaviour(new ViewModelElement(planElement.getId(), planElement.getName(), planElement.getClass().toString()));
                } else if (planElement instanceof Task) {
                    repoViewModel.addTask(new ViewModelElement(planElement.getId(), planElement.getName(), planElement.getClass().toString()));
                }
                break;
            case ELEMENT_DELETED:
                throw new RuntimeException("Not implemented, yet!");
            case ELEMENT_ATTRIBUTE_CHANGED:
                throw new RuntimeException("Not implemented, yet!");
            default:
                throw new RuntimeException("Unknown model event captured!");
        }
    }

    @Override
    public ArrayList<ViewModelElement> getUsages(ViewModelElement viewModelElement) {
        ArrayList<ViewModelElement> usage = new ArrayList<>();
        for (PlanElement planElement : this.modelManager.getUsages(viewModelElement.getId())) {
            // TODO: fix type string in viewModelElement for everywhere
            usage.add(new ViewModelElement(planElement.getId(), planElement.getName(), planElement.getClass().getTypeName()));
        }
        return usage;
    }

    public void configurationChanged() {
        //TODO for future purposes
        Configuration actiConfiguration = configurationManager.getActiveConfiguration();
        mainWindowController.setUpFileTreeView(actiConfiguration.getPlansPath(), actiConfiguration.getRolesPath(), actiConfiguration.getTasksPath());
    }

    @Override
    public void guiInitialized() {
        mainWindowController.enableMenuBar();
        Configuration activeConfiguration = configurationManager.getActiveConfiguration();
        if (activeConfiguration != null) {
            mainWindowController.setUpFileTreeView(activeConfiguration.getPlansPath(), activeConfiguration.getRolesPath(), activeConfiguration.getTasksPath());
            new Thread(fileSystemEventHandler).start(); // <- will be stopped by the PlanDesigner.isRunning() flag
        }
	repoTabPane = mainWindowController.getRepositoryTabPane();
        repoViewModel.setRepositoryTabPane(repoTabPane);
        repoViewModel.initGuiContent();
    }
}
