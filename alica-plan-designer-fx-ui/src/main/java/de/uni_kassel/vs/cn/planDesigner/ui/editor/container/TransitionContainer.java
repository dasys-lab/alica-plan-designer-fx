package de.uni_kassel.vs.cn.planDesigner.ui.editor.container;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.alica.Transition;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.Bendpoint;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;

import java.util.ArrayList;
import java.util.List;

import static de.uni_kassel.vs.cn.planDesigner.ui.editor.EditorConstants.PLAN_SHIFTING_PARAMETER;
import static de.uni_kassel.vs.cn.planDesigner.ui.editor.EditorConstants.SECTION_MARGIN;

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

    @Override
    public void draw() {
        getChildren().clear();
        draggableNodes.clear();
        potentialDraggableNodes.clear();
        int fromY = fromState.getPmlUiExtension().getYPos();
        int toX = toState.getPmlUiExtension().getXPos();
        int toY = toState.getPmlUiExtension().getYPos();
        int fromX = fromState.getPmlUiExtension().getXPos();
        int shifting = PLAN_SHIFTING_PARAMETER + SECTION_MARGIN;
        double centerMarginX = fromState.getVisualRepresentation().getLayoutBounds().getWidth()/3;
        double centerMarginY = fromState.getVisualRepresentation().getLayoutBounds().getWidth()/4;

        Polygon polygon = null;
        double _toX = toX + shifting;
        double _toY = toY + shifting;
        double _fromX = fromX + shifting;
        double _fromY = fromY + shifting;

        double vecX = _toX - _fromX;
        double vecY = _toY - _fromY;
        double vecLen = Math.sqrt(vecX*vecX + vecY*vecY);
        _toX = _toX - StateContainer.STATE_RADIUS*1.1*(vecX/vecLen);
        _toY = _toY - StateContainer.STATE_RADIUS*(vecY/vecLen);
        vecX = _toX - _fromX;
        vecY = _toY - _fromY;
        vecLen = Math.sqrt(vecX*vecX + vecY*vecY);

        double triangleSpanVecX = vecY;
        double triangleSpanVecY = -vecX;
        double triangleSpanLen = Math.sqrt(triangleSpanVecY * triangleSpanVecY + triangleSpanVecX * triangleSpanVecX);
        if (getPmlUiExtension().getBendpoints().size() == 0) {
            visualRepresentation = new Line(_fromX,
                    _fromY,
                    _toX,
                    _toY);
            potentialDraggableNodes.add(new Pane());
            polygon = new Polygon(
            _toX - 5*(vecX/vecLen)+5*(triangleSpanVecX/triangleSpanLen), _toY - 5*(vecY/vecLen) + 5* triangleSpanVecY/triangleSpanLen,
                    _toX, _toY,
                    _toX - 5*(vecX/vecLen)-5*(triangleSpanVecX/triangleSpanLen), _toY - 5*(vecY/vecLen) - 5* triangleSpanVecY/triangleSpanLen);
        } else {
            double[] points = new double[getPmlUiExtension().getBendpoints().size() * 2 + 4];
            points[0] = _fromX;
            points[1] = _fromY;

            for (int i = 0, j = 2; i < points.length / 2 - 2; i++, j += 2) {
                Bendpoint currentBendpoint = getPmlUiExtension().getBendpoints().get(i);
                points[j] = currentBendpoint.getXPos() + shifting;
                points[j + 1] = currentBendpoint.getYPos() + shifting;
                BendpointContainer bendpointContainer = new BendpointContainer(currentBendpoint, getPmlUiExtension(), commandStack);
                bendpointContainer.getWrapGroup().setVisible(false);
                draggableNodes.add(bendpointContainer.getWrapGroup());
                _fromX = points[j] + shifting;
                _fromY = points[j+1] + shifting;
                vecX = _toX - _fromX;
                vecY = _toY - _fromY;
                vecLen = Math.sqrt(vecX*vecX + vecY*vecY);
                triangleSpanVecX = vecY;
                triangleSpanVecY = -vecX;
                triangleSpanLen = Math.sqrt(triangleSpanVecY * triangleSpanVecY + triangleSpanVecX * triangleSpanVecX);

            }

            points[points.length - 2] = _toX;
            points[points.length - 1] = _toY;

            visualRepresentation = new Polyline(points);
            visualRepresentation.setFill(null);
            polygon = new Polygon(
                    _toX - 5*(vecX/vecLen)+5*(triangleSpanVecX/triangleSpanLen), _toY - 5*(vecY/vecLen) + 5* triangleSpanVecY/triangleSpanLen,
                    _toX, _toY,
                    _toX - 5*(vecX/vecLen)-5*(triangleSpanVecX/triangleSpanLen), _toY - 5*(vecY/vecLen) - 5* triangleSpanVecY/triangleSpanLen);
            //polygon = new Polygon(toX + shifting + xEnd - 20, toY + shifting + yEnd, toX + shifting + xEnd, toY + shifting + yEnd, toX + shifting + xEnd, toY + shifting + yEnd + 20);
        }

        polygon.setFill(Color.RED);
        polygon.setStroke(Color.RED);
        polygon.setStrokeWidth(4);
        polygon.setVisible(true);
        visualRepresentation.setStrokeWidth(3);
        visualRepresentation.setStroke(Color.RED);
        visualRepresentation.setPickOnBounds(false);
        this.getChildren().add(visualRepresentation);
        this.getChildren().add(polygon);
        this.getChildren().addAll(draggableNodes);
    }

    public void setBendpointContainerVisibility(boolean isVisible) {
        getDraggableNodes().forEach(d -> d.setVisible(isVisible));
    }

    public List<Node> getDraggableNodes() {
        return draggableNodes;
    }
}
