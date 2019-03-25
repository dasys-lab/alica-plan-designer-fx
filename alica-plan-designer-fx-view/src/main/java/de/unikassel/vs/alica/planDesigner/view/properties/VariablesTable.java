package de.unikassel.vs.alica.planDesigner.view.properties;

import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public abstract class VariablesTable<S extends ViewModelElement> extends VBox {

    HBox addDeleteHBox;
    PropertiesTable<S> table;
    I18NRepo i18NRepo;

    public VariablesTable() {
        super();
        i18NRepo = I18NRepo.getInstance();
        table = new PropertiesTable<>();
        table.setEditable(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Add & Remove Buttons
        Button addButton = new Button(i18NRepo.getString("action.list.addShort"));
        addButton.setOnAction(e -> {
            onAddElement();
        });

        Button deleteButton = new Button(i18NRepo.getString("action.list.removeShort"));
        deleteButton.setOnAction(e -> {
            S selectedItem = table.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                onRemoveElement();
            }
        });

        addDeleteHBox = new HBox();
        addDeleteHBox.getChildren().addAll(addButton, deleteButton);

        getChildren().addAll(table, addDeleteHBox);
    }

    public <T> void addColumn(String title, String propertyName, StringConverter<T> converter, boolean editable) {
        table.addColumn(title, propertyName, converter, editable);

    }

    public void addItem(S viewModel) {
        table.addItem(viewModel);
    }

    public void removeItem(S viewModel) {
        table.removeItem(viewModel);
    }

    public void clear() {
        table.clear();
    }

    public S getSelectedItem() {
        return table.getSelectionModel().getSelectedItem();
    }

    protected abstract void onAddElement();

    protected abstract void onRemoveElement();
}
