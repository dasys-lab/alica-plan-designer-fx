package de.unikassel.vs.alica.planDesigner.view.editor.tab.roleTab;

import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.scene.control.Label;

public class RoleLabel extends Label {

    protected final ViewModelElement viewModelElement;

    public RoleLabel(ViewModelElement viewModelElement) {
        this.viewModelElement = viewModelElement;
    }

    public String getViewModelType() {
        return viewModelElement.getType();
    }

    public long getViewModelId() {
        return viewModelElement.getId();
    }

    public ViewModelElement getViewModelElement() {
        return viewModelElement;
    }

    public String getViewModelName() {
        return viewModelElement.getName();
    }
}
