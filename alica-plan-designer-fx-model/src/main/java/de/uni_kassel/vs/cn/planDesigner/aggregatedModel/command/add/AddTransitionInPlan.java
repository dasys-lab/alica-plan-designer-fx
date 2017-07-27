package de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.add;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.PlanModelVisualisationObject;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.alica.State;
import de.uni_kassel.vs.cn.planDesigner.alica.Transition;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;

import static de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils.getAlicaFactory;
import static de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils.getPmlUiExtensionModelFactory;

/**
 * Created by marci on 02.12.16.
 */
public class AddTransitionInPlan extends AbstractCommand<Transition> {
    private final PmlUiExtension newlyCreatedPmlUiExtension;
    private PlanModelVisualisationObject parentOfElement;
    private State from;
    private State to;

    public AddTransitionInPlan(PlanModelVisualisationObject parentOfElement, State from, State to) {
        super(getAlicaFactory().createTransition(), parentOfElement.getPlan());
        this.parentOfElement = parentOfElement;
        this.from = from;
        this.to = to;
        this.newlyCreatedPmlUiExtension = getPmlUiExtensionModelFactory().createPmlUiExtension();
    }

    @Override
    public void doCommand() {
        getElementToEdit().setPreCondition(getAlicaFactory().createPreCondition());
        getElementToEdit().setInState(from);
        getElementToEdit().setOutState(to);
        parentOfElement.getPlan().getTransitions().add(getElementToEdit());
        parentOfElement
                .getPmlUiExtensionMap()
                .getExtension()
                .put(getElementToEdit(), newlyCreatedPmlUiExtension);
    }

    @Override
    public void undoCommand() {
        getElementToEdit().setOutState(null);
        getElementToEdit().setInState(null);
        parentOfElement.getPlan().getTransitions().remove(getElementToEdit());
        parentOfElement
                .getPmlUiExtensionMap()
                .getExtension()
                .remove(getElementToEdit());
    }

    @Override
    public String getCommandString() {
        return "Add new transition";
    }
}
