package de.uni_kassel.vs.cn.planDesigner.ui.editor.container;

import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.command.change.ChangePosition;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.alica.PostCondition;
import de.uni_kassel.vs.cn.planDesigner.alica.State;
import de.uni_kassel.vs.cn.planDesigner.alica.TerminalState;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.PlanEditorGroup;
import de.uni_kassel.vs.cn.planDesigner.ui.img.AlicaIcon;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Insets;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by marci on 02.12.16.
 */
public class StateContainer extends AbstractPlanElementContainer<State> implements Observable {

    public static final double STATE_RADIUS = 20.0;
    private boolean dragged;
    private List<InvalidationListener> invalidationListeners;
    private List<AbstractPlanHBox> statePlans;

    /**
     * This constructor is for dummy containers. NEVER use in real UI
     */
    public StateContainer() {
        super(null, null, null);
    }

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
        visualRepresentation = new Circle(STATE_RADIUS, getVisualisationColor());
        setEffectToStandard();
        getChildren().add(visualRepresentation);
        Text elementName = new Text(getContainedElement().getName());
        getChildren().add(elementName);
        elementName.setLayoutX(elementName.getLayoutX() - elementName.getLayoutBounds().getWidth() / 2);
        elementName.setLayoutY(elementName.getLayoutY() - STATE_RADIUS * 1.3);

        statePlans = getContainedElement()
                .getPlans()
                .stream()
                .map(abstractPlan -> new AbstractPlanHBox(abstractPlan, this))
                .collect(Collectors.toList());
        if (getContainedElement() instanceof TerminalState) {
            PostCondition postCondition = ((TerminalState) getContainedElement()).getPostCondition();
            if (postCondition != null) {
                statePlans.add(new AbstractPlanHBox(postCondition, this));
            }
        }
        getChildren().addAll(statePlans);
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
    public CommandStack getCommandStackForDrag() {
        return commandStack;
    }

    @Override
    public void redrawElement() {
        //((PlanEditorGroup) getParent()).setupPlanVisualisation();
        setupContainer();
        invalidationListeners.forEach(listener -> listener.invalidated(this));
    }

    @Override
    public AbstractCommand createMoveElementCommand() {
        return new ChangePosition(getPmlUiExtension(), getContainedElement(),
                (int) (getLayoutX()),
                (int) (getLayoutY()), getContainedElement().getInPlan());
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

}
