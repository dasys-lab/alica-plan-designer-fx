package de.uni_kassel.vs.cn.planDesigner.ui.menu;

import de.uni_kassel.vs.cn.planDesigner.PlanDesigner;
import de.uni_kassel.vs.cn.planDesigner.alica.configuration.Configuration;
import de.uni_kassel.vs.cn.planDesigner.alica.configuration.WorkspaceManager;
import de.uni_kassel.vs.cn.planDesigner.common.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.controller.CreatNewDialogController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.eclipse.emf.ecore.EClass;

import java.io.File;
import java.io.IOException;

import static de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils.getAlicaFactory;

/**
 * Created by marci on 17.03.17.
 */
public class NewResourceMenu extends Menu {
    public NewResourceMenu() {
        super(I18NRepo.getString("label.menu.new"));
        MenuItem newPlanMenuItem = new MenuItem(I18NRepo.getString("label.menu.new.plan"));
        newPlanMenuItem.setOnAction(e -> onElementClick(getAlicaFactory().createPlan().eClass()));
        getItems().add(newPlanMenuItem);
        MenuItem newPlanTypeMenuItem = new MenuItem(I18NRepo.getString("label.menu.new.plantype"));
        newPlanTypeMenuItem.setOnAction(e -> onElementClick(getAlicaFactory().createPlanType().eClass()));
        getItems().add(newPlanTypeMenuItem);
        MenuItem newBehaviourMenuItem = new MenuItem(I18NRepo.getString("label.menu.new.behaviour"));
        newBehaviourMenuItem.setOnAction(e -> onElementClick(getAlicaFactory().createBehaviour().eClass()));
        getItems().add(newBehaviourMenuItem);
        MenuItem newFolderMenuItem = new MenuItem(I18NRepo.getString("label.menu.new.folder"));
        newFolderMenuItem.setOnAction(e -> onElementClick(null));
        getItems().add(newFolderMenuItem);
    }

    protected File getHintFile() {
        return new File(new WorkspaceManager().getActiveWorkspace().getConfiguration().getPlansPath());
    }

    private void onElementClick(EClass resourceInstanceClass) {
        createFileDialog(getHintFile(), resourceInstanceClass);
    }

    private void createFileDialog(File unwrappedFile, EClass planClass) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("createNewDialog.fxml"));
        try {
            Parent rootOfDialog = fxmlLoader.load();
            CreatNewDialogController controller = fxmlLoader.getController();
            controller.setAlicaType(planClass);
            controller.setInitialDirectoryHint(unwrappedFile);
            Stage stage = new Stage();
            stage.setResizable(false);
            if (planClass != null) {
                stage.setTitle(I18NRepo.getString("label.menu.new") + " " +
                        I18NRepo.getString("label.menu.new." + planClass.getName().toLowerCase()));
            } else {
                stage.setTitle(I18NRepo.getString("label.menu.new") + " " +
                        I18NRepo.getString("label.menu.new.folder"));
            }
            stage.setScene(new Scene(rootOfDialog));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(PlanDesigner.getPrimaryStage());
            stage.showAndWait();

        } catch (IOException e) {
            // if the helper window is not loadable something is really wrong here
            e.printStackTrace();
            System.exit(1);
        }
    }
}
