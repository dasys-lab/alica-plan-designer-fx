package de.uni_kassel.vs.cn.planDesigner.ui.editor.container;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.alica.Transition;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.EditorConstants;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marci on 03.12.16.
 */
public class TransitionContainer extends PlanElementContainer<Transition> {
    private StateContainer fromState;
    private StateContainer toState;
    private List<Node> draggableNodes;
    private List<Node> potentialDraggableNodes;

    public TransitionContainer(Transition transition, PmlUiExtension pmlUiExtension, CommandStack commandStack,
                               StateContainer fromState, StateContainer toState) {
        super(transition, pmlUiExtension, commandStack);
        this.fromState = fromState;
        this.toState = toState;
        draggableNodes = new ArrayList<>();
        potentialDraggableNodes = new ArrayList<>();
        draw();

    }

    public void draw() {
        getChildren().clear();
        draggableNodes.clear();
        potentialDraggableNodes.clear();
        if (getPmlUiExtension().getBendpoints().size() == 0) {
            visualRepresentation = new Line(fromState.getPmlUiExtension().getXPos() + EditorConstants.PLAN_SHIFTING_PARAMETER,
                    fromState.getPmlUiExtension().getYPos() + EditorConstants.PLAN_SHIFTING_PARAMETER,
                    toState.getPmlUiExtension().getXPos() + EditorConstants.PLAN_SHIFTING_PARAMETER,
                    toState.getPmlUiExtension().getYPos() + EditorConstants.PLAN_SHIFTING_PARAMETER);
            potentialDraggableNodes.add(new Pane());
        } else {
            double[] points = new double[getPmlUiExtension().getBendpoints().size()*2+4];
            points[0] = fromState.getPmlUiExtension().getXPos() + EditorConstants.PLAN_SHIFTING_PARAMETER;
            points[1] = fromState.getPmlUiExtension().getYPos() + EditorConstants.PLAN_SHIFTING_PARAMETER;
            for (int i = 0, j = 2; i < points.length/2-2; i++,j+=2) {
                points[j] = getPmlUiExtension().getBendpoints().get(i).getXPos() + EditorConstants.PLAN_SHIFTING_PARAMETER;
                points[j+1] = getPmlUiExtension().getBendpoints().get(i).getYPos() + EditorConstants.PLAN_SHIFTING_PARAMETER;
                BendpointContainer bendpointContainer = new BendpointContainer(getPmlUiExtension().getBendpoints().get(i),
                        getPmlUiExtension(), commandStack);
                bendpointContainer.getWrapGroup().setVisible(false);
                draggableNodes.add(bendpointContainer.getWrapGroup());
            }
            points[points.length-2] = toState.getPmlUiExtension().getXPos() + EditorConstants.PLAN_SHIFTING_PARAMETER;
            points[points.length-1] = toState.getPmlUiExtension().getYPos() + EditorConstants.PLAN_SHIFTING_PARAMETER;

            visualRepresentation = new Polyline(points);
            visualRepresentation.setFill(null);
        }
        visualRepresentation.setStrokeWidth(3);
        visualRepresentation.setStroke(Color.RED);
        visualRepresentation.setPickOnBounds(false);
        this.getChildren().add(visualRepresentation);
        this.getChildren().addAll(draggableNodes);
    }

    public List<Node> getDraggableNodes() {
        return draggableNodes;
    }
}
