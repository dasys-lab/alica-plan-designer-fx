package de.uni_kassel.vs.cn.planDesigner.ui.editor.container;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.change.ChangePosition;
import de.uni_kassel.vs.cn.planDesigner.alica.Synchronisation;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by marci on 19.03.17.
 */
public class SynchronisationContainer extends AbstractPlanElementContainer<Synchronisation> implements Observable {

    public static final double STATE_RADIUS = 20.0;
    private boolean dragged;
    private List<InvalidationListener> invalidationListeners;
    private List<TransitionContainer> transitionContainers;

    /**
     * @param containedElement
     * @param pmlUiExtension
     * @param commandStack
     */
    public SynchronisationContainer(Synchronisation containedElement, PmlUiExtension pmlUiExtension, CommandStack commandStack) {
        super(containedElement, pmlUiExtension, commandStack);
        invalidationListeners = new ArrayList<>();
        makeDraggable(this);
        //setBackground(new Background(new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY)));
        setupContainer();
    }

    @Override
    public void setupContainer() {
        getChildren().clear();
        setLayoutX(getPmlUiExtension().getXPos());
        setLayoutY(getPmlUiExtension().getYPos());
        visualRepresentation = new Circle(STATE_RADIUS, getVisualisationColor());
        getChildren().add(visualRepresentation);
        Text e = new Text(getContainedElement().getName());
        getChildren().add(e);
        e.setLayoutX(e.getLayoutX() - e.getLayoutBounds().getWidth()/2);
        e.setLayoutY(e.getLayoutY() - StateContainer.STATE_RADIUS);

        if (transitionContainers != null && transitionContainers.size() > 0) {
            transitionContainers.forEach(transitionContainer -> {
                Line line = new Line(getPmlUiExtension().getXPos(),
                        getPmlUiExtension().getYPos(),
                        transitionContainer.getLayoutX(),
                        transitionContainer.getLayoutY());
                //line.getStrokeDashArray().addAll(2d, 10d);
                line.setFill(Color.VIOLET);
                getChildren().add(line);
            });

        }
    }

    @Override
    public Color getVisualisationColor() {
        return Color.WHITE;
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
                (int) (getLayoutY()));
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
