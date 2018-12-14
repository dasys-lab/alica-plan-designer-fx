package de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab;

import de.unikassel.vs.alica.planDesigner.view.model.PlanViewModel;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

public class PlanPropertiesTable<S> extends TableView<S> {

    protected PlanViewModel planViewModel;

    public PlanPropertiesTable () {
        this.addColumn("Key", "None", new DefaultStringConverter(), true);
        this.addColumn("Value", "None", new DefaultStringConverter(), true);
    }

    protected <T> void addColumn(String title, String propertyName, StringConverter<T> converter, boolean editable) {
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

    /**
     * Removes all items and set the new one.
     * @param viewModelItem
     */
    public void setItem(S viewModelItem) {
        getItems().clear();
        getItems().add(viewModelItem);

        // TODO: depends on the number of properties to show...
//        setPrefHeight(24 * (getItems().size() + 1) + 2);
    }

}
