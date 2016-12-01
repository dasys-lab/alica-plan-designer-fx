package de.uni_kassel.vs.cn.planDesigner.aggregatedModel;

import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;

/**
 * Created by marci on 01.12.16.
 */
public abstract class Command<T extends PlanElement> {

    private T elementToEdit;

    public abstract void doCommand();
    public abstract void undoCommand();

    public Command(T element) {
        this.elementToEdit = element;
    }

    protected T getElementToEdit() {
        return elementToEdit;
    }
}
