package de.uni_kassel.vs.cn.planDesigner.view.editor.tab.behaviourTab;

import de.uni_kassel.vs.cn.planDesigner.common.ViewModelElement;
import de.uni_kassel.vs.cn.planDesigner.events.GuiModificationEvent;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.IEditorTab;
import javafx.scene.control.Tab;

public class BehaviourTab extends Tab implements IEditorTab {

    ViewModelElement behaviour;
    ViewModelElement preCondition;
    ViewModelElement runtimeCondition;
    ViewModelElement postCondition;

    public BehaviourTab(ViewModelElement behaviour) {
        this.behaviour = behaviour;
    }

    @Override
    public boolean representsViewModelElement(ViewModelElement element) {
        if (this.behaviour.equals(element) || this.behaviour.getId() == element.getParentId()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ViewModelElement getViewModelElement() {
        return behaviour;
    }

    @Override
    public GuiModificationEvent handleDelete() {
        System.err.println("BehaviourTab: HandleDelete not implemented!");
        return null;
    }

    @Override
    public void save() {
        System.err.println("BehaviourTab: Save not implemented!");
    }
}
