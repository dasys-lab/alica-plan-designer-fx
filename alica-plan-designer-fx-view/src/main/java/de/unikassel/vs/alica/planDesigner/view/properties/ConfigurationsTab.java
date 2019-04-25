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
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.LongStringConverter;

import java.util.Map;

public class ConfigurationsTab extends Tab {

    //----
    // CONSTANTS
    //----
    public static final int CELL_SIZE = 25;
    public static final int CELL_OFFSET = 5;

    //----
    // PROPERTIES
    //----
    private final ObjectProperty<BehaviourViewModel> behaviour = new SimpleObjectProperty<>();
    private final ObjectProperty<ConfigurationViewModel> configuration = new SimpleObjectProperty<>();
    private final ObjectProperty<StateViewModel> configurationState = new SimpleObjectProperty<>();

    //----
    // GUI ELEMENTS
    //----
    private final VBox root;
    private TextField keyField;
    private TextField valField;
    private TableView<Map.Entry<String, String>> keyValueTableView;

    //----
    // CONSTRUCTOR
    //----
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

    //----
    // PUBLIC METHODS
    //----
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

    //----
    // PRIVATE METHODS
    //----

    //----
    // CONFIGURATION MANAGER (only for behaviour)
    // Creates GUI for adding/removing configurations for behaviours. Only shown when
    // behaviour is selected.
    //----
    private TitledPane createConfigurationManager() {
        TitledPane pane = new TitledPane();
        pane.setText(I18NRepo.getInstance().getString("label.caption.configurations"));

        behaviour.addListener((observable, oldValue, newValue) -> updateConfigurationManager(pane, newValue));
        setOnSelectionChanged(event -> updateConfigurationManager(pane, behaviour.get()));

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

        pane.setContent(configurations);
    }

    //----
    // CONFIGURATION CHOOSER
    //----
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
        setOnSelectionChanged(event -> {
            updateConfigurationChooser(content, behaviour.get());
        });

        return pane;
    }

    private void updateConfigurationChooser(VBox content, BehaviourViewModel newBehaviour) {
        if (content.getChildren() != null) {
            content.getChildren().clear();
        }
        ToggleGroup group = new ToggleGroup();
        for(ConfigurationViewModel c : newBehaviour.getConfigurations()) {
            RadioButton box = new RadioButton(c.getName() );
            if (c.getId() == configuration.get().getId()) {
                box.setSelected(true);
            }
            box.setUserData(c);
            box.setToggleGroup(group);
            content.getChildren().add(box);
        }

        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            Toggle value = observable.getValue();
            ConfigurationsTab.this.configuration.set((ConfigurationViewModel) value.getUserData());
        });

        // TODO change radiobutton when list elements properties change
        newBehaviour.getConfigurations().addListener((ListChangeListener<ConfigurationViewModel>) c -> {
            updateConfigurationChooser(content, behaviour.get());
        });
    }

    //----
    // KEY VALUE TABLE
    // ----
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

        // Controls
        keyField = new TextField("Key");
        valField = new TextField("Value");
        Button remButton = new Button("-");
        Button putButton = new Button("+");
        HBox controls = new HBox(keyField, valField, remButton, putButton);
        remButton.setOnAction(evt -> createKeyValueEvent(configuration, keyField.getText(), valField.getText(), false));
        putButton.setOnAction(evt -> createKeyValueEvent(configuration, keyField.getText(), valField.getText(),true));

        // Table
        ObservableList<Map.Entry<String, String>> items = FXCollections.observableArrayList(configuration.getKeyValuePairs().entrySet());
        keyValueTableView = new TableView<>(items);
        keyValueTableView.setFixedCellSize(CELL_SIZE);
        keyValueTableView.setPlaceholder(new Text());
        keyValueTableView.setEditable(true);
        resizeTableView(configuration);

        TableColumn<Map.Entry<String, String>, String> keyColumn = new TableColumn<>("Key");
        keyColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        keyColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        keyColumn.setEditable(true);
        keyColumn.setOnEditCommit(event -> {
            String value = event.getRowValue().getValue();
            createKeyValueEvent(configuration, event.getOldValue(), value, false);
            createKeyValueEvent(configuration, event.getNewValue(), value, true);
            event.consume();
        });
        keyValueTableView.getColumns().add(keyColumn);

        TableColumn<Map.Entry<String, String>, String> valueColumn = new TableColumn<>("Value");
        valueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue()));
        valueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        valueColumn.setEditable(true);
        valueColumn.setOnEditCommit(event -> {
            String key = event.getRowValue().getKey();
            createKeyValueEvent(configuration, key, event.getOldValue(), false);
            createKeyValueEvent(configuration, key, event.getNewValue(), true);
            event.consume();
        });
        keyValueTableView.getColumns().add(valueColumn);

        configuration.getKeyValuePairs().addListener((MapChangeListener<? super String, ? super String>) change -> {
           keyValueTableView.setItems(FXCollections.observableArrayList(configuration.getKeyValuePairs().entrySet()));
           resizeTableView(configuration);
        });
        keyValueTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        HBox.setHgrow(keyValueTableView, Priority.ALWAYS);



        keyValueTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                keyField.setText(newValue.getKey());
                valField.setText(newValue.getValue());
            }
        });

        pane.setContent(new ScrollPane(new HBox(keyValueTableView, controls)));
    }


    //----
    // MODEL MANAGEMENT
    //----
    private void createKeyValueEvent(ConfigurationViewModel configuration, String key, String value, boolean add) {
        GuiChangeAttributeEvent addKeyValueEvent = new GuiChangeAttributeEvent(GuiEventType.CHANGE_ELEMENT, Types.CONFIGURATION, configuration.getName());
        addKeyValueEvent.setElementId(configuration.getId());
        addKeyValueEvent.setParentId(configurationState.get().getId());

        addKeyValueEvent.setAttributeName(key);
        addKeyValueEvent.setAttributeType(value);

        addKeyValueEvent.setNewValue(add);

        IGuiModificationHandler controller = MainWindowController.getInstance().getGuiModificationHandler();
        controller.handle(addKeyValueEvent);

        if (!add) {
            keyField.clear();
            valField.clear();
            keyValueTableView.setSelectionModel(null);
        }
    }

    //----
    // HELPER METHODS
    //----
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

    private void resizeTableView(ConfigurationViewModel configuration) {
        int itemsAndHeaderSize = configuration.getKeyValuePairs().size() + 1;
        keyValueTableView.setPrefHeight(itemsAndHeaderSize * CELL_SIZE + CELL_OFFSET);
        keyValueTableView.setMinHeight( itemsAndHeaderSize * CELL_SIZE + CELL_OFFSET);
        keyValueTableView.setMaxHeight( itemsAndHeaderSize * CELL_SIZE + CELL_OFFSET);
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
