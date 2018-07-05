package de.uni_kassel.vs.cn.planDesigner.view.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.model.VariableViewModel;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.LongStringConverter;

import java.util.function.Function;

import static javafx.scene.control.cell.TextFieldTableCell.forTableColumn;

public class VariablesTab extends Tab {

    private TableView<VariableViewModel> variables;
    private I18NRepo i18NRepo;

    public VariablesTab() {
        i18NRepo = I18NRepo.getInstance();
        setText(i18NRepo.getString("label.variables"));

        variables = new TableView<>();
        TableColumn<VariableViewModel, String> nameColumn = createCol(i18NRepo.getString("label.column.name"), VariableViewModel::nameProperty);
//        TableColumn<VariableViewModel, Long> idColumn = createCol(i18NRepo.getString("label.column.id"), VariableViewModel::idProperty);

        TableColumn<VariableViewModel, Long> idColumn = new TableColumn<>(i18NRepo.getString("label.column.id"));
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());

        Callback<TableColumn<VariableViewModel, Long>, TableCell<VariableViewModel, Long>> defaultTextFieldCellFactory
                = TextFieldTableCell.<VariableViewModel, Long>forTableColumn(new LongStringConverter());
        idColumn.setCellFactory(col -> {
            TableCell<VariableViewModel, Long> cell = defaultTextFieldCellFactory.call(col);
            cell.setEditable(false);
            return cell;
        });

        TableColumn<VariableViewModel, String> typeColumn = createCol(i18NRepo.getString("label.column.elementType"), VariableViewModel::variableTypeProperty);
        TableColumn<VariableViewModel, String> commentColumn = createCol(i18NRepo.getString("label.column.comment"), VariableViewModel::commentProperty);

        variables.getColumns().addAll(nameColumn, idColumn, typeColumn, commentColumn);
        VBox vBox = new VBox();
        HBox hBox = new HBox();
        Button addButton = new Button(i18NRepo.getString("action.list.add"));
        addButton.setOnAction(e -> {
            // TODO: Send event for creating new Variable for this Behaviour and adding it accordingly, when the ModelModificationEvent comes back
        });

        Button deleteButton = new Button(i18NRepo.getString("action.list.remove"));
        deleteButton.setOnAction(e -> {
            VariableViewModel selectedItem = variables.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // TODO: Send event for deleting the selected variable from the behaviour and update the table accordingly
            }
        });
        hBox.getChildren().addAll(addButton, deleteButton);
        vBox.getChildren().addAll(variables, hBox);
        setContent(vBox);
    }

    private <S, T> TableColumn<S, T> createCol(String title, Function<S, ObservableValue<T>> property) {
        TableColumn<S, T> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        return col;
    }

    public void addVariable(VariableViewModel variableViewModel) {
        variables.getItems().add(variableViewModel);
        variables.setPrefHeight(24 * (variables.getItems().size() + 1) + 2);
    }
}
