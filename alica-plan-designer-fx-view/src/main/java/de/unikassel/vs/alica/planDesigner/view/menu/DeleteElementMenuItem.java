package de.unikassel.vs.alica.planDesigner.view.menu;

import de.unikassel.vs.alica.planDesigner.controller.UsagesWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;

import java.util.ArrayList;

public class DeleteElementMenuItem extends MenuItem {

    private I18NRepo i18NRepo;
    private IGuiModificationHandler guiModificationHandler;
    private ViewModelElement elementToDelete;

    public DeleteElementMenuItem(ViewModelElement elementToDelete, IGuiModificationHandler guiModificationHandler) {
        this.elementToDelete = elementToDelete;
        this.guiModificationHandler = guiModificationHandler;
        this.i18NRepo = I18NRepo.getInstance();
        setText(i18NRepo.getString("label.menu.delete"));
        setOnAction(e -> delete());
        this.setOnAction(event -> delete());
        this.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));
    }

    /**
     * Default implementation of onRemoveElement event firing. Can be overwritten for setting special
     * properties on the event. (DeleteElementMenuItem.setOnAction())
     */
    protected void delete() {
        if (!isElementUsed(elementToDelete)) {
            GuiModificationEvent event = new GuiModificationEvent(GuiEventType.DELETE_ELEMENT, elementToDelete.getType(), elementToDelete.getName());
            event.setElementId(elementToDelete.getId());
            event.setParentId(elementToDelete.getParentId());
            //TODO create relative directory
            guiModificationHandler.handle(event);
        }
    }

    /**
     * Can be called from outside, if the setOnAction-Property gets overwritten from extern.
     * @return
     */
    public boolean isElementUsed(ViewModelElement elementToDelete) {
        ArrayList<ViewModelElement> usages = guiModificationHandler.getUsages(elementToDelete);
        if (usages.isEmpty()) {
            return false;
        }
        UsagesWindowController.createUsagesWindow(usages, i18NRepo.getString("label.usage.nodelete"), guiModificationHandler);

        return true;
    }


}
