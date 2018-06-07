package de.uni_kassel.vs.cn.planDesigner.command;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.AbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;

public class CreateAbstractPlan extends AbstractCommand {

    protected AbstractPlan abstractPlan;

    public CreateAbstractPlan(ModelManager modelManager, AbstractPlan abstractPlan) {
        super(modelManager);
        this.abstractPlan = abstractPlan;
    }

    @Override
    public void doCommand() {
    }

    @Override
    public void undoCommand() {
    }

    @Override
    public String getCommandString() {
        return "Create " + abstractPlan.getName();
    }
}
