package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.alica.Task;
import de.uni_kassel.vs.cn.planDesigner.alica.TaskRepository;

import static de.uni_kassel.vs.cn.planDesigner.emfUtil.EMFModelUtils.getAlicaFactory;

/**
 * Created by marci on 23.02.17.
 */
public class AddTaskToRepository extends AbstractCommand<Task> {

    private final TaskRepository parentOfElement;
    private final String name;

    public AddTaskToRepository(TaskRepository parentOfElement, String name) {
        super(getAlicaFactory().createTask(), parentOfElement);
        this.parentOfElement = parentOfElement;
        this.name = name;
    }

    @Override
    public void doCommand() {
        getElementToEdit().setName(name);
        parentOfElement.getTasks().add(getElementToEdit());
    }

    @Override
    public void undoCommand() {
        parentOfElement.getTasks().remove(getElementToEdit());
    }

    @Override
    public String getCommandString() {
        return "Create new task " + name;
    }
}
