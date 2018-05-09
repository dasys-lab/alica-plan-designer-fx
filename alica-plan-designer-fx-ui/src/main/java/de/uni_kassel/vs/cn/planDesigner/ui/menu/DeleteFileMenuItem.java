package de.uni_kassel.vs.cn.planDesigner.ui.menu;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.delete.DeleteAbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.alica.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanType;
import de.uni_kassel.vs.cn.planDesigner.alica.util.RepoViewBackend;
import de.uni_kassel.vs.cn.planDesigner.common.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import javafx.scene.control.MenuItem;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Created by marci on 20.04.17.
 */
public class DeleteFileMenuItem extends MenuItem {
    private File toDelete;
    private CommandStack commandStack;

    public DeleteFileMenuItem(File toDelete) {
        super(I18NRepo.getString("label.menu.edit.delete"));
        this.toDelete = toDelete;
        setOnAction(e -> deleteFile());
    }

    public void deleteFile() {
        RepoViewBackend repoViewBackend = RepoViewBackend.getInstance();
        MainController mainController = MainController.getInstance();

        // Plans
        Optional<Pair<Plan, Path>> planPathPair = repoViewBackend.getPlanPathPair(toDelete);
        if (planPathPair.isPresent()) {
            commandStack.storeAndExecute(new DeleteAbstractPlan(planPathPair.get().getKey()));
            repoViewBackend.getPlans().remove(planPathPair.get());
            mainController.closeTabIfOpen(planPathPair.get().getKey());
            mainController.getRepositoryTabPane().init();
            return;
        }

        // PlanTypes
        Optional<Pair<PlanType, Path>> planTypePathPair = repoViewBackend.getPlanTypePathPair(toDelete);
        if (planTypePathPair.isPresent()) {
            commandStack.storeAndExecute(new DeleteAbstractPlan(planTypePathPair.get().getKey()));
            repoViewBackend.getPlanTypes().remove(planTypePathPair.get());
            mainController.closeTabIfOpen(planTypePathPair.get().getKey());
            mainController.getRepositoryTabPane().init();
            return;
        }

        // Behaviours
        Optional<Pair<Behaviour, Path>> behaviourPathPair = repoViewBackend.getBehaviourPathPair(toDelete);
        if (behaviourPathPair.isPresent()) {
            commandStack.storeAndExecute(new DeleteAbstractPlan(behaviourPathPair.get().getKey()));
            repoViewBackend.getBehaviours().remove(behaviourPathPair.get());
            mainController.closeTabIfOpen(behaviourPathPair.get().getKey());
            mainController.getRepositoryTabPane().init();
            return;
        }

        // Folders
        if (toDelete.isDirectory()) {
            for (File alsoDelete : toDelete.listFiles()) {
                DeleteFileMenuItem deleteFileMenuItem = new DeleteFileMenuItem(alsoDelete);
                deleteFileMenuItem.setCommandStack(commandStack);
                deleteFileMenuItem.deleteFile();
            }
            try {
                Files.delete(toDelete.toPath());
            } catch (IOException e) {
                throw new RuntimeException("");
            }
        }
    }

    public void setToDelete(File toDelete) {
        this.toDelete = toDelete;
    }

    public void setCommandStack(CommandStack commandStack) {
        this.commandStack = commandStack;
    }

    public CommandStack getCommandStack() {
        return commandStack;
    }
}
