package de.uni_kassel.vs.cn.planDesigner.view.editor.tab.planTypeTab;

import de.uni_kassel.vs.cn.planDesigner.controller.PlanTypeWindowController;
import de.uni_kassel.vs.cn.planDesigner.events.GuiEventType;
import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.Types;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.AbstractPlanTab;
import de.uni_kassel.vs.cn.planDesigner.view.model.PlanTypeViewModel;
import de.uni_kassel.vs.cn.planDesigner.view.model.ViewModelElement;
import de.uni_kassel.vs.cn.planDesigner.events.GuiModificationEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class PlanTypeTab extends AbstractPlanTab {

    private PlanTypeWindowController planTypeWindowController;

    private IGuiModificationHandler guiModificationHandler;

    public PlanTypeTab(ViewModelElement planType, IGuiModificationHandler guiModificationHandler) {
        super(planType);
        setText(I18NRepo.getInstance().getString("label.caption.plantype") + ": " + planType.getName());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("planTypeWindow.fxml"));
        this.guiModificationHandler = guiModificationHandler;
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
            GuiModificationEvent event = new GuiModificationEvent(GuiEventType.SAVE_ELEMENT, Types.PLANTYPE, presentedViewModelElement.getName());
            event.setElementId(presentedViewModelElement.getId());
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
