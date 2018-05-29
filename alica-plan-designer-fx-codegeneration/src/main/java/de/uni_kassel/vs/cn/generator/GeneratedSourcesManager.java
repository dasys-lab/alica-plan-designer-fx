package de.uni_kassel.vs.cn.generator;

import de.uni_kassel.vs.cn.planDesigner.alica.*;
import de.uni_kassel.vs.cn.planDesigner.configuration.Configuration;
import de.uni_kassel.vs.cn.planDesigner.configuration.ConfigurationManager;

import java.io.File;
import java.util.*;
import java.nio.file.Paths;

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
        configuration = ConfigurationManager.getInstance().getActiveConfiguration();
        stateCheckingCode = new HashMap<>();
        transitionConditionCode = new HashMap<>();
        conditionCode = new HashMap<>();
    }

    public String getIncludeDir() {
        return Paths.get(configuration.getGenSrcPath(), "include").toString();
    }

    public String getSrcDir() {
        return Paths.get(configuration.getGenSrcPath(), "src").toString();
    }

    public List<File> getAllGeneratedFilesForAbstractPlan(AbstractPlan abstractPlan) {
        String destinationPath = abstractPlan.getDestinationPath();
        if (destinationPath.lastIndexOf('.') > destinationPath.lastIndexOf(File.separator)) {
            destinationPath = destinationPath.substring(0, destinationPath.lastIndexOf(File.separator) + 1);
        }

        String headerFilename = (abstractPlan instanceof Plan ? (abstractPlan.getName() + abstractPlan.getId()) : abstractPlan.getName()) + ".h";
        String sourceFilename = (abstractPlan instanceof Plan ? (abstractPlan.getName() + abstractPlan.getId()) : abstractPlan.getName()) + ".cpp";
        File headerFile = new File(Paths.get(getIncludeDir(), destinationPath, headerFilename).toString());
        File sourceFile = new File(Paths.get(getSrcDir(), destinationPath, sourceFilename).toString());

        List<File> generatedFiles = new ArrayList<>();
        generatedFiles.add(headerFile);
        generatedFiles.add(sourceFile);

        if (abstractPlan instanceof Plan) {
            File constraintHeaderFile = new File(Paths.get(getIncludeDir(), destinationPath, "constraints", abstractPlan.getName() + abstractPlan.getId() + "Constraints.h").toString());
            File constraintSourceFile = new File(Paths.get(getSrcDir(), destinationPath, "constraints", abstractPlan.getName() + abstractPlan.getId() + "Constraints.cpp").toString());
            generatedFiles.add(constraintHeaderFile);
            generatedFiles.add(constraintSourceFile);
        }
        return generatedFiles;
    }

    /**
     * delegate {@link Map#put(Object, Object)}
     *
     * @param transition
     * @param lineNumber
     */
    public void putTransitionLines(Transition transition, Integer lineNumber) {
        transitionConditionCode.put(transition, lineNumber);
    }

    /**
     * if code has not been generated this method returns 0
     *
     * @param transition
     * @return line number of generated code
     */
    public int getLineNumberForTransition(Transition transition) {
        if (transitionConditionCode.containsKey(transition)) {
            return transitionConditionCode.get(transition);
        } else {
            return 0;
        }
    }

    /**
     * delegate {@link Map#put(Object, Object)}
     *
     * @param state
     * @param lineNumber
     */
    public void putStateCheckingLines(State state, Integer lineNumber) {
        stateCheckingCode.put(state, lineNumber);
    }

    /**
     * if code has not been generated this method returns 0
     *
     * @param state
     * @return line number of generated code
     */
    public int getLineNumberForState(State state) {
        if (stateCheckingCode.containsKey(state)) {
            return stateCheckingCode.get(state);
        } else {
            return 0;
        }
    }

    /**
     * @param state
     * @return the file where the state code is located
     */
    public File getFileForState(State state) {
        List<File> allGeneratedFilesForAbstractPlan = getAllGeneratedFilesForAbstractPlan(state.getParentPlan());
        return allGeneratedFilesForAbstractPlan.stream()
                .filter(e -> e.getAbsolutePath().endsWith("Constraints.cpp"))
                .findFirst().orElse(null);
    }

    /**
     * @param transition
     * @return
     */
    public File getFileForTransition(Transition transition) {
        Plan plan = transition.getOutState().getParentPlan();
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
