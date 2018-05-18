package de.uni_kassel.vs.cn.planDesigner.command.change;

import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.alica.EntryPoint;
import de.uni_kassel.vs.cn.planDesigner.alica.Task;

/**
 * Created by marci on 07.04.17.
 */
public class SetTaskOfEntryPoint extends AbstractCommand<Task> {

    private EntryPoint parentOfElement;
    private Task previousTask;

    public SetTaskOfEntryPoint(Task element, EntryPoint parentOfElement) {
        super(element, parentOfElement.getPlan());
        this.parentOfElement = parentOfElement;
    }

    @Override
    public void doCommand() {
        previousTask = parentOfElement.getTask();
        parentOfElement.setTask(getElementToEdit());
    }

    @Override
    public void undoCommand() {
        parentOfElement.setTask(previousTask);
    }

    @Override
    public String getCommandString() {
        return "Set Task of EntryPoint " + parentOfElement.getId() + " from " + previousTask.getName()
                + " to " + getElementToEdit().getName();
    }
}
