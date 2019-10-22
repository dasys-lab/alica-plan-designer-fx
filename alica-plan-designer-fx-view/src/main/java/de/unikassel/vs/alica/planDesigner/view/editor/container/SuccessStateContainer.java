package de.unikassel.vs.alica.planDesigner.view.editor.container;

import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.model.StateViewModel;
import javafx.scene.paint.Color;

public class SuccessStateContainer extends StateContainer {

    public SuccessStateContainer(StateViewModel state, PlanTab planTab) {
        super(state, planTab);
        this.setId("SuccessStateContainer");
    }

    @Override
    public Color getVisualisationColor() {
        return Color.GREEN;
    }

}
