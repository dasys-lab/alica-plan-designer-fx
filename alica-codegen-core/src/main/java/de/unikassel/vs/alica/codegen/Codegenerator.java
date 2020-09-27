package de.unikassel.vs.alica.codegen;

import de.unikassel.vs.alica.planDesigner.alicamodel.AbstractPlan;
import de.unikassel.vs.alica.planDesigner.alicamodel.Behaviour;
import de.unikassel.vs.alica.planDesigner.alicamodel.Condition;
import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class Codegenerator implements ICodegenerator {
    protected final Logger LOG = LogManager.getLogger(this.getClass());

    protected IGenerator generator;
    protected List<Plan> plans;
    protected List<Behaviour> behaviours;
    protected List<Condition> conditions;
    protected String destination;
    protected GeneratedSourcesManager generatedSourcesManager;

    public StringProperty currentFile = new SimpleStringProperty();

    @Override
    public void generate() {

    }

    /**
     * (Re)Generates source files for the given object.
     * If the given object is an instance of {@link Plan} or {@link Behaviour}.
     *
     * @param abstractPlan
     */
    @Override
    public void generate(AbstractPlan abstractPlan) {
        if (abstractPlan instanceof Plan) {
            generate((Plan) abstractPlan);
        } else if (abstractPlan instanceof Behaviour) {
            generate((Behaviour) abstractPlan);
        } else {
            LOG.error("Nothing to generate for something else than a plan or behaviour!");
        }
    }

    @Override
    public void generate(Plan plan) {
        List<File> generatedFiles = generatedSourcesManager.getGeneratedConditionFilesForPlan(plan);
        generatedFiles.addAll(generatedSourcesManager.getGeneratedConstraintFilesForPlan(plan));
        generator.createConstraintsForPlan(plan);
        generator.createPlan(plan);
        generator.createConditionCreator(plans, behaviours, conditions);
        generator.createUtilityFunctionCreator(plans);
    }

    @Override
    public void generate(Behaviour behaviour) {
        List<File> generatedFiles = generatedSourcesManager.getGeneratedFilesForBehaviour(behaviour);
        generatedFiles.addAll(generatedSourcesManager.getGeneratedConstraintFilesForBehaviour(behaviour));
        generator.createBehaviourCreator(behaviours);
        generator.createConstraintsForBehaviour(behaviour);
        generator.createBehaviours(behaviour);
    }

    public void monitorDestinationFiles(List<String> extensions) {
        try {
            if (Files.notExists(Paths.get(destination))) {
                Files.createDirectories(Paths.get(destination));
            }
            Files.walk(Paths.get(destination)).filter(e -> {
                String fileName = e.getFileName().toString();
                for (String extension: extensions) {
                    if (fileName.endsWith(extension)) {
                        return true;
                    }
                }
                return false;
            }).forEach(e -> {
                currentFile.set(e.toString());
                // without sleep the files are generated too fast to see them in the text field
                try {
                    Thread.sleep(10);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            });
        } catch (IOException e) {
            LOG.error("Could not find expression validator path! ", e);
            throw new RuntimeException(e);
        }
    }

    public void setPlans(List<Plan> plans) {
        this.plans = plans;
        Collections.sort(plans, new PlanElementComparator());
    }

    public void setBehaviours(List<Behaviour> behaviours) {
        this.behaviours = behaviours;
        Collections.sort(behaviours, new PlanElementComparator());
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
        Collections.sort(conditions, new PlanElementComparator());
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setGeneratedSourcesManager(GeneratedSourcesManager generatedSourcesManager) {
        this.generatedSourcesManager = generatedSourcesManager;
        if (this.generator != null) {
            this.generator.setGeneratedSourcesManager(generatedSourcesManager);
        }
    }
}
