package de.uni_kassel.vs.cn.planDesigner.ui.editor.container;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.alica.Transition;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.Bendpoint;
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
        int fromY = fromState.getPmlUiExtension().getYPos();
        int toX = toState.getPmlUiExtension().getXPos();
        int toY = toState.getPmlUiExtension().getYPos();
        int fromX = fromState.getPmlUiExtension().getXPos();
        int shifting = EditorConstants.PLAN_SHIFTING_PARAMETER;
        if (getPmlUiExtension().getBendpoints().size() == 0) {
            visualRepresentation = new Line(fromX + shifting,
                    fromY + shifting,
                    toX + shifting,
                    toY + shifting);
            potentialDraggableNodes.add(new Pane());
        } else {
            double[] points = new double[getPmlUiExtension().getBendpoints().size()*2+4];
            points[0] = fromX + shifting;
            points[1] = fromY + shifting;

            for (int i = 0, j = 2; i < points.length/2-2; i++,j+=2) {
                Bendpoint currentBendpoint = getPmlUiExtension().getBendpoints().get(i);
                points[j] = currentBendpoint.getXPos() + shifting;
                points[j+1] = currentBendpoint.getYPos() + shifting;
                BendpointContainer bendpointContainer = new BendpointContainer(currentBendpoint, getPmlUiExtension(), commandStack);
                bendpointContainer.getWrapGroup().setVisible(false);
                draggableNodes.add(bendpointContainer.getWrapGroup());
            }
            points[points.length-2] = toX + shifting;
            points[points.length-1] = toY + shifting;

            visualRepresentation = new Polyline(points);
            visualRepresentation.setFill(null);
        }
        visualRepresentation.setStrokeWidth(3);
        visualRepresentation.setStroke(Color.RED);
        visualRepresentation.setPickOnBounds(false);
        this.getChildren().add(visualRepresentation);
        this.getChildren().addAll(draggableNodes);
    }

    public void setBendpointContainerVisibility(boolean isVisible) {
        getDraggableNodes().forEach(d -> d.setVisible(isVisible));
    }

    public List<Node> getDraggableNodes() {
        return draggableNodes;
    }
}
