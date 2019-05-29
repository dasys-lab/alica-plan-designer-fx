package de.unikassel.vs.alica.planDesigner.view.properties.configuration;

import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaIcon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;


public class DropDownItem extends MenuItem {
    public DropDownItem(DropDown dropDown, ConfigurationViewModel item) {
        setUserData(item);
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%1$"+25+ "s",item.getName())).append(" | ");
        builder.append(item.getId()).append(" | ");
        String itemComment = item.getComment();
        if (itemComment != null && !itemComment.trim().isEmpty()) {
            builder.append(itemComment);
        } else {
            builder.append("no comment");
        }

        setText(builder.toString());
        Button button = new Button();
        button.setGraphic(new ImageView(new AlicaIcon(AlicaIcon.REMOVE, AlicaIcon.Size.SMALL)));
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createConfigurationRemoveEvent(item);
            }
        });
        setGraphic(button);

        setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dropDown.setValue(item);
            }
        });
    }

    public DropDownItem(DropDown dropDown) {
        setText("new Config");
        Button button = new Button();
        button.setGraphic(new ImageView(new AlicaIcon(AlicaIcon.ADD, AlicaIcon.Size.SMALL)));
        setGraphic(button);
        EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dropDown.setValue(null);
            }
        };

        setOnAction(eventHandler);
        button.setOnAction(eventHandler);
    }

    private void createConfigurationRemoveEvent(ConfigurationViewModel item) {
        GuiModificationEvent event = new GuiModificationEvent(GuiEventType.DELETE_ELEMENT, Types.CONFIGURATION, item.getName());
        event.setElementId(item.getId());
        event.setParentId(item.getBehaviour().getId());
        MainWindowController.getInstance().getGuiModificationHandler().handle(event);
    }
}
