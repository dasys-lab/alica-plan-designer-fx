package de.unikassel.vs.alica.codegen;

import de.unikassel.vs.alica.planDesigner.alicamodel.Behaviour;
import de.unikassel.vs.alica.planDesigner.alicamodel.Condition;
import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;

import java.util.List;
import java.util.Map;

/**
 * This interface declares methods for generating code.
 * An example for an implementation for C++ as target language
 * can be found at de.unikassel.vs.alica.codegen.cpp.CPPGeneratorImpl
 */
public interface IGenerator {

    void setGeneratedSourcesManager(GeneratedSourcesManager generatedSourcesManager);
    void createBehaviourCreator(List<Behaviour> behaviours);
    void createBehaviours(Behaviour behaviour);
    void createConditionCreator(List<Plan> plans, List<Behaviour> behaviours, List<Condition> conditions);
    void createConstraintCreator(List<Plan> plans, List<Behaviour> behaviours, List<Condition> conditions);
    void createConstraints(List<Plan> plans);
    void createConstraintsForPlan(Plan plan);
    void createConstraintsForBehaviour(Behaviour behaviour);
    void createPlans(List<Plan> plans);
    void createPlan(Plan plan);
    void createUtilityFunctionCreator(List<Plan> plans);

    void createDomainCondition();
    void createDomainBehaviour();
}
