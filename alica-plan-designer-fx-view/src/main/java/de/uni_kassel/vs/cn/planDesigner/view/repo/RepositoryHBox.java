package de.uni_kassel.vs.cn.planDesigner.view.repo;

import de.uni_kassel.vs.cn.planDesigner.view.model.ViewModelElement;
import de.uni_kassel.vs.cn.planDesigner.controller.MainWindowController;
import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tools.DraggableHBox;
import de.uni_kassel.vs.cn.planDesigner.view.menu.DeleteElementMenuItem;
import de.uni_kassel.vs.cn.planDesigner.view.menu.ShowUsagesMenuItem;
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

        // right click for opening context menu with option to show usage of model element
        setOnContextMenuRequested(e -> {
            ShowUsagesMenuItem usageMenu = new ShowUsagesMenuItem(this.viewModelElement, guiModificationHandler);
            DeleteElementMenuItem deleteMenu = new DeleteElementMenuItem(this.viewModelElement, guiModificationHandler);
            ContextMenu contextMenu = new ContextMenu(usageMenu, deleteMenu);
            contextMenu.show(RepositoryHBox.this, e.getScreenX(), e.getScreenY());
            e.consume();
        });

        // double click for open the corresponding file
        setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == 2) {
                MainWindowController.getInstance().openFile(viewModelElement);
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
