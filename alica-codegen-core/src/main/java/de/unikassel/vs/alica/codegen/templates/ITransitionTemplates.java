package de.unikassel.vs.alica.codegen.templates;

import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.alicamodel.State;
import de.unikassel.vs.alica.planDesigner.alicamodel.Transition;

public interface ITransitionTemplates {
    String constraintPlanTransitionPreConditionImpl(Transition transition);
    String constraintPlanTransitionPreCondition(Plan plan, Transition transition);
    String transitionPreConditionPlanImpl(Transition transition);
    String transitionPreConditionPlan(State state, Transition transition);
}
