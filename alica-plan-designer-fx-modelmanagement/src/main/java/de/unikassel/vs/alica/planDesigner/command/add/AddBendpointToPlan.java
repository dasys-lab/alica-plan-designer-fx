package de.unikassel.vs.alica.planDesigner.command.add;

import de.unikassel.vs.alica.planDesigner.alicamodel.Transition;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.events.UiExtensionModelEvent;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.BendPoint;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PlanModelVisualisationObject;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PmlUiExtension;

public class AddBendpointToPlan extends AbstractCommand {

    protected PmlUiExtension pmlUiExtension;
    protected BendPoint bendPoint;
    private PlanModelVisualisationObject parentOfElement;

    public AddBendpointToPlan(ModelManager modelManager, BendPoint bendPoint, PlanModelVisualisationObject parenOfElement, PmlUiExtension pmlUiExtension) {
        super(modelManager);
        this.bendPoint = bendPoint;
        this.pmlUiExtension = pmlUiExtension;
        this.parentOfElement = parenOfElement;
    }

    @Override
    public void doCommand() {
        pmlUiExtension.getBendPoints().add(bendPoint);
        UiExtensionModelEvent event = new UiExtensionModelEvent(ModelEventType.ELEMENT_CREATED, bendPoint.getTransition(), Types.BENDPOINT);
        event.setNewX(bendPoint.getXPos());
        event.setNewY(bendPoint.getYPos());
        event.setParentId(parentOfElement.getPlan().getId());
        modelManager.fireEvent(event);
    }

    @Override
    public void undoCommand() {
        pmlUiExtension.getBendPoints().remove(bendPoint);
    }
}
