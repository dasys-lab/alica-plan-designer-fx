package de.unikassel.vs.alica.planDesigner.alicamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class Role extends PlanElement {

    protected HashMap<Task, Float> taskPriorities = new HashMap<>();
    protected ArrayList<Characteristic> characteristics = new ArrayList<>();
    private RoleSet roleSet;

    public Role() {
        super();
    }

    public Role(long id) {
        this.id = id;
    }

    public float getPriority(long taskID) {
        Optional<Task> task = taskPriorities.keySet().stream().filter(t -> t.getId() == taskID).findFirst();
        return taskPriorities.get(task.get());
    }
    public float getPriority(Task task) {return taskPriorities.get(task); }

    public HashMap<Task, Float> getTaskPriorities() {
        return this.taskPriorities;
    }

    public void addTaskIDPriority(Task task , float priority) {
        this.taskPriorities.put(task, priority);
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
