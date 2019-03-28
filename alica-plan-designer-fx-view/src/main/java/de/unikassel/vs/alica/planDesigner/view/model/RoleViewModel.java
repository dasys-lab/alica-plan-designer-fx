package de.unikassel.vs.alica.planDesigner.view.model;

public class RoleViewModel extends PlanElementViewModel {

    protected RoleSetViewModel roleSetViewModel;

    public RoleViewModel (long id, String name, String type) {
        super(id, name, type);
    }

    public RoleSetViewModel getRoleSetViewModel() {
        return roleSetViewModel;
    }

    public void setRoleSetViewModel(RoleSetViewModel roleSetViewModel) {
        this.roleSetViewModel = roleSetViewModel;
    }
}
