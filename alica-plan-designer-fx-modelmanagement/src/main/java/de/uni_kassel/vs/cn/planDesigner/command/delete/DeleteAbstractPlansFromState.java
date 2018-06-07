package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.AbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.State;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;

public class DeleteAbstractPlansFromState extends AbstractCommand {

    private final State state;
    private final AbstractPlan element;

    public DeleteAbstractPlansFromState(ModelManager manager, AbstractPlan element, State state) {
        super(manager);
        this.state = state;
        this.element = element;
    }

    @Override
    public void doCommand() {
        state.getPlans().remove(element);
    }

    @Override
    public void undoCommand() {
        state.getPlans().add(element);
    }

    @Override
    public String getCommandString() {
        return "Delete Object " + element.getName() + " from State "
                + state + " in Plan " + state.getParentPlan().getName();
    }
}
