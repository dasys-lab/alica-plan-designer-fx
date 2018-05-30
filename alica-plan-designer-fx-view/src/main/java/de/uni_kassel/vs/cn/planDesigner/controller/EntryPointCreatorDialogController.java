package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.generator.EMFModelUtils;
import de.uni_kassel.vs.cn.generator.RepoViewBackend;
import de.uni_kassel.vs.cn.planDesigner.command.add.AddEntryPointInPlan;
import de.uni_kassel.vs.cn.planDesigner.command.add.AddTaskToRepository;
import de.uni_kassel.vs.cn.planDesigner.command.change.ChangePosition;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Task;
import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.PlanTab;
import de.uni_kassel.vs.cn.planDesigner.view.repo.RepositoryViewModel;
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
    private I18NRepo i18NRepo;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        i18NRepo = I18NRepo.getInstance();
        createTaskButton.setText(i18NRepo.getString("action.create.task"));
        createTaskButton.setOnAction(e -> createTask());
        confirmTaskChoiceButton.setText(i18NRepo.getString("action.confirm"));
        confirmTaskChoiceButton.setOnAction(e -> {
            Stage window = (Stage) confirmTaskChoiceButton.getScene().getWindow();
            Task selectedItem = taskComboBox.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                createNewEntryPoint(selectedItem);
                window.close();
            }
        });

        taskComboBox.setItems(FXCollections.observableArrayList(RepositoryViewModel.getInstance().getTasks().getKey()));
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
                window.setTitle(i18NRepo.getString("action.create.task"));
                createTaskButton.setOnAction(e -> {
                    createTask();
                    window.close();
                });
            }
        });

    }

    private void createTask() {
        if (newTaskNameTextField.getText() != null && newTaskNameTextField.getText().isEmpty() == false) {
            MainWindowController
                    .getInstance()
                    .getCommandStack()
                    .storeAndExecute(new AddTaskToRepository(RepositoryViewModel.getInstance()
                            .getTaskRepository().get(0).getKey(), newTaskNameTextField.getText()));
            try {
                EMFModelUtils.saveAlicaFile(RepositoryViewModel.getInstance().getTaskRepository().get(0).getKey());
            } catch (IOException e) {
                ErrorWindowController.createErrorWindow(i18NRepo.getString("label.error.save"), e);
                e.printStackTrace();
            }
            taskComboBox.setItems(FXCollections.observableArrayList(RepositoryViewModel.getInstance().getTasks().getKey()));
            newTaskNameTextField.setText("");
        }
    }

    private void createNewEntryPoint(Task selectedItem) {
        PlanTab selectedPlanTab = (PlanTab) MainWindowController.getInstance().getEditorTabPane().getSelectionModel().getSelectedItem();
        AddEntryPointInPlan command = new AddEntryPointInPlan((selectedPlanTab.getPlanEditorGroup().getPlanModelVisualisationObject()));
        MainWindowController.getInstance()
                .getCommandStack()
                .storeAndExecute(command);
        command.getElementToEdit().setTask(selectedItem);
        MainWindowController.getInstance()
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
