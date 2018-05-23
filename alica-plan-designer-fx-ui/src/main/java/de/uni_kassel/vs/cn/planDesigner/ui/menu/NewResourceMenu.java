package de.uni_kassel.vs.cn.planDesigner.ui.menu;

import de.uni_kassel.vs.cn.planDesigner.PlanDesigner;
import de.uni_kassel.vs.cn.planDesigner.alica.configuration.ConfigurationManager;
import de.uni_kassel.vs.cn.planDesigner.common.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.controller.CreateNewDialogController;
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


public class NewResourceMenu extends Menu {

    private I18NRepo i18NRepo;

    public NewResourceMenu() {
        super(I18NRepo.getInstance().getString("label.menu.new"));
        i18NRepo = I18NRepo.getInstance();
        MenuItem newPlanMenuItem = new MenuItem(i18NRepo.getString("label.menu.new.plan"));
        newPlanMenuItem.setOnAction(e -> onElementClick(getAlicaFactory().createPlan().eClass()));
        getItems().add(newPlanMenuItem);
        MenuItem newPlanTypeMenuItem = new MenuItem(i18NRepo.getString("label.menu.new.plantype"));
        newPlanTypeMenuItem.setOnAction(e -> onElementClick(getAlicaFactory().createPlanType().eClass()));
        getItems().add(newPlanTypeMenuItem);
        MenuItem newBehaviourMenuItem = new MenuItem(i18NRepo.getString("label.menu.new.behaviour"));
        newBehaviourMenuItem.setOnAction(e -> onElementClick(getAlicaFactory().createBehaviour().eClass()));
        getItems().add(newBehaviourMenuItem);
        MenuItem newFolderMenuItem = new MenuItem(i18NRepo.getString("label.menu.new.folder"));
        newFolderMenuItem.setOnAction(e -> onElementClick(null));
        getItems().add(newFolderMenuItem);
    }

    protected File getHintFile() {
        return new File(ConfigurationManager.getInstance().getActiveWorkspace().getConfiguration().getPlansPath());
    }

    private void onElementClick(EClass resourceInstanceClass) {
        createFileDialog(getHintFile(), resourceInstanceClass);
    }

    public static CreateNewDialogController createFileDialog(File unwrappedFile, EClass planClass) {
        FXMLLoader fxmlLoader = new FXMLLoader(NewResourceMenu.class.getClassLoader().getResource("createNewDialog.fxml"));
        I18NRepo i18 = I18NRepo.getInstance();
        try {
            Parent rootOfDialog = fxmlLoader.load();
            CreateNewDialogController controller = fxmlLoader.getController();
            controller.setAlicaType(planClass);
            controller.setInitialDirectoryHint(unwrappedFile);
            Stage stage = new Stage();
            stage.setResizable(false);
            if (planClass != null) {
                stage.setTitle(i18.getString("label.menu.new") + " " +
                        i18.getInstance().getString("label.menu.new." + planClass.getName().toLowerCase()));
            } else {
                stage.setTitle(i18.getString("label.menu.new") + " " +
                        i18.getString("label.menu.new.folder"));
            }
            stage.setScene(new Scene(rootOfDialog));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(PlanDesigner.getPrimaryStage());
            stage.showAndWait();
            return controller;

        } catch (IOException e) {
            // if the helper window is not loadable something is really wrong here
            e.printStackTrace();
            System.exit(1);
            // unreachable statement
            return null;
        }
    }
}
