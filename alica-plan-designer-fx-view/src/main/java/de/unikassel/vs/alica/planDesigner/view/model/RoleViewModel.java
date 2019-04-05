package de.unikassel.vs.alica.planDesigner.view.model;

import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;

import java.util.HashMap;
import java.util.Optional;

public class RoleViewModel extends PlanElementViewModel {

    private RoleSetViewModel roleSetViewModel;
    private HashMap<TaskViewModel, Float> taskPriorities;

    public RoleViewModel (long id, String name, String type) {
        super(id, name, type);
    }

    public RoleSetViewModel getRoleSetViewModel() {
        return roleSetViewModel;
    }

    public void setRoleSetViewModel(RoleSetViewModel roleSetViewModel) {
        this.roleSetViewModel = roleSetViewModel;
    }

    public void setTaskPriorities(HashMap<TaskViewModel, Float> taskPriorities) {
        this.taskPriorities = taskPriorities;
    }

    public Float getTaskPriority(long taskID) {
        Optional<TaskViewModel> task = taskPriorities.keySet().stream().filter(t -> t.getId() == taskID).findFirst();
        return task.isPresent() ?  taskPriorities.get(task.get()) : null;

    }

    public void registerListener(IGuiModificationHandler handler) {
        super.registerListener(handler);
    }

    public void setTaskPriority(TaskViewModel task, float priority) {

        if(priority != roleSetViewModel.getPriorityDefault()) {
            taskPriorities.put(task, priority);
        }
    }

    public HashMap<TaskViewModel, Float> getTaskPriorities() {
        return taskPriorities;
    }
}
