package de.unikassel.vs.alica.planDesigner.view.menu;

import de.unikassel.vs.alica.planDesigner.PlanDesignerApplication;
import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.controller.UsagesWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.filebrowser.FileTreeItem;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
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
        ViewModelElement elementToDelete = ((FileTreeItem)toDelete.getTreeItem()).getViewModelElement();
        if (!isElementUsed(elementToDelete)) {
            GuiModificationEvent event = new GuiModificationEvent(GuiEventType.DELETE_ELEMENT, elementToDelete.getType(), elementToDelete.getName());
            event.setElementId(elementToDelete.getId());
            guiModificationHandler.handle(event);
        }
//        // Folders
//        if (toDelete.isDirectory()) {
//            for (File alsoDelete : toDelete.listFiles()) {
//                DeleteFileMenuItem deleteFileMenuItem = new DeleteFileMenuItem(alsoDelete);
//                deleteFileMenuItem.setCommandStack(commandStack);
//                deleteFileMenuItem.deleteFile();
//            }
//            try {
//                Files.onRemoveElement(toDelete.toPath());
//            } catch (IOException e) {
//                throw new RuntimeException("");
//            }
//        }
    }

    private boolean isElementUsed(ViewModelElement elementToDelete) {
        ArrayList<ViewModelElement> usages = guiModificationHandler.getUsages(elementToDelete);
        if (usages.isEmpty()) {
            return false;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(ShowUsagesMenuItem.class.getClassLoader().getResource("usagesWindow.fxml"));
        try {
            Parent infoWindow = fxmlLoader.load();
            UsagesWindowController controller = fxmlLoader.getController();
            controller.createReferencesList(usages, guiModificationHandler);
            Stage stage = new Stage();
            stage.setTitle(i18NRepo.getString("label.usage.nodelete"));
            stage.setScene(new Scene(infoWindow));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(PlanDesignerApplication.getPrimaryStage());
            stage.showAndWait();
        } catch (IOException ignored) {
        } finally {
            return true;
        }
    }

    public void setTreeCell(TreeCell toDelete) {
        this.toDelete = toDelete;
    }
}
