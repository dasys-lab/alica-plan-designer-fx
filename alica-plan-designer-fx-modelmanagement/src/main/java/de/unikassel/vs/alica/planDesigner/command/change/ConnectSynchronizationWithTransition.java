package de.unikassel.vs.alica.planDesigner.command.change;

import de.unikassel.vs.alica.planDesigner.alicamodel.Synchronisation;
import de.unikassel.vs.alica.planDesigner.alicamodel.Transition;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.events.ModelEvent;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class ConnectSynchronizationWithTransition extends AbstractCommand {

    private Transition transition;
    private Synchronisation synchronisation;

    public ConnectSynchronizationWithTransition(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager);

        synchronisation = (Synchronisation) modelManager.getPlanElement(mmq.getRelatedObjects().get(Types.SYNCHRONISATION));
        transition = (Transition) modelManager.getPlanElement(mmq.getRelatedObjects().get(Types.TRANSITION));
    }

    @Override
    public void doCommand() {
        synchronisation.addSyncedTransition(transition);
        ModelEvent modelEvent = new ModelEvent(ModelEventType.ELEMENT_CONNECTED, transition, Types.SYNCTRANSITION);
        modelEvent.setParentId(synchronisation.getId());
        modelManager.fireEvent(modelEvent);
    }

    @Override
    public void undoCommand() {
        synchronisation.removeSyncedTransition(transition);
        ModelEvent modelEvent = new ModelEvent(ModelEventType.ELEMENT_DISCONNECTED, transition, Types.SYNCTRANSITION);
        modelEvent.setParentId(synchronisation.getId());
        modelManager.fireEvent(modelEvent);
    }
}
