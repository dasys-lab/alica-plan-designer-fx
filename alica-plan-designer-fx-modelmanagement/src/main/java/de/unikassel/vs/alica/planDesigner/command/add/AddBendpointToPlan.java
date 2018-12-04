package de.unikassel.vs.alica.planDesigner.command.add;

import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.events.UiExtensionModelEvent;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.BendPoint;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PlanModelVisualisationObject;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PmlUiExtension;

import java.lang.annotation.ElementType;

public class AddBendpointToPlan extends AbstractCommand {

    protected PmlUiExtension extension;
    protected BendPoint bendPoint;
    protected PlanModelVisualisationObject parentOfElement;

    public AddBendpointToPlan(ModelManager modelManager, PlanModelVisualisationObject parenOfElement, BendPoint bendPoint, PmlUiExtension extension) {
        super(modelManager);
        this.parentOfElement = parenOfElement;
        this.bendPoint = bendPoint;
        this.extension = extension;
    }

    @Override
    public void doCommand() {
        extension.addBendpoint(bendPoint);
    }

    @Override
    public void undoCommand() {
        extension.removeBendpoint(bendPoint);
    }
}
