package de.uni_kassel.vs.cn.planDesigner.events;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement;

/**
 * An Event, that holds information about a change in the UiExtensionModel
 */
public class UiExtensionModelEvent {
    private PlanElement element;

    private int newX;
    private int newY;

    public UiExtensionModelEvent(PlanElement element) {
        this.element = element;
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
