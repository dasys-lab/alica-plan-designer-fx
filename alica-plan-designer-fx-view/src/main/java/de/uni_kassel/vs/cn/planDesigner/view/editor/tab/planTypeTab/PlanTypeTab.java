package de.uni_kassel.vs.cn.planDesigner.view.editor.tab.planTypeTab;

import de.uni_kassel.vs.cn.planDesigner.controller.PlanTypeWindowController;
import de.uni_kassel.vs.cn.planDesigner.events.GuiEventType;
import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.uni_kassel.vs.cn.planDesigner.view.Types;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.AbstractPlanTab;
import de.uni_kassel.vs.cn.planDesigner.view.model.PlanTypeViewModel;
import de.uni_kassel.vs.cn.planDesigner.view.model.ViewModelElement;
import de.uni_kassel.vs.cn.planDesigner.events.GuiModificationEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class PlanTypeTab extends AbstractPlanTab {

    private PlanTypeWindowController controller;

    private ViewModelElement planType;

    private IGuiModificationHandler guiModificationHandler;

    private PlanTypeViewModel planTypeViewModel;

    public PlanTypeTab(ViewModelElement planType, IGuiModificationHandler guiModificationHandler) {
        super(planType);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("plantypeWindow.fxml"));
        this.planType = planType;
        this.guiModificationHandler = guiModificationHandler;
        try {
            Parent window = fxmlLoader.load();
            controller = fxmlLoader.getController();
            controller.setPlanType(planType);
            controller.setPlanTypeTab(this);
            controller.setGuiModificationHandler(guiModificationHandler);
            setContent(window);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PlanTypeWindowController getController() {
        return controller;
    }

    @Override
    public GuiModificationEvent handleDelete() {
        System.err.println("PlanTypeTab: Not implemented!");
        return null;
    }

    @Override
    public void save() {
        if (isDirty()) {
            GuiModificationEvent event = new GuiModificationEvent(GuiEventType.SAVE_ELEMENT, Types.PLANTYPE, planType.getName());
            event.setElementId(planType.getId());
            guiModificationHandler.handle(event);
            this.setDirty(false);
        }
    }

    public void setPlanTypeViewModel(PlanTypeViewModel planTypeViewModel) {
        this.planTypeViewModel = planTypeViewModel;
        controller.init(planTypeViewModel);
    }
}
