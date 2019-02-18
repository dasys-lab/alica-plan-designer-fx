package de.unikassel.vs.alica.planDesigner.command.add;

import de.unikassel.vs.alica.planDesigner.alicamodel.State;
import de.unikassel.vs.alica.planDesigner.alicamodel.Transition;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PlanUiExtensionPair;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.UiExtension;

public class AddTransitionInPlan extends AbstractCommand {
    protected UiExtension newlyCreatedUiExtension;
    protected PlanUiExtensionPair parentOfElement;
    protected State in;
    protected State out;
    protected Transition transition;

    public AddTransitionInPlan(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager);
        this.parentOfElement = modelManager.getPlanUIExtensionPair(mmq.getParentId());
        this.in = (State) modelManager.getPlanElement(mmq.getRelatedObjects().get(Transition.INSTATE));
        this.out = (State) modelManager.getPlanElement(mmq.getRelatedObjects().get(Transition.OUTSTATE));
    }

    @Override
    public void doCommand() {
        this.transition = new Transition();
        this.transition.setInState(this.in);
        this.transition.setOutState(this.out);
        this.newlyCreatedUiExtension = this.parentOfElement.getUiExtension(this.transition);
        this.modelManager.createdPlanElement(Types.TRANSITION, this.transition,this. parentOfElement.getPlan(), false);
    }

    @Override
    public void undoCommand() {
        this.transition.setOutState(null);
        this.transition.setInState(null);
        this.parentOfElement.remove(this.transition);
        this.modelManager.removedPlanElement(Types.TRANSITION, this.transition, this.parentOfElement.getPlan(), false);
    }
}
