package de.unikassel.vs.alica.planDesigner.controller;

import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class NameAndCommentDialogController implements Initializable {

    @FXML
    private Label nameLabel;

    @FXML
    private TextField nameTextField;

    @FXML
    private Label commentLabel;

    @FXML
    private TextArea commentTextArea;

    @FXML
    private Button cancelButton;

    @FXML
    private Button okButton;

    private AtomicReference<String> nameReference;
    private AtomicReference<String> commentReference;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        I18NRepo i18NRepo = I18NRepo.getInstance();
        nameLabel.setText(i18NRepo.getString("label.name"));
        commentLabel.setText(i18NRepo.getString("label.comment"));
        cancelButton.setText(i18NRepo.getString("action.cancel"));
        okButton.setText(i18NRepo.getString("label.ok"));

        okButton.setOnAction(e -> {
            nameReference.set(nameTextField.getText());
            commentReference.set(commentTextArea.getText());
            ((Stage) okButton.getScene().getWindow()).close();
        });

        cancelButton.setOnAction( e -> ((Stage) cancelButton.getScene().getWindow()).close());
    }

    public void setNameReference(AtomicReference<String> nameReference) {
        this.nameReference = nameReference;
    }
    public void setCommentReference(AtomicReference<String> commentReference) {
        this.commentReference = commentReference;
    }

}
