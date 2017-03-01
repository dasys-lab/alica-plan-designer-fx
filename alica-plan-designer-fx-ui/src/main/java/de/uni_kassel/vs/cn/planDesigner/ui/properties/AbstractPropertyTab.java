package de.uni_kassel.vs.cn.planDesigner.ui.properties;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.PlanElementContainer;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.EditorTab;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Tab;
import javafx.util.Pair;

/**
 * Created by marci on 24.02.17.
 */
public abstract class AbstractPropertyTab extends Tab {
    private final SimpleObjectProperty<Pair<PlanElement, PlanElementContainer>> selectedElementContainer;
    protected PlanElement selectedPlanElement;
    protected CommandStack commandStack;

    public AbstractPropertyTab(EditorTab<PlanElement> activeEditorTab, CommandStack commandStack) {
        this.selectedPlanElement = activeEditorTab.getSelectedPlanElement().getValue().getKey();
        this.selectedElementContainer = activeEditorTab.getSelectedPlanElement();
        this.commandStack = commandStack;
        addListenersForActiveTab(activeEditorTab);
        createTabContent();
    }

    protected void addListenersForActiveTab(EditorTab<PlanElement> activeEditorTab) {
        activeEditorTab.getSelectedPlanElement().addListener((observable, oldValue, newValue) -> {
            selectedPlanElement = newValue.getKey();
            createTabContent();
        });
    }

    protected abstract void createTabContent();

    public Pair<PlanElement, PlanElementContainer> getSelectedElementContainer() {
        return selectedElementContainer.get();
    }

    public SimpleObjectProperty<Pair<PlanElement, PlanElementContainer>> selectedElementContainerProperty() {
        return selectedElementContainer;
    }
}
