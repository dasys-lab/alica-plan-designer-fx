package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.Bendpoint;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.PmlUiExtension;

public class AddBendpointToPlan extends AbstractCommand {

    protected PmlUiExtension pmlUiExtension;
    protected Bendpoint bendpoint;

    public AddBendpointToPlan(ModelManager modelManager, Bendpoint bendpoint, PmlUiExtension pmlUiExtension) {
        super(modelManager);
        this.bendpoint = bendpoint;
        this.pmlUiExtension = pmlUiExtension;
    }

    @Override
    public void doCommand() {
        pmlUiExtension.getBendpoints().add(bendpoint);
    }

    @Override
    public void undoCommand() {
        pmlUiExtension.getBendpoints().remove(bendpoint);
    }
}
