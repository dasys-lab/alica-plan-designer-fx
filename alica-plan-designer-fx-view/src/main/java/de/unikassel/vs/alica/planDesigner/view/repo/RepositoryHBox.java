package de.unikassel.vs.alica.planDesigner.view.repo;

import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.DraggableHBox;
import de.unikassel.vs.alica.planDesigner.view.menu.DeleteElementMenuItem;
import de.unikassel.vs.alica.planDesigner.view.menu.RenameElementMenuItem;
import de.unikassel.vs.alica.planDesigner.view.menu.ShowUsagesMenuItem;
import de.unikassel.vs.alica.planDesigner.view.model.SerializableViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.TaskViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.MouseButton;

public class RepositoryHBox extends DraggableHBox {

    protected IGuiModificationHandler guiModificationHandler;
    protected ViewModelElement viewModelElement;

    public RepositoryHBox(ViewModelElement viewModelElement, IGuiModificationHandler guiModificationHandler) {
        this.guiModificationHandler = guiModificationHandler;
        this.viewModelElement = viewModelElement;
        setIcon(this.viewModelElement.getType());
        setText(this.viewModelElement.getName());

        this.viewModelElement.nameProperty().addListener((observable, oldValue, newValue) -> {
            setText(newValue);
        });

        this.viewModelElement.typeProperty().addListener((observable, oldValue, newValue) -> {
            setIcon(newValue);
        });

        // right click for opening context menu with option to show usage of model element
        setOnContextMenuRequested(e -> {
            RenameElementMenuItem renameFileMenuItem = new RenameElementMenuItem(this.viewModelElement, guiModificationHandler);
            ShowUsagesMenuItem usageMenu = new ShowUsagesMenuItem(this.viewModelElement, guiModificationHandler);
            DeleteElementMenuItem deleteMenu = new DeleteElementMenuItem(this.viewModelElement, guiModificationHandler);
            ContextMenu contextMenu = new ContextMenu(renameFileMenuItem, usageMenu, deleteMenu);
            contextMenu.show(RepositoryHBox.this, e.getScreenX(), e.getScreenY());
            e.consume();
        });

        // double click for open the corresponding file
        setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == 2) {
                if (viewModelElement instanceof SerializableViewModel) {
                    MainWindowController.getInstance().openFile((SerializableViewModel) viewModelElement);
                } else if (viewModelElement instanceof TaskViewModel) {
                    TaskViewModel taskViewModel = (TaskViewModel) viewModelElement;
                    MainWindowController.getInstance().openFile(taskViewModel.getTaskRepositoryViewModel());
                } else {
                    throw new RuntimeException("RepositoryHBox: Unkown ViewModelElement type " + viewModelElement.getType() + " for opening tab!");
                }
                e.consume();
            }
        });
    }

    public String getViewModelType() {
        return viewModelElement.getType();
    }

    public long getViewModelId() {
        return viewModelElement.getId();
    }

    public ViewModelElement getViewModelElement() {
        return viewModelElement;
    }

    public String getViewModelName() {
        return viewModelElement.getName();
    }
}
