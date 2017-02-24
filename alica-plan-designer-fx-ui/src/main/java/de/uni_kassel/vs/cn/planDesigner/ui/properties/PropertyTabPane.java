package de.uni_kassel.vs.cn.planDesigner.ui.properties;

import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.alica.Transition;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.EditorTab;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.EditorTabPane;
import javafx.scene.control.Tab;
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
                getTabs().add(new PropertyTab(activeEditorTab));
                if (newValue instanceof Plan || newValue instanceof Transition) {
                    getTabs().add(new Tab());
                }
            } catch (NullPointerException ignored) {
            }
        });
    }
}
