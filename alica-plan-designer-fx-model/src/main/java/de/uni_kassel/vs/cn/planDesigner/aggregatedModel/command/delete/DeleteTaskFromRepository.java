package de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.delete;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.Command;
import de.uni_kassel.vs.cn.planDesigner.alica.Task;
import de.uni_kassel.vs.cn.planDesigner.alica.TaskRepository;

/**
 * Created by marci on 01.03.17.
 */
public class DeleteTaskFromRepository extends Command<Task> {
    private final TaskRepository parentOfElement;

    public DeleteTaskFromRepository(TaskRepository parentOfElement, Task toDelete) {
        super(toDelete);
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
