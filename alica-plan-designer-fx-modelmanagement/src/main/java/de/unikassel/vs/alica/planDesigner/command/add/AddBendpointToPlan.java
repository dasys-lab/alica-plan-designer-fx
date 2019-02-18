package de.unikassel.vs.alica.planDesigner.command.add;

import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.events.UiExtensionModelEvent;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.BendPoint;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PlanUiExtensionPair;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.UiExtension;

public class AddBendpointToPlan extends AbstractCommand {

    protected UiExtension extension;
    protected BendPoint bendPoint;
    protected PlanUiExtensionPair parentOfElement;

    public AddBendpointToPlan(ModelManager modelManager, PlanUiExtensionPair parenOfElement, BendPoint bendPoint, UiExtension extension) {
        super(modelManager);
        this.parentOfElement = parenOfElement;
        this.bendPoint = bendPoint;
        this.extension = extension;
    }

    @Override
    public void doCommand() {
        extension.addBendpoint(bendPoint);
        UiExtensionModelEvent event = new UiExtensionModelEvent(ModelEventType.ELEMENT_CREATED, bendPoint.getTransition(), Types.BENDPOINT);
        event.setExtension(extension);
        event.setParentId(parentOfElement.getPlan().getId());
        modelManager.fireUiExtensionModelEvent(event);
    }

    @Override
    public void undoCommand() {
        extension.removeBendpoint(bendPoint);
        UiExtensionModelEvent event = new UiExtensionModelEvent(ModelEventType.ELEMENT_CREATED, bendPoint.getTransition(), Types.BENDPOINT);
        event.setExtension(extension);
        event.setParentId(parentOfElement.getPlan().getId());
        modelManager.fireUiExtensionModelEvent(event);
    }
}
