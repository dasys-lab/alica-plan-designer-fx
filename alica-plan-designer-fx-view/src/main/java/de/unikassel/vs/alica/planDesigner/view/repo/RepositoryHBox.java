package de.unikassel.vs.alica.planDesigner.view.repo;

import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEventExpanded;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.editor.container.EntryPointContainer;
import de.unikassel.vs.alica.planDesigner.view.editor.container.StateContainer;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.DraggableHBox;
import de.unikassel.vs.alica.planDesigner.view.menu.DeleteElementMenuItem;
import de.unikassel.vs.alica.planDesigner.view.menu.RenameElementMenuItem;
import de.unikassel.vs.alica.planDesigner.view.menu.ShowUsagesMenuItem;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import de.unikassel.vs.alica.planDesigner.view.model.SerializableViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.TaskViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.MouseButton;
import javafx.scene.input.PickResult;
import javafx.scene.shape.Circle;

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

        // set the onDragObjectImage to cursor
        setOnDragDetected(e -> {
            RepositoryHBox repositoryHBox = (RepositoryHBox) e.getSource();
            ImageCursor cursor = new ImageCursor(repositoryHBox.icon.getImage());
            getScene().setCursor(cursor);
         });

        setOnMouseReleased(e ->{
            getScene().setCursor(Cursor.DEFAULT);
            PickResult pickResult = e.getPickResult();
            Parent parent = pickResult.getIntersectedNode().getParent();
            if(pickResult.getIntersectedNode() instanceof Circle) {
                if(parent instanceof StateContainer) {
                    if(viewModelElement instanceof TaskViewModel) { return; }

                    StateContainer stateContainer = (StateContainer) parent;
                    GuiModificationEventExpanded guiModificationEventExpanded = new GuiModificationEventExpanded(GuiEventType.ADD_ELEMENT, viewModelElement.getType(), viewModelElement.getName(),stateContainer.getModelElement().getId());
                    guiModificationEventExpanded.setParentId(stateContainer.getState().getParentId());
                    guiModificationEventExpanded.setElementId(viewModelElement.getId());
                    guiModificationHandler.handle(guiModificationEventExpanded);
                }
                if(parent instanceof EntryPointContainer && viewModelElement instanceof TaskViewModel) {
                    EntryPointContainer entryPointContainer = (EntryPointContainer) parent;
                    GuiModificationEventExpanded guiModificationEventExpanded = new GuiModificationEventExpanded(GuiEventType.ADD_ELEMENT, viewModelElement.getType(), viewModelElement.getName(),entryPointContainer.getModelElement().getId());
                    guiModificationEventExpanded.setParentId(entryPointContainer.getModelElement().getParentId());
                    guiModificationEventExpanded.setElementId(viewModelElement.getId());
                    guiModificationHandler.handle(guiModificationEventExpanded);
                }
            }
            e.consume();
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