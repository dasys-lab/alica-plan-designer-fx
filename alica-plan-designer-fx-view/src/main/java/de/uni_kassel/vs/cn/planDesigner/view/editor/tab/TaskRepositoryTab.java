package de.uni_kassel.vs.cn.planDesigner.view.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.events.GuiEventType;
import de.uni_kassel.vs.cn.planDesigner.events.GuiModificationEvent;
import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.Types;
import de.uni_kassel.vs.cn.planDesigner.view.filebrowser.TreeViewModelElement;
import de.uni_kassel.vs.cn.planDesigner.view.repo.RepositoryTab;
import de.uni_kassel.vs.cn.planDesigner.view.repo.ViewModelElement;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TaskRepositoryTab extends RepositoryTab implements IEditorTab {

    private TreeViewModelElement taskRepository;

    public TaskRepositoryTab(TreeViewModelElement taskRepository) {
        super(taskRepository.getName(), null);
        this.taskRepository = taskRepository;
        initGui();
    }

    /**
     * Modifies the content, as it is already set in the RepositoryTab constructor (see base class).
     * This allows to create new Tasks, too.
     */
    public void initGui() {
        // create list of tasks
        VBox contentContainer = new VBox();

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

        // fill both into global container
        contentContainer.getChildren().addAll(repositoryListView, createTaskHBox);

        // override base class guiModificationHandler content
        setContent(contentContainer);
    }

    @Override
    public ViewModelElement getViewModelElement() {
        return taskRepository;
    }

}
