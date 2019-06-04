package de.unikassel.vs.alica.planDesigner.view.model;

import de.unikassel.vs.alica.planDesigner.events.GuiChangeAttributeEvent;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.roleTab.characteristics.CharacteristicViewModelCreatable;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.roleTab.roles.RoleTableView;
import javafx.beans.property.SimpleStringProperty;

import java.util.Arrays;

public class CharacteristicViewModel extends PlanElementViewModel {

    protected RoleTableView roleTableView;
    protected RoleViewModel roleViewModel;
    protected SimpleStringProperty value = new SimpleStringProperty(null, "value", "");
    protected SimpleStringProperty weight = new SimpleStringProperty(null, "weight", "");

    public CharacteristicViewModel (long id, String name, String type, RoleTableView roleTableView) {
        super(id, name, type);
        this.roleTableView = roleTableView;
        this.uiPropertyList.clear();
        this.uiPropertyList.addAll(Arrays.asList("name", "id", "comment", "value", "weight"));
    }

    @Override
    public void registerListener(IGuiModificationHandler handler) {
        super.registerListener(handler);
        value.addListener((observable, oldValue, newValue) -> {
            fireGUIAttributeChangeEvent(handler, newValue, value.getClass().getSimpleName(), value.getName());
        });
        weight.addListener((observable, oldValue, newValue) -> {
            fireGUIAttributeChangeEvent(handler, newValue, weight.getClass().getSimpleName(), weight.getName());
        });
    }

    @Override
    protected void fireGUIAttributeChangeEvent(IGuiModificationHandler handler, Object newValue, String attributeType, String attributeName) {

        switch (attributeName) {
            case "name":
//                if (getId() == 0 && getParentId() == 0) {
                if (this instanceof CharacteristicViewModelCreatable) {
                    GuiModificationEvent event = new GuiModificationEvent(GuiEventType.CREATE_ELEMENT, getType(), String.valueOf(newValue));
                    event.setParentId(this.roleTableView.getSelectedItem().idProperty().getValue());
                    handler.handle(event);
                }
                else {
                    GuiChangeAttributeEvent guiChangeAttributeEvent = new GuiChangeAttributeEvent(GuiEventType.CHANGE_ELEMENT, getType(), getName());
                    guiChangeAttributeEvent.setNewValue(newValue);
                    guiChangeAttributeEvent.setAttributeType(attributeType);
                    guiChangeAttributeEvent.setAttributeName(attributeName);
                    guiChangeAttributeEvent.setElementId(getId());
                    guiChangeAttributeEvent.setParentId(getParentId());
                    handler.handle(guiChangeAttributeEvent);
                }
                break;
            case "value":
            case "weight":
                if (getId() == 0 && getParentId() == 0)
                    break;
                GuiChangeAttributeEvent guiChangeAttributeEvent = new GuiChangeAttributeEvent(GuiEventType.CHANGE_ELEMENT, getType(), getName());
                guiChangeAttributeEvent.setNewValue(newValue);
                guiChangeAttributeEvent.setAttributeType(attributeType);
                guiChangeAttributeEvent.setAttributeName(attributeName);
                guiChangeAttributeEvent.setElementId(getId());
                guiChangeAttributeEvent.setParentId(getParentId());
                handler.handle(guiChangeAttributeEvent);
                break;
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
    public void setWeight(String weight) {
        this.weight.setValue(weight);
    }
    public String getWeight() {
        return weight.get();
    }

}
