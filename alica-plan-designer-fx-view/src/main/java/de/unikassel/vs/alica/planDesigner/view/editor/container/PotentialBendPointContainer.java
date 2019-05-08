package de.unikassel.vs.alica.planDesigner.view.editor.container;

import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.model.PlanViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class PotentialBendPointContainer extends BendpointContainer {

    private TransitionContainer transitionContainer;

    public PotentialBendPointContainer(ViewModelElement containedElement, PlanViewModel parent, PlanTab planTab,
                                       TransitionContainer transitionContainer) {
        super(containedElement, parent, planTab);
        this.transitionContainer = transitionContainer;
    }

    @Override
    protected Color getVisualisationColor() {
        return Color.WHITE;
    }

    @Override
    protected void init() {
//        this.setLayoutX(containedElement.getXPos());
//        this.setLayoutY(containedElement.getYPos());
        setFill(getVisualisationColor());
        addEventHandler(MouseEvent.MOUSE_PRESSED, new OnClickCreateHandler());
    }

    private class OnClickCreateHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
//            commandStack.storeAndExecute(new AddBendpointToPlan(PotentialBendPointContainer.this.getContainedElement(),
//                    PotentialBendPointContainer.this.getPmlUiExtension(), parent));
            removeEventHandler(MouseEvent.MOUSE_PRESSED, OnClickCreateHandler.this);
            transitionContainer.redrawElement();
        }
    }
}
