package de.unikassel.vs.alica.codegen;

import de.unikassel.vs.alica.codegen.templates.*;
import de.unikassel.vs.alica.planDesigner.alicamodel.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CodegenHelper {
    protected static final Logger LOG = LogManager.getLogger(GeneratorImpl.class);

    private GeneratedSourcesManager generatedSourcesManager;

    private String implPath;
    private String genPath;

    private ICreatorTemplates creators;
    private IBehaviourTemplates behaviours;
    private IDomainTemplates domain;
    private IPlanTemplates plans;
    private ITransitionTemplates transitions;

    public void setGeneratedSourcesManager(GeneratedSourcesManager generatedSourcesManager) {
        this.generatedSourcesManager = generatedSourcesManager;
    }

    public void setBaseDir(String baseDir) {
        this.implPath = Paths.get(baseDir, "impl").toString();
        this.genPath = Paths.get(baseDir, "gen").toString();
    }

    public void setCreatorTemplates(ICreatorTemplates creators) {
        this.creators = creators;
    }

    public void setBehaviourTemplates(IBehaviourTemplates behaviours) {
        this.behaviours = behaviours;
    }

    public void setDomainTemplates(IDomainTemplates domain) {
        this.domain = domain;
    }

    public void setPlanTemplates(IPlanTemplates plans) {
        this.plans = plans;
    }

    public void setTransitionTemplates(ITransitionTemplates transitions) {
        this.transitions = transitions;
    }

    private void createFolders(Path file) {
        // generate list of folders that does not exist
        List<Path> foldersNotExist = new ArrayList<>();
        Path folder = file.getParent();
        while (Files.notExists(folder)) {
            foldersNotExist.add(0, folder);
            folder = folder.getParent();
        }
        // create these folders
        for (Path f: foldersNotExist) {
            createFolder(f);
        }
    }

    public void createFolder(Path folder) {
        folder.toFile().mkdir();
    }

    protected void writeSourceFile(String filePath, String fileContent) {
        try {
            Path file = Paths.get(filePath);
            createFolders(file);
            Files.write(file, fileContent.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            LOG.error("Couldn't write source file "
                    + filePath + " with content size " + fileContent
                    .getBytes(StandardCharsets.UTF_8).length, e);
            throw new RuntimeException(e);
        }
    }

    protected String cutDestinationPathToDirectory(AbstractPlan plan) {
        String destinationPath = plan.getRelativeDirectory();
        if (destinationPath.lastIndexOf('.') > destinationPath.lastIndexOf(File.separator)) {
            destinationPath = destinationPath.substring(0, destinationPath.lastIndexOf(File.separator) + 1);
        }
        return destinationPath;
    }

    public void createBehaviourImpl(String filename, Behaviour behaviour) {
        String srcPath = Paths.get(implPath, "behaviours", filename).toString();
        String fileContentSource = behaviours.behaviourImpl(behaviour);
        if (new File(srcPath).exists()) {
            LOG.debug("File \"" + srcPath + "\" already exists and is not overwritten");
            return;
        }
        writeSourceFile(srcPath, fileContentSource);
    }

    public void createBehaviourCreator(String filename, List<Behaviour> behaviours) {
        String srcPath = Paths.get(genPath, "creators", filename).toString();
        String fileContentSource = creators.behaviourCreator(behaviours);
        writeSourceFile(srcPath, fileContentSource);
    }

    public void preConditionBehaviourImpl(String filename, Behaviour behaviour) {
        String srcPath = Paths.get(implPath, "conditions", filename).toString();
        String fileContentSource = behaviours.preConditionBehaviourImpl(behaviour);
        if (new File(srcPath).exists()) {
            LOG.debug("File \"" + srcPath + "\" already exists and is not overwritten");
            return;
        }
        writeSourceFile(srcPath, fileContentSource);
    }

    public void preConditionCreator(String filename, Behaviour behaviour) {
        String srcPath = Paths.get(genPath, "conditions", filename).toString();
        String fileContentSource = behaviours.preConditionBehaviour(behaviour);
        writeSourceFile(srcPath, fileContentSource);
    }

    public void runtimeConditionBehaviourImpl(String filename, Behaviour behaviour) {
        String srcPath = Paths.get(implPath, "conditions", filename).toString();
        String fileContentSource = behaviours.runtimeConditionBehaviourImpl(behaviour);
        if (new File(srcPath).exists()) {
            LOG.debug("File \"" + srcPath + "\" already exists and is not overwritten");
            return;
        }
        writeSourceFile(srcPath, fileContentSource);
    }

    public void runtimeConditionCreator(String filename, Behaviour behaviour) {
        String srcPath = Paths.get(genPath, "conditions", filename).toString();
        String fileContentSource = behaviours.runtimeConditionBehaviour(behaviour);
        writeSourceFile(srcPath, fileContentSource);
    }

    public void postConditionBehaviourImpl(String filename, Behaviour behaviour) {
        String srcPath = Paths.get(implPath, "conditions", filename).toString();
        String fileContentSource = behaviours.postConditionBehaviourImpl(behaviour);
        if (new File(srcPath).exists()) {
            LOG.debug("File \"" + srcPath + "\" already exists and is not overwritten");
            return;
        }
        writeSourceFile(srcPath, fileContentSource);
    }

    public void postConditionCreator(String filename, Behaviour behaviour) {
        String srcPath = Paths.get(genPath, "conditions", filename).toString();
        String fileContentSource = behaviours.postConditionBehaviour(behaviour);
        writeSourceFile(srcPath, fileContentSource);
    }

    public void createBehaviourCondition(String filename, Behaviour behaviour) {
        String destinationPath = cutDestinationPathToDirectory(behaviour);
        String srcPath = Paths.get(genPath, "behaviours", destinationPath, filename).toString();
        String fileContentSource = behaviours.behaviourCondition(behaviour);
        writeSourceFile(srcPath, fileContentSource);
    }

    public void createBehaviour(String filename, Behaviour behaviour) {
        String destinationPath = cutDestinationPathToDirectory(behaviour);
        String srcPath = Paths.get(genPath, "behaviours", destinationPath, filename).toString();
        String fileContentSource = behaviours.behaviour(behaviour);
        writeSourceFile(srcPath, fileContentSource);
    }

    public void createConditionCreator(String filename, List<Plan> plans, List<Behaviour> behaviours, List<Condition> conditions) {
        String srcPath = Paths.get(genPath, "creators", filename).toString();
        String fileContentSource = creators.conditionCreator(plans, behaviours, conditions);
        writeSourceFile(srcPath, fileContentSource);
    }

    public void createConstraintCreator(String filename, List<Plan> plans, List<Behaviour> behaviours, List<Condition> conditions) {
        String srcPath = Paths.get(genPath, "creators", filename).toString();
        String fileContentSource = creators.constraintCreator(plans, behaviours, conditions);
        writeSourceFile(srcPath, fileContentSource);
    }

    public void constraintPreCondition(String filename, Behaviour behaviour) {
        String srcPath = Paths.get(genPath, "conditions", filename).toString();
        String fileContentSource = behaviours.constraintPreCondition(behaviour);
        writeSourceFile(srcPath, fileContentSource);
    }

    public void constraintPreConditionImpl(String filename, Behaviour behaviour) {
        String srcPath = Paths.get(implPath, "conditions", filename).toString();
        String fileContentSource = behaviours.constraintPreConditionImpl(behaviour);
        if (new File(srcPath).exists()) {
            LOG.debug("File \"" + srcPath + "\" already exists and is not overwritten");
            return;
        }
        writeSourceFile(srcPath, fileContentSource);
    }

    public void constraintRuntimeCondition(String filename, Behaviour behaviour) {
        String srcPath = Paths.get(genPath, "conditions", filename).toString();
        String fileContentSource = behaviours.constraintRuntimeCondition(behaviour);
        writeSourceFile(srcPath, fileContentSource);
    }

    public void constraintRuntimeConditionImpl(String filename, Behaviour behaviour) {
        String srcPath = Paths.get(implPath, "conditions", filename).toString();
        String fileContentSource = behaviours.constraintRuntimeConditionImpl(behaviour);
        if (new File(srcPath).exists()) {
            LOG.debug("File \"" + srcPath + "\" already exists and is not overwritten");
            return;
        }
        writeSourceFile(srcPath, fileContentSource);
    }

    public void constraintPostCondition(String filename, Behaviour behaviour) {
        String srcPath = Paths.get(genPath, "conditions", filename).toString();
        String fileContentSource = behaviours.constraintPostCondition(behaviour);
        writeSourceFile(srcPath, fileContentSource);
    }

    public void constraintPostConditionImpl(String filename, Behaviour behaviour) {
        String srcPath = Paths.get(implPath, "conditions", filename).toString();
        String fileContentSource = behaviours.constraintPostConditionImpl(behaviour);
        if (new File(srcPath).exists()) {
            LOG.debug("File \"" + srcPath + "\" already exists and is not overwritten");
            return;
        }
        writeSourceFile(srcPath, fileContentSource);
    }

    public void createBehaviourConstraints(String filename, Behaviour behaviour) {
        String destinationPathWithoutName = cutDestinationPathToDirectory(behaviour);
        String srcPath = Paths.get(genPath,"constraints", destinationPathWithoutName, filename).toString();
        String fileContentSource = behaviours.constraints(behaviour);
        writeSourceFile(srcPath, fileContentSource);
    }

    public void constraintPlanPreCondition(String filename, Plan plan) {
        String srcPath = Paths.get(genPath, "constraints", filename).toString();
        String fileContentSource = plans.constraintPlanPreCondition(plan);
        writeSourceFile(srcPath, fileContentSource);
    }

    public void constraintPlanPreConditionImpl(String filename, Plan plan) {
        String srcPath = Paths.get(implPath, "constraints", filename).toString();
        String fileContentSource = plans.constraintPlanPreConditionImpl(plan);
        if (new File(srcPath).exists()) {
            LOG.debug("File \"" + srcPath + "\" already exists and is not overwritten");
            return;
        }
        writeSourceFile(srcPath, fileContentSource);
    }

    public void constraintPlanRuntimeCondition(String filename, Plan plan) {
        String srcPath = Paths.get(genPath, "constraints", filename).toString();
        String fileContentSource = plans.constraintPlanRuntimeCondition(plan);
        writeSourceFile(srcPath, fileContentSource);
    }

    public void constraintPlanRuntimeConditionImpl(String filename, Plan plan) {
        String srcPath = Paths.get(implPath, "constraints", filename).toString();
        String fileContentSource = plans.constraintPlanRuntimeConditionImpl(plan);
        if (new File(srcPath).exists()) {
            LOG.debug("File \"" + srcPath + "\" already exists and is not overwritten");
            return;
        }
        writeSourceFile(srcPath, fileContentSource);
    }

    public void constraintPlanTransitionPreCondition(String filename, Plan plan, Transition transition) {
        String srcPath = Paths.get(genPath, "constraints", filename).toString();
        String fileContentSource = transitions.constraintPlanTransitionPreCondition(plan, transition);
        writeSourceFile(srcPath, fileContentSource);
    }

    public void constraintPlanTransitionPreConditionImpl(String filename, Transition transition) {
        String srcPath = Paths.get(implPath, "constraints", filename).toString();
        String fileContentSource = transitions.constraintPlanTransitionPreConditionImpl(transition);
        if (new File(srcPath).exists()) {
            LOG.debug("File \"" + srcPath + "\" already exists and is not overwritten");
            return;
        }
        writeSourceFile(srcPath, fileContentSource);
    }

    public void createPlanConstraints(String filename, Plan plan) {
        String destinationPathWithoutName = cutDestinationPathToDirectory(plan);
        File constraintsGenPath = Paths.get(genPath, destinationPathWithoutName, "constraints").toFile();

        String srcPath = Paths.get(constraintsGenPath.toString(), filename).toString();
        String fileContentSource = plans.constraints(plan);
        writeSourceFile(srcPath, fileContentSource);
    }

    public void readConstraintsForPlan(String filename, Plan plan) {
        String destinationPathWithoutName = cutDestinationPathToDirectory(plan);
        String srcPath = Paths.get(genPath, destinationPathWithoutName, "constraints", filename).toString();

        for (State inPlan: plan.getStates()) {
            try {
                LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(srcPath));
                while (lineNumberReader.ready()) {
                    if (lineNumberReader.readLine().contains("// State: " + inPlan.getName())) {
                        generatedSourcesManager.putLineForModelElement(inPlan.getId(), lineNumberReader.getLineNumber());
                        break;
                    }
                }
                lineNumberReader.close();
            } catch (IOException e) {
                LOG.error("Could not open/read lines for " + srcPath, e);
            }
        }
    }

    public void createDomainBehaviour(String filename) {
        String srcPath = Paths.get(genPath, "domain", filename).toString();
        String fileContentSource = domain.domainBehaviour();
        writeSourceFile(srcPath, fileContentSource);
    }

    public void createDomainBehaviourImpl(String filename) {
        String srcPath = Paths.get(implPath, "domain", filename).toString();
        String fileContentSource = domain.domainBehaviourImpl();
        if (new File(srcPath).exists()) {
            LOG.debug("File \"" + srcPath + "\" already exists and is not overwritten");
            return;
        }
        writeSourceFile(srcPath, fileContentSource);
    }

    public void createDomainCondition(String filename) {
        String srcPath = Paths.get(genPath, "domain", filename).toString();
        String fileContentSource = domain.domainCondition();
        writeSourceFile(srcPath, fileContentSource);
    }

    public void createDomainConditionImpl(String filename) {
        String srcPath = Paths.get(implPath, "domain", filename).toString();
        String fileContentSource = domain.domainConditionImpl();
        if (new File(srcPath).exists()) {
            LOG.debug("File \"" + srcPath + "\" already exists and is not overwritten");
            return;
        }
        writeSourceFile(srcPath, fileContentSource);
    }

    public void utilityFunctionPlan(String filename, Plan plan) {
        String srcPath = Paths.get(genPath, "utilityfunctions", filename).toString();
        String fileContentSource = plans.utilityFunctionPlan(plan);
        writeSourceFile(srcPath, fileContentSource);
    }

    public void utilityFunctionPlanImpl(String filename, Plan plan) {
        String srcPath = Paths.get(implPath, "utilityfunctions", filename).toString();
        String fileContentSource = plans.utilityFunctionPlanImpl(plan);
        if (new File(srcPath).exists()) {
            LOG.debug("File \"" + srcPath + "\" already exists and is not overwritten");
            return;
        }
        writeSourceFile(srcPath, fileContentSource);
    }

    public void preConditionPlan(String filename, Plan plan) {
        String srcPath = Paths.get(genPath, "conditions", filename).toString();
        String fileContentSource = plans.preConditionPlan(plan);
        writeSourceFile(srcPath, fileContentSource);
    }

    public void preConditionPlanImpl(String filename, Plan plan) {
        String srcPath = Paths.get(implPath, "conditions", filename).toString();
        String fileContentSource = plans.preConditionPlanImpl(plan);
        if (new File(srcPath).exists()) {
            LOG.debug("File \"" + srcPath + "\" already exists and is not overwritten");
            return;
        }
        writeSourceFile(srcPath, fileContentSource);
    }

    public void runtimeConditionPlan(String filename, Plan plan) {
        String srcPath = Paths.get(genPath, "conditions", filename).toString();
        String fileContentSource = plans.runtimeConditionPlan(plan);
        writeSourceFile(srcPath, fileContentSource);
    }

    public void runtimeConditionPlanImpl(String filename, Plan plan) {
        String srcPath = Paths.get(implPath, "conditions", filename).toString();
        String fileContentSource = plans.runtimeConditionPlanImpl(plan);
        if (new File(srcPath).exists()) {
            LOG.debug("File \"" + srcPath + "\" already exists and is not overwritten");
            return;
        }
        writeSourceFile(srcPath, fileContentSource);
    }

    public void transitionPreConditionPlan(String filename, State state, Transition transition) {
        String srcPath = Paths.get(genPath, "conditions", filename).toString();
        String fileContentSource = transitions.transitionPreConditionPlan(state, transition);
        writeSourceFile(srcPath, fileContentSource);
    }

    public void transitionPreConditionPlanImpl(String filename, Transition transition) {
        String srcPath = Paths.get(implPath, "conditions", filename).toString();
        String fileContentSource = transitions.transitionPreConditionPlanImpl(transition);
        if (new File(srcPath).exists()) {
            LOG.debug("File \"" + srcPath + "\" already exists and is not overwritten");
            return;
        }
        writeSourceFile(srcPath, fileContentSource);
    }

    public void createUtilityFunctionCreator(String filename, List<Plan> plans) {
        String srcPath = Paths.get(genPath, "creators", filename).toString();
        String fileContentSource = creators.utilityFunctionCreator(plans);
        writeSourceFile(srcPath, fileContentSource);
    }
}
