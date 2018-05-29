package de.uni_kassel.vs.cn.generator;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Condition;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;

import java.util.List;
import java.util.Map;

/**
 * This interface declares methods for generating code.
 * An example for an implementation for C++ as target language
 * can be found at {@link de.uni_kassel.vs.cn.generator.cpp.CPPGeneratorImpl}
 */
public interface IGenerator {

    void setProtectedRegions(Map<String, String> protectedRegions);
    void createBehaviourCreator(List<Behaviour> behaviours);
    void createBehaviour(Behaviour behaviour);
    void createConditionCreator(List<Plan> plans, List<Condition> conditions);
    void createConstraintCreator(List<Plan> plans, List<Condition> conditions);
    void createConstraints(List<Plan> plans);
    void createConstraintsForPlan(Plan plan);
    void createPlans(List<Plan> plans);
    void createPlan(Plan plan);
    void createUtilityFunctionCreator(List<Plan> plans);

    void createDomainCondition();
    void createDomainBehaviour();

    void setFormatter(String formatter);
    IConstraintCodeGenerator getActiveConstraintCodeGenerator();
}
