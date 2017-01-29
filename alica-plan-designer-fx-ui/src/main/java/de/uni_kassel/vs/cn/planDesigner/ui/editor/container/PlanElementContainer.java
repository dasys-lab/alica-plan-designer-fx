package de.uni_kassel.vs.cn.planDesigner.ui.editor.container;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.PlanEditorPane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

/**
 * The {@link PlanElementContainer} is a base class for visual representations, with a model object to hold changes from the visualisation
 * that will be written back to resource later.
 */
public abstract class PlanElementContainer<T extends PlanElement> extends Pane {

    private T containedElement;
    private PmlUiExtension pmlUiExtension;
    protected Shape visualRepresentation;
    protected CommandStack commandStack;

    /**
     *
     * @param containedElement
     * @param pmlUiExtension
     * @param commandStack
     */
    public PlanElementContainer(T containedElement, PmlUiExtension pmlUiExtension, CommandStack commandStack) {
        this.containedElement = containedElement;
        this.pmlUiExtension = pmlUiExtension;
        this.commandStack = commandStack;
        setBackground(Background.EMPTY);
        setPickOnBounds(false);
        addEventHandler(MouseEvent.MOUSE_CLICKED, getMouseClickedEventHandler(containedElement));
    }

    protected EventHandler<MouseEvent> getMouseClickedEventHandler(T containedElement) {
        return event -> ((PlanEditorPane) getParent()).getPlanEditorTab().getSelectedPlanElement().setValue(containedElement);
    }


    /**
     *
     * @return
     */
    public Shape getVisualRepresentation() {
        return visualRepresentation;
    }

    /**
     *
     * @return
     */
    public T getContainedElement() {
        return containedElement;
    }

    /**
     *
     * @return
     */
    public PmlUiExtension getPmlUiExtension() {
        return pmlUiExtension;
    }

    /**
     *
     */
    public abstract void draw();
}
