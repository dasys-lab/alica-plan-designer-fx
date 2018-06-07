package de.uni_kassel.vs.cn.planDesigner.command;

import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;

public abstract class AbstractCommand {

    protected ModelManager modelManager;

    public abstract void doCommand();
    public abstract void undoCommand();
    public abstract String getCommandString();

    public AbstractCommand(ModelManager modelManager) {
        this.modelManager = modelManager;
    }
}
