package de.unikassel.vs.alica.planDesigner.command.add;

import de.unikassel.vs.alica.planDesigner.alicamodel.TerminalState;
import de.unikassel.vs.alica.planDesigner.command.AbstractUiPositionCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PlanUiExtensionPair;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.UiExtension;

public class AddTerminalStateInPlan extends AbstractUiPositionCommand {

    public static final String DEFAULT_STATE_NAME = "MISSING NAME";

    private PlanUiExtensionPair parentOfElement;
    private UiExtension newlyCreatedUiExtension;
    private TerminalState newState;
    private String name;
    private String comment;
    private final String type;

    public AddTerminalStateInPlan(ModelManager manager, ModelModificationQuery mmq) {
        super(manager, mmq);
        this.type = mmq.getElementType();
        this.name = mmq.getName();
        this.comment = mmq.getComment();
        this.parentOfElement = modelManager.getPlanUIExtensionPair(mmq.getParentId());
    }

    @Override
    public void doCommand() {
        //Creating a new State and setting all necessary fields
        this.newState = new TerminalState(this.type == Types.SUCCESSSTATE, null);
        this.newState.setParentPlan(parentOfElement.getPlan());
        this.newState.setName(this.name);
        this.newState.setComment(this.comment);
        if(newState.getName() == null || newState.getName().equals("")) {
            newState.setName(DEFAULT_STATE_NAME);
        }
        //Creating an extension with coordinates
        this.newlyCreatedUiExtension = this.parentOfElement.getUiExtension(this.newState);
        this.newlyCreatedUiExtension.setX(x);
        this.newlyCreatedUiExtension.setY(y);
        modelManager.storePlanElement(type, newState, newState.getParentPlan(), false);
    }

    @Override
    public void undoCommand() {
        parentOfElement.remove(newState);
        modelManager.removedPlanElement(type, newState ,parentOfElement.getPlan(), false);
    }
}
