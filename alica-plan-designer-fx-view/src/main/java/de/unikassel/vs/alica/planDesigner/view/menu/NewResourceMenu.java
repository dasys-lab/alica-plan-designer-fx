package de.unikassel.vs.alica.planDesigner.view.menu;

import de.unikassel.vs.alica.planDesigner.PlanDesignerApplication;
import de.unikassel.vs.alica.planDesigner.controller.CreateNewDialogController;
import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class NewResourceMenu extends Menu {

    private I18NRepo i18NRepo;
    private File initialDirectoryHint;
    private MenuItem newTaskRepositoryMenuItem;

    public NewResourceMenu(File initialDirectoryHint) {
        super(I18NRepo.getInstance().getString("label.menu.new"));
        this.initialDirectoryHint = initialDirectoryHint;
        i18NRepo = I18NRepo.getInstance();
        MenuItem newPlanMenuItem = new MenuItem(i18NRepo.getString("label.menu.new.plan"));
        newPlanMenuItem.setOnAction(e -> createFileDialog(this.initialDirectoryHint, Types.PLAN).showAndWait());
        getItems().add(newPlanMenuItem);
        MenuItem newPlanTypeMenuItem = new MenuItem(i18NRepo.getString("label.menu.new.plantype"));
        newPlanTypeMenuItem.setOnAction(e -> createFileDialog(this.initialDirectoryHint, Types.PLANTYPE).showAndWait());
        getItems().add(newPlanTypeMenuItem);
        MenuItem newBehaviourMenuItem = new MenuItem(i18NRepo.getString("label.menu.new.behaviour"));
        newBehaviourMenuItem.setOnAction(e -> createFileDialog(this.initialDirectoryHint, Types.BEHAVIOUR).showAndWait());
        getItems().add(newBehaviourMenuItem);
        MenuItem newRolesetMenuItem = new MenuItem(i18NRepo.getString("label.menu.new.roleset"));
        newRolesetMenuItem.setOnAction(e -> createFileDialog(this.initialDirectoryHint, Types.ROLESET).showAndWait());
        getItems().add(newRolesetMenuItem);
        newTaskRepositoryMenuItem = new MenuItem(i18NRepo.getString("label.menu.new.taskrepository"));
        newTaskRepositoryMenuItem.setOnAction(e -> createFileDialog(this.initialDirectoryHint, Types.TASKREPOSITORY).showAndWait());
        getItems().add(newTaskRepositoryMenuItem);
        MenuItem newFolderMenuItem = new MenuItem(i18NRepo.getString("label.menu.new.folder"));
        newFolderMenuItem.setOnAction(e -> createFileDialog(this.initialDirectoryHint, Types.FOLDER).showAndWait());
        getItems().add(newFolderMenuItem);
    }

    public void setInitialDirectoryHint(File initialDirectoryHint) {
        this.initialDirectoryHint = initialDirectoryHint;
    }

    public void showTaskRepositoryItem(boolean show) {
        newTaskRepositoryMenuItem.setDisable(!show);
    }

    public CreateNewDialogController createFileDialog(File initialDirectoryHint, String type) {
        FXMLLoader fxmlLoader = new FXMLLoader(NewResourceMenu.class.getClassLoader().getResource("createNewDialog.fxml"));

        try {
            Parent rootOfDialog = fxmlLoader.load();
            CreateNewDialogController createNewDialogController = fxmlLoader.getController();
            createNewDialogController.setGuiModificationHandler(MainWindowController.getInstance().getGuiModificationHandler());
            createNewDialogController.setType(type);
            createNewDialogController.setInitialDirectoryHint(initialDirectoryHint);
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle(i18NRepo.getString("label.menu.new") + " " + i18NRepo.getString("label.menu.new." + type));
            stage.setScene(new Scene(rootOfDialog));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(PlanDesignerApplication.getPrimaryStage());
            createNewDialogController.setStage(stage);
            return createNewDialogController;
        } catch (IOException e) {
            // if the helper window is not loadable something is really wrong here
            e.printStackTrace();
            PlanDesignerApplication.setRunning(false);
            return null;
        }
    }
}
