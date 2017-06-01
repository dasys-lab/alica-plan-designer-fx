package de.uni_kassel.vs.cn.generator;

import de.uni_kassel.vs.cn.generator.cpp.CPPGeneratorImpl;
import de.uni_kassel.vs.cn.generator.cpp.GeneratedSourcesManager;
import de.uni_kassel.vs.cn.generator.cpp.parser.CommentsBaseListener;
import de.uni_kassel.vs.cn.generator.cpp.parser.CommentsLexer;
import de.uni_kassel.vs.cn.generator.cpp.parser.CommentsParser;
import de.uni_kassel.vs.cn.generator.cpp.parser.ProtectedRegionsVisitor;
import de.uni_kassel.vs.cn.generator.plugin.PluginManager;
import de.uni_kassel.vs.cn.planDesigner.alica.*;
import de.uni_kassel.vs.cn.planDesigner.alica.util.AllAlicaFiles;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by marci on 18.05.17.
 */
public class Codegenerator {

    private List<Plan> allPlans;
    private List<Behaviour> allBehaviours;
    private List<PreCondition> preConditions;
    private List<RuntimeCondition> runtimeConditions;
    private List<PostCondition> postConditions;
    private List<Condition> allConditions;

    public void generate() {
        initialze();


        // TODO document this! Here can the programming language be changed
        IGenerator actualGenerator = new CPPGeneratorImpl();

        GeneratedSourcesManager generatedSourcesManager = new GeneratedSourcesManager();
        ProtectedRegionsVisitor protectedRegionsVisitor = new ProtectedRegionsVisitor();
        for (Plan plan : allPlans) {
            generatedSourcesManager
                    .getAllGeneratedFilesForAbstractPlan(plan)
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
                            e1.printStackTrace();
                        }
                    });
        }

        for (Behaviour behaviour : allBehaviours) {
            generatedSourcesManager
                    .getAllGeneratedFilesForAbstractPlan(behaviour)
                    .forEach(e -> {

                        try {
                            CommentsLexer lexer = new CommentsLexer(CharStreams.fromPath(e.toPath()));
                            CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
                            CommentsParser parser = new CommentsParser(commonTokenStream);
                            CommentsParser.All_textContext all_textContext = parser.all_text();
                            protectedRegionsVisitor.visit(all_textContext);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    });
        }

        PluginManager.getInstance().getActivePlugin().setProtectedRegions(protectedRegionsVisitor.getProtectedRegions());
        actualGenerator.setProtectedRegions(protectedRegionsVisitor.getProtectedRegions());

        actualGenerator.createDomainBehaviour();
        actualGenerator.createDomainCondition();

        actualGenerator.createUtilityFunctionCreator(allPlans);
        actualGenerator.createBehaviourCreator(allBehaviours);
        actualGenerator.createConditionCreator(allPlans, allConditions);
        actualGenerator.createConstraintCreator(allPlans, allConditions);

        actualGenerator.createConstraints(allPlans);
        actualGenerator.createPlans(allPlans);

        for (Behaviour behaviour : allBehaviours) {
            actualGenerator.createBehaviour(behaviour);
        }
    }

    private void initialze() {
        allPlans = AllAlicaFiles
                .getInstance()
                .getPlans()
                .stream()
                .map(e -> e.getKey())
                .sorted(Comparator.comparing(e -> e.getId()))
                .collect(Collectors.toList());

        allBehaviours = AllAlicaFiles
                .getInstance()
                .getBehaviours()
                .stream()
                .map(e -> e.getKey())
                .sorted(Comparator.comparing(e -> e.getId()))
                .collect(Collectors.toList());

        preConditions = new ArrayList<>();
        preConditions.addAll(allPlans
                .stream()
                .map(e -> e.getPreCondition())
                .filter(e -> e != null)
                .collect(Collectors.toList()));

        runtimeConditions = new ArrayList<>();
        runtimeConditions.addAll(allPlans
                .stream()
                .map(e -> e.getRuntimeCondition())
                .filter(e -> e != null)
                .collect(Collectors.toList()));

        postConditions = new ArrayList<>();

        allPlans
                .stream()
                .map(e -> e.getStates())
                .map(e -> e.stream().filter(f -> (f instanceof TerminalState)).collect(Collectors.toList()))
                .filter(e -> e != null && e.isEmpty() == false)
                .forEach(e -> e.forEach(f -> {
                    PostCondition postCondition = ((TerminalState) f).getPostCondition();
                    if (postCondition != null) {
                        postConditions.add(postCondition);
                    }
                }));

        allBehaviours
                .forEach(e -> {
                    RuntimeCondition runtimeCondition = e.getRuntimeCondition();
                    if (runtimeCondition != null) {
                        runtimeConditions.add(runtimeCondition);
                    }

                    PostCondition postCondition = e.getPostCondition();

                    if (postCondition != null) {
                        postConditions.add(postCondition);
                    }

                    PreCondition preCondition = e.getPreCondition();
                    if (preCondition != null) {
                        preConditions.add(preCondition);
                    }
                });

        allConditions = new ArrayList<>();
        allConditions.addAll(preConditions);
        allConditions.addAll(postConditions);
        allConditions.addAll(runtimeConditions);
    }
}
