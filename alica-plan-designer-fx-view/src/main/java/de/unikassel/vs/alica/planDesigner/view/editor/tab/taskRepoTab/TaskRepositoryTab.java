package de.unikassel.vs.alica.planDesigner.view.editor.tab.taskRepoTab;

import de.unikassel.vs.alica.planDesigner.PlanDesignerApplication;
import de.unikassel.vs.alica.planDesigner.controller.ErrorWindowController;
import de.unikassel.vs.alica.planDesigner.controller.UsagesWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiChangeAttributeEvent;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.model.TaskRepositoryViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.TaskViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.IEditorTab;
import de.unikassel.vs.alica.planDesigner.view.menu.ShowUsagesMenuItem;
import de.unikassel.vs.alica.planDesigner.view.properties.PropertiesTable;
import de.unikassel.vs.alica.planDesigner.view.repo.RepositoryTab;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.LongStringConverter;

import java.io.IOException;
import java.util.ArrayList;

public class TaskRepositoryTab extends RepositoryTab implements IEditorTab {

    private TaskRepositoryViewModel taskRepository;
    private I18NRepo i18NRepo;
    private PropertiesTable<ViewModelElement> propertiesTable;

    public TaskRepositoryTab(TaskRepositoryViewModel taskRepository, IGuiModificationHandler handler) {
        super(I18NRepo.getInstance().getString("label.caption.taskrepository"), null);
        this.guiModificationHandler = handler;
        this.taskRepository = taskRepository;
        this.addElements(taskRepository.getTaskViewModels());

        i18NRepo = I18NRepo.getInstance();
        setText(I18NRepo.getInstance().getString("label.caption.taskrepository") + ": " + this.taskRepository.getName());
        initGui();
        setOnCloseRequest(e-> {
            if (taskRepository.getDirty()) {
                ErrorWindowController.createErrorWindow(i18NRepo.getString("label.error.close.task"), null);
                e.consume();
            }
        });
    }


    /**
     * Modifies the content, as it is already set in the RepositoryTab constructor (see base class).
     * This allows to create new Tasks, too.
     */
    private void initGui() {
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
        createTaskButton.setText(i18NRepo.getInstance().getString("action.create.task"));
        TextField taskNameField = new TextField();
        createTaskButton.setOnAction(e -> {
            if (taskNameField.getText() != null && !taskNameField.getText().isEmpty()) {
                GuiModificationEvent event = new GuiModificationEvent(GuiEventType.CREATE_ELEMENT, Types.TASK, taskNameField.getText());
                event.setParentId(this.taskRepository.getId());
                guiModificationHandler.handle(event);
            }
        });
        createTaskHBox.getChildren().addAll(taskNameField, createTaskButton);

        TitledPane taskList = new TitledPane();
        taskList.setContent(repositoryListView);
        taskList.setText(i18NRepo.getString("label.caption.tasks"));
        taskList.setCollapsible(false);
        taskList.setPadding(new Insets(10,0,0,0));
        taskList.setStyle("-fx-font-weight: bold;");
        repositoryListView.setStyle("-fx-font-weight: normal;");

        // create list of tasks
        VBox contentContainer = new VBox();
        contentContainer.setPrefHeight(Double.MAX_VALUE);

        // fill all into global container
        contentContainer.getChildren().addAll(propertiesTable, taskList, createTaskHBox);

        // override base class guiModificationHandler content
        setContent(contentContainer);

        // register listener on view model
        taskRepository.nameProperty().addListener((observable, oldValue, newValue) -> {
            fireGuiChangeAttributeEvent(newValue, "name");
            setText(i18NRepo.getString("label.caption.taskrepository") + ": " + newValue);
        });
        taskRepository.commentProperty().addListener((observable, oldValue, newValue) -> {
            fireGuiChangeAttributeEvent(newValue, "comment");
        });
        taskRepository.getTaskViewModels().addListener(new ListChangeListener<TaskViewModel>() {
            @Override
            public void onChanged(Change<? extends TaskViewModel> c) {
                while(c.next()) {
                    if (c.wasAdded()) {
                        for (TaskViewModel task : c.getAddedSubList()) {
                            addElement(task);
                        }
                    }

                    if (c.wasRemoved()) {
                        for (TaskViewModel task : c.getRemoved()) {
                            removeElement(task);
                        }
                    }
                }
            }
        });
        taskRepository.dirtyProperty().addListener((observable, oldValue, newValue) -> {
            this.setDirty(newValue);
        });
    }

    private void fireGuiChangeAttributeEvent(String newValue, String attribute) {
        GuiChangeAttributeEvent guiChangeAttributeEvent = new GuiChangeAttributeEvent(GuiEventType.CHANGE_ELEMENT, Types.TASKREPOSITORY, taskRepository.getName());
        guiChangeAttributeEvent.setNewValue(newValue);
        guiChangeAttributeEvent.setAttributeType(String.class.getSimpleName());
        guiChangeAttributeEvent.setAttributeName(attribute);
        guiChangeAttributeEvent.setElementId(taskRepository.getId());
        guiModificationHandler.handle(guiChangeAttributeEvent);
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
    public TaskRepositoryViewModel getPresentedViewModelElement() {
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
        GuiModificationEvent event = new GuiModificationEvent(GuiEventType.SAVE_ELEMENT, Types.TASKREPOSITORY, taskRepository.getName());
        event.setElementId(taskRepository.getId());
        guiModificationHandler.handle(event);
    }

    public void setDirty(boolean dirty) {
        if (!getText().contains("*") && dirty) {
            this.setText(getText() + "*");
        } else if (getText().contains("*") && !dirty) {
            this.setText(getText().substring(0, getText().length()-1));
        }
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
}
