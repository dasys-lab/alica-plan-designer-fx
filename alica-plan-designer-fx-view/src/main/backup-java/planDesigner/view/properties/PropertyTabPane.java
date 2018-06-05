package de.uni_kassel.vs.cn.planDesigner.view.properties;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement;
import javafx.scene.control.TabPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by marci on 27.11.16.
 */
public class PropertyTabPane extends TabPane {
    private static final Logger LOG = LogManager.getLogger(PropertyTabPane.class);

    private AbstractEditorTab<PlanElement> activeEditorTab;

    @SuppressWarnings("unchecked")
    public void init(EditorTabPane editorTabPane) {
        editorTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            activeEditorTab = (AbstractEditorTab<PlanElement>) newValue;
            getTabs().clear();
            if (activeEditorTab == null) {
                return;
            }
            try {
                getTabs().add(new PropertyTab(activeEditorTab, activeEditorTab.getCommandStack()));
                if (newValue instanceof PlanTab && ((PlanTab) newValue).getSelectedPlanElements().get().size() == 1
                        &&  ((PlanTab) newValue).getSelectedPlanElements().get().get(0).getKey() instanceof Plan
                        || newValue instanceof BehaviourTab
                        && ((BehaviourTab) newValue).getSelectedPlanElements().get().get(0).getKey() instanceof Behaviour) {
                    getTabs().add(new VariablesTab(activeEditorTab, activeEditorTab.getCommandStack()));
                }
                getTabs().add(new ParametrisationTab(activeEditorTab, activeEditorTab.getCommandStack()));
                // TODO this seems like bad code
            } catch (NullPointerException e) {
                LOG.error("Error in PropertyTabInitialisation", e);
            }
        });
    }
}
