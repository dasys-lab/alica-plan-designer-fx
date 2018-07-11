package de.uni_kassel.vs.cn.planDesigner.view.editor.tab.taskRepoTab;

import de.uni_kassel.vs.cn.planDesigner.PlanDesignerApplication;
import de.uni_kassel.vs.cn.planDesigner.controller.ErrorWindowController;
import de.uni_kassel.vs.cn.planDesigner.controller.UsagesWindowController;
import de.uni_kassel.vs.cn.planDesigner.events.GuiEventType;
import de.uni_kassel.vs.cn.planDesigner.events.GuiModificationEvent;
import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.Types;
import de.uni_kassel.vs.cn.planDesigner.view.model.PlanElementViewModel;
import de.uni_kassel.vs.cn.planDesigner.view.model.TaskRepositoryViewModel;
import de.uni_kassel.vs.cn.planDesigner.view.model.ViewModelElement;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.IEditorTab;
import de.uni_kassel.vs.cn.planDesigner.view.menu.ShowUsagesMenuItem;
import de.uni_kassel.vs.cn.planDesigner.view.properties.PropertiesTable;
import de.uni_kassel.vs.cn.planDesigner.view.repo.RepositoryTab;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LongStringConverter;

import java.io.IOException;
import java.util.ArrayList;

public class TaskRepositoryTab extends RepositoryTab implements IEditorTab {

    private ViewModelElement taskRepository;
    private I18NRepo i18NRepo;
    private boolean dirty;
    private PropertiesTable<ViewModelElement> propertiesTable;

    public TaskRepositoryTab(ViewModelElement taskRepository, IGuiModificationHandler handler) {
        super(I18NRepo.getInstance().getString("label.caption.taskrepository"), null);
        this.guiModificationHandler = handler;
        if (taskRepository.getType().equals(Types.TASKREPOSITORY)) {
            this.taskRepository = guiModificationHandler.getViewModelElement(taskRepository.getId());
        } else if (taskRepository.getType().equals(Types.TASK)){
            this.taskRepository = guiModificationHandler.getViewModelElement(taskRepository.getParentId());
        } else {
            System.err.println("TaskRepository: Creation of TaskRepositoryTab with ViewModelElement of quantifierType " + taskRepository.getType() + " not supported!");
        }
        i18NRepo = I18NRepo.getInstance();
        setText(I18NRepo.getInstance().getString("label.caption.taskrepository") + ": " + this.taskRepository.getName());
        initGui();
        setOnCloseRequest(e-> {
            if (getText().contains("*")) {
                ErrorWindowController.createErrorWindow(i18NRepo.getString("label.error.close.task"), null);
                e.consume();
            }
        });
    }

    /**
     * Modifies the content, as it is already set in the RepositoryTab constructor (see base class).
     * This allows to create new Tasks, too.
     */
    public void initGui() {
        propertiesTable = new PropertiesTable();
        propertiesTable.setEditable(true);
        propertiesTable.addColumn(i18NRepo.getString("label.column.name"), "name", new DefaultStringConverter(), true);
        propertiesTable.addColumn(i18NRepo.getString("label.column.id"), "id", new LongStringConverter(), false);
        propertiesTable.addColumn(i18NRepo.getString("label.column.comment"), "comment", new DefaultStringConverter(), true);
        propertiesTable.addColumn(i18NRepo.getString("label.column.relDir"), "relativeDirectory", new DefaultStringConverter(), false);
        propertiesTable.addItem(taskRepository);

        // guiModificationHandler for creating new tasks
        HBox createTaskHBox = new HBox();
        Button createTaskButton = new Button();
        createTaskButton.setText(I18NRepo.getInstance().getString("action.create.task"));
        TextField taskNameField = new TextField();
        createTaskButton.setOnAction(e -> {
            if (taskNameField.getText() != null && !taskNameField.getText().isEmpty()) {
                GuiModificationEvent event = new GuiModificationEvent(GuiEventType.CREATE_ELEMENT, Types.TASK, taskNameField.getText());
                event.setParentId(this.taskRepository.getId());
                guiModificationHandler.handle(event);
            }
        });
        createTaskHBox.getChildren().addAll(taskNameField, createTaskButton);

        HBox spacing = new HBox();
        spacing.setPrefHeight(10);
        // create list of tasks
        VBox contentContainer = new VBox();
        contentContainer.setPrefHeight(Double.MAX_VALUE);

        // fill all into global container
        contentContainer.getChildren().addAll(propertiesTable, spacing, repositoryListView, createTaskHBox);

        // override base class guiModificationHandler content
        setContent(contentContainer);
    }

    @Override
    public boolean representsViewModelElement(ViewModelElement viewModelElement) {
        if (viewModelElement.getType().equals(Types.TASK)) {
            return taskRepository.equals(this.guiModificationHandler.getViewModelElement(viewModelElement.getParentId()));
        } else {
            return taskRepository.equals(viewModelElement);
        }
    }

    @Override
    public ViewModelElement getPresentedViewModelElement() {
        return taskRepository;
    }

    @Override
    public GuiModificationEvent handleDelete() {
        ViewModelElement elementToDelete = this.getSelectedItem();
        if (elementToDelete == null) {
            return null;
        }

        if (!isTaskUsed(elementToDelete)) {
            GuiModificationEvent event = new GuiModificationEvent(GuiEventType.DELETE_ELEMENT, Types.TASK, elementToDelete.getName());
            event.setElementId(elementToDelete.getId());
            event.setParentId(taskRepository.getId());
            return event;
        } else {
            return null;
        }
    }

    @Override
    public void save() {
        if (getText().contains("*")) {
            GuiModificationEvent event = new GuiModificationEvent(GuiEventType.SAVE_ELEMENT, Types.TASKREPOSITORY, taskRepository.getName());
            event.setElementId(taskRepository.getId());
            guiModificationHandler.handle(event);
        }
    }

    public void setDirty(boolean dirty) {
        if (!getText().contains("*") && dirty) {
            this.setText(getText() + "*");
        } else if (getText().contains("*") && !dirty) {
            this.setText(getText().substring(0, getText().length()-1));
        }
        this.dirty = dirty;
    }

    private boolean isTaskUsed(ViewModelElement taskToBeDeleted) {
        ArrayList<ViewModelElement> usages = guiModificationHandler.getUsages(taskToBeDeleted);
        if (usages.isEmpty()) {
            return false;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(ShowUsagesMenuItem.class.getClassLoader().getResource("usagesWindow.fxml"));
        try {
            Parent infoWindow = fxmlLoader.load();
            UsagesWindowController controller = fxmlLoader.getController();
            controller.createReferencesList(usages, guiModificationHandler);
            Stage stage = new Stage();
            stage.setTitle(i18NRepo.getString("label.usage.nodelete"));
            stage.setScene(new Scene(infoWindow));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(PlanDesignerApplication.getPrimaryStage());
            stage.showAndWait();
        } catch (IOException ignored) {
        } finally {
            return true;
        }
    }

    public boolean isDirty() {return dirty;}

}
