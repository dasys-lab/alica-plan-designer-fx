package de.unikassel.vs.alica.planDesigner.view.properties.configuration;

import de.unikassel.vs.alica.planDesigner.PlanDesignerApplication;
import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiChangeAttributeEvent;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.model.BehaviourViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.ConfigurationViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.StateViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.HashMap;
import java.util.Map;

public class ConfigurationsTab extends Tab {

    //----
    // CONSTANTS
    //----
    private static final int CELL_SIZE = 30;
    private static final int CELL_OFFSET = 5;

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
    private final VBox configurationManager;
    private final TitledPane keyValuePairTable;
    private TableView<Map.Entry<String, String>> keyValueTableView;
    private DropDown dropDown;

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
        root.getChildren().addAll(configurationManager, keyValuePairTable);
        switch(parentViewModel.getType()) {
            case Types.BEHAVIOUR:
                this.selectedBehaviour.set((BehaviourViewModel) parentViewModel);
                this.selectedConfiguration.set(null);
                this.selectedState.set(null);
                break;
            case Types.CONFIGURATION:
                this.selectedConfiguration.set((ConfigurationViewModel) parentViewModel);
                this.selectedBehaviour.set(((ConfigurationViewModel)parentViewModel).getBehaviour());
                break;
            default:
                System.err.println("ConfigurationsTab: This should not happen!");
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
    private VBox createConfigurationManager() {
        VBox pane = new VBox();

        selectedBehaviour.addListener(new ChangeListener<BehaviourViewModel>() {
            @Override
            public void changed(ObservableValue<? extends BehaviourViewModel> observable, BehaviourViewModel oldValue, BehaviourViewModel newValue) {
                ConfigurationsTab.this.updateConfigurationManager(newValue);
            }
        });

        return pane;
    }

    private void updateConfigurationManager(BehaviourViewModel behaviour) {
        configurationManager.getChildren().clear();
        if(behaviour == null) {
            return;
        }

        dropDown = new DropDown(selectedBehaviour, selectedConfiguration);
        dropDown.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(dropDown, Priority.ALWAYS);

        if (selectedConfiguration.get() != null) {
            dropDown.setValue(selectedConfiguration.get());
        }

        configurationManager.getChildren().addAll(dropDown);
    }

    //----
    // KEY VALUE TABLE
    // ----
    private TitledPane createKeyValuePairTable() {
        TitledPane pane = new TitledPane();
        pane.setText(I18NRepo.getInstance().getString("label.caption.keyvaluepairs"));

        this.selectedConfiguration.addListener((observable, oldValue, newValue) -> updateKeyValuePairTable(newValue));
        pane.expandedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue && selectedConfiguration.get() != null) {
                    resizeTableView(selectedConfiguration.get());
                }
            }
        });

        return pane;
    }

    private void updateKeyValuePairTable(ConfigurationViewModel configuration) {
        keyValuePairTable.setContent(null);
        keyValuePairTable.setExpanded(false);
        if(configuration == null || selectedConfiguration.get() == null) {
            return;
        }

        // Table
        ObservableList<Map.Entry<String, String>> items = FXCollections.observableArrayList(configuration.getKeyValuePairs().entrySet());
        keyValueTableView = new TableView<>(items);
        HashMap<String, String> map = new HashMap<>();
        map.put("","");
        keyValueTableView.getItems().addAll(map.entrySet());
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
            if (!"".equals(event.getOldValue())) {
                createKeyValueEvent(configuration, event.getOldValue(), value, false);
            }
            createKeyValueEvent(configuration, event.getNewValue(), value, true);
            event.consume();
            resizeTableView(configuration);
        });
        keyValueTableView.getColumns().add(keyColumn);

        TableColumn<Map.Entry<String, String>, String> valueColumn = new TableColumn<>("Value");
        valueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue()));
        valueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        valueColumn.setEditable(true);
        valueColumn.setOnEditCommit(event -> {
            String key = event.getRowValue().getKey();
            if (!"".equals(key)) {
                createKeyValueEvent(configuration, key, event.getOldValue(), false);
            }
            if (key != null && !key.isEmpty()) {
                createKeyValueEvent(configuration, key, event.getNewValue(), true);
            }
            event.consume();
            resizeTableView(configuration);
        });
        keyValueTableView.getColumns().add(valueColumn);

        configuration.getKeyValuePairs().addListener((MapChangeListener<? super String, ? super String>) change -> {
            keyValueTableView.setItems(FXCollections.observableArrayList(configuration.getKeyValuePairs().entrySet()));
            HashMap<String, String> tmp = new HashMap<>();
            tmp.put("","");
            keyValueTableView.getItems().addAll(tmp.entrySet());
            resizeTableView(configuration);
        });
        keyValueTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        HBox.setHgrow(keyValueTableView, Priority.ALWAYS);

        keyValueTableView.setRowFactory(new Callback<TableView<Map.Entry<String, String>>, TableRow<Map.Entry<String, String>>>() {
            @Override
            public TableRow<Map.Entry<String, String>> call(TableView<Map.Entry<String, String>> param) {
                final TableRow<Map.Entry<String, String>> row = new TableRow<>();
                final ContextMenu rowMenu = new ContextMenu();
                MenuItem removeItem = new MenuItem("Delete");
                removeItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        createKeyValueEvent(selectedConfiguration.get(), row.getItem().getKey(), row.getItem().getValue(), false);
                    }
                });
                rowMenu.getItems().addAll(removeItem);

                // only display context menu for non-null items:
                row.contextMenuProperty().bind(Bindings.when(Bindings.isNotNull(row.itemProperty())).then(rowMenu).otherwise((ContextMenu)null));
                return row;
            }
        });

        keyValuePairTable.setContent(new ScrollPane(new HBox(keyValueTableView)));
    }


    //----
    // MODEL MANAGEMENT
    //----
    private void createKeyValueEvent(ConfigurationViewModel configuration, String key, String value, boolean add) {
        GuiChangeAttributeEvent addKeyValueEvent = new GuiChangeAttributeEvent(GuiEventType.CHANGE_ELEMENT, Types.CONFIGURATION, configuration.getName());
        addKeyValueEvent.setElementId(configuration.getId());

        if(selectedState.get()!= null) {
            addKeyValueEvent.setParentId(selectedState.get().getId());
        }

        addKeyValueEvent.setAttributeName(key);
        addKeyValueEvent.setAttributeType(value);

        addKeyValueEvent.setNewValue(add);

        IGuiModificationHandler handler = MainWindowController.getInstance().getGuiModificationHandler();
        handler.handle(addKeyValueEvent);

        if (!add) {
            keyValueTableView.setSelectionModel(null);
        }
        keyValueTableView.refresh();
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
        int itemsAndHeaderSize = configuration.getKeyValuePairs().size() + 2;

        keyValueTableView.setPrefHeight(itemsAndHeaderSize * CELL_SIZE + CELL_OFFSET);
        keyValueTableView.setMinHeight( itemsAndHeaderSize * CELL_SIZE + CELL_OFFSET);
        keyValueTableView.setMaxHeight( itemsAndHeaderSize * CELL_SIZE + CELL_OFFSET);
        keyValueTableView.setMinWidth(keyValuePairTable.getWidth() - CELL_OFFSET);
        keyValueTableView.refresh();
    }
}
