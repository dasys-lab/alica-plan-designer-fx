package de.unikassel.vs.alica.planDesigner.controller;

import de.unikassel.vs.alica.planDesigner.PlanDesignerApplication;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.AbstractPlanTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.taskRepoTab.TaskRepositoryTab;
import de.unikassel.vs.alica.planDesigner.view.model.SerializableViewModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class IsDirtyWindowController {

    /*
        Creates a modal for a Tab selected as dirty. Allows the user to save the content of the tab, cancel
        the close event, or to close the tab
     */
    public static void createIsDirtyWindow(AbstractPlanTab tab, Event event) {
        I18NRepo i18NRepo = I18NRepo.getInstance();
        Stage stage = init(event);
        Button saveBtn = new Button(i18NRepo.getString("action.save"));
        saveBtn.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent arg0) {
                tab.save();
                stage.close();
            }

        });
        Button cancelBtn = new Button(i18NRepo.getString("action.cancel"));
        cancelBtn.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent arg0) {
                event.consume();
                stage.close();
            }

        });
        Button closeBtn = new Button(i18NRepo.getString("action.close"));
        closeBtn.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent arg0) {
                tab.revertChanges();
                stage.close();
            }

        });

        HBox buttons = new HBox();
        buttons.getChildren().addAll(saveBtn, cancelBtn, closeBtn);

        createAndShowModal(stage, new Label(i18NRepo.getString("label.error.closeDirtyTab")), buttons);
    }

    /**
     *  Creates a modal for closing the PlanDesigner. Allows to close the PlanDesigner or the cancel the event.
     **/
    public static void createMultipleTabsDirtyWindow(Event event) {
        I18NRepo i18NRepo = I18NRepo.getInstance();
        Stage stage = init(event);
        Button cancelBtn = new Button(i18NRepo.getString("action.cancel"));
        cancelBtn.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent arg0) {
                event.consume();
                stage.close();
            }

        });
        Button closeBtn = new Button(i18NRepo.getString("action.closeAnyways"));
        closeBtn.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent arg0) {
                stage.close();
                PlanDesignerApplication.setRunning(false);
                PlanDesignerApplication.getPrimaryStage().close();
            }

        });

        HBox buttons = new HBox();
        buttons.getChildren().addAll(cancelBtn, closeBtn);
        createAndShowModal(stage, new Label(i18NRepo.getString("label.error.closePlanDesigner")), buttons);
    }

    public static boolean isAnyTabDirty() {
        ObservableList<Tab> openTabs = MainWindowController.getInstance().getEditorTabPane().getTabs();
        for(Tab openTab : openTabs) {
            if(openTab instanceof  TaskRepositoryTab) {
                if (((SerializableViewModel) ((TaskRepositoryTab) openTab).getViewModelElement()).getDirty()) {
                    return true;
                }
            } else if(openTab instanceof  AbstractPlanTab) {
                if (((SerializableViewModel) ((AbstractPlanTab) openTab).getViewModelElement()).getDirty() || ((AbstractPlanTab) openTab).isDirty()) {
                    return true;
                }
            }
        }
        return false;
    }

    private static Stage init(Event event) {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        Stage primaryStage = PlanDesignerApplication.getPrimaryStage();
        stage.initOwner(primaryStage);
        stage.setTitle("Warning");
        // Relocate the pop-up Stage
        stage.setOnShown(e -> {
            stage.setX(primaryStage.getX() + primaryStage.getWidth() / 2.0 - stage.getWidth() / 2.0);
            stage.setY(primaryStage.getY() + primaryStage.getHeight() / 2.0 - stage.getHeight() / 2.0);
            stage.show();
        });
        stage.setOnCloseRequest(e ->{
            event.consume();
            stage.close();
        });
        return stage;
    }

    private static void createAndShowModal(Stage stage, Label errorLabel, HBox buttons) {
        errorLabel.setAlignment(Pos.CENTER);
        buttons.setSpacing(30);
        buttons.setAlignment(Pos.BOTTOM_CENTER);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(errorLabel, buttons);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(50);
        stage.setScene(new Scene(vBox, 300, 200));
        stage.showAndWait();
    }

}
