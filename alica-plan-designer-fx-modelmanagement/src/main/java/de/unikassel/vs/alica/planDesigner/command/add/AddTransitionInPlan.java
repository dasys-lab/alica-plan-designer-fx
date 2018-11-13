package de.unikassel.vs.alica.planDesigner.command.add;

import de.unikassel.vs.alica.planDesigner.alicamodel.PreCondition;
import de.unikassel.vs.alica.planDesigner.alicamodel.State;
import de.unikassel.vs.alica.planDesigner.alicamodel.Transition;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PlanModelVisualisationObject;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PmlUiExtension;

public class AddTransitionInPlan extends AbstractCommand {
    protected final PmlUiExtension newlyCreatedPmlUiExtension;
    protected PlanModelVisualisationObject parentOfElement;
    protected State from;
    protected State to;
    protected Transition transition;

    public AddTransitionInPlan(ModelManager modelManager, PlanModelVisualisationObject parentOfElement, State from, State to) {
        super(modelManager);
        this.transition = new Transition();
        this.parentOfElement = parentOfElement;
        this.from = from;
        this.to = to;
        this.newlyCreatedPmlUiExtension = new PmlUiExtension();
    }

    @Override
    public void doCommand() {
        /*transition.setPreCondition(new PreCondition());
        transition.setInState(from);
        transition.setOutState(to);
        parentOfElement.getPlan().getTransitions().add(transition);
        parentOfElement
                .getPmlUiExtensionMap()
                .getExtension()
                .put(transition, newlyCreatedPmlUiExtension);*/
        transition.setInState(from);
        transition.setOutState(to);
        modelManager.addPlanElement(Types.TRANSITION, transition, parentOfElement.getPlan(), false);
    }

    @Override
    public void undoCommand() {
        transition.setOutState(null);
        transition.setInState(null);
        modelManager.removePlanElement(Types.TRANSITION, transition, parentOfElement.getPlan(), false);
    }
}
