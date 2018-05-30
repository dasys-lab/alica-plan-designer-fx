package de.uni_kassel.vs.cn.planDesigner.view.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Task;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.TaskRepository;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.TaskImpl;
import de.uni_kassel.vs.cn.planDesigner.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.command.add.AddTaskToRepository;
import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.editor.container.AbstractPlanElementContainer;
import de.uni_kassel.vs.cn.planDesigner.view.img.AlicaIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;

import java.nio.file.Path;
import java.util.Comparator;

/**
 * Created by marci on 23.02.17.
 */
public class TaskRepositoryTab extends AbstractEditorTab<TaskRepository> {

    private final AdapterImpl taskRepoListener;
    private ListView<Task> taskListView;

    public TaskRepositoryTab(Pair<TaskRepository, Path> taskRepositoryPathPair, CommandStack commandStack) {
        super(taskRepositoryPathPair, commandStack);
        taskRepoListener = new AdapterImpl() {
            @Override
            public void notifyChanged(Notification msg) {
                super.notifyChanged(msg);
                createContentView();
            }
        };
        getEditable().eAdapters().add(taskRepoListener);

        onClosedProperty().addListener((observable, oldValue, newValue) -> {
            getEditable().eAdapters().remove(taskRepoListener);
        });
        createContentView();
    }

    public void createContentView() {
        VBox contentContainer = new VBox();
        taskListView = new ListView<Task>(FXCollections.observableArrayList(getEditable().getTasks()));
        taskListView.getItems().sort(Comparator.comparing(task -> task.getName()));
        taskListView.setCellFactory(param -> new TaskListCell());
        taskListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            List<Pair<PlanElement, AbstractPlanElementContainer>> selected =new ArrayList<>();
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
            selectedPlanElements.setValue(selected);
        });
        HBox newTaskContainer = new HBox();
        Button createTaskButton = new Button();
        createTaskButton.setText(I18NRepo.getInstance().getString("action.create.task"));
        TextField taskNameField = new TextField();
        createTaskButton.setOnAction(e -> {
            if (taskNameField.getText() != null && !taskNameField.getText().isEmpty()) {
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
                image = new AlicaIcon(TaskImpl.class.getSimpleName());
            }
            getChildren().add(new ImageView(image));
        }

        @Override
        protected void updateItem(Task item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setGraphic(new ImageView(image));
                setText(" " + item.getName());
            } else {
                setText(null);
            }
        }
    }
}
