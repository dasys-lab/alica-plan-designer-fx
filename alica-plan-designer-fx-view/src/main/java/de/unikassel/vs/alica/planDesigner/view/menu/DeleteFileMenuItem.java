package de.unikassel.vs.alica.planDesigner.view.menu;

import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.controller.UsagesWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.filebrowser.FileTreeItem;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.geometry.Pos;
import javafx.scene.control.*;

import java.util.ArrayList;

public class DeleteFileMenuItem extends MenuItem {
    private TreeCell toDelete;

    private IGuiModificationHandler guiModificationHandler;
    private I18NRepo i18NRepo;

    public DeleteFileMenuItem() {
        super(I18NRepo.getInstance().getString("label.menu.delete"));
        this.guiModificationHandler = MainWindowController.getInstance().getGuiModificationHandler();
        this.i18NRepo = I18NRepo.getInstance();
        setOnAction(e -> deleteFile());
    }

    public void deleteFile() {
        if(((FileTreeItem)toDelete.getTreeItem()).getViewModelElement() instanceof ViewModelElement) {
            ViewModelElement elementToDelete = ((FileTreeItem)toDelete.getTreeItem()).getViewModelElement();
            if (!isElementUsed(elementToDelete)) {
                GuiModificationEvent event = new GuiModificationEvent(GuiEventType.DELETE_ELEMENT, elementToDelete.getType(), elementToDelete.getName());
                event.setElementId(elementToDelete.getId());
                guiModificationHandler.handle(event);
            }
        } else {
            //Stop pop up Window, if the folder is not empty or is the root folder
            boolean root = false;
            for (Object object : toDelete.getTreeView().getRoot().getChildren())
            {
                TreeItem treeItem = (TreeItem) object;
                if(treeItem.getValue().toString().equals(toDelete.getTreeItem().getValue().toString())){
                    root = true;
                }
            }
            if(!toDelete.getTreeItem().isLeaf() || root) {
                Dialog dialog = new Dialog();
                dialog.setTitle("Folder delete fail");
                DialogPane dialogPane = dialog.getDialogPane();
                dialogPane.setStyle("-fx-background-color: #fff;");
                dialogPane.setMinWidth(500);
                dialogPane.setContentText("Cannot delete folders if they are not empty or is the root folder!!!");
                ButtonType okButtonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().add(okButtonType);
                Button okButton = (Button) dialog.getDialogPane().lookupButton(okButtonType);
                okButton.setAlignment(Pos.CENTER);
                dialog.showAndWait();
                return;
            }
            GuiModificationEvent event = new GuiModificationEvent(GuiEventType.DELETE_ELEMENT, Types.FOLDER, toDelete.getTreeItem().getValue().toString());
            guiModificationHandler.handle(event);
        }
    }

    private boolean isElementUsed(ViewModelElement elementToDelete) {
        ArrayList<ViewModelElement> usages = guiModificationHandler.getUsages(elementToDelete);
        if (usages.isEmpty()) {
            return false;
        }

        UsagesWindowController.createUsagesWindow(usages, i18NRepo.getString("label.usage.nodelete"),
                guiModificationHandler);
        return  true;
    }

    public void setTreeCell(TreeCell toDelete) {
        this.toDelete = toDelete;
    }
}
