package de.unikassel.vs.alica.planDesigner.controller;

import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.model.TaskViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class EntryPointCreatorDialogController implements Initializable {
    @FXML
    private TextField newTaskNameTextField;

    @FXML
    private Button createTaskButton;

    @FXML
    private Button confirmTaskChoiceButton;

    @FXML
    private ComboBox<ViewModelElement> taskComboBox;

    private AtomicReference<TaskViewModel> selectedTask;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        I18NRepo i18NRepo = I18NRepo.getInstance();

        createTaskButton.setText(i18NRepo.getString("action.create.task"));
        createTaskButton.setOnAction(e -> createTask());
        confirmTaskChoiceButton.setText(i18NRepo.getString("action.confirm"));


        ObservableList<ViewModelElement> tasks = MainWindowController.getInstance().getGuiModificationHandler().getRepoViewModel().getTasks();
        taskComboBox.setItems(tasks);
        taskComboBox.setConverter(new StringConverter<ViewModelElement>() {
            @Override
            public String toString(ViewModelElement object) {
                return object.getName();
            }

            @Override
            public ViewModelElement fromString(String string) {
                return null;
            }
        });

        confirmTaskChoiceButton.setOnAction(e -> {
            selectedTask.set((TaskViewModel) taskComboBox.getSelectionModel().getSelectedItem());
            ((Stage) confirmTaskChoiceButton.getScene().getWindow()).close();
        });

        createTaskButton.setOnAction(e -> createTask());

    }

    public void setSelectedTaskReference(AtomicReference<TaskViewModel> taskReference){
        this.selectedTask = taskReference;
    }

    private void createTask() {
        if (newTaskNameTextField.getText() != null && !newTaskNameTextField.getText().isEmpty()) {
            GuiModificationEvent event = new GuiModificationEvent(GuiEventType.CREATE_ELEMENT, Types.TASK, newTaskNameTextField.getText());
            event.setParentId(MainWindowController.getInstance().getGuiModificationHandler().getRepoViewModel().getTasks().get(0).getParentId());
            MainWindowController.getInstance().getGuiModificationHandler().handle(event);
        }
    }
}