package de.unikassel.vs.alica.planDesigner.view.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class RoleSetViewModel extends SerializableViewModel {

    private ObservableList<RoleViewModel> roles;

    public RoleSetViewModel(long id, String name, String type) {
        super(id, name, type);
        roles = FXCollections.observableArrayList(new ArrayList<>());
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

    public ObservableList<RoleViewModel> getRoleViewModels() {
        return roles;
    }
}
