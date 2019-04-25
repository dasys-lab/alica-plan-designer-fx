package de.unikassel.vs.alica.planDesigner.view.editor.tab.roleTab;

import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaIcon;
import de.unikassel.vs.alica.planDesigner.view.menu.DeleteElementMenuItem;
import de.unikassel.vs.alica.planDesigner.view.menu.RenameElementMenuItem;
import de.unikassel.vs.alica.planDesigner.view.menu.ShowUsagesMenuItem;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import de.unikassel.vs.alica.planDesigner.view.repo.RepositoryLabel;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.stage.WindowEvent;

public class RoleListLabel extends Label {

    private RoleListView roleListView;
    protected final ViewModelElement viewModelElement;
    protected IGuiModificationHandler guiModificationHandler;

    public RoleListLabel(RoleListView roleListView, ViewModelElement viewModelElement, IGuiModificationHandler guiModificationHandler) {
        this.roleListView = roleListView;
        this.viewModelElement = viewModelElement;
        setGraphic(this.viewModelElement.getType());
        this.guiModificationHandler = guiModificationHandler;
        this.viewModelElement.nameProperty().addListener((observable, oldValue, newValue) -> setText(newValue));
        this.viewModelElement.typeProperty().addListener((observable, oldValue, newValue) -> setGraphic(newValue));

        RenameElementMenuItem renameFileMenuItem = new RenameElementMenuItem(this.viewModelElement, guiModificationHandler);
        ShowUsagesMenuItem usageMenu = new ShowUsagesMenuItem(this.viewModelElement, guiModificationHandler);
        DeleteElementMenuItem deleteMenu = new DeleteElementMenuItem(this.viewModelElement, guiModificationHandler);
        ContextMenu contextMenu = new ContextMenu(renameFileMenuItem, usageMenu, deleteMenu);
        this.setContextMenu(contextMenu);

        this.setOnContextMenuRequested(e -> {
            System.out.println("RLL:setOnContextMenuRequested");
            RoleListLabel.this.getContextMenu().show(RoleListLabel.this.roleListView, e.getScreenX(), e.getScreenY());
            e.consume();
        });
    }

    public void setGraphic(String iconName) {
        this.setGraphic(new ImageView(new AlicaIcon(iconName, AlicaIcon.Size.SMALL)));
    }

    public ViewModelElement getViewModelElement() {
        return viewModelElement;
    }

    public long getViewModelId() {
        return viewModelElement.getId();
    }

    public String getViewModelName() {
        return viewModelElement.getName();
    }
}
