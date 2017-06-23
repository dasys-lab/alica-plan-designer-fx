package de.uni_kassel.vs.cn.planDesigner.ui.editor.container;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.change.ChangePosition;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.alica.Synchronisation;
import de.uni_kassel.vs.cn.planDesigner.alica.Transition;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Point2D;
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

/**
 * Created by marci on 19.03.17.
 */
public class SynchronisationContainer extends AbstractPlanElementContainer<Synchronisation> implements Observable {
    private boolean dragged;
    private List<InvalidationListener> invalidationListeners;
    private List<TransitionContainer> transitionContainers;
    private Map<Transition, Shape> transitionToLineMap;

    /**
     * @param containedElement
     * @param pmlUiExtension
     * @param commandStack
     */
    public SynchronisationContainer(Synchronisation containedElement, List<TransitionContainer>  transitionContainers,PmlUiExtension pmlUiExtension, CommandStack commandStack) {
        super(containedElement, pmlUiExtension, commandStack);
        invalidationListeners = new ArrayList<>();
        transitionToLineMap = new HashMap<>();
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
        setLayoutX(getPmlUiExtension().getXPos());
        setLayoutY(getPmlUiExtension().getYPos());
        visualRepresentation = new Circle(StateContainer.STATE_RADIUS, getVisualisationColor());
        visualRepresentation.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
        ((Circle)visualRepresentation).setFill(new ImagePattern(new Image(getClass().getClassLoader()
                .getResourceAsStream("images/synchronisation36x24.png"))));
        Text e = new Text(getContainedElement().getName());
        getChildren().add(e);
        e.setLayoutX(e.getLayoutX() - e.getLayoutBounds().getWidth()/2);
        e.setLayoutY(e.getLayoutY() - StateContainer.STATE_RADIUS);
        if (transitionContainers != null && transitionContainers.size() > 0) {
            transitionContainers.forEach(transitionContainer -> {
                if (transitionContainer.getVisualRepresentation() instanceof Line) {
                    Line visualRepresentation = (Line) transitionContainer.getVisualRepresentation();
                    double middleXT = visualRepresentation.getStartX() + (visualRepresentation.getEndX() - visualRepresentation.getStartX())/2;
                    double middleYT = visualRepresentation.getStartY() + (visualRepresentation.getEndY() - visualRepresentation.getStartY())/2;
                    Point2D middleP = transitionContainer.localToParent(middleXT, middleYT);
                    Point2D middleS = this.parentToLocal(middleP);
                    Line line = new Line(visualRepresentation.getLayoutX(),
                            visualRepresentation.getLayoutY(),
                            middleS.getX(),
                            middleS.getY());
                    line.setFill(Color.VIOLET);
                    getChildren().add(line);
                    transitionToLineMap.put(transitionContainer.getContainedElement(), line);
                } else if(transitionContainer.getVisualRepresentation() instanceof Polyline) {
                    Polyline visualRepresentation = (Polyline) transitionContainer.getVisualRepresentation();
                    double middleXT;
                    double middleYT;
                    if (((visualRepresentation.getPoints().size() / 2) / 2) % 2 == 1) {
                        int i = ((visualRepresentation.getPoints().size() / 2) / 2)*2;
                        middleXT = visualRepresentation.getPoints().get(i);
                        middleYT = visualRepresentation.getPoints().get(i+1);

                    } else {
                        int i = (visualRepresentation.getPoints().size() / 2);
                        middleXT = visualRepresentation.getPoints().get(i-2) + visualRepresentation.getPoints().get(i) - visualRepresentation.getPoints().get(i-2);
                        middleYT = visualRepresentation.getPoints().get(i-1) + visualRepresentation.getPoints().get(i+1) - visualRepresentation.getPoints().get(i-1);
                    }
                    Point2D middleP = transitionContainer.localToParent(middleXT, middleYT);
                    Point2D middleS = this.parentToLocal(middleP);
                    Line line = new Line(visualRepresentation.getLayoutX(),
                            visualRepresentation.getLayoutY(),
                            middleS.getX(),
                            middleS.getY());
                    line.setFill(Color.VIOLET);
                    getChildren().add(line);
                    transitionToLineMap.put(transitionContainer.getContainedElement(), line);
                }
            });

        }
        getChildren().add(visualRepresentation);
    }

    @Override
    public Color getVisualisationColor() {
        return Color.TRANSPARENT;
    }

    @Override
    public CommandStack getCommandStackForDrag() {
        return commandStack;
    }

    @Override
    public void redrawElement() {
        //((PlanEditorPane) getParent()).setupPlanVisualisation();
        setupContainer();
        invalidationListeners.forEach(listener -> listener.invalidated(this));
    }

    @Override
    public AbstractCommand createMoveElementCommand() {
        return new ChangePosition(getPmlUiExtension(), getContainedElement(),
                (int) (getLayoutX()),
                (int) (getLayoutY()), (PlanElement) getContainedElement().eResource().getContents().get(0));
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
