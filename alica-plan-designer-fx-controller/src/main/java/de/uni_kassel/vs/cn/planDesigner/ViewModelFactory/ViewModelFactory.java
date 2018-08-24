package de.uni_kassel.vs.cn.planDesigner.ViewModelFactory;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.*;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.view.Types;
import de.uni_kassel.vs.cn.planDesigner.view.model.*;
import de.uni_kassel.vs.cn.planDesigner.view.repo.RepositoryViewModel;

import java.util.ArrayList;

public class ViewModelFactory {

    protected ModelManager modelManager;

    public ViewModelFactory(ModelManager modelManager) {
        this.modelManager = modelManager;
    }

    public ViewModelElement createViewModelElement(PlanElement planElement, String type) {
        if (planElement instanceof SerializablePlanElement) {
            return new ViewModelElement(planElement.getId(), planElement.getName(), type, ((SerializablePlanElement) planElement).getRelativeDirectory());
        } else {
            return new ViewModelElement(planElement.getId(), planElement.getName(), type);
        }
    }

    public ViewModelElement createViewModelElement(PlanElement planElement, String type, long parentId) {
        ViewModelElement element = createViewModelElement(planElement, type);
        element.setParentId(parentId);
        return element;
    }

    public RepositoryViewModel createRepositoryViewModel() {
        return new RepositoryViewModel();
    }

    public TaskRepositoryViewModel createTaskRepositoryViewModel() {
        return new TaskRepositoryViewModel();
    }

    public ViewModelElement getViewModelElement(PlanElement planElement) {
        if(planElement == null) {
            return null;
        }
        if (planElement instanceof Task) {
            return createViewModelElement(planElement, Types.TASK, ((Task) planElement).getTaskRepository().getId());
        } else if (planElement instanceof TaskRepository) {
            PlanElementViewModel taskRepo = new PlanElementViewModel(planElement.getId(), planElement.getName(), Types.TASKREPOSITORY);
            taskRepo.setComment(planElement.getComment());
            taskRepo.setRelativeDirectory(((TaskRepository) planElement).getRelativeDirectory());
            return taskRepo;
        } else if (planElement instanceof Plan) {
            PlanViewModel element = null;
            Plan plan = (Plan) planElement;
            if (plan.getMasterPlan()) {
                element = new PlanViewModel(plan.getId(), plan.getName(), Types.MASTERPLAN);
            } else {
                element = new PlanViewModel(plan.getId(), plan.getName(), Types.PLAN);
            }
            element.setComment(plan.getComment());
            element.setRelativeDirectory(plan.getRelativeDirectory());
            element.setUtilityThreshold(plan.getUtilityThreshold());
            element.setMasterPlan(plan.getMasterPlan());
            return element;
        } else if (planElement instanceof TaskRepository) {
            PlanElementViewModel element = new PlanViewModel(planElement.getId(), planElement.getName(), Types.TASKREPOSITORY);
            TaskRepository taskRepository = (TaskRepository) planElement;
            element.setComment(taskRepository.getComment());
            element.setRelativeDirectory(taskRepository.getRelativeDirectory());
            return element;
        } else {
            System.err.println("ViewModelFactory: getViewModelElement for type " + planElement.getClass().toString() + " not implemented!");
        }
        return null;
    }

    public BehaviourViewModel createBehaviourViewModel(PlanElement planElement) {
        if (planElement == null) {
            return null;
        }
        Behaviour behaviour = (Behaviour) planElement;
        BehaviourViewModel behaviourViewModel = new BehaviourViewModel(behaviour.getId(), behaviour.getName(), Types.BEHAVIOUR);
        behaviourViewModel.setComment(behaviour.getComment());
        behaviourViewModel.setRelativeDirectory(behaviour.getRelativeDirectory());
        behaviourViewModel.setFrequency(behaviour.getFrequency());
        behaviourViewModel.setDeferring(behaviour.getDeferring());
        for (Variable variable : behaviour.getVariables()) {
            VariableViewModel variableViewModel = new VariableViewModel(variable.getId(), variable.getName(), Types.VARIABLE);
            variableViewModel.setVariableType(variable.getType());
            behaviourViewModel.getVariables().add(variableViewModel);
        }
        behaviourViewModel.setPreCondition(getConditionViewModel(behaviour.getPreCondition(), Types.PRECONDITION, behaviour.getId()));
        behaviourViewModel.setRuntimeCondition(getConditionViewModel(behaviour.getRuntimeCondition(), Types.RUNTIMECONDITION, behaviour.getId()));
        behaviourViewModel.setPostCondition(getConditionViewModel(behaviour.getPostCondition(), Types.POSTCONDITION, behaviour.getId()));
        return behaviourViewModel;
    }

    public ConditionViewModel getConditionViewModel(Condition condition, String type, long parentId) {
        if (condition == null) {
            return null;
        }
        ConditionViewModel conditionViewModel = new ConditionViewModel(condition.getId(), condition.getName(), type);
        conditionViewModel.setConditionString(condition.getConditionString());
        conditionViewModel.setEnabled(condition.getEnabled());
        conditionViewModel.setPluginName(condition.getPluginName());
        conditionViewModel.setComment(condition.getComment());
        for (Variable var : condition.getVariables()) {
            conditionViewModel.getVars().add(new VariableViewModel(var.getId(), var.getName(), var.getType()));
        }
        for (Quantifier quantifier : condition.getQuantifiers()) {
            // TODO: Quantifier is not very clean or fully implemented, yet.
            conditionViewModel.getQuantifier().add(new QuantifierViewModel(quantifier.getId(), quantifier.getName(), Types.QUANTIFIER));
        }
        conditionViewModel.setParentId(parentId);
        return conditionViewModel;
    }

    public PlanTypeViewModel createPlanTypeViewModel(ViewModelElement viewModelElement, ArrayList<Plan> plans) {
        PlanElement planElement = modelManager.getPlanElement(viewModelElement.getId());
        if (planElement == null || !(planElement instanceof PlanType)) {
            System.err.println("ViewModelFactory: Opening PlanTypeTab for unknown Id or not presented element is not a PlanType!");
            return null;
        }

        PlanType planType = (PlanType) planElement;
        PlanTypeViewModel planTypeViewModel = new PlanTypeViewModel(planType.getId(), planType.getName(),
                Types.PLANTYPE);
        planTypeViewModel.setRelativeDirectory(planType.getRelativeDirectory());
        planTypeViewModel.setComment(planType.getComment());

        for (Plan plan : plans) {
            if (plan.getMasterPlan()) {
                planTypeViewModel.addPlanToAllPlans(createViewModelElement(plan, Types.MASTERPLAN));
            } else {
                planTypeViewModel.addPlanToAllPlans(createViewModelElement(plan, Types.PLAN));
            }
        }

        for (AnnotatedPlan annotatedPlan : planType.getPlans()) {
            Plan plan = annotatedPlan.getPlan();
            planTypeViewModel.removePlanFromAllPlans(plan.getId());
            planTypeViewModel.addPlanToPlansInPlanType(createAnnotatedPlanView(annotatedPlan));
        }
        return planTypeViewModel;
    }

    public AnnotatedPlanView createAnnotatedPlanView(AnnotatedPlan annotatedPlan) {
        Plan plan = annotatedPlan.getPlan();
        if (plan.getMasterPlan()) {
            return new AnnotatedPlanView(annotatedPlan.getId(), plan.getName(), Types.MASTERPLAN, annotatedPlan
                    .isActivated(), plan.getId());
        } else {
            return new AnnotatedPlanView(annotatedPlan.getId(), plan.getName(), Types.PLAN, annotatedPlan
                    .isActivated(), plan.getId());
        }
    }
}
