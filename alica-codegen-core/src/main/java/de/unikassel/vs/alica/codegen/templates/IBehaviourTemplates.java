package de.unikassel.vs.alica.codegen.templates;

import de.unikassel.vs.alica.planDesigner.alicamodel.Behaviour;

public interface IBehaviourTemplates {
    String behaviourImpl(Behaviour behaviour);
    String preConditionBehaviourImpl(Behaviour behaviour);
    String preConditionBehaviour(Behaviour behaviour);
    String runtimeConditionBehaviourImpl(Behaviour behaviour);
    String runtimeConditionBehaviour(Behaviour behaviour);
    String postConditionBehaviourImpl(Behaviour behaviour);
    String postConditionBehaviour(Behaviour behaviour);
    String constraintPreConditionImpl(Behaviour behaviour);
    String constraintPreCondition(Behaviour behaviour);
    String constraintRuntimeConditionImpl(Behaviour behaviour);
    String constraintRuntimeCondition(Behaviour behaviour);
    String constraintPostConditionImpl(Behaviour behaviour);
    String constraintPostCondition(Behaviour behaviour);
    String behaviourCondition(Behaviour behaviour);
    String behaviour(Behaviour behaviour);
    String constraints(Behaviour behaviour);
}
