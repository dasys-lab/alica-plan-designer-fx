package de.unikassel.vs.alica.planDesigner.command.change;

import de.unikassel.vs.alica.planDesigner.alicamodel.PlanElement;
import de.unikassel.vs.alica.planDesigner.command.UiPositionCommand;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;

public class ChangePosition extends UiPositionCommand {

    protected PlanElement planElement;

    protected int oldX;
    protected int oldY;

    public ChangePosition(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager, mmq);
        this.planElement = modelManager.getPlanElement(mmq.getElementId());
        this.uiElement = this.createUiElement(mmq.getParentId(), planElement);
        this.oldX = uiElement.getX();
        this.oldY = uiElement.getY();
    }

    @Override
    public void doCommand() {
        uiElement.setX(x);
        uiElement.setY(y);

        this.fireEvent(ModelEventType.ELEMENT_ATTRIBUTE_CHANGED, this.planElement);
    }

    @Override
    public void undoCommand() {
        uiElement.setX(oldX);
        uiElement.setY(oldY);

        this.fireEvent(ModelEventType.ELEMENT_ATTRIBUTE_CHANGED, this.planElement);
    }
}
