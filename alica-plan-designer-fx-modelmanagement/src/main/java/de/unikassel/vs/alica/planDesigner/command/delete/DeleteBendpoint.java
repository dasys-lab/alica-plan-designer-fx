package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.Transition;
import de.unikassel.vs.alica.planDesigner.command.UiPositionCommand;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.BendPoint;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.UiElement;

import java.util.HashMap;

public class DeleteBendpoint extends UiPositionCommand {

    protected BendPoint bendPoint;
    protected Transition transition;
    protected UiElement uiElement;
    protected int index;


    public DeleteBendpoint(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager, mmq);
        this.transition = (Transition) modelManager.getPlanElement(mmq.getElementId());
        this.uiElement = this.createUiElement(mmq.getParentId(), transition);

        for(BendPoint bPoint : uiElement.getBendPoints()) {
            if (bPoint.getId() == mmq.getRelatedObjects().get(Types.BENDPOINT)) {
                this.bendPoint = bPoint;
                break;
            }
        }
        this.index = uiElement.getBendPoints().indexOf(bendPoint);
    }

    @Override
    public void doCommand() {
        HashMap<String, Long> bendPointMap = new HashMap<>();
        bendPointMap.put(Types.BENDPOINT, bendPoint.getId());
        uiElement.removeBendpoint(bendPoint);
        this.modelManager.dropPlanElement(mmq.getElementType(), bendPoint, false);
        this.fireEvent(ModelEventType.ELEMENT_REMOVED, this.transition, bendPointMap);
    }

    @Override
    public void undoCommand() {
        uiElement.getBendPoints().add(index, bendPoint);
        this.modelManager.storePlanElement(mmq.getElementType(), bendPoint, false);
        this.fireEvent(ModelEventType.ELEMENT_CREATED, this.bendPoint.getTransition());
    }
}
