package de.unikassel.vs.alica.planDesigner.command.create;

import de.unikassel.vs.alica.planDesigner.alicamodel.Role;
import de.unikassel.vs.alica.planDesigner.alicamodel.RoleSet;
import de.unikassel.vs.alica.planDesigner.alicamodel.Task;
import de.unikassel.vs.alica.planDesigner.alicamodel.TaskRepository;
import de.unikassel.vs.alica.planDesigner.command.Command;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class CreateRole extends Command {

    Role role;
    RoleSet roleSet;

    public CreateRole(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager, mmq);
        this.roleSet = (RoleSet) modelManager.getPlanElement(mmq.getParentId());
        this.role = createRole(this.roleSet);
    }

    protected Role createRole(RoleSet roleSet) {
        Role role = new Role();
        role.setName(mmq.getName());
        role.setRoleSet(roleSet);
        return role;
    }

    @Override
    public void doCommand() {
        this.roleSet.addRole(role);
        modelManager.storePlanElement(Types.ROLE, this.role,  false);
        this.fireEvent(ModelEventType.ELEMENT_CREATED, this.role);
    }

    @Override
    public void undoCommand() {
        this.roleSet.removeRole(role);
        modelManager.dropPlanElement(Types.ROLE, this.role, false);
        this.fireEvent(ModelEventType.ELEMENT_DELETED, this.role);
    }
}
