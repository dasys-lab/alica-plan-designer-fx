package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.command.Command;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.UiExtension;

public class DeletePlan extends Command {

    protected Plan plan;
    protected UiExtension uiExtension;

    public DeletePlan(ModelManager manager, ModelModificationQuery mmq) {
        super(manager, mmq);
        plan = (Plan) manager.getPlanElement(mmq.getElementId());
        uiExtension = modelManager.getUiExtensionMap().get(mmq.getElementId());
    }

    @Override
    public void doCommand() {
        if (plan == null) {
            return;
        }
        modelManager.dropPlanElement(Types.PLAN, plan, true);
        this.fireEvent(ModelEventType.ELEMENT_DELETED, this.plan);
    }

    @Override
    public void undoCommand() {
        if (plan == null) {
            return;
        }
        // The UiExtension has been deleted with the plan, so it needs to be put back into the map
        modelManager.getUiExtensionMap().put(mmq.getElementId(), uiExtension);
        if (!plan.getMasterPlan()) {
            modelManager.storePlanElement(Types.PLAN, plan, true);
        } else {
            modelManager.storePlanElement(Types.MASTERPLAN, plan, true);
        }
        this.fireEvent(ModelEventType.ELEMENT_CREATED, this.plan);
    }
}
