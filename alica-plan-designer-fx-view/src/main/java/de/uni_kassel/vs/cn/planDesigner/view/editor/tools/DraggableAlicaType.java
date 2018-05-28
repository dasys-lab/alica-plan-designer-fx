package de.uni_kassel.vs.cn.planDesigner.view.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.alica.*;
import org.eclipse.emf.ecore.EObject;

import java.util.stream.Stream;

/**
 * Created by marci on 23.11.16.
 */
public enum DraggableAlicaType {
    PLAN(Plan.class),
    PLANNING_PROBLEM(PlanningProblem.class),
    PLANTYPE(PlanType.class),
    STATE(State.class),
    BEHAVIOUR(Behaviour.class),
    TASK(Task.class),
    ENTRY_POINT(EntryPoint.class),
    SUCCESS_STATE(SuccessState.class),
    FAILURE_STATE(FailureState.class),
    SYNCHRONISATION(Synchronisation.class),
    PRECONDITION(PreCondition.class),
    POSTCONDITION(PostCondition.class),
    RUNTIMECONDITION(RuntimeCondition.class),
    TRANSITION(Transition.class);

    private Class<? extends EObject> associatedClass;

    DraggableAlicaType(Class<? extends EObject> planClass) {
        associatedClass = planClass;
    }

    public Class<? extends EObject> getAssociatedClass() {
        return associatedClass;
    }

    public static DraggableAlicaType getDraggableTypeByClass(Class<? extends EObject> eClass) {
        return Stream
                .of(DraggableAlicaType.values())
                .filter(e -> e.getAssociatedClass().equals(eClass))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        try {
            return getClass().getClassLoader().loadClass(associatedClass.getName()).getSimpleName();
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
