package de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.change;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.Command;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.Bendpoint;

/**
 * Created by marci on 03.01.17.
 */
public class ChangePositionForBendpoint extends Command<Bendpoint> {

    private int newX;
    private int newY;

    private int oldX;
    private int oldY;

    public ChangePositionForBendpoint(Bendpoint element, int newX, int newY) {
        super(element);
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
        return "Change Bendpoint position to " + newX + ", " + newY;
    }
}
