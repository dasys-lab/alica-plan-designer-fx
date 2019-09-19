package de.unikassel.vs.alica.planDesigner.view.model;

import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Arrays;

public class SerializableViewModel extends PlanElementViewModel {

    protected final SimpleBooleanProperty dirty = new SimpleBooleanProperty(null, "dirty", false);
    protected final SimpleStringProperty debugSenderId = new SimpleStringProperty(null, "debugSenderId", "");

    public SerializableViewModel(long id, String name, String type) {
        super(id, name, type);

        this.uiPropertyList.clear();
        this.uiPropertyList.addAll(Arrays.asList("name", "id", "comment", "relativeDirectory"));
    }

    public void registerListener(IGuiModificationHandler handler) {
        super.registerListener(handler);
    }

    public boolean isDirty() {
        return dirty.get();
    }
    public void setDirty(boolean dirty) {
        this.dirty.set(dirty);
    }
    public SimpleBooleanProperty dirtyProperty() {
        return dirty;
    }


    public final StringProperty debugSenderIdProperty() {return debugSenderId; }
    public void setDebugSenderId(String id) {
        this.debugSenderId.setValue(id);
    }
    public String getDebugSenderId() {
        return debugSenderId.get();
    }

}
