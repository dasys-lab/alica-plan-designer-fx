package de.unikassel.vs.alica.generator;

import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.alicamodel.State;

/**
 * This interface defines the methods that a constraint plugin must implement.
 */
public interface IConstraintCodeGenerator {
    String constraintPlanCheckingMethods(Plan plan);
    String expressionsPlanCheckingMethods(Plan plan);
    String constraintStateCheckingMethods(State state);
    String expressionsStateCheckingMethods(State state);
}
