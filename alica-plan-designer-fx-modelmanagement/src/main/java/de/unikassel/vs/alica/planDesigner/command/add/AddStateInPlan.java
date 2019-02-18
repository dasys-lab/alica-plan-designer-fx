package de.unikassel.vs.alica.planDesigner.command.add;

import de.unikassel.vs.alica.planDesigner.alicamodel.State;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PlanUiExtensionPair;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.UiExtension;


public class AddStateInPlan extends AbstractCommand {

    public static final String DEFAULT_STATE_NAME = "MISSING NAME";

    private PlanUiExtensionPair parentOfElement;
    private UiExtension newlyCreatedUiExtension;
    private State newState;
    private final String type;

    public AddStateInPlan(ModelManager manager, PlanUiExtensionPair parentOfElement, State newState, UiExtension uiExtension, String type) {
        super(manager);
        this.newState = newState;
        this.type = type;
        if(newState.getName() == null || newState.getName().equals("")) {
            newState.setName(DEFAULT_STATE_NAME);
        }
        this.parentOfElement = parentOfElement;
        this.newlyCreatedUiExtension = uiExtension;
    }

    @Override
    public void doCommand() {
        parentOfElement.put(newState, newlyCreatedUiExtension);
        modelManager.createdPlanElement(type, newState, newState.getParentPlan(), false);
    }

    @Override
    public void undoCommand() {
        parentOfElement.remove(newState);
        modelManager.removedPlanElement(type, newState ,parentOfElement.getPlan(), false);
    }

    public UiExtension getNewlyCreatedUiExtension() {
        return newlyCreatedUiExtension;
    }
}
