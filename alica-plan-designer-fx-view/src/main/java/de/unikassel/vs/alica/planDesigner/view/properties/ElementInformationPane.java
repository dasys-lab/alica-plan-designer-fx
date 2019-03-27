package de.unikassel.vs.alica.planDesigner.view.properties;

import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaIcon;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.BeanPropertyUtils;

/**
 * Root gui object for showing properties, conditions, and variables of selected objects.
 */
public class ElementInformationPane extends TitledPane {


    protected I18NRepo i18NRepo;

    /**
     * Not all Tabs are shown all the time, it depends on
     * the item the ElementInformationPane instance
     * should represent.
     */
    protected TabPane tabPane;
    protected PropertySheet propertySheet;
    protected Tab propertiesTab;
    protected VariablesTab variablesTab;
    protected ParametrisationTab parametrisationTab;
    protected ConditionsTab preConditionTab;
    protected ConditionsTab runtimeConditionTab;
    protected ConditionsTab postConditionTab;

    protected IGuiModificationHandler guiModificationHandler;

    private ViewModelElement elementShown = null;

    public ElementInformationPane(IGuiModificationHandler handler) {
        i18NRepo = I18NRepo.getInstance();
        guiModificationHandler = handler;

        propertySheet = new PropertySheet();
        propertySheet.setModeSwitcherVisible(false);

        propertiesTab = new Tab(i18NRepo.getString("label.caption.properties"));
        propertiesTab.setContent(propertySheet);
        variablesTab = new VariablesTab(guiModificationHandler);
        parametrisationTab = new ParametrisationTab(guiModificationHandler, i18NRepo.getString("label.caption.parametrisation"));
        preConditionTab     = new ConditionsTab(i18NRepo.getString("label.caption.preCondtions")    , Types.PRECONDITION);
        runtimeConditionTab = new ConditionsTab(i18NRepo.getString("label.caption.runtimeCondtions"), Types.RUNTIMECONDITION);
        postConditionTab    = new ConditionsTab(i18NRepo.getString("label.caption.postCondtions")   , Types.POSTCONDITION);

        this.tabPane = new TabPane();
        this.tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        this.tabPane.getTabs().addAll(propertiesTab, variablesTab, parametrisationTab, preConditionTab, runtimeConditionTab, postConditionTab);

        this.setContent(tabPane);
    }


    public void setViewModelElement(ViewModelElement element) {
        if (element == null || element == elementShown) {
            return;
        }

        elementShown = element;

        setText(elementShown.getName());
        setGraphic(new ImageView(new AlicaIcon(elementShown.getType(), AlicaIcon.Size.SMALL)));

        adaptUI(elementShown.getType());
        adaptConditions(elementShown);
        variablesTab.setParentViewModel(elementShown);
        parametrisationTab.setViewModel(elementShown);

        propertySheet.getItems().clear();
        propertySheet.getItems().addAll(createPropertySheetList(elementShown));
    }

    private void adaptUI(String type) {
        tabPane.getTabs().removeAll(preConditionTab, propertiesTab, runtimeConditionTab, variablesTab, postConditionTab, parametrisationTab);
        switch (type) {
            case Types.TASKREPOSITORY:
            case Types.TASK:
            case Types.ENTRYPOINT:
            case Types.SYNCHRONISATION:
                this.setContent(propertySheet);
                break;
            case Types.PLANTYPE:
                this.setContent(tabPane);
                tabPane.getTabs().addAll(propertiesTab, parametrisationTab, variablesTab);
                break;
            case Types.STATE:
                this.setContent(tabPane);
                tabPane.getTabs().addAll(propertiesTab, parametrisationTab);
                break;
            case Types.PLAN:
            case Types.MASTERPLAN:
                this.setContent(tabPane);
                tabPane.getTabs().addAll(propertiesTab, variablesTab, preConditionTab, runtimeConditionTab);
                break;
            case Types.BEHAVIOUR:
                this.setContent(tabPane);
                tabPane.getTabs().addAll(propertiesTab, variablesTab, preConditionTab, runtimeConditionTab, postConditionTab);
                break;
            case Types.SUCCESSSTATE:
            case Types.FAILURESTATE:
                this.setContent(tabPane);
                tabPane.getTabs().addAll(propertiesTab, postConditionTab);
                break;
            case Types.TRANSITION:
                this.setContent(tabPane);
                tabPane.getTabs().addAll(propertiesTab, preConditionTab);
                break;
            default:
                System.err.println("ElementInformationPane: Unknown element type '" + type + "' choosen for showing properties stuff!");
                this.setContent(tabPane);
                tabPane.getTabs().addAll(propertiesTab, variablesTab, preConditionTab, runtimeConditionTab, postConditionTab);
                break;
        }
    }

    private void adaptConditions(ViewModelElement element){
        preConditionTab.setViewModelElement(element);
        runtimeConditionTab.setViewModelElement(element);
        postConditionTab.setViewModelElement(element);
    }


    protected ObservableList<PropertySheet.Item> createPropertySheetList(ViewModelElement element) {
        ObservableList<PropertySheet.Item> list = BeanPropertyUtils.getProperties(element, ViewModelElement.filterProperties(element.getUiPropertyList()));

        PropertySheet.Item[] retList = new PropertySheet.Item[list.size()];
        for (PropertySheet.Item item : list) {
            int idx = element.getUiPropertyList().indexOf(item.getName());
            if (idx != -1) {
                retList[idx] = item;
            } else {
                System.err.println("ElementInformationPane: Unkown PropertySheet.Item Type, because it is maybe missing in the uiPropertyList of the ViewModelElement.");
            }
        }
        ObservableList<PropertySheet.Item> retObsList = FXCollections.observableArrayList();
        retObsList.addAll(retList);
        return retObsList;
    }

}
