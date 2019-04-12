package de.unikassel.vs.alica.planDesigner.alicamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class Role extends PlanElement {

    protected HashMap<Task, Float> taskPriorities = new HashMap<>();
    protected ArrayList<Characteristic> characteristics = new ArrayList<>();
    private RoleSet roleSet;
    private ChangeListenerForDirtyFlag atributeChangedListener;

    public Role() {
        super();
    }
    public Role(long id) {
        this.id = id;
    }

    public Float getPriority(long taskID) {
        Optional<Task> task = taskPriorities.keySet().stream().filter(t -> t.getId() == taskID).findFirst();
        return task.isPresent()? taskPriorities.get(task.get()): null;
    }
    public Float getPriority(Task task) {return taskPriorities.get(task); }

    public void setTaskPriorities(HashMap<Task, Float> taskPriorities) {
        this.taskPriorities = taskPriorities;
//        this.atributeChangedListener.setDirty();
    }
    public HashMap<Task, Float> getTaskPriorities() {
        return this.taskPriorities;
    }
    public void addTaskPriority(Task task, float priority) {
        this.taskPriorities.put(task, priority);
        this.atributeChangedListener.setDirty();
    }

    public ArrayList<Characteristic> getCharacteristics() {
        return this.characteristics;
    }
    public void setCharacteristics(ArrayList<Characteristic> characteristics) {
        this.characteristics = characteristics;
    }

    public RoleSet getRoleSet() {
        return roleSet;
    }
    public void setRoleSet(RoleSet roleSet) {
        this.roleSet = roleSet;
    }

    public void registerDirtyFlag(ChangeListenerForDirtyFlag listener) {
        this.atributeChangedListener = listener;
        this.name.addListener(listener);
        this.comment.addListener(listener);
        this.characteristics.forEach(c -> c.addListener(listener));
    }
}
