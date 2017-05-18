package de.uni_kassel.vs.cn.generator.cpp;

import de.uni_kassel.vs.cn.generator.IConstraintCodeGenerator;
import de.uni_kassel.vs.cn.generator.IGenerator;
import de.uni_kassel.vs.cn.planDesigner.alica.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alica.Condition;
import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.configuration.Configuration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;

/**
 * Created by marci on 12.05.17.
 */
public class CPPGeneratorImpl implements IGenerator {

    private XtendTemplates xtendTemplates;

    public CPPGeneratorImpl() {
        xtendTemplates = new XtendTemplates();
    }

    static <T> void useTemplateAndSaveResults(String sourcePath, String headerPath, T objectToInteractWith,
                                              Function<T, String> templateForHeader, Function<T, String> templateForSource) {
        String fileContentHeader = templateForHeader.apply(objectToInteractWith);
        try {
            Files.write(Paths.get(headerPath), fileContentHeader.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            // TODO handle exception
            throw new RuntimeException(e);
        }

        String fileContentSource = templateForSource.apply(objectToInteractWith);
        try {
            Files.write(Paths.get(sourcePath), fileContentSource.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            // TODO handle exception
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createBehaviourCreator(List<Behaviour> behaviours) {
        String headerPath = getIncludeDir() + "ConditionCreator.h";
        String fileContentHeader = xtendTemplates.behaviourCreatorHeader();
        try {
            Files.write(Paths.get(headerPath), fileContentHeader.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            // TODO handle exception
            throw new RuntimeException(e);
        }

        String srcPath = getSrcDir() + "ConditionCreator.cpp";
        String fileContentSource = xtendTemplates.behaviourCreatorSource(behaviours);
        try {
            Files.write(Paths.get(srcPath), fileContentSource.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            // TODO handle exception
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createBehaviour(Behaviour behaviour) {
        useTemplateAndSaveResults(getSrcDir() + behaviour.getDestinationPath() + "/" + behaviour.getName() + ".cpp",
                getIncludeDir() + behaviour.getDestinationPath() + "/" + behaviour.getName() + ".h",
                behaviour,
                xtendTemplates::behaviourHeader,
                xtendTemplates::behaviourSource);
    }

    @Override
    public void createConditionCreator(List<Plan> plans, List<Condition> conditions) {
        String headerPath = getIncludeDir() + "ConditionCreator.h";
        String fileContentHeader = xtendTemplates.conditionCreatorHeader();
        try {
            Files.write(Paths.get(headerPath), fileContentHeader.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            // TODO handle exception
            throw new RuntimeException(e);
        }

        String srcPath = getSrcDir() + "ConditionCreator.cpp";
        String fileContentSource = xtendTemplates.conditionCreatorSource(plans, conditions);
        try {
            Files.write(Paths.get(srcPath), fileContentSource.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            // TODO handle exception
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createConstraintCreator(List<Plan> plans, List<Condition> conditions) {
        String headerPath = getIncludeDir() + "ConstraintCreator.h";
        String fileContentHeader = xtendTemplates.constraintCreatorHeader();
        try {
            Files.write(Paths.get(headerPath), fileContentHeader.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            // TODO handle exception
            throw new RuntimeException(e);
        }

        String srcPath = getSrcDir() + "ConstraintCreator.cpp";
        String fileContentSource = xtendTemplates.constraintCreatorSource(plans, conditions);
        try {
            Files.write(Paths.get(srcPath), fileContentSource.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            // TODO handle exception
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createConstraints(List<Plan> plans) {
        for (Plan plan : plans) {
            String headerPath = getIncludeDir() + plan.getDestinationPath() +
                    "/" + plan.getName() + plan.getId() + "Constraints.h";
            String fileContentHeader = xtendTemplates.constraintsHeader(plan);
            try {
                Files.write(Paths.get(headerPath), fileContentHeader.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                // TODO handle exception
                throw new RuntimeException(e);
            }

            String srcPath = getSrcDir() + plan.getDestinationPath() +
                    "/" + plan.getName() + plan.getId() + "Constraints.cpp";
            String fileContentSource = xtendTemplates.constraintsSource(plan, getActiveConstraintCodeGenerator());
            try {
                Files.write(Paths.get(srcPath), fileContentSource.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                // TODO handle exception
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void createPlans(List<Plan> plans) {
        for (Plan plan : plans) {
            String headerPath = getIncludeDir() + plan.getDestinationPath() +
                    "/" + plan.getName() + plan.getId() + ".h";
            String fileContentHeader = xtendTemplates.planHeader(plan);
            try {
                Files.write(Paths.get(headerPath), fileContentHeader.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                // TODO handle exception
                throw new RuntimeException(e);
            }

            String srcPath = getSrcDir() + plan.getDestinationPath() +
                    "/" + plan.getName() + plan.getId() + ".cpp";
            String fileContentSource = xtendTemplates.planSource(plan, getActiveConstraintCodeGenerator());
            try {
                Files.write(Paths.get(srcPath), fileContentSource.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                // TODO handle exception
                throw new RuntimeException(e);
            }
        }
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
        String headerPath = getIncludeDir() + "DomainCondition.h";
        XtendTemplates xtendTemplates = new XtendTemplates();
        String fileContentHeader = xtendTemplates.domainConditionHeader();
        try {
            Files.write(Paths.get(headerPath), fileContentHeader.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            // TODO handle exception
            throw new RuntimeException(e);
        }

        String srcPath = getSrcDir() + "DomainCondition.cpp";
        String fileContentSource = xtendTemplates.domainConditionSource();
        try {
            Files.write(Paths.get(srcPath), fileContentSource.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            // TODO handle exception
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createDomainBehaviour() {
        String headerPath = getIncludeDir() + "DomainBehaviour.h";
        XtendTemplates xtendTemplates = new XtendTemplates();
        String fileContentHeader = xtendTemplates.domainBehaviourHeader();
        try {
            Files.write(Paths.get(headerPath), fileContentHeader.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            // TODO handle exception
            throw new RuntimeException(e);
        }

        String srcPath = getSrcDir() + "DomainBehaviour.cpp";
        String fileContentSource = xtendTemplates.domainBehaviourSource();
        try {
            Files.write(Paths.get(srcPath), fileContentSource.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            // TODO handle exception
            throw new RuntimeException(e);
        }
    }

    @Override
    public IConstraintCodeGenerator getActiveConstraintCodeGenerator() {
        // TODO plugin
        return null;
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
