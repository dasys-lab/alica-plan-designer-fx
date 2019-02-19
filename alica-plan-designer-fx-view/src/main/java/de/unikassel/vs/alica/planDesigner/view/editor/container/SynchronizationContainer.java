package de.unikassel.vs.alica.planDesigner.view.editor.container;

import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaIcon;
import de.unikassel.vs.alica.planDesigner.view.model.SynchronizationViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.TransitionViewModel;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class SynchronizationContainer extends AbstractPlanElementContainer implements Observable {
    private boolean dragged;
    private List<InvalidationListener> invalidationListeners;
    private List<TransitionContainer> transitionContainers;
    private Map<TransitionContainer, Shape> transitionToLineMap;
    private SynchronizationViewModel synchronisation;

    /**
     * @param containedElement
     */
    public SynchronizationContainer(SynchronizationViewModel containedElement, List<TransitionContainer> transitionContainers, PlanTab planTab) {
        super(containedElement, null, planTab);

        invalidationListeners = new ArrayList<>();
        transitionToLineMap = new HashMap<>();
        this.synchronisation = containedElement;
        this.transitionContainers = new ArrayList<>(transitionContainers);
        for (TransitionContainer transitionContainer : transitionContainers) {
            transitionContainer.addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    SynchronizationContainer.this.setupContainer();
                }
            });
        }

        createSyncTransitionToSynchronisationListeners(this,synchronisation);

        makeDraggable(this);
        setupContainer();
    }

    public void createSyncTransitionToSynchronisationListeners(Node node, SynchronizationViewModel synchronization) {
        synchronization.getTransitions().addListener(new ListChangeListener<TransitionViewModel>() {
            @Override
            public void onChanged(Change<? extends TransitionViewModel> c) {
                while(c.next()){
                    if(c.wasAdded()) {
                        for(TransitionViewModel transitionViewModel : c.getAddedSubList()){
                            for (TransitionContainer transitionContainer : SynchronizationContainer.this.getPlanEditorGroup().getTransitionContainers().values()) {
                                if (transitionContainer.getViewModelElement() == transitionViewModel) {
                                    transitionContainers.add(transitionContainer);
                                    transitionContainer.addListener(new InvalidationListener() {
                                        @Override
                                        public void invalidated(Observable observable) {
                                            SynchronizationContainer.this.setupContainer();
                                        }
                                    });
                                }
                            }
                        }
                    } else if(c.wasRemoved()){
                        for(TransitionViewModel transitionViewModel : c.getRemoved()){
                            for (TransitionContainer transitionContainer : transitionContainers) {
                                if (transitionContainer.getViewModelElement() == transitionViewModel) {
                                    transitionContainers.remove(transitionContainer);
                                }
                            }
                        }
                    }
                }
                Platform.runLater(SynchronizationContainer.this::redrawElement);
            }
        });
    }

    @Override
    public void setupContainer() {
        getChildren().clear();

        SynchronizationViewModel syncViewModel = (SynchronizationViewModel) getViewModelElement();

        // POSITION
        setLayoutX(syncViewModel.getXPosition());
        setLayoutY(syncViewModel.getYPosition());

        // SYNC - CONTAINER
        Circle circle = new Circle(StateContainer.STATE_RADIUS, getVisualisationColor());
        circle.setFill(new ImagePattern(new AlicaIcon(syncViewModel.getType(), AlicaIcon.Size.SYNC)));
        visualRepresentation = circle;
        setEffectToStandard();

        // TEXT
        Text e = new Text(synchronisation.getName());
        getChildren().add(e);
        e.setLayoutX(e.getLayoutX() - e.getLayoutBounds().getWidth() / 2);
        e.setLayoutY(e.getLayoutY() - StateContainer.STATE_RADIUS);

        // SYNC - TRANSITIONS
        if (transitionContainers != null && transitionContainers.size() > 0) {

            Point2D middle = null;
            Shape transitionLine = null;
            for (TransitionContainer transContainer: transitionContainers) {
                // LINE (WITHOUT BENDPOINTS)
                if (transContainer.getVisualRepresentation() instanceof Line) {
                    transitionLine = (Line) transContainer.getVisualRepresentation();

                    // CALCULATE MIDDLE OF TRANSITION
                    double middleXT = ((Line) transitionLine).getStartX() + (((Line) transitionLine).getEndX() - ((Line) transitionLine).getStartX()) / 2;
                    double middleYT = ((Line) transitionLine).getStartY() + (((Line) transitionLine).getEndY() - ((Line) transitionLine).getStartY()) / 2;
                    middle = parentToLocal(transContainer.localToParent(middleXT, middleYT));

                    // POLYLINE (WITH BENDPOINTS)
                } else if (transContainer.getVisualRepresentation() instanceof Polyline) {
                    transitionLine = (Polyline) transContainer.getVisualRepresentation();

                    ObservableList<Double> points = ((Polyline) transitionLine).getPoints();
                    double middleXT;
                    double middleYT;

                    // CALCULATE "MIDDLE" OF TRANSITION
                    if (((points.size() / 2) / 2) % 2 == 1) {
                        int i = ((points.size() / 2) / 2) * 2;
                        middleXT = points.get(i);
                        middleYT = points.get(i + 1);

                    } else {
                        int i = (points.size() / 2);
                        middleXT = points.get(i - 2) + points.get(i) - points.get(i - 2);
                        middleYT = points.get(i - 1) + points.get(i + 1) - points.get(i - 1);
                    }
                    middle = parentToLocal(transContainer.localToParent(middleXT, middleYT));
                }

                // MAGICAL MATH
                double _fromX = transitionLine.getLayoutX();
                double _fromY = transitionLine.getLayoutY();
                double _toX = middle.getX() - 3 * ((middle.getX() - _fromX) / Math.sqrt(Math.pow(middle.getX() - _fromX, 2) + Math.pow(middle.getY() - _fromY, 2)));
                double _toY = middle.getY() - 3 * ((middle.getY() - _fromY) / Math.sqrt(Math.pow(middle.getX() - _fromX, 2) + Math.pow(middle.getY() - _fromY, 2)));
                double vecX = _toX - _fromX;
                double vecY = _toY - _fromY;
                double vecLen = Math.sqrt((vecX * vecX) + (vecY * vecY));
                double triangleSpanVecX = vecY;
                double triangleSpanVecY = -vecX;
                double triangleSpanLen = Math.sqrt(triangleSpanVecY * triangleSpanVecY + triangleSpanVecX * triangleSpanVecX);


                // CREATE SYNCTRANSITION LINE
                Line line = new Line(_fromX, _fromY, _toX, _toY);
                line.setStroke(Color.RED);
                line.setStrokeWidth(3);
                getChildren().add(line);

                // CREATE SYNTRANSITION ARROWHEAD
                int arrowSize = 5;
                Polygon polygon = new Polygon(
                        _toX - arrowSize * (vecX / vecLen) + arrowSize * (triangleSpanVecX / triangleSpanLen),
                        _toY - arrowSize * (vecY / vecLen) + arrowSize * (triangleSpanVecY / triangleSpanLen),
                        _toX,
                        _toY,
                        _toX - arrowSize * (vecX / vecLen) - arrowSize * (triangleSpanVecX / triangleSpanLen),
                        _toY - arrowSize * (vecY / vecLen) - arrowSize * (triangleSpanVecY / triangleSpanLen));
                polygon.setFill(Color.RED);
                polygon.setStroke(Color.RED);
                polygon.setStrokeWidth(4);
                polygon.setVisible(true);
                getChildren().add(polygon);

                transitionToLineMap.put(transContainer, line);
            }

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
        setupContainer();
        invalidationListeners.forEach(new Consumer<InvalidationListener>() {
            @Override
            public void accept(InvalidationListener listener) {
                listener.invalidated(SynchronizationContainer.this);
            }
        });
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
