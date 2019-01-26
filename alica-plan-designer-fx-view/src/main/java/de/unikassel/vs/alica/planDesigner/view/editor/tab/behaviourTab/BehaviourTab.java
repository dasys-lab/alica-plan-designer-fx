package de.unikassel.vs.alica.planDesigner.view.editor.tab.behaviourTab;

import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.AbstractPlanTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.EditorTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.EditorTabPane;
import de.unikassel.vs.alica.planDesigner.view.model.BehaviourViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.SerializableViewModel;

public class BehaviourTab extends AbstractPlanTab {

    public BehaviourTab(SerializableViewModel serializableViewModel, EditorTabPane editorTabPane) {
        super(serializableViewModel, editorTabPane.getGuiModificationHandler());
    }

    public GuiModificationEvent handleDelete() {
        System.err.println("BehaviourTab: HandleDelete is a no-op for behaviour tabs!");
        return null;
    }

    public void save() {
        save(Types.BEHAVIOUR);
    }

}
