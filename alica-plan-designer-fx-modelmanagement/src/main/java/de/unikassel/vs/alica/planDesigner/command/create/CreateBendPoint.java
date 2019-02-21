package de.unikassel.vs.alica.planDesigner.command.create;

import de.unikassel.vs.alica.planDesigner.alicamodel.Transition;

import de.unikassel.vs.alica.planDesigner.command.UiPositionCommand;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.events.UiExtensionModelEvent;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.BendPoint;

public class CreateBendPoint extends UiPositionCommand {

    protected BendPoint bendPoint;

    public CreateBendPoint(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager, mmq);
        this.bendPoint = createBendPoint();
        this.uiElement = modelManager.getPlanUIExtensionPair(mmq.getParentId()).getUiElement(this.bendPoint.getTransition());
    }

    protected BendPoint createBendPoint() {
        BendPoint bendPoint = new BendPoint();
        bendPoint.setX(x);
        bendPoint.setY(y);
        bendPoint.setTransition((Transition) modelManager.getPlanElement(mmq.getRelatedObjects().get(Types.TRANSITION)));
        return bendPoint;
    }

    @Override
    public void doCommand() {
        this.uiElement.addBendpoint(this.bendPoint);
        this.modelManager.storePlanElement(mmq.getElementType(), bendPoint, false);
        this.fireEvent(ModelEventType.ELEMENT_CREATED, this.bendPoint.getTransition());
    }

    @Override
    public void undoCommand() {
        this.uiElement.removeBendpoint(this.bendPoint);
        this.modelManager.dropPlanElement(mmq.getElementType(), bendPoint, false);
        this.fireEvent(ModelEventType.ELEMENT_DELETED, this.bendPoint.getTransition());
    }
}
