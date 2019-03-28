package de.unikassel.vs.alica.planDesigner.view.editor.tab.roleTab;

import de.unikassel.vs.alica.planDesigner.controller.UsagesWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.EditorTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.EditorTabPane;
import de.unikassel.vs.alica.planDesigner.view.model.*;
import de.unikassel.vs.alica.planDesigner.view.repo.RepositoryListView;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class RoleSetTab extends EditorTab {

    protected RoleSetViewModel roleSetViewModel;
    protected RepositoryListView roleSetListView;

    public RoleSetTab(SerializableViewModel serializableViewModel, EditorTabPane editorTabPane) {
        super(serializableViewModel, editorTabPane.getGuiModificationHandler());

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
        roleSetViewModel.getRoleViewModels().addListener(new ListChangeListener<RoleViewModel>() {
            @Override
            public void onChanged(Change<? extends RoleViewModel> c) {
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
            }
        });

        roleSetListView = new RepositoryListView();
        roleSetListView.setGuiModificationHandler(editorTabPane.getGuiModificationHandler());
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
        // create Button and Label for creating new Task
        HBox createRoleHBox = new HBox();
        Button createRoleButton = new Button();
        createRoleButton.setText(i18NRepo.getString("action.create.role"));
        TextField taskNameField = new TextField();
        createRoleButton.setOnAction(e -> {
            if (taskNameField.getText() != null && !taskNameField.getText().isEmpty()) {
                GuiModificationEvent event = new GuiModificationEvent(GuiEventType.CREATE_ELEMENT, Types.ROLE, taskNameField.getText());
                event.setParentId(this.roleSetViewModel.getId());
                guiModificationHandler.handle(event);
            }
        });
        createRoleHBox.getChildren().addAll(taskNameField, createRoleButton);

        TitledPane roles = new TitledPane();
        roles.setContent(roleSetListView);
        roles.setText(i18NRepo.getString("label.caption.roles"));
        roles.setCollapsible(false);
        roles.setPadding(new Insets(10,0,0,0));
        roles.setStyle("-fx-font-weight: bold;");
        roleSetListView.setStyle("-fx-font-weight: normal;");
        roleSetListView.setPrefHeight(Double.MAX_VALUE);

        VBox rolesAndButtonVBox = new VBox();
        rolesAndButtonVBox.setPrefHeight(Double.MAX_VALUE);
        rolesAndButtonVBox.getChildren().addAll(roles, createRoleHBox);

        splitPane.getItems().add(0, rolesAndButtonVBox);
    }

    public void save() {
        save(Types.ROLESET);
    }

    public GuiModificationEvent handleDelete() {
        ViewModelElement elementToDelete = roleSetListView.getSelectedItem();
        if (elementToDelete == null) {
            return null;
        }

        if (!isRoleUsed(elementToDelete)) {
            GuiModificationEvent event = new GuiModificationEvent(GuiEventType.DELETE_ELEMENT, Types.ROLE, elementToDelete.getName());
            event.setElementId(elementToDelete.getId());
            event.setParentId(roleSetViewModel.getId());
            return event;
        } else {
            return null;
        }
    }

    private boolean isRoleUsed(ViewModelElement roleToBeDeleted) {
        ArrayList<ViewModelElement> usages = guiModificationHandler.getUsages(roleToBeDeleted);
        if (usages.isEmpty()) {
            return false;
        }
        UsagesWindowController.createUsagesWindow(usages, i18NRepo.getString("label.usage.nodelete"), guiModificationHandler);
        return true;
    }
}
