package de.uni_kassel.vs.cn.planDesigner.view.properties;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class PropertiesTable<S> extends TableView<S> {

    public <T> void addColumn(String title, String propertyName, StringConverter<T> converter, boolean editable) {
        TableColumn<S, T> column = new TableColumn<S, T>(title);
        column.setCellValueFactory(new PropertyValueFactory<S, T>(propertyName));
        Callback<TableColumn<S, T>, TableCell<S, T>> defaultTextFieldCellFactory
                = TextFieldTableCell.<S, T>forTableColumn(converter);
        column.setCellFactory(col -> {
            TableCell<S, T> cell = defaultTextFieldCellFactory.call(col);
            cell.setEditable(editable);
            return cell;
        });
        getColumns().add(column);
        // 24 Pixel * (1 empty row + 1 heading) + 2 pixel for border
        setPrefHeight(24 * (1 + 1) + 2);
    }

    public void addItem(S viewModelItem) {
        getItems().add(viewModelItem);
        // 24 Pixel * (#items + 1 heading) + 2 pixel for border
        setPrefHeight(24 * (getItems().size() + 1) + 2);
    }
}
