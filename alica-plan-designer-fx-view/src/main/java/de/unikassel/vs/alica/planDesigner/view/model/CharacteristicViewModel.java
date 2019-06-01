package de.unikassel.vs.alica.planDesigner.view.model;

import de.unikassel.vs.alica.planDesigner.events.GuiChangeAttributeEvent;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.roleTab.RoleListView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Arrays;

public class CharacteristicViewModel extends PlanElementViewModel {

    protected RoleListView roleListView;
    protected RoleViewModel roleViewModel;
//    private SimpleStringProperty name = new SimpleStringProperty(null, "name", "");
    protected SimpleStringProperty value = new SimpleStringProperty(null, "value", "");
    protected SimpleStringProperty weight = new SimpleStringProperty(null, "weight", "");

    public CharacteristicViewModel (long id, String name, String type, RoleListView roleListView) {
        super(id, name, type);
        this.roleListView = roleListView;
        this.uiPropertyList.clear();
        this.uiPropertyList.addAll(Arrays.asList("name", "id", "comment", "value", "weight"));
    }

    @Override
    public void registerListener(IGuiModificationHandler handler) {
        super.registerListener(handler);
//        masterPlan.addListener((observable, oldValue, newValue) -> {
//            fireGUIAttributeChangeEvent(handler, newValue, masterPlan.getClass().getSimpleName(), masterPlan.getName());
//        });
//        utilityThreshold.addListener((observable, oldValue, newValue) -> {
//            fireGUIAttributeChangeEvent(handler, newValue, utilityThreshold.getClass().getSimpleName(), utilityThreshold.getName());
//        });
    }

    @Override
    protected void fireGUIAttributeChangeEvent(IGuiModificationHandler handler, Object newValue, String attributeType, String attributeName) {

        if (idProperty().getValue() == 0 && getParentId() == 0) {
            GuiModificationEvent event = new GuiModificationEvent(GuiEventType.CREATE_ELEMENT, getType(), String.valueOf(newValue));
            event.setParentId(this.roleListView.getSelectedItem().idProperty().getValue());
            handler.handle(event);
        }
        else {
            GuiChangeAttributeEvent guiChangeAttributeEvent = new GuiChangeAttributeEvent(GuiEventType.CHANGE_ELEMENT, getType(), getName());
            guiChangeAttributeEvent.setNewValue(newValue);
            guiChangeAttributeEvent.setAttributeType(attributeType);
            guiChangeAttributeEvent.setAttributeName(attributeName);
            guiChangeAttributeEvent.setElementId(getId());
            handler.handle(guiChangeAttributeEvent);
        }
    }

    public RoleViewModel getRoleViewModel() {
        return roleViewModel;
    }
    public void setRoleViewModel(RoleViewModel roleViewModel) {
        this.roleViewModel = roleViewModel;
    }


    public final SimpleStringProperty valueProperty() {return value; }
    public void setValue(String value) {
        this.value.setValue(value);
    }
    public String getValue() {
        return value.get();
    }

    public final SimpleStringProperty weightProperty() {return weight; }
    public void setweight(String weight) {
        this.weight.setValue(weight);
    }
    public String getWeight() {
        return weight.get();
    }

}
