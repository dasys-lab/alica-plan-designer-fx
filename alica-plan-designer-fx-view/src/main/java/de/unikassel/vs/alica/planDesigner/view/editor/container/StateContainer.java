package de.unikassel.vs.alica.planDesigner.view.editor.container;

import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.model.PlanElementViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.StateViewModel;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Insets;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class StateContainer extends AbstractPlanElementContainer implements Observable {

    public static final double STATE_RADIUS = 20.0;
    private boolean dragged;
    private List<InvalidationListener> invalidationListeners;
    private List<AbstractPlanHBox> statePlans;
    private StateViewModel state;

    public StateContainer(StateViewModel state, PlanTab planTab) {
        super(state, null, planTab);
        this.state = state;
        this.statePlans = new ArrayList<>();
        invalidationListeners = new ArrayList<>();
        makeDraggable(this);
        createPositionListeners(this, state);
        deleteAbstractPlansFromState(this);
        createAbstractPlanToStateListeners( state);
        setBackground(new Background(new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY)));
        setupContainer();
    }

    @Override
    public void setupContainer() {
        getChildren().clear();
        visualRepresentation = new Circle(STATE_RADIUS, getVisualisationColor());
        setEffectToStandard();
        getChildren().add(visualRepresentation);
        Text elementName = new Text(state.getName());
        getChildren().add(elementName);
        elementName.setLayoutX(elementName.getLayoutX() - elementName.getLayoutBounds().getWidth() / 2);
        elementName.setLayoutY(elementName.getLayoutY() - STATE_RADIUS * 1.3);

        for (PlanElementViewModel plan : state.getPlanElements()) {
            statePlans.add(new AbstractPlanHBox(plan, this));
        }

        if(statePlans != null && !statePlans.isEmpty()) {
            getChildren().addAll(statePlans);
        }
    }

    @Override
    public void setEffectToStandard() {
        visualRepresentation.setEffect(new DropShadow(BlurType.THREE_PASS_BOX,
                new Color(0,0,0,0.8), 10, 0, 0, 0));
    }

    @Override
    public Color getVisualisationColor() {
        return Color.YELLOW;
    }

    @Override
    public void redrawElement() {
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

    public List<AbstractPlanHBox> getStatePlans() {
        return statePlans;
    }

    public StateViewModel getState() {
        return state;
    }
}
