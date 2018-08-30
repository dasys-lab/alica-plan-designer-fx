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

    public ArrayList<Long> incompleteTasksInEntryPoints = new ArrayList<>();

    public ArrayList<Long> incompletePlansInPlanTypes = new ArrayList<>();

    public ArrayList<Long> incompletePlanElementsInStates = new ArrayList<>();

    public ArrayList<Long> incompleteStatesInTransitions = new ArrayList<>();

    public ArrayList<Long> incompleteSyncronizationsInTransitions = new ArrayList<>();

    public void setDefaultTaskId(long defaultTaskId) {
        this.defaultTaskId = defaultTaskId;
    }

    public void addIncompleteTaskIdOfEntryPoint(long incompleteTaskId) {
        if(!incompleteTasksInEntryPoints.contains(incompleteTaskId)) {
            incompleteTasksInEntryPoints.add(incompleteTaskId);
        }
    }

    public void addIncompletePlanInPlantype(long incompletePlanId) {
        if (!incompletePlansInPlanTypes.contains(incompletePlanId)) {
            incompletePlansInPlanTypes.add(incompletePlanId);
        }
    }

    public void addIncompletePlanElementInState(long incompletePlanId) {
        if (!incompletePlanElementsInStates.contains(incompletePlanId)) {
            incompletePlanElementsInStates.add(incompletePlanId);
        }
    }

    public void addIncompleteStateInTransition(long incompleteStateId) {
        if (!incompleteStatesInTransitions.contains(incompleteStateId)) {
            incompleteStatesInTransitions.add(incompleteStateId);
        }
    }

    public void addIncompleteSynchronizationsInTransitions(long incompleteSynchronizationId) {
        if (!incompleteSyncronizationsInTransitions.contains(incompleteSynchronizationId)) {
            incompleteSyncronizationsInTransitions.add(incompleteSynchronizationId);
        }
    }
}
