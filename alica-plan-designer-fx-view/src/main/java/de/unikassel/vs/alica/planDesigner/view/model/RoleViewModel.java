package de.unikassel.vs.alica.planDesigner.view.model;

import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;

import java.util.HashMap;

public class RoleViewModel extends PlanElementViewModel {

    //TODO: move to model side
    public static final float DEFAULT = 0.0f;

    private RoleSetViewModel roleSetViewModel;
    private HashMap<Long, Float> taskPriorities;

    public RoleViewModel (long id, String name, String type) {
        super(id, name, type);
    }

    public RoleSetViewModel getRoleSetViewModel() {
        return roleSetViewModel;
    }

    public void setRoleSetViewModel(RoleSetViewModel roleSetViewModel) {
        this.roleSetViewModel = roleSetViewModel;
    }

    public void setTaskPriorities(HashMap<Long, Float> taskPriorities) {
        this.taskPriorities = taskPriorities;
    }

    public float getTaskPriority(long taskID) {
        return taskPriorities.containsKey(taskID)? taskPriorities.get(taskID) : RoleViewModel.DEFAULT;
    }

    public void registerListener(IGuiModificationHandler handler) {
        super.registerListener(handler);
    }

    public void setTaskPriority(long taskID, float priority) {

        if(priority != DEFAULT) {
            taskPriorities.put(taskID, priority);
        }
    }
}
