package de.uni_kassel.vs.cn.planDesigner.view.editor.tab.behaviourTab;

import de.uni_kassel.vs.cn.planDesigner.events.GuiModificationEvent;
import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.ConditionsTitledPane;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.IEditorTab;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.VariablesTab;
import de.uni_kassel.vs.cn.planDesigner.view.model.BehaviourViewModel;
import de.uni_kassel.vs.cn.planDesigner.view.model.VariableViewModel;
import de.uni_kassel.vs.cn.planDesigner.view.model.ViewModelElement;
import de.uni_kassel.vs.cn.planDesigner.view.properties.PropertiesTable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LongStringConverter;

public class BehaviourTab extends Tab implements IEditorTab {

    PropertiesTable<BehaviourViewModel> propertiesTable;
    VariablesTab variablesTab;
    VBox contentList;
    BehaviourViewModel behaviourViewModel;
    I18NRepo i18NRepo;

    public BehaviourTab(BehaviourViewModel behaviourViewModel) {
        super(I18NRepo.getInstance().getString("label.caption.behaviour") + ": " + behaviourViewModel.getName());
        i18NRepo = I18NRepo.getInstance();
        this.behaviourViewModel = behaviourViewModel;

        // Properties
        propertiesTable = new PropertiesTable();
        propertiesTable.setEditable(true);
        propertiesTable.addColumn(i18NRepo.getString("label.column.name"), "name", new DefaultStringConverter(), true);
        propertiesTable.addColumn(i18NRepo.getString("label.column.id"), "id", new LongStringConverter(), false);
        propertiesTable.addColumn(i18NRepo.getString("label.column.comment"), "comment", new DefaultStringConverter(), true);
        propertiesTable.addColumn(i18NRepo.getString("label.column.relDir"), "relativeDirectory", new DefaultStringConverter(), false);
        propertiesTable.addColumn(i18NRepo.getString("label.column.frequency"), "frequency", new IntegerStringConverter(), true);
        propertiesTable.addColumn(i18NRepo.getString("label.column.deferring"), "deferring", new LongStringConverter(), true);
        propertiesTable.addItem(behaviourViewModel);

        // Variables
        variablesTab = new VariablesTab();
        for (VariableViewModel variableViewModel : behaviourViewModel.getVariables()) {
            variablesTab.addItem(variableViewModel);
        }
        TabPane variablesTabPane = new TabPane();
        variablesTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        variablesTabPane.getTabs().addAll(variablesTab);

        // Behaviours Conditions
        ConditionsTitledPane preConditionTabPane = new ConditionsTitledPane(i18NRepo.getString("label.caption.preCondtions"));
        ConditionsTitledPane runtimeConditionTabPane = new ConditionsTitledPane(i18NRepo.getString("label.caption.runtimeCondtions"));
        ConditionsTitledPane postConditionTabPane = new ConditionsTitledPane(i18NRepo.getString("label.caption.postCondtions"));

        contentList = new VBox();
        contentList.getChildren().addAll(propertiesTable, variablesTabPane, preConditionTabPane, runtimeConditionTabPane, postConditionTabPane);
        setContent(contentList);
    }

    @Override
    public boolean representsViewModelElement(ViewModelElement element) {
        if (this.behaviourViewModel.equals(element) || this.behaviourViewModel.getId() == element.getParentId()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ViewModelElement getViewModelElement() {
        return behaviourViewModel;
    }

    @Override
    public GuiModificationEvent handleDelete() {
        System.err.println("BehaviourTab: HandleDelete is a no-op for behaviour tabs!");
        return null;
    }

    @Override
    public void save() {
        System.err.println("BehaviourTab: Save not implemented, yet!");
    }
}
