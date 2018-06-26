package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.State;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Transition;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.PlanModelVisualisationObject;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.PmlUiExtension;

import java.util.Map;

public class DeleteTransitionInPlan extends AbstractCommand{

    private final PlanModelVisualisationObject parentOfElement;
    private PmlUiExtension pmlUiExtension;
    private State inState;
    private State outState;
    private Transition transition;

    public DeleteTransitionInPlan(ModelManager manager, Transition transition, PlanModelVisualisationObject parentOfElement) {
        super(manager);
        this.parentOfElement = parentOfElement;
        this.transition = transition;
    }

    private void saveForLaterRetrieval() {
        pmlUiExtension = parentOfElement.getPmlUiExtensionMap().getExtension().get(transition);
        outState = transition.getOutState();
        inState = transition.getInState();
    }

    @Override
    public void doCommand() {
        saveForLaterRetrieval();

        parentOfElement.getPlan().getTransitions().remove(transition);
        parentOfElement.getPmlUiExtensionMap().getExtension().remove(transition);
        transition.setInState(null);
        transition.setOutState(null);
    }

    @Override
    public void undoCommand() {
        parentOfElement.getPlan().getTransitions().add(transition);
        parentOfElement.getPmlUiExtensionMap().getExtension().put(transition, pmlUiExtension);
        transition.setInState(inState);
        transition.setOutState(outState);
    }
}
