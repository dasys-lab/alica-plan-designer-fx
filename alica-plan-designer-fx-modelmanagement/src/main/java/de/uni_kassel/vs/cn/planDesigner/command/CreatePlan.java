package de.uni_kassel.vs.cn.planDesigner.command;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelModificationQuery;

public class CreatePlan extends AbstractCommand {

    protected Plan plan;

    public CreatePlan(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager);
        if (mmq.getModelElementType().equals("plan")) {
            this.plan = new Plan();
            this.plan.setName(mmq.getName());
            this.plan.setRelativeDirectory(modelManager.makeRelativePlansDirectory(mmq.getAbsoluteDirectory(), plan.getName()+ ".pml"));
        }
    }

    @Override
    public void doCommand() {
        modelManager.addPlan(plan);
//        planElementMap.put(planElement.getId(), planElement);
//        fireCreationEvent(planElement);
    }

    @Override
    public void undoCommand() {

    }

    @Override
    public String getCommandString() {
        return "Create " + plan.getName();
    }
}
