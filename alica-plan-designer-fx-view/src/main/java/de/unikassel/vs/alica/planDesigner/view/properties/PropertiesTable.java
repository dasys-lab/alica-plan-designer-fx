package de.unikassel.vs.alica.planDesigner.view.properties;

import de.unikassel.vs.alica.planDesigner.view.model.RoleViewModel;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Font;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class PropertiesTable<S> extends TableView<S> {

    public <T> void addColumn(String title, String propertyName, StringConverter<T> converter, boolean editable) {
        addColumn(title, propertyName, converter, editable, null);
    }

    public <T> void addColumn(String title, String propertyName, StringConverter<T> converter, boolean editable, EventHandler<TableColumn.CellEditEvent<S, T>> listener) {
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
        if (listener != null) {
            column.setOnEditCommit(listener);
        }
        resizeTable();
    }

    public void addItem(S viewModelItem) {
        getItems().add(viewModelItem);
        resizeTable();
    }

    public void clear() {
        getItems().clear();
        resizeTable();
    }

    public void removeItem(S viewModelItem) {
        getItems().remove(viewModelItem);
        resizeTable();
    }

    private void resizeTable() {
        int elements = this.getItems().size();
        double fontSize = Font.getDefault().getSize() * 2;

        // FontSize * (1 empty row + 1 heading) + 2 pixel for border
        this.setPrefHeight(fontSize * (elements + 1) + 2);
    }
}
