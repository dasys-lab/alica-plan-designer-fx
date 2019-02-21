package de.unikassel.vs.alica.planDesigner.command.create;

import de.unikassel.vs.alica.planDesigner.alicamodel.*;
import de.unikassel.vs.alica.planDesigner.command.Command;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class CreateVariable extends Command {

    private final AbstractPlan abstractPlan;
    private final Variable variable;

    public CreateVariable(ModelManager manager, ModelModificationQuery mmq) {
        super(manager, mmq);
        this.abstractPlan = (AbstractPlan) modelManager.getPlanElement(mmq.getParentId());
        this.variable = createVariable(this.abstractPlan);
    }

    protected Variable createVariable(AbstractPlan abstractPlan) {
        Variable variable = new Variable(abstractPlan);
        variable.setName(mmq.getName());
        variable.setComment(mmq.getComment());
        return variable;
    }

    @Override
    public void doCommand() {
        abstractPlan.addVariable(this.variable);
        this.modelManager.storePlanElement(Types.VARIABLE, variable, false);
        this.fireEvent(ModelEventType.ELEMENT_CREATED, this.variable);
    }

    @Override
    public void undoCommand() {
        abstractPlan.removeVariable(this.variable);
        modelManager.getUsages(this.variable.getId());
        modelManager.dropPlanElement(Types.VARIABLE, variable,false);
        this.fireEvent(ModelEventType.ELEMENT_DELETED, this.variable);
    }

}
