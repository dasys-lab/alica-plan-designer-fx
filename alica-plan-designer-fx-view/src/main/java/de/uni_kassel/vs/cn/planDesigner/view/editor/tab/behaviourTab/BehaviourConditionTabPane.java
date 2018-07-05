package de.uni_kassel.vs.cn.planDesigner.view.editor.tab.behaviourTab;

import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.QuantifierTab;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.VariablesTab;
import de.uni_kassel.vs.cn.planDesigner.view.model.ConditionViewModel;
import de.uni_kassel.vs.cn.planDesigner.view.model.QuantifierViewModel;
import de.uni_kassel.vs.cn.planDesigner.view.model.VariableViewModel;
import de.uni_kassel.vs.cn.planDesigner.view.properties.PropertiesTable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.LongStringConverter;

public class BehaviourConditionTabPane extends TabPane {

    protected ConditionViewModel condition;
    protected I18NRepo i18NRepo;

    public BehaviourConditionTabPane(ConditionViewModel condition) {
        super();
        i18NRepo = I18NRepo.getInstance();
        this.condition = condition;

        Tab propertiesTab = new Tab();
        PropertiesTable<ConditionViewModel> propertiesTable = new PropertiesTable<>();
        propertiesTable.addColumn(i18NRepo.getString("label.column.name"), "nameProperty", new DefaultStringConverter());
        propertiesTable.addColumn(i18NRepo.getString("label.column.id"), "idProperty", new LongStringConverter());
        propertiesTable.addColumn(i18NRepo.getString("label.column.elementType"), "variableTypeProperty", new DefaultStringConverter());
        propertiesTable.addColumn(i18NRepo.getString("label.column.comment"), "commentProperty", new DefaultStringConverter());
        propertiesTab.setContent(propertiesTable);


        VariablesTab variablesTab = new VariablesTab();
        for (VariableViewModel variableViewModel : condition.getVars()) {
            variablesTab.addItem(variableViewModel);
        }

        QuantifierTab quantifierTab = new QuantifierTab();
        for (QuantifierViewModel quantifierViewModel : condition.getQuantifier()) {
            quantifierTab.addItem(quantifierViewModel);
        }

        getTabs().addAll(propertiesTab, variablesTab, quantifierTab);
    }

    public void setCondition(ConditionViewModel condition) {
        this.condition = condition;
    }
}