package de.uni_kassel.vs.cn.generator.cpp;

import de.uni_kassel.vs.cn.generator.Generator;
import de.uni_kassel.vs.cn.planDesigner.alica.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alica.Condition;
import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.configuration.Configuration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by marci on 12.05.17.
 */
public class CPPGeneratorImpl implements Generator {

    @Override
    public void createBehaviourCreator(List<Behaviour> behaviours) {

    }

    @Override
    public void createBehaviour(Behaviour behaviour) {

    }

    @Override
    public void createConditionCreator(List<Condition> conditions) {

    }

    @Override
    public void createCondition(Condition condition) {

    }

    @Override
    public void createConstraintCreator(List<Plan> plans) {

    }

    @Override
    public void createConstraints(List<Plan> plans) {

    }

    @Override
    public void createPlans(List<Plan> plans) {

    }

    @Override
    public void createUtilityFunctionCreator(List<Plan> plans) {
        String headerPath = getIncludeDir() + "UtilityFunctionCreator.h";
        XtendTemplates xtendTemplates = new XtendTemplates();
        String fileContentHeader = xtendTemplates.utilityFunctionCreatorHeader();
        try {
            Files.write(Paths.get(headerPath), fileContentHeader.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            // TODO handle exception
            throw new RuntimeException(e);
        }

        String srcPath = getSrcDir() + "UtilityFunctionCreator.cpp";
        String fileContentSource = xtendTemplates.utilityFunctionCreatorSource(plans);
        try {
            Files.write(Paths.get(srcPath), fileContentSource.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            // TODO handle exception
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createDomainCondition() {

    }

    @Override
    public void createDomainBehaviour() {

    }

    private String getIncludeDir() {
        Configuration configuration = new Configuration();
        String expressionValidatorsPath = configuration.getExpressionValidatorsPath();
        return expressionValidatorsPath + "/include/";
    }

    private String getSrcDir() {
        Configuration configuration = new Configuration();
        String expressionValidatorsPath = configuration.getExpressionValidatorsPath();
        return expressionValidatorsPath + "/src/";
    }
}
