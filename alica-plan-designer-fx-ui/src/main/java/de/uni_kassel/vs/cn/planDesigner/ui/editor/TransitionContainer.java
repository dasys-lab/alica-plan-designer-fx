package de.uni_kassel.vs.cn.planDesigner.ui.editor;

import de.uni_kassel.vs.cn.planDesigner.alica.Transition;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

/**
 * Created by marci on 03.12.16.
 */
public class TransitionContainer extends Pane {
    private Line line;
    private Transition transition;
    private StateContainer fromState;
    private StateContainer toState;
    private PmlUiExtension pmlUiExtension;

    public TransitionContainer(Transition transition, PmlUiExtension pmlUiExtension, StateContainer fromState, StateContainer toState) {
        this.transition = transition;
        this.pmlUiExtension = pmlUiExtension;
        this.fromState = fromState;
        this.toState = toState;
        setBackground(Background.EMPTY);
        line = new Line(fromState.getVisualRepresentation().getLayoutX(), fromState.getVisualRepresentation().getLayoutY(),
                toState.getVisualRepresentation().getLayoutX(), toState.getVisualRepresentation().getLayoutY());
        line.setFill(Color.GREEN);
        line.setStroke(Color.RED);
        this.getChildren().add(line);

    }

    public Line getLine() {
        return line;
    }
}
