package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.menu.IShowUsageHandler;
import de.uni_kassel.vs.cn.planDesigner.view.repo.RepositoryHBox;
import de.uni_kassel.vs.cn.planDesigner.view.repo.ViewModelElement;
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
    private ListView<RepositoryHBox> listOfReferences;

    @FXML
    private Button continueButton;


    public void createReferencesList(ArrayList<ViewModelElement> usages, IShowUsageHandler usageHandler) {
        List<RepositoryHBox> repositoryHBoxes = new ArrayList<>();
        for (ViewModelElement usage : usages) {
            repositoryHBoxes.add(new RepositoryHBox(usage, usageHandler));
        }
        listOfReferences.setItems(FXCollections.observableArrayList(repositoryHBoxes));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        continueButton.setText(I18NRepo.getInstance().getString("label.ok"));
        continueButton.setOnAction(e -> ((Stage)continueButton.getScene().getWindow()).close());
    }
}
