package de.unikassel.vs.alica.planDesigner.view.menu;

import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.filebrowser.FileTreeItem;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;

public class CopyFileMenuItem extends MenuItem {
    private TreeCell toCopy;
    private IGuiModificationHandler guiModificationHandler;

    public CopyFileMenuItem() {
        super(I18NRepo.getInstance().getString("label.menu.edit.copy"));
        this.guiModificationHandler = MainWindowController.getInstance().getGuiModificationHandler();
        setOnAction(e -> onCopy());
    }
    public void onCopy() {
        ViewModelElement elementToCopy = ((FileTreeItem)toCopy.getTreeItem()).getViewModelElement();
        GuiModificationEvent event = new GuiModificationEvent(GuiEventType.COPY_ELEMENT, elementToCopy.getType(), elementToCopy.getName());
        event.setElementId(elementToCopy.getId());
        guiModificationHandler.handle(event);
    }


    public void setTreeCell(TreeCell toCopy) {
        this.toCopy = toCopy;
    }

}
