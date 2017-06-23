package de.uni_kassel.vs.cn.generator.cpp;

import de.uni_kassel.vs.cn.generator.IConstraintCodeGenerator;
import de.uni_kassel.vs.cn.generator.IGenerator;
import de.uni_kassel.vs.cn.generator.plugin.PluginManager;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.GeneratedSourcesManager;
import de.uni_kassel.vs.cn.planDesigner.alica.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by marci on 12.05.17.
 */
public class CPPGeneratorImpl implements IGenerator {

    private static final Logger LOG = LogManager.getLogger(CPPGeneratorImpl.class);
    private XtendTemplates xtendTemplates;

    private GeneratedSourcesManager generatedSourcesManager;
    private String formatter;

    public CPPGeneratorImpl() {
        generatedSourcesManager = GeneratedSourcesManager.get();
        xtendTemplates = new XtendTemplates();
    }

    @Override
    public void setProtectedRegions(Map<String, String> protectedRegions) {
        xtendTemplates.setProtectedRegions(protectedRegions);
    }

    @Override
    public void createBehaviourCreator(List<Behaviour> behaviours) {
        String headerPath = generatedSourcesManager.getIncludeDir() + "ConditionCreator.h";
        String fileContentHeader = xtendTemplates.behaviourCreatorHeader();
        writeSourceFile(headerPath, fileContentHeader);

        formatFile(headerPath);

        String srcPath = generatedSourcesManager.getSrcDir() + "ConditionCreator.cpp";
        String fileContentSource = xtendTemplates.behaviourCreatorSource(behaviours);
        writeSourceFile(srcPath, fileContentSource);

        formatFile(srcPath);
    }

    @Override
    public void createBehaviour(Behaviour behaviour) {
        useTemplateAndSaveResults(generatedSourcesManager.getSrcDir() + behaviour.getDestinationPath() + "/" + behaviour.getName() + ".cpp",
                generatedSourcesManager.getIncludeDir() + behaviour.getDestinationPath() + "/" + behaviour.getName() + ".h",
                behaviour,
                xtendTemplates::behaviourHeader,
                xtendTemplates::behaviourSource);
    }

    @Override
    public void createConditionCreator(List<Plan> plans, List<Condition> conditions) {
        String headerPath = generatedSourcesManager.getIncludeDir() + "ConditionCreator.h";
        String fileContentHeader = xtendTemplates.conditionCreatorHeader();
        writeSourceFile(headerPath, fileContentHeader);

        formatFile(headerPath);

        String srcPath = generatedSourcesManager.getSrcDir() + "ConditionCreator.cpp";
        String fileContentSource = xtendTemplates.conditionCreatorSource(plans, conditions);
        writeSourceFile(srcPath, fileContentSource);

        formatFile(srcPath);
    }

    private void writeSourceFile(String filePath, String fileContent) {
        try {
            Files.write(Paths.get(filePath), fileContent.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            LOG.error("Couldn't write source file "
                    + filePath + " with content size " + fileContent
                    .getBytes(StandardCharsets.UTF_8).length, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createConstraintCreator(List<Plan> plans, List<Condition> conditions) {
        String headerPath = generatedSourcesManager.getIncludeDir() + "ConstraintCreator.h";
        String fileContentHeader = xtendTemplates.constraintCreatorHeader();
        writeSourceFile(headerPath, fileContentHeader);

        formatFile(headerPath);

        String srcPath = generatedSourcesManager.getSrcDir() + "ConstraintCreator.cpp";
        String fileContentSource = xtendTemplates.constraintCreatorSource(plans, conditions);
        writeSourceFile(srcPath, fileContentSource);

        formatFile(srcPath);
    }

    @Override
    public void createConstraints(List<Plan> plans) {
        for (Plan plan : plans) {
            String headerPath = generatedSourcesManager.getIncludeDir() + plan.getDestinationPath() +
                    "/" + "constraints/" + plan.getName() + plan.getId() + "Constraints.h";
            String fileContentHeader = xtendTemplates.constraintsHeader(plan);
            writeSourceFile(headerPath, fileContentHeader);

            formatFile(headerPath);

            String srcPath = generatedSourcesManager.getSrcDir() + plan.getDestinationPath() +
                    "/" + "constraints/" + plan.getName() + plan.getId() + "Constraints.cpp";
            String fileContentSource = xtendTemplates.constraintsSource(plan, getActiveConstraintCodeGenerator());
            writeSourceFile(srcPath, fileContentSource);
            formatFile(srcPath);

            for (State inPlan : plan.getStates()) {
                try {
                    LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(srcPath));
                    while (lineNumberReader.ready()) {
                        if (lineNumberReader.readLine().contains("// State: " + inPlan.getName())) {
                            generatedSourcesManager.putStateCheckingLines(inPlan, lineNumberReader.getLineNumber());
                            break;
                        }
                    }
                    lineNumberReader.close();
                } catch (IOException e) {
                    LOG.error("Could not open/read lines for " + srcPath, e);
                }
            }
        }

    }

    @Override
    public void createPlans(List<Plan> plans) {
        for (Plan plan : plans) {
            String headerPath = generatedSourcesManager.getIncludeDir() + plan.getDestinationPath() +
                    "/" + plan.getName() + plan.getId() + ".h";
            String fileContentHeader = xtendTemplates.planHeader(plan);
            writeSourceFile(headerPath, fileContentHeader);

            formatFile(headerPath);

            String srcPath = generatedSourcesManager.getSrcDir() + plan.getDestinationPath() +
                    "/" + plan.getName() + plan.getId() + ".cpp";
            String fileContentSource = xtendTemplates.planSource(plan, getActiveConstraintCodeGenerator());
            writeSourceFile(srcPath, fileContentSource);

            formatFile(srcPath);

            RuntimeCondition runtimeCondition = plan.getRuntimeCondition();
            if (runtimeCondition != null) {
                try {
                    LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(srcPath));
                    while (lineNumberReader.ready()) {
                        if (lineNumberReader.readLine().contains("/*PROTECTED REGION ID(" + runtimeCondition.getId() + ") ENABLED START*/")) {
                            generatedSourcesManager.putConditionLines(runtimeCondition, lineNumberReader.getLineNumber());
                            break;
                        }

                    }
                    lineNumberReader.close();
                } catch (IOException e) {
                    LOG.error("Could not open/read lines for " + srcPath, e);
                }
            }

            PreCondition preCondition = plan.getPreCondition();
            if (preCondition != null) {
                try {
                    LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(srcPath));
                    while (lineNumberReader.ready()) {
                        if (lineNumberReader.readLine().contains("/*PROTECTED REGION ID(" + preCondition.getId() + ") ENABLED START*/")) {
                            generatedSourcesManager.putConditionLines(preCondition, lineNumberReader.getLineNumber());
                            break;
                        }

                    }
                    lineNumberReader.close();
                } catch (IOException e) {
                    LOG.error("Could not open/read lines for " + srcPath, e);
                }
            }

            for (Transition inPlan : plan.getTransitions()) {
                try {
                    LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(srcPath));
                    while (lineNumberReader.ready()) {
                        if (lineNumberReader.readLine().contains("/*PROTECTED REGION ID(" + inPlan.getId() + ") ENABLED START*/")) {
                            generatedSourcesManager.putTransitionLines(inPlan, lineNumberReader.getLineNumber());
                            break;
                        }

                    }
                    lineNumberReader.close();
                } catch (IOException e) {
                    LOG.error("Could not open/read lines for " + srcPath, e);
                }
            }
        }
    }

    @Override
    public void createUtilityFunctionCreator(List<Plan> plans) {
        String headerPath = generatedSourcesManager.getIncludeDir() + "UtilityFunctionCreator.h";
        XtendTemplates xtendTemplates = new XtendTemplates();
        String fileContentHeader = xtendTemplates.utilityFunctionCreatorHeader();
        writeSourceFile(headerPath, fileContentHeader);

        formatFile(headerPath);

        String srcPath = generatedSourcesManager.getSrcDir() + "UtilityFunctionCreator.cpp";
        String fileContentSource = xtendTemplates.utilityFunctionCreatorSource(plans);
        writeSourceFile(srcPath, fileContentSource);

        formatFile(srcPath);
    }

    @Override
    public void createDomainCondition() {
        String headerPath = generatedSourcesManager.getIncludeDir() + "DomainCondition.h";
        XtendTemplates xtendTemplates = new XtendTemplates();
        String fileContentHeader = xtendTemplates.domainConditionHeader();
        writeSourceFile(headerPath, fileContentHeader);

        formatFile(headerPath);

        String srcPath = generatedSourcesManager.getSrcDir() + "DomainCondition.cpp";
        String fileContentSource = xtendTemplates.domainConditionSource();
        writeSourceFile(srcPath, fileContentSource);

        formatFile(srcPath);
    }

    @Override
    public void createDomainBehaviour() {
        String headerPath = generatedSourcesManager.getIncludeDir() + "DomainBehaviour.h";
        XtendTemplates xtendTemplates = new XtendTemplates();
        String fileContentHeader = xtendTemplates.domainBehaviourHeader();
        writeSourceFile(headerPath, fileContentHeader);

        formatFile(headerPath);

        String srcPath = generatedSourcesManager.getSrcDir() + "DomainBehaviour.cpp";
        String fileContentSource = xtendTemplates.domainBehaviourSource();
        writeSourceFile(srcPath, fileContentSource);

        formatFile(srcPath);
    }

    @Override
    public void setFormatter(String formatter) {
        this.formatter = formatter;
    }

    @Override
    public IConstraintCodeGenerator getActiveConstraintCodeGenerator() {
        return PluginManager.getInstance().getActivePlugin().getConstraintCodeGenerator();
    }

    private <T> void useTemplateAndSaveResults(String sourcePath, String headerPath, T objectToInteractWith,
                                               Function<T, String> templateForHeader, Function<T, String> templateForSource) {
        String fileContentHeader = templateForHeader.apply(objectToInteractWith);
        writeSourceFile(headerPath, fileContentHeader);
        formatFile(headerPath);


        String fileContentSource = templateForSource.apply(objectToInteractWith);
        writeSourceFile(sourcePath, fileContentSource);
        formatFile(sourcePath);
    }

    private void formatFile(String fileName) {
        if (formatter != null && formatter.length() > 0) {
            try {
                Runtime.getRuntime().exec(formatter + " -i "
                        + fileName).waitFor();
            } catch (IOException | InterruptedException e) {
                LOG.error("An error occurred while formatting generated sources", e);
                throw new RuntimeException(e);
            }
        } else {
            LOG.warn("Generated files are not formatted because no formatter is configured");
        }
    }
}
