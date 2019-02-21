package de.unikassel.vs.alica.planDesigner.command.change;

import de.unikassel.vs.alica.planDesigner.alicamodel.Synchronisation;
import de.unikassel.vs.alica.planDesigner.alicamodel.Transition;
import de.unikassel.vs.alica.planDesigner.command.Command;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class ConnectSynchronizationWithTransition extends Command {

    private Transition transition;
    private Synchronisation synchronisation;

    public ConnectSynchronizationWithTransition(ModelManager manager, ModelModificationQuery mmq) {
        super(manager, mmq);
        // hack to make the fireEvent-Method work
        mmq.setParentId(mmq.getRelatedObjects().get(Types.TRANSITION));

        synchronisation = (Synchronisation) manager.getPlanElement(mmq.getRelatedObjects().get(Types.SYNCHRONISATION));
        transition = (Transition) manager.getPlanElement(mmq.getRelatedObjects().get(Types.TRANSITION));
    }

    @Override
    public void doCommand() {
        synchronisation.addSyncedTransition(transition);
        transition.setSynchronisation(synchronisation);
        this.fireEvent(ModelEventType.ELEMENT_CONNECTED, synchronisation);
    }

    @Override
    public void undoCommand() {
        synchronisation.removeSyncedTransition(transition);
        transition.setSynchronisation(null);
        this.fireEvent(ModelEventType.ELEMENT_DISCONNECTED, synchronisation);
    }
}
