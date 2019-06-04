package de.unikassel.vs.alica.planDesigner.view.editor.tab.roleTab.roles;

import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.model.RoleViewModel;

public class RoleViewModelCreatable extends RoleViewModel {


    public RoleViewModelCreatable(long id, String name, String type) {
        super(id, name, type);
    }

    public void registerListener(IGuiModificationHandler handler) {
        name.addListener((observable, oldValue, newValue) -> {
            if (getName() != null && !getName().isEmpty()) {
                GuiModificationEvent event = new GuiModificationEvent(GuiEventType.CREATE_ELEMENT, Types.ROLE, getName());
                event.setParentId(getRoleSetViewModel().getId());
                handler.handle(event);
                setName("");
            }
        });
    }
}
