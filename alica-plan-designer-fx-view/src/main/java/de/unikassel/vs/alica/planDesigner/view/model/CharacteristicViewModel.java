package de.unikassel.vs.alica.planDesigner.view.model;

import java.util.Arrays;

public class CharacteristicViewModel extends PlanElementViewModel {

    protected RoleSetViewModel roleSetViewModel;

    public CharacteristicViewModel (long id, String name, String type) {
        super(id, name, type);

        this.uiPropertyList.clear();
        this.uiPropertyList.addAll(Arrays.asList("name", "id", "comment"));
    }

    public RoleSetViewModel getRoleSetViewModel() {
        return roleSetViewModel;
    }

    public void setRoleSetViewModel(RoleSetViewModel roleSetViewModel) {
        this.roleSetViewModel = roleSetViewModel;
    }
}
