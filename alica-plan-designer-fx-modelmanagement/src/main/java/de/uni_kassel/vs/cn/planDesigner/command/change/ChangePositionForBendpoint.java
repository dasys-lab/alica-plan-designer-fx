package de.uni_kassel.vs.cn.planDesigner.command.change;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.Bendpoint;

public class ChangePositionForBendpoint extends AbstractCommand {

    protected Bendpoint bendpoint;
    protected Plan plan;

    protected int newX;
    protected int newY;

    protected int oldX;
    protected int oldY;

    public ChangePositionForBendpoint(ModelManager modelManager, Plan plan, Bendpoint bendpoint, int newX, int newY) {
        super(modelManager);
        this.newX = newX;
        this.newY = newY;

        // save old position for undo
        oldX = bendpoint.getXPos();
        oldY = bendpoint.getYPos();
    }

    @Override
    public void doCommand() {
        bendpoint.setXPos(newX);
        bendpoint.setYPos(newY);
    }

    @Override
    public void undoCommand() {
        bendpoint.setXPos(oldX);
        bendpoint.setYPos(oldY);
    }

    @Override
    public String getCommandString() {
        return "Change Bendpoint position to " + newX + ", " + newY;
    }
}
