package de.uni_kassel.vs.cn.planDesigner.view.editor.tab.planTypeTab;

import de.uni_kassel.vs.cn.planDesigner.controller.PlanTypeWindowController;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.AbstractPlanTab;
import de.uni_kassel.vs.cn.planDesigner.common.ViewModelElement;
import de.uni_kassel.vs.cn.planDesigner.events.GuiModificationEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class PlanTypeTab extends AbstractPlanTab {

    private PlanTypeWindowController controller;

    public PlanTypeTab(ViewModelElement planType) {
        super(planType);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("plantypeWindow.fxml"));
        try {
            Parent window = fxmlLoader.load();
            controller = fxmlLoader.getController();
            controller.setPlanType(planType);
            controller.setPlanTypeTab(this);
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
}
