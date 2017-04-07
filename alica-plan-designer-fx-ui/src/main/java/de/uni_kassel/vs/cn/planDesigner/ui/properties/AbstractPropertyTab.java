package de.uni_kassel.vs.cn.planDesigner.ui.properties;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.AbstractPlanElementContainer;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.AbstractEditorTab;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Tab;
import javafx.util.Pair;

/**
 * Created by marci on 24.02.17.
 */
public abstract class AbstractPropertyTab extends Tab {
    private final SimpleObjectProperty<Pair<PlanElement, AbstractPlanElementContainer>> selectedElementContainer;
    protected PlanElement selectedPlanElement;
    protected CommandStack commandStack;

    public AbstractPropertyTab(AbstractEditorTab<PlanElement> activeEditorTab, CommandStack commandStack) {
        this.selectedPlanElement = activeEditorTab.getSelectedPlanElement().getValue().getKey();
        this.selectedElementContainer = activeEditorTab.getSelectedPlanElement();
        this.commandStack = commandStack;
        addListenersForActiveTab(activeEditorTab);
        createTabContent();
    }

    protected void addListenersForActiveTab(AbstractEditorTab<PlanElement> activeEditorTab) {
        activeEditorTab.getSelectedPlanElement().addListener((observable, oldValue, newValue) -> {
            selectedPlanElement = newValue.getKey();
            createTabContent();
        });
    }

    protected abstract void createTabContent();

    public PlanElement getSelectedEditorTabPlanElement() {
        return selectedPlanElement;
    }

    public Pair<PlanElement, AbstractPlanElementContainer> getSelectedElementContainer() {
        return selectedElementContainer.get();
    }

    public SimpleObjectProperty<Pair<PlanElement, AbstractPlanElementContainer>> selectedElementContainerProperty() {
        return selectedElementContainer;
    }
}
