package de.uni_kassel.vs.cn.planDesigner.modelmanagement;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Task;

import java.util.ArrayList;

public class ParsedModelReferences {
    // SINGLETON
    private static volatile ParsedModelReferences instance;
    public static ParsedModelReferences getInstance() {
        if (instance == null) {
            synchronized (ParsedModelReferences.class) {
                if (instance == null) {
                    instance = new ParsedModelReferences();
                }
            }
        }
        return instance;
    }

    public long defaultTaskId;

    public ArrayList<Task> incompleteTasks = new ArrayList<>();

    public ArrayList<Plan> incompletePlansInPlantypes = new ArrayList<>();

    public void setDefaultTaskId(long defaultTaskId) {
        this.defaultTaskId = defaultTaskId;
    }

    public void addIncompleteTask(Task incompleteTask) {
        incompleteTasks.add(incompleteTask);
    }

    public void addIncompletePlanInPlanTypes(Plan incompletePlan) {incompletePlansInPlantypes.add(incompletePlan);}


}
