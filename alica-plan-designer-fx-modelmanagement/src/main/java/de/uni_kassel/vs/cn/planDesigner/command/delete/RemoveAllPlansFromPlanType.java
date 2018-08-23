package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.AnnotatedPlan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanType;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.Types;

import java.util.ArrayList;
import java.util.List;

public class RemoveAllPlansFromPlanType extends AbstractCommand {

    private List<AnnotatedPlan> backupPlans;
    private PlanType planType;

    public RemoveAllPlansFromPlanType(ModelManager manager, PlanType planType) {
        super(manager);
        this.planType = planType;
        backupPlans = new ArrayList<>();
    }

    @Override
    public void doCommand() {
        backupPlans.addAll(planType.getPlans());
        for(AnnotatedPlan annotatedPlan : backupPlans) {
            modelManager.removePlanElement(Types.ANNOTATEDPLAN, annotatedPlan, planType, false);
        }
        planType.getPlans().clear();

    }

    @Override
    public void undoCommand() {
        planType.getPlans().addAll(backupPlans);
        for(AnnotatedPlan annotatedPlan : backupPlans) {
            modelManager.addPlanElement(Types.ANNOTATEDPLAN, annotatedPlan, planType, false);
        }
    }

}
