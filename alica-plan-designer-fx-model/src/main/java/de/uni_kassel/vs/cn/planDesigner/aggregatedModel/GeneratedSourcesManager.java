package de.uni_kassel.vs.cn.planDesigner.aggregatedModel;

import de.uni_kassel.vs.cn.planDesigner.alica.*;
import de.uni_kassel.vs.cn.planDesigner.alica.configuration.Configuration;
import de.uni_kassel.vs.cn.planDesigner.alica.configuration.WorkspaceManager;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by marci on 31.05.17.
 */
public class GeneratedSourcesManager {

    private Configuration configuration;

    private Map<State, Integer> stateCheckingCode;

    private Map<Transition, Integer> transitionConditionCode;

    private Map<Condition, Integer> conditionCode;

    private static GeneratedSourcesManager INSTANCE;

    public static GeneratedSourcesManager get() {
        if (INSTANCE == null) {
            INSTANCE = new GeneratedSourcesManager();
        }
        return INSTANCE;
    }

    private GeneratedSourcesManager() {
        configuration = new WorkspaceManager().getActiveWorkspace().getConfiguration();
        stateCheckingCode = new HashMap<>();
        transitionConditionCode = new HashMap<>();
        conditionCode = new HashMap<>();
    }

    public String getIncludeDir() {
        String expressionValidatorsPath = configuration.getExpressionValidatorsPath();
        return expressionValidatorsPath + "/include/";
    }

    public String getSrcDir() {
        String expressionValidatorsPath = configuration.getExpressionValidatorsPath();
        return expressionValidatorsPath + "/src/";
    }

    public List<File> getAllGeneratedFilesForAbstractPlan(AbstractPlan abstractPlan) {
        String destinationPath = abstractPlan.getDestinationPath();
        if (destinationPath.lastIndexOf(File.separator) != destinationPath.charAt(destinationPath.length() - 1)
                || destinationPath.lastIndexOf('.') > destinationPath.lastIndexOf(File.separator))
        {
            destinationPath = destinationPath.substring(0, destinationPath.lastIndexOf(File.separator)) + File.separator;
        }

        File header = new File(getIncludeDir() + destinationPath + "/" +
                (abstractPlan instanceof  Plan ? (abstractPlan.getName() + abstractPlan.getId()) : abstractPlan.getName()) + ".h");
        File source = new File(getSrcDir() + destinationPath + "/" +
                (abstractPlan instanceof  Plan ? (abstractPlan.getName() + abstractPlan.getId()) : abstractPlan.getName()) + ".cpp");

        if (abstractPlan instanceof Plan) {
            File headerConstraint = new File(getIncludeDir() + destinationPath +
                    "/constraints/" + abstractPlan.getName() + abstractPlan.getId() + "Constraints.h");
            File sourceConstraint = new File(getSrcDir() + destinationPath +
                    "/constraints/" + abstractPlan.getName() + abstractPlan.getId() + "Constraints.cpp");

            return Arrays.asList(header, source, headerConstraint, sourceConstraint);
        } else {
            return Arrays.asList(header, source);
        }
    }

    /**
     * delegate {@link Map#put(Object, Object)}
     * @param transition
     * @param lineNumber
     */
    public void putTransitionLines(Transition transition, Integer lineNumber) {
        transitionConditionCode.put(transition, lineNumber);
    }

    public int getLineNumberForTransition(Transition transition) {
        return transitionConditionCode.get(transition);
    }

    /**
     * delegate {@link Map#put(Object, Object)}
     * @param state
     * @param lineNumber
     */
    public void putStateCheckingLines(State state, Integer lineNumber) {
        stateCheckingCode.put(state, lineNumber);
    }

    /**
     * if code has not been generated this method throws an exception
     * @param state
     * @return
     */
    public int getLineNumberForState(State state) {
       return stateCheckingCode.get(state);
    }

    /**
     *
     * @param state
     * @return the file where the state code is located
     */
    public File getFileForState(State state) {
        List<File> allGeneratedFilesForAbstractPlan = getAllGeneratedFilesForAbstractPlan(state.getInPlan());
        return allGeneratedFilesForAbstractPlan.stream()
                .filter(e -> e.getAbsolutePath().endsWith("Constraints.cpp"))
                .findFirst().orElse(null);
    }

    /**
     *
     * @param transition
     * @return
     */
    public File getFileForTransition(Transition transition) {
        Plan plan = transition.getOutState().getInPlan();
        List<File> allGeneratedFilesForAbstractPlan = getAllGeneratedFilesForAbstractPlan(plan);
        return allGeneratedFilesForAbstractPlan
                .stream()
                .filter(e -> e.getAbsolutePath().endsWith(plan.getName() + plan.getId() + ".cpp"))
                .findFirst().orElse(null);
    }

    public void putConditionLines(Condition condition, int lineNumber) {
        conditionCode.put(condition, lineNumber);
    }

    public int getLineNumberForCondition(Condition condition) {
        return conditionCode.get(condition);
    }
}
