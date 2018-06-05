package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.planDesigner.PlanDesignerApplication;
import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ErrorWindowController implements Initializable {

    @FXML
    private Button confirmButton;

    @FXML
    private Label errorLabel;

    public static void createErrorWindow(String errorMessage, Exception problemCause) {
        FXMLLoader fxmlLoader = new FXMLLoader(ErrorWindowController.class
                .getClassLoader().getResource("errorWindow.fxml"));
        try {
            Parent rootOfDialog = fxmlLoader.load();
            ErrorWindowController controller = fxmlLoader.getController();
            if (problemCause == null) {
                controller.setErrorLabelText(errorMessage);
            } else {
                controller.setErrorLabelText(errorMessage + "\n\n" + problemCause.toString());
            }
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("Error!");
            stage.setScene(new Scene(rootOfDialog));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(PlanDesignerApplication.getPrimaryStage());
            stage.show();

        } catch (IOException e) {
            // if the helper window is not loadable something is really wrong here
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        confirmButton.setText(I18NRepo.getInstance().getString("label.ok"));
        confirmButton.setOnAction(e -> {
            Node source = (Node) e.getSource();
            ((Stage) source.getScene().getWindow()).close();
        });
    }

    public void setErrorLabelText(String errorLabelText) {
        errorLabel.setText(errorLabelText);
    }
    
    public Button getConfirmButton() {
		return confirmButton;
	}
}
