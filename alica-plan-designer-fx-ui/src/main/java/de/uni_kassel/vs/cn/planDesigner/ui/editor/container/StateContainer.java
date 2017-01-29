package de.uni_kassel.vs.cn.planDesigner.ui.editor.container;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.Command;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.change.ChangePosition;
import de.uni_kassel.vs.cn.planDesigner.alica.State;
import de.uni_kassel.vs.cn.planDesigner.alica.SuccessState;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.EditorConstants;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.PlanEditorPane;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * Created by marci on 02.12.16.
 */
public class StateContainer extends PlanElementContainer<State> implements DraggableEditorElement {

    public static final double STATE_RADIUS = 20.0;
    private boolean dragged;

    public StateContainer(PmlUiExtension pmlUiExtension, State state, CommandStack commandStack) {
        super(state, pmlUiExtension, commandStack);
        draw();
    }

    @Override
    public void draw() {
        getChildren().clear();
        visualRepresentation = new Circle(20, Color.YELLOW);
        getChildren().add(visualRepresentation);
        getChildren().add(new Text(getContainedElement().getName()));
        setLayoutX(getPmlUiExtension().getXPos() * 1);
        setLayoutY(getPmlUiExtension().getYPos() * 1);
        // TODO fix slow dragging
        if(getContainedElement() instanceof SuccessState) {
            visualRepresentation.setFill(Color.GREEN);
        } else {
            makeDraggable(this);
        }
    }

    @Override
    public Node createWrapper(Node node) {
        return this;
    }

    @Override
    public CommandStack getCommandStackForDrag() {
        return commandStack;
    }

    @Override
    public void redrawElement() {
        ((PlanEditorPane) getParent()).visualize();
    }

    @Override
    public Command createMoveElementCommand() {
        return new ChangePosition(getPmlUiExtension(), getContainedElement(),
                (int) (getLayoutX() + getTranslateX() - EditorConstants.PLAN_SHIFTING_PARAMETER - EditorConstants.SECTION_MARGIN),
                (int) (getLayoutY() + getTranslateY() - EditorConstants.PLAN_SHIFTING_PARAMETER - EditorConstants.SECTION_MARGIN));
    }

    @Override
    public void setDragged(boolean dragged) {
        this.dragged = dragged;
    }

    @Override
    public boolean wasDragged() {
        return dragged;
    }
}
