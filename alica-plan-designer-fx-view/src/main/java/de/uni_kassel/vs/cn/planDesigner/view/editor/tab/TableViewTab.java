package de.uni_kassel.vs.cn.planDesigner.view.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class TableViewTab<S> extends Tab {

    protected I18NRepo i18NRepo;
    protected TableView<S> table;

    public TableViewTab () {
        table = new TableView<>();
        i18NRepo = I18NRepo.getInstance();
    }

    protected <S,T> TableColumn<S,T> makeColumn(String title, String propertyName, StringConverter<T> converter) {
        TableColumn<S,T> column = new TableColumn<S, T>(title);
        column.setCellValueFactory(new PropertyValueFactory<S,T>(propertyName));
        Callback<TableColumn<S,T>, TableCell<S, T>> defaultTextFieldCellFactory
                = TextFieldTableCell.<S,T>forTableColumn(converter);
        column.setCellFactory(col -> {
            TableCell<S,T> cell = defaultTextFieldCellFactory.call(col);
            cell.setEditable(false);
            return cell;
        });
        return column;
    }

    public void addItem(S viewModelItem) {
        table.getItems().add(viewModelItem);
        table.setPrefHeight(24 * (table.getItems().size() + 1) + 2);
    }
}
