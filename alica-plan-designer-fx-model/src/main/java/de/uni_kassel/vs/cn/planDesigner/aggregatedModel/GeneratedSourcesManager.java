package de.uni_kassel.vs.cn.planDesigner.aggregatedModel;

import de.uni_kassel.vs.cn.planDesigner.alica.AbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.configuration.Configuration;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created by marci on 31.05.17.
 */
public class GeneratedSourcesManager {

    private Configuration configuration;

    public GeneratedSourcesManager() {
        configuration = new Configuration();
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
        File header = new File(getIncludeDir() + abstractPlan.getDestinationPath() + "/" +
                (abstractPlan instanceof  Plan ? (abstractPlan.getName() + abstractPlan.getId()) : abstractPlan.getName()) + ".h");
        File source = new File(getSrcDir() + abstractPlan.getDestinationPath() + "/" +
                (abstractPlan instanceof  Plan ? (abstractPlan.getName() + abstractPlan.getId()) : abstractPlan.getName()) + ".cpp");

        if (abstractPlan instanceof Plan) {
            File headerConstraint = new File(getIncludeDir() + abstractPlan.getDestinationPath() +
                    "/constraints/" + abstractPlan.getName() + abstractPlan.getId() + "Constraints.h");
            File sourceConstraint = new File(getSrcDir() + abstractPlan.getDestinationPath() +
                    "/constraints/" + abstractPlan.getName() + abstractPlan.getId() + "Constraints.cpp");

            return Arrays.asList(header, source, headerConstraint, sourceConstraint);
        } else {
            return Arrays.asList(header, source);
        }
    }
}
