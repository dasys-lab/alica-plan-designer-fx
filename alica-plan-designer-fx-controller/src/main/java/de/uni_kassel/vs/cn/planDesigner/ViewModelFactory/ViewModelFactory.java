package de.uni_kassel.vs.cn.planDesigner.ViewModelFactory;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.*;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.view.Types;
import de.uni_kassel.vs.cn.planDesigner.view.model.*;
import de.uni_kassel.vs.cn.planDesigner.view.repo.RepositoryViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewModelFactory {

    protected ModelManager modelManager;
    protected Map<Long, ViewModelElement> viewModelElements;

    public ViewModelFactory(ModelManager modelManager) {
        this.modelManager = modelManager;
        this.viewModelElements = new HashMap<>();
    }

    public RepositoryViewModel createRepositoryViewModel() {
        return new RepositoryViewModel();
    }

    /**
     * Just returns an existing view model object, if it already exists.
     * Otherwise, it will create one according to the given planElement object.
     * @param planElement The model object that corresponds to the wanted view model object.
     * @return the view model object
     */
    public ViewModelElement getViewModelElement(PlanElement planElement) {
        if (planElement == null) {
            System.err.println("ViewModelFactory: Cannot create ViewModelElement from 'null'.");
            return null;
        }

        ViewModelElement element = this.viewModelElements.get(planElement.getId());
        if (element != null) {
            return element;
        }

        if (planElement instanceof Behaviour) {
            element = createBehaviourViewModel(planElement);
        } else if (planElement instanceof Task) {
            element =  createTaskViewModel(planElement);
        } else if (planElement instanceof TaskRepository) {
            element =  createTaskRepositoryViewModel(planElement);
        } else if (planElement instanceof Plan) {
            element =  createPlanViewModel((Plan)planElement);
        } else if (planElement instanceof  PlanType) {
            element = createPlanTypeViewModel((PlanType) planElement);
        } else {
            System.err.println("ViewModelFactory: getViewModelElement for type " + planElement.getClass().toString() + " not implemented!");
        }

        viewModelElements.put(planElement.getId(), element);
        return element;
    }

    private ViewModelElement createViewModelElement(PlanElement planElement, String type) {
        if (planElement instanceof SerializablePlanElement) {
            return new ViewModelElement(planElement.getId(), planElement.getName(), type, ((SerializablePlanElement) planElement).getRelativeDirectory());
        } else {
            return new ViewModelElement(planElement.getId(), planElement.getName(), type);
        }
    }

    private TaskRepositoryViewModel createTaskRepositoryViewModel(PlanElement planElement) {
        TaskRepositoryViewModel taskRepo = new TaskRepositoryViewModel(planElement.getId(), planElement.getName(), Types.TASKREPOSITORY);
        taskRepo.setComment(planElement.getComment());
        taskRepo.setRelativeDirectory(((TaskRepository) planElement).getRelativeDirectory());
        // we need to add the repo before creating tasks, in order to avoid circles (Task <-> Repo)
        this.viewModelElements.put(taskRepo.getId(), taskRepo);
        for (Task task : ((TaskRepository) planElement).getTasks()) {
            TaskViewModel taskElement = (TaskViewModel) getViewModelElement(task);
            taskRepo.addTask(taskElement);
        }
        return taskRepo;
    }

    private ViewModelElement createTaskViewModel(PlanElement planElement) {
        Task task = (Task) planElement;
        TaskViewModel taskViewModel = new TaskViewModel(task.getId(), task.getName(), Types.TASK);
        taskViewModel.setTaskRepositoryViewModel((TaskRepositoryViewModel) getViewModelElement(task.getTaskRepository()));
        taskViewModel.getTaskRepositoryViewModel().addTask(taskViewModel);
        taskViewModel.setParentId(task.getTaskRepository().getId());
        return taskViewModel;
    }

    private BehaviourViewModel createBehaviourViewModel(PlanElement planElement) {
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

    private ConditionViewModel getConditionViewModel(Condition condition, String type, long parentId) {
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

    private PlanTypeViewModel createPlanTypeViewModel(PlanType planType) {
        if (planType == null) {
            System.err.println("ViewModelFactory: Opening PlanTypeTab for unknown Id or not presented element is not a PlanType!");
            return null;
        }

        PlanTypeViewModel planTypeViewModel = new PlanTypeViewModel(planType.getId(), planType.getName(),
                Types.PLANTYPE);
        planTypeViewModel.setRelativeDirectory(planType.getRelativeDirectory());
        planTypeViewModel.setComment(planType.getComment());

        for (Plan plan : modelManager.getPlans()) {
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

    private AnnotatedPlanView createAnnotatedPlanView(AnnotatedPlan annotatedPlan) {
        Plan plan = annotatedPlan.getPlan();
        if (plan.getMasterPlan()) {
            return new AnnotatedPlanView(annotatedPlan.getId(), plan.getName(), Types.MASTERPLAN, annotatedPlan
                    .isActivated(), plan.getId());
        } else {
            return new AnnotatedPlanView(annotatedPlan.getId(), plan.getName(), Types.PLAN, annotatedPlan
                    .isActivated(), plan.getId());
        }
    }

    public PlanViewModel createPlanViewModel(Plan plan) {
        PlanViewModel planViewModel;
        if (plan.getMasterPlan()) {
            planViewModel = new PlanViewModel(plan.getId(), plan.getName(), Types.MASTERPLAN);
        } else {
            planViewModel = new PlanViewModel(plan.getId(), plan.getName(), Types.PLAN);
        }
        planViewModel.setMasterPlan(plan.getMasterPlan());
        planViewModel.setUtilityThreshold(plan.getUtilityThreshold());
        planViewModel.setComment(plan.getComment());
        planViewModel.setRelativeDirectory(plan.getRelativeDirectory());
        for (State state : plan.getStates()) {
            planViewModel.getStates().add(new StateViewModel(state.getId(), state.getName(), Types.STATE));
            System.out.println("ViewModelFactory: abstract plans :");
            for(AbstractPlan abstractPlan : state.getPlans()) {
                //TODO add
                System.out.println("ViewModelFactory: abstract plan :" + abstractPlan.getName());
            }
        }
        for (EntryPoint ep : plan.getEntryPoints()) {
            EntryPointViewModel entryPointViewModel = new EntryPointViewModel(ep.getId(), ep.getName(), Types.ENTRYPOINT);
            for (StateViewModel stateViewModel : planViewModel.getStates()) {
                if (stateViewModel.getId() == ep.getState().getId()) {
                    entryPointViewModel.setState(stateViewModel);
                    stateViewModel.setEntryPoint(entryPointViewModel);
                    break;
                }
            }
            entryPointViewModel.setTask(new PlanElementViewModel(ep.getTask().getId(), ep.getTask().getName(), Types.TASK));
            planViewModel.getEntryPoints().add(entryPointViewModel);
        }
        for (Transition transition : plan.getTransitions()) {
            TransitionViewModel transitionViewModel = new TransitionViewModel(transition.getId(), transition.getName(), Types.TRANSITION);
            boolean inStateFound = false;
            boolean outStateFound = false;
            for (StateViewModel stateViewModel : planViewModel.getStates()) {
                if (stateViewModel.getId() == transition.getInState().getId()) {
                    transitionViewModel.setInState(stateViewModel);
                    stateViewModel.getInTransitions().add(transitionViewModel);
                    inStateFound = true;
                }
                if (stateViewModel.getId() == transition.getOutState().getId()) {
                    transitionViewModel.setOutState(stateViewModel);
                    stateViewModel.getOutTransitions().add(transitionViewModel);
                    outStateFound = true;
                }
                if (inStateFound && outStateFound) {
                    break;
                }
            }
            if (transition.getPreCondition() != null) {
                ConditionViewModel conditionViewModel = new ConditionViewModel(transition.getPreCondition().getId(), transition.getPreCondition().getName(),
                        Types.PRECONDITION);
                transitionViewModel.setPreCondition(conditionViewModel);
                planViewModel.getConditions().add(conditionViewModel);
            }
        }
        for (Synchronization synchronization : plan.getSynchronizations()) {
            SynchronizationViewModel synchronizationViewModel = new SynchronizationViewModel(synchronization.getId(), synchronization.getName(),
                    Types.SYNCHRONIZATION);
            for (Transition transition : synchronization.getSyncedTransitions()) {
                for (TransitionViewModel trans : planViewModel.getTransitions()) {
                    if (trans.getId() == transition.getId()) {
                        synchronizationViewModel.getTransitions().add(trans);
                        break;
                    }
                }
            }

            planViewModel.getSynchronisations().add(synchronizationViewModel);
        }
        if (plan.getPreCondition() != null) {
            planViewModel.getConditions().add(new ConditionViewModel(plan.getPreCondition().getId(), plan.getPreCondition().getName(),
                    Types.PRECONDITION));
        }
        if (plan.getRuntimeCondition() != null) {
            planViewModel.getConditions().add(new ConditionViewModel(plan.getRuntimeCondition().getId(), plan.getRuntimeCondition().getName(),
                    Types.RUNTIMECONDITION));
        }
        return planViewModel;
    }

    public void removeElement(ViewModelElement viewModelElement) {
        switch (viewModelElement.getType()) {
            case Types.TASK:
                ((TaskViewModel) viewModelElement).getTaskRepositoryViewModel().removeTask(viewModelElement.getId());
                break;
        }

        viewModelElements.remove(viewModelElement.getId());
    }
}
