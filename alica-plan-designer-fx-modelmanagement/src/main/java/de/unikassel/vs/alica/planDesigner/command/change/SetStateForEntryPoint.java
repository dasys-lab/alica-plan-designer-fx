package de.unikassel.vs.alica.planDesigner.command.change;

import de.unikassel.vs.alica.planDesigner.alicamodel.EntryPoint;
import de.unikassel.vs.alica.planDesigner.alicamodel.State;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;

public class SetStateForEntryPoint extends AbstractCommand {

    protected EntryPoint entryPoint;
    protected State newState;
    protected State previousState;

    public SetStateForEntryPoint(ModelManager modelManager, EntryPoint entryPoint, State state) {
        super(modelManager);
        this.entryPoint = entryPoint;
        this.newState = state;
    }

    @Override
    public void doCommand() {
        EntryPoint ep = (entryPoint);
        previousState = ep.getState();
        ep.setState(newState);
    }

    @Override
    public void undoCommand() {
        entryPoint.setState(previousState);
    }
}
