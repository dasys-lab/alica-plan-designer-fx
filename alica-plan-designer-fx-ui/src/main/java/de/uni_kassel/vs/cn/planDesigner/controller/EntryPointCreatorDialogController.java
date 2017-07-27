package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.add.AddEntryPointInPlan;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.add.AddTaskToRepository;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.change.ChangePosition;
import de.uni_kassel.vs.cn.planDesigner.alica.Task;
import de.uni_kassel.vs.cn.planDesigner.alica.util.AllAlicaFiles;
import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;
import de.uni_kassel.vs.cn.planDesigner.common.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.PlanTab;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by marci on 24.02.17.
 */
public class EntryPointCreatorDialogController implements Initializable {
    @FXML
    private TextField newTaskNameTextField;

    @FXML
    private Button createTaskButton;

    @FXML
    private Button confirmTaskChoiceButton;

    @FXML
    private ComboBox<Task> taskComboBox;

    private int x = 0;
    private int y = 0;
    boolean createEntryPoint = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createTaskButton.setText(I18NRepo.getString("action.create.task"));
        createTaskButton.setOnAction(e -> createTask());
        confirmTaskChoiceButton.setText(I18NRepo.getString("action.confirm"));
        confirmTaskChoiceButton.setOnAction(e -> {
            Stage window = (Stage) confirmTaskChoiceButton.getScene().getWindow();
            Task selectedItem = taskComboBox.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                createNewEntryPoint(selectedItem);
                window.close();
            }
        });

        taskComboBox.setItems(FXCollections.observableArrayList(AllAlicaFiles.getInstance().getTasks().getKey()));
        taskComboBox.setButtonCell(new ListCell<Task>() {
            @Override
            protected void updateItem(Task item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getName());
                } else {
                    setText(null);
                }
            }
        });
        taskComboBox.setCellFactory(new Callback<ListView<Task>, ListCell<Task>>() {
            @Override
            public ListCell<Task> call(ListView<Task> param) {
                return new ListCell<Task>() {
                    @Override
                    protected void updateItem(Task item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getName());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });

        taskComboBox.onShownProperty().addListener((observable, oldValue, newValue) -> {
            if (createEntryPoint == false) {
                taskComboBox.setVisible(false);
                confirmTaskChoiceButton.setVisible(false);
                Stage window = (Stage) newTaskNameTextField.getScene().getWindow();
                window.setTitle(I18NRepo.getString("action.create.task"));
                createTaskButton.setOnAction(e -> {
                    createTask();
                    window.close();
                });
            }
        });

    }

    private void createTask() {
        if (newTaskNameTextField.getText() != null && newTaskNameTextField.getText().isEmpty() == false) {
            MainController
                    .getInstance()
                    .getCommandStack()
                    .storeAndExecute(new AddTaskToRepository(AllAlicaFiles.getInstance()
                            .getTaskRepository().get(0).getKey(), newTaskNameTextField.getText()));
            try {
                EMFModelUtils.saveAlicaFile(AllAlicaFiles.getInstance().getTaskRepository().get(0).getKey());
            } catch (IOException e) {
                ErrorWindowController.createErrorWindow(I18NRepo.getString("label.error.save"), e);
                e.printStackTrace();
            }
            taskComboBox.setItems(FXCollections.observableArrayList(AllAlicaFiles.getInstance().getTasks().getKey()));
            newTaskNameTextField.setText("");
        }
    }

    private void createNewEntryPoint(Task selectedItem) {
        PlanTab selectedPlanTab = (PlanTab) MainController.getInstance().getEditorTabPane().getSelectionModel().getSelectedItem();
        AddEntryPointInPlan command = new AddEntryPointInPlan((selectedPlanTab.getPlanEditorPane().getPlanModelVisualisationObject()));
        MainController.getInstance()
                .getCommandStack()
                .storeAndExecute(command);
        command.getElementToEdit().setTask(selectedItem);
        MainController.getInstance()
                .getCommandStack()
                .storeAndExecute(new ChangePosition(command.getNewlyCreatedPmlUiExtension(), command.getElementToEdit(),
                        x, y,selectedPlanTab.getEditable()));
    }

    public void setX(int x) {
        this.x = x;
        createEntryPoint = true;
    }

    public void setY(int y) {
        this.y = y;
        createEntryPoint = true;
    }
}
