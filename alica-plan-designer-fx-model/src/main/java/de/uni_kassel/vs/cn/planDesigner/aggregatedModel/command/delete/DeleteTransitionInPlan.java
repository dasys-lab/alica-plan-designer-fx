package de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.delete;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.PlanModelVisualisationObject;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.Command;
import de.uni_kassel.vs.cn.planDesigner.alica.Transition;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;

import java.util.AbstractMap;

/**
 * Created by marci on 01.12.16.
 */
public class DeleteTransitionInPlan extends Command<Transition> {

    private final PlanModelVisualisationObject parentOfElement;
    private PmlUiExtension pmlUiExtension;

    public DeleteTransitionInPlan(Transition element, PlanModelVisualisationObject parentOfElement) {
        super(element);
        this.parentOfElement = parentOfElement;
    }

    @Override
    public void doCommand() {
        parentOfElement.getPlan().getTransitions().remove(getElementToEdit());
        pmlUiExtension = parentOfElement.getPmlUiExtensionMap().getExtension().get(getElementToEdit());
        parentOfElement.getPmlUiExtensionMap().getExtension().remove(getElementToEdit());
    }

    @Override
    public void undoCommand() {
        parentOfElement.getPlan().getTransitions().add(getElementToEdit());
        parentOfElement.getPmlUiExtensionMap().getExtension()
                .add(new AbstractMap.SimpleEntry<>(getElementToEdit(), pmlUiExtension));
    }

    @Override
    public String getCommandString() {
        return "Delete Transition " + getElementToEdit().getComment() + " in Plan " + parentOfElement.getPlan().getName();
    }
}
