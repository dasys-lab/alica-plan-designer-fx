package de.uni_kassel.vs.cn.planDesigner.view.editor.container;

import de.uni_kassel.vs.cn.planDesigner.view.model.StateViewModel;
import javafx.scene.paint.Color;

public class SuccessStateContainer extends TerminalStateContainer {

    public SuccessStateContainer(/*PmlUiExtension pmlUiExtension,*/ StateViewModel state) {
        super(state);
    }

    @Override
    public Color getVisualisationColor() {
        return Color.GREEN;
    }
}
