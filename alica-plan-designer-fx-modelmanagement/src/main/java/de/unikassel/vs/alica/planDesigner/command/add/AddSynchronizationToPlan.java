package de.unikassel.vs.alica.planDesigner.command.add;


import de.unikassel.vs.alica.planDesigner.alicamodel.Synchronisation;
import de.unikassel.vs.alica.planDesigner.command.AbstractUiPositionCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PlanUiExtensionPair;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.UiExtension;

public class AddSynchronizationToPlan extends AbstractUiPositionCommand {

    private PlanUiExtensionPair parentOfElement;
    private UiExtension newlyCreatedUiExtension;
    protected Synchronisation synchronisation;
    protected String name;
    protected String comment;

    public AddSynchronizationToPlan(ModelManager manager, ModelModificationQuery mmq) {
        super(manager, mmq);
        this.parentOfElement = manager.getPlanUIExtensionPair(mmq.getParentId());
    }

    @Override
    public void doCommand() {
        this.synchronisation = new Synchronisation();
        this.synchronisation.setName(this.name);
        this.synchronisation.setComment(this.comment);
        this.newlyCreatedUiExtension = this.parentOfElement.getUiExtension(this.synchronisation);
        this.newlyCreatedUiExtension.setX(x);
        this.newlyCreatedUiExtension.setY(y);
        modelManager.createdPlanElement(Types.SYNCHRONISATION, synchronisation, parentOfElement.getPlan(), false);
    }

    @Override
    public void undoCommand() {
        parentOfElement.remove(synchronisation);
        modelManager.createdPlanElement(Types.SYNCHRONISATION, synchronisation, parentOfElement.getPlan(), false);
    }
}
