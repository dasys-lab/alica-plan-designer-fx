package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.planDesigner.alica.AbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.repo.RepositoryHBox;
import de.uni_kassel.vs.cn.planDesigner.view.repo.RepositoryViewModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by marci on 10.05.17.
 */
public class UsagesWindowController implements Initializable {

    @FXML
    private ListView<RepositoryHBox<AbstractPlan>> listOfReferences;

    @FXML
    private Button continueButton;


    public void createReferencesList(List<AbstractPlan> plans) {
        List<RepositoryHBox<AbstractPlan>> repositoryHBoxes = plans
                .stream()
                .map(e -> {
                    Path pathForAbstractPlan = RepositoryViewModel.getInstance().getPathForAbstractPlan(e);
                    return new RepositoryHBox<>(e, pathForAbstractPlan);
                })
                .collect(Collectors.toList());
        listOfReferences.setItems(FXCollections.observableArrayList(repositoryHBoxes));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        continueButton.setText(I18NRepo.getInstance().getString("label.ok"));
        continueButton.setOnAction(e -> ((Stage)continueButton.getScene().getWindow()).close());
    }
}
