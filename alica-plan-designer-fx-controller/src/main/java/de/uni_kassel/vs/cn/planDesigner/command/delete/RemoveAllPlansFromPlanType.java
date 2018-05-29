package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.AnnotatedPlan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanType;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marci on 17.03.17.
 */
public class RemoveAllPlansFromPlanType extends AbstractCommand<PlanType> {

    private List<AnnotatedPlan> backupPlans;

    public RemoveAllPlansFromPlanType(PlanType element) {
        super(element, element);
    }

    @Override
    public void doCommand() {
        if (backupPlans == null) {
            backupPlans = new ArrayList<>();
        }
        backupPlans.addAll(getElementToEdit().getPlans());
        getElementToEdit().getPlans().clear();

    }

    @Override
    public void undoCommand() {
        getElementToEdit().getPlans().addAll(backupPlans);
    }

    @Override
    public String getCommandString() {
        return "Remove all plans from plantype " + getElementToEdit().getName();
    }
}
