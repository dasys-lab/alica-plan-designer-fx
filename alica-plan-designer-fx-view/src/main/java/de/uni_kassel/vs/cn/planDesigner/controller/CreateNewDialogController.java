package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.generator.AlicaModelUtils;
import de.uni_kassel.vs.cn.planDesigner.alica.*;
import de.uni_kassel.vs.cn.planDesigner.command.CreateAbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import static de.uni_kassel.vs.cn.generator.EMFModelUtils.getAlicaFactory;

public class CreateNewDialogController implements Initializable {

    @FXML
    private Label pathLabel;

    @FXML
    private TextField pathTextField;

    @FXML
    private Button openFileChooserButton;

    @FXML
    private Label nameLabel;

    @FXML
    private TextField nameTextField;

    @FXML
    private Button createButton;

    private File initialDirectoryHint;

    private EClass alicaType;
    private EObject createdObject;

    private I18NRepo i18NRepo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        i18NRepo = I18NRepo.getInstance();
        pathLabel.setText(i18NRepo.getString("label.choose.parentDirectory"));
        openFileChooserButton.setOnAction(e -> {
            Node source = (Node) e.getSource();
            openFileChooser((Stage) source.getScene().getWindow());
        });
        openFileChooserButton.setText(i18NRepo.getString("action.choose"));
        createButton.setText(i18NRepo.getString("action.create"));
        createButton.setOnAction(e -> createFile());
    }

    public void setInitialDirectoryHint(File initialDirectoryHint) {
        this.initialDirectoryHint = initialDirectoryHint;
        if (initialDirectoryHint.isDirectory() == false) {
            this.initialDirectoryHint = initialDirectoryHint.getParentFile();
        }
        pathTextField.setText(this.initialDirectoryHint.getAbsolutePath());
    }

    private void openFileChooser(Stage stage) {
        DirectoryChooser fileChooser = new DirectoryChooser();

        if (initialDirectoryHint != null) {
            if (initialDirectoryHint.isDirectory()) {
                fileChooser.setInitialDirectory(initialDirectoryHint);
            } else {
                fileChooser.setInitialDirectory(new File(initialDirectoryHint.getParent()));
            }
        }

        File chosenFile = fileChooser.showDialog(stage);
        String parentPath = chosenFile.getAbsolutePath();

        pathTextField.setText(parentPath);

    }

    private void createFile() {
        String fileName = nameTextField.getText();
        if (fileName == null || fileName.length() == 0 || AlicaModelUtils.containsIllegalCharacter(fileName)) {
            ErrorWindowController.createErrorWindow(i18NRepo.getString("label.error.save.name"), null);
            return;
        }

        if (alicaType != null) {
            if (alicaType.getInstanceClass().equals(Behaviour.class) && fileName.endsWith(".beh") == false) {
                fileName = fileName + ".beh";
            }

            if (alicaType.getInstanceClass().equals(Plan.class) && fileName.endsWith(".pml") == false) {
                fileName = fileName + ".pml";
            }

            if (alicaType.getInstanceClass().equals(PlanType.class) && fileName.endsWith(".pty") == false) {
                fileName = fileName + ".pty";
            }

            EObject emptyObject = getAlicaFactory().create(alicaType);
            ((PlanElement)emptyObject).setName(fileName.replace(".beh","")
                    .replace(".pty","").replace(".pml", ""));
            File alicaFile = new File(Paths.get(pathTextField.getText(), fileName).toString());
            if (alicaFile.exists()) {
                ErrorWindowController
                        .createErrorWindow(i18NRepo.getString("label.error.save.alreadyExists"), null);
                return;
            }
            ((Stage)pathTextField.getScene().getWindow()).close();

            createdObject = getAlicaFactory().create(alicaType);
            ((PlanElement) createdObject).setName(fileName.replace(".beh","")
                    .replace(".pty","").replace(".pml", ""));
            Path alicaFilePath = Paths.get(pathTextField.getText(), fileName);
            if (Files.exists(alicaFilePath)) {
                ErrorWindowController
                        .createErrorWindow(i18NRepo.getString("label.error.save.alreadyExists"), null);
                return;
            }

            MainController.getInstance().getCommandStack()
                    .storeAndExecute(new CreateAbstractPlan((AbstractPlan) createdObject, alicaFilePath));
            ((Stage)pathTextField.getScene().getWindow()).close();
        } else {
            try {
                Files.createDirectory(new File(Paths.get(pathTextField.getText(), fileName).toString()).toPath());
            } catch (IOException e) {
                ErrorWindowController.createErrorWindow(i18NRepo.getString("label.error.create.folder"), e);
            }
            ((Stage)pathTextField.getScene().getWindow()).close();
        }
    }

    public void setAlicaType(EClass alicaType) {
        this.alicaType = alicaType;
        String nameString = i18NRepo.getString("label.name");
        if (alicaType != null) {
            if (alicaType.getInstanceClass().equals(Behaviour.class)) {
                nameLabel.setText(i18NRepo.getString("label.menu.new.behaviour") + " " + nameString);
            }

            if (alicaType.getInstanceClass().equals(Plan.class)) {
                nameLabel.setText(i18NRepo.getString("label.menu.new.plan") + " " + nameString);
            }

            if (alicaType.getInstanceClass().equals(PlanType.class)) {
                nameLabel.setText(i18NRepo.getString("label.menu.new.plantype") + " " + nameString);
            }
        } else {
            nameLabel.setText(i18NRepo.getString("label.menu.new.folder") + " " + nameString);
        }
    }

    public EObject getCreatedObject() {
        return createdObject;
    }
}
