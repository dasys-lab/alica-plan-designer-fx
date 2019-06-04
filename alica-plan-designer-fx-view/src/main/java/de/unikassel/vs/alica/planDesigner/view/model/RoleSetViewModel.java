package de.unikassel.vs.alica.planDesigner.view.model;

import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoleSetViewModel extends SerializableViewModel {

    private TaskRepositoryViewModel taskRepository;
    private FloatProperty defaultPriority;
    private BooleanProperty defaultRoleSet;
    private ObservableList<RoleViewModel> roleViewModels;

    public RoleSetViewModel(long id, String name, String type, float defaultPriority, boolean defaultRoleSet) {
        super(id, name, type);
        this.defaultPriority = new SimpleFloatProperty(null, "defaultPriority", 0.0f);
        this.defaultPriority.setValue(defaultPriority);
        this.defaultRoleSet = new SimpleBooleanProperty(null, "defaultRoleSet", true);
        this.defaultRoleSet.setValue(defaultRoleSet);
        this.uiPropertyList.clear();
        this.uiPropertyList.addAll(Arrays.asList("name", "id", "comment", "relativeDirectory", "defaultPriority", "defaultRoleSet"));

        roleViewModels = FXCollections.observableArrayList(new ArrayList<>());
//        roleViewModels.addListener(new ListChangeListener<RoleViewModel>() {
//            @Override
//            public void onChanged(Change<? extends RoleViewModel> c) {
//                if(!c.next())
//                    return;
//                List<? extends RoleViewModel> addedSubList = c.getAddedSubList();
//                System.out.println("RSVM: (debugging) role model list changed: " + addedSubList.get(0));
//            }
//        });
    }

    public void registerListener(IGuiModificationHandler handler) {
        super.registerListener(handler);
        defaultPriority.addListener((observable, oldValue, newValue) -> {
            fireGUIAttributeChangeEvent(handler, newValue, defaultPriority.getClass().getSimpleName(), defaultPriority.getName());
        });
        defaultRoleSet.addListener((observable, oldValue, newValue) -> {
            fireGUIAttributeChangeEvent(handler, newValue, defaultRoleSet.getClass().getSimpleName(), defaultRoleSet.getName());
        });
    }

    public void addRole(RoleViewModel role) {
        if (!this.roleViewModels.contains(role)) {
            this.roleViewModels.add(role);
        }
    }

    public void removeRole(long id) {
        for(ViewModelElement role : roleViewModels) {
            if(role.getId() == id) {
                this.roleViewModels.remove(role);
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

    public boolean getDefaultRoleSet() {
        return defaultRoleSet.get();
    }
    public void setDefaultRoleSet(boolean defaultRoleSet) {
        this.defaultRoleSet.set(defaultRoleSet);
    }


    public ObservableList<RoleViewModel> getRoleViewModels() {
        return roleViewModels;
    }

    public TaskRepositoryViewModel getTaskRepository() {
        return taskRepository;
    }

    public void setTaskRepository(TaskRepositoryViewModel taskRepository) {
        this.taskRepository = taskRepository;
    }
}
