package de.uni_kassel.vs.cn.planDesigner.command.change;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.EntryPoint;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Task;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;


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
