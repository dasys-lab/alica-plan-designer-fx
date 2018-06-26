package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Task;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.TaskRepository;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;

public class DeleteTaskFromRepository extends AbstractCommand {
    protected final TaskRepository taskRepository;
    protected Task task;

    public DeleteTaskFromRepository(ModelManager modelManager, TaskRepository taskRepository, Task task) {
        super(modelManager);
        this.taskRepository = taskRepository;
        this.task = task;
    }

    @Override
    public void doCommand() {
        taskRepository.getTasks().remove(task);
    }

    @Override
    public void undoCommand() {
        taskRepository.getTasks().add(task);
    }
}
