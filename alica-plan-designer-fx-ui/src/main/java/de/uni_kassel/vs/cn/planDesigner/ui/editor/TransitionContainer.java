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
            visualRepresentation = new Line(fromState.getPmlUiExtension().getXPos()+50, fromState.getPmlUiExtension().getYPos()+50,
                    toState.getPmlUiExtension().getXPos()+50, toState.getPmlUiExtension().getYPos()+50);
        } else {
            double[] points = new double[pmlUiExtension.getBendpoints().size()*2+4];
            points[0] = fromState.getPmlUiExtension().getXPos()+50;
            points[1] = fromState.getPmlUiExtension().getYPos()+50;
            for (int i = 0, j = 2; i < points.length/2-2; i++,j+=2) {
                // TODO remove the magic numbers
                points[j] = pmlUiExtension.getBendpoints().get(i).getXPos()-180+50;
                points[j+1] = pmlUiExtension.getBendpoints().get(i).getYPos()-140+50;
            }
            points[points.length-2] = toState.getPmlUiExtension().getXPos()+50;
            points[points.length-1] = toState.getPmlUiExtension().getYPos()+50;

            visualRepresentation = new Polyline(points);
            visualRepresentation.setFill(Color.TRANSPARENT);
            visualRepresentation.setStrokeWidth(1);
        }
        visualRepresentation.setStroke(Color.RED);
        this.getChildren().add(visualRepresentation);

    }
}
