package de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab;

import de.unikassel.vs.alica.planDesigner.view.model.PlanViewModel;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.Pair;


public class PlanPropertiesTable extends TableView<Pair<String, Object>> {

    protected PlanViewModel planViewModel;

    public PlanPropertiesTable () {
//        PropertySheet
        // table definition
        //TODO replay strings with i18n lookup
        TableColumn<Pair<String, Object>, String> nameColumn = new TableColumn<>("Key");
        nameColumn.setPrefWidth(100);
        TableColumn<Pair<String, Object>, Object> valueColumn = new TableColumn<>("Value");
        valueColumn.setSortable(false);
        valueColumn.setPrefWidth(150);

        nameColumn.setCellValueFactory(new PairKeyFactory());
        valueColumn.setCellValueFactory(new PairValueFactory());

        this.getColumns().setAll(nameColumn, valueColumn);

        valueColumn.setCellFactory(new Callback<TableColumn<Pair<String, Object>, Object>, TableCell<Pair<String, Object>, Object>>() {
            @Override
            public TableCell<Pair<String, Object>, Object> call(TableColumn<Pair<String, Object>, Object> column) {
                PairValueCell cell = new PairValueCell();
                cell.setEditable(true);
                cell.itemProperty().addListener((obs, oldValue, newValue) -> {
                    TableRow row = cell.getTableRow();
                    if (row == null) {
                        cell.setEditable(false);
                    } else {
                        Pair<String, Object> item = (Pair<String, Object>) cell.getTableRow().getItem();
                        if (item != null && item.getKey() == "Comment") {
                            cell.setEditable(true);
                        }
                    }
//                    cell.pseudoClassStateChanged(editableCssClass, cell.isEditable());
                });
                return cell;
            }
        });
    }

    public void setPlanViewModel(PlanViewModel planViewModel) {
        this.planViewModel = planViewModel;
        this.getItems().clear();
        // Name
        this.getItems().add(new Pair<>("Name", planViewModel.nameProperty()));
        this.getItems().add(new Pair<>("ID", planViewModel.idProperty()));
        this.getItems().add(new Pair<>("Comment", planViewModel.commentProperty()));
        this.getItems().add(new Pair<>("Master Plan", planViewModel.masterPlanProperty()));
        this.getItems().add(new Pair<>("Utility Threshold", planViewModel.utilityThresholdProperty()));
        this.getItems().add(new Pair<>("Relative Directory", planViewModel.relativeDirectoryProperty()));
    }

    /**
     * Alot nested classes for special cell handling stuff.
     * From: https://stackoverflow.com/questions/17067481/javafx-2-tableview-different-cell-factory-depending-on-the-data-inside-the-cel)
      */

    class PairKeyFactory implements Callback<TableColumn.CellDataFeatures<Pair<String, Object>, String>, ObservableValue<String>> {
        @Override
        public ObservableValue<String> call(TableColumn.CellDataFeatures<Pair<String, Object>, String> data) {
            return new ReadOnlyObjectWrapper<>(data.getValue().getKey());
        }
    }

    class PairValueFactory implements Callback<TableColumn.CellDataFeatures<Pair<String, Object>, Object>, ObservableValue<Object>> {
        @SuppressWarnings("unchecked")
        @Override
        public ObservableValue<Object> call(TableColumn.CellDataFeatures<Pair<String, Object>, Object> data) {
            Object value = data.getValue().getValue();
            return (value instanceof ObservableValue)
                    ? (ObservableValue) value
                    : new ReadOnlyObjectWrapper<>(value);
        }
    }

    class PairValueCell extends TextFieldTableCell<Pair<String, Object>, Object> {
        @Override
        public void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);

            if (item != null) {
                if (item instanceof String) {
                    setText((String) item);
                    setGraphic(null);
                } else if (item instanceof SimpleStringProperty) {
                    setText(((SimpleStringProperty) item).get());
                    setGraphic(null);
                } else if (item instanceof Boolean) {
                    CheckBox checkBox = new CheckBox();
                    checkBox.setSelected((boolean) item);
                    setGraphic(checkBox);
                } else if (item instanceof Double) {
                    setText(Double.toString((Double) item));
                    setGraphic(null);
                } else if (item instanceof Long) {
                    setText(Long.toString((Long) item));
                    setGraphic(null);
                } else {
                    setText("N/A");
                    setGraphic(null);
                }
            } else {
                setText(null);
                setGraphic(null);
            }
        }
    }

}
