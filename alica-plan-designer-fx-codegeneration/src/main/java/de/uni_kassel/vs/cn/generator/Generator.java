package de.uni_kassel.vs.cn.generator;

import de.uni_kassel.vs.cn.planDesigner.alica.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alica.Condition;
import de.uni_kassel.vs.cn.planDesigner.alica.Plan;

import java.util.List;

/**
 * Created by marci on 12.05.17.
 */
public interface Generator {
    void createBehaviourCreator(List<Behaviour> behaviours);
    void createBehaviour(Behaviour behaviour);
    void createConditionCreator(List<Condition> conditions);
    void createCondition(Condition condition);
    void createConstraintCreator(List<Plan> plans);
    void createConstraints(List<Plan> plans);
    void createPlans(List<Plan> plans);
    void createUtilityFunctionCreator(List<Plan> plans);

    // TODO do we really need these? They're just empty unused dummies!
    void createDomainCondition();
    void createDomainBehaviour();
}
