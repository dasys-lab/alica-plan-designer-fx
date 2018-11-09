package de.unikassel.vs.alica.planDesigner.command;


import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;

public abstract class AbstractCommand {

    protected ModelManager modelManager;

    public abstract void doCommand();
    public abstract void undoCommand();

    public AbstractCommand(ModelManager modelManager) {
        this.modelManager = modelManager;
    }
}
