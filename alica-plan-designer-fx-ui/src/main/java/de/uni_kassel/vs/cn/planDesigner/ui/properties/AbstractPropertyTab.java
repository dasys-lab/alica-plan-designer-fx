package de.uni_kassel.vs.cn.planDesigner.ui.properties;

import de.uni_kassel.vs.cn.planDesigner.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
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
    protected final AbstractEditorTab<PlanElement> activeEditorTab;
    protected PlanElement selectedPlanElement;
    protected CommandStack commandStack;

    public AbstractPropertyTab(AbstractEditorTab<PlanElement> activeEditorTab, CommandStack commandStack) {
        this.activeEditorTab = activeEditorTab;
        if (activeEditorTab.getSelectedPlanElement().get().size() == 1) {
            this.selectedPlanElement = activeEditorTab.getSelectedPlanElement().get().get(0).getKey();
            this.selectedElementContainer = new SimpleObjectProperty<>(activeEditorTab.getSelectedPlanElement().get().get(0));
        } else {
            this.selectedPlanElement = null;
            this.selectedElementContainer = null;
        }
        this.commandStack = commandStack;
        addListenersForActiveTab(activeEditorTab);
        createTabContent();
    }

    protected void addListenersForActiveTab(AbstractEditorTab<PlanElement> activeEditorTab) {
        activeEditorTab.getSelectedPlanElement().addListener((observable, oldValue, newValue) -> {
            if (newValue.size() == 1) {
                selectedPlanElement = newValue.get(0).getKey();
            }
            createTabContent();
            if (selectedPlanElement instanceof Plan && selectedPlanElement != activeEditorTab.getEditable()) {
                setDisable(true);
                getContent().setDisable(true);
            } else {
                setDisable(false);
            }
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

    public CommandStack getCommandStack() {
        return commandStack;
    }
}
