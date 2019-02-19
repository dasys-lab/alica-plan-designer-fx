package de.unikassel.vs.alica.planDesigner.modelmanagement;

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

    public ArrayList<Long> incompleteBendPointTransitions = new ArrayList<>(); // done

    public ArrayList<Long> incompleteAbstractPlanInVariables = new ArrayList<>();

    public ArrayList<Long> incompleteVariableInParametrisations = new ArrayList<>();

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

    public void addIncompleteBendPointTransition(long incompleteTransitionId) {
        if (!incompleteBendPointTransitions.contains(incompleteTransitionId)) {
            incompleteBendPointTransitions.add(incompleteTransitionId);
        }
    }

    public void addIncompleteAbstractPlanInParametrisations(long incompleteAbstractPlanId) {
        if (!incompleteAbstractPlanInVariables.contains(incompleteAbstractPlanId)) {
            incompleteAbstractPlanInVariables.add(incompleteAbstractPlanId);
        }
    }

    public void addIncompleteVariableInParametrisations(long incompleteVariableId) {
        if (!incompleteVariableInParametrisations.contains(incompleteVariableId)) {
            incompleteVariableInParametrisations.add(incompleteVariableId);
        }
    }
}
