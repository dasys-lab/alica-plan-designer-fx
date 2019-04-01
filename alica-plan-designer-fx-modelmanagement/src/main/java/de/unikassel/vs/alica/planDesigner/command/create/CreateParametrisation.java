package de.unikassel.vs.alica.planDesigner.command.create;

import de.unikassel.vs.alica.planDesigner.alicamodel.*;
import de.unikassel.vs.alica.planDesigner.command.Command;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class CreateParametrisation extends Command {

    private final Parametrisation parametrisation;
    private final PlanElement parent;

    public CreateParametrisation(ModelManager manager, ModelModificationQuery mmq) {
        super(manager, mmq);
        this.parent = modelManager.getPlanElement(mmq.getParentId());
        this.parametrisation = createParametrisation();
    }

    protected Parametrisation createParametrisation() {
        Parametrisation parametrisation = new Parametrisation();

        PlanElement tempElement =  modelManager.getPlanElement(mmq.getRelatedObjects().get(Types.PLAN));
        if (tempElement instanceof AnnotatedPlan) {
            parametrisation.setSubPlan(((AnnotatedPlan) tempElement).getPlan());
        } else if (tempElement instanceof Plan) {
            parametrisation.setSubPlan((Plan) tempElement);
        } else {
            parametrisation.setSubPlan((Behaviour) tempElement);
        }
        parametrisation.setSubVariable((Variable) modelManager.getPlanElement(mmq.getRelatedObjects().get(Types.PARAMETRISATION)));
        parametrisation.setVariable((Variable) modelManager.getPlanElement(mmq.getRelatedObjects().get(Types.VARIABLE)));

        return parametrisation;
    }

    @Override
    public void doCommand() {
        if (parent instanceof State) {
            ((State) parent).addParametrisation(this.parametrisation);
        } else if (parent instanceof PlanType) {
            ((PlanType) parent).addParametrisation(this.parametrisation);
        }
        this.modelManager.storePlanElement(Types.PARAMETRISATION, this.parametrisation, false);
        this.fireEvent(ModelEventType.ELEMENT_CREATED, this.parametrisation);
    }

    @Override
    public void undoCommand() {
        if (parent instanceof State) {
            ((State) parent).removeParametrisation(this.parametrisation);
        } else if (parent instanceof PlanType) {
            ((PlanType) parent).removeParametrisation(this.parametrisation);
        }
        modelManager.dropPlanElement(Types.PARAMETRISATION, this.parametrisation,false);
        this.fireEvent(ModelEventType.ELEMENT_DELETED, this.parametrisation);
    }
}
