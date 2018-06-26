package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.AbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.State;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;

public class AddAbstractPlanToState extends AbstractCommand {
    protected State state;
    protected AbstractPlan abstractPlan;

    public AddAbstractPlanToState(ModelManager modelManager, AbstractPlan abstractPlan, State state) {
        super(modelManager);
        this.abstractPlan = abstractPlan;
        this.state = state;
    }

    @Override
    public void doCommand() {
        state.addAbstractPlan(abstractPlan);
    }

    @Override
    public void undoCommand() {
        state.removeAbstractPlan(abstractPlan);
    }
}
