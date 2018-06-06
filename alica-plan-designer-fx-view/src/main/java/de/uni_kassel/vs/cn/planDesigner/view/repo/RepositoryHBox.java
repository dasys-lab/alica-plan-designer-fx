package de.uni_kassel.vs.cn.planDesigner.view.repo;

import de.uni_kassel.vs.cn.planDesigner.view.editor.tools.DraggableHBox;
import de.uni_kassel.vs.cn.planDesigner.view.menu.IShowUsageHandler;
import de.uni_kassel.vs.cn.planDesigner.view.menu.ShowUsagesMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.MouseButton;

public class RepositoryHBox extends DraggableHBox {

    protected IShowUsageHandler showUsageHandler;
    protected ViewModelElement viewModelElement;

    public RepositoryHBox(ViewModelElement viewModelElement, IShowUsageHandler showUsageHandler) {
        this.showUsageHandler = showUsageHandler;
        this.viewModelElement = viewModelElement;
        setIcon(this.viewModelElement.getType());
        setText(this.viewModelElement.getName());

        // right click for opening context menu with option to show usage of model element
        setOnContextMenuRequested(e -> {
            ContextMenu contextMenu = new ContextMenu(new ShowUsagesMenuItem(this.viewModelElement, showUsageHandler));
            contextMenu.show(RepositoryHBox.this, e.getScreenX(), e.getScreenY());
        });

        // double click for open the corresponding file
        setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == 2) {
                //MainWindowController.getInstance().openFile(pathToObject.toFile());
                // TODO: Fire event for opening this element.
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

    public String getViewModelName() {
        return viewModelElement.getName();
    }
}
