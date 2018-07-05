package de.uni_kassel.vs.cn.planDesigner.view.properties;

import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

import java.util.function.Function;

public class KeyValuePropertiesTab extends Tab {

    protected TableView<KeyValuePair> keyValues;

    public KeyValuePropertiesTab () {
        super(I18NRepo.getInstance().getString("label.properties"));
        keyValues = new TableView<>();

        keyValues.setEditable(true);

        TableColumn<KeyValuePair, String> nameCol = createCol("Name", KeyValuePair::keyProperty);
        TableColumn<KeyValuePair, String> canEditCol = createCol("Value", KeyValuePair::valueProperty);

        Callback<TableColumn<KeyValuePair, String>, TableCell<KeyValuePair, String>> defaultTextFieldCellFactory
                = TextFieldTableCell.<KeyValuePair>forTableColumn();

        nameCol.setCellFactory(col -> {
            TableCell<KeyValuePair, String> cell = defaultTextFieldCellFactory.call(col);
            cell.setEditable(false);
            return cell;
        });
        canEditCol.setCellFactory(col -> {
            TableCell<KeyValuePair, String> cell = defaultTextFieldCellFactory.call(col);
            cell.itemProperty().addListener((obs, oldValue, newValue) -> {
                TableRow row = cell.getTableRow();
                if (row == null) {
                    cell.setEditable(false);
                } else {
                    KeyValuePair item = (KeyValuePair) cell.getTableRow().getItem();
                    if (item == null) {
                        cell.setEditable(false);
                    } else {
                        if (item.isEditable())

                        {
                            cell.setStyle("-fx-background-color: lightgreen");
                        }
                        cell.setEditable(item.isEditable());

                    }
                }
            });
            return cell ;
        });

        keyValues.getColumns().addAll(nameCol, canEditCol);


        this.setContent(keyValues);
    }

    private <S,T> TableColumn<S,T> createCol(String title, Function<S, ObservableValue<T>> property) {
        TableColumn<S,T> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        return col ;
    }

    public void addKeyValueProperty(String key, String value, boolean editable) {
        keyValues.getItems().add(new KeyValuePair(key, value, editable));
        // 24 pixel per row, +1 row because of headings, +2 pixel for borders
        keyValues.setPrefHeight(24*(keyValues.getItems().size()+1) + 2);
    }
}
