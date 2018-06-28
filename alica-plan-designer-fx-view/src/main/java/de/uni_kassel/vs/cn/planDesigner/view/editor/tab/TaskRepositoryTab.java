package de.uni_kassel.vs.cn.planDesigner.view.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.Types;
import de.uni_kassel.vs.cn.planDesigner.view.editor.container.AbstractPlanElementContainer;
import de.uni_kassel.vs.cn.planDesigner.view.filebrowser.TreeViewModelElement;
import de.uni_kassel.vs.cn.planDesigner.view.img.AlicaIcon;
import de.uni_kassel.vs.cn.planDesigner.view.repo.RepositoryTab;
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

public class TaskRepositoryTab extends RepositoryTab {

    public TaskRepositoryTab(TreeViewModelElement taskRepository) {
        super(taskRepository.getName(), null);
        initGui();
    }

    /**
     * Modifies the content, as it is already set in the RepositoryTab constructor (see base class).
     * This allows to create new Tasks, too.
     */
    public void initGui() {
        // create list of tasks
        VBox contentContainer = new VBox();

        // gui for creating new tasks
        HBox createTaskHBox = new HBox();
        Button createTaskButton = new Button();
        createTaskButton.setText(I18NRepo.getInstance().getString("action.create.task"));
        TextField taskNameField = new TextField();
        createTaskButton.setOnAction(e -> {
            if (taskNameField.getText() != null && !taskNameField.getText().isEmpty()) {
                //TODO send event to add task
                initGui();
            }
        });
        createTaskHBox.getChildren().addAll(taskNameField, createTaskButton);

        // fill both into global container
        contentContainer.getChildren().addAll(repositoryListView, createTaskHBox);

        // override base class gui content
        setContent(contentContainer);
    }
}
