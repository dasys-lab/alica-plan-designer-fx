package de.uni_kassel.vs.cn.planDesigner.command;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelModificationQuery;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.Types;

import java.io.File;

public class DeletePlan extends AbstractCommand {

    protected Plan plan;

    public DeletePlan(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager);
        String planName = extractName(modelManager, mmq);
        if (mmq.getModelElementType().equals(Types.PLAN)) {
           for(Plan currentPlan: modelManager.getPlans()) {
                if (currentPlan.getName().equals(planName)) {
                    plan = currentPlan;
                    break;
                }
           }
        }
    }

    @Override
    public void doCommand() {
        modelManager.removePlan(plan);
    }

    @Override
    public void undoCommand() {
        modelManager.addPlan(plan);
    }

    private String extractName(ModelManager modelManager, ModelModificationQuery mmq) {
        String planName = mmq.getAbsoluteDirectory().replace(modelManager.getPlansPath(), "").replace(".pml", "");
        int index = planName.lastIndexOf(File.separator);
        if (index != -1) {
            planName = planName.substring(index + 1);
        }
        return planName;
    }
}
