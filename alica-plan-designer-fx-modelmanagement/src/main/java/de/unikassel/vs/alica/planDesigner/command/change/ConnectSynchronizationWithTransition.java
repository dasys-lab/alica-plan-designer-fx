package de.unikassel.vs.alica.planDesigner.command.change;

import de.unikassel.vs.alica.planDesigner.alicamodel.Synchronization;
import de.unikassel.vs.alica.planDesigner.alicamodel.Transition;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.events.ModelEvent;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PlanModelVisualisationObject;

public class ConnectSynchronizationWithTransition extends AbstractCommand {

    private Transition transition;
    private final PlanModelVisualisationObject parentOfElement;
    private Synchronization synchronization;

    public ConnectSynchronizationWithTransition(ModelManager modelManager, long syncId, PlanModelVisualisationObject parenOfElement, long transId) {
        super(modelManager);
        for (Synchronization sync : parenOfElement.getPlan().getSynchronizations()) {
            if (syncId == sync.getId()) {
                this.synchronization = sync;
            }
        }
        for (Transition trans : parenOfElement.getPlan().getTransitions()) {
            if (transId == trans.getId()) {
                this.transition = trans;
            }
        }
        this.parentOfElement = parenOfElement;
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
