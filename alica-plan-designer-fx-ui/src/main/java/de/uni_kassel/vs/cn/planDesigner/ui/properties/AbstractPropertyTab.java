package de.uni_kassel.vs.cn.planDesigner.ui.properties;

import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.EditorTab;
import javafx.scene.control.Tab;

/**
 * Created by marci on 24.02.17.
 */
public abstract class AbstractPropertyTab extends Tab {
    protected PlanElement selectedPlanElement;

    public AbstractPropertyTab(EditorTab<PlanElement> activeEditorTab) {
        this.selectedPlanElement = activeEditorTab.getSelectedPlanElement().getValue().getKey();
        activeEditorTab.getSelectedPlanElement().addListener((observable, oldValue, newValue) -> {
            selectedPlanElement = newValue.getKey();
            createTabContent();
        });
        createTabContent();
    }

    protected abstract void createTabContent();
}
