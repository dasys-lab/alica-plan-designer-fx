package de.uni_kassel.vs.cn.planDesigner.command.change;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.PmlUiExtension;

public class ChangePosition extends AbstractCommand {

    protected PlanElement planElement;
    protected PmlUiExtension uiElement;
    protected int newX;
    protected int newY;

    protected int oldX;
    protected int oldY;

    public ChangePosition(ModelManager modelManager, PmlUiExtension uiElement, PlanElement planElement, int newX, int newY) {
        super(modelManager);
        this.uiElement = uiElement;
        this.planElement = planElement;
        this.newX = newX;
        this.newY = newY;

        // save old position for undo
        oldX = uiElement.getXPos();
        oldY = uiElement.getYPos();
    }

    @Override
    public void doCommand() {
        uiElement.setXPos(newX);
        uiElement.setYPos(newY);
    }

    @Override
    public void undoCommand() {
        uiElement.setXPos(oldX);
        uiElement.setYPos(oldY);
    }
}
