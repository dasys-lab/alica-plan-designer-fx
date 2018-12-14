package de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab;

import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.model.PlanViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;

/**
 * Root gui object for showing properties, conditions, and variables of selected objects in a plan.
 */
public class PropertiesConditionsVariablesPane extends TitledPane {

    protected I18NRepo i18NRepo;

    /**
     * Not all Tabs are shown all the time, it depends on
     * the item the PropertiesConditionsVariablesPane instance
     * should represent.
     */
    protected TabPane tabPane;
    protected PlanPropertiesTable planPropertiesTable;
    protected Tab propertiesTab;
    protected Tab variablesTab;
    protected Tab preconditionTab;
    protected Tab runtimeconditionTab;
    protected Tab postconditionTab;

    public PropertiesConditionsVariablesPane() {
        i18NRepo = I18NRepo.getInstance();

        planPropertiesTable = new PlanPropertiesTable();

        propertiesTab = new Tab(i18NRepo.getString("label.caption.properties"));
        propertiesTab.setContent(planPropertiesTable);
        variablesTab = new Tab(i18NRepo.getString("label.caption.variables"));
        preconditionTab = new Tab(i18NRepo.getString("label.caption.preCondtions"));
        runtimeconditionTab = new Tab(i18NRepo.getString("label.caption.runtimeCondtions"));
        postconditionTab = new Tab(i18NRepo.getString("label.caption.postCondtions"));

        this.tabPane = new TabPane();
        this.tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        this.tabPane.getTabs().addAll(propertiesTab, variablesTab, preconditionTab, runtimeconditionTab, postconditionTab);

        this.setContent(tabPane);
    }

    public void setViewModelElement(ViewModelElement element) {
        this.planPropertiesTable.setPlanViewModel((PlanViewModel) element);
    }

}
