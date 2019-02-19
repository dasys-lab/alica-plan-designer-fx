package de.unikassel.vs.alica.planDesigner.command.add;

import de.unikassel.vs.alica.planDesigner.alicamodel.AbstractPlan;
import de.unikassel.vs.alica.planDesigner.alicamodel.EntryPoint;
import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.alicamodel.Task;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.events.ModelEvent;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class AddTaskToEntryPoint extends AbstractCommand {
    protected EntryPoint entryPoint;
    protected Task task;

    public AddTaskToEntryPoint(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager);
        this.task = (Task) modelManager.getPlanElement(mmq.getElementId());
        this.entryPoint = (EntryPoint) modelManager.getPlanElement(mmq.getTargetID());
    }

    @Override
    public void doCommand() {
        entryPoint.setTask(task);
        entryPoint.getPlan().setDirty(true);
        //event for updateView
        Plan plan = (Plan) entryPoint.getPlan();
        ModelEvent event = new ModelEvent(ModelEventType.ELEMENT_ADD, plan, Types.TASK);
        event.setParentId(task.getId());
        event.setNewValue(entryPoint);
        modelManager.fireEvent(event);
    }

    @Override
    public void undoCommand() { entryPoint.setTask(null); }
}
