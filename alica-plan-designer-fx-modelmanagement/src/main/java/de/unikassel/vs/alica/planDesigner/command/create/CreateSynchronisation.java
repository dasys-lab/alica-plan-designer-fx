package de.unikassel.vs.alica.planDesigner.command.create;

import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.alicamodel.Synchronisation;
import de.unikassel.vs.alica.planDesigner.command.UiPositionCommand;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class CreateSynchronisation extends UiPositionCommand {

    protected Synchronisation synchronisation;
    protected Plan plan;

    public CreateSynchronisation(ModelManager manager, ModelModificationQuery mmq) {
        super(manager, mmq);
        this.plan = (Plan) manager.getPlanElement(mmq.getParentId());
        this.synchronisation = createSynchronisation();
        this.uiElement = this.createUiElement(plan.getId(), synchronisation);
    }

    protected Synchronisation createSynchronisation() {
        Synchronisation synchronisation = new Synchronisation();
        synchronisation.setName(mmq.getName());
        synchronisation.setComment(mmq.getComment());
        return synchronisation;
    }

    @Override
    public void doCommand() {
        this.plan.addSynchronisation(this.synchronisation);
        this.uiExtension.add(this.synchronisation, this.uiElement);
        this.modelManager.storePlanElement(Types.SYNCHRONISATION, synchronisation,false);
        this.fireEvent(ModelEventType.ELEMENT_CREATED, synchronisation);
    }

    @Override
    public void undoCommand() {
        this.plan.removeSynchronisation(synchronisation);
        this.uiExtension.remove(this.synchronisation);
        modelManager.dropPlanElement(Types.SYNCHRONISATION, synchronisation, false);
        this.fireEvent(ModelEventType.ELEMENT_DELETED, synchronisation);
    }
}