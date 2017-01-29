package de.uni_kassel.vs.cn.planDesigner.ui.editor.container;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.Command;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.change.ChangePositionForBendpoint;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.Bendpoint;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.EditorConstants;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * The {@link BendpointContainer} class holds a visual representation of a {@link de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.Bendpoint}.
 * It also contains an object of type {@link Bendpoint} to hold modifications to it.
 * This modifications are later written back to the actual Resource.
 */
public class BendpointContainer extends Rectangle implements DraggableEditorElement {
    private static final double SQUARE_SIZE = 5;
    private static final double SHIFTING_CONSTANT = EditorConstants.PLAN_SHIFTING_PARAMETER + EditorConstants.SECTION_MARGIN - (SQUARE_SIZE / 2);
    private Bendpoint containedElement;
    private PmlUiExtension pmlUiExtension;
    private CommandStack commandStack;
    private Group wrapGroup;
    private boolean dragged;

    public BendpointContainer(Bendpoint containedElement, PmlUiExtension pmlUiExtension, CommandStack commandStack) {
        super(containedElement.getXPos() + SHIFTING_CONSTANT,
                containedElement.getYPos() + SHIFTING_CONSTANT, 10, 10);
        this.containedElement = containedElement;
        this.pmlUiExtension = pmlUiExtension;
        this.commandStack = commandStack;
        setFill(Color.BLACK);
        this.wrapGroup = (Group) makeDraggable(this);
    }

    public Bendpoint getContainedElement() {
        return containedElement;
    }

    public PmlUiExtension getPmlUiExtension() {
        return pmlUiExtension;
    }

    @Override
    public CommandStack getCommandStackForDrag() {
        return commandStack;
    }

    @Override
    public void redrawElement() {
        ((TransitionContainer) this.getParent().getParent()).draw();
    }

    @Override
    public Command createMoveElementCommand() {
        return new ChangePositionForBendpoint(containedElement, (int) (getX() + getTranslateX() - SHIFTING_CONSTANT),
                (int) (getY() + getTranslateY() - SHIFTING_CONSTANT));
    }

    @Override
    public void setDragged(boolean dragged) {
        this.dragged = dragged;
    }

    @Override
    public boolean wasDragged() {
        return dragged;
    }

    public Group getWrapGroup() {
        return wrapGroup;
    }
}
