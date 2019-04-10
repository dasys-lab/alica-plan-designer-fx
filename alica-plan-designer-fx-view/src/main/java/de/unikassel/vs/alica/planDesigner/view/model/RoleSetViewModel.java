package de.unikassel.vs.alica.planDesigner.view.model;

import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Arrays;

public class RoleSetViewModel extends SerializableViewModel {

    private ObservableList<RoleViewModel> roles;
    private FloatProperty defaultPriority;
    private TaskRepositoryViewModel taskRepository;

    public RoleSetViewModel(long id, String name, String type, float defaultPriority) {
        super(id, name, type);
        this.defaultPriority = new SimpleFloatProperty(null, "defaultPriority", 0.01f);
        this.defaultPriority.setValue(defaultPriority);
        this.uiPropertyList.clear();
        this.uiPropertyList.addAll(Arrays.asList("name", "id", "comment", "relativeDirectory", "defaultPriority", "defaultRoleSet"));

        roles = FXCollections.observableArrayList(new ArrayList<>());
    }

    public void registerListener(IGuiModificationHandler handler) {
        super.registerListener(handler);
        defaultPriority.addListener((observable, oldValue, newValue) -> {
            fireGUIAttributeChangeEvent(handler, newValue, defaultPriority.getClass().getSimpleName(), defaultPriority.getName());
        });
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

    public FloatProperty getDefaultPriorityProperty() {
        return defaultPriority;
    }

    public float getDefaultPriority() {
        return defaultPriority.get();
    }
    public void setDefaultPriority(float defaultPriority) {
        this.defaultPriority.set(defaultPriority);
    }

    public ObservableList<RoleViewModel> getRoleViewModels() {
        return roles;
    }

    public TaskRepositoryViewModel getTaskRepository() {
        return taskRepository;
    }

    public void setTaskRepository(TaskRepositoryViewModel taskRepository) {
        this.taskRepository = taskRepository;
    }

    //    public ObservableList<TaskViewModel> getTaskViewModels() {
//        return tasks;
//    }
//
//    public void addTask(TaskViewModel task) {
//        tasks.add(task);
//    }
}
