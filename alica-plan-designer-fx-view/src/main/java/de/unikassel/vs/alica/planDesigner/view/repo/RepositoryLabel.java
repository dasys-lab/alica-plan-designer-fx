package de.unikassel.vs.alica.planDesigner.view.repo;

import de.unikassel.vs.alica.planDesigner.controller.ErrorWindowController;
import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.editor.container.EntryPointContainer;
import de.unikassel.vs.alica.planDesigner.view.editor.container.FailureStateContainer;
import de.unikassel.vs.alica.planDesigner.view.editor.container.StateContainer;
import de.unikassel.vs.alica.planDesigner.view.editor.container.SuccessStateContainer;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaIcon;
import de.unikassel.vs.alica.planDesigner.view.menu.DeleteElementMenuItem;
import de.unikassel.vs.alica.planDesigner.view.menu.RenameElementMenuItem;
import de.unikassel.vs.alica.planDesigner.view.menu.ShowUsagesMenuItem;
import de.unikassel.vs.alica.planDesigner.view.model.SerializableViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.TaskViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.PickResult;
import javafx.scene.shape.Circle;

public class RepositoryLabel extends Label {

    protected IGuiModificationHandler guiModificationHandler;
    protected ViewModelElement viewModelElement;

    public RepositoryLabel(ViewModelElement viewModelElement, IGuiModificationHandler guiModificationHandler) {
        this.guiModificationHandler = guiModificationHandler;
        this.viewModelElement = viewModelElement;
        setGraphic(this.viewModelElement.getType());
        setText(this.viewModelElement.getName());

        this.viewModelElement.nameProperty().addListener((observable, oldValue, newValue) -> {
            setText(newValue);
        });

        this.viewModelElement.typeProperty().addListener((observable, oldValue, newValue) -> {
            setGraphic(newValue);
        });

        // right click for opening context menu with option to show usage of model element
        setOnContextMenuRequested(e -> {
            RenameElementMenuItem renameFileMenuItem = new RenameElementMenuItem(this.viewModelElement, guiModificationHandler);
            ShowUsagesMenuItem usageMenu = new ShowUsagesMenuItem(this.viewModelElement, guiModificationHandler);
            DeleteElementMenuItem deleteMenu = new DeleteElementMenuItem(this.viewModelElement, guiModificationHandler);
            ContextMenu contextMenu = new ContextMenu(renameFileMenuItem, usageMenu, deleteMenu);
            contextMenu.show(RepositoryLabel.this, e.getScreenX(), e.getScreenY());
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
                    throw new RuntimeException("RepositoryLabel: Unkown ViewModelElement type " + viewModelElement.getType() + " for opening tab!");
                }
                e.consume();
            }
        });

        // set the onDragObjectImage to cursor
        setOnDragDetected(e -> {
            RepositoryLabel repositoryLabel = (RepositoryLabel) e.getSource();
            ImageCursor cursor = new ImageCursor(new AlicaIcon(viewModelElement.getType(), AlicaIcon.Size.SMALL));
            getScene().setCursor(cursor);
         });

        //Drag from RepositoryList to add a AbstractPlan to State or Task to EntryPoint
        setOnMouseReleased(e ->{
            getScene().setCursor(Cursor.DEFAULT);
            PickResult pickResult = e.getPickResult();
            Parent parent = pickResult.getIntersectedNode().getParent();

            if(parent instanceof FailureStateContainer || parent instanceof SuccessStateContainer) { return; }

            if(pickResult.getIntersectedNode() instanceof Circle) {
                try {
                    if (parent instanceof StateContainer) {
                        if (viewModelElement instanceof TaskViewModel) { return; }
                        StateContainer stateContainer = (StateContainer) parent;
                        GuiModificationEvent guiModificationEvent = new GuiModificationEvent(GuiEventType.ADD_ELEMENT,viewModelElement.getType(), viewModelElement.getName());
                        guiModificationEvent.setParentId(stateContainer.getState().getId());
                        guiModificationEvent.setElementId(viewModelElement.getId());
                        guiModificationHandler.handle(guiModificationEvent);
                    }
                    if (parent instanceof EntryPointContainer && viewModelElement instanceof TaskViewModel) {
                        EntryPointContainer entryPointContainer = (EntryPointContainer) parent;
                        GuiModificationEvent guiModificationEvent = new GuiModificationEvent(GuiEventType.ADD_ELEMENT, viewModelElement.getType(), viewModelElement.getName());
                        guiModificationEvent.setParentId(entryPointContainer.getViewModelElement().getId());
                        guiModificationEvent.setElementId(viewModelElement.getId());
                        guiModificationHandler.handle(guiModificationEvent);
                    }
                }catch (RuntimeException excp){
                    // Exception might get thrown, because the element can't be added, because this would cause a loop
                    // in the model
                    ErrorWindowController.createErrorWindow(excp.getMessage(), null);
                }
            }
            e.consume();
        });
    }

    public void setGraphic(String iconName) {
        this.setGraphic(new ImageView(new AlicaIcon(iconName, AlicaIcon.Size.SMALL)));
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