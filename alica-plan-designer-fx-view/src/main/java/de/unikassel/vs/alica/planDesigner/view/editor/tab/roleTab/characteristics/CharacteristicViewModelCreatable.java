package de.unikassel.vs.alica.planDesigner.view.editor.tab.roleTab.characteristics;

import de.unikassel.vs.alica.planDesigner.view.editor.tab.roleTab.roles.RoleTableView;
import de.unikassel.vs.alica.planDesigner.view.model.CharacteristicViewModel;

public class CharacteristicViewModelCreatable extends CharacteristicViewModel {
    public CharacteristicViewModelCreatable(int id, String name, String roleCharcteristic, RoleTableView roleTableView) {
        super(id, name, roleCharcteristic, roleTableView);
    }
}
