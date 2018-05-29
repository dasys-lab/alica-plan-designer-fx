package de.uni_kassel.vs.cn.planDesigner.view.editor.container;

import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.command.add.AddBendpointToPlan;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.Bendpoint;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class PotentialBendPointContainer extends BendpointContainer {

    private TransitionContainer transitionContainer;

    public PotentialBendPointContainer(Bendpoint containedElement, PmlUiExtension pmlUiExtension, CommandStack commandStack, Plan parent, TransitionContainer transitionContainer) {
        super(containedElement, pmlUiExtension, commandStack, parent);
        this.transitionContainer = transitionContainer;
    }

    @Override
    protected Color getVisualisationColor() {
        return Color.WHITE;
    }

    @Override
    protected void init() {
        this.setLayoutX(containedElement.getXPos());
        this.setLayoutY(containedElement.getYPos());
        setFill(getVisualisationColor());
        addEventHandler(MouseEvent.MOUSE_PRESSED, new OnClickCreateHandler());
    }

    private class OnClickCreateHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            commandStack.storeAndExecute(new AddBendpointToPlan(PotentialBendPointContainer.this.getContainedElement(), PotentialBendPointContainer.this.getPmlUiExtension(), parent));
            removeEventHandler(MouseEvent.MOUSE_PRESSED, OnClickCreateHandler.this);
            transitionContainer.redrawElement();
        }
    }
}
