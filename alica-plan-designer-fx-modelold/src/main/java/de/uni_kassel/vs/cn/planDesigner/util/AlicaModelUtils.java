package de.uni_kassel.vs.cn.generator;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.*;

import java.util.List;

/**
 * General purpose utility class
 */
public class AlicaModelUtils {
    // Characters that are illegal as C++ identifiers are not allowed to be names of any ALICA object
    public static final String forbiddenCharacters = ".*[\\./\\*\\\\$§?\\[\\]!{}\\-äüö#\"%~'ÄÖÜß@]+.*";

    /**
     * Checks whether a given string contains illegal characters
     * @param toCheck the string to check (NEVER <code>null</code>)
     * @return the result
     */
    public static boolean containsIllegalCharacter(String toCheck) {
        return toCheck.matches(forbiddenCharacters);
    }

    public static void addParametrisations(AbstractPlan abstractPlan, State state) {
        List<Variable> variables = null;
        if (abstractPlan instanceof Plan) {
            variables = ((Plan) abstractPlan).getVars();
        }

        if (abstractPlan instanceof Behaviour) {
            variables = ((Behaviour)abstractPlan).getVars();
        }

        if (variables != null) {
            variables.forEach(var -> {
                Parametrisation parametrisation = EMFModelUtils.getAlicaFactory().createParametrisation();
                parametrisation.setSubplan(abstractPlan);
                parametrisation.setSubvar(var);
                parametrisation.setVar(null);
                state.getParametrisation().add(parametrisation);
            });
        }
    }
}
