package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.PlanModelVisualisationObject;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.alica.State;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;

import static de.uni_kassel.vs.cn.generator.EMFModelUtils.getPmlUiExtensionModelFactory;

/**
 * Created by marci on 02.12.16.
 */
public class AddStateInPlan extends AbstractCommand<State> {

    public static final String DEFAULT_STATE_NAME = "MISSING NAME";
    private PlanModelVisualisationObject parentOfElement;
    private PmlUiExtension newlyCreatedPmlUiExtension;

    public AddStateInPlan(PlanModelVisualisationObject parentOfElement, State newObject) {
        super(newObject, parentOfElement.getPlan());
        getElementToEdit().setName(DEFAULT_STATE_NAME);
        this.parentOfElement = parentOfElement;
        this.newlyCreatedPmlUiExtension = getPmlUiExtensionModelFactory().createPmlUiExtension();
    }

    @Override
    public void doCommand() {
        parentOfElement.getPlan().getStates().add(getElementToEdit());
        parentOfElement
                .getPmlUiExtensionMap()
                .getExtension()
                .put(getElementToEdit(), newlyCreatedPmlUiExtension);
    }

    @Override
    public void undoCommand() {
        parentOfElement.getPlan().getStates().remove(getElementToEdit());
        parentOfElement
                .getPmlUiExtensionMap()
                .getExtension()
                .remove(getElementToEdit());
    }

    @Override
    public String getCommandString() {
        return "Add State to Plan " + getElementToEdit();
    }

    public PmlUiExtension getNewlyCreatedPmlUiExtension() {
        return newlyCreatedPmlUiExtension;
    }
}
