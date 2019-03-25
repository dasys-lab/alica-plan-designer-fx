package de.unikassel.vs.alica.planDesigner.command.change;

import de.unikassel.vs.alica.planDesigner.alicamodel.EntryPoint;
import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.alicamodel.State;
import de.unikassel.vs.alica.planDesigner.command.Command;
import de.unikassel.vs.alica.planDesigner.events.ModelEvent;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.UiExtension;

public class ConnectEntryPointsWithState extends Command {

    protected EntryPoint entryPoint;
    protected State newState;
    protected State previousState;
    protected UiExtension parentOfElement;

    public ConnectEntryPointsWithState(ModelManager manager, ModelModificationQuery mmq) {
        super(manager, mmq);
        this.entryPoint = (EntryPoint) manager.getPlanElement((mmq.getRelatedObjects().get(Types.ENTRYPOINT)));
        this.previousState = entryPoint.getState();
        this.newState = (State) manager.getPlanElement(mmq.getRelatedObjects().get(Types.STATE));
        this.parentOfElement = manager.getPlanUIExtensionPair(mmq.getParentId());
    }


    @Override
    public void doCommand() {
        if (newState.getEntryPoint() != null) {
            return;
        }

        entryPoint.setState(newState);
        newState.setEntryPoint(entryPoint);

        if (previousState != null) {
            previousState.setEntryPoint(null);
        }

        //event for updateView
        Plan plan = parentOfElement.getPlan();
        ModelEvent event = new ModelEvent(ModelEventType.ELEMENT_CREATED, plan, Types.INITSTATECONNECTION);
        event.setParentId(plan.getId());
        event.setNewValue(entryPoint.getId());

        modelManager.fireEvent(event);
    }

    @Override
    public void undoCommand() {
        entryPoint.setState(previousState);
    }
}
