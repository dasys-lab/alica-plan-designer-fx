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
    private FloatProperty priorityDefault;
    private TaskRepositoryViewModel taskRepository;

    public RoleSetViewModel(long id, String name, String type, float priorityDefault) {
        super(id, name, type);
        this.priorityDefault  = new SimpleFloatProperty(null, "priorityDefault", 0.01f);
        this.priorityDefault.setValue(priorityDefault);
        this.uiPropertyList.clear();
        this.uiPropertyList.addAll(Arrays.asList("name", "id", "comment", "relativeDirectory", "priorityDefault"));

        roles = FXCollections.observableArrayList(new ArrayList<>());
    }

    public void registerListener(IGuiModificationHandler handler) {
        super.registerListener(handler);
        priorityDefault.addListener((observable, oldValue, newValue) -> {
            fireGUIAttributeChangeEvent(handler, newValue, priorityDefault.getClass().getSimpleName(), priorityDefault.getName());
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
