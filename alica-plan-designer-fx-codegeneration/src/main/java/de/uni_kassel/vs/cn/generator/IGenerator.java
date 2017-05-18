package de.uni_kassel.vs.cn.generator;

import de.uni_kassel.vs.cn.planDesigner.alica.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alica.Condition;
import de.uni_kassel.vs.cn.planDesigner.alica.Plan;

import java.util.List;

/**
 * Created by marci on 12.05.17.
 */
public interface IGenerator {
    void createBehaviourCreator(List<Behaviour> behaviours);
    void createBehaviour(Behaviour behaviour);
    void createConditionCreator(List<Plan> plans, List<Condition> conditions);
    void createConstraintCreator(List<Plan> plans, List<Condition> conditions);
    void createConstraints(List<Plan> plans);
    void createPlans(List<Plan> plans);
    void createUtilityFunctionCreator(List<Plan> plans);

    // TODO do we really need these? They're just empty unused dummies!
    void createDomainCondition();
    void createDomainBehaviour();

    IConstraintCodeGenerator getActiveConstraintCodeGenerator();
}
