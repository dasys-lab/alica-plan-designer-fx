package de.unikassel.vs.alica.planDesigner.view.properties;

import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.model.BehaviourViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.ConfigurationViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.StateViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.LongStringConverter;

import java.util.Map;

public class ConfigurationsTab extends Tab {

    private final ObjectProperty<BehaviourViewModel> behaviour = new SimpleObjectProperty<>();
    private final ObjectProperty<ConfigurationViewModel> configuration = new SimpleObjectProperty<>();
    private final ObjectProperty<StateViewModel> containingState = new SimpleObjectProperty<>();

    public ConfigurationsTab(String title) {
        super(title);

        this.setContent(new VBox(
                createConfigurationChooser(),
                createKeyValuePairTable()
        ));

        configuration.addListener((observable, oldValue, newValue) -> {
            if(containingState.get() != null) {
                replaceConfiguration(containingState.get(), oldValue, newValue);
            }
        });
    }

    public void setParentViewModel(ViewModelElement parentViewModel, ViewModelElement containingState) {
        switch(parentViewModel.getType()) {
            case Types.BEHAVIOUR:
                this.behaviour.set((BehaviourViewModel) parentViewModel);
                this.configuration.set(null);
                this.containingState.set(null);
                break;
            case Types.CONFIGURATION:
                this.behaviour.set(((ConfigurationViewModel)parentViewModel).getBehaviour());
                this.configuration.set((ConfigurationViewModel) parentViewModel);
                this.containingState.set((StateViewModel) containingState);
                break;
            default:
                this.behaviour.set(null);
                this.configuration.set(null);
                this.containingState.set(null);
        }
    }

    private TitledPane createConfigurationChooser() {
        TitledPane pane = new TitledPane();
        pane.setText(I18NRepo.getInstance().getString("label.caption.configurations"));

        behaviour.addListener((observable, oldValue, newValue) -> updateConfigurationChooser(pane, newValue));

        return pane;
    }

    private void updateConfigurationChooser(TitledPane pane, BehaviourViewModel behaviour) {
        pane.setContent(null);
        pane.setExpanded(true);
        if(behaviour == null) {
            return;
        }

        I18NRepo i18NRepo = I18NRepo.getInstance();

        VariablesTable<ConfigurationViewModel> configurations = new VariablesTable<ConfigurationViewModel>() {
            @Override
            protected void onAddElement() {
                GuiModificationEvent event = new GuiModificationEvent(GuiEventType.CREATE_ELEMENT, Types.CONFIGURATION, "NEW_CONFIGURATION");
                event.setParentId(behaviour.getId());
                MainWindowController.getInstance().getGuiModificationHandler().handle(event);
            }

            @Override
            protected void onRemoveElement() {
                ConfigurationViewModel selected = getSelectedItem();
                GuiModificationEvent event = new GuiModificationEvent(GuiEventType.DELETE_ELEMENT, Types.CONFIGURATION, selected.getName());
                event.setElementId(selected.getId());
                event.setParentId(selected.getBehaviour().getId());
                MainWindowController.getInstance().getGuiModificationHandler().handle(event);
            }
        };

        configurations.table.setRowFactory(param -> {
            TableRow<ConfigurationViewModel> r =  new TableRow<ConfigurationViewModel>() {
                @Override
                public void updateItem(ConfigurationViewModel item, boolean empty) {
                    super.updateItem(item, empty);
                    if (configuration.get() == item) {
                        setStyle("-fx-font-weight: bold;");
                    } else {
                        setStyle("");
                    }
                    configuration.addListener(observable -> {
                        if (configuration.get() == item) {
                            setStyle("-fx-font-weight: bold;");
                        } else {
                            setStyle("");
                        }
                    });
                                 }
            };
            r.setOnMouseClicked(evt -> {
                    if (evt.getClickCount() == 2 && !r.isEmpty()) {
                        configuration.set(r.getItem());
                    }
            });
            return r;
        });

        configurations.addColumn(i18NRepo.getString("alicatype.property.name"), "name", new DefaultStringConverter(), true);
        configurations.addColumn(i18NRepo.getString("alicatype.property.id"), "id", new LongStringConverter(), false);
        configurations.addColumn(i18NRepo.getString("alicatype.property.comment"), "comment", new DefaultStringConverter(), true);

        for(ConfigurationViewModel c : behaviour.getConfigurations()) {
            configurations.addItem(c);
        }
        behaviour.getConfigurations().addListener((ListChangeListener<? super ConfigurationViewModel>) change -> {
            while(change.next()){
                for(ConfigurationViewModel added : change.getAddedSubList()) {
                    configurations.addItem(added);
                }
                for(ConfigurationViewModel removed : change.getRemoved()) {
                    configurations.removeItem(removed);
                }
            }
        });

        pane.setContent(new ScrollPane(configurations));
    }

    private TitledPane createKeyValuePairTable() {
        TitledPane pane = new TitledPane();
        pane.setText(I18NRepo.getInstance().getString("label.caption.keyvaluepairs"));

        this.configuration.addListener((observable, oldValue, newValue) -> updateKeyValuePairTable(pane, newValue));

        return pane;
    }

    private void updateKeyValuePairTable(TitledPane pane, ConfigurationViewModel configuration) {
        pane.setContent(null);
        pane.setExpanded(false);
        if(configuration == null) {
            return;
        }

        String keyValueHolder = "\"%s\" : \"%s\"";
        // ListView
        ObservableList<String> keyValueStrings = FXCollections.observableArrayList();
        for(Map.Entry<String, String> entry : configuration.getKeyValuePairs().entrySet()) {
            keyValueStrings.add(String.format(keyValueHolder, entry.getKey(), entry.getValue()));
        }
        configuration.getKeyValuePairs().addListener((MapChangeListener<? super String, ? super String>) change -> {
            if(change.wasRemoved()) {
                keyValueStrings.remove(String.format(keyValueHolder, change.getKey(), change.getValueRemoved()));
            }
            if(change.wasAdded()) {
                keyValueStrings.add(String.format(keyValueHolder, change.getKey(), change.getValueAdded()));
            }
        });
        ListView<String> listView = new ListView<>(keyValueStrings);
        // Controls
        TextField keyField = new TextField();
        keyField.setPromptText("Key");
        TextField valField = new TextField();
        valField.setPromptText("Value");
        Button remButton = new Button("-");
        Button putButton = new Button("+");
        HBox controls = new HBox(keyField, valField, remButton, putButton);
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String[] splitString = newValue.split("\"");
            String key = splitString[1];
            String val = splitString[3];
            keyField.setText(key);
            valField.setText(val);
        });
        remButton.setOnAction(evt -> {
            keyField.clear();
            valField.clear();
            listView.setSelectionModel(null);
            // TODO: implement removing key value pair
            System.out.println("Removing key value pair not implemented yet");
        });
        putButton.setOnAction(evt -> {
            // TODO: implement adding key value pai
            System.out.println("Adding key value pair not implemented yet");
        });
        pane.setContent(new ScrollPane(new HBox(listView, controls)));
    }

    private void replaceConfiguration(StateViewModel state, ConfigurationViewModel oldConfigurationViewModel, ConfigurationViewModel newConfiguration) {
        GuiModificationEvent removeOld = new GuiModificationEvent(
                GuiEventType.REMOVE_ELEMENT, Types.CONFIGURATION, oldConfigurationViewModel.getName());
        removeOld.setElementId(oldConfigurationViewModel.getId());
        removeOld.setParentId(state.getId());

        GuiModificationEvent addNew = new GuiModificationEvent(
                GuiEventType.ADD_ELEMENT, Types.CONFIGURATION, newConfiguration.getName());
        addNew.setElementId(newConfiguration.getId());
        addNew.setParentId(state.getId());

        IGuiModificationHandler controller = MainWindowController.getInstance().getGuiModificationHandler();
        controller.handle(removeOld);
        controller.handle(addNew);
    }

}
