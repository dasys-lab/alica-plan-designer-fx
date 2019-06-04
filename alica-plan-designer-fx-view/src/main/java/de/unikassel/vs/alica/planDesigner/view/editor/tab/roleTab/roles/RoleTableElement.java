package de.unikassel.vs.alica.planDesigner.view.editor.tab.roleTab.roles;

import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.model.RoleViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.beans.property.StringProperty;

public class RoleTableElement {
    private RoleTableView tableView;
    private ViewModelElement viewModelElement;

    public RoleTableElement(RoleTableView tableView,  ViewModelElement viewModelElement) {
        this.tableView = tableView;
        this.viewModelElement = viewModelElement;
        this.viewModelElement.registerListener(tableView.getGuiModificationHandler());
    }

    public RoleTableElement(RoleTableView tableView,  ViewModelElement viewModelElement, IGuiModificationHandler guiModificationHandler) {
        this.tableView = tableView;
        this.viewModelElement = viewModelElement;
        this.viewModelElement.registerListener(guiModificationHandler);
    }

    public RoleTableView getTableView() {
        return tableView;
    }

    public RoleViewModel getViewModel() {
        return (RoleViewModel) viewModelElement;
    }

    public StringProperty nameProperty() {
        return viewModelElement.nameProperty();
    }
    public void setName(String name) {
        this.viewModelElement.setName(name);
    }

    public ViewModelElement getViewModelElement() {
        return viewModelElement;
    }

    public long getViewModelId() { return viewModelElement.getId(); }
}
