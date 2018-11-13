package de.unikassel.vs.alica.planDesigner.view.editor.tab;

import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.model.ConditionViewModel;
import de.unikassel.vs.alica.planDesigner.view.properties.PropertiesTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.DefaultStringConverter;

public class ConditionsTitledPane extends TitledPane {

    private ObservableList<String> pluginNames;
    protected I18NRepo i18NRepo;
    PropertiesTable<ConditionViewModel> conditionPropertiesTable;
    ConditionViewModel conditionViewModel;

    public ConditionsTitledPane(String type, ConditionViewModel conditionViewModel) {
        super();
        setText(type);
        i18NRepo = I18NRepo.getInstance();
        this.conditionViewModel = conditionViewModel;
        this.pluginNames = FXCollections.observableArrayList();

        conditionPropertiesTable = new PropertiesTable<ConditionViewModel>();
        conditionPropertiesTable.setEditable(true);
        conditionPropertiesTable.addColumn(i18NRepo.getString("label.column.name"), "name", new DefaultStringConverter(), true);
        conditionPropertiesTable.addColumn(i18NRepo.getString("label.column.comment"), "comment", new DefaultStringConverter(), true);
        conditionPropertiesTable.addColumn(i18NRepo.getString("label.column.pluginName"), "pluginName", new DefaultStringConverter(), true);
        conditionPropertiesTable.addColumn(i18NRepo.getString("label.column.enabled"), "enabled", new BooleanStringConverter(), true);

        // Variables & Quantifier Tabs
        VariablesTab variablesTab = new VariablesTab(conditionViewModel);
        QuantifiersTab quantifiersTab = new QuantifiersTab();

        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getTabs().addAll(variablesTab, quantifiersTab);

        // TODO: ask for the GUI of the condition plugin and integrate it

        // vBox with all parts in it
        VBox vBox = new VBox();
        vBox.getChildren().addAll(conditionPropertiesTable, tabPane);
        vBox.setSpacing(0);
        vBox.setPadding(new Insets(0,0,10,0));
        this.setContent(vBox);
        this.setExpanded(false);
    }

    public void addPluginName(String pluginName) {
        this.pluginNames.add(pluginName);
    }

    public void removePluginName(String pluginName) {
        this.pluginNames.remove(pluginName);
    }

}