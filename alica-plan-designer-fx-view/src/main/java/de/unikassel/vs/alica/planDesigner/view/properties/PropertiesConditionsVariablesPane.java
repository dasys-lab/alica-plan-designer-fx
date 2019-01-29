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
public class PropertiesConditionsVariablesPane extends TitledPane {

    protected I18NRepo i18NRepo;

    /**
     * Not all Tabs are shown all the time, it depends on
     * the item the PropertiesConditionsVariablesPane instance
     * should represent.
     */
    protected TabPane tabPane;
    protected PropertySheet propertySheet;
    protected Tab propertiesTab;
    protected VariablesTab variablesTab;
    protected Tab preConditionTab;
    protected Tab runtimeConditionTab;
    protected Tab postConditionTab;

    protected IGuiModificationHandler guiModificationHandler;

    public PropertiesConditionsVariablesPane(IGuiModificationHandler handler) {
        i18NRepo = I18NRepo.getInstance();
        guiModificationHandler = handler;

        propertySheet = new PropertySheet();
        propertySheet.setModeSwitcherVisible(false);

        propertiesTab = new Tab(i18NRepo.getString("label.caption.properties"));
        propertiesTab.setContent(propertySheet);
        variablesTab = new VariablesTab(guiModificationHandler);
        preConditionTab = new Tab(i18NRepo.getString("label.caption.preCondtions"));
        runtimeConditionTab = new Tab(i18NRepo.getString("label.caption.runtimeCondtions"));
        postConditionTab = new Tab(i18NRepo.getString("label.caption.postCondtions"));

        this.tabPane = new TabPane();
        this.tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        this.tabPane.getTabs().addAll(propertiesTab, variablesTab, preConditionTab, runtimeConditionTab, postConditionTab);

        this.setContent(tabPane);
    }


    public void setViewModelElement(ViewModelElement element) {
        if (element == null) {
            return;
        }

        setText(element.getName());
        setGraphic(new ImageView(new AlicaIcon(element.getType(), AlicaIcon.Size.SMALL)));

        adaptUI(element.getType());
        variablesTab.setParentViewModel(element);

        propertySheet.getItems().clear();
        propertySheet.getItems().addAll(createPropertySheetList(element));
    }

    private void adaptUI(String type) {
        switch (type) {
            case Types.TASKREPOSITORY:
            case Types.TASK:
            case Types.PLANTYPE:
                this.setContent(propertySheet);
                break;
            case Types.PLAN:
            case Types.MASTERPLAN:
                this.setContent(tabPane);
                tabPane.getTabs().removeAll(postConditionTab);
                if (!tabPane.getTabs().contains(variablesTab)) {
                    tabPane.getTabs().add(variablesTab);
                }
                if (!tabPane.getTabs().contains(preConditionTab)) {
                    tabPane.getTabs().add(preConditionTab);
                }
                if (!tabPane.getTabs().contains(runtimeConditionTab)) {
                    tabPane.getTabs().add(runtimeConditionTab);
                }
                break;
            default:
                this.setContent(tabPane);
                if (!tabPane.getTabs().contains(variablesTab)) {
                    tabPane.getTabs().add(variablesTab);
                }
                if (!tabPane.getTabs().contains(preConditionTab)) {
                    tabPane.getTabs().add(preConditionTab);
                }
                if (!tabPane.getTabs().contains(runtimeConditionTab)) {
                    tabPane.getTabs().add(runtimeConditionTab);
                }
                if (!tabPane.getTabs().contains(postConditionTab)) {
                    tabPane.getTabs().add(postConditionTab);
                }
                break;
        }
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
