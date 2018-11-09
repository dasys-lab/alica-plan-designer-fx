package de.unikassel.vs.alica.planDesigner.command.add;

import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.BendPoint;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PmlUiExtension;

public class AddBendpointToPlan extends AbstractCommand {

    protected PmlUiExtension pmlUiExtension;
    protected BendPoint bendPoint;

    public AddBendpointToPlan(ModelManager modelManager, BendPoint bendPoint, PmlUiExtension pmlUiExtension) {
        super(modelManager);
        this.bendPoint = bendPoint;
        this.pmlUiExtension = pmlUiExtension;
    }

    @Override
    public void doCommand() {
        pmlUiExtension.getBendPoints().add(bendPoint);
    }

    @Override
    public void undoCommand() {
        pmlUiExtension.getBendPoints().remove(bendPoint);
    }
}
