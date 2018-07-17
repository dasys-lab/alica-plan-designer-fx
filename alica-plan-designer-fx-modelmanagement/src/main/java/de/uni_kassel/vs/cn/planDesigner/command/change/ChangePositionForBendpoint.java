package de.uni_kassel.vs.cn.planDesigner.command.change;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.BendPoint;

public class ChangePositionForBendpoint extends AbstractCommand {

    protected BendPoint bendPoint;
    protected Plan plan;

    protected int newX;
    protected int newY;

    protected int oldX;
    protected int oldY;

    public ChangePositionForBendpoint(ModelManager modelManager, Plan plan, BendPoint bendPoint, int newX, int newY) {
        super(modelManager);
        this.newX = newX;
        this.newY = newY;

        // save old position for undo
        oldX = bendPoint.getXPos();
        oldY = bendPoint.getYPos();
    }

    @Override
    public void doCommand() {
        bendPoint.setXPos(newX);
        bendPoint.setYPos(newY);
    }

    @Override
    public void undoCommand() {
        bendPoint.setXPos(oldX);
        bendPoint.setYPos(oldY);
    }
}
