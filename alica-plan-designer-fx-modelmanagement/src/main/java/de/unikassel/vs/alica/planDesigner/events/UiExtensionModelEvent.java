package de.unikassel.vs.alica.planDesigner.events;

import de.unikassel.vs.alica.planDesigner.alicamodel.PlanElement;

/**
 * An Event, that holds information about a change in the UiExtensionModel
 */
public class UiExtensionModelEvent extends ModelEvent{

    private int newX;
    private int newY;

    public UiExtensionModelEvent(ModelEventType modelEventType, PlanElement element, String elementType) {
        super(modelEventType, element, elementType);
    }

    public PlanElement getElement() {
        return element;
    }

    public int getNewX() {
        return newX;
    }

    public void setNewX(int newX) {
        this.newX = newX;
    }

    public int getNewY() {
        return newY;
    }

    public void setNewY(int newY) {
        this.newY = newY;
    }
}
