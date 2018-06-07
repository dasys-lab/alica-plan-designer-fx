package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Task;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.TaskRepository;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;

/**
 * Created by marci on 01.03.17.
 */
public class DeleteTaskFromRepository extends AbstractCommand<Task> {
    private final TaskRepository parentOfElement;

    public DeleteTaskFromRepository(TaskRepository parentOfElement, Task toDelete) {
        super(toDelete, parentOfElement);
        this.parentOfElement = parentOfElement;
    }

    @Override
    public void doCommand() {
        parentOfElement.getTasks().remove(getElementToEdit());
    }

    @Override
    public void undoCommand() {
        parentOfElement.getTasks().add(getElementToEdit());
    }

    @Override
    public String getCommandString() {
        return "Remove Task " + getElementToEdit().getName() + "from Repository";
    }
}
