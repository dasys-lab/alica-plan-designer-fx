package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.planDesigner.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.configuration.ConfigurationManager;
import de.uni_kassel.vs.cn.planDesigner.filebrowser.FileSystemEventHandler;
import de.uni_kassel.vs.cn.planDesigner.view.filebrowser.PLDFileTreeView;
import de.uni_kassel.vs.cn.planDesigner.view.repo.RepositoryViewModel;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 * Central class that synchronizes model and view.
 * It is THE CONTROLLER regarding the Model-View-Controller pattern,
 * implemented in the Plan Designer.
 */
public final class Controller {

    // Common Objects
    private ConfigurationManager configurationManager;
    private FileSystemEventHandler fileSystemEventHandler;

    // Model Objects
    private ModelManager modelManager;
    private CommandStack commandStack;

    // View Objects
    private RepositoryViewModel repoViewModel;
    private PLDFileTreeView pldFileTreeView;

    public Controller () {
        configurationManager = ConfigurationManager.getInstance();
        modelManager = new ModelManager();
        fileSystemEventHandler = new FileSystemEventHandler(this);
        new Thread(fileSystemEventHandler).start();
        repoViewModel.setPlans(modelManager.getPlansForUI());
        pldFileTreeView = MainWindowController.getInstance().getFileTreeView();
    }

    public void handleFileSystemEvent(WatchEvent event, Path path) {
        pldFileTreeView.updateTreeView(event.kind(), path);
        modelManager.handleFileSystemEvent(event, path);
    }
}
