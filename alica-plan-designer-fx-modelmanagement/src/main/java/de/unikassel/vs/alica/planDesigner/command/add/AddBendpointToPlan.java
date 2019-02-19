package de.unikassel.vs.alica.planDesigner.command.add;

import de.unikassel.vs.alica.planDesigner.alicamodel.Transition;

import de.unikassel.vs.alica.planDesigner.command.AbstractUiPositionCommand;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.events.UiExtensionModelEvent;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.BendPoint;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PlanUiExtensionPair;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.UiExtension;

import java.util.Map;

public class AddBendpointToPlan extends AbstractUiPositionCommand {

    protected UiExtension extension;
    protected BendPoint bendPoint;
    protected PlanUiExtensionPair parentOfElement;
    protected Map<String, Long> relatedObjects;

    public AddBendpointToPlan(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager, mmq);
        this.parentOfElement = modelManager.getPlanUIExtensionPair(mmq.getParentId());
        this.relatedObjects = mmq.getRelatedObjects();
    }

    @Override
    public void doCommand() {

        this.bendPoint = new BendPoint();
        this.bendPoint.setX(x);
        this.bendPoint.setY(y);
        Transition transition = (Transition) modelManager.getPlanElement(this.relatedObjects.get(Types.TRANSITION));
        this.bendPoint.setTransition(transition);
        this.extension = this.parentOfElement.getUiExtension(transition);
        this.extension.addBendpoint(this.bendPoint);
        UiExtensionModelEvent event = new UiExtensionModelEvent(ModelEventType.ELEMENT_CREATED, this.bendPoint.getTransition(), Types.BENDPOINT);
        event.setExtension(this.extension);
        event.setParentId(this.parentOfElement.getPlan().getId());
        this.modelManager.fireUiExtensionModelEvent(event);
    }

    @Override
    public void undoCommand() {
        this.extension.removeBendpoint(this.bendPoint);
        UiExtensionModelEvent event = new UiExtensionModelEvent(ModelEventType.ELEMENT_CREATED, this.bendPoint.getTransition(), Types.BENDPOINT);
        event.setExtension(this.extension);
        event.setParentId(this.parentOfElement.getPlan().getId());
        this.modelManager.fireUiExtensionModelEvent(event);
    }
}
