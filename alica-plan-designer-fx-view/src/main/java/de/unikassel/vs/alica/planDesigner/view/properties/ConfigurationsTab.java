package de.unikassel.vs.alica.planDesigner.view.properties;

import de.unikassel.vs.alica.planDesigner.PlanDesignerApplication;
import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiChangeAttributeEvent;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaIcon;
import de.unikassel.vs.alica.planDesigner.view.model.BehaviourViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.ConfigurationViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.StateViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import de.unikassel.vs.alica.planDesigner.view.properties.variables.VariablesTable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.LongStringConverter;

import java.util.Map;

public class ConfigurationsTab extends Tab {

    private final ObjectProperty<BehaviourViewModel> behaviour = new SimpleObjectProperty<>();

    private final ObjectProperty<ConfigurationViewModel> configuration = new SimpleObjectProperty<>();
    private final ObjectProperty<StateViewModel> configurationState = new SimpleObjectProperty<>();

    private final VBox root;

    public ConfigurationsTab(String title) {
        super(title);

        this.root = new VBox();
        this.setContent(root);

        configuration.addListener((observable, oldValue, newValue) -> {
            if(configurationState.get() != null) {
                if (oldValue != null && newValue != null) {
                    if (oldValue.getBehaviour().getId() == newValue.getBehaviour().getId()) {
                        replaceConfiguration(configurationState.get(), oldValue, newValue);
                    }
                }
            }
        });
    }

    public void setParentState(StateViewModel stateViewModel) {
        this.configurationState.set(stateViewModel);
    }

    public void setParentViewModel(ViewModelElement parentViewModel) {
        root.getChildren().clear();
        switch(parentViewModel.getType()) {
            case Types.BEHAVIOUR:
                root.getChildren().addAll(createConfigurationManager());

                this.behaviour.set((BehaviourViewModel) parentViewModel);
                this.configuration.set(null);
                this.configurationState.set(null);
                break;
            case Types.CONFIGURATION:
                root.getChildren().addAll(createConfigurationChooser(), createKeyValuePairTable());

                this.configuration.set((ConfigurationViewModel) parentViewModel);
                this.behaviour.set(((ConfigurationViewModel)parentViewModel).getBehaviour());
                break;
            default:
                System.err.println("This should not happen!");
        }
    }

    private TitledPane createConfigurationChooser() {
        TitledPane pane = new TitledPane();
        pane.setText(I18NRepo.getInstance().getString("label.caption.configurations"));

        HBox parent = new HBox();
        VBox content = new VBox();
        Button link = new Button();

        parent.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(link, new Insets(0,10,0,0));
        parent.getChildren().addAll(link, content);
        pane.setContent(parent);

        behaviour.addListener((observable, oldValue, newValue) -> updateConfigurationChooser(content, newValue));
        behaviour.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                link.setGraphic(new ImageView(new AlicaIcon(Types.BEHAVIOUR, AlicaIcon.Size.SMALL)));
                link.setText(newValue.getName());
                link.setOnAction(event -> {
                    behaviour.set(null);
                    setParentViewModel(newValue);
                });
            }
        });

        return pane;
    }

    private void updateConfigurationChooser(VBox content, BehaviourViewModel newValue) {
        if (content.getChildren() != null) {
            content.getChildren().clear();
        }
        ToggleGroup group = new ToggleGroup();
        for(ConfigurationViewModel c : newValue.getConfigurations()) {
            RadioButton box = new RadioButton(c.getName());
            if (c.getId() == configuration.get().getId()) {
                box.setSelected(true);
            }
            box.setUserData(c);
            box.setToggleGroup(group);
            content.getChildren().add(box);
        }

        group.selectedToggleProperty().addListener((observable, oldValue, newValue1) -> {
            Toggle value = observable.getValue();
            ConfigurationsTab.this.configuration.set((ConfigurationViewModel) value.getUserData());
        });
    }

    private TitledPane createConfigurationManager() {
        TitledPane pane = new TitledPane();
        pane.setText(I18NRepo.getInstance().getString("label.caption.configurations"));

        behaviour.addListener((observable, oldValue, newValue) -> updateConfigurationManager(pane, newValue));

        return pane;
    }

    private void updateConfigurationManager(TitledPane pane, BehaviourViewModel behaviour) {
        pane.setContent(null);
        pane.setExpanded(true);
        if(behaviour == null) {
            return;
        }

        I18NRepo i18NRepo = I18NRepo.getInstance();

        VariablesTable<ConfigurationViewModel> configurations = new VariablesTable<ConfigurationViewModel>() {
            @Override
            protected void onAddElement() {
                String new_configuration = "NEW_CONFIGURATION";

                if (isDuplicate(new_configuration)) {
                    createAlert();
                    return;
                }

                GuiModificationEvent event = new GuiModificationEvent(GuiEventType.CREATE_ELEMENT, Types.CONFIGURATION, new_configuration);
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

    private boolean isDuplicate(String newName) {
        for (ConfigurationViewModel c : behaviour.get().getConfigurations()) {
            if (newName.equals(c.getName())) {
                return true;
            }
        }
        return false;
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

        // Table
        ObservableList<Map.Entry<String, String>> items = FXCollections.observableArrayList(configuration.getKeyValuePairs().entrySet());
        TableView<Map.Entry<String,String>> tableView = new TableView<>(items);
        tableView.setFixedCellSize(25);

        TableColumn<Map.Entry<String, String>, String> keyColumn = new TableColumn<>("Key");
        keyColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        tableView.getColumns().add(keyColumn);

        TableColumn<Map.Entry<String, String>, String> valueColumn = new TableColumn<>("Value");
        valueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue()));
        tableView.getColumns().add(valueColumn);

        configuration.getKeyValuePairs().addListener((MapChangeListener<? super String, ? super String>) change -> {
           tableView.setItems(FXCollections.observableArrayList(configuration.getKeyValuePairs().entrySet()));
           tableView.setPrefHeight((configuration.getKeyValuePairs().size() + 1) * 25 + 5);
           tableView.setMinHeight((configuration.getKeyValuePairs().size() + 1) * 25 + 5);
           tableView.setMaxHeight((configuration.getKeyValuePairs().size() + 1) * 25 + 5);
        });
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        HBox.setHgrow(tableView, Priority.ALWAYS);

        // Controls
        TextField keyField = new TextField("Key");
        TextField valField = new TextField("Value");
        Button remButton = new Button("-");
        Button putButton = new Button("+");
        HBox controls = new HBox(keyField, valField, remButton, putButton);
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                keyField.setText(newValue.getKey());
                valField.setText(newValue.getValue());
            }
        });
        remButton.setOnAction(evt -> createEvent(configuration, tableView, keyField, valField, false));
        putButton.setOnAction(evt -> createEvent(configuration, tableView, keyField, valField, true));
        pane.setContent(new ScrollPane(new HBox(tableView, controls)));
    }

    private void createEvent(ConfigurationViewModel configuration, TableView<Map.Entry<String, String>> table, TextField keyField, TextField valField, boolean add) {
        GuiChangeAttributeEvent addKeyValueEvent = new GuiChangeAttributeEvent(GuiEventType.CHANGE_ELEMENT, Types.CONFIGURATION, configuration.getName());
        addKeyValueEvent.setElementId(configuration.getId());
        addKeyValueEvent.setParentId(configurationState.get().getId());

        addKeyValueEvent.setAttributeName(keyField.getText());
        addKeyValueEvent.setAttributeType(valField.getText());

        addKeyValueEvent.setNewValue(add);

        IGuiModificationHandler controller = MainWindowController.getInstance().getGuiModificationHandler();
        controller.handle(addKeyValueEvent);

        if (!add) {
            keyField.clear();
            valField.clear();
            table.setSelectionModel(null);
        }
    }

    private void replaceConfiguration(StateViewModel state, ConfigurationViewModel oldConfigurationViewModel, ConfigurationViewModel newConfiguration) {
        GuiModificationEvent removeOld = new GuiModificationEvent(GuiEventType.REMOVE_ELEMENT, Types.CONFIGURATION, oldConfigurationViewModel.getName());
        removeOld.setElementId(oldConfigurationViewModel.getId());
        removeOld.setParentId(state.getId());

        GuiModificationEvent addNew = new GuiModificationEvent(GuiEventType.ADD_ELEMENT, Types.CONFIGURATION, newConfiguration.getName());
        addNew.setElementId(newConfiguration.getId());
        addNew.setParentId(state.getId());

        IGuiModificationHandler controller = MainWindowController.getInstance().getGuiModificationHandler();
        controller.handle(removeOld);
        controller.handle(addNew);
    }

    private void createAlert() {
        I18NRepo i18NRepo = I18NRepo.getInstance();

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Variable Binding!");
        alert.setContentText(i18NRepo.getString("label.error.invalidVariableBinding"));

        ButtonType closeBtn = new ButtonType(i18NRepo.getString("action.close"));

        alert.getButtonTypes().setAll(closeBtn);

        alert.initOwner(PlanDesignerApplication.getPrimaryStage());
        alert.showAndWait();
    }

}
