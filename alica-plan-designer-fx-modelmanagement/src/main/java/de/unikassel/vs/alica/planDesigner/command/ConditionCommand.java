package de.unikassel.vs.alica.planDesigner.command;

import de.unikassel.vs.alica.planDesigner.alicamodel.*;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public abstract class ConditionCommand extends Command {
    public ConditionCommand(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager, mmq);
    }

    protected void setCondition(Condition condition, PlanElement planElement) {
        if (planElement instanceof Behaviour) {
            Behaviour behaviour = (Behaviour) planElement;
            switch (mmq.getElementType()) {
                case Types.PRECONDITION:
                    behaviour.setPreCondition((PreCondition) condition);
                    break;
                case Types.RUNTIMECONDITION:
                    behaviour.setRuntimeCondition((RuntimeCondition) condition);
                    break;
                case Types.POSTCONDITION:
                    behaviour.setPostCondition((PostCondition) condition);
                    break;
                default:
                    throw new RuntimeException("CreateCondition: Condition type " + mmq.getElementType() + " does not exist!");
            }
        }else if (planElement instanceof Plan) {
            Plan plan = (Plan) planElement;
            switch (mmq.getElementType()) {
                case Types.PRECONDITION:
                    plan.setPreCondition((PreCondition) condition);
                    break;
                case Types.RUNTIMECONDITION:
                    plan.setRuntimeCondition((RuntimeCondition) condition);
                    break;
                default:
                    throw new RuntimeException("CreateCondition: Condition type " + mmq.getElementType() + " does not exist!");
            }
        } else if (planElement instanceof TerminalState) {
            ((TerminalState) planElement).setPostCondition((PostCondition) condition);
        } else if (planElement instanceof Transition) {
            ((Transition) planElement).setPreCondition((PreCondition) condition);
        } else {
            throw new RuntimeException("CreateCondition: Element type of element with id " + planElement.getId() + " does not have conditions!");
        }
    }
}
