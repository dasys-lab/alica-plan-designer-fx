package de.unikassel.vs.alica.planDesigner.view.model;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class RoleSetViewModel extends SerializableViewModel {

    private FloatProperty priorityDefault = new SimpleFloatProperty(null, "priorityDefault", 0.0f);
    private ObservableList<RoleViewModel> roles;
    private ObservableList<TaskViewModel> tasks;

//    public RoleSetViewModel(long id, String name, String type) {
//        this(id, name, type, 0.0f);
//    }

    public RoleSetViewModel(long id, String name, String type, float priorityDefault) {
        super(id, name, type);
        this.priorityDefault.setValue(priorityDefault);
        roles = FXCollections.observableArrayList(new ArrayList<>());
        tasks = FXCollections.observableArrayList(new ArrayList<>());
        this.uiPropertyList.add("priorityDefault");
    }

    public void addRole(RoleViewModel role) {
        if (!this.roles.contains(role)) {
            this.roles.add(role);
        }
    }

    public void removeRole(long id) {
        for(ViewModelElement role : roles) {
            if(role.getId() == id) {
                this.roles.remove(role);
                break;
            }
        }
    }

    public FloatProperty priorityDefaultProperty() {
        return priorityDefault;
    }

    public float getPriorityDefault() {
        return priorityDefault.get();
    }

    public void setPriorityDefault(float priorityDefault) {
        this.priorityDefault.set(priorityDefault);
    }

    public ObservableList<RoleViewModel> getRoleViewModels() {
        return roles;
    }

    public ObservableList<TaskViewModel> getTaskViewModels() {
        return tasks;
    }

    public void addTask(TaskViewModel task) {
        tasks.add(task);
    }
}
