package de.uni_kassel.vs.cn.planDesigner.ui.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.PlanTab;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class TabChangeListener implements ChangeListener<Tab> {

    private TabPane workbench;

    public TabChangeListener(TabPane workbench) {
        this.workbench = workbench;
    }
    @Override
    public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
        if (newValue != null) {
            ((PlanTab) newValue).getPlanEditorGroup().setupPlanVisualisation();
            workbench.getSelectionModel().selectedItemProperty().removeListener(this);
        }
    }
}
