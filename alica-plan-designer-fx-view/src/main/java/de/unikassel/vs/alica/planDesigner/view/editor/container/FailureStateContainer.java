package de.unikassel.vs.alica.planDesigner.view.editor.container;

import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.model.StateViewModel;
import javafx.scene.paint.Color;

public class FailureStateContainer extends StateContainer {
    public FailureStateContainer(StateViewModel state, PlanTab planTab) {
        super(state, planTab);
    }

    @Override
    public Color getVisualisationColor() {
        return Color.RED;
    }

}
