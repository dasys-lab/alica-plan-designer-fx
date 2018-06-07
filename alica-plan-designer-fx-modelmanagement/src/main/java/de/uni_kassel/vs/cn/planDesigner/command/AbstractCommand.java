package de.uni_kassel.vs.cn.planDesigner.command;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;

public abstract class AbstractCommand {

    private ModelManager modelManager;

    private PlanElement affectedPlan;

    private PlanElement elementToEdit;

    private boolean saved;

    public abstract void doCommand();
    public abstract void undoCommand();
    public abstract String getCommandString();

    public AbstractCommand(ModelManager modelManager, PlanElement element, PlanElement affectedPlan) {
        this.modelManager = modelManager;
        this.elementToEdit = element;
        this.affectedPlan = affectedPlan;
        saved = false;
    }

    /**
     *
     * @return the plan whose elements are changed/created/deleted
     */
    public PlanElement getAffectedPlan() {
        return affectedPlan;
    }

    public PlanElement getElementToEdit() {
        return elementToEdit;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }
}
