package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.*;
import de.unikassel.vs.alica.planDesigner.command.Command;
import de.unikassel.vs.alica.planDesigner.command.ConditionCommand;
import de.unikassel.vs.alica.planDesigner.events.ModelEvent;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class DeleteCondition extends ConditionCommand {

    protected Condition condition;
    protected PlanElement planElement;

    public DeleteCondition(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager, mmq);
        this.planElement = modelManager.getPlanElement(mmq.getParentId());
        this.condition = (Condition) modelManager.getPlanElement(mmq.getElementId());
    }

    @Override
    public void doCommand() {
        setCondition(null, planElement);

        modelManager.dropPlanElement(mmq.getElementType(), condition, false);
        ModelEvent event = new ModelEvent(ModelEventType.ELEMENT_DELETED, condition, mmq.getElementType());
        event.setParentId(mmq.getParentId());
        modelManager.fireEvent(event);
    }

    @Override
    public void undoCommand() {
        setCondition(condition, planElement);

        modelManager.storePlanElement(mmq.getElementType(), condition, false);
        ModelEvent event = new ModelEvent(ModelEventType.ELEMENT_CREATED, condition, mmq.getElementType());
        event.setParentId(mmq.getParentId());
        modelManager.fireEvent(event);
    }
}
