package de.unikassel.vs.alica.planDesigner.command.change;

import de.unikassel.vs.alica.planDesigner.alicamodel.Synchronization;
import de.unikassel.vs.alica.planDesigner.alicamodel.Transition;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.events.ModelEvent;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class ConnectSynchronizationWithTransition extends AbstractCommand {

    private Transition transition;
    private Synchronization synchronization;

    public ConnectSynchronizationWithTransition(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager);

        synchronization = (Synchronization) modelManager.getPlanElement(mmq.getRelatedObjects().get(Types.SYNCHRONIZATION));
        transition = (Transition) modelManager.getPlanElement(mmq.getRelatedObjects().get(Types.TRANSITION));
    }

    @Override
    public void doCommand() {
        synchronization.getSyncedTransitions().add(transition);
        ModelEvent modelEvent = new ModelEvent(ModelEventType.ELEMENT_CONNECTED, transition, Types.SYNCTRANSITION);
        modelEvent.setParentId(synchronization.getId());
        modelManager.fireEvent(modelEvent);
    }

    @Override
    public void undoCommand() {
        synchronization.getSyncedTransitions().remove(transition);
        ModelEvent modelEvent = new ModelEvent(ModelEventType.ELEMENT_DISCONNECTED, transition, Types.SYNCTRANSITION);
        modelEvent.setParentId(synchronization.getId());
        modelManager.fireEvent(modelEvent);
    }
}
