package de.uni_kassel.vs.cn.planDesigner.view.menu;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.AbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanType;
import de.uni_kassel.vs.cn.planDesigner.controller.MainWindowController;
import de.uni_kassel.vs.cn.planDesigner.controller.UsagesWindowController;
import de.uni_kassel.vs.cn.planDesigner.view.repo.RepositoryTabPane;
import de.uni_kassel.vs.cn.planDesigner.view.repo.RepositoryViewModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

/**
 * Created by marci on 20.04.17.
 */
public class DeleteFileMenuItem extends MenuItem {
    private File toDelete;
    private CommandStack commandStack;

    public DeleteFileMenuItem(File toDelete) {
        super(I18NRepo.getInstance().getString("label.menu.edit.delete"));
        this.toDelete = toDelete;
        setOnAction(e -> deleteFile());
    }

    public void deleteFile() {
        RepositoryViewModel repositoryViewModel = RepositoryViewModel.getInstance();
        MainWindowController mainWindowController = MainWindowController.getInstance();
        RepositoryTabPane repositoryTabPane = mainWindowController.getRepositoryTabPane();

        // Plans
        Optional<Pair<Plan, Path>> planPathPair = repositoryViewModel.getPlanPathPair(toDelete);
        if (planPathPair.isPresent()) {
            if(checkAbstractPlanUsage(commandStack, planPathPair.get().getKey())) {
                return;
            }
            repositoryViewModel.getPlans().remove(planPathPair.get());
            Tab repoTab = repositoryTabPane.getSelectionModel().getSelectedItem();
            mainWindowController.closeTabIfOpen(planPathPair.get().getKey());
            repositoryTabPane.init();
            repositoryTabPane.getSelectionModel().select(repoTab);
            return;
        }

        // PlanTypes
        Optional<Pair<PlanType, Path>> planTypePathPair = repositoryViewModel.getPlanTypePathPair(toDelete);
        if (planTypePathPair.isPresent()) {
            if(checkAbstractPlanUsage(commandStack, planTypePathPair.get().getKey())) {
                return;
            }
            repositoryViewModel.getPlanTypes().remove(planTypePathPair.get());
            Tab repoTab = repositoryTabPane.getSelectionModel().getSelectedItem();
            mainWindowController.closeTabIfOpen(planTypePathPair.get().getKey());
            repositoryTabPane.init();
            repositoryTabPane.getSelectionModel().select(repoTab);
            return;
        }

        // Behaviours
        Optional<Pair<Behaviour, Path>> behaviourPathPair = repositoryViewModel.getBehaviourPathPair(toDelete);
        if (behaviourPathPair.isPresent()) {
            if(checkAbstractPlanUsage(commandStack, behaviourPathPair.get().getKey())) {
                return;
            }
            repositoryViewModel.getBehaviours().remove(behaviourPathPair.get());
            Tab repoTab = repositoryTabPane.getSelectionModel().getSelectedItem();
            mainWindowController.closeTabIfOpen(behaviourPathPair.get().getKey());
            repositoryTabPane.init();
            repositoryTabPane.getSelectionModel().select(repoTab);
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

    private boolean checkAbstractPlanUsage(CommandStack commandStack, AbstractPlan toBeDeleted) {
        List<AbstractPlan> usages = EMFModelUtils.getUsages(toBeDeleted);
        if (usages.size() > 0) {
            FXMLLoader fxmlLoader = new FXMLLoader(ShowUsagesMenuItem.class.getClassLoader().getResource("usagesWindow.fxml"));
            try {
                Parent infoWindow = fxmlLoader.load();
                UsagesWindowController controller = fxmlLoader.getController();
                controller.createReferencesList(usages);
                Stage stage = new Stage();
                stage.setTitle(I18NRepo.getInstance().getString("label.usage.nodelete"));
                stage.setScene(new Scene(infoWindow));
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(PlanDesigner.getPrimaryStage());
                stage.show();
            } catch (IOException ignored) {
            }
            return true;
        } else {
            commandStack.storeAndExecute(new DeleteAbstractPlan(toBeDeleted));
            return false;
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
