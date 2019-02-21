package de.unikassel.vs.alica.planDesigner.command.create;

import de.unikassel.vs.alica.planDesigner.alicamodel.*;
import de.unikassel.vs.alica.planDesigner.command.Command;
import de.unikassel.vs.alica.planDesigner.command.ConditionCommand;
import de.unikassel.vs.alica.planDesigner.events.ModelEvent;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class CreateCondition extends ConditionCommand {

    protected Condition newCondition;
    protected Condition oldCondition;
    protected PlanElement planElement;

    public CreateCondition(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager, mmq);
        this.planElement = modelManager.getPlanElement(mmq.getParentId());

        this.newCondition = createNewCondition();
        this.oldCondition = getOldCondition(planElement);
    }

    protected Condition getOldCondition(PlanElement planElement) {
        Condition oldCondition;
        if (planElement instanceof Behaviour) {
            Behaviour behaviour = (Behaviour) planElement;
            switch (mmq.getElementType()) {
                case Types.PRECONDITION:
                    oldCondition = behaviour.getPreCondition();
                    break;
                case Types.RUNTIMECONDITION:
                    oldCondition = behaviour.getRuntimeCondition();
                    break;
                case Types.POSTCONDITION:
                    oldCondition = behaviour.getPostCondition();
                    break;
                default:
                    throw new RuntimeException("CreateCondition: Condition type " + mmq.getElementType() + " does not exist!");
            }
        }else if (planElement instanceof Plan) {
            Plan plan = (Plan) planElement;
            switch (mmq.getElementType()) {
                case Types.PRECONDITION:
                    oldCondition = plan.getPreCondition();
                    break;
                case Types.RUNTIMECONDITION:
                    oldCondition = plan.getRuntimeCondition();
                    break;
                default:
                    throw new RuntimeException("CreateCondition: Condition type " + mmq.getElementType() + " does not exist!");
            }
        } else if (planElement instanceof TerminalState) {
            oldCondition = ((TerminalState) planElement).getPostCondition();
        } else {
            throw new RuntimeException("CreateCondition: Element type of element with id " + planElement.getId() + " does not have conditions!");
        }
        return oldCondition;
    }

    protected Condition createNewCondition() {
        Condition condition;
        switch (mmq.getElementType()) {
            case Types.PRECONDITION:
                condition = new PreCondition();
                break;
            case Types.RUNTIMECONDITION:
                condition = new PreCondition();
                break;
            case Types.POSTCONDITION:
                condition = new PreCondition();
                break;
            default:
                throw new RuntimeException("CreateCondition: Condition type " + mmq.getElementType() + " does not exist!");
        }
        condition.setPluginName(mmq.getName());
        return condition;
    }

    @Override
    public void doCommand() {
        setCondition(newCondition, planElement);

        modelManager.dropPlanElement(mmq.getElementType(), oldCondition, false);
        this.fireEvent(ModelEventType.ELEMENT_DELETED, oldCondition);

        modelManager.storePlanElement(mmq.getElementType(), newCondition, false);
        this.fireEvent(ModelEventType.ELEMENT_CREATED, newCondition);
    }

    @Override
    public void undoCommand() {
        setCondition(oldCondition, planElement);

        modelManager.dropPlanElement(mmq.getElementType(), newCondition, false);
        this.fireEvent(ModelEventType.ELEMENT_DELETED, newCondition);

        modelManager.storePlanElement(mmq.getElementType(), oldCondition, false);
        this.fireEvent(ModelEventType.ELEMENT_CREATED, oldCondition);
    }
}
