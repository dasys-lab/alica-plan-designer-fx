package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.generator.GeneratedSourcesManager;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.*;
import de.uni_kassel.vs.cn.planDesigner.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.configuration.Configuration;
import de.uni_kassel.vs.cn.planDesigner.configuration.ConfigurationEventHandler;
import de.uni_kassel.vs.cn.planDesigner.configuration.ConfigurationManager;
import de.uni_kassel.vs.cn.planDesigner.events.ResourceCreationEvent;
import de.uni_kassel.vs.cn.planDesigner.filebrowser.FileSystemEventHandler;
import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IGuiStatusHandler;
import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IResourceCreationHandler;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.IModelEventHandler;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelEvent;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.filebrowser.TreeViewModelElement;
import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IShowUsageHandler;
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
public final class Controller implements IModelEventHandler, IShowUsageHandler, IGuiStatusHandler, IResourceCreationHandler {

    // Common Objects
    private ConfigurationManager configurationManager;
    private FileSystemEventHandler fileSystemEventHandler;
    private ConfigurationEventHandler configEventHandler;
    private I18NRepo i18NRepo;

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

        i18NRepo = I18NRepo.getInstance();

        setupModelManager();
        mainWindowController = MainWindowController.getInstance();
        mainWindowController.setGuiStatusHandler(this);
        mainWindowController.setResourceCreationHandler(this);
        mainWindowController.setShowUsageHandler(this);

        commandStack = new CommandStack();

        setupConfigGuiStuff();


        repoTabPane = mainWindowController.getRepositoryTabPane();

        repoViewModel = new RepositoryViewModel();
        repoViewModel.setRepositoryTabPane(repoTabPane);

        fileSystemEventHandler = new FileSystemEventHandler(this);
        new Thread(fileSystemEventHandler).start(); // <- will be stopped by the PlanDesigner.isRunning() flag

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
            modelManager.loadModelFromDisk();
        }
    }

    protected void setupConfigGuiStuff() {
        configWindowController = new ConfigurationWindowController();
        configEventHandler = new ConfigurationEventHandler(configWindowController, configurationManager);
        configWindowController.setHandler(configEventHandler);
        mainWindowController.setConfigWindowController(configWindowController);
    }

    /**
     * Determines the type string corresponding to the given PlanElement.
     * @param planElement whose type is to be determined
     * @return type of the plan element
     */
    public String getTypeString(PlanElement planElement) {
        if (planElement instanceof Plan) {
            Plan plan = (Plan) planElement;
            if (plan.getMasterPlan()) {
                return i18NRepo.getString("alicatype.masterplan");
            } else {
                return i18NRepo.getString("alicatype.plan");
            }
        } else if (planElement instanceof Behaviour) {
            return i18NRepo.getString("alicatype.behaviour");
        } else if (planElement instanceof PlanType) {
            return i18NRepo.getString("alicatype.plantype");
        } else if (planElement instanceof Task) {
            return i18NRepo.getString("alicatype.task");
        } else if (planElement instanceof Role) {
            return i18NRepo.getString("alicatype.role");
        } else {
            return null;
        }
    }

    // Handler Event Methods

    /**
     * Called when something relevant in the filesystem has changed.
     * @param event
     * @param path
     */
    public void handleFileSystemEvent(WatchEvent event, Path path) {
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
                    repoViewModel.addPlan(new ViewModelElement(plan.getId(), plan.getName(), getTypeString(planElement)));
                } else if (planElement instanceof PlanType) {
                    repoViewModel.addPlanType(new ViewModelElement(planElement.getId(), planElement.getName(), getTypeString(planElement)));
                } else if (planElement instanceof Behaviour) {
                    mainWindowController.getFileTreeView().addBehaviour(new TreeViewModelElement(planElement.getId(),
                            planElement.getName(), getTypeString(planElement), ((Behaviour) planElement).getRelativeDirectory()));
                    repoViewModel.addBehaviour(new ViewModelElement(planElement.getId(), planElement.getName(), getTypeString(planElement)));
                } else if (planElement instanceof Task) {
                    repoViewModel.addTask(new ViewModelElement(planElement.getId(), planElement.getName(), getTypeString(planElement)));
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

    /**
     * Called by the 'ShowUsage'-ContextMenu of RepositoryHBoxes
     * @param viewModelElement
     * @return
     */
    @Override
    public ArrayList<ViewModelElement> getUsages(ViewModelElement viewModelElement) {
        ArrayList<ViewModelElement> usage = new ArrayList<>();
        for (PlanElement planElement : this.modelManager.getUsages(viewModelElement.getId())) {
            usage.add(new ViewModelElement(planElement.getId(), planElement.getName(), getTypeString(planElement)));
        }
        return usage;
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
    public void handleGuiInitialzed() {
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

    /**
     * Called by the context menu for creating plans, behaviours etc.
     * @param event
     */
    @Override
    public void handleResourceCreationEvent(ResourceCreationEvent event) {
        this.modelManager.createResource(event.getAbsoluteDirectory(), event.getType(), event.getName());
    }
}
