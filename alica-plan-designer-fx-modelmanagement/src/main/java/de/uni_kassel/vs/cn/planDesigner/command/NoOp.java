package de.uni_kassel.vs.cn.planDesigner.command;

import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;

public class NoOp extends AbstractCommand {

    public NoOp(ModelManager manager) {
        super(manager);
    }

    @Override
    public void doCommand() {

    }

    @Override
    public void undoCommand() {

    }
}
