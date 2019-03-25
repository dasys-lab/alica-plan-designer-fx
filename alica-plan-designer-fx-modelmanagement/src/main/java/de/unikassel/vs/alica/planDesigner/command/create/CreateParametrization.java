package de.unikassel.vs.alica.planDesigner.command.create;

import de.unikassel.vs.alica.planDesigner.alicamodel.*;
import de.unikassel.vs.alica.planDesigner.command.Command;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class CreateParametrization extends Command {

    private final AbstractPlan subPlan;
    private final Variable subVariable;
    private final Variable variable;
    private final Parametrisation parametrisation;
    private final PlanElement parent;

    public CreateParametrization(ModelManager manager, ModelModificationQuery mmq) {
        super(manager, mmq);

        this.parent = modelManager.getPlanElement(mmq.getParentId());

        this.subPlan = (AbstractPlan) modelManager.getPlanElement(mmq.getRelatedObjects().get(Types.PLAN));
        this.variable = (Variable) modelManager.getPlanElement(mmq.getRelatedObjects().get(Types.VARIABLE));
        this.subVariable = (Variable) modelManager.getPlanElement(mmq.getRelatedObjects().get(Types.PARAMETRIZATION));

        this.parametrisation = createParametrization();
    }

    protected Parametrisation createParametrization() {
        Parametrisation parametrisation = new Parametrisation();

        parametrisation.setSubPlan(subPlan);
        parametrisation.setSubVariable(subVariable);
        parametrisation.setVariable(variable);

        return parametrisation;
    }

    @Override
    public void doCommand() {
        if (parent instanceof State) {
            ((State) parent).addParametrisation(this.parametrisation);
        } else if (parent instanceof PlanType) {
            ((PlanType) parent).addParametrisation(this.parametrisation);
        }
        this.modelManager.storePlanElement(Types.PARAMETRIZATION, this.parametrisation, false);
        this.fireEvent(ModelEventType.ELEMENT_CREATED, this.parametrisation);
    }

    @Override
    public void undoCommand() {
        if (parent instanceof State) {
            ((State) parent).removeParametrisation(this.parametrisation);
        } else if (parent instanceof PlanType) {
            ((PlanType) parent).removeParametrisation(this.parametrisation);
        }
        modelManager.dropPlanElement(Types.PARAMETRIZATION, this.parametrisation,false);
        this.fireEvent(ModelEventType.ELEMENT_DELETED, this.parametrisation);
    }
}
