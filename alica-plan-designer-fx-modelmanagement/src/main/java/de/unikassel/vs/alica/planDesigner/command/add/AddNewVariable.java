package de.unikassel.vs.alica.planDesigner.command.add;

import de.unikassel.vs.alica.planDesigner.alicamodel.*;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class AddNewVariable extends AbstractCommand {

    ModelModificationQuery mmq;

    private final PlanElement parentPlanElement;
    private final Variable variable ;

    public AddNewVariable(ModelManager manager, ModelModificationQuery mmq) {
        super(manager);
        this.mmq = mmq;
        if (!mmq.getElementType().equals(Types.VARIABLE)) {
            throw new RuntimeException("AddNewVariable: Type does not match command!");
        }

        this.parentPlanElement = manager.getPlanElement(mmq.getParentId());
        if (!(parentPlanElement instanceof Behaviour) && !(parentPlanElement instanceof Plan)) {
            throw new RuntimeException("AddNewVariable: The variable's parent element isn't a behaviour or a plan!");
        }

        this.variable = new Variable((AbstractPlan) parentPlanElement);
        this.variable.setName(mmq.getName());
    }

    @Override
    public void doCommand() {
        modelManager.createdPlanElement(Types.VARIABLE, variable, parentPlanElement, false);
    }

    @Override
    public void undoCommand() {
        modelManager.removedPlanElement(Types.VARIABLE, variable, parentPlanElement, false);
    }

}
