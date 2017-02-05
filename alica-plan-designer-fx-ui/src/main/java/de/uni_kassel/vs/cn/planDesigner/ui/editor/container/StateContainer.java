package de.uni_kassel.vs.cn.planDesigner.ui.editor.container;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.Command;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.change.ChangePosition;
import de.uni_kassel.vs.cn.planDesigner.alica.State;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.PlanEditorPane;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marci on 02.12.16.
 */
public class StateContainer extends PlanElementContainer<State> implements Observable {

    public static final double STATE_RADIUS = 20.0;
    private boolean dragged;
    private List<InvalidationListener> invalidationListeners;

    public StateContainer(PmlUiExtension pmlUiExtension, State state, CommandStack commandStack) {
        super(state, pmlUiExtension, commandStack);
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
        visualRepresentation = new Circle(STATE_RADIUS, getStateColor());
        getChildren().add(visualRepresentation);
        getChildren().add(new Text(getContainedElement().getName()));

        // TODO fix slow dragging
    }

    protected Color getStateColor() {
        return Color.YELLOW;
    }

    @Override
    protected EventHandler<MouseEvent> getMouseClickedEventHandler(State containedElement) {
        return event -> ((PlanEditorPane) getParent()).getPlanEditorTab().getSelectedPlanElement().setValue(containedElement);
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
    public Command createMoveElementCommand() {
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
