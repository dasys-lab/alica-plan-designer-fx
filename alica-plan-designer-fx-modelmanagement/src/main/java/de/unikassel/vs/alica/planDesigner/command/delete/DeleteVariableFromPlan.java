package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.AbstractPlan;
import de.unikassel.vs.alica.planDesigner.alicamodel.Condition;
import de.unikassel.vs.alica.planDesigner.alicamodel.PlanElement;
import de.unikassel.vs.alica.planDesigner.alicamodel.Variable;
import de.unikassel.vs.alica.planDesigner.command.Command;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class DeleteVariableFromPlan extends Command {

    protected Variable variable;
    protected PlanElement parentElement;

    public DeleteVariableFromPlan(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager, mmq);
        this.variable = (Variable) modelManager.getPlanElement(mmq.getElementId());
        this.parentElement = modelManager.getPlanElement(mmq.getParentId());
    }

    @Override
    public void doCommand() {
        if(parentElement instanceof Condition){
            ((Condition) parentElement).removeVariable(this.variable);
        }else{
            ((AbstractPlan) parentElement).removeVariable(this.variable);
        }
        this.modelManager.dropPlanElement(Types.VARIABLE, this.variable, false);
        this.fireEvent(ModelEventType.ELEMENT_DELETED, this.variable);
    }

    @Override
    public void undoCommand() {
        if(parentElement instanceof Condition){
            ((Condition) parentElement).addVariable(this.variable);
        }else{
            ((AbstractPlan) parentElement).addVariable(this.variable);
        }
        this.modelManager.storePlanElement(Types.VARIABLE, this.variable, false);
        this.fireEvent(ModelEventType.ELEMENT_CREATED, this.variable);
    }
}
