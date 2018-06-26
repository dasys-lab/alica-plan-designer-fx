package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.State;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.PlanModelVisualisationObject;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.PmlUiExtension;


public class AddStateInPlan extends AbstractCommand {

    public static final String DEFAULT_STATE_NAME = "MISSING NAME";

    private PlanModelVisualisationObject parentOfElement;
    private PmlUiExtension newlyCreatedPmlUiExtension;
    private State newState;

    public AddStateInPlan(ModelManager manager, PlanModelVisualisationObject parentOfElement, State newState, PmlUiExtension pmlUiExtension) {
        super(manager);
        this.newState = newState;
        newState.setName(DEFAULT_STATE_NAME);
        this.parentOfElement = parentOfElement;
        this.newlyCreatedPmlUiExtension = pmlUiExtension;
    }

    @Override
    public void doCommand() {
        parentOfElement.getPlan().getStates().add(newState);
        parentOfElement
                .getPmlUiExtensionMap()
                .getExtension()
                .put(newState, newlyCreatedPmlUiExtension);
    }

    @Override
    public void undoCommand() {
        parentOfElement.getPlan().getStates().remove(newState);
        parentOfElement
                .getPmlUiExtensionMap()
                .getExtension()
                .remove(newState);
    }

    public PmlUiExtension getNewlyCreatedPmlUiExtension() {
        return newlyCreatedPmlUiExtension;
    }
}
