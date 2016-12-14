package de.uni_kassel.vs.cn.planDesigner.ui.editor;

import de.uni_kassel.vs.cn.planDesigner.alica.Transition;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Created by marci on 03.12.16.
 */
public class TransitionContainer {
    private Line line;
    private Transition transition;
    private StateContainer fromState;
    private StateContainer toState;
    private PmlUiExtension pmlUiExtension;

    public TransitionContainer(Transition transition, PmlUiExtension pmlUiExtension) {
        this.transition = transition;
        this.pmlUiExtension = pmlUiExtension;
    }

    public void initLine() {
        if (fromState == null || toState == null) {
            throw new IllegalStateException("Line cannot be initialized, some state connections are missing!");
        }
        line = new Line(fromState.getLayoutX(), fromState.getLayoutY(), toState.getLayoutX(), toState.getLayoutY());
        line.setFill(Color.GREEN);
        line.setStroke(Color.BROWN);
    }

    public Line getLine() {
        return line;
    }

    public void setToState(StateContainer toState) {
        this.toState = toState;
    }

    public void setFromState(StateContainer fromState) {
        this.fromState = fromState;
    }
}
