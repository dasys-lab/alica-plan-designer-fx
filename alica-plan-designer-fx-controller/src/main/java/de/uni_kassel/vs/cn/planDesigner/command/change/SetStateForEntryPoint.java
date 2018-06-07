package de.uni_kassel.vs.cn.planDesigner.command.change;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.EntryPoint;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.State;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;

public class SetStateForEntryPoint extends AbstractCommand {

    private State stateToSet;
    private State previousState;

    public SetStateForEntryPoint(EntryPoint elementToEdit, State stateToSet) {
        super(elementToEdit, elementToEdit.getPlan());
        this.stateToSet = stateToSet;
    }

    @Override
    public void doCommand() {
        EntryPoint ep = ((EntryPoint)getElementToEdit());
        previousState = ep.getState();
        ep.setState(stateToSet);
    }

    @Override
    public void undoCommand() {
        ((EntryPoint)getElementToEdit()).setState(previousState);
    }

    @Override
    public String getCommandString() {
        return "Set State of EntryPoint to " + stateToSet;
    }
}
