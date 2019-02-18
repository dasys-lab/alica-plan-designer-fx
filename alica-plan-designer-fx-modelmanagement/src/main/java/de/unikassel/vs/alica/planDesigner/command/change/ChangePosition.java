package de.unikassel.vs.alica.planDesigner.command.change;

import de.unikassel.vs.alica.planDesigner.alicamodel.PlanElement;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.events.UiExtensionModelEvent;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.UiExtension;

public class ChangePosition extends AbstractCommand {

    protected PlanElement planElement;
    protected UiExtension uiElement;
    protected int newX;
    protected int newY;

    protected int oldX;
    protected int oldY;

    public ChangePosition(ModelManager modelManager, UiExtension uiElement, PlanElement planElement, int newX, int newY) {
        super(modelManager);
        this.uiElement = uiElement;
        this.planElement = planElement;
        this.newX = newX;
        this.newY = newY;

        // save old position for undo
        oldX = uiElement.getX();
        oldY = uiElement.getY();
    }

    @Override
    public void doCommand() {
        uiElement.setX(newX);
        uiElement.setY(newY);

        UiExtensionModelEvent event = createUiExtensionModelEvent(newX, newY);
        fireUiExtensionModelEvent(event);
    }

    @Override
    public void undoCommand() {
        uiElement.setX(oldX);
        uiElement.setY(oldY);

        UiExtensionModelEvent event = createUiExtensionModelEvent(oldX, oldY);
        fireUiExtensionModelEvent(event);
    }

    private void fireUiExtensionModelEvent(UiExtensionModelEvent event){
        modelManager.fireUiExtensionModelEvent(event);
    }

    private UiExtensionModelEvent createUiExtensionModelEvent(int x, int y){
        UiExtensionModelEvent event = new UiExtensionModelEvent(ModelEventType.ELEMENT_ATTRIBUTE_CHANGED, planElement, null);
        UiExtension extension = new UiExtension();
        extension.setX(x);
        extension.setY(y);
        event.setExtension(extension);

        return event;
    }
}
