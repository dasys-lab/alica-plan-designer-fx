package de.uni_kassel.vs.cn.generator;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.State;

/**
 * This interface defines the methods that a constraint plugin must implement.
 */
public interface IConstraintCodeGenerator {
    String constraintPlanCheckingMethods(Plan plan);
    String expressionsPlanCheckingMethods(Plan plan);
    String constraintStateCheckingMethods(State state);
    String expressionsStateCheckingMethods(State state);
}
