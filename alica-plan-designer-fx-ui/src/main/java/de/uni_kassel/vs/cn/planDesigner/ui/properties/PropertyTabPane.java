package de.uni_kassel.vs.cn.planDesigner.ui.properties;

import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.EditorTab;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.EditorTabPane;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.PlanTab;
import javafx.scene.control.TabPane;

/**
 * Created by marci on 27.11.16.
 */
public class PropertyTabPane extends TabPane {

    private EditorTab<PlanElement> activeEditorTab;

    public void init(EditorTabPane editorTabPane) {
        editorTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            activeEditorTab = (EditorTab<PlanElement>) newValue;
            getTabs().clear();
            try {
                getTabs().add(new PropertyTab(activeEditorTab, activeEditorTab.getCommandStack()));
                if (newValue instanceof PlanTab &&
                        ((PlanTab) newValue).getSelectedPlanElement().get().getKey() instanceof Plan) {
                    getTabs().add(new VariablesTab(activeEditorTab, activeEditorTab.getCommandStack()));
                }
                // TODO this seems like bad code
            } catch (NullPointerException ignored) {
            }
        });
    }
}
