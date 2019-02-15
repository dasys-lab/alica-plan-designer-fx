package de.unikassel.vs.alica.planDesigner.controller;

import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.repo.RepositoryLabel;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UsagesWindowController implements Initializable {

    @FXML
    private ListView<RepositoryLabel> listOfReferences;

    @FXML
    private Button continueButton;


    public void createReferencesList(ArrayList<ViewModelElement> usages, IGuiModificationHandler usageHandler) {
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
