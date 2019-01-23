package de.unikassel.vs.alica.planDesigner.view.editor.tab.behaviourTab;

import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.AbstractPlanTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.EditorTab;
import de.unikassel.vs.alica.planDesigner.view.model.BehaviourViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.SerializableViewModel;

public class BehaviourTab extends AbstractPlanTab {

    BehaviourViewModel behaviourViewModel;
    I18NRepo i18NRepo;

    public BehaviourTab(SerializableViewModel serializableViewModel, IGuiModificationHandler guiModificationHandler) {
        super(serializableViewModel, guiModificationHandler);
        setText(I18NRepo.getInstance().getString("label.caption.behaviour") + ": " + serializableViewModel.getName());
        i18NRepo = I18NRepo.getInstance();
        this.behaviourViewModel = (BehaviourViewModel) serializableViewModel;
    }

    public GuiModificationEvent handleDelete() {
        System.err.println("BehaviourTab: HandleDelete is a no-op for behaviour tabs!");
        return null;
    }

    public void save() {
        save(Types.BEHAVIOUR);
    }

}
