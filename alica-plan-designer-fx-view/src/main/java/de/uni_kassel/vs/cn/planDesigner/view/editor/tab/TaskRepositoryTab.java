package de.uni_kassel.vs.cn.planDesigner.view.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.Types;
import de.uni_kassel.vs.cn.planDesigner.view.editor.container.AbstractPlanElementContainer;
import de.uni_kassel.vs.cn.planDesigner.view.filebrowser.TreeViewModelElement;
import de.uni_kassel.vs.cn.planDesigner.view.img.AlicaIcon;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class TaskRepositoryTab extends AbstractEditorTab {

    private ListView<TreeViewModelElement> taskListView;

    public TaskRepositoryTab(TreeViewModelElement taskRepository) {
        super(taskRepository);
        createContentView();
    }

    public void createContentView() {
        VBox contentContainer = new VBox();
        //TODO fire event to create content based on model
        taskListView = new ListView<TreeViewModelElement>();
        taskListView.getItems().sort(Comparator.comparing(task -> task.getName()));
        taskListView.setCellFactory(param -> new TaskListCell());
        taskListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            List<Pair<Long, AbstractPlanElementContainer>> selected = new ArrayList<>();
            selected.add(new Pair<Long,AbstractPlanElementContainer>(observable.getValue().getId(),
                    new AbstractPlanElementContainer(observable.getValue().getId(), null) {
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
                //TODO send event to add task
                createContentView();
            }
        });
        newTaskContainer.getChildren().addAll(taskNameField, createTaskButton);
        contentContainer.getChildren().addAll(taskListView, newTaskContainer);
        setContent(contentContainer);
    }

    public ListView<TreeViewModelElement> getTaskListView() {
        return taskListView;
    }

    private static class TaskListCell extends ListCell<TreeViewModelElement> {

        private static AlicaIcon image;

        public TaskListCell() {
            // only create icon once to save resources
            if (image == null) {
                image = new AlicaIcon(Types.TASK);
            }
            getChildren().add(new ImageView(image));
        }

        @Override
        protected void updateItem(TreeViewModelElement item, boolean empty) {
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
