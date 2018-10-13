package de.uni_kassel.vs.cn.planDesigner.view.editor.container;

import de.uni_kassel.vs.cn.planDesigner.view.Types;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.planTab.PlanTab;
import de.uni_kassel.vs.cn.planDesigner.view.model.StateViewModel;
import javafx.scene.paint.Color;

public class FailureStateContainer extends TerminalStateContainer {
    public FailureStateContainer(/*PmlUiExtension pmlUiExtension,*/ StateViewModel state, PlanTab planTab) {
        super(state, planTab);
    }

    @Override
    public Color getVisualisationColor() {
        return Color.RED;
    }

}
