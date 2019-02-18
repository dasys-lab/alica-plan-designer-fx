package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.State;
import de.unikassel.vs.alica.planDesigner.alicamodel.Transition;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PlanUiExtensionPair;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.UiExtension;

public class DeleteTransitionInPlan extends AbstractCommand{

    private final PlanUiExtensionPair parentOfElement;
    private UiExtension uiExtension;
    private State inState;
    private State outState;
    private Transition transition;

    public DeleteTransitionInPlan(ModelManager manager, Transition transition, PlanUiExtensionPair parentOfElement) {
        super(manager);
        this.parentOfElement = parentOfElement;
        this.transition = transition;
    }

    private void saveForLaterRetrieval() {
        uiExtension = parentOfElement.getPmlUiExtension(transition);
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
        parentOfElement.put(transition, uiExtension);
        transition.setInState(inState);
        transition.setOutState(outState);
    }
}
