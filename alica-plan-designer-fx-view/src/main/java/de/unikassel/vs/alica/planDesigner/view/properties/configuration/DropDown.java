package de.unikassel.vs.alica.planDesigner.view.properties.configuration;

import de.unikassel.vs.alica.planDesigner.PlanDesignerApplication;
import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiChangeAttributeEvent;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.model.BehaviourViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.ConfigurationViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.ArrayList;

public class DropDown extends MenuButton {

    private final ObjectProperty<BehaviourViewModel> behaviour;
    private final ObjectProperty<ConfigurationViewModel> configuration;

    public DropDown(ObjectProperty<BehaviourViewModel> selectedBehaviour, ObjectProperty<ConfigurationViewModel> selectedConfiguration) {
        this.behaviour = selectedBehaviour;
        this.configuration = selectedConfiguration;
        createMenu();

        behaviour.get().getConfigurations().addListener(new ListChangeListener<ConfigurationViewModel>() {
            @Override
            public void onChanged(Change<? extends ConfigurationViewModel> c) {
                createMenu();
            }
        });
    }

    private void createMenu() {
        getItems().clear();
        ArrayList<MenuItem> menuItems = new ArrayList<>();
        for (ConfigurationViewModel c : behaviour.get().getConfigurations()) {
            MenuItem item = new DropDownItem(this, c);
            menuItems.add(item);
        }
        menuItems.add(new DropDownItem(this));
        this.getItems().addAll(menuItems);
        this.setValue(configuration.get());
    }

    public void setValue(ConfigurationViewModel item) {
        HBox content = new HBox();
        content.setAlignment(Pos.CENTER_LEFT);
        setGraphic(content);

        TextField name = new TextField();
        Label id = new Label();
        TextField comment = new TextField();

        name.setPromptText("name");
        comment.setPromptText("comment");


        HBox.setHgrow(comment, Priority.ALWAYS);
        comment.setMaxWidth(Double.MAX_VALUE);

        Separator separator = new Separator(Orientation.VERTICAL);
        HBox.setMargin(separator, new Insets(0, 5, 0,5));
        Separator separator1 = new Separator(Orientation.VERTICAL);
        HBox.setMargin(separator1, new Insets(0,5,0,5));

        content.getChildren().addAll(name, separator, id, separator1, comment);

        EventHandler<KeyEvent> keyTyped;
        if (item != null) {
            name.setText(item.getName());
            id.setText(String.valueOf(item.getId()));
            if (item.getComment() != null && !item.getComment().trim().isEmpty()) {
                comment.setText(item.getComment());
            }
            keyTyped = new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode() == KeyCode.ENTER || event.getCharacter().equals("\r")) {
                         String nameText = name.getText();
                        String commentText = comment.getText();

                        if (!item.getName().equals(nameText)) {
                            if (isDuplicate(nameText)) {
                                createAlert();
                                setValue(configuration.get());
                                return;
                            } else {
                                createConfigChangeEvent(item, nameText, "name");
                            }
                        }
                        createConfigChangeEvent(item, commentText, "comment");
                    } else if (event.getCode() == KeyCode.ESCAPE) {
                        setValue(configuration.get());
                    }
                }
            };
        } else {
            id.setText("<<will be set later>>");

            keyTyped = new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode() == KeyCode.ENTER || event.getCharacter().equals("\r")) {
                        createConfigCreateEvent(name.getText(), comment.getText());
                    } else if (event.getCode() == KeyCode.ESCAPE) {
                        setValue(configuration.get());
                    }
                }
            };
        }
        name.setOnKeyTyped(keyTyped);
        comment.setOnKeyTyped(keyTyped);

        configuration.set(item);
    }

    private void createConfigCreateEvent(String name, String comment) {
        if (isDuplicate(name)) {
            createAlert();
            setValue(configuration.get());
            return;
        }
        GuiModificationEvent event = new GuiModificationEvent(GuiEventType.CREATE_ELEMENT, Types.CONFIGURATION, name);
        event.setParentId(behaviour.get().getId());
        event.setComment(comment);
        MainWindowController.getInstance().getGuiModificationHandler().handle(event);
        for (ConfigurationViewModel conf: behaviour.get().getConfigurations()) {
            if (name.equals(conf.getName())) {
                configuration.set(conf);
            }
        }
        setValue(configuration.get());
        createMenu();
    }

    private void createConfigChangeEvent(ConfigurationViewModel config, String newValue, String attribute) {
        GuiChangeAttributeEvent event = new GuiChangeAttributeEvent(GuiEventType.CHANGE_ELEMENT, Types.CONFIGURATION, config.getName());
        event.setAttributeType(String.class.getSimpleName());
        event.setNewValue(newValue);
        event.setAttributeName(attribute);
        event.setElementId(config.getId());
        MainWindowController.getInstance().getGuiModificationHandler().handle(event);
        setValue(configuration.get());
        createMenu();
    }

    //----
    // ERROR HANDLING
    //----
    private boolean isDuplicate(String newName) {
        for (ConfigurationViewModel c : behaviour.get().getConfigurations()) {
            if (newName.equals(c.getName())) {
                return true;
            }
        }
        return false;
    }

    private void createAlert() {
        I18NRepo i18NRepo = I18NRepo.getInstance();

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Configuration!");
        alert.setContentText(i18NRepo.getString("label.error.invalidConfiguration"));

        ButtonType closeBtn = new ButtonType(i18NRepo.getString("action.close"));

        alert.getButtonTypes().setAll(closeBtn);

        alert.initOwner(PlanDesignerApplication.getPrimaryStage());
        alert.showAndWait();
    }
}
