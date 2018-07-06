package de.uni_kassel.vs.cn.planDesigner.view.editor.tab.behaviourTab;

import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.Types;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.QuantifierTab;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.VariablesTab;
import de.uni_kassel.vs.cn.planDesigner.view.model.ConditionViewModel;
import de.uni_kassel.vs.cn.planDesigner.view.model.QuantifierViewModel;
import de.uni_kassel.vs.cn.planDesigner.view.model.VariableViewModel;
import de.uni_kassel.vs.cn.planDesigner.view.properties.PropertiesTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.LongStringConverter;

import java.util.ArrayList;

/**
 * TODO: Maybe rename/refactor for general condition representation
 */
public class BehaviourConditionTitledPane extends TitledPane {

    protected ConditionViewModel condition;
    protected I18NRepo i18NRepo;
    PropertiesTable<ConditionViewModel> propertiesTable;
    private ObservableList<String> pluginNames;

    public BehaviourConditionTitledPane(ConditionViewModel condition, String type) {
        super();
        setText(type);
        i18NRepo = I18NRepo.getInstance();
        this.condition = condition;
        this.pluginNames = FXCollections.observableArrayList();


        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        Tab propertiesTab = new Tab();
        propertiesTab.setText(i18NRepo.getString("label.caption.properties"));
        propertiesTable = new PropertiesTable<>();
        propertiesTable.addColumn(i18NRepo.getString("label.column.name"), "name", new DefaultStringConverter(), true);
        propertiesTable.addColumn(i18NRepo.getString("label.column.id"), "id", new LongStringConverter(), false);
        propertiesTable.addColumn(i18NRepo.getString("label.column.elementType"), "variableType", new DefaultStringConverter(), true);
        propertiesTable.addColumn(i18NRepo.getString("label.column.comment"), "comment", new DefaultStringConverter(), true);
        addComboColumn(i18NRepo.getString("label.column.pluginName"), "pluginName", new DefaultStringConverter(), true);
        propertiesTab.setContent(propertiesTable);


        VariablesTab variablesTab = new VariablesTab();
        QuantifierTab quantifierTab = new QuantifierTab();
        if (condition != null) {
            for (VariableViewModel variableViewModel : condition.getVars()) {
                variablesTab.addItem(variableViewModel);
            }
            for (QuantifierViewModel quantifierViewModel : condition.getQuantifier()) {
                quantifierTab.addItem(quantifierViewModel);
            }
        }

        tabPane.getTabs().addAll(propertiesTab, variablesTab, quantifierTab);

        this.setContent(tabPane);
    }

    public void addPluginName(String pluginName) {
        this.pluginNames.add(pluginName);
    }

    public void removePluginName(String pluginName) {
        this.pluginNames.remove(pluginName);
    }

    public void addComboColumn(String title, String propertyName, StringConverter converter, boolean editable) {
        TableColumn<ConditionViewModel, String> column = new TableColumn(title);
        column.setCellValueFactory(new PropertyValueFactory<ConditionViewModel, String>(propertyName));
        Callback<TableColumn<ConditionViewModel, String>, TableCell<ConditionViewModel, String>> defaultComboBoxCellFactory
                = ComboBoxTableCell.<ConditionViewModel, String>forTableColumn(converter);
        column.setCellFactory(col -> {
            ComboBoxTableCell<ConditionViewModel, String> cell = (ComboBoxTableCell<ConditionViewModel, String>) defaultComboBoxCellFactory.call(col);
            cell.getItems().setAll(this.pluginNames);
            cell.setEditable(editable);
            return cell;
        });
        propertiesTable.getColumns().add(column);
        // 24 Pixel * (1 empty row + 1 heading) + 2 pixel for border
        propertiesTable.setPrefHeight(24 * (1 + 1) + 2);
    }

    public void setCondition(ConditionViewModel condition) {
        this.condition = condition;
    }
}