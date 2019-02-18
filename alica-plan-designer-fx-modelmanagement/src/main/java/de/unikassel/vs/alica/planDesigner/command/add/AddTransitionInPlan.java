package de.unikassel.vs.alica.planDesigner.command.add;

import de.unikassel.vs.alica.planDesigner.alicamodel.State;
import de.unikassel.vs.alica.planDesigner.alicamodel.Transition;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PlanUiExtensionPair;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.UiExtension;

public class AddTransitionInPlan extends AbstractCommand {
    protected final UiExtension newlyCreatedUiExtension;
    protected PlanUiExtensionPair parentOfElement;
    protected State from;
    protected State to;
    protected Transition transition;

    public AddTransitionInPlan(ModelManager modelManager, PlanUiExtensionPair parentOfElement, State from, State to) {
        super(modelManager);
        this.transition = new Transition();
        this.parentOfElement = parentOfElement;
        this.from = from;
        this.to = to;
        this.newlyCreatedUiExtension = new UiExtension();
    }

    @Override
    public void doCommand() {
        /*transition.setPreCondition(new PreCondition());
        transition.setInState(from);
        transition.setOutState(to);
        parentOfElement.getPlan().getTransitions().put(transition);
        parentOfElement
                .getPmlUiExtensionMap()
                .getExtension()
                .put(transition, newlyCreatedUiExtension);*/
        transition.setInState(from);
        transition.setOutState(to);
        parentOfElement.put(transition, newlyCreatedUiExtension);
        modelManager.createdPlanElement(Types.TRANSITION, transition, parentOfElement.getPlan(), false);
    }

    @Override
    public void undoCommand() {
        transition.setOutState(null);
        transition.setInState(null);
        parentOfElement.put(transition, newlyCreatedUiExtension);
        modelManager.removedPlanElement(Types.TRANSITION, transition, parentOfElement.getPlan(), false);
    }
}
