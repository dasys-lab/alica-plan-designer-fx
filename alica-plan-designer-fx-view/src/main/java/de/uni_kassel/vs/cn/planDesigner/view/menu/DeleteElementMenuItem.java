package de.uni_kassel.vs.cn.planDesigner.view.menu;

import de.uni_kassel.vs.cn.planDesigner.PlanDesignerApplication;
import de.uni_kassel.vs.cn.planDesigner.controller.UsagesWindowController;
import de.uni_kassel.vs.cn.planDesigner.events.GuiEventType;
import de.uni_kassel.vs.cn.planDesigner.events.GuiModificationEvent;
import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.common.ViewModelElement;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
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
     * Default implementation of delete event firing. Can be overwritten for setting special
     * properties on the event. (DeleteElementMenuItem.setOnAction())
     */
    protected void delete() {
        System.out.println("DeleteElementMenuItem: delete() called!");
        if (!isElementUsed(elementToDelete)) {
            GuiModificationEvent event = new GuiModificationEvent(GuiEventType.DELETE_ELEMENT, elementToDelete.getType(), elementToDelete.getName());
            event.setElementId(elementToDelete.getId());
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


}
