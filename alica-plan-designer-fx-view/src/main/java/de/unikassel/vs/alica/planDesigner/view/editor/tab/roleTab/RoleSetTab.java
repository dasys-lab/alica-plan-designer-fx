package de.unikassel.vs.alica.planDesigner.view.editor.tab.roleTab;

import de.unikassel.vs.alica.planDesigner.controller.UsagesWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.EditorTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.EditorTabPane;
import de.unikassel.vs.alica.planDesigner.view.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.ArrayList;

public class RoleSetTab extends EditorTab {

    protected RoleSetViewModel roleSetViewModel;
    protected RoleListView roleSetListView;
    protected TaskTableView taskTableView;

    private EditorTabPane editorTabPane;
    ObservableList<TaskPriority> taskPriorities = FXCollections.observableArrayList();

    public RoleSetTab(SerializableViewModel serializableViewModel, EditorTabPane editorTabPane) {
        super(serializableViewModel, editorTabPane.getGuiModificationHandler());
        this.editorTabPane = editorTabPane;
        editorTabPane.getSelectionModel().selectedItemProperty().addListener((observable, selectedTabBefore, selectedTab) -> {

            if (this == selectedTab) {
                this.elementInformationPane.setViewModelElement(roleSetViewModel);
            }
        });
        editorTabPane.focusedProperty().addListener((observable, focusedBefore, focused) -> {

            if (focused && editorTabPane.getSelectionModel().getSelectedItem() == this) {
                this.elementInformationPane.setViewModelElement(roleSetViewModel);
            }
        });
        roleSetViewModel = (RoleSetViewModel) serializableViewModel;
        roleSetViewModel.getRoleViewModels().addListener((ListChangeListener<RoleViewModel>) c -> {

            while(c.next()) {

                if (c.wasAdded()) {
                    for (RoleViewModel role : c.getAddedSubList()) {
                        roleSetListView.addElement(role);
                    }
                }

                if (c.wasRemoved()) {
                    for (RoleViewModel role : c.getRemoved()) {
                        roleSetListView.removeElement(role);
                    }
                }
            }
        });

        roleSetListView = new RoleListView();
        roleSetListView.addElements(roleSetViewModel.getRoleViewModels());
        roleSetListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue != null) {
                this.elementInformationPane.setViewModelElement(newValue.getViewModelElement());
            }
        });

        roleSetListView.focusedProperty().addListener((observable, focusedBefore, focused) -> {

            if (focused) {
                this.elementInformationPane.setViewModelElement(roleSetListView.getSelectedItem());
            }
        });
        draw();
    }

    private void draw() {
        HBox createRoleVisual = createRoleButtonVisual();
        TitledPane roleListVisual = createRoleListVisual();
        TaskTableView taskPriorityTableVisual = createTaskPriorityTableVisual();
        VBox roleSetVisual = new VBox();
        roleSetVisual.setPrefHeight(Double.MAX_VALUE);
        HBox roleTaskPriorityListVisual = new HBox();
        roleTaskPriorityListVisual.getChildren().addAll(roleListVisual, taskPriorityTableVisual);
        roleSetVisual.getChildren().addAll( roleTaskPriorityListVisual, createRoleVisual );
        splitPane.getItems().add(0, roleSetVisual);
    }

    private TitledPane createRoleListVisual() {
        TitledPane rolesPane = new TitledPane();
        rolesPane.setContent(roleSetListView);
        rolesPane.setText(i18NRepo.getString("label.caption.roles"));
        rolesPane.setCollapsible(false);
        rolesPane.setPadding(new Insets(0,0,0,0));
        rolesPane.setStyle("-fx-font-weight: bold;");
        roleSetListView.setStyle("-fx-font-weight: normal;");
        roleSetListView.setPrefHeight(Double.MAX_VALUE);
        rolesPane.prefHeightProperty().bind(splitPane.heightProperty());
        return rolesPane;
    }

    private TaskTableView createTaskPriorityTableVisual() {
        TableColumn<TaskViewModel, String> taskColumn = new TableColumn(i18NRepo.getString("label.caption.tasks"));
        taskColumn.setCellValueFactory(new PropertyValueFactory("task"));

        TableColumn<Float, Float> priorityColumn = new TableColumn(i18NRepo.getString("label.caption.priorities"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory("priority"));
        priorityColumn.setEditable(true);

        taskTableView = new TaskTableView(taskColumn, priorityColumn);
        taskTableView.prefHeightProperty().bind(splitPane.heightProperty());
        taskTableView.prefWidthProperty().bind(splitPane.widthProperty());
        taskTableView.setEditable(true);

        ObservableList<TaskViewModel> taskViewModels = roleSetViewModel.getTaskViewModels();

        for (TaskViewModel taskViewModel : taskViewModels) {
            taskPriorities.add( new TaskPriority(taskViewModel.getName(), "0.0"));
        }

        taskTableView.setItems(taskPriorities);


//        taskTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//
//            if (newValue != null) {
//                this.elementInformationPane.setViewModelElement(newValue.getViewModelElement());
//            }
//        });
//        taskTableView.focusedProperty().addListener((observable, focusedBefore, focused) -> {
//
//            if (focused) {
//                this.elementInformationPane.setViewModelElement(taskTableView.getSelectedItem());
//            }
//        });
        return taskTableView;
    }


    private HBox createRoleButtonVisual() {
        HBox createRoleHBox = new HBox();

        Button createButton = new Button();
        createButton.setText(i18NRepo.getString("action.create.role"));

        TextField textField = new TextField();
        createButton.setOnAction(e -> {

            if (textField.getText() != null && !textField.getText().isEmpty()) {
                GuiModificationEvent event = new GuiModificationEvent(GuiEventType.CREATE_ELEMENT, Types.ROLE, textField.getText());
                event.setParentId(this.roleSetViewModel.getId());
                guiModificationHandler.handle(event);
            }
        });
        createRoleHBox.getChildren().addAll(textField, createButton);
        return createRoleHBox;
    }

    public void save() {
        save(Types.ROLESET);
    }

    public GuiModificationEvent handleDelete() {
//        ViewModelElement elementToDelete = roleSetListView.getSelectedItem();
//        if (elementToDelete == null) {
//            return null;
//        }
//
//        if (!isRoleUsed(elementToDelete)) {
//            GuiModificationEvent event = new GuiModificationEvent(GuiEventType.DELETE_ELEMENT, Types.ROLE, elementToDelete.getName());
//            event.setElementId(elementToDelete.getId());
//            event.setParentId(roleSetViewModel.getId());
//            return event;
//        } else {
            return null;
//        }
    }

    private boolean isRoleUsed(ViewModelElement roleToBeDeleted) {
        ArrayList<ViewModelElement> usages = guiModificationHandler.getUsages(roleToBeDeleted);
        if (usages.isEmpty()) {
            return false;
        }
        UsagesWindowController.createUsagesWindow(usages, i18NRepo.getString("label.usage.nodelete"), guiModificationHandler);
        return true;
    }

    public class TaskPriority {

        private StringProperty task;
        private StringProperty priority;

        private TaskPriority(String task, String priority) {
            this.task = new SimpleStringProperty(task);
            this.priority = new SimpleStringProperty(priority);
        }

        public StringProperty priorityProperty() { return priority; }
        public StringProperty taskProperty() { return task; }
    }
}
