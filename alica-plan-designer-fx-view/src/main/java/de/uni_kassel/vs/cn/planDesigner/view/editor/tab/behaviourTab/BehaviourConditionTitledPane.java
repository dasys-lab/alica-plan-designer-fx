package de.uni_kassel.vs.cn.planDesigner.view.editor.tab.behaviourTab;

import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.Types;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.QuantifierTab;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.VariablesTab;
import de.uni_kassel.vs.cn.planDesigner.view.model.ConditionViewModel;
import de.uni_kassel.vs.cn.planDesigner.view.model.QuantifierViewModel;
import de.uni_kassel.vs.cn.planDesigner.view.model.VariableViewModel;
import de.uni_kassel.vs.cn.planDesigner.view.properties.PropertiesTable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.LongStringConverter;

/**
 * TODO: Maybe rename/refactor for general condition representation
 */
public class BehaviourConditionTitledPane extends TitledPane {

    protected ConditionViewModel condition;
    protected I18NRepo i18NRepo;

    public BehaviourConditionTitledPane(ConditionViewModel condition, String type) {
        super();
        setText(type);
        i18NRepo = I18NRepo.getInstance();
        this.condition = condition;


        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        Tab propertiesTab = new Tab();
        propertiesTab.setText(i18NRepo.getString("label.caption.properties"));
        PropertiesTable<ConditionViewModel> propertiesTable = new PropertiesTable<>();
        propertiesTable.addColumn(i18NRepo.getString("label.column.name"), "name", new DefaultStringConverter(), true);
        propertiesTable.addColumn(i18NRepo.getString("label.column.id"), "id", new LongStringConverter(), false);
        propertiesTable.addColumn(i18NRepo.getString("label.column.elementType"), "variableType", new DefaultStringConverter(), true);
        propertiesTable.addColumn(i18NRepo.getString("label.column.comment"), "comment", new DefaultStringConverter(), true);
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

    public void setCondition(ConditionViewModel condition) {
        this.condition = condition;
    }
}