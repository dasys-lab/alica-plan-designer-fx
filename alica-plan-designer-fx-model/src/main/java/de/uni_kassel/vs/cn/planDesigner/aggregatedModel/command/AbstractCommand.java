package de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command;

import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import org.eclipse.emf.ecore.EObject;

/**
 * Base class for command pattern.
 * @param <T>
 */
public abstract class AbstractCommand<T extends EObject> {

    private PlanElement affectedPlan;

    private T elementToEdit;

    private boolean saved;

    public abstract void doCommand();
    public abstract void undoCommand();
    public abstract String getCommandString();

    public AbstractCommand(T element, PlanElement affectedPlan) {
        this.elementToEdit = element;
        this.affectedPlan = affectedPlan;
        saved = false;
    }

    public PlanElement getAffectedPlan() {
        return affectedPlan;
    }

    public T getElementToEdit() {
        return elementToEdit;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }
}
