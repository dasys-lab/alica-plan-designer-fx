package de.unikassel.vs.alica.planDesigner.view.properties.configuration;

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
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
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
    private final ObjectProperty<BehaviourViewModel> selectedBehaviour = new SimpleObjectProperty<>();
    private final ObjectProperty<ConfigurationViewModel> selectedConfiguration = new SimpleObjectProperty<>();
    private final ObjectProperty<StateViewModel> selectedState = new SimpleObjectProperty<>();

    //----
    // GUI ELEMENTS
    //----
    private final VBox root;
    private final TitledPane configurationManager;
    private final TitledPane configurationChooser;
    private final TitledPane keyValuePairTable;
    private TextField keyField;
    private TextField valField;
    private TableView<Map.Entry<String, String>> keyValueTableView;
    private ChangeListener<BehaviourViewModel> chooserListener;
    private ChangeListener<BehaviourViewModel> managerListener;

    //----
    // CONSTRUCTOR
    //----
    public ConfigurationsTab(String title) {
        super(title);

        this.root = new VBox();
        this.setContent(root);

        selectedConfiguration.addListener((observable, oldValue, newValue) -> {
            if(selectedState.get() != null) {
                if (oldValue != null && newValue != null) {
                    if (oldValue.getBehaviour().getId() == newValue.getBehaviour().getId()) {
                        replaceConfiguration(selectedState.get(), oldValue, newValue);
                    }
                }
            }
        });

        configurationManager = createConfigurationManager();
        configurationChooser = createConfigurationChooser();
        keyValuePairTable = createKeyValuePairTable();
    }

    //----
    // PUBLIC METHODS
    //----
    public void setParentState(StateViewModel stateViewModel) {
        this.selectedState.set(stateViewModel);
    }

    public void setParentViewModel(ViewModelElement parentViewModel) {
        root.getChildren().clear();
        switch(parentViewModel.getType()) {
            case Types.BEHAVIOUR:
                root.getChildren().add(configurationManager);
                selectedBehaviour.removeListener(chooserListener);
                selectedBehaviour.addListener(managerListener);

                this.selectedBehaviour.set((BehaviourViewModel) parentViewModel);
                this.selectedConfiguration.set(null);
                this.selectedState.set(null);
                break;
            case Types.CONFIGURATION:
                root.getChildren().addAll(configurationChooser, keyValuePairTable);
                selectedBehaviour.removeListener(managerListener);
                selectedBehaviour.addListener(chooserListener);

                this.selectedConfiguration.set((ConfigurationViewModel) parentViewModel);
                this.selectedBehaviour.set(((ConfigurationViewModel)parentViewModel).getBehaviour());
                break;
            default:
                System.err.println("ConfigurationTab: This should not happen!");
        }
    }

    //----
    // PRIVATE METHODS
    //----

    //----
    // CONFIGURATION MANAGER (only for selectedBehaviour)
    // Creates GUI for adding/removing configurations for behaviours. Only shown when
    // selectedBehaviour is selected.
    //----
    private TitledPane createConfigurationManager() {
        TitledPane pane = new TitledPane();
        pane.setText(I18NRepo.getInstance().getString("label.caption.configurations"));

        managerListener = new ChangeListener<BehaviourViewModel>() {
            @Override
            public void changed(ObservableValue<? extends BehaviourViewModel> observable, BehaviourViewModel oldValue, BehaviourViewModel newValue) {
                ConfigurationsTab.this.updateConfigurationManager(newValue);
            }
        };
        selectedBehaviour.addListener(managerListener);

        return pane;
    }

    private void updateConfigurationManager(BehaviourViewModel behaviour) {
        configurationManager.setContent(null);
        configurationManager.setExpanded(true);
        if(behaviour == null) {
            return;
        }

        I18NRepo i18NRepo = I18NRepo.getInstance();

        ConfigurationsTable<ConfigurationViewModel> configurations = new ConfigurationsTable<ConfigurationViewModel>() {
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

        configurations.addColumn(i18NRepo.getString("alicatype.property.name"), "name", new DefaultStringConverter(), true, new ConfigurationsTableListener());
        configurations.addColumn(i18NRepo.getString("alicatype.property.id"), "id", new LongStringConverter(), false);
        configurations.addColumn(i18NRepo.getString("alicatype.property.comment"), "comment", new DefaultStringConverter(), true, new ConfigurationsTableListener());

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

        configurationManager.setContent(configurations);
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

        chooserListener = new ChangeListener<BehaviourViewModel>() {
            @Override
            public void changed(ObservableValue<? extends BehaviourViewModel> observable, BehaviourViewModel oldValue, BehaviourViewModel newValue) {
                ConfigurationsTab.this.updateConfigurationChooser(content, newValue);
                link.setGraphic(new ImageView(new AlicaIcon(Types.BEHAVIOUR, AlicaIcon.Size.SMALL)));
                link.setText(newValue.getName());
                link.setOnAction(event -> {
                    selectedBehaviour.set(null);
                    ConfigurationsTab.this.setParentViewModel(newValue);
                });
            }
        };
        selectedBehaviour.addListener(chooserListener);

        return pane;
    }

    private void updateConfigurationChooser(VBox content, BehaviourViewModel newBehaviour) {
        if (content.getChildren() != null) {
            content.getChildren().clear();
        }
        ToggleGroup group = new ToggleGroup();
        for(ConfigurationViewModel c : newBehaviour.getConfigurations()) {
            RadioButton box = new RadioButton(c.getName() );
            if (c.getId() == selectedConfiguration.get().getId()) {
                box.setSelected(true);
            }
            box.setUserData(c);
            box.setToggleGroup(group);
            content.getChildren().add(box);
        }

        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            Toggle value = observable.getValue();
            ConfigurationsTab.this.selectedConfiguration.set((ConfigurationViewModel) value.getUserData());
        });

        // TODO change radiobutton when list elements properties change
        newBehaviour.getConfigurations().addListener((ListChangeListener<ConfigurationViewModel>) c -> {
            updateConfigurationChooser(content, selectedBehaviour.get());
        });
    }

    //----
    // KEY VALUE TABLE
    // ----
    private TitledPane createKeyValuePairTable() {
        TitledPane pane = new TitledPane();
        pane.setText(I18NRepo.getInstance().getString("label.caption.keyvaluepairs"));

        this.selectedConfiguration.addListener((observable, oldValue, newValue) -> updateKeyValuePairTable(newValue));

        return pane;
    }

    private void updateKeyValuePairTable(ConfigurationViewModel configuration) {
        keyValuePairTable.setContent(null);
        keyValuePairTable.setExpanded(false);
        if(configuration == null) {
            return;
        }

        // Controls
        keyField = new TextField("Key");
        valField = new TextField("Value");
        Button remButton = new Button("-");
        Button putButton = new Button("+");
        HBox controls = new HBox(keyField, valField, remButton, putButton);
        remButton.setOnAction(evt -> createKeyValueEvent(configuration, keyField.getText(), valField.getText(),false));
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

        keyValuePairTable.setContent(new ScrollPane(new HBox(keyValueTableView, controls)));
    }


    //----
    // MODEL MANAGEMENT
    //----
    private void createKeyValueEvent(ConfigurationViewModel configuration, String key, String value, boolean add) {
        GuiChangeAttributeEvent addKeyValueEvent = new GuiChangeAttributeEvent(GuiEventType.CHANGE_ELEMENT, Types.CONFIGURATION, configuration.getName());
        addKeyValueEvent.setElementId(configuration.getId());
        addKeyValueEvent.setParentId(selectedState.get().getId());

        addKeyValueEvent.setAttributeName(key);
        addKeyValueEvent.setAttributeType(value);

        addKeyValueEvent.setNewValue(add);

        IGuiModificationHandler handler = MainWindowController.getInstance().getGuiModificationHandler();
        handler.handle(addKeyValueEvent);

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
        for (ConfigurationViewModel c : selectedBehaviour.get().getConfigurations()) {
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

    public class ConfigurationsTableListener<S, T> implements EventHandler<TableColumn.CellEditEvent<ConfigurationViewModel, String>> {

        private String attributeName;

        @Override
        public void handle(TableColumn.CellEditEvent<ConfigurationViewModel, String> event) {
            ConfigurationViewModel configurationViewModel = event.getRowValue();
            String newValue = event.getNewValue();
            String oldName = event.getOldValue();
            if ("name".equals(attributeName)) {
                if (isDuplicate(newValue)) {
                    createAlert();
                    event.consume();
                    event.getTableView().getItems().set(event.getTablePosition().getRow(), configurationViewModel);
                    return;
                }
            }

            GuiChangeAttributeEvent attributeEvent = new GuiChangeAttributeEvent(GuiEventType.CHANGE_ELEMENT, Types.CONFIGURATION, oldName);
            attributeEvent.setNewValue(newValue);
            attributeEvent.setAttributeType(String.class.getSimpleName());
            attributeEvent.setAttributeName(attributeName);
            attributeEvent.setElementId(configurationViewModel.getId());
            IGuiModificationHandler handler = MainWindowController.getInstance().getGuiModificationHandler();
            handler.handle(attributeEvent);
            event.consume();
        }

        public void setPropertyName(String propertyName) {
            this.attributeName = propertyName;
        }
    }
}
