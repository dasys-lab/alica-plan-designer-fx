package de.unikassel.vs.alica.planDesigner.command.change;

import de.unikassel.vs.alica.planDesigner.alicamodel.EntryPoint;
import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.alicamodel.State;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.events.ModelEvent;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PlanModelVisualisationObject;

public class ConnectEntryPointsWithState extends AbstractCommand {

    protected EntryPoint entryPoint;
    protected State newState;
    protected State previousState;
    protected PlanModelVisualisationObject parentOfElement;

    public ConnectEntryPointsWithState(ModelManager modelManager, PlanModelVisualisationObject parentOfElement, EntryPoint entryPoint, State state) {
        super(modelManager);
        this.entryPoint = entryPoint;
        this.parentOfElement = parentOfElement;
        this.newState = state;
    }


    @Override
    public void doCommand() {
        previousState = entryPoint.getState();

        if(newState.getEntryPoint() != null){
            return;
        }

        entryPoint.setState(newState);
        newState.setEntryPoint(entryPoint);

        if (previousState != null) {
            previousState.setEntryPoint(null);
        }

        //event for updateView
        Plan plan = (Plan) parentOfElement.getPlan();
        ModelEvent event = new ModelEvent(ModelEventType.ELEMENT_CREATED, plan, Types.INITSTATECONNECTION);
        event.setParentId(parentOfElement.getPlan().getId());
        event.setNewValue(entryPoint.getId());

        modelManager.fireEvent(event);
    }

    @Override
    public void undoCommand() {
        entryPoint.setState(previousState);
    }
}
