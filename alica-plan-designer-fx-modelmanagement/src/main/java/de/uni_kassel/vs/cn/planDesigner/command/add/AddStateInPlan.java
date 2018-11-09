package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.State;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.events.ModelEvent;
import de.uni_kassel.vs.cn.planDesigner.events.ModelEventType;
import de.uni_kassel.vs.cn.planDesigner.events.UiExtensionModelEvent;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.Types;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.PlanModelVisualisationObject;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.PmlUiExtension;


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
        newState.setName(DEFAULT_STATE_NAME);
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
