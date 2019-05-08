package de.unikassel.vs.alica.planDesigner.controller;

import de.unikassel.vs.alica.planDesigner.PlanDesignerApplication;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.menu.ShowUsagesMenuItem;
import de.unikassel.vs.alica.planDesigner.view.repo.RepositoryLabel;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UsagesWindowController implements Initializable {

    @FXML
    private ListView<RepositoryLabel> listOfReferences;

    @FXML
    private Button continueButton;

    public static void createUsagesWindow(ViewModelElement viewModelElement, String title, IGuiModificationHandler guiModificationHandler) {
        createUsagesWindow(guiModificationHandler.getUsages(viewModelElement), title, guiModificationHandler);
    }

    public static void createUsagesWindow(List<ViewModelElement> usages, String title, IGuiModificationHandler guiModificationHandler) {
        FXMLLoader fxmlLoader = new FXMLLoader(ShowUsagesMenuItem.class.getClassLoader().getResource("usagesWindow.fxml"));
        try {
            Parent infoWindow = fxmlLoader.load();
            UsagesWindowController controller = fxmlLoader.getController();
            controller.createReferencesList(usages, guiModificationHandler);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(infoWindow));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(PlanDesignerApplication.getPrimaryStage());
            stage.showAndWait();
        } catch (IOException ignored) {
            System.err.println("UsagesWindowController: Could not show UsagesWindow");
        }
    }

    private void createReferencesList(List<ViewModelElement> usages, IGuiModificationHandler usageHandler) {
        List<RepositoryLabel> repositoryLabels = new ArrayList<>();
        for (ViewModelElement usage : usages) {
            repositoryLabels.add(new RepositoryLabel(usage, usageHandler));
        }
        listOfReferences.setItems(FXCollections.observableArrayList(repositoryLabels));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        continueButton.setText(I18NRepo.getInstance().getString("label.ok"));
        continueButton.setOnAction(e -> ((Stage)continueButton.getScene().getWindow()).close());
    }
}
