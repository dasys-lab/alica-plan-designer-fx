package de.unikassel.vs.alica.planDesigner.view.model;

import de.unikassel.vs.alica.planDesigner.events.GuiChangeAttributeEvent;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

public class ViewModelElement {
    protected final SimpleLongProperty id = new SimpleLongProperty(null, "id", 0);
    protected final SimpleStringProperty name = new SimpleStringProperty(null, "name", "");
    protected final SimpleStringProperty type = new SimpleStringProperty(null, "type", "");
    protected final SimpleStringProperty relativeDirectory = new SimpleStringProperty(null, "relativeDirectory", "");
    protected final SimpleLongProperty parentId = new SimpleLongProperty(null, "parentId", 0);
    protected ArrayList<String> uiPropertyList;

    public ViewModelElement(long id, String name, String type) {
        this.id.setValue(id);
        this.name.setValue(name);
        this.type.setValue(type);

        this.uiPropertyList = new ArrayList<>();
        this.uiPropertyList.addAll(Arrays.asList("name", "id", "relativeDirectory"));
    }

    public ViewModelElement(long id, String name, String type, String relativeDirectory) {
        this(id, name, type);
        this.relativeDirectory.setValue(relativeDirectory);
    }

    public void registerListener(IGuiModificationHandler handler) {
        name.addListener((observable, oldValue, newValue) -> {
            fireGUIAttributeChangeEvent(handler, newValue, name.getClass().getSimpleName(), name.getName());
        });
        relativeDirectory.addListener((observable, oldValue, newValue) -> {
            fireGUIAttributeChangeEvent(handler, newValue, relativeDirectory.getClass().getSimpleName(), relativeDirectory.getName());
        });
    }

    protected void fireGUIAttributeChangeEvent(IGuiModificationHandler handler, Object newValue, String attributeType, String attributeName) {
        GuiChangeAttributeEvent guiChangeAttributeEvent = new GuiChangeAttributeEvent(GuiEventType.CHANGE_ELEMENT, getType(), getName());
        guiChangeAttributeEvent.setNewValue(newValue);
        guiChangeAttributeEvent.setAttributeType(attributeType);
        guiChangeAttributeEvent.setAttributeName(attributeName);
        guiChangeAttributeEvent.setElementId(getId());
        handler.handle(guiChangeAttributeEvent);
    }

    public final SimpleLongProperty idProperty() {
        return this.id;
    }
    public long getId() {
        return this.id.get();
    }

    public final SimpleStringProperty nameProperty() {
        return this.name;
    }
    public String getName() {
        return name.get();
    }
    public void setName(String name) {
        this.name.set(name);
    }

    public final SimpleStringProperty typeProperty() { return this.type; }
    public String getType() {
        return type.get();
    }

    public final SimpleStringProperty relativeDirectoryProperty() {return this.relativeDirectory; }
    public void setRelativeDirectory(String relativeDirectory) {this.relativeDirectory.set(relativeDirectory);}
    public String getRelativeDirectory() {return this.relativeDirectory.get();}

    public void setParentId(long id) {
        this.parentId.setValue(id);
    }

    public long getParentId() {
        return parentId.get();
    }

    public ArrayList<String> getUiPropertyList() {
        return uiPropertyList;
    }

    public static Predicate<PropertyDescriptor> filterProperties(ArrayList<String> uiPropertyList) {
        return p -> uiPropertyList.contains(p.getName());
    }

    public String toString() {
        return type + ": " + name + "(" + id + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ViewModelElement) {
            ViewModelElement otherElement = (ViewModelElement) other;
            return this.getId() == otherElement.getId() && this.getName().equals(otherElement.getName());
        } else {
            return false;
        }
    }
}
