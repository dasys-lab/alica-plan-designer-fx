package de.unikassel.vs.alica.planDesigner.command.create;

import de.unikassel.vs.alica.planDesigner.alicamodel.*;
import de.unikassel.vs.alica.planDesigner.command.Command;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class CreateVariableBinding extends Command {

    private final VariableBinding variableBinding;
    private final PlanElement parent;

    public CreateVariableBinding(ModelManager manager, ModelModificationQuery mmq) {
        super(manager, mmq);
        this.parent = modelManager.getPlanElement(mmq.getParentId());
        this.variableBinding = createVariableBinding();
    }

    protected VariableBinding createVariableBinding() {
        VariableBinding variableBinding = new VariableBinding();

        PlanElement tempElement =  modelManager.getPlanElement(mmq.getRelatedObjects().get(Types.PLAN));
        if (tempElement instanceof AnnotatedPlan) {
            variableBinding.setSubPlan(((AnnotatedPlan) tempElement).getPlan());
        } else if (tempElement instanceof Plan) {
            variableBinding.setSubPlan((Plan) tempElement);
        } else if (tempElement instanceof PlanType) {
            variableBinding.setSubPlan((Plan) tempElement);
        } else {
            variableBinding.setSubPlan((Behaviour) tempElement);
        }
        variableBinding.setSubVariable((Variable) modelManager.getPlanElement(mmq.getRelatedObjects().get(Types.VARIABLEBINDING)));
        variableBinding.setVariable((Variable) modelManager.getPlanElement(mmq.getRelatedObjects().get(Types.VARIABLE)));

        return variableBinding;
    }

    @Override
    public void doCommand() {
        if (parent instanceof State) {
            ((State) parent).addVariableBinding(this.variableBinding);
        } else if (parent instanceof PlanType) {
            ((PlanType) parent).addParametrisation(this.variableBinding);
        }
        this.modelManager.storePlanElement(Types.VARIABLEBINDING, this.variableBinding, false);
        this.fireEvent(ModelEventType.ELEMENT_CREATED, this.variableBinding);
    }

    @Override
    public void undoCommand() {
        if (parent instanceof State) {
            ((State) parent).removeVariableBinding(this.variableBinding);
        } else if (parent instanceof PlanType) {
            ((PlanType) parent).removeParametrisation(this.variableBinding);
        }
        modelManager.dropPlanElement(Types.VARIABLEBINDING, this.variableBinding,false);
        this.fireEvent(ModelEventType.ELEMENT_DELETED, this.variableBinding);
    }
}
