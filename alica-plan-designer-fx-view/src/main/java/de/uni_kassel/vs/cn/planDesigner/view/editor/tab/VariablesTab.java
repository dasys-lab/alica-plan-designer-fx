package de.uni_kassel.vs.cn.planDesigner.view.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.model.VariableViewModel;
import de.uni_kassel.vs.cn.planDesigner.view.properties.PropertiesTable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.LongStringConverter;

public class VariablesTab extends Tab {

    PropertiesTable<VariableViewModel> table;
    I18NRepo i18NRepo;

    public VariablesTab() {
        super();
        i18NRepo = I18NRepo.getInstance();
        setText(i18NRepo.getString("label.caption.variables"));

        table = new PropertiesTable<>();
        table.addColumn(i18NRepo.getString("label.column.name"), "nameProperty", new DefaultStringConverter());
        table.addColumn(i18NRepo.getString("label.column.id"), "idProperty", new LongStringConverter());
        table.addColumn(i18NRepo.getString("label.column.elementType"), "variableTypeProperty", new DefaultStringConverter());
        table.addColumn(i18NRepo.getString("label.column.comment"), "commentProperty", new DefaultStringConverter());

        VBox variablesButtonVBox = new VBox();
        HBox addDeleteHBox = new HBox();
        Button addButton = new Button(i18NRepo.getString("action.list.add"));
        addButton.setOnAction(e -> {
            // TODO: Send event for creating new Variable for this Behaviour and adding it accordingly, when the ModelModificationEvent comes back
        });

        Button deleteButton = new Button(i18NRepo.getString("action.list.remove"));
        deleteButton.setOnAction(e -> {
            VariableViewModel selectedItem = table.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // TODO: Send event for deleting the selected variable from the behaviour and update the table accordingly
            }
        });
        addDeleteHBox.getChildren().addAll(addButton, deleteButton);
        variablesButtonVBox.getChildren().addAll(table, addDeleteHBox);
        setContent(variablesButtonVBox);
    }

    public void addItem(VariableViewModel viewModel) {
        this.table.addItem(viewModel);
        table.setPrefHeight(24*(table.getItems().size()+1)+2);
    }

}
