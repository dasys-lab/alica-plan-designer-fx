package de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.add;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.Bendpoint;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import org.eclipse.emf.common.util.ECollections;

import java.util.Comparator;

public class AddBendpointToPlan extends AbstractCommand<Bendpoint> {

    private PmlUiExtension pmlUiExtension;

    public AddBendpointToPlan(Bendpoint element, PmlUiExtension pmlUiExtension, PlanElement affectedPlan) {
        super(element, affectedPlan);
        this.pmlUiExtension = pmlUiExtension;
    }

    @Override
    public void doCommand() {
        // TODO bendpoints are not saved
        pmlUiExtension.getBendpoints().add(getElementToEdit());
        // Test if saving works if no sort happens
        //ECollections.sort(pmlUiExtension.getBendpoints(), Comparator.comparingInt(Bendpoint::getXPos));
    }

    @Override
    public void undoCommand() {
        pmlUiExtension.getBendpoints().remove(getElementToEdit());
    }

    @Override
    public String getCommandString() {
        return "Add Bendpoint to Transition";
    }
}
