package de.uni_kassel.vs.cn.planDesigner.ui.editor;

import de.uni_kassel.vs.cn.planDesigner.alica.Transition;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;

/**
 * Created by marci on 03.12.16.
 */
public class TransitionContainer extends PlanElementContainer<Transition> {
    private StateContainer fromState;
    private StateContainer toState;

    public TransitionContainer(Transition transition, PmlUiExtension pmlUiExtension, StateContainer fromState, StateContainer toState) {
        super(transition, pmlUiExtension);
        this.fromState = fromState;
        this.toState = toState;
        if (pmlUiExtension.getBendpoints().size() == 0) {
            visualRepresentation = new Line(fromState.getPmlUiExtension().getXPos() + EditorConstants.PLAN_SHIFTING_PARAMETER,
                    fromState.getPmlUiExtension().getYPos() + EditorConstants.PLAN_SHIFTING_PARAMETER,
                    toState.getPmlUiExtension().getXPos() + EditorConstants.PLAN_SHIFTING_PARAMETER,
                    toState.getPmlUiExtension().getYPos() + EditorConstants.PLAN_SHIFTING_PARAMETER);
        } else {
            double[] points = new double[pmlUiExtension.getBendpoints().size()*2+4];
            points[0] = fromState.getPmlUiExtension().getXPos() + EditorConstants.PLAN_SHIFTING_PARAMETER;
            points[1] = fromState.getPmlUiExtension().getYPos() + EditorConstants.PLAN_SHIFTING_PARAMETER;
            for (int i = 0, j = 2; i < points.length/2-2; i++,j+=2) {
                points[j] = pmlUiExtension.getBendpoints().get(i).getXPos() + EditorConstants.PLAN_SHIFTING_PARAMETER;
                points[j+1] = pmlUiExtension.getBendpoints().get(i).getYPos() + EditorConstants.PLAN_SHIFTING_PARAMETER;
            }
            points[points.length-2] = toState.getPmlUiExtension().getXPos() + EditorConstants.PLAN_SHIFTING_PARAMETER;
            points[points.length-1] = toState.getPmlUiExtension().getYPos() + EditorConstants.PLAN_SHIFTING_PARAMETER;

            visualRepresentation = new Polyline(points);
            visualRepresentation.setFill(Color.TRANSPARENT);
            visualRepresentation.setStrokeWidth(1);
        }
        visualRepresentation.setStroke(Color.RED);
        this.getChildren().add(visualRepresentation);

    }
}
