package de.uni_kassel.vs.cn.planDesigner.ui.properties;

import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.EditorTab;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.EditorTabPane;
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
            getTabs().add(new PropertyTab(activeEditorTab));
        });
    }
}
