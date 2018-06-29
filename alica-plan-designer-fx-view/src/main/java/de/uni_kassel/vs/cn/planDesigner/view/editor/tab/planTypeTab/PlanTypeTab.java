package de.uni_kassel.vs.cn.planDesigner.view.editor.tab.planTypeTab;

import de.uni_kassel.vs.cn.planDesigner.controller.PlanTypeWindowController;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.AbstractPlanTab;
import de.uni_kassel.vs.cn.planDesigner.view.filebrowser.TreeViewModelElement;
import javafx.application.Platform;
import de.uni_kassel.vs.cn.planDesigner.events.GuiModificationEvent;
import de.uni_kassel.vs.cn.planDesigner.view.repo.ViewModelElement;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.nio.file.Path;

public class PlanTypeTab extends AbstractPlanTab {

    private PlanTypeWindowController controller;

    public PlanTypeTab(TreeViewModelElement planType) {
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

    @Override
    public GuiModificationEvent handleDelete() {
        System.err.println("PlanTypeTab: Not implemented!");
        return null;
    }
}
