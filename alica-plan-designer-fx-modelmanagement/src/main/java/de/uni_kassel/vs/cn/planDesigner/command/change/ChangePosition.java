package de.uni_kassel.vs.cn.planDesigner.command.change;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.PmlUiExtension;

public class ChangePosition extends AbstractCommand {

    private PlanElement planElement;
    private int newX;
    private int newY;

    private int oldX;
    private int oldY;

    public ChangePosition(PmlUiExtension element, PlanElement planElement, int newX, int newY, PlanElement affectedPlan, ModelManager modelManager) {
        super(element, affectedPlan);
        this.planElement = planElement;
        this.newX = newX;
        this.newY = newY;

        // save old position for undo
        oldX = getElementToEdit().getXPos();
        oldY = getElementToEdit().getYPos();
    }

    @Override
    public void doCommand() {
        getElementToEdit().setXPos(newX);
        getElementToEdit().setYPos(newY);
    }

    @Override
    public void undoCommand() {
        getElementToEdit().setXPos(oldX);
        getElementToEdit().setYPos(oldY);
    }

    @Override
    public String getCommandString() {
        return "Change " + planElement.getClass().getSuperclass().getSimpleName() + " "
                + planElement.getName() + " position to " + newX + ", " + newY;
    }
}
