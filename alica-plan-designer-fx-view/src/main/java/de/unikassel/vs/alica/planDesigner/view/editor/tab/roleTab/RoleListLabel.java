package de.unikassel.vs.alica.planDesigner.view.editor.tab.roleTab;

import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.scene.control.Label;

public class RoleListLabel extends Label {

    protected final ViewModelElement viewModelElement;
    protected IGuiModificationHandler guiModificationHandler;

    public RoleListLabel(ViewModelElement viewModelElement, IGuiModificationHandler guiModificationHandler) {
        this.viewModelElement = viewModelElement;
        this.guiModificationHandler = guiModificationHandler;
        this.viewModelElement.nameProperty().addListener((observable, oldValue, newValue) -> setText(newValue));
    }

    public ViewModelElement getViewModelElement() {
        return viewModelElement;
    }

    public long getViewModelId() {
        return viewModelElement.getId();
    }

    public String getViewModelName() {
        return viewModelElement.getName();
    }
}
