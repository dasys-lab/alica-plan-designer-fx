package de.unikassel.vs.alica.planDesigner.alicamodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoleSet extends SerializablePlanElement {

    private float priorityDefault;
    private final ArrayList<Role> roles = new ArrayList<>();

    public RoleSet() {}

    public float getPriorityDefault() {
        return priorityDefault;
    }

    public void setPriorityDefault(float priorityDefault) {
        this.priorityDefault = priorityDefault;
    }

    public Role getRole(long roleID) {
        for (Role role : roles) {
            if (roleID == role.getId()) {
                return role;
            }
        }
        return null;
    }

    public boolean contains (Role role) {
        return roles.contains(role);
    }

    public List<Role> getRoles() {
        return Collections.unmodifiableList(roles);
    }

    public void addRole(Role role) {
        role.registerDirtyFlag(this.changeListenerForDirtyFlag);
        roles.add(role);
        this.changeListenerForDirtyFlag.setDirty();
    }

    public void removeRole(Role role) {
        roles.remove(role);
        this.changeListenerForDirtyFlag.setDirty();
    }

    @Override
    public void registerDirtyFlag() {
        super.registerDirtyFlag();
        for (Role role : roles) {
            role.registerDirtyFlag(this.changeListenerForDirtyFlag);
        }
    }
}
