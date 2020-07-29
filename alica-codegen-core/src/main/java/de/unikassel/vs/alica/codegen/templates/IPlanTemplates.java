package de.unikassel.vs.alica.codegen.templates;

import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;

public interface IPlanTemplates {
    String constraintPlanPreConditionImpl(Plan plan);
    String constraintPlanPreCondition(Plan plan);
    String constraintPlanRuntimeConditionImpl(Plan plan);
    String constraintPlanRuntimeCondition(Plan plan);
    String utilityFunctionPlan(Plan plan);
    String utilityFunctionPlanImpl(Plan plan);
    String preConditionPlanImpl(Plan plan);
    String preConditionPlan(Plan plan);
    String runtimeConditionPlanImpl(Plan plan);
    String runtimeConditionPlan(Plan plan);
    String constraints(Plan plan);
}
