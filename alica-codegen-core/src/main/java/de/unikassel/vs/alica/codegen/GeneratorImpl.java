package de.unikassel.vs.alica.codegen;

import de.unikassel.vs.alica.planDesigner.alicamodel.*;

import java.util.List;

public abstract class GeneratorImpl {
    public void createConstraints(List<Plan> plans) {
        for (Plan plan : plans) {
            createConstraintsForPlan(plan);
        }
    }

    public void createPlans(List<Plan> plans) {
        for (Plan plan : plans) {
            createPlan(plan);
        }
    }

    public void createBehaviours(Behaviour behaviour) {
        this.createBehaviourImpl(behaviour);
        if (behaviour.getPreCondition() != null) {
            this.preConditionCreator(behaviour);
            this.preConditionBehaviourImpl(behaviour);
        }
        if (behaviour.getRuntimeCondition() != null) {
            this.runtimeConditionCreator(behaviour);
            this.runtimeConditionBehaviourImpl(behaviour);
        }
        if (behaviour.getPostCondition() != null) {
            this.postConditionCreator(behaviour);
            this.postConditionBehaviourImpl(behaviour);
        }
    }

    public void createConstraintsForBehaviour(Behaviour behaviour) {
        PreCondition preCondition = behaviour.getPreCondition();
        if (preCondition != null) {
            if (preCondition.getVariables().size() > 0 || preCondition.getQuantifiers().size() > 0) {
                this.constraintPreCondition(behaviour);
                this.constraintPreConditionImpl(behaviour);
            }
        }
        RuntimeCondition runtimeCondition = behaviour.getRuntimeCondition();
        if (runtimeCondition != null) {
            if (runtimeCondition.getVariables().size() > 0 || runtimeCondition.getQuantifiers().size() > 0) {
                this.constraintRuntimeCondition(behaviour);
                this.constraintRuntimeConditionImpl(behaviour);
            }
        }
        PostCondition postCondition = behaviour.getPostCondition();
        if (postCondition != null) {
            if (postCondition.getVariables().size() > 0 || postCondition.getQuantifiers().size() > 0) {
                this.constraintPostCondition(behaviour);
                this.constraintPostConditionImpl(behaviour);
            }
        }
    }

    public void createConstraintsForPlan(Plan plan) {
        if (plan.getPreCondition() != null) {
            this.constraintPlanPreCondition(plan);
            this.constraintPlanPreConditionImpl(plan);
        }
        RuntimeCondition runtimeCondition = plan.getRuntimeCondition();
        if (runtimeCondition != null) {
            if (runtimeCondition.getVariables().size() > 0 || runtimeCondition.getQuantifiers().size() > 0) {
                this.constraintPlanRuntimeCondition(plan);
                this.constraintPlanRuntimeConditionImpl(plan);
            }
        }
        List<Transition> transitions = plan.getTransitions();
        for (Transition transition: transitions) {
            PreCondition preCondition = transition.getPreCondition();
            if (preCondition != null) {
                if (preCondition.getVariables().size() > 0 || preCondition.getQuantifiers().size() > 0) {
                    this.constraintPlanTransitionPreCondition(plan, transition);
                    this.constraintPlanTransitionPreConditionImpl(transition);
                }
            }
        }
    }

    public void createPlan(Plan plan) {
        this.utilityFunctionPlan(plan);
        this.utilityFunctionPlanImpl(plan);

        if (plan.getPreCondition() != null) {
            this.preConditionPlan(plan);
            this.preConditionPlanImpl(plan);
        }

        if (plan.getRuntimeCondition() != null) {
            this.runtimeConditionPlan(plan);
            this.runtimeConditionPlanImpl(plan);
        }

        List<State> states = plan.getStates();
        for (State state: states) {
            List<Transition> transitions = state.getOutTransitions();
            for (Transition transition: transitions) {
                if (transition.getPreCondition() != null) {
                    this.transitionPreConditionPlan(state, transition);
                    this.transitionPreConditionPlanImpl(transition);
                }
            }
        }
    }

    public abstract void createBehaviourImpl(Behaviour behaviour);

    public abstract void preConditionCreator(Behaviour behaviour);

    public abstract void preConditionBehaviourImpl(Behaviour behaviour);

    public abstract void runtimeConditionCreator(Behaviour behaviour);

    public abstract void runtimeConditionBehaviourImpl(Behaviour behaviour);

    public abstract void postConditionCreator(Behaviour behaviour);

    public abstract void postConditionBehaviourImpl(Behaviour behaviour);

    protected abstract void constraintPostConditionImpl(Behaviour behaviour);

    protected abstract void constraintPostCondition(Behaviour behaviour);

    protected abstract void constraintRuntimeConditionImpl(Behaviour behaviour);

    protected abstract void constraintRuntimeCondition(Behaviour behaviour);

    protected abstract void constraintPreConditionImpl(Behaviour behaviour);

    protected abstract void constraintPreCondition(Behaviour behaviour);

    protected abstract void constraintPlanTransitionPreConditionImpl(Transition transition);

    protected abstract void constraintPlanTransitionPreCondition(Plan plan, Transition transition);

    protected abstract void constraintPlanRuntimeConditionImpl(Plan plan);

    protected abstract void constraintPlanRuntimeCondition(Plan plan);

    protected abstract void constraintPlanPreConditionImpl(Plan plan);

    protected abstract void constraintPlanPreCondition(Plan plan);

    protected abstract void preConditionPlanImpl(Plan plan);

    protected abstract void runtimeConditionPlanImpl(Plan plan);

    protected abstract void transitionPreConditionPlanImpl(Transition transition);

    protected abstract void transitionPreConditionPlan(State state, Transition transition);

    protected abstract void runtimeConditionPlan(Plan plan);

    protected abstract void preConditionPlan(Plan plan);

    protected abstract void utilityFunctionPlanImpl(Plan plan);

    protected abstract void utilityFunctionPlan(Plan plan);
}
