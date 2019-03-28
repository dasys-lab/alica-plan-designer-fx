package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.*;
import de.unikassel.vs.alica.planDesigner.command.Command;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class DeleteParametrisation extends Command {

    protected Parametrisation parametrisation;
    protected PlanElement parent;

    public DeleteParametrisation(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager, mmq);
        this.parametrisation = (Parametrisation) modelManager.getPlanElement(mmq.getElementId());
        this.parent = modelManager.getPlanElement(mmq.getParentId());
    }

    @Override
    public void doCommand() {
        if (parent instanceof State) {
            ((State) this.parent).removeParametrisation(parametrisation);
        } else if (parent instanceof PlanType) {
            ((PlanType) this.parent).removeParametrisation(parametrisation);
        }
        this.modelManager.dropPlanElement(Types.VARIABLE, this.parametrisation, false);
        this.fireEvent(ModelEventType.ELEMENT_DELETED, this.parametrisation);
    }

    @Override
    public void undoCommand() {
        if (parent instanceof State) {
            ((State) this.parent).addParametrisation(parametrisation);
        } else if (parent instanceof PlanType) {
            ((PlanType) this.parent).addParametrisation(parametrisation);
        }
        this.modelManager.storePlanElement(Types.PARAMETRISATION, this.parametrisation, false);
        this.fireEvent(ModelEventType.ELEMENT_CREATED, this.parametrisation);
    }
}
