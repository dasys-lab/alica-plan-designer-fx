package de.unikassel.vs.alica.planDesigner.view.editor.container;

import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.model.PlanElementViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.StateViewModel;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class StateContainer extends Container implements Observable {

    public static final double STATE_RADIUS = 20.0;
    private boolean dragged;
    private List<InvalidationListener> invalidationListeners;
    private List<AbstractPlanContainer> statePlans;
    private StateViewModel state;

    public StateContainer(StateViewModel state, PlanTab planTab) {
        super(state, null, planTab);
        this.state = state;
        this.statePlans = new ArrayList<>();
        invalidationListeners = new ArrayList<>();
        makeDraggable(this);
        createPositionListeners(this, state);
        createAbstractPlanToStateListeners( state);
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

        for (PlanElementViewModel plan : state.getAbstractPlans()) {
            statePlans.add(new AbstractPlanContainer(this, plan, this.planTab));
        }

        if(statePlans != null && !statePlans.isEmpty()) {
            getChildren().addAll(statePlans);
        }
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

    public List<AbstractPlanContainer> getStatePlans() {
        return statePlans;
    }

    public StateViewModel getState() {
        return state;
    }

    @Override
    public void setEffectToStandard() {
        this.setEffect(null);
        this.visualRepresentation.setEffect(Container.standardEffect);
    }

    @Override
    public void setCustomEffect(Effect effect) {
        this.visualRepresentation.setEffect(effect);
    }
}
