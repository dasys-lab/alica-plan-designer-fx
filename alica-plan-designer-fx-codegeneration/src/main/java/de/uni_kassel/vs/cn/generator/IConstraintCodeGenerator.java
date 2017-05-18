package de.uni_kassel.vs.cn.generator;

import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.State;

/**
 * Created by marci on 18.05.17.
 */
public interface IConstraintCodeGenerator {
    String constraintPlanCheckingMethods(Plan plan);
    String expressionsPlanCheckingMethods(Plan plan);
    String constraintStateCheckingMethods(State state);
    String expressionsStateCheckingMethods(State state);
}
