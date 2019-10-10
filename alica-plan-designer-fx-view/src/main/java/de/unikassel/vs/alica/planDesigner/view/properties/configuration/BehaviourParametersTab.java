package de.unikassel.vs.alica.planDesigner.view.properties.configuration;

import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiChangeAttributeEvent;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.model.BehaviourViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
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

import java.util.AbstractMap;
import java.util.Map;

public class BehaviourParametersTab extends Tab {

    private final ObjectProperty<BehaviourViewModel> selectedBehaviour = new SimpleObjectProperty<>();


    private TableView<Map.Entry<String, String>> parameterTableView;
    private ParameterListener parameterListener;

    public BehaviourParametersTab(String title) {
        super(title);

        // Table
        ObservableList<Map.Entry<String, String>> items = FXCollections.observableArrayList();
        parameterTableView = new TableView<>(items);
        parameterTableView.setFixedCellSize(ParameterListener.CELL_SIZE);
        parameterTableView.setPlaceholder(new Text());
        parameterTableView.setEditable(true);

        // Key Column (for edit key)
        TableColumn<Map.Entry<String, String>, String> keyColumn = new TableColumn<>("Key");
        keyColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        keyColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        keyColumn.setEditable(true);
        keyColumn.setOnEditCommit(event -> {
//            System.out.println("BehaviourParametersTab: Key RowValue: " + event.getRowValue() + " OldValue: " + event.getOldValue() + " NewValue: " + event.getNewValue());
            if (!fireEvent(new AbstractMap.SimpleEntry<String,String>(event.getNewValue(), event.getRowValue().getValue()), event.getRowValue())) {
                parameterTableView.refresh();
            }
            event.consume();
        });
        parameterTableView.getColumns().add(keyColumn);

        // Value Column (for edit value)
        TableColumn<Map.Entry<String, String>, String> valueColumn = new TableColumn<>("Value");
        valueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue()));
        valueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        valueColumn.setEditable(true);
        valueColumn.setOnEditCommit(event -> {
//            System.out.println("BehaviourParametersTab: Value RowValue: " + event.getRowValue() + " OldValue: " + event.getOldValue() + " NewValue: " + event.getNewValue());
            if (!fireEvent(new AbstractMap.SimpleEntry<String,String>(event.getRowValue().getKey(), event.getNewValue()), event.getRowValue())) {
                parameterTableView.refresh();
            }
            event.consume();
        });
        parameterTableView.getColumns().add(valueColumn);

        // Row Factory (for delete)
        parameterTableView.setRowFactory(new Callback<TableView<Map.Entry<String, String>>, TableRow<Map.Entry<String, String>>>() {
            @Override
            public TableRow<Map.Entry<String, String>> call(TableView<Map.Entry<String, String>> param) {
                final TableRow<Map.Entry<String, String>> row = new TableRow<>();
                final ContextMenu rowMenu = new ContextMenu();
                MenuItem removeItem = new MenuItem("Delete");
                removeItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("BehaviourParametersTab: Delete Key: " + row.getItem().getKey() + " Value: " + row.getItem().getValue());
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                fireEvent(null, row.getItem());
                            }
                        });
                    }
                });
                rowMenu.getItems().addAll(removeItem);

                // only display context menu for non-null items:
                row.contextMenuProperty().bind(Bindings.when(Bindings.isNotNull(row.itemProperty())).then(rowMenu).otherwise((ContextMenu)null));
                return row;
            }
        });

        this.parameterTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox vbox = new VBox();
        vbox.setFillWidth(true);
        vbox.getChildren().add(this.parameterTableView);
        this.setContent(vbox);

        // listener object for updating tableview
        this.parameterListener = new ParameterListener(this.parameterTableView);

        // Update if new Behaviour is selected
        this.selectedBehaviour.addListener(parameterListener);
    }

    public void setParentViewModel(ViewModelElement parentViewModel) {
        if (this.selectedBehaviour.get() != null) {
            this.selectedBehaviour.get().getParameters().removeListener(this.parameterListener);
        }
        this.selectedBehaviour.set((BehaviourViewModel) parentViewModel);
        this.selectedBehaviour.get().getParameters().addListener(this.parameterListener);
    }

    private boolean fireEvent(Map.Entry<String, String> newValue, Map.Entry<String, String> oldValue) {
        GuiChangeAttributeEvent addKeyValueEvent = new GuiChangeAttributeEvent(GuiEventType.CHANGE_ELEMENT, Types.BEHAVIOUR, selectedBehaviour.get().getName());
        addKeyValueEvent.setElementId(selectedBehaviour.get().getId());
        addKeyValueEvent.setAttributeType(Map.Entry.class.getSimpleName());
        addKeyValueEvent.setAttributeName("parameters");
        if (!newValue.getKey().equals("")) {
            addKeyValueEvent.setNewValue(newValue);
        }
        if (!oldValue.getKey().equals ("")) {
            addKeyValueEvent.setOldValue(oldValue);
        }
        if (addKeyValueEvent.getNewValue() == null && addKeyValueEvent.getOldValue() == null)
        {
            return false;
        }

        MainWindowController.getInstance().getGuiModificationHandler().handle(addKeyValueEvent);
        return true;
    }
}
