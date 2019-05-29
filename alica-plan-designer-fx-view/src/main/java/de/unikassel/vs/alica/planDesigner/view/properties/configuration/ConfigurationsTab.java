package de.unikassel.vs.alica.planDesigner.view.properties.configuration;

import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiChangeAttributeEvent;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.model.BehaviourViewModel;
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

    //----
    // GUI ELEMENTS
    //----
    private final VBox root;
    private final TitledPane keyValuePairTable;
    private TableView<Map.Entry<String, String>> keyValueTableView;

    //----
    // CONSTRUCTOR
    //----
    public ConfigurationsTab(String title) {
        super(title);

        this.root = new VBox();
        this.setContent(root);

        keyValuePairTable = new TitledPane();
        keyValuePairTable.setText(I18NRepo.getInstance().getString("label.caption.keyvaluepairs"));
        root.getChildren().addAll(keyValuePairTable);
    }

    //----
    // PUBLIC METHODS
    //----

    public void setParentViewModel(ViewModelElement parentViewModel) {
        this.selectedBehaviour.set((BehaviourViewModel) parentViewModel);
        this.updateKeyValuePairTable(this.selectedBehaviour.get());
    }

    //----
    // PRIVATE METHODS
    //----


    //----
    // KEY VALUE TABLE
    // ----
    private void updateKeyValuePairTable(BehaviourViewModel behaviour) {
        keyValuePairTable.setContent(null);
        keyValuePairTable.setExpanded(false);
        if(behaviour == null) {
            return;
        }

        // Table
        ObservableList<Map.Entry<String, String>> items = FXCollections.observableArrayList(behaviour.getKeyValuePairs().entrySet());
        keyValueTableView = new TableView<>(items);
        HashMap<String, String> map = new HashMap<>();
        map.put("","");
        keyValueTableView.getItems().addAll(map.entrySet());
        keyValueTableView.setFixedCellSize(CELL_SIZE);
        keyValueTableView.setPlaceholder(new Text());
        keyValueTableView.setEditable(true);
        resizeTableView(behaviour);

        TableColumn<Map.Entry<String, String>, String> keyColumn = new TableColumn<>("Key");
        keyColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        keyColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        keyColumn.setEditable(true);
        keyColumn.setOnEditCommit(event -> {
            String newValue = event.getRowValue().getValue();
            if (!event.getOldValue().isEmpty()) {
                createKeyValueEvent(behaviour, event.getOldValue(), newValue, false);
            }
            createKeyValueEvent(behaviour, event.getNewValue(), newValue, true);
            event.consume();
            resizeTableView(behaviour);
        });
        keyValueTableView.getColumns().add(keyColumn);

        TableColumn<Map.Entry<String, String>, String> valueColumn = new TableColumn<>("Value");
        valueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue()));
        valueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        valueColumn.setEditable(true);
        valueColumn.setOnEditCommit(event -> {
            String key = event.getRowValue().getKey();
            if (!"".equals(key)) {
                createKeyValueEvent(behaviour, key, event.getOldValue(), false);
            }
            if (key != null && !key.isEmpty()) {
                createKeyValueEvent(behaviour, key, event.getNewValue(), true);
            }
            event.consume();
            resizeTableView(behaviour);
        });
        keyValueTableView.getColumns().add(valueColumn);

        behaviour.getKeyValuePairs().addListener((MapChangeListener<? super String, ? super String>) change -> {
            keyValueTableView.setItems(FXCollections.observableArrayList(behaviour.getKeyValuePairs().entrySet()));
            HashMap<String, String> tmp = new HashMap<>();
            tmp.put("","");
            keyValueTableView.getItems().addAll(tmp.entrySet());
            resizeTableView(behaviour);
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
    private void createKeyValueEvent(BehaviourViewModel behaviour, String attributeName, String attributeType, boolean add) {
        GuiChangeAttributeEvent addKeyValueEvent = new GuiChangeAttributeEvent(GuiEventType.CHANGE_ELEMENT, Types.BEHAVIOUR, behaviour.getName());
        addKeyValueEvent.setElementId(behaviour.getId());

        addKeyValueEvent.setAttributeType("keyValuePairs");

        addKeyValueEvent.setAttributeName(attributeName);
        addKeyValueEvent.setAttributeType(attributeType);
        addKeyValueEvent.setNewValue(add);

        MainWindowController.getInstance().getGuiModificationHandler().handle(addKeyValueEvent);

        if (!add) {
            keyValueTableView.setSelectionModel(null);
        }
        keyValueTableView.refresh();
    }

    //----
    // HELPER METHODS
    //----
    private void resizeTableView(BehaviourViewModel behaviour) {
        int itemsAndHeaderSize = behaviour.getKeyValuePairs().size() + 2;

        keyValueTableView.setPrefHeight(itemsAndHeaderSize * CELL_SIZE + CELL_OFFSET);
        keyValueTableView.setMinHeight( itemsAndHeaderSize * CELL_SIZE + CELL_OFFSET);
        keyValueTableView.setMaxHeight( itemsAndHeaderSize * CELL_SIZE + CELL_OFFSET);
        keyValueTableView.setMinWidth(keyValuePairTable.getWidth() - CELL_OFFSET);
        keyValueTableView.refresh();
    }
}
