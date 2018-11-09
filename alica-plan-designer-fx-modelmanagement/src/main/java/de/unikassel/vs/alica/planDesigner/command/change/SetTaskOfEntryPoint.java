package de.unikassel.vs.alica.planDesigner.command.change;

import de.unikassel.vs.alica.planDesigner.alicamodel.EntryPoint;
import de.unikassel.vs.alica.planDesigner.alicamodel.Task;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;


public class SetTaskOfEntryPoint extends AbstractCommand {

    protected EntryPoint entryPoint;
    protected Task previousTask;
    protected Task newTask;

    public SetTaskOfEntryPoint(ModelManager modelManager, Task newTask, EntryPoint entryPoint) {
        super(modelManager);
        this.entryPoint = entryPoint;
    }

    @Override
    public void doCommand() {
        previousTask = entryPoint.getTask();
        entryPoint.setTask(newTask);
    }

    @Override
    public void undoCommand() {
        entryPoint.setTask(previousTask);
    }
}
