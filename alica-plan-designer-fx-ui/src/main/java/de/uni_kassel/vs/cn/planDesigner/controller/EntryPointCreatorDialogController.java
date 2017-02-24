package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.planDesigner.PlanDesigner;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.add.AddEntryPointInPlan;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.add.AddTaskToRepository;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.change.ChangePosition;
import de.uni_kassel.vs.cn.planDesigner.alica.Task;
import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;
import de.uni_kassel.vs.cn.planDesigner.common.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.PlanTab;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

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

    private int x;
    private int y;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createTaskButton.setText(I18NRepo.getString("action.create.task"));
        createTaskButton.setOnAction(e -> {
            if (newTaskNameTextField.getText() != null && newTaskNameTextField.getText().isEmpty() == false) {
                MainController
                        .getInstance()
                        .getCommandStack()
                        .storeAndExecute(new AddTaskToRepository(PlanDesigner.allAlicaFiles
                                .getTaskRepository().get(0).getKey(), newTaskNameTextField.getText()));
                taskComboBox.setItems(FXCollections.observableArrayList(PlanDesigner.allAlicaFiles.getTasks().getKey()));
                newTaskNameTextField.setText("");
            }
        });
        confirmTaskChoiceButton.setText(I18NRepo.getString("action.confirm"));
        confirmTaskChoiceButton.setOnAction(e -> {
            Stage window = (Stage) confirmTaskChoiceButton.getScene().getWindow();
            Task selectedItem = taskComboBox.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                createNewEntryPoint(selectedItem);
                window.close();
            }
        });

        taskComboBox.setItems(FXCollections.observableArrayList(PlanDesigner.allAlicaFiles.getTasks().getKey()));
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
                final ListCell<Task> listCell = new ListCell<Task>() {
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
                return listCell;
            }
        });

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
                        x, y));
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
