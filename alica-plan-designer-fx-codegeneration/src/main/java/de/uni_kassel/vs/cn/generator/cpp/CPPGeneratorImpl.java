package de.uni_kassel.vs.cn.generator.cpp;

import de.uni_kassel.vs.cn.generator.IConstraintCodeGenerator;
import de.uni_kassel.vs.cn.generator.IGenerator;
import de.uni_kassel.vs.cn.generator.plugin.PluginManager;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.GeneratedSourcesManager;
import de.uni_kassel.vs.cn.planDesigner.alica.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
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
 * Code generator for C++. It uses the {@link XtendTemplates} for creating the code.
 * After this the created strings are written to disk according to {@link GeneratedSourcesManager}.
 * Every file that is written is formatted by the formatter that is set by setFormatter.
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

    /**
     * delegate {@link XtendTemplates#setProtectedRegions(Map)}
     * @param protectedRegions mapping from identifier to content of protected region
     */
    @Override
    public void setProtectedRegions(Map<String, String> protectedRegions) {
        xtendTemplates.setProtectedRegions(protectedRegions);
    }

    @Override
    public void createBehaviourCreator(List<Behaviour> behaviours) {
        String headerPath = Paths.get(generatedSourcesManager.getIncludeDir(), "BehaviourCreator.h").toString();
        String fileContentHeader = xtendTemplates.behaviourCreatorHeader();
        writeSourceFile(headerPath, fileContentHeader);

        formatFile(headerPath);

        String srcPath = Paths.get(generatedSourcesManager.getSrcDir(), "BehaviourCreator.cpp").toString();
        String fileContentSource = xtendTemplates.behaviourCreatorSource(behaviours);
        writeSourceFile(srcPath, fileContentSource);

        formatFile(srcPath);
    }

    @Override
    public void createBehaviour(Behaviour behaviour) {
        String destinationPath = cutDestinationPathToDirectory(behaviour);
        useTemplateAndSaveResults(Paths.get(generatedSourcesManager.getSrcDir(), destinationPath, behaviour.getName() + ".cpp").toString(),
                Paths.get(generatedSourcesManager.getIncludeDir(), destinationPath, behaviour.getName() + ".h").toString(),
                behaviour,
                xtendTemplates::behaviourHeader,
                xtendTemplates::behaviourSource);
    }

    @Override
    public void createConditionCreator(List<Plan> plans, List<Condition> conditions) {
        String headerPath = Paths.get(generatedSourcesManager.getIncludeDir(), "ConditionCreator.h").toString();
        String fileContentHeader = xtendTemplates.conditionCreatorHeader();
        writeSourceFile(headerPath, fileContentHeader);

        formatFile(headerPath);

        String srcPath = Paths.get(generatedSourcesManager.getSrcDir(),"ConditionCreator.cpp").toString();
        String fileContentSource = xtendTemplates.conditionCreatorSource(plans, conditions);
        writeSourceFile(srcPath, fileContentSource);

        formatFile(srcPath);
    }

    /**
     * Small helper for writing source files
     * @param filePath filePath to write to
     * @param fileContent the content to write
     */
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
        String headerPath = Paths.get(generatedSourcesManager.getIncludeDir(), "ConstraintCreator.h").toString();
        String fileContentHeader = xtendTemplates.constraintCreatorHeader();
        writeSourceFile(headerPath, fileContentHeader);

        formatFile(headerPath);

        String srcPath = Paths.get(generatedSourcesManager.getSrcDir(), "ConstraintCreator.cpp").toString();
        String fileContentSource = xtendTemplates.constraintCreatorSource(plans, conditions);
        writeSourceFile(srcPath, fileContentSource);

        formatFile(srcPath);
    }

    /**
     * calls createConstraintsForPlan on each plan
     * @param plans
     */
    @Override
    public void createConstraints(List<Plan> plans) {
        for (Plan plan : plans) {
            createConstraintsForPlan(plan);
        }

    }

    @Override
    public void createConstraintsForPlan(Plan plan) {
        String destinationPathWithoutName = cutDestinationPathToDirectory(plan);
        String constraintHeaderPath = Paths.get(generatedSourcesManager.getIncludeDir(),
                destinationPathWithoutName, "constraints").toString();
        File cstrIncPathOnDisk = new File(constraintHeaderPath);
        if (cstrIncPathOnDisk.exists() == false) {
            cstrIncPathOnDisk.mkdir();
        }
        String headerPath = Paths.get(constraintHeaderPath, plan.getName()+ plan.getId() + "Constraints.h").toString();
        String fileContentHeader = xtendTemplates.constraintsHeader(plan);
        writeSourceFile(headerPath, fileContentHeader);

        formatFile(headerPath);

        String constraintSourcePath = Paths.get(generatedSourcesManager.getSrcDir(), destinationPathWithoutName, "constraints").toString();
        File cstrSrcPathOnDisk = new File(constraintSourcePath);
        if (cstrSrcPathOnDisk.exists() == false) {
            cstrSrcPathOnDisk.mkdir();
        }

        String srcPath = Paths.get(constraintSourcePath, plan.getName() + plan.getId() + "Constraints.cpp").toString();
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

    /**
     * calls createPlan for each plan
     * @param plans list of all plans to generate (usually this should be all plans in workspace)
     */
    @Override
    public void createPlans(List<Plan> plans) {
        for (Plan plan : plans) {
            createPlan(plan);
        }
    }

    @Override
    public void createPlan(Plan plan) {
        String destinationPath = cutDestinationPathToDirectory(plan);

        String headerPath = Paths.get(generatedSourcesManager.getIncludeDir(), destinationPath, plan.getName() + plan.getId() + ".h").toString();
        String fileContentHeader = xtendTemplates.planHeader(plan);
        writeSourceFile(headerPath, fileContentHeader);

        formatFile(headerPath);

        String srcPath = Paths.get(generatedSourcesManager.getSrcDir(), destinationPath, plan.getName() + plan.getId() + ".cpp").toString();
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

    private String cutDestinationPathToDirectory(AbstractPlan plan) {
        String destinationPath = plan.getDestinationPath();
        if (destinationPath.lastIndexOf('.') > destinationPath.lastIndexOf(File.separator))
        {
            destinationPath = destinationPath.substring(0, destinationPath.lastIndexOf(File.separator)+1);
        }
        return destinationPath;
    }

    @Override
    public void createUtilityFunctionCreator(List<Plan> plans) {
        String headerPath = Paths.get(generatedSourcesManager.getIncludeDir(), "UtilityFunctionCreator.h").toString();
        String fileContentHeader = xtendTemplates.utilityFunctionCreatorHeader();
        writeSourceFile(headerPath, fileContentHeader);

        formatFile(headerPath);

        String srcPath = Paths.get(generatedSourcesManager.getSrcDir(), "UtilityFunctionCreator.cpp").toString();
        String fileContentSource = xtendTemplates.utilityFunctionCreatorSource(plans);
        writeSourceFile(srcPath, fileContentSource);

        formatFile(srcPath);
    }

    @Override
    public void createDomainCondition() {
        String headerPath = Paths.get(generatedSourcesManager.getIncludeDir(), "DomainCondition.h").toString();
        String fileContentHeader = xtendTemplates.domainConditionHeader();
        writeSourceFile(headerPath, fileContentHeader);

        formatFile(headerPath);

        String srcPath = Paths.get(generatedSourcesManager.getSrcDir(), "DomainCondition.cpp").toString();
        String fileContentSource = xtendTemplates.domainConditionSource();
        writeSourceFile(srcPath, fileContentSource);

        formatFile(srcPath);
    }

    @Override
    public void createDomainBehaviour() {
        String headerPath = Paths.get(generatedSourcesManager.getIncludeDir(), "DomainBehaviour.h").toString();
        String fileContentHeader = xtendTemplates.domainBehaviourHeader();
        writeSourceFile(headerPath, fileContentHeader);

        formatFile(headerPath);

        String srcPath = Paths.get(generatedSourcesManager.getSrcDir(), "DomainBehaviour.cpp").toString();
        String fileContentSource = xtendTemplates.domainBehaviourSource();
        writeSourceFile(srcPath, fileContentSource);

        formatFile(srcPath);
    }

    @Override
    public void setFormatter(String formatter) {
        this.formatter = formatter;
    }

    /**
     * This returns the {@link IConstraintCodeGenerator} of the active condition plugin.
     * TODO This maybe a candidate for a default method.
     * @return
     */
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

    /**
     * Calls the executable found by the formatter attribute on the file found by filename.
     * It is assumed that the executable is clang-format or has the same CLI as clang-format.
     * @param fileName
     */
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
