package de.unikassel.vs.alica.planDesigner.command.add;

import de.unikassel.vs.alica.planDesigner.alicamodel.State;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PlanModelVisualisationObject;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PmlUiExtension;


public class AddStateInPlan extends AbstractCommand {

    public static final String DEFAULT_STATE_NAME = "MISSING NAME";

    private PlanModelVisualisationObject parentOfElement;
    private PmlUiExtension newlyCreatedPmlUiExtension;
    private State newState;
    private final String type;

    public AddStateInPlan(ModelManager manager, PlanModelVisualisationObject parentOfElement, State newState, PmlUiExtension pmlUiExtension, String type) {
        super(manager);
        this.newState = newState;
        this.type = type;
        if(newState.getName() == null || newState.getName().equals("")) {
            newState.setName(DEFAULT_STATE_NAME);
        }
        this.parentOfElement = parentOfElement;
        this.newlyCreatedPmlUiExtension = pmlUiExtension;
    }

    @Override
    public void doCommand() {
        modelManager.addPlanElementAtPosition(type, newState, newlyCreatedPmlUiExtension, parentOfElement);
    }

    @Override
    public void undoCommand() {
        modelManager.removePlanElement(type, newState ,parentOfElement.getPlan(), false);
    }

    public PmlUiExtension getNewlyCreatedPmlUiExtension() {
        return newlyCreatedPmlUiExtension;
    }
}
