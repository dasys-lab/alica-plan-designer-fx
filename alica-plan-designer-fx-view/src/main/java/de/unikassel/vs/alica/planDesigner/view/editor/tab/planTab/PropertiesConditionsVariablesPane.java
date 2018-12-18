package de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab;

import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.BeanPropertyUtils;

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
    protected PropertySheet planPropertySheet;
    protected Tab propertiesTab;
    protected Tab variablesTab;
    protected Tab preconditionTab;
    protected Tab runtimeconditionTab;
    protected Tab postconditionTab;

    public PropertiesConditionsVariablesPane() {
        i18NRepo = I18NRepo.getInstance();

        planPropertySheet = new PropertySheet();
        planPropertySheet.setModeSwitcherVisible(false);

        propertiesTab = new Tab(i18NRepo.getString("label.caption.properties"));
        propertiesTab.setContent(planPropertySheet);
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
        planPropertySheet.getItems().addAll(createPropertySheetList(element));
    }

    protected ObservableList<PropertySheet.Item> createPropertySheetList(ViewModelElement element) {
        ObservableList<PropertySheet.Item> list = BeanPropertyUtils.getProperties(element, ViewModelElement.filterProperties(element.getUiPropertyList()));

        PropertySheet.Item[] retList = new PropertySheet.Item[list.size()];
        for (PropertySheet.Item item : list) {
            int idx = element.getUiPropertyList().indexOf(item.getName());
            if (idx != -1) {
                retList[idx] = item;
            } else {
                System.err.println("PropertiesConditionsVariablesPane: Unkown PropertySheet.Item Type, because it is maybe missing in the uiPropertyList of the ViewModelElement.");
            }
        }
        ObservableList<PropertySheet.Item> retObsList = FXCollections.observableArrayList();
        retObsList.addAll(retList);
        return retObsList;
    }
}
