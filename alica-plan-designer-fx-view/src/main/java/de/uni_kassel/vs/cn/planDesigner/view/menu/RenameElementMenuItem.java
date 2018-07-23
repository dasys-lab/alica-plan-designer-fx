package de.uni_kassel.vs.cn.planDesigner.view.menu;


import de.uni_kassel.vs.cn.planDesigner.PlanDesignerApplication;
import de.uni_kassel.vs.cn.planDesigner.events.GuiChangeAttributeEvent;
import de.uni_kassel.vs.cn.planDesigner.events.GuiEventType;
import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.Types;
import de.uni_kassel.vs.cn.planDesigner.view.model.ViewModelElement;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RenameElementMenuItem extends MenuItem {

    private ViewModelElement element;
    private IGuiModificationHandler guiModificationHandler;
    private I18NRepo i18NRepo;

    public RenameElementMenuItem(ViewModelElement viewModelElement, IGuiModificationHandler guiModificationHandler) {
        i18NRepo = I18NRepo.getInstance();
        setText(i18NRepo.getInstance().getString("label.menu.edit.rename"));
        setOnAction(e -> onRename());
        this.element = viewModelElement;
        this.guiModificationHandler = guiModificationHandler;
    }

    public void onRename() {
        openRenameWindow();
    }

    /**
     * Can be called from outside, if the setOnAction-Property gets overwritten from extern.
     *
     * @return
     */
    public void openRenameWindow() {
        Stage stage = new Stage();
        HBox nameHBox = new HBox();
        TextField newNameTextField = new TextField(element.getName());
        nameHBox.getChildren().addAll(new Label(i18NRepo.getString("alicatype.property.name")), newNameTextField);
        nameHBox.setSpacing(5);
        nameHBox.setAlignment(Pos.CENTER);

        HBox buttons = new HBox();
        Button renameBtn = new Button(i18NRepo.getString("action.rename"));
        renameBtn.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent arg0) {
                if(newNameTextField.getText() != element.getName()) {
                    GuiChangeAttributeEvent guiChangeAttributeEvent = new GuiChangeAttributeEvent(GuiEventType.CHANGE_ELEMENT, element.getType(), element.getName());

                    guiChangeAttributeEvent.setElementId(element.getId());
                    if (element.getType() == Types.TASK) {
                        guiChangeAttributeEvent.setParentId(element.getParentId());
                    } else {
                        guiChangeAttributeEvent.setParentId(element.getId());
                    }
                    guiChangeAttributeEvent.setAttributeType(String.class.getSimpleName());
                    guiChangeAttributeEvent.setAttributeName("name");
                    guiChangeAttributeEvent.setNewValue(newNameTextField.getText());
                    guiModificationHandler.handle(guiChangeAttributeEvent);
                }
                stage.close();
            }

        });
        Button closeBtn = new Button(i18NRepo.getString("action.close"));
        closeBtn.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent arg0) {
                stage.close();
            }

        });
        buttons.getChildren().addAll(renameBtn, closeBtn);
        buttons.setSpacing(30);
        buttons.setAlignment(Pos.BOTTOM_CENTER);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(nameHBox, buttons);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(5);

        stage.setTitle(i18NRepo.getString("label.caption.rename"));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(new Scene(vBox, 300, 100));
        stage.initOwner(PlanDesignerApplication.getPrimaryStage());
        stage.showAndWait();
    }


}

