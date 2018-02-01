package de.uni_kassel.vs.cn.defaultPlugin;

import de.uni_kassel.vs.cn.generator.IConstraintCodeGenerator;
import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.State;

import java.util.Map;

/**
 * Glue Code for calling {@link DefaultTemplate}.
 */
public class DefaultConstraintCodeGenerator implements IConstraintCodeGenerator {
    private DefaultTemplate defaultTemplate;

    public DefaultConstraintCodeGenerator() {
        defaultTemplate = new DefaultTemplate();
    }

    public void setProtectedRegions(Map<String, String> protectedRegions) {
        defaultTemplate.setProtectedRegions(protectedRegions);
    }

    @Override
    public String constraintPlanCheckingMethods(Plan plan) {
        return defaultTemplate.constraintPlanCheckingMethods(plan);
    }

    @Override
    public String expressionsPlanCheckingMethods(Plan plan) {
        return defaultTemplate.expressionsPlanCheckingMethods(plan);
    }

    @Override
    public String constraintStateCheckingMethods(State state) {
        return defaultTemplate.constraintStateCheckingMethods(state);
    }

    @Override
    public String expressionsStateCheckingMethods(State state) {
        return defaultTemplate.expressionsStateCheckingMethods(state);
    }
}
