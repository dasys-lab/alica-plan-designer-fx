package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Task;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.TaskRepository;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;


public class AddTaskToRepository extends AbstractCommand {

    private final TaskRepository taskRepository;
    private final Task task;

    public AddTaskToRepository(ModelManager manager, TaskRepository taskRepository, Task task) {
        super(manager);
        this.taskRepository = taskRepository;
        this.task = task;
    }

    @Override
    public void doCommand() {
        taskRepository.getTasks().add(task);
    }

    @Override
    public void undoCommand() {
        taskRepository.getTasks().remove(task);
    }
}
