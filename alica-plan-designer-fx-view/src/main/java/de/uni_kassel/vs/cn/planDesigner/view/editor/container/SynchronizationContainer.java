package de.uni_kassel.vs.cn.planDesigner.view.editor.container;

import de.uni_kassel.vs.cn.planDesigner.view.model.SynchronizationViewModel;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Point2D;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SynchronizationContainer extends AbstractPlanElementContainer implements Observable {
    private boolean dragged;
    private List<InvalidationListener> invalidationListeners;
    private List<TransitionContainer> transitionContainers;
    private Map<TransitionContainer, Shape> transitionToLineMap;
    private SynchronizationViewModel synchronisation;

    /**
     * @param containedElement
     */
    public SynchronizationContainer(SynchronizationViewModel containedElement, List<TransitionContainer> transitionContainers/*, PmlUiExtension pmlUiExtension*/) {
        super(containedElement, null);
        invalidationListeners = new ArrayList<>();
        transitionToLineMap = new HashMap<>();
        this.synchronisation = containedElement;
        this.transitionContainers = new ArrayList<>(transitionContainers);
        for (TransitionContainer transitionContainer : transitionContainers) {
            transitionContainer.addListener(observable -> setupContainer());
        }
        makeDraggable(this);

        setupContainer();
    }

    @Override
    public void setupContainer() {
        getChildren().clear();
//        setLayoutX(getPmlUiExtension().getXPos());
//        setLayoutY(getPmlUiExtension().getYPos());
        visualRepresentation = new Circle(StateContainer.STATE_RADIUS, getVisualisationColor());
        setEffectToStandard();
        ((Circle) visualRepresentation).setFill(new ImagePattern(new Image(getClass().getClassLoader()
                .getResourceAsStream("images/synchronization36x24.png"))));
        Text e = new Text(synchronisation.getName());
        getChildren().add(e);
        e.setLayoutX(e.getLayoutX() - e.getLayoutBounds().getWidth() / 2);
        e.setLayoutY(e.getLayoutY() - StateContainer.STATE_RADIUS);
        if (transitionContainers != null && transitionContainers.size() > 0) {
            transitionContainers.forEach(transitionContainer -> {
                if (transitionContainer.getVisualRepresentation() instanceof Line) {
                    Line visualRepresentation = (Line) transitionContainer.getVisualRepresentation();
                    double middleXT = visualRepresentation.getStartX() + (visualRepresentation.getEndX() - visualRepresentation.getStartX()) / 2;
                    double middleYT = visualRepresentation.getStartY() + (visualRepresentation.getEndY() - visualRepresentation.getStartY()) / 2;
                    Point2D middleP = transitionContainer.localToParent(middleXT, middleYT);
                    Point2D middleS = this.parentToLocal(middleP);
                    Line line = new Line(visualRepresentation.getLayoutX(),
                            visualRepresentation.getLayoutY(),
                            middleS.getX(),
                            middleS.getY());
                    line.setFill(Color.VIOLET);
                    getChildren().add(line);
                    transitionToLineMap.put(transitionContainer, line);
                } else if (transitionContainer.getVisualRepresentation() instanceof Polyline) {
                    Polyline visualRepresentation = (Polyline) transitionContainer.getVisualRepresentation();
                    double middleXT;
                    double middleYT;
                    if (((visualRepresentation.getPoints().size() / 2) / 2) % 2 == 1) {
                        int i = ((visualRepresentation.getPoints().size() / 2) / 2) * 2;
                        middleXT = visualRepresentation.getPoints().get(i);
                        middleYT = visualRepresentation.getPoints().get(i + 1);

                    } else {
                        int i = (visualRepresentation.getPoints().size() / 2);
                        middleXT =
                                visualRepresentation.getPoints().get(i - 2) + visualRepresentation.getPoints().get(i) - visualRepresentation.getPoints().get(i - 2);
                        middleYT =
                                visualRepresentation.getPoints().get(i - 1) + visualRepresentation.getPoints().get(i + 1) - visualRepresentation.getPoints().get(i - 1);
                    }
                    Point2D middleP = transitionContainer.localToParent(middleXT, middleYT);
                    Point2D middleS = this.parentToLocal(middleP);
                    Line line = new Line(visualRepresentation.getLayoutX(),
                            visualRepresentation.getLayoutY(),
                            middleS.getX(),
                            middleS.getY());
                    line.setFill(Color.VIOLET);
                    getChildren().add(line);
                    transitionToLineMap.put(transitionContainer, line);
                }
            });

        }
        getChildren().add(visualRepresentation);
    }

    @Override
    public void setEffectToStandard() {
        visualRepresentation.setEffect(new DropShadow(BlurType.THREE_PASS_BOX,
                new Color(0, 0, 0, 0.8), 10, 0, 0, 0));
    }

    @Override
    public Color getVisualisationColor() {
        return Color.TRANSPARENT;
    }

    @Override
    public void redrawElement() {
        //((PlanEditorGroup) getParent()).setupPlanVisualisation();
        setupContainer();
        invalidationListeners.forEach(listener -> listener.invalidated(this));
    }

    @Override
    public void setDragged(boolean dragged) {
        this.dragged = dragged;
    }

    @Override
    public boolean wasDragged() {
        return dragged;
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
