package de.uni_kassel.vs.cn.planDesigner.ui.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.add.AddTaskToRepository;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.alica.Task;
import de.uni_kassel.vs.cn.planDesigner.alica.TaskRepository;
import de.uni_kassel.vs.cn.planDesigner.alica.impl.TaskImpl;
import de.uni_kassel.vs.cn.planDesigner.common.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.AbstractPlanElementContainer;
import de.uni_kassel.vs.cn.planDesigner.ui.img.AlicaIcon;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marci on 23.02.17.
 */
public class TaskRepositoryTab extends AbstractEditorTab<TaskRepository> {

    private ListView<Task> taskListView;

    public TaskRepositoryTab(Pair<TaskRepository, Path> taskRepositoryPathPair, CommandStack commandStack) {
        super(taskRepositoryPathPair, commandStack);
        createContentView();
    }

    public void createContentView() {
        VBox contentContainer = new VBox();
        taskListView = new ListView<Task>(FXCollections.observableArrayList(getEditable().getTasks()));
        taskListView.setCellFactory(param -> new TaskListCell());
        taskListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            List<Pair<PlanElement, AbstractPlanElementContainer>> selected = new ArrayList<>();
            selected.add(new Pair<>(observable.getValue(),
                    new AbstractPlanElementContainer<Task>(observable.getValue(), null, getCommandStack()) {
                        @Override
                        public void setupContainer() {

                        }

                        @Override
                        public Color getVisualisationColor() {
                            return null;
                        }
                    }));
            selectedPlanElement.setValue(selected);
        });
        HBox newTaskContainer = new HBox();
        Button createTaskButton = new Button();
        createTaskButton.setText(I18NRepo.getString("action.create.task"));
        TextField taskNameField = new TextField();
        createTaskButton.setOnAction(e -> {
            if (taskNameField.getText() != null && taskNameField.getText().isEmpty() == false) {
                getCommandStack().storeAndExecute(new AddTaskToRepository(getEditable(), taskNameField.getText()));
                createContentView();
            }
        });
        newTaskContainer.getChildren().addAll(taskNameField, createTaskButton);

        contentContainer.getChildren().addAll(taskListView, newTaskContainer);
        setContent(contentContainer);
    }

    public ListView<Task> getTaskListView() {
        return taskListView;
    }

    private static class TaskListCell extends ListCell<Task> {

        private static AlicaIcon image;

        public TaskListCell() {
            // only create icon once to save resources
            if (image == null) {
                image = new AlicaIcon(TaskImpl.class);
            }
            getChildren().add(new ImageView(image));
        }

        @Override
        protected void updateItem(Task item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item.getName());
            } else {
                setText(null);
            }
        }
    }
}
