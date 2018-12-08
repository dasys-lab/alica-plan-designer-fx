package de.unikassel.vs.alica.planDesigner.events;

import de.unikassel.vs.alica.planDesigner.alicamodel.PlanElement;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PmlUiExtension;

import java.util.Map;

/**
 * An Event, that holds information about a change in the UiExtensionModel
 */
public class UiExtensionModelEvent extends ModelEvent{

    private Map<String, Long> relatedObjects;
    private PmlUiExtension extension;

    public UiExtensionModelEvent(ModelEventType modelEventType, PlanElement element, String elementType) {
        super(modelEventType, element, elementType);
    }

    public PlanElement getElement() {
        return element;
    }

    public Map<String, Long> getRelatedObjects() {
        return relatedObjects;
    }

    public void setRelatedObjects(Map<String, Long> relatedObjects) {
        this.relatedObjects = relatedObjects;
    }

    public void setExtension(PmlUiExtension extension) {
        this.extension = extension;
    }

    public PmlUiExtension getExtension() {
        return extension;
    }
}
