package de.unikassel.vs.alica.planDesigner.view.menu;

import de.unikassel.vs.alica.planDesigner.PlanDesignerApplication;
import de.unikassel.vs.alica.planDesigner.controller.UsagesWindowController;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ShowUsagesMenuItem extends MenuItem {

    private I18NRepo i18NRepo;

    public ShowUsagesMenuItem(ViewModelElement viewModelElement, IGuiModificationHandler guiModificationHandler) {
        i18NRepo = I18NRepo.getInstance();
        setText(i18NRepo.getString("label.menu.usage"));
        setOnAction( e -> {
            FXMLLoader fxmlLoader = new FXMLLoader(ShowUsagesMenuItem.class.getClassLoader().getResource("usagesWindow.fxml"));
            try {
                Parent infoWindow = fxmlLoader.load();
                UsagesWindowController controller = fxmlLoader.getController();
                controller.createReferencesList(guiModificationHandler.getUsages(viewModelElement), guiModificationHandler);
                Stage stage = new Stage();
                stage.setResizable(false);
                stage.setTitle(i18NRepo.getString("label.usage.info"));
                stage.setScene(new Scene(infoWindow));
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(PlanDesignerApplication.getPrimaryStage());
                stage.showAndWait();
            } catch (IOException ignored) {
            }
        });
    }


}
