package de.uni_kassel.vs.cn.planDesigner.modelmanagement;

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

    public ArrayList<Long> incompleteTasksInEntryPoints = new ArrayList<>(); //done

    public ArrayList<Long> incompletePlansInPlanTypes = new ArrayList<>(); //done

    public ArrayList<Long> incompleteAbstractPlansInStates = new ArrayList<>(); // done

    public ArrayList<Long> incompleteStatesInTransitions = new ArrayList<>(); // done

    public ArrayList<Long> incompleteSynchronizationsInTransitions = new ArrayList<>(); // done

    public void setDefaultTaskId(long defaultTaskId) {
        this.defaultTaskId = defaultTaskId;
    }

    public void addIncompleteTaskIdOfEntryPoint(long incompleteTaskId) {
        if(!incompleteTasksInEntryPoints.contains(incompleteTaskId)) {
            incompleteTasksInEntryPoints.add(incompleteTaskId);
        }
    }

    public void addIncompletePlanInPlanType(long incompletePlanId) {
        if (!incompletePlansInPlanTypes.contains(incompletePlanId)) {
            incompletePlansInPlanTypes.add(incompletePlanId);
        }
    }

    public void addIncompleteAbstractPlanInState(long incompletePlanId) {
        if (!incompleteAbstractPlansInStates.contains(incompletePlanId)) {
            incompleteAbstractPlansInStates.add(incompletePlanId);
        }
    }

    public void addIncompleteStateInTransition(long incompleteStateId) {
        if (!incompleteStatesInTransitions.contains(incompleteStateId)) {
            incompleteStatesInTransitions.add(incompleteStateId);
        }
    }

    public void addIncompleteSynchronizationInTransition(long incompleteSynchronizationId) {
        if (!incompleteSynchronizationsInTransitions.contains(incompleteSynchronizationId)) {
            incompleteSynchronizationsInTransitions.add(incompleteSynchronizationId);
        }
    }
}
