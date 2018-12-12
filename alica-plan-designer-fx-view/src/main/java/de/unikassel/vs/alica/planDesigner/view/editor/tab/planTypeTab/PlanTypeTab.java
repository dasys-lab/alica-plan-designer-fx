package de.unikassel.vs.alica.planDesigner.view.editor.tab.planTypeTab;

import de.unikassel.vs.alica.planDesigner.controller.PlanTypeWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.AbstractPlanTab;
import de.unikassel.vs.alica.planDesigner.view.model.PlanTypeViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class PlanTypeTab extends AbstractPlanTab {

    private PlanTypeWindowController planTypeWindowController;

    public PlanTypeTab(ViewModelElement planType, IGuiModificationHandler guiModificationHandler) {
        super(planType, guiModificationHandler);
        setText(I18NRepo.getInstance().getString("label.caption.plantype") + ": " + planType.getName());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("planTypeWindow.fxml"));
        try {
            Parent window = fxmlLoader.load();
            planTypeWindowController = fxmlLoader.getController();
            planTypeWindowController.setPlanTypeTab(this);
            planTypeWindowController.setGuiModificationHandler(guiModificationHandler);
            setContent(window);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PlanTypeWindowController getPlanTypeWindowController() {
        return planTypeWindowController;
    }

    @Override
    public GuiModificationEvent handleDelete() {
        System.err.println("PlanTypeTab: Not implemented!");
        return null;
    }

    @Override
    public void save() {
        if (isDirty()) {
            GuiModificationEvent event = new GuiModificationEvent(GuiEventType.SAVE_ELEMENT, Types.PLANTYPE, viewModelElement.getName());
            event.setElementId(viewModelElement.getId());
            guiModificationHandler.handle(event);
        }
    }

    public void setPlanTypeViewModel(PlanTypeViewModel planTypeViewModel) {
        planTypeWindowController.init(planTypeViewModel);
    }

    public void updateText(String newName) {
        this.setText(I18NRepo.getInstance().getString("label.caption.plantype") + ": " + newName);
        setDirty(true);
    }
}
