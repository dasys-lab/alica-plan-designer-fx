package de.unikassel.vs.alica.planDesigner.view.properties.configuration;

import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiChangeAttributeEvent;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.model.BehaviourViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.AbstractMap;
import java.util.Map;

public class BehaviourParametersTab extends Tab {

    private final ObjectProperty<BehaviourViewModel> selectedBehaviour = new SimpleObjectProperty<>();

    private static final int CELL_SIZE = 30;
    private static final int CELL_OFFSET = 5;
    private TableView<Map.Entry<String, String>> keyValueTableView;

    private static final MapChangeListener<? super String, ? super String> parameterListener = new ParameterListener();

    public BehaviourParametersTab(String title) {
        super(title);

        // Table
        ObservableList<Map.Entry<String, String>> items = FXCollections.observableArrayList();
        keyValueTableView = new TableView<>(items);
        keyValueTableView.getItems().add(new AbstractMap.SimpleEntry<String,String>("",""));
        keyValueTableView.setFixedCellSize(CELL_SIZE);
        keyValueTableView.setPlaceholder(new Text());
        keyValueTableView.setEditable(true);

        // Key Column (for edit key)
        TableColumn<Map.Entry<String, String>, String> keyColumn = new TableColumn<>("Key");
        keyColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        keyColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        keyColumn.setEditable(true);
        keyColumn.setOnEditCommit(event -> {
//            System.out.println("BehaviourParametersTab: RowValue: " + event.getRowValue() + " OldValue: " + event.getOldValue() + " NewValue: " + event.getNewValue());
            createKeyValueEvent(new AbstractMap.SimpleEntry<String,String>(event.getNewValue(), event.getRowValue().getValue()), event.getRowValue());
            event.consume();
            resizeTableView();
        });
        keyValueTableView.getColumns().add(keyColumn);

        // Value Column (for edit value)
        TableColumn<Map.Entry<String, String>, String> valueColumn = new TableColumn<>("Value");
        valueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue()));
        valueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        valueColumn.setEditable(true);
        valueColumn.setOnEditCommit(event -> {
//            System.out.println("BehaviourParametersTab: RowValue: " + event.getRowValue() + " OldValue: " + event.getOldValue() + " NewValue: " + event.getNewValue());
            createKeyValueEvent(new AbstractMap.SimpleEntry<String,String>(event.getRowValue().getKey(), event.getNewValue()), event.getRowValue());
            event.consume();
            resizeTableView();
        });
        keyValueTableView.getColumns().add(valueColumn);

        // Row Factory (for delete)
        keyValueTableView.setRowFactory(new Callback<TableView<Map.Entry<String, String>>, TableRow<Map.Entry<String, String>>>() {
            @Override
            public TableRow<Map.Entry<String, String>> call(TableView<Map.Entry<String, String>> param) {
                final TableRow<Map.Entry<String, String>> row = new TableRow<>();
                final ContextMenu rowMenu = new ContextMenu();
                MenuItem removeItem = new MenuItem("Delete");
                // TODO: use the events right
                removeItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("BehaviourParametersTab: Key: " + row.getItem().getKey() + " Value: " + row.getItem().getValue());
                        createKeyValueEvent(null, row.getItem());
                    }
                });
                rowMenu.getItems().addAll(removeItem);

                // only display context menu for non-null items:
                row.contextMenuProperty().bind(Bindings.when(Bindings.isNotNull(row.itemProperty())).then(rowMenu).otherwise((ContextMenu)null));
                return row;
            }
        });

        keyValueTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        HBox.setHgrow(keyValueTableView, Priority.ALWAYS);

        this.setContent(keyValueTableView);
    }

    public void setParentViewModel(ViewModelElement parentViewModel) {
        this.selectedBehaviour.get().getParameters().removeListener(this.parameterListener);
        this.selectedBehaviour.set((BehaviourViewModel) parentViewModel);
        this.selectedBehaviour.get().getParameters().addListener(this.parameterListener);

    }

    private void createKeyValueEvent(Map.Entry<String, String> newValue, Map.Entry<String, String> oldValue) {
        GuiChangeAttributeEvent addKeyValueEvent = new GuiChangeAttributeEvent(GuiEventType.CHANGE_ELEMENT, Types.BEHAVIOUR, selectedBehaviour.get().getName());
        addKeyValueEvent.setElementId(selectedBehaviour.get().getId());
        addKeyValueEvent.setAttributeType(Map.Entry.class.getSimpleName());
        addKeyValueEvent.setAttributeName("parameters");
        addKeyValueEvent.setNewValue(newValue);
        addKeyValueEvent.setOldValue(oldValue);
        MainWindowController.getInstance().getGuiModificationHandler().handle(addKeyValueEvent);
    }

    private void resizeTableView() {
        int itemsAndHeaderSize = 2;
        if (selectedBehaviour.get() != null) {
            itemsAndHeaderSize = selectedBehaviour.get().getParameters().size() + 2;
        }

        keyValueTableView.setPrefHeight(itemsAndHeaderSize * CELL_SIZE + CELL_OFFSET);
        keyValueTableView.setMinHeight( itemsAndHeaderSize * CELL_SIZE + CELL_OFFSET);
        keyValueTableView.setMaxHeight( itemsAndHeaderSize * CELL_SIZE + CELL_OFFSET);
//        keyValueTableView.setMinWidth(keyValueTableView.getWidth() - CELL_OFFSET);
        keyValueTableView.refresh();
    }
}
