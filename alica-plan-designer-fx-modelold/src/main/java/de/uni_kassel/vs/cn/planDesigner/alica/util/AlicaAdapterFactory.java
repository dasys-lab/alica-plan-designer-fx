/**
 */
package de.uni_kassel.vs.cn.planDesigner.alicamodel.util;

import java.util.Map;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the alicamodel.
 * It provides an adapter <code>createXXX</code> method for each class of the alicamodel.
 * <!-- end-user-doc -->
 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.AlicaPackage
 * @generated
 */
public class AlicaAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached alicamodel package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static AlicaPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AlicaAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = AlicaPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the alicamodel's package or is an instance object of the alicamodel.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AlicaSwitch<Adapter> modelSwitch =
		new AlicaSwitch<Adapter>() {
			@Override
			public Adapter caseTransition(Transition object) {
				return createTransitionAdapter();
			}
			@Override
			public Adapter caseCondition(Condition object) {
				return createConditionAdapter();
			}
			@Override
			public Adapter casePreCondition(PreCondition object) {
				return createPreConditionAdapter();
			}
			@Override
			public Adapter caseEntryPoint(EntryPoint object) {
				return createEntryPointAdapter();
			}
			@Override
			public Adapter caseTerminalState(TerminalState object) {
				return createTerminalStateAdapter();
			}
			@Override
			public Adapter caseSuccessState(SuccessState object) {
				return createSuccessStateAdapter();
			}
			@Override
			public Adapter caseFailureState(FailureState object) {
				return createFailureStateAdapter();
			}
			@Override
			public Adapter caseAbstractPlan(AbstractPlan object) {
				return createAbstractPlanAdapter();
			}
			@Override
			public Adapter caseBehaviour(Behaviour object) {
				return createBehaviourAdapter();
			}
			@Override
			public Adapter caseState(State object) {
				return createStateAdapter();
			}
			@Override
			public Adapter casePlan(Plan object) {
				return createPlanAdapter();
			}
			@Override
			public Adapter casePlanType(PlanType object) {
				return createPlanTypeAdapter();
			}
			@Override
			public Adapter caseRating(Rating object) {
				return createRatingAdapter();
			}
			@Override
			public Adapter casePostCondition(PostCondition object) {
				return createPostConditionAdapter();
			}
			@Override
			public Adapter caseRuntimeCondition(RuntimeCondition object) {
				return createRuntimeConditionAdapter();
			}
			@Override
			public Adapter casePlanElement(PlanElement object) {
				return createPlanElementAdapter();
			}
			@Override
			public Adapter caseTask(Task object) {
				return createTaskAdapter();
			}
			@Override
			public Adapter caseEStringToEStringMapEntry(Map.Entry<String, String> object) {
				return createEStringToEStringMapEntryAdapter();
			}
			@Override
			public Adapter caseRole(Role object) {
				return createRoleAdapter();
			}
			@Override
			public Adapter caseRoleSet(RoleSet object) {
				return createRoleSetAdapter();
			}
			@Override
			public Adapter caseELongToDoubleMapEntry(Map.Entry<Long, Double> object) {
				return createELongToDoubleMapEntryAdapter();
			}
			@Override
			public Adapter caseRoleDefinitionSet(RoleDefinitionSet object) {
				return createRoleDefinitionSetAdapter();
			}
			@Override
			public Adapter caseRoleTaskMapping(RoleTaskMapping object) {
				return createRoleTaskMappingAdapter();
			}
			@Override
			public Adapter caseCharacteristic(Characteristic object) {
				return createCharacteristicAdapter();
			}
			@Override
			public Adapter caseTaskGraph(TaskGraph object) {
				return createTaskGraphAdapter();
			}
			@Override
			public Adapter caseEdge(Edge object) {
				return createEdgeAdapter();
			}
			@Override
			public Adapter caseTaskWrapper(TaskWrapper object) {
				return createTaskWrapperAdapter();
			}
			@Override
			public Adapter caseInternalRoleTaskMapping(InternalRoleTaskMapping object) {
				return createInternalRoleTaskMappingAdapter();
			}
			@Override
			public Adapter caseNode(Node object) {
				return createNodeAdapter();
			}
			@Override
			public Adapter caseTaskRepository(TaskRepository object) {
				return createTaskRepositoryAdapter();
			}
			@Override
			public Adapter caseSynchronisation(Synchronisation object) {
				return createSynchronisationAdapter();
			}
			@Override
			public Adapter caseVariable(Variable object) {
				return createVariableAdapter();
			}
			@Override
			public Adapter caseParametrisation(Parametrisation object) {
				return createParametrisationAdapter();
			}
			@Override
			public Adapter caseAnnotatedPlan(AnnotatedPlan object) {
				return createAnnotatedPlanAdapter();
			}
			@Override
			public Adapter caseQuantifier(Quantifier object) {
				return createQuantifierAdapter();
			}
			@Override
			public Adapter caseForallAgents(ForallAgents object) {
				return createForallAgentsAdapter();
			}
			@Override
			public Adapter caseIInhabitable(IInhabitable object) {
				return createIInhabitableAdapter();
			}
			@Override
			public Adapter caseCapability(Capability object) {
				return createCapabilityAdapter();
			}
			@Override
			public Adapter caseCapValue(CapValue object) {
				return createCapValueAdapter();
			}
			@Override
			public Adapter caseCapabilityDefinitionSet(CapabilityDefinitionSet object) {
				return createCapabilityDefinitionSetAdapter();
			}
			@Override
			public Adapter casePlanningProblem(PlanningProblem object) {
				return createPlanningProblemAdapter();
			}
			@Override
			public Adapter casePlanner(Planner object) {
				return createPlannerAdapter();
			}
			@Override
			public Adapter caseFluent(Fluent object) {
				return createFluentAdapter();
			}
			@Override
			public Adapter caseDomainDescription(DomainDescription object) {
				return createDomainDescriptionAdapter();
			}
			@Override
			public Adapter casePlanners(Planners object) {
				return createPlannersAdapter();
			}
			@Override
			public Adapter caseEStringToEObjectMapEntry(Map.Entry<String, Object> object) {
				return createEStringToEObjectMapEntryAdapter();
			}
			@Override
			public Adapter caseFluentParameters(FluentParameters object) {
				return createFluentParametersAdapter();
			}
			@Override
			public Adapter caseConstant(Constant object) {
				return createConstantAdapter();
			}
			@Override
			public Adapter caseBehaviourCreator(BehaviourCreator object) {
				return createBehaviourCreatorAdapter();
			}
			@Override
			public Adapter caseConditionCreator(ConditionCreator object) {
				return createConditionCreatorAdapter();
			}
			@Override
			public Adapter caseUtilityFunctionCreator(UtilityFunctionCreator object) {
				return createUtilityFunctionCreatorAdapter();
			}
			@Override
			public Adapter caseConstraintCreator(ConstraintCreator object) {
				return createConstraintCreatorAdapter();
			}
			@Override
			public Adapter caseDomainBehaviour(DomainBehaviour object) {
				return createDomainBehaviourAdapter();
			}
			@Override
			public Adapter caseDomainCondition(DomainCondition object) {
				return createDomainConditionAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Transition <em>Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Transition
	 * @generated
	 */
	public Adapter createTransitionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Condition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Condition
	 * @generated
	 */
	public Adapter createConditionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.PreCondition <em>Pre Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.PreCondition
	 * @generated
	 */
	public Adapter createPreConditionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.EntryPoint <em>Entry Point</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.EntryPoint
	 * @generated
	 */
	public Adapter createEntryPointAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.TerminalState <em>Terminal State</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.TerminalState
	 * @generated
	 */
	public Adapter createTerminalStateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.SuccessState <em>Success State</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.SuccessState
	 * @generated
	 */
	public Adapter createSuccessStateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.FailureState <em>Failure State</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.FailureState
	 * @generated
	 */
	public Adapter createFailureStateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.AbstractPlan <em>Abstract Plan</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.AbstractPlan
	 * @generated
	 */
	public Adapter createAbstractPlanAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour <em>Behaviour</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour
	 * @generated
	 */
	public Adapter createBehaviourAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.State <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.State
	 * @generated
	 */
	public Adapter createStateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan <em>Plan</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan
	 * @generated
	 */
	public Adapter createPlanAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanType <em>Plan Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanType
	 * @generated
	 */
	public Adapter createPlanTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Rating <em>Rating</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Rating
	 * @generated
	 */
	public Adapter createRatingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.PostCondition <em>Post Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.PostCondition
	 * @generated
	 */
	public Adapter createPostConditionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.RuntimeCondition <em>Runtime Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.RuntimeCondition
	 * @generated
	 */
	public Adapter createRuntimeConditionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement <em>Plan Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement
	 * @generated
	 */
	public Adapter createPlanElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Task <em>Task</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Task
	 * @generated
	 */
	public Adapter createTaskAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>EString To EString Map Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see java.util.Map.Entry
	 * @generated
	 */
	public Adapter createEStringToEStringMapEntryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Role <em>Role</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Role
	 * @generated
	 */
	public Adapter createRoleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.RoleSet <em>Role Set</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.RoleSet
	 * @generated
	 */
	public Adapter createRoleSetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>ELong To Double Map Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see java.util.Map.Entry
	 * @generated
	 */
	public Adapter createELongToDoubleMapEntryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.RoleDefinitionSet <em>Role Definition Set</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.RoleDefinitionSet
	 * @generated
	 */
	public Adapter createRoleDefinitionSetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.RoleTaskMapping <em>Role Task Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.RoleTaskMapping
	 * @generated
	 */
	public Adapter createRoleTaskMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Characteristic <em>Characteristic</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Characteristic
	 * @generated
	 */
	public Adapter createCharacteristicAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.TaskGraph <em>Task Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.TaskGraph
	 * @generated
	 */
	public Adapter createTaskGraphAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Edge <em>Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Edge
	 * @generated
	 */
	public Adapter createEdgeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.TaskWrapper <em>Task Wrapper</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.TaskWrapper
	 * @generated
	 */
	public Adapter createTaskWrapperAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.InternalRoleTaskMapping <em>Internal Role Task Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.InternalRoleTaskMapping
	 * @generated
	 */
	public Adapter createInternalRoleTaskMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Node <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Node
	 * @generated
	 */
	public Adapter createNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.TaskRepository <em>Task Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.TaskRepository
	 * @generated
	 */
	public Adapter createTaskRepositoryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Synchronisation <em>Synchronisation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Synchronisation
	 * @generated
	 */
	public Adapter createSynchronisationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Variable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Variable
	 * @generated
	 */
	public Adapter createVariableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Parametrisation <em>Parametrisation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Parametrisation
	 * @generated
	 */
	public Adapter createParametrisationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.AnnotatedPlan <em>Annotated Plan</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.AnnotatedPlan
	 * @generated
	 */
	public Adapter createAnnotatedPlanAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Quantifier <em>Quantifier</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Quantifier
	 * @generated
	 */
	public Adapter createQuantifierAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.ForallAgents <em>Forall Agents</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.ForallAgents
	 * @generated
	 */
	public Adapter createForallAgentsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.IInhabitable <em>IInhabitable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.IInhabitable
	 * @generated
	 */
	public Adapter createIInhabitableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Capability <em>Capability</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Capability
	 * @generated
	 */
	public Adapter createCapabilityAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.CapValue <em>Cap Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.CapValue
	 * @generated
	 */
	public Adapter createCapValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.CapabilityDefinitionSet <em>Capability Definition Set</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.CapabilityDefinitionSet
	 * @generated
	 */
	public Adapter createCapabilityDefinitionSetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanningProblem <em>Planning Problem</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanningProblem
	 * @generated
	 */
	public Adapter createPlanningProblemAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Planner <em>Planner</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Planner
	 * @generated
	 */
	public Adapter createPlannerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Fluent <em>Fluent</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Fluent
	 * @generated
	 */
	public Adapter createFluentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.DomainDescription <em>Domain Description</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.DomainDescription
	 * @generated
	 */
	public Adapter createDomainDescriptionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Planners <em>Planners</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Planners
	 * @generated
	 */
	public Adapter createPlannersAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>EString To EObject Map Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see java.util.Map.Entry
	 * @generated
	 */
	public Adapter createEStringToEObjectMapEntryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.FluentParameters <em>Fluent Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.FluentParameters
	 * @generated
	 */
	public Adapter createFluentParametersAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Constant <em>Constant</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Constant
	 * @generated
	 */
	public Adapter createConstantAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.BehaviourCreator <em>Behaviour Creator</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.BehaviourCreator
	 * @generated
	 */
	public Adapter createBehaviourCreatorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.ConditionCreator <em>Condition Creator</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.ConditionCreator
	 * @generated
	 */
	public Adapter createConditionCreatorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.UtilityFunctionCreator <em>Utility Function Creator</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.UtilityFunctionCreator
	 * @generated
	 */
	public Adapter createUtilityFunctionCreatorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.ConstraintCreator <em>Constraint Creator</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.ConstraintCreator
	 * @generated
	 */
	public Adapter createConstraintCreatorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.DomainBehaviour <em>Domain Behaviour</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.DomainBehaviour
	 * @generated
	 */
	public Adapter createDomainBehaviourAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.DomainCondition <em>Domain Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.DomainCondition
	 * @generated
	 */
	public Adapter createDomainConditionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //AlicaAdapterFactory
