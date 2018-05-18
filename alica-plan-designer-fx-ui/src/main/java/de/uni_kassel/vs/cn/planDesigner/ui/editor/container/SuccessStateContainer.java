package de.uni_kassel.vs.cn.planDesigner.ui.editor.container;

import de.uni_kassel.vs.cn.planDesigner.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.alica.State;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import javafx.scene.paint.Color;

/**
 * Created by marci on 01.02.17.
 */
public class SuccessStateContainer extends TerminalStateContainer {
    public SuccessStateContainer(PmlUiExtension pmlUiExtension, State state, CommandStack commandStack) {
        super(pmlUiExtension, state, commandStack);
    }

    @Override
    public Color getVisualisationColor() {
        return Color.GREEN;
    }
}
