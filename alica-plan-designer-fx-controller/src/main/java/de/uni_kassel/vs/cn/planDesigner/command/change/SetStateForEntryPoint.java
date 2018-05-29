package de.uni_kassel.vs.cn.planDesigner.command.change;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.EntryPoint;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.State;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;

/**
 * Created by marci on 08.03.17.
 */
public class SetStateForEntryPoint extends AbstractCommand<EntryPoint> {

    private State stateToSet;
    private State previousState;

    public SetStateForEntryPoint(EntryPoint elementToEdit, State stateToSet) {
        super(elementToEdit, elementToEdit.getPlan());
        this.stateToSet = stateToSet;
    }

    @Override
    public void doCommand() {
        previousState = getElementToEdit().getState();
        getElementToEdit().setState(stateToSet);
    }

    @Override
    public void undoCommand() {
        getElementToEdit().setState(previousState);
    }

    @Override
    public String getCommandString() {
        return "Set State of EntryPoint to " + stateToSet;
    }
}
