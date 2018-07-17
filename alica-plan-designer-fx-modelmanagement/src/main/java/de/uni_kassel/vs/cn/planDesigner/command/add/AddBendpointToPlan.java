package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.BendPoint;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.PmlUiExtension;

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
