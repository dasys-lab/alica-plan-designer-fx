package de.uni_kassel.vs.cn.planDesigner.ui.editor.container;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.alica.Transition;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.Bendpoint;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by marci on 03.12.16.
 */
public class TransitionContainer extends AbstractPlanElementContainer<Transition> implements Observable {
    private StateContainer fromState;
    private StateContainer toState;
    private List<Node> draggableNodes;
    private List<Node> potentialDraggableNodes;
    private List<InvalidationListener> invalidationListeners = new ArrayList<>();

    public TransitionContainer(Transition transition, PmlUiExtension pmlUiExtension, CommandStack commandStack,
                               StateContainer fromState, StateContainer toState) {
        super(transition, pmlUiExtension, commandStack);
        this.fromState = fromState;
        this.toState = toState;
        InvalidationListener invalidationListener = observable -> setupContainer();
        fromState.addListener(invalidationListener);
        toState.addListener(invalidationListener);
        draggableNodes = new ArrayList<>();
        potentialDraggableNodes = new ArrayList<>();
        setupContainer();
    }

    @Override
    public void setupContainer() {
        //setBackground(new Background(new BackgroundFill(Color.GREEN,null,null)));
        getChildren().clear();
        draggableNodes.clear();
        potentialDraggableNodes.clear();

        Polygon polygon;
        double _toX = toState.getLayoutX() + toState.getTranslateX();
        double _toY = toState.getLayoutY() + toState.getTranslateY();
        double _fromX = fromState.getLayoutX() + fromState.getTranslateX();
        double _fromY = fromState.getLayoutY() + fromState.getTranslateY();

        double vecX = _toX - _fromX;
        double vecY = _toY - _fromY;
        double vecLen = Math.sqrt(vecX*vecX + vecY*vecY);
        _toX = _toX - StateContainer.STATE_RADIUS*(vecX/vecLen);
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
                points[j] = currentBendpoint.getXPos();
                points[j + 1] = currentBendpoint.getYPos();
                BendpointContainer bendpointContainer = new BendpointContainer(currentBendpoint,
                        getPmlUiExtension(), commandStack, fromState.getContainedElement().getInPlan());
                bendpointContainer.setVisible(false);
                draggableNodes.add(bendpointContainer);
                _fromX = points[j];
                _fromY = points[j+1];
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
            ((Shape)visualRepresentation).setFill(null);
            polygon = new Polygon(
                    _toX - 5*(vecX/vecLen)+5*(triangleSpanVecX/triangleSpanLen), _toY - 5*(vecY/vecLen) + 5* triangleSpanVecY/triangleSpanLen,
                    _toX, _toY,
                    _toX - 5*(vecX/vecLen)-5*(triangleSpanVecX/triangleSpanLen), _toY - 5*(vecY/vecLen) - 5* triangleSpanVecY/triangleSpanLen);
        }

        polygon.setFill(getVisualisationColor());
        polygon.setStroke(getVisualisationColor());
        polygon.setStrokeWidth(4);
        polygon.setVisible(true);
        ((Shape)visualRepresentation).setStrokeWidth(3);
        ((Shape)visualRepresentation).setStroke(getVisualisationColor());
        visualRepresentation.setPickOnBounds(false);
        this.getChildren().add(visualRepresentation);
        this.getChildren().add(polygon);
        this.getChildren().addAll(draggableNodes);
        invalidationListeners.forEach(e-> e.invalidated(this));
    }

    @Override
    public Color getVisualisationColor() {
        return Color.RED;
    }

    public void setBendpointContainerVisibility(boolean isVisible) {
        getDraggableNodes().forEach(d -> d.setVisible(isVisible));
    }

    public List<Node> getDraggableNodes() {
        return draggableNodes;
    }

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListeners.add(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListeners.remove(listener);
    }
}
