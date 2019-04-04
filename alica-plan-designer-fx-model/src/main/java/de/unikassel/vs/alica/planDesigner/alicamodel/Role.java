package de.unikassel.vs.alica.planDesigner.alicamodel;

import java.util.ArrayList;
import java.util.HashMap;

public class Role extends PlanElement {

    protected ArrayList<Characteristic> characteristics = new ArrayList<>();
    protected HashMap<Long, Float> taskPriorities = new HashMap<>();
    private RoleSet roleSet;

    public Role() {
        super();
    }

    public Role(long id) {
        this.id = id;
    }

    public float getPriority(long taskID) {
        return taskPriorities.get(taskID);
    }

    public HashMap<Long, Float> getTaskPriorities() {
        return this.taskPriorities;
    }

    public void addTaskIDPriority(long taskID , float priority) {
        this.taskPriorities.put(taskID, priority);
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
}
