package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.*;
import de.unikassel.vs.alica.planDesigner.command.Command;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class DeleteParametrisation extends Command {

    protected VariableBinding variableBinding;
    protected PlanElement parent;

    public DeleteParametrisation(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager, mmq);
        this.variableBinding = (VariableBinding) modelManager.getPlanElement(mmq.getElementId());
        this.parent = modelManager.getPlanElement(mmq.getParentId());
    }

    @Override
    public void doCommand() {
        if (parent instanceof State) {
            ((State) this.parent).removeVariableBinding(variableBinding);
        } else if (parent instanceof PlanType) {
            ((PlanType) this.parent).removeParametrisation(variableBinding);
        }
        this.modelManager.dropPlanElement(Types.VARIABLE, this.variableBinding, false);
        this.fireEvent(ModelEventType.ELEMENT_DELETED, this.variableBinding);
    }

    @Override
    public void undoCommand() {
        if (parent instanceof State) {
            ((State) this.parent).addVariableBinding(variableBinding);
        } else if (parent instanceof PlanType) {
            ((PlanType) this.parent).addParametrisation(variableBinding);
        }
        this.modelManager.storePlanElement(Types.VARIABLEBINDING, this.variableBinding, false);
        this.fireEvent(ModelEventType.ELEMENT_CREATED, this.variableBinding);
    }
}
