package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.AnnotatedPlan;
import de.unikassel.vs.alica.planDesigner.alicamodel.PlanType;
import de.unikassel.vs.alica.planDesigner.alicamodel.VariableBinding;
import de.unikassel.vs.alica.planDesigner.command.Command;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

import java.util.ArrayList;
import java.util.List;

public class DeleteAnnotatedPlan extends Command {

    protected PlanType planType;
    protected AnnotatedPlan annotatedPlan;
    private List<VariableBinding> variableBindingList;

    public DeleteAnnotatedPlan(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager, mmq);
        this.planType = (PlanType) modelManager.getPlanElement(mmq.getParentId());
        this.annotatedPlan = (AnnotatedPlan) modelManager.getPlanElement(mmq.getElementId());
    }

    @Override
    public void doCommand() {
        this.planType.removeAnnotatedPlan(this.annotatedPlan);
        //remove VariableBindings with AnnotatedPlan
        variableBindingList = new ArrayList<>(this.planType.getVariableBindings());
        for (VariableBinding variableBinding: variableBindingList) {
            if(variableBinding.getSubPlan().getId() == this.annotatedPlan.getPlan().getId()) {
                this.planType.removeVariableBinding(variableBinding);
            }
        }

        this.modelManager.dropPlanElement(Types.ANNOTATEDPLAN, this.annotatedPlan, false);
        this.fireEvent(ModelEventType.ELEMENT_DELETED, this.annotatedPlan);
    }

    @Override
    public void undoCommand() {
        this.planType.addAnnotatedPlan(this.annotatedPlan);
        //add VariableBindings with AnnotatedPlan
        for (VariableBinding variableBinding : variableBindingList) {
            if (variableBinding.getSubPlan().getId() == this.annotatedPlan.getPlan().getId()) {
                this.planType.addVariableBinding(variableBinding);
            }
        }

        this.modelManager.storePlanElement(Types.ANNOTATEDPLAN, this.annotatedPlan, false);
        this.fireEvent(ModelEventType.ELEMENT_CREATED, this.annotatedPlan);
    }
}
