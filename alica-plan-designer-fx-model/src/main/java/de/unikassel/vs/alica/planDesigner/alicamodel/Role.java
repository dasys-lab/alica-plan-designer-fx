package de.unikassel.vs.alica.planDesigner.alicamodel;

import java.util.ArrayList;

public class Role extends PlanElement {

    protected ArrayList<Characteristic> characteristics = new ArrayList<>();
    private RoleSet roleSet;

    public Role() {
        super();
    }

    public Role(long id) {
        this.id = id;
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
