package de.unikassel.vs.alica.planDesigner.view.editor.tab.taskRepoTab;

import de.unikassel.vs.alica.planDesigner.controller.UsagesWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.EditorTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.EditorTabPane;
import de.unikassel.vs.alica.planDesigner.view.model.SerializableViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.TaskRepositoryViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.TaskViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import de.unikassel.vs.alica.planDesigner.view.repo.RepositoryListView;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class TaskRepositoryTab extends EditorTab {

    protected TaskRepositoryViewModel taskRepositoryViewModel;
    protected RepositoryListView tasksRepoListView;

    public TaskRepositoryTab(SerializableViewModel serializableViewModel, EditorTabPane editorTabPane) {
        super(serializableViewModel, editorTabPane.getGuiModificationHandler());

        editorTabPane.getSelectionModel().selectedItemProperty().addListener((observable, selectedTabBefore, selectedTab) -> {
            if (this == selectedTab) {
                this.propertiesConditionsVariablesPane.setViewModelElement(taskRepositoryViewModel);
            }
        });
        editorTabPane.focusedProperty().addListener((observable, focusedBefore, focused) -> {
            if (focused && editorTabPane.getSelectionModel().getSelectedItem() == this) {
                this.propertiesConditionsVariablesPane.setViewModelElement(taskRepositoryViewModel);
            }
        });

        taskRepositoryViewModel = (TaskRepositoryViewModel) serializableViewModel;
        taskRepositoryViewModel.getTaskViewModels().addListener(new ListChangeListener<TaskViewModel>() {
            @Override
            public void onChanged(Change<? extends TaskViewModel> c) {
                while(c.next()) {
                    if (c.wasAdded()) {
                        for (TaskViewModel task : c.getAddedSubList()) {
                            tasksRepoListView.addElement(task);
                        }
                    }

                    if (c.wasRemoved()) {
                        for (TaskViewModel task : c.getRemoved()) {
                            tasksRepoListView.removeElement(task);
                        }
                    }
                }
            }
        });

        tasksRepoListView = new RepositoryListView();
        tasksRepoListView.setGuiModificationHandler(editorTabPane.getGuiModificationHandler());
        tasksRepoListView.addElements(taskRepositoryViewModel.getTaskViewModels());
        tasksRepoListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                this.propertiesConditionsVariablesPane.setViewModelElement(newValue.getViewModelElement());
            }
        });
        tasksRepoListView.focusedProperty().addListener((observable, focusedBefore, focused) -> {
            if (focused) {
                this.propertiesConditionsVariablesPane.setViewModelElement(tasksRepoListView.getSelectedItem());
            }
        });

        draw();
    }

    private void draw() {
        // create Button and Label for creating new Task
        HBox createTaskHBox = new HBox();
        Button createTaskButton = new Button();
        createTaskButton.setText(i18NRepo.getString("action.create.task"));
        TextField taskNameField = new TextField();
        createTaskButton.setOnAction(e -> {
            if (taskNameField.getText() != null && !taskNameField.getText().isEmpty()) {
                GuiModificationEvent event = new GuiModificationEvent(GuiEventType.CREATE_ELEMENT, Types.TASK, taskNameField.getText());
                event.setParentId(this.taskRepositoryViewModel.getId());
                guiModificationHandler.handle(event);
            }
        });
        createTaskHBox.getChildren().addAll(taskNameField, createTaskButton);

        TitledPane taskList = new TitledPane();
        taskList.setContent(tasksRepoListView);
        taskList.setText(i18NRepo.getString("label.caption.tasks"));
        taskList.setCollapsible(false);
        taskList.setPadding(new Insets(10,0,0,0));
        taskList.setStyle("-fx-font-weight: bold;");
        tasksRepoListView.setStyle("-fx-font-weight: normal;");
        tasksRepoListView.setPrefHeight(Double.MAX_VALUE);

        VBox tasksAndButtonVBox = new VBox();
        tasksAndButtonVBox.setPrefHeight(Double.MAX_VALUE);
        tasksAndButtonVBox.getChildren().addAll(taskList,createTaskHBox);

        splitPane.getItems().add(0, tasksAndButtonVBox);
    }

    public void save() {
        save(Types.TASKREPOSITORY);
    }

    public GuiModificationEvent handleDelete() {
        ViewModelElement elementToDelete = tasksRepoListView.getSelectedItem();
        if (elementToDelete == null) {
            return null;
        }

        if (!isTaskUsed(elementToDelete)) {
            GuiModificationEvent event = new GuiModificationEvent(GuiEventType.DELETE_ELEMENT, Types.TASK, elementToDelete.getName());
            event.setElementId(elementToDelete.getId());
            event.setParentId(taskRepositoryViewModel.getId());
            return event;
        } else {
            return null;
        }
    }

    private boolean isTaskUsed(ViewModelElement taskToBeDeleted) {
        ArrayList<ViewModelElement> usages = guiModificationHandler.getUsages(taskToBeDeleted);
        if (usages.isEmpty()) {
            return false;
        }
        UsagesWindowController.createUsagesWindow(usages, i18NRepo.getString("label.usage.nodelete"), guiModificationHandler);
        return true;
    }
}
