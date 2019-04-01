package de.unikassel.vs.alica.planDesigner.alicamodel;

import java.util.ArrayList;
import java.util.HashMap;

public class Role extends PlanElement {

    protected ArrayList<Characteristic> characteristics = new ArrayList<>();
    protected HashMap<Long, Float> taskPriority = new HashMap<>();
    private RoleSet roleSet;

    public Role() {
        super();
    }

    public Role(long id) {
        this.id = id;
    }

    public float getPriority(long taskID) {
        return taskPriority.get(taskID);
    }

    public HashMap<Long, Float> getTaskPriority() {
        return this.taskPriority;
    }

    public void addTaskIDPriority(long taskID , float priority) {
        this.taskPriority.put(taskID, priority);
    }

    public ArrayList<Characteristic> getCharacteristics() {
        return this.characteristics;
    }

    public RoleSet getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(RoleSet roleSet) {
        this.roleSet = roleSet;
    }

    public void registerDirtyFlag(ChangeListenerForDirtyFlag listener) {
        comment.addListener(listener);
        name.addListener(listener);
    }

    public void addTask(Task task) {

        if(!taskPriority.keySet().contains(task.getId())) {
            taskPriority.put(task.getId(), 0.0f);
        }
    }
}
