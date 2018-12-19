package de.unikassel.vs.alica.planDesigner.ViewModelFactory;

import de.unikassel.vs.alica.planDesigner.alicamodel.*;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.BendPoint;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.model.*;
import de.unikassel.vs.alica.planDesigner.view.repo.RepositoryViewModel;

import java.util.HashMap;
import java.util.Map;

public class ViewModelManager {

    protected ModelManager modelManager;
    protected Map<Long, ViewModelElement> viewModelElements;

    public ViewModelManager(ModelManager modelManager) {
        this.modelManager = modelManager;
        this.viewModelElements = new HashMap<>();
    }

    public RepositoryViewModel createRepositoryViewModel() {
        return new RepositoryViewModel();
    }

    /**
     * Just returns an existing view model object, if it already exists.
     * Otherwise, it will create one according to the given planElement object.
     *
     * @param planElement The model object that corresponds to the wanted view model object.
     * @return the view model object
     */
    public ViewModelElement getViewModelElement(PlanElement planElement) {
        if (planElement == null) {
            throw new NullPointerException("planElement was null");
        }

        ViewModelElement element = this.viewModelElements.get(planElement.getId());
        if (element != null) {
            return element;
        }

        if (planElement instanceof Behaviour) {
            element = createBehaviourViewModel((Behaviour) planElement);
        } else if (planElement instanceof Task) {
            element = createTaskViewModel((Task) planElement);
        } else if (planElement instanceof TaskRepository) {
            element = createTaskRepositoryViewModel((TaskRepository) planElement);
        } else if (planElement instanceof Plan) {
            element = createPlanViewModel((Plan) planElement);
        } else if (planElement instanceof PlanType) {
            element = createPlanTypeViewModel((PlanType) planElement);
        } else if (planElement instanceof State) {
            element = createStateViewModel((State) planElement);
        } else if (planElement instanceof AnnotatedPlan) {
            element = createAnnotatedPlanViewModel((AnnotatedPlan) planElement);
        } else if (planElement instanceof EntryPoint) {
            element = createEntryPointViewModel((EntryPoint) planElement);
        } else if (planElement instanceof Variable) {
            element = createVariableViewModel((Variable) planElement);
        } else if (planElement instanceof Transition) {
            element = createTransitionViewModel((Transition) planElement);
        } else if (planElement instanceof Synchronization) {
            element = createSynchronizationViewModel((Synchronization) planElement);
        } else if (planElement instanceof Quantifier) {
            element = createQuantifierViewModel((Quantifier) planElement);
        } else if (planElement instanceof Condition) {
            element = createConditionViewModel((Condition) planElement);
        } else if (planElement instanceof BendPoint) {
            element = createBendPointViewModel((BendPoint) planElement);
        } else {
            System.err.println("ViewModelManager: getViewModelElement for type " + planElement.getClass().toString() + " not implemented!");
        }

        viewModelElements.put(planElement.getId(), element);
        return element;
    }

    private BendPointViewModel createBendPointViewModel(BendPoint bendPoint) {
        BendPointViewModel bendPointViewModel = new BendPointViewModel(bendPoint.getId(), bendPoint.getName(), Types.BENDPOINT);
        bendPointViewModel.setX(bendPoint.getX());
        bendPointViewModel.setY(bendPoint.getY());
        bendPointViewModel.setTransition((TransitionViewModel) getViewModelElement(bendPoint.getTransition()));
        return bendPointViewModel;
    }

    private TaskRepositoryViewModel createTaskRepositoryViewModel(TaskRepository taskRepository) {
        TaskRepositoryViewModel taskRepositoryViewModel = new TaskRepositoryViewModel(taskRepository.getId(), taskRepository.getName(), Types.TASKREPOSITORY);
        taskRepositoryViewModel.setComment(taskRepository.getComment());
        taskRepositoryViewModel.setRelativeDirectory(taskRepository.getRelativeDirectory());
        // we need to add the repo before creating tasks, in order to avoid circles (Task <-> Repo)
        this.viewModelElements.put(taskRepositoryViewModel.getId(), taskRepositoryViewModel);
        for (Task task : taskRepository.getTasks()) {
            taskRepositoryViewModel.addTask((TaskViewModel) getViewModelElement(task));
        }
        return taskRepositoryViewModel;
    }

    private TaskViewModel createTaskViewModel(Task task) {
        TaskViewModel taskViewModel = new TaskViewModel(task.getId(), task.getName(), Types.TASK);
        taskViewModel.setTaskRepositoryViewModel((TaskRepositoryViewModel) getViewModelElement(task.getTaskRepository()));
        taskViewModel.getTaskRepositoryViewModel().addTask(taskViewModel);
        taskViewModel.setParentId(task.getTaskRepository().getId());
        return taskViewModel;
    }

    private BehaviourViewModel createBehaviourViewModel(Behaviour behaviour) {
        BehaviourViewModel behaviourViewModel = new BehaviourViewModel(behaviour.getId(), behaviour.getName(), Types.BEHAVIOUR);
        behaviourViewModel.setComment(behaviour.getComment());
        behaviourViewModel.setRelativeDirectory(behaviour.getRelativeDirectory());
        behaviourViewModel.setFrequency(behaviour.getFrequency());
        behaviourViewModel.setDeferring(behaviour.getDeferring());

        for (Variable variable : behaviour.getVariables()) {
            behaviourViewModel.getVariables().add((VariableViewModel) getViewModelElement(variable));
        }

        if (behaviour.getPreCondition() != null) {
            ConditionViewModel preConditionViewModel = (ConditionViewModel) getViewModelElement(behaviour.getPreCondition());
            preConditionViewModel.setParentId(behaviour.getId());
            behaviourViewModel.setPreCondition(preConditionViewModel);
        }

        if (behaviour.getRuntimeCondition() != null) {
            ConditionViewModel runtimeConditionViewModel = (ConditionViewModel) getViewModelElement(behaviour.getRuntimeCondition());
            runtimeConditionViewModel.setParentId(behaviour.getId());
            behaviourViewModel.setRuntimeCondition(runtimeConditionViewModel);
        }

        if (behaviour.getPostCondition() != null) {
            ConditionViewModel postConditionViewModel = (ConditionViewModel) getViewModelElement(behaviour.getPostCondition());
            postConditionViewModel.setParentId(behaviour.getId());
            behaviourViewModel.setPostCondition(postConditionViewModel);
        }

        return behaviourViewModel;
    }

    private VariableViewModel createVariableViewModel(Variable var) {
        VariableViewModel variableViewModel = new VariableViewModel(var.getId(), var.getName(), Types.VARIABLE);
        variableViewModel.setVariableType(var.getType());
        return variableViewModel;
    }

    private ConditionViewModel createConditionViewModel(Condition condition) {
        ConditionViewModel conditionViewModel = null;
        if (condition instanceof PreCondition) {
            conditionViewModel = new ConditionViewModel(condition.getId(), condition.getName(), Types.PRECONDITION);
        } else if (condition instanceof RuntimeCondition) {
            conditionViewModel = new ConditionViewModel(condition.getId(), condition.getName(), Types.RUNTIMECONDITION);
        } else if (condition instanceof PostCondition) {
            conditionViewModel = new ConditionViewModel(condition.getId(), condition.getName(), Types.POSTCONDITION);
        }
        conditionViewModel.setConditionString(condition.getConditionString());
        conditionViewModel.setEnabled(condition.getEnabled());
        conditionViewModel.setPluginName(condition.getPluginName());
        conditionViewModel.setComment(condition.getComment());
        for (Variable var : condition.getVariables()) {
            conditionViewModel.getVars().add((VariableViewModel) getViewModelElement(var));
        }
        for (Quantifier quantifier : condition.getQuantifiers()) {
            // TODO: Quantifier is not very clean or fully implemented, yet.
            conditionViewModel.getQuantifier().add((QuantifierViewModel) getViewModelElement(quantifier));
        }
        return conditionViewModel;
    }

    private QuantifierViewModel createQuantifierViewModel(Quantifier quantifier) {
        return new QuantifierViewModel(quantifier.getId(), quantifier.getName(), Types.QUANTIFIER);
    }

    private PlanTypeViewModel createPlanTypeViewModel(PlanType planType) {
        PlanTypeViewModel planTypeViewModel = new PlanTypeViewModel(planType.getId(), planType.getName(),
                Types.PLANTYPE);
        planTypeViewModel.setRelativeDirectory(planType.getRelativeDirectory());
        planTypeViewModel.setComment(planType.getComment());

        for (Plan plan : modelManager.getPlans()) {
            planTypeViewModel.addPlanToAllPlans((PlanViewModel) getViewModelElement(plan));
        }

        for (AnnotatedPlan annotatedPlan : planType.getPlans()) {
            Plan plan = annotatedPlan.getPlan();
            planTypeViewModel.removePlanFromAllPlans(plan.getId());
            planTypeViewModel.getPlansInPlanType().add((AnnotatedPlanView) getViewModelElement(annotatedPlan));
        }
        return planTypeViewModel;
    }

    private AnnotatedPlanView createAnnotatedPlanViewModel(AnnotatedPlan annotatedPlan) {
        Plan plan = annotatedPlan.getPlan();
        return new AnnotatedPlanView(annotatedPlan.getId(), plan.getName(), Types.ANNOTATEDPLAN, annotatedPlan
                .isActivated(), plan.getId());
    }

    private StateViewModel createStateViewModel(State state) {
        String type;
        if (state instanceof TerminalState) {
            type = ((TerminalState) state).isSuccess() ? Types.SUCCESSSTATE : Types.FAILURESTATE;
        } else {
            type = Types.STATE;
        }
        StateViewModel stateViewModel = new StateViewModel(state.getId(), state.getName(), type);
        stateViewModel.setComment(state.getComment());
        stateViewModel.setParentId(state.getParentPlan().getId());

        for (AbstractPlan abstractPlan : state.getPlans()) {
            stateViewModel.getPlanElements().add((PlanElementViewModel) getViewModelElement(modelManager.getPlanElement(abstractPlan.getId())));
        }
        if (state.getEntryPoint() != null) {
            stateViewModel.setEntryPoint((EntryPointViewModel) getViewModelElement(modelManager.getPlanElement(state.getEntryPoint().getId())));
        }
        return stateViewModel;
    }

    private EntryPointViewModel createEntryPointViewModel(EntryPoint ep) {
        EntryPointViewModel entryPointViewModel = new EntryPointViewModel(ep.getId(), ep.getName(), Types.ENTRYPOINT);
        // we need to add the ep before creating the state, in order to avoid circles (EntryPoint <-> State)
        this.viewModelElements.put(entryPointViewModel.getId(), entryPointViewModel);
        if (ep.getState() != null) {
            StateViewModel entryState = (StateViewModel) getViewModelElement(modelManager.getPlanElement(ep.getState().getId()));
            entryPointViewModel.setState(entryState);
            entryState.setEntryPoint(entryPointViewModel);
        }
        if (ep.getTask() != null) {
            entryPointViewModel.setTask((TaskViewModel) getViewModelElement(ep.getTask()));
        }
        entryPointViewModel.setParentId(ep.getPlan().getId());
        return entryPointViewModel;
    }

    private TransitionViewModel createTransitionViewModel(Transition transition) {
        TransitionViewModel transitionViewModel = new TransitionViewModel(transition.getId(), transition.getName(), Types.TRANSITION);
        transitionViewModel.setInState((StateViewModel) getViewModelElement(transition.getInState()));
        transitionViewModel.setOutState((StateViewModel) getViewModelElement(transition.getOutState()));
        if (transition.getPreCondition() != null) {
            ConditionViewModel conditionViewModel = (ConditionViewModel) getViewModelElement(transition.getPreCondition());
            conditionViewModel.setParentId(transition.getId());
            transitionViewModel.setPreCondition(conditionViewModel);
        }
        return transitionViewModel;
    }

    private SynchronizationViewModel createSynchronizationViewModel(Synchronization synchronization) {
        SynchronizationViewModel synchronizationViewModel = new SynchronizationViewModel(synchronization.getId(), synchronization.getName(),
                Types.SYNCHRONIZATION);
        for (Transition transition : synchronization.getSyncedTransitions()) {
            synchronizationViewModel.getTransitions().add((TransitionViewModel) getViewModelElement(transition));
        }
        return synchronizationViewModel;
    }


    private PlanViewModel createPlanViewModel(Plan plan) {
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
            planViewModel.getStates().add(
                    (StateViewModel) getViewModelElement(state));
        }
        for (EntryPoint ep : plan.getEntryPoints()) {
            planViewModel.getEntryPoints().add((EntryPointViewModel) getViewModelElement(ep));
        }
        for (Transition transition : plan.getTransitions()) {
            planViewModel.getTransitions().add((TransitionViewModel) getViewModelElement(transition));
        }
        for (Synchronization synchronization : plan.getSynchronizations()) {
            planViewModel.getSynchronisations().add((SynchronizationViewModel) getViewModelElement(synchronization));
        }
        if (plan.getPreCondition() != null) {
            ConditionViewModel conditionViewModel = (ConditionViewModel) getViewModelElement(plan.getPreCondition());
            conditionViewModel.setParentId(plan.getId());
            planViewModel.getConditions().add(conditionViewModel);
        }
        if (plan.getRuntimeCondition() != null) {
            ConditionViewModel conditionViewModel = (ConditionViewModel) getViewModelElement(plan.getRuntimeCondition());
            conditionViewModel.setParentId(plan.getId());
            planViewModel.getConditions().add(conditionViewModel);
        }
        return planViewModel;
    }

    public void removeElement(long parentId, ViewModelElement viewModelElement) {
        switch (viewModelElement.getType()) {
            case Types.TASK:
                ((TaskViewModel) viewModelElement).getTaskRepositoryViewModel().removeTask(viewModelElement.getId());
                break;
            case Types.STATE:
            case Types.SUCCESSSTATE:
            case Types.FAILURESTATE:
                StateViewModel stateViewModel = (StateViewModel) viewModelElement;
                PlanViewModel planViewModel = (PlanViewModel) getViewModelElement(modelManager.getPlanElement(stateViewModel.getParentId()));

                planViewModel.getStates().remove(stateViewModel);
                break;
            case Types.ENTRYPOINT:
                EntryPointViewModel entryPointViewModel = (EntryPointViewModel) viewModelElement;
                planViewModel = (PlanViewModel) getViewModelElement(modelManager.getPlanElement(entryPointViewModel.getParentId()));

                planViewModel.getEntryPoints().remove(entryPointViewModel);
                if (entryPointViewModel.getState() != null) {
                    StateViewModel entryState = entryPointViewModel.getState();
                    entryState.setEntryPoint(null);
                }
                break;
            case Types.ANNOTATEDPLAN:
                AnnotatedPlanView annotatedPlanView = (AnnotatedPlanView) viewModelElement;
                PlanTypeViewModel planTypeViewModel = (PlanTypeViewModel) getViewModelElement(modelManager.getPlanElement(parentId));
                planTypeViewModel.getPlansInPlanType().remove(annotatedPlanView);
                break;
            default:
                System.err.println("Remove Element not supported for type: " + viewModelElement.getType());
                //TODO: handle other types
        }

        viewModelElements.remove(viewModelElement.getId());
    }

    public void addElement(long parentId, ViewModelElement viewModelElement) {
        switch (viewModelElement.getType()) {
            case Types.ANNOTATEDPLAN:
                AnnotatedPlanView annotatedPlanView = (AnnotatedPlanView) viewModelElement;
                PlanTypeViewModel planTypeViewModel = (PlanTypeViewModel) getViewModelElement(modelManager.getPlanElement(parentId));
                planTypeViewModel.getPlansInPlanType().add(annotatedPlanView);
                break;
            default:
                System.err.println("Add Element not supported for type: " + viewModelElement.getType());
                //TODO: handle other types
        }
    }
}
