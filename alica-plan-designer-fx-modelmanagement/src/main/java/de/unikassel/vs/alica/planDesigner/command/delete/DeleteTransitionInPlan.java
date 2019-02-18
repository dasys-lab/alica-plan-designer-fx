package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.State;
import de.unikassel.vs.alica.planDesigner.alicamodel.Transition;
import de.unikassel.vs.alica.planDesigner.command.AbstractUiPositionCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PlanUiExtensionPair;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.UiExtension;

public class DeleteTransitionInPlan extends AbstractUiPositionCommand{

    private final PlanUiExtensionPair parentOfElement;
    private UiExtension uiExtension;
    private State inState;
    private State outState;
    private Transition transition;

    public DeleteTransitionInPlan(ModelManager manager, ModelModificationQuery mmq) {
        super(manager, mmq);
        this.parentOfElement = this.modelManager.getPlanUIExtensionPair(mmq.getParentId());
        this.transition = (Transition) this.modelManager.getPlanElement(mmq.getElementId());
    }

    private void saveForLaterRetrieval() {
        uiExtension = parentOfElement.getUiExtension(transition);
        outState = transition.getOutState();
        inState = transition.getInState();
    }

    @Override
    public void doCommand() {
        saveForLaterRetrieval();

        parentOfElement.getPlan().getTransitions().remove(transition);
        parentOfElement.remove(transition);
        transition.setInState(null);
        transition.setOutState(null);
    }

    @Override
    public void undoCommand() {
        parentOfElement.getPlan().getTransitions().add(transition);
        this.uiExtension = parentOfElement.getUiExtension(this.transition);
        this.uiExtension.setX(this.x);
        this.uiExtension.setY(this.y);
        transition.setInState(inState);
        transition.setOutState(outState);
    }
}
