package de.unikassel.vs.alica.planDesigner.view.model;

import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Arrays;

public class SerializableViewModel extends PlanElementViewModel {

    protected final SimpleBooleanProperty dirty = new SimpleBooleanProperty(null, "dirty", false);

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
}
