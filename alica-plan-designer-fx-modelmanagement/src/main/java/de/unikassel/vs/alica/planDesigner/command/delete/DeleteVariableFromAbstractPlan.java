package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.*;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class DeleteVariableFromAbstractPlan extends AbstractCommand {

    protected Variable variable;
    protected AbstractPlan abstractPlan;

    public DeleteVariableFromAbstractPlan(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager);
        this.variable = (Variable) modelManager.getPlanElement(mmq.getElementId());
        this.abstractPlan = (AbstractPlan) modelManager.getPlanElement(mmq.getParentId());
    }

    @Override
    public void doCommand() {
        modelManager.removedPlanElement(Types.VARIABLE, variable, abstractPlan, false);
//        if (abstractPlan instanceof WithVariables) {
//            ((WithVariables) abstractPlan).removeVariable(variable);
//        } else {
//          throw new RuntimeException(this.getClass().getName() + ": Parent does not implement WithVariables Interface");
//        }
    }

    @Override
    public void undoCommand() {
        modelManager.storePlanElement(Types.VARIABLE, variable, abstractPlan, false);
//        if (abstractPlan instanceof WithVariables) {
//            ((Plan) abstractPlan).addVariable(variable);
//        } else {
//            throw new RuntimeException(this.getClass().getName() + ": Parent does not implement WithVariables Interface");
//        }
    }
}
