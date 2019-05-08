package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.alicamodel.State;
import de.unikassel.vs.alica.planDesigner.alicamodel.Transition;
import de.unikassel.vs.alica.planDesigner.command.UiPositionCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.UiExtension;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.UiElement;

import java.util.HashMap;
import java.util.Map;

public class DeleteStateInPlan extends UiPositionCommand {

    protected State state;
    protected Plan plan;

    public DeleteStateInPlan(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager, mmq);
        this.state = (State) modelManager.getPlanElement(mmq.getElementId());
        if (!isSafeToDelete(this.state)) {
            state = null;
            return;
        }
        this.plan = (Plan) modelManager.getPlanElement(mmq.getParentId());
        createUiElement(mmq.getParentId(), this.state);
    }

    private boolean isSafeToDelete(State state) {
        if (state.getOutTransitions().isEmpty()
                && state.getInTransitions().isEmpty()
                && state.getAbstractPlans().isEmpty())  {
            return true;
        }
        return false;
    }

    @Override
    public void doCommand() {
        if (state == null) {
            return;
        }
        plan.removeState(state);
    }

    @Override
    public void undoCommand() {
        if (state == null) {
            return;
        }
        plan.addState(state);
    }
}
