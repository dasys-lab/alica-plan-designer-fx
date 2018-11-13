package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.PreCondition;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.State;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Transition;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.Types;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.PlanModelVisualisationObject;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.PmlUiExtension;

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
