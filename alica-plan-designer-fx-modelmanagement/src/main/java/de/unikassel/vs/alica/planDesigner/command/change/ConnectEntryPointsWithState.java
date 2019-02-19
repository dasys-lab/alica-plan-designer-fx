package de.unikassel.vs.alica.planDesigner.command.change;

import de.unikassel.vs.alica.planDesigner.alicamodel.EntryPoint;
import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.alicamodel.State;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.events.ModelEvent;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PlanUiExtensionPair;

public class ConnectEntryPointsWithState extends AbstractCommand {

    protected EntryPoint entryPoint;
    protected State newState;
    protected State previousState;
    protected PlanUiExtensionPair parentOfElement;

    public ConnectEntryPointsWithState(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager);
        this.entryPoint = (EntryPoint) modelManager.getPlanElement((mmq.getRelatedObjects().get(State.ENTRYPOINT)));
        this.previousState = entryPoint.getState();
        this.newState = (State) modelManager.getPlanElement(mmq.getRelatedObjects().get(Types.STATE));
        this.parentOfElement = modelManager.getPlanUIExtensionPair(mmq.getParentId());
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
