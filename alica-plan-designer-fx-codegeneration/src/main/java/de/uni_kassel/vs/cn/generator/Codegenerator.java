package de.uni_kassel.vs.cn.generator;

import de.uni_kassel.vs.cn.generator.cpp.CPPGeneratorImpl;
import de.uni_kassel.vs.cn.generator.cpp.parser.CommentsLexer;
import de.uni_kassel.vs.cn.generator.cpp.parser.CommentsParser;
import de.uni_kassel.vs.cn.generator.cpp.parser.ProtectedRegionsVisitor;
import de.uni_kassel.vs.cn.generator.plugin.PluginManager;
import de.uni_kassel.vs.cn.planDesigner.alica.*;
import de.uni_kassel.vs.cn.planDesigner.configuration.ConfigurationManager;
import de.uni_kassel.vs.cn.planDesigner.controller.ModelManager;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * General Code Generator. It manages calling the correct {@link IGenerator} implementation
 * and serves as a simple way of generating code for the rest of the application.
 * If you want to generate a file just call {@link Codegenerator#generate(AbstractPlan)}
 * or {@link Codegenerator#generate()} to generate all files.
 * <p>
 * Do not cache this object.
 * A new instance should be created for every use or at least after creating a new ALICA object.
 */
public class Codegenerator {

    private static final Logger LOG = LogManager.getLogger(Codegenerator.class);

    private final IGenerator languageSpecificGenerator;
    private ModelManager modelManager;

    private List<Plan> plans;
    private List<Behaviour> behaviours;
    private List<Condition> conditions;

    /**
     * This constructor initializes a C++ code generator
     */
    public Codegenerator() {
        // TODO: Document this! Here can the programming language be changed.
        languageSpecificGenerator = new CPPGeneratorImpl();
        languageSpecificGenerator.setFormatter(ConfigurationManager.getInstance().getClangFormatPath());

        modelManager = new ModelManager();
        plans = modelManager.getPlans();
        Collections.sort(plans, new PlanElementComparator());
        behaviours = modelManager.getBehaviours();
        Collections.sort(behaviours, new PlanElementComparator());
        conditions = modelManager.getConditions();
        Collections.sort(conditions, new PlanElementComparator());
    }

    /**
     * Generates source files for all ALICA plans and behaviours in workspace.
     */
    // TODO: To be reviewed and maybe adapted, because of MVC pattern adaption.
    public void generate() {
        ProtectedRegionsVisitor protectedRegionsVisitor = new ProtectedRegionsVisitor();
        String expressionValidatorsPath = ConfigurationManager.getInstance().getActiveConfiguration()
                .getGenSrcPath();
        try {
            if (Files.notExists(Paths.get(expressionValidatorsPath))) {
                Files.createDirectories(Paths.get(expressionValidatorsPath));
            }
            Files.walk(Paths.get(expressionValidatorsPath)).filter(e -> {
                String fileName = e.getFileName().toString();
                return fileName.endsWith(".h") || fileName.endsWith(".cpp");
            }).forEach(e -> {
                try {
                    CommentsLexer lexer = new CommentsLexer(CharStreams.fromPath(e));
                    CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
                    CommentsParser parser = new CommentsParser(commonTokenStream);
                    CommentsParser.All_textContext all_textContext = parser.all_text();
                    protectedRegionsVisitor.visit(all_textContext);
                } catch (IOException e1) {
                    LOG.error("Could not parse existing source file " + e, e1);
                    throw new RuntimeException(e1);
                }
            });
        } catch (IOException e) {
            LOG.error("Could not find expression validator path! ", e);
            throw new RuntimeException(e);
        }

        PluginManager.getInstance().getDefaultPlugin().setProtectedRegions(protectedRegionsVisitor.getProtectedRegions());
        languageSpecificGenerator.setProtectedRegions(protectedRegionsVisitor.getProtectedRegions());

        languageSpecificGenerator.createDomainBehaviour();
        languageSpecificGenerator.createDomainCondition();

        languageSpecificGenerator.createUtilityFunctionCreator(plans);
        languageSpecificGenerator.createBehaviourCreator(behaviours);
        languageSpecificGenerator.createConditionCreator(plans, conditions);
        languageSpecificGenerator.createConstraintCreator(plans, conditions);

        languageSpecificGenerator.createConstraints(plans);
        languageSpecificGenerator.createPlans(plans);

        for (Behaviour behaviour : behaviours) {
            languageSpecificGenerator.createBehaviour(behaviour);
        }
        LOG.info("Generated all files successfully");
    }

    /**
     * (Re)Generates source files for the given object.
     * If the given object is an instance of {@link Plan} or {@link Behaviour}.
     *
     * @param planElement
     */
    // TODO: To be reviewed and maybe adapted, because of MVC pattern adaption.
    public void generate(AbstractPlan planElement) {
        if (!(planElement instanceof Behaviour || planElement instanceof Plan)) {
            return;
        }

        GeneratedSourcesManager generatedSourcesManager = GeneratedSourcesManager.get();
        ProtectedRegionsVisitor protectedRegionsVisitor = new ProtectedRegionsVisitor();
        generatedSourcesManager
                .getAllGeneratedFilesForAbstractPlan(planElement)
                .forEach(e -> {
                    try {
                        if (e.exists()) {
                            CommentsLexer lexer = new CommentsLexer(CharStreams.fromPath(e.toPath()));
                            CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
                            CommentsParser parser = new CommentsParser(commonTokenStream);
                            CommentsParser.All_textContext all_textContext = parser.all_text();
                            protectedRegionsVisitor.visit(all_textContext);
                        }
                    } catch (IOException e1) {
                        LOG.error("Could not parse existing source file " + e.getAbsolutePath(), e1);
                        throw new RuntimeException(e1);
                    }
                });

        PluginManager.getInstance().getDefaultPlugin().setProtectedRegions(protectedRegionsVisitor.getProtectedRegions());
        languageSpecificGenerator.setProtectedRegions(protectedRegionsVisitor.getProtectedRegions());

        if (planElement instanceof Behaviour) {
            languageSpecificGenerator.createBehaviourCreator(behaviours);
            languageSpecificGenerator.createBehaviour((Behaviour) planElement);
        } else if (planElement instanceof Plan) {
            languageSpecificGenerator.createConstraintsForPlan((Plan) planElement);
            languageSpecificGenerator.createPlan((Plan) planElement);
            languageSpecificGenerator.createConditionCreator(plans, conditions);
            languageSpecificGenerator.createUtilityFunctionCreator(plans);
        }
    }
}
