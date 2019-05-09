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

import java.io.File;

public class CopyFileMenuItem extends MenuItem {
    private TreeCell toCopy;
    private IGuiModificationHandler guiModificationHandler;
    private String copyName;

    public CopyFileMenuItem() {
        super(I18NRepo.getInstance().getString("label.menu.edit.copy"));
        this.guiModificationHandler = MainWindowController.getInstance().getGuiModificationHandler();
        setOnAction(e -> onCopy());
    }
    public void onCopy() {
        ViewModelElement elementToCopy = ((FileTreeItem)toCopy.getTreeItem()).getViewModelElement();
        copyName = elementToCopy.getName() +  "copy";
        GuiModificationEvent event = new GuiModificationEvent(GuiEventType.CREATE_ELEMENT, elementToCopy.getType(), copyName);
        event.setElementId(elementToCopy.getId());
        guiModificationHandler.handle(event);
        System.out.println(elementToCopy);
    }

    public void setTreeCell(TreeCell toDelete) {
        this.toCopy = toDelete;
    }

}
