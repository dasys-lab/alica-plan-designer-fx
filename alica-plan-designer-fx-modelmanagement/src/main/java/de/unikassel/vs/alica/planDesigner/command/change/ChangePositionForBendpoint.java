package de.unikassel.vs.alica.planDesigner.command.change;

import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.BendPoint;

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
        oldX = bendPoint.getX();
        oldY = bendPoint.getY();
    }

    @Override
    public void doCommand() {
        bendPoint.setX(newX);
        bendPoint.setY(newY);
    }

    @Override
    public void undoCommand() {
        bendPoint.setX(oldX);
        bendPoint.setY(oldY);
    }
}
