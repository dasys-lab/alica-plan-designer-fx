package de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command;

import org.eclipse.emf.ecore.EObject;

/**
 * Base class for command pattern.
 * @param <T>
 */
public abstract class Command<T extends EObject> {

    private T elementToEdit;

    public abstract void doCommand();
    public abstract void undoCommand();
    public abstract String getCommandString();

    public Command(T element) {
        this.elementToEdit = element;
    }

    protected T getElementToEdit() {
        return elementToEdit;
    }
}
