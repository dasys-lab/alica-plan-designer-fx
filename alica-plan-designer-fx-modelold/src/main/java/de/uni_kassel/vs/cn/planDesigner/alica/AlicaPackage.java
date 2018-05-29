/**
 */
package de.uni_kassel.vs.cn.planDesigner.alicamodel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the alicamodel.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.AlicaFactory
 * @model kind="package"
 * @generated
 */
public interface AlicaPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "alicamodel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///de.uni_kassel.vs.cn";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "alicamodel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	AlicaPackage eINSTANCE = de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl.init();

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.PlanElementImpl <em>Plan Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.PlanElementImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getPlanElement()
	 * @generated
	 */
	int PLAN_ELEMENT = 15;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_ELEMENT__ID = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_ELEMENT__NAME = 1;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_ELEMENT__COMMENT = 2;

	/**
	 * The number of structural features of the '<em>Plan Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_ELEMENT_FEATURE_COUNT = 3;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_ELEMENT___GENERATE_ID = 0;

	/**
	 * The number of operations of the '<em>Plan Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_ELEMENT_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.TransitionImpl <em>Transition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.TransitionImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getTransition()
	 * @generated
	 */
	int TRANSITION = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Msg</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__MSG = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Pre Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__PRE_CONDITION = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>In State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__IN_STATE = PLAN_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Out State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__OUT_STATE = PLAN_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Synchronisation</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__SYNCHRONISATION = PLAN_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Transition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION___GENERATE_ID = PLAN_ELEMENT___GENERATE_ID;

	/**
	 * The number of operations of the '<em>Transition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION_OPERATION_COUNT = PLAN_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.ConditionImpl <em>Condition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.ConditionImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getCondition()
	 * @generated
	 */
	int CONDITION = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Condition String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION__CONDITION_STRING = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Vars</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION__VARS = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Quantifiers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION__QUANTIFIERS = PLAN_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Plugin Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION__PLUGIN_NAME = PLAN_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION__PARAMETERS = PLAN_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION___GENERATE_ID = PLAN_ELEMENT___GENERATE_ID;

	/**
	 * The operation id for the '<em>Ensure Variable Consistency</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION___ENSURE_VARIABLE_CONSISTENCY__ABSTRACTPLAN = PLAN_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION_OPERATION_COUNT = PLAN_ELEMENT_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.PreConditionImpl <em>Pre Condition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.PreConditionImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getPreCondition()
	 * @generated
	 */
	int PRE_CONDITION = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRE_CONDITION__ID = CONDITION__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRE_CONDITION__NAME = CONDITION__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRE_CONDITION__COMMENT = CONDITION__COMMENT;

	/**
	 * The feature id for the '<em><b>Condition String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRE_CONDITION__CONDITION_STRING = CONDITION__CONDITION_STRING;

	/**
	 * The feature id for the '<em><b>Vars</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRE_CONDITION__VARS = CONDITION__VARS;

	/**
	 * The feature id for the '<em><b>Quantifiers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRE_CONDITION__QUANTIFIERS = CONDITION__QUANTIFIERS;

	/**
	 * The feature id for the '<em><b>Plugin Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRE_CONDITION__PLUGIN_NAME = CONDITION__PLUGIN_NAME;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRE_CONDITION__PARAMETERS = CONDITION__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRE_CONDITION__ENABLED = CONDITION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Pre Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRE_CONDITION_FEATURE_COUNT = CONDITION_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRE_CONDITION___GENERATE_ID = CONDITION___GENERATE_ID;

	/**
	 * The operation id for the '<em>Ensure Variable Consistency</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRE_CONDITION___ENSURE_VARIABLE_CONSISTENCY__ABSTRACTPLAN = CONDITION___ENSURE_VARIABLE_CONSISTENCY__ABSTRACTPLAN;

	/**
	 * The number of operations of the '<em>Pre Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRE_CONDITION_OPERATION_COUNT = CONDITION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.IInhabitable <em>IInhabitable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.IInhabitable
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getIInhabitable()
	 * @generated
	 */
	int IINHABITABLE = 36;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IINHABITABLE__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IINHABITABLE__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IINHABITABLE__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The number of structural features of the '<em>IInhabitable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IINHABITABLE_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IINHABITABLE___GENERATE_ID = PLAN_ELEMENT___GENERATE_ID;

	/**
	 * The number of operations of the '<em>IInhabitable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IINHABITABLE_OPERATION_COUNT = PLAN_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.EntryPointImpl <em>Entry Point</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.EntryPointImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getEntryPoint()
	 * @generated
	 */
	int ENTRY_POINT = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_POINT__ID = IINHABITABLE__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_POINT__NAME = IINHABITABLE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_POINT__COMMENT = IINHABITABLE__COMMENT;

	/**
	 * The feature id for the '<em><b>Task</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_POINT__TASK = IINHABITABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Success Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_POINT__SUCCESS_REQUIRED = IINHABITABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_POINT__STATE = IINHABITABLE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Min Cardinality</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_POINT__MIN_CARDINALITY = IINHABITABLE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Max Cardinality</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_POINT__MAX_CARDINALITY = IINHABITABLE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Plan</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_POINT__PLAN = IINHABITABLE_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Entry Point</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_POINT_FEATURE_COUNT = IINHABITABLE_FEATURE_COUNT + 6;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_POINT___GENERATE_ID = IINHABITABLE___GENERATE_ID;

	/**
	 * The number of operations of the '<em>Entry Point</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_POINT_OPERATION_COUNT = IINHABITABLE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.StateImpl <em>State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.StateImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getState()
	 * @generated
	 */
	int STATE = 9;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Plans</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__PLANS = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>In Plan</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__IN_PLAN = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Parametrisation</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__PARAMETRISATION = PLAN_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>In Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__IN_TRANSITIONS = PLAN_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Out Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__OUT_TRANSITIONS = PLAN_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Entry Point</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__ENTRY_POINT = PLAN_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE___GENERATE_ID = PLAN_ELEMENT___GENERATE_ID;

	/**
	 * The operation id for the '<em>Ensure Parametrisation Consistency</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE___ENSURE_PARAMETRISATION_CONSISTENCY = PLAN_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_OPERATION_COUNT = PLAN_ELEMENT_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.TerminalStateImpl <em>Terminal State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.TerminalStateImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getTerminalState()
	 * @generated
	 */
	int TERMINAL_STATE = 4;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL_STATE__ID = STATE__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL_STATE__NAME = STATE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL_STATE__COMMENT = STATE__COMMENT;

	/**
	 * The feature id for the '<em><b>Plans</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL_STATE__PLANS = STATE__PLANS;

	/**
	 * The feature id for the '<em><b>In Plan</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL_STATE__IN_PLAN = STATE__IN_PLAN;

	/**
	 * The feature id for the '<em><b>Parametrisation</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL_STATE__PARAMETRISATION = STATE__PARAMETRISATION;

	/**
	 * The feature id for the '<em><b>In Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL_STATE__IN_TRANSITIONS = STATE__IN_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>Out Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL_STATE__OUT_TRANSITIONS = STATE__OUT_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>Entry Point</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL_STATE__ENTRY_POINT = STATE__ENTRY_POINT;

	/**
	 * The feature id for the '<em><b>Post Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL_STATE__POST_CONDITION = STATE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Terminal State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL_STATE_FEATURE_COUNT = STATE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL_STATE___GENERATE_ID = STATE___GENERATE_ID;

	/**
	 * The operation id for the '<em>Ensure Parametrisation Consistency</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL_STATE___ENSURE_PARAMETRISATION_CONSISTENCY = STATE___ENSURE_PARAMETRISATION_CONSISTENCY;

	/**
	 * The number of operations of the '<em>Terminal State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL_STATE_OPERATION_COUNT = STATE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.SuccessStateImpl <em>Success State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.SuccessStateImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getSuccessState()
	 * @generated
	 */
	int SUCCESS_STATE = 5;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUCCESS_STATE__ID = TERMINAL_STATE__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUCCESS_STATE__NAME = TERMINAL_STATE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUCCESS_STATE__COMMENT = TERMINAL_STATE__COMMENT;

	/**
	 * The feature id for the '<em><b>Plans</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUCCESS_STATE__PLANS = TERMINAL_STATE__PLANS;

	/**
	 * The feature id for the '<em><b>In Plan</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUCCESS_STATE__IN_PLAN = TERMINAL_STATE__IN_PLAN;

	/**
	 * The feature id for the '<em><b>Parametrisation</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUCCESS_STATE__PARAMETRISATION = TERMINAL_STATE__PARAMETRISATION;

	/**
	 * The feature id for the '<em><b>In Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUCCESS_STATE__IN_TRANSITIONS = TERMINAL_STATE__IN_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>Out Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUCCESS_STATE__OUT_TRANSITIONS = TERMINAL_STATE__OUT_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>Entry Point</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUCCESS_STATE__ENTRY_POINT = TERMINAL_STATE__ENTRY_POINT;

	/**
	 * The feature id for the '<em><b>Post Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUCCESS_STATE__POST_CONDITION = TERMINAL_STATE__POST_CONDITION;

	/**
	 * The number of structural features of the '<em>Success State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUCCESS_STATE_FEATURE_COUNT = TERMINAL_STATE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUCCESS_STATE___GENERATE_ID = TERMINAL_STATE___GENERATE_ID;

	/**
	 * The operation id for the '<em>Ensure Parametrisation Consistency</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUCCESS_STATE___ENSURE_PARAMETRISATION_CONSISTENCY = TERMINAL_STATE___ENSURE_PARAMETRISATION_CONSISTENCY;

	/**
	 * The number of operations of the '<em>Success State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUCCESS_STATE_OPERATION_COUNT = TERMINAL_STATE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.FailureStateImpl <em>Failure State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.FailureStateImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getFailureState()
	 * @generated
	 */
	int FAILURE_STATE = 6;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAILURE_STATE__ID = TERMINAL_STATE__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAILURE_STATE__NAME = TERMINAL_STATE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAILURE_STATE__COMMENT = TERMINAL_STATE__COMMENT;

	/**
	 * The feature id for the '<em><b>Plans</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAILURE_STATE__PLANS = TERMINAL_STATE__PLANS;

	/**
	 * The feature id for the '<em><b>In Plan</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAILURE_STATE__IN_PLAN = TERMINAL_STATE__IN_PLAN;

	/**
	 * The feature id for the '<em><b>Parametrisation</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAILURE_STATE__PARAMETRISATION = TERMINAL_STATE__PARAMETRISATION;

	/**
	 * The feature id for the '<em><b>In Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAILURE_STATE__IN_TRANSITIONS = TERMINAL_STATE__IN_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>Out Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAILURE_STATE__OUT_TRANSITIONS = TERMINAL_STATE__OUT_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>Entry Point</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAILURE_STATE__ENTRY_POINT = TERMINAL_STATE__ENTRY_POINT;

	/**
	 * The feature id for the '<em><b>Post Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAILURE_STATE__POST_CONDITION = TERMINAL_STATE__POST_CONDITION;

	/**
	 * The number of structural features of the '<em>Failure State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAILURE_STATE_FEATURE_COUNT = TERMINAL_STATE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAILURE_STATE___GENERATE_ID = TERMINAL_STATE___GENERATE_ID;

	/**
	 * The operation id for the '<em>Ensure Parametrisation Consistency</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAILURE_STATE___ENSURE_PARAMETRISATION_CONSISTENCY = TERMINAL_STATE___ENSURE_PARAMETRISATION_CONSISTENCY;

	/**
	 * The number of operations of the '<em>Failure State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAILURE_STATE_OPERATION_COUNT = TERMINAL_STATE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AbstractPlanImpl <em>Abstract Plan</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AbstractPlanImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getAbstractPlan()
	 * @generated
	 */
	int ABSTRACT_PLAN = 7;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_PLAN__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_PLAN__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_PLAN__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Destination Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_PLAN__DESTINATION_PATH = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Abstract Plan</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_PLAN_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_PLAN___GENERATE_ID = PLAN_ELEMENT___GENERATE_ID;

	/**
	 * The number of operations of the '<em>Abstract Plan</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_PLAN_OPERATION_COUNT = PLAN_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.BehaviourImpl <em>Behaviour</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.BehaviourImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getBehaviour()
	 * @generated
	 */
	int BEHAVIOUR = 8;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIOUR__ID = ABSTRACT_PLAN__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIOUR__NAME = ABSTRACT_PLAN__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIOUR__COMMENT = ABSTRACT_PLAN__COMMENT;

	/**
	 * The feature id for the '<em><b>Destination Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIOUR__DESTINATION_PATH = ABSTRACT_PLAN__DESTINATION_PATH;

	/**
	 * The feature id for the '<em><b>Pre Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIOUR__PRE_CONDITION = ABSTRACT_PLAN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Runtime Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIOUR__RUNTIME_CONDITION = ABSTRACT_PLAN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Post Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIOUR__POST_CONDITION = ABSTRACT_PLAN_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Frequency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIOUR__FREQUENCY = ABSTRACT_PLAN_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIOUR__VARS = ABSTRACT_PLAN_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Behaviour</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIOUR_FEATURE_COUNT = ABSTRACT_PLAN_FEATURE_COUNT + 5;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIOUR___GENERATE_ID = ABSTRACT_PLAN___GENERATE_ID;

	/**
	 * The number of operations of the '<em>Behaviour</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIOUR_OPERATION_COUNT = ABSTRACT_PLAN_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.PlanImpl <em>Plan</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.PlanImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getPlan()
	 * @generated
	 */
	int PLAN = 10;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__ID = ABSTRACT_PLAN__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__NAME = ABSTRACT_PLAN__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__COMMENT = ABSTRACT_PLAN__COMMENT;

	/**
	 * The feature id for the '<em><b>Destination Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__DESTINATION_PATH = ABSTRACT_PLAN__DESTINATION_PATH;

	/**
	 * The feature id for the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__PRIORITY = ABSTRACT_PLAN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>States</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__STATES = ABSTRACT_PLAN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Transitions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__TRANSITIONS = ABSTRACT_PLAN_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Min Cardinality</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__MIN_CARDINALITY = ABSTRACT_PLAN_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Max Cardinality</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__MAX_CARDINALITY = ABSTRACT_PLAN_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Synchronisations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__SYNCHRONISATIONS = ABSTRACT_PLAN_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Entry Points</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__ENTRY_POINTS = ABSTRACT_PLAN_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Master Plan</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__MASTER_PLAN = ABSTRACT_PLAN_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Utility Function</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__UTILITY_FUNCTION = ABSTRACT_PLAN_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Utility Threshold</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__UTILITY_THRESHOLD = ABSTRACT_PLAN_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__VARS = ABSTRACT_PLAN_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Pre Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__PRE_CONDITION = ABSTRACT_PLAN_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Runtime Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__RUNTIME_CONDITION = ABSTRACT_PLAN_FEATURE_COUNT + 12;

	/**
	 * The number of structural features of the '<em>Plan</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_FEATURE_COUNT = ABSTRACT_PLAN_FEATURE_COUNT + 13;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN___GENERATE_ID = ABSTRACT_PLAN___GENERATE_ID;

	/**
	 * The operation id for the '<em>Calculate Cardinalities</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN___CALCULATE_CARDINALITIES = ABSTRACT_PLAN_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Ensure Parametrisation Consistency</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN___ENSURE_PARAMETRISATION_CONSISTENCY = ABSTRACT_PLAN_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>Plan</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_OPERATION_COUNT = ABSTRACT_PLAN_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.PlanTypeImpl <em>Plan Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.PlanTypeImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getPlanType()
	 * @generated
	 */
	int PLAN_TYPE = 11;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_TYPE__ID = ABSTRACT_PLAN__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_TYPE__NAME = ABSTRACT_PLAN__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_TYPE__COMMENT = ABSTRACT_PLAN__COMMENT;

	/**
	 * The feature id for the '<em><b>Destination Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_TYPE__DESTINATION_PATH = ABSTRACT_PLAN__DESTINATION_PATH;

	/**
	 * The feature id for the '<em><b>Parametrisation</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_TYPE__PARAMETRISATION = ABSTRACT_PLAN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Plans</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_TYPE__PLANS = ABSTRACT_PLAN_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Plan Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_TYPE_FEATURE_COUNT = ABSTRACT_PLAN_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_TYPE___GENERATE_ID = ABSTRACT_PLAN___GENERATE_ID;

	/**
	 * The operation id for the '<em>Ensure Parametrisation Consistency</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_TYPE___ENSURE_PARAMETRISATION_CONSISTENCY = ABSTRACT_PLAN_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Plan Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_TYPE_OPERATION_COUNT = ABSTRACT_PLAN_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.RatingImpl <em>Rating</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.RatingImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getRating()
	 * @generated
	 */
	int RATING = 12;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATING__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATING__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATING__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The number of structural features of the '<em>Rating</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATING_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATING___GENERATE_ID = PLAN_ELEMENT___GENERATE_ID;

	/**
	 * The number of operations of the '<em>Rating</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATING_OPERATION_COUNT = PLAN_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.PostConditionImpl <em>Post Condition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.PostConditionImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getPostCondition()
	 * @generated
	 */
	int POST_CONDITION = 13;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST_CONDITION__ID = CONDITION__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST_CONDITION__NAME = CONDITION__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST_CONDITION__COMMENT = CONDITION__COMMENT;

	/**
	 * The feature id for the '<em><b>Condition String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST_CONDITION__CONDITION_STRING = CONDITION__CONDITION_STRING;

	/**
	 * The feature id for the '<em><b>Vars</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST_CONDITION__VARS = CONDITION__VARS;

	/**
	 * The feature id for the '<em><b>Quantifiers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST_CONDITION__QUANTIFIERS = CONDITION__QUANTIFIERS;

	/**
	 * The feature id for the '<em><b>Plugin Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST_CONDITION__PLUGIN_NAME = CONDITION__PLUGIN_NAME;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST_CONDITION__PARAMETERS = CONDITION__PARAMETERS;

	/**
	 * The number of structural features of the '<em>Post Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST_CONDITION_FEATURE_COUNT = CONDITION_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST_CONDITION___GENERATE_ID = CONDITION___GENERATE_ID;

	/**
	 * The operation id for the '<em>Ensure Variable Consistency</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST_CONDITION___ENSURE_VARIABLE_CONSISTENCY__ABSTRACTPLAN = CONDITION___ENSURE_VARIABLE_CONSISTENCY__ABSTRACTPLAN;

	/**
	 * The number of operations of the '<em>Post Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST_CONDITION_OPERATION_COUNT = CONDITION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.RuntimeConditionImpl <em>Runtime Condition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.RuntimeConditionImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getRuntimeCondition()
	 * @generated
	 */
	int RUNTIME_CONDITION = 14;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RUNTIME_CONDITION__ID = CONDITION__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RUNTIME_CONDITION__NAME = CONDITION__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RUNTIME_CONDITION__COMMENT = CONDITION__COMMENT;

	/**
	 * The feature id for the '<em><b>Condition String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RUNTIME_CONDITION__CONDITION_STRING = CONDITION__CONDITION_STRING;

	/**
	 * The feature id for the '<em><b>Vars</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RUNTIME_CONDITION__VARS = CONDITION__VARS;

	/**
	 * The feature id for the '<em><b>Quantifiers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RUNTIME_CONDITION__QUANTIFIERS = CONDITION__QUANTIFIERS;

	/**
	 * The feature id for the '<em><b>Plugin Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RUNTIME_CONDITION__PLUGIN_NAME = CONDITION__PLUGIN_NAME;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RUNTIME_CONDITION__PARAMETERS = CONDITION__PARAMETERS;

	/**
	 * The number of structural features of the '<em>Runtime Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RUNTIME_CONDITION_FEATURE_COUNT = CONDITION_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RUNTIME_CONDITION___GENERATE_ID = CONDITION___GENERATE_ID;

	/**
	 * The operation id for the '<em>Ensure Variable Consistency</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RUNTIME_CONDITION___ENSURE_VARIABLE_CONSISTENCY__ABSTRACTPLAN = CONDITION___ENSURE_VARIABLE_CONSISTENCY__ABSTRACTPLAN;

	/**
	 * The number of operations of the '<em>Runtime Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RUNTIME_CONDITION_OPERATION_COUNT = CONDITION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.TaskImpl <em>Task</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.TaskImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getTask()
	 * @generated
	 */
	int TASK = 16;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__DESCRIPTION = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Task</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK___GENERATE_ID = PLAN_ELEMENT___GENERATE_ID;

	/**
	 * The number of operations of the '<em>Task</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_OPERATION_COUNT = PLAN_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.EStringToEStringMapEntryImpl <em>EString To EString Map Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.EStringToEStringMapEntryImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getEStringToEStringMapEntry()
	 * @generated
	 */
	int ESTRING_TO_ESTRING_MAP_ENTRY = 17;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_ESTRING_MAP_ENTRY__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_ESTRING_MAP_ENTRY__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_ESTRING_MAP_ENTRY__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_ESTRING_MAP_ENTRY__KEY = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_ESTRING_MAP_ENTRY__VALUE = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>EString To EString Map Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_ESTRING_MAP_ENTRY_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_ESTRING_MAP_ENTRY___GENERATE_ID = PLAN_ELEMENT___GENERATE_ID;

	/**
	 * The number of operations of the '<em>EString To EString Map Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_ESTRING_MAP_ENTRY_OPERATION_COUNT = PLAN_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.RoleImpl <em>Role</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.RoleImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getRole()
	 * @generated
	 */
	int ROLE = 18;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Characteristics</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE__CHARACTERISTICS = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Role</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE___GENERATE_ID = PLAN_ELEMENT___GENERATE_ID;

	/**
	 * The number of operations of the '<em>Role</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_OPERATION_COUNT = PLAN_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.RoleSetImpl <em>Role Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.RoleSetImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getRoleSet()
	 * @generated
	 */
	int ROLE_SET = 19;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_SET__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_SET__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_SET__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Usable With Plan ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_SET__USABLE_WITH_PLAN_ID = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_SET__DEFAULT = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_SET__MAPPINGS = PLAN_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Role Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_SET_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_SET___GENERATE_ID = PLAN_ELEMENT___GENERATE_ID;

	/**
	 * The number of operations of the '<em>Role Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_SET_OPERATION_COUNT = PLAN_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.ELongToDoubleMapEntryImpl <em>ELong To Double Map Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.ELongToDoubleMapEntryImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getELongToDoubleMapEntry()
	 * @generated
	 */
	int ELONG_TO_DOUBLE_MAP_ENTRY = 20;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELONG_TO_DOUBLE_MAP_ENTRY__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELONG_TO_DOUBLE_MAP_ENTRY__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELONG_TO_DOUBLE_MAP_ENTRY__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELONG_TO_DOUBLE_MAP_ENTRY__KEY = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELONG_TO_DOUBLE_MAP_ENTRY__VALUE = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>ELong To Double Map Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELONG_TO_DOUBLE_MAP_ENTRY_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELONG_TO_DOUBLE_MAP_ENTRY___GENERATE_ID = PLAN_ELEMENT___GENERATE_ID;

	/**
	 * The number of operations of the '<em>ELong To Double Map Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELONG_TO_DOUBLE_MAP_ENTRY_OPERATION_COUNT = PLAN_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.RoleDefinitionSetImpl <em>Role Definition Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.RoleDefinitionSetImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getRoleDefinitionSet()
	 * @generated
	 */
	int ROLE_DEFINITION_SET = 21;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_DEFINITION_SET__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_DEFINITION_SET__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_DEFINITION_SET__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Roles</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_DEFINITION_SET__ROLES = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Role Definition Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_DEFINITION_SET_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_DEFINITION_SET___GENERATE_ID = PLAN_ELEMENT___GENERATE_ID;

	/**
	 * The number of operations of the '<em>Role Definition Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_DEFINITION_SET_OPERATION_COUNT = PLAN_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.RoleTaskMappingImpl <em>Role Task Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.RoleTaskMappingImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getRoleTaskMapping()
	 * @generated
	 */
	int ROLE_TASK_MAPPING = 22;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_TASK_MAPPING__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_TASK_MAPPING__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_TASK_MAPPING__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Task Priorities</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_TASK_MAPPING__TASK_PRIORITIES = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Role</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_TASK_MAPPING__ROLE = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Role Task Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_TASK_MAPPING_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_TASK_MAPPING___GENERATE_ID = PLAN_ELEMENT___GENERATE_ID;

	/**
	 * The number of operations of the '<em>Role Task Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_TASK_MAPPING_OPERATION_COUNT = PLAN_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.CharacteristicImpl <em>Characteristic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.CharacteristicImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getCharacteristic()
	 * @generated
	 */
	int CHARACTERISTIC = 23;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTERISTIC__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTERISTIC__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTERISTIC__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Weight</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTERISTIC__WEIGHT = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Capability</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTERISTIC__CAPABILITY = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTERISTIC__VALUE = PLAN_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Characteristic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTERISTIC_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTERISTIC___GENERATE_ID = PLAN_ELEMENT___GENERATE_ID;

	/**
	 * The number of operations of the '<em>Characteristic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTERISTIC_OPERATION_COUNT = PLAN_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.TaskGraphImpl <em>Task Graph</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.TaskGraphImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getTaskGraph()
	 * @generated
	 */
	int TASK_GRAPH = 24;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_GRAPH__NODES = 0;

	/**
	 * The feature id for the '<em><b>Edges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_GRAPH__EDGES = 1;

	/**
	 * The number of structural features of the '<em>Task Graph</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_GRAPH_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Task Graph</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_GRAPH_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.EdgeImpl <em>Edge</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.EdgeImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getEdge()
	 * @generated
	 */
	int EDGE = 25;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE__FROM = 0;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE__TO = 1;

	/**
	 * The number of structural features of the '<em>Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.NodeImpl <em>Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.NodeImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getNode()
	 * @generated
	 */
	int NODE = 28;

	/**
	 * The feature id for the '<em><b>In Edge</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__IN_EDGE = 0;

	/**
	 * The feature id for the '<em><b>Out Edge</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__OUT_EDGE = 1;

	/**
	 * The number of structural features of the '<em>Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.TaskWrapperImpl <em>Task Wrapper</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.TaskWrapperImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getTaskWrapper()
	 * @generated
	 */
	int TASK_WRAPPER = 26;

	/**
	 * The feature id for the '<em><b>In Edge</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_WRAPPER__IN_EDGE = NODE__IN_EDGE;

	/**
	 * The feature id for the '<em><b>Out Edge</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_WRAPPER__OUT_EDGE = NODE__OUT_EDGE;

	/**
	 * The feature id for the '<em><b>Task</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_WRAPPER__TASK = NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_WRAPPER__MAPPINGS = NODE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Task Wrapper</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_WRAPPER_FEATURE_COUNT = NODE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Task Wrapper</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_WRAPPER_OPERATION_COUNT = NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.InternalRoleTaskMappingImpl <em>Internal Role Task Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.InternalRoleTaskMappingImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getInternalRoleTaskMapping()
	 * @generated
	 */
	int INTERNAL_ROLE_TASK_MAPPING = 27;

	/**
	 * The feature id for the '<em><b>Role</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_ROLE_TASK_MAPPING__ROLE = 0;

	/**
	 * The feature id for the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_ROLE_TASK_MAPPING__PRIORITY = 1;

	/**
	 * The number of structural features of the '<em>Internal Role Task Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_ROLE_TASK_MAPPING_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Internal Role Task Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_ROLE_TASK_MAPPING_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.TaskRepositoryImpl <em>Task Repository</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.TaskRepositoryImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getTaskRepository()
	 * @generated
	 */
	int TASK_REPOSITORY = 29;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_REPOSITORY__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_REPOSITORY__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_REPOSITORY__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Tasks</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_REPOSITORY__TASKS = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Default Task</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_REPOSITORY__DEFAULT_TASK = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Task Repository</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_REPOSITORY_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_REPOSITORY___GENERATE_ID = PLAN_ELEMENT___GENERATE_ID;

	/**
	 * The operation id for the '<em>Create Default Task</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_REPOSITORY___CREATE_DEFAULT_TASK = PLAN_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Task Repository</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_REPOSITORY_OPERATION_COUNT = PLAN_ELEMENT_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.SynchronisationImpl <em>Synchronisation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.SynchronisationImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getSynchronisation()
	 * @generated
	 */
	int SYNCHRONISATION = 30;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNCHRONISATION__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNCHRONISATION__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNCHRONISATION__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Synched Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNCHRONISATION__SYNCHED_TRANSITIONS = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Talk Timeout</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNCHRONISATION__TALK_TIMEOUT = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Sync Timeout</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNCHRONISATION__SYNC_TIMEOUT = PLAN_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Fail On Sync Time Out</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNCHRONISATION__FAIL_ON_SYNC_TIME_OUT = PLAN_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Synchronisation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNCHRONISATION_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNCHRONISATION___GENERATE_ID = PLAN_ELEMENT___GENERATE_ID;

	/**
	 * The number of operations of the '<em>Synchronisation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNCHRONISATION_OPERATION_COUNT = PLAN_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.VariableImpl <em>Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.VariableImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getVariable()
	 * @generated
	 */
	int VARIABLE = 31;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__TYPE = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE___GENERATE_ID = PLAN_ELEMENT___GENERATE_ID;

	/**
	 * The number of operations of the '<em>Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_OPERATION_COUNT = PLAN_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.ParametrisationImpl <em>Parametrisation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.ParametrisationImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getParametrisation()
	 * @generated
	 */
	int PARAMETRISATION = 32;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETRISATION__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETRISATION__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETRISATION__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Subplan</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETRISATION__SUBPLAN = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Subvar</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETRISATION__SUBVAR = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Var</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETRISATION__VAR = PLAN_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Parametrisation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETRISATION_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETRISATION___GENERATE_ID = PLAN_ELEMENT___GENERATE_ID;

	/**
	 * The number of operations of the '<em>Parametrisation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETRISATION_OPERATION_COUNT = PLAN_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AnnotatedPlanImpl <em>Annotated Plan</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AnnotatedPlanImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getAnnotatedPlan()
	 * @generated
	 */
	int ANNOTATED_PLAN = 33;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATED_PLAN__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATED_PLAN__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATED_PLAN__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Plan</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATED_PLAN__PLAN = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Activated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATED_PLAN__ACTIVATED = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Annotated Plan</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATED_PLAN_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATED_PLAN___GENERATE_ID = PLAN_ELEMENT___GENERATE_ID;

	/**
	 * The number of operations of the '<em>Annotated Plan</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATED_PLAN_OPERATION_COUNT = PLAN_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.QuantifierImpl <em>Quantifier</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.QuantifierImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getQuantifier()
	 * @generated
	 */
	int QUANTIFIER = 34;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFIER__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFIER__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFIER__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Scope</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFIER__SCOPE = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Sorts</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFIER__SORTS = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Quantifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFIER_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFIER___GENERATE_ID = PLAN_ELEMENT___GENERATE_ID;

	/**
	 * The number of operations of the '<em>Quantifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFIER_OPERATION_COUNT = PLAN_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.ForallAgentsImpl <em>Forall Agents</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.ForallAgentsImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getForallAgents()
	 * @generated
	 */
	int FORALL_AGENTS = 35;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORALL_AGENTS__ID = QUANTIFIER__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORALL_AGENTS__NAME = QUANTIFIER__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORALL_AGENTS__COMMENT = QUANTIFIER__COMMENT;

	/**
	 * The feature id for the '<em><b>Scope</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORALL_AGENTS__SCOPE = QUANTIFIER__SCOPE;

	/**
	 * The feature id for the '<em><b>Sorts</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORALL_AGENTS__SORTS = QUANTIFIER__SORTS;

	/**
	 * The number of structural features of the '<em>Forall Agents</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORALL_AGENTS_FEATURE_COUNT = QUANTIFIER_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORALL_AGENTS___GENERATE_ID = QUANTIFIER___GENERATE_ID;

	/**
	 * The number of operations of the '<em>Forall Agents</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORALL_AGENTS_OPERATION_COUNT = QUANTIFIER_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.CapabilityImpl <em>Capability</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.CapabilityImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getCapability()
	 * @generated
	 */
	int CAPABILITY = 37;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAPABILITY__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAPABILITY__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAPABILITY__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Cap Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAPABILITY__CAP_VALUES = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Capability</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAPABILITY_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAPABILITY___GENERATE_ID = PLAN_ELEMENT___GENERATE_ID;

	/**
	 * The number of operations of the '<em>Capability</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAPABILITY_OPERATION_COUNT = PLAN_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.CapValueImpl <em>Cap Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.CapValueImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getCapValue()
	 * @generated
	 */
	int CAP_VALUE = 38;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAP_VALUE__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAP_VALUE__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAP_VALUE__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The number of structural features of the '<em>Cap Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAP_VALUE_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAP_VALUE___GENERATE_ID = PLAN_ELEMENT___GENERATE_ID;

	/**
	 * The number of operations of the '<em>Cap Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAP_VALUE_OPERATION_COUNT = PLAN_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.CapabilityDefinitionSetImpl <em>Capability Definition Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.CapabilityDefinitionSetImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getCapabilityDefinitionSet()
	 * @generated
	 */
	int CAPABILITY_DEFINITION_SET = 39;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAPABILITY_DEFINITION_SET__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAPABILITY_DEFINITION_SET__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAPABILITY_DEFINITION_SET__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Capabilities</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAPABILITY_DEFINITION_SET__CAPABILITIES = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Capability Definition Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAPABILITY_DEFINITION_SET_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAPABILITY_DEFINITION_SET___GENERATE_ID = PLAN_ELEMENT___GENERATE_ID;

	/**
	 * The number of operations of the '<em>Capability Definition Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAPABILITY_DEFINITION_SET_OPERATION_COUNT = PLAN_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.PlanningProblemImpl <em>Planning Problem</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.PlanningProblemImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getPlanningProblem()
	 * @generated
	 */
	int PLANNING_PROBLEM = 40;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNING_PROBLEM__ID = ABSTRACT_PLAN__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNING_PROBLEM__NAME = ABSTRACT_PLAN__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNING_PROBLEM__COMMENT = ABSTRACT_PLAN__COMMENT;

	/**
	 * The feature id for the '<em><b>Destination Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNING_PROBLEM__DESTINATION_PATH = ABSTRACT_PLAN__DESTINATION_PATH;

	/**
	 * The feature id for the '<em><b>Plans</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNING_PROBLEM__PLANS = ABSTRACT_PLAN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Planner</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNING_PROBLEM__PLANNER = ABSTRACT_PLAN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Alternative Plan</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNING_PROBLEM__ALTERNATIVE_PLAN = ABSTRACT_PLAN_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Wait Plan</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNING_PROBLEM__WAIT_PLAN = ABSTRACT_PLAN_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Update Rate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNING_PROBLEM__UPDATE_RATE = ABSTRACT_PLAN_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Distribute Problem</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNING_PROBLEM__DISTRIBUTE_PROBLEM = ABSTRACT_PLAN_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Planning Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNING_PROBLEM__PLANNING_TYPE = ABSTRACT_PLAN_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Requirements</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNING_PROBLEM__REQUIREMENTS = ABSTRACT_PLAN_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Planner Params</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNING_PROBLEM__PLANNER_PARAMS = ABSTRACT_PLAN_FEATURE_COUNT + 8;

	/**
	 * The number of structural features of the '<em>Planning Problem</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNING_PROBLEM_FEATURE_COUNT = ABSTRACT_PLAN_FEATURE_COUNT + 9;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNING_PROBLEM___GENERATE_ID = ABSTRACT_PLAN___GENERATE_ID;

	/**
	 * The number of operations of the '<em>Planning Problem</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNING_PROBLEM_OPERATION_COUNT = ABSTRACT_PLAN_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.PlannerImpl <em>Planner</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.PlannerImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getPlanner()
	 * @generated
	 */
	int PLANNER = 41;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNER__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNER__NAME = 1;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNER__PARAMETERS = 2;

	/**
	 * The feature id for the '<em><b>Command</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNER__COMMAND = 3;

	/**
	 * The number of structural features of the '<em>Planner</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNER_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Planner</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.FluentImpl <em>Fluent</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.FluentImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getFluent()
	 * @generated
	 */
	int FLUENT = 42;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLUENT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Formula</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLUENT__FORMULA = 1;

	/**
	 * The number of structural features of the '<em>Fluent</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLUENT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Fluent</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLUENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.DomainDescriptionImpl <em>Domain Description</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.DomainDescriptionImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getDomainDescription()
	 * @generated
	 */
	int DOMAIN_DESCRIPTION = 43;

	/**
	 * The feature id for the '<em><b>Fluents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_DESCRIPTION__FLUENTS = 0;

	/**
	 * The feature id for the '<em><b>Types</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_DESCRIPTION__TYPES = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_DESCRIPTION__NAME = 2;

	/**
	 * The feature id for the '<em><b>Constants</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_DESCRIPTION__CONSTANTS = 3;

	/**
	 * The number of structural features of the '<em>Domain Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_DESCRIPTION_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Domain Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_DESCRIPTION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.PlannersImpl <em>Planners</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.PlannersImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getPlanners()
	 * @generated
	 */
	int PLANNERS = 44;

	/**
	 * The feature id for the '<em><b>Planners</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNERS__PLANNERS = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNERS__NAME = 1;

	/**
	 * The number of structural features of the '<em>Planners</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNERS_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Planners</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNERS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.EStringToEObjectMapEntryImpl <em>EString To EObject Map Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.EStringToEObjectMapEntryImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getEStringToEObjectMapEntry()
	 * @generated
	 */
	int ESTRING_TO_EOBJECT_MAP_ENTRY = 45;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_EOBJECT_MAP_ENTRY__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_EOBJECT_MAP_ENTRY__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_EOBJECT_MAP_ENTRY__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_EOBJECT_MAP_ENTRY__KEY = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_EOBJECT_MAP_ENTRY__VALUE = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>EString To EObject Map Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_EOBJECT_MAP_ENTRY_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Generate ID</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_EOBJECT_MAP_ENTRY___GENERATE_ID = PLAN_ELEMENT___GENERATE_ID;

	/**
	 * The number of operations of the '<em>EString To EObject Map Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_EOBJECT_MAP_ENTRY_OPERATION_COUNT = PLAN_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.FluentParametersImpl <em>Fluent Parameters</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.FluentParametersImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getFluentParameters()
	 * @generated
	 */
	int FLUENT_PARAMETERS = 46;

	/**
	 * The feature id for the '<em><b>Parameter</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLUENT_PARAMETERS__PARAMETER = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLUENT_PARAMETERS__TYPE = 1;

	/**
	 * The number of structural features of the '<em>Fluent Parameters</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLUENT_PARAMETERS_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Fluent Parameters</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLUENT_PARAMETERS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.ConstantImpl <em>Constant</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.ConstantImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getConstant()
	 * @generated
	 */
	int CONSTANT = 47;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT__TYPE = 1;

	/**
	 * The number of structural features of the '<em>Constant</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Constant</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.BehaviourCreatorImpl <em>Behaviour Creator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.BehaviourCreatorImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getBehaviourCreator()
	 * @generated
	 */
	int BEHAVIOUR_CREATOR = 48;

	/**
	 * The feature id for the '<em><b>Behaviours</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIOUR_CREATOR__BEHAVIOURS = 0;

	/**
	 * The number of structural features of the '<em>Behaviour Creator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIOUR_CREATOR_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Behaviour Creator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIOUR_CREATOR_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.ConditionCreatorImpl <em>Condition Creator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.ConditionCreatorImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getConditionCreator()
	 * @generated
	 */
	int CONDITION_CREATOR = 49;

	/**
	 * The feature id for the '<em><b>Conditions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION_CREATOR__CONDITIONS = 0;

	/**
	 * The feature id for the '<em><b>Plans</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION_CREATOR__PLANS = 1;

	/**
	 * The number of structural features of the '<em>Condition Creator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION_CREATOR_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Condition Creator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION_CREATOR_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.UtilityFunctionCreatorImpl <em>Utility Function Creator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.UtilityFunctionCreatorImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getUtilityFunctionCreator()
	 * @generated
	 */
	int UTILITY_FUNCTION_CREATOR = 50;

	/**
	 * The feature id for the '<em><b>Conditions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UTILITY_FUNCTION_CREATOR__CONDITIONS = 0;

	/**
	 * The feature id for the '<em><b>Plans</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UTILITY_FUNCTION_CREATOR__PLANS = 1;

	/**
	 * The number of structural features of the '<em>Utility Function Creator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UTILITY_FUNCTION_CREATOR_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Utility Function Creator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UTILITY_FUNCTION_CREATOR_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.ConstraintCreatorImpl <em>Constraint Creator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.ConstraintCreatorImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getConstraintCreator()
	 * @generated
	 */
	int CONSTRAINT_CREATOR = 51;

	/**
	 * The feature id for the '<em><b>Conditions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_CREATOR__CONDITIONS = 0;

	/**
	 * The feature id for the '<em><b>Plans</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_CREATOR__PLANS = 1;

	/**
	 * The number of structural features of the '<em>Constraint Creator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_CREATOR_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Constraint Creator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_CREATOR_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.DomainBehaviourImpl <em>Domain Behaviour</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.DomainBehaviourImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getDomainBehaviour()
	 * @generated
	 */
	int DOMAIN_BEHAVIOUR = 52;

	/**
	 * The number of structural features of the '<em>Domain Behaviour</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_BEHAVIOUR_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Domain Behaviour</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_BEHAVIOUR_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.DomainConditionImpl <em>Domain Condition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.DomainConditionImpl
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getDomainCondition()
	 * @generated
	 */
	int DOMAIN_CONDITION = 53;

	/**
	 * The number of structural features of the '<em>Domain Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_CONDITION_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Domain Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_CONDITION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanningType <em>Planning Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanningType
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getPlanningType()
	 * @generated
	 */
	int PLANNING_TYPE = 54;


	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Transition <em>Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Transition</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Transition
	 * @generated
	 */
	EClass getTransition();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Transition#getMsg <em>Msg</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Msg</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Transition#getMsg()
	 * @see #getTransition()
	 * @generated
	 */
	EAttribute getTransition_Msg();

	/**
	 * Returns the meta object for the containment reference '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Transition#getPreCondition <em>Pre Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pre Condition</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Transition#getPreCondition()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_PreCondition();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Transition#getInState <em>In State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>In State</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Transition#getInState()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_InState();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Transition#getOutState <em>Out State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Out State</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Transition#getOutState()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_OutState();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Transition#getSynchronisation <em>Synchronisation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Synchronisation</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Transition#getSynchronisation()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_Synchronisation();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Condition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Condition</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Condition
	 * @generated
	 */
	EClass getCondition();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Condition#getConditionString <em>Condition String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Condition String</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Condition#getConditionString()
	 * @see #getCondition()
	 * @generated
	 */
	EAttribute getCondition_ConditionString();

	/**
	 * Returns the meta object for the reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Condition#getVars <em>Vars</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Vars</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Condition#getVars()
	 * @see #getCondition()
	 * @generated
	 */
	EReference getCondition_Vars();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Condition#getQuantifiers <em>Quantifiers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Quantifiers</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Condition#getQuantifiers()
	 * @see #getCondition()
	 * @generated
	 */
	EReference getCondition_Quantifiers();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Condition#getPluginName <em>Plugin Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Plugin Name</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Condition#getPluginName()
	 * @see #getCondition()
	 * @generated
	 */
	EAttribute getCondition_PluginName();

	/**
	 * Returns the meta object for the map '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Condition#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Parameters</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Condition#getParameters()
	 * @see #getCondition()
	 * @generated
	 */
	EReference getCondition_Parameters();

	/**
	 * Returns the meta object for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Condition#ensureVariableConsistency(de.uni_kassel.vs.cn.planDesigner.alicamodel.AbstractPlan) <em>Ensure Variable Consistency</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Ensure Variable Consistency</em>' operation.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Condition#ensureVariableConsistency(de.uni_kassel.vs.cn.planDesigner.alicamodel.AbstractPlan)
	 * @generated
	 */
	EOperation getCondition__EnsureVariableConsistency__AbstractPlan();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.PreCondition <em>Pre Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pre Condition</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.PreCondition
	 * @generated
	 */
	EClass getPreCondition();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.PreCondition#isEnabled <em>Enabled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Enabled</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.PreCondition#isEnabled()
	 * @see #getPreCondition()
	 * @generated
	 */
	EAttribute getPreCondition_Enabled();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.EntryPoint <em>Entry Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entry Point</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.EntryPoint
	 * @generated
	 */
	EClass getEntryPoint();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.EntryPoint#getTask <em>Task</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Task</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.EntryPoint#getTask()
	 * @see #getEntryPoint()
	 * @generated
	 */
	EReference getEntryPoint_Task();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.EntryPoint#isSuccessRequired <em>Success Required</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Success Required</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.EntryPoint#isSuccessRequired()
	 * @see #getEntryPoint()
	 * @generated
	 */
	EAttribute getEntryPoint_SuccessRequired();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.EntryPoint#getState <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>State</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.EntryPoint#getState()
	 * @see #getEntryPoint()
	 * @generated
	 */
	EReference getEntryPoint_State();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.EntryPoint#getMinCardinality <em>Min Cardinality</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Min Cardinality</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.EntryPoint#getMinCardinality()
	 * @see #getEntryPoint()
	 * @generated
	 */
	EAttribute getEntryPoint_MinCardinality();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.EntryPoint#getMaxCardinality <em>Max Cardinality</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Cardinality</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.EntryPoint#getMaxCardinality()
	 * @see #getEntryPoint()
	 * @generated
	 */
	EAttribute getEntryPoint_MaxCardinality();

	/**
	 * Returns the meta object for the container reference '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.EntryPoint#getPlan <em>Plan</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Plan</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.EntryPoint#getPlan()
	 * @see #getEntryPoint()
	 * @generated
	 */
	EReference getEntryPoint_Plan();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.TerminalState <em>Terminal State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Terminal State</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.TerminalState
	 * @generated
	 */
	EClass getTerminalState();

	/**
	 * Returns the meta object for the containment reference '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.TerminalState#getPostCondition <em>Post Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Post Condition</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.TerminalState#getPostCondition()
	 * @see #getTerminalState()
	 * @generated
	 */
	EReference getTerminalState_PostCondition();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.SuccessState <em>Success State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Success State</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.SuccessState
	 * @generated
	 */
	EClass getSuccessState();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.FailureState <em>Failure State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Failure State</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.FailureState
	 * @generated
	 */
	EClass getFailureState();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.AbstractPlan <em>Abstract Plan</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Plan</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.AbstractPlan
	 * @generated
	 */
	EClass getAbstractPlan();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.AbstractPlan#getDestinationPath <em>Destination Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Destination Path</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.AbstractPlan#getDestinationPath()
	 * @see #getAbstractPlan()
	 * @generated
	 */
	EAttribute getAbstractPlan_DestinationPath();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour <em>Behaviour</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Behaviour</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour
	 * @generated
	 */
	EClass getBehaviour();

	/**
	 * Returns the meta object for the containment reference '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour#getPreCondition <em>Pre Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pre Condition</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour#getPreCondition()
	 * @see #getBehaviour()
	 * @generated
	 */
	EReference getBehaviour_PreCondition();

	/**
	 * Returns the meta object for the containment reference '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour#getRuntimeCondition <em>Runtime Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Runtime Condition</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour#getRuntimeCondition()
	 * @see #getBehaviour()
	 * @generated
	 */
	EReference getBehaviour_RuntimeCondition();

	/**
	 * Returns the meta object for the containment reference '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour#getPostCondition <em>Post Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Post Condition</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour#getPostCondition()
	 * @see #getBehaviour()
	 * @generated
	 */
	EReference getBehaviour_PostCondition();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour#getFrequency <em>Frequency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Frequency</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour#getFrequency()
	 * @see #getBehaviour()
	 * @generated
	 */
	EAttribute getBehaviour_Frequency();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour#getVars <em>Vars</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Vars</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour#getVars()
	 * @see #getBehaviour()
	 * @generated
	 */
	EReference getBehaviour_Vars();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.State <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.State
	 * @generated
	 */
	EClass getState();

	/**
	 * Returns the meta object for the reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.State#getPlans <em>Plans</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Plans</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.State#getPlans()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_Plans();

	/**
	 * Returns the meta object for the container reference '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.State#getInPlan <em>In Plan</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>In Plan</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.State#getInPlan()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_InPlan();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.State#getParametrisation <em>Parametrisation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parametrisation</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.State#getParametrisation()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_Parametrisation();

	/**
	 * Returns the meta object for the reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.State#getInTransitions <em>In Transitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>In Transitions</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.State#getInTransitions()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_InTransitions();

	/**
	 * Returns the meta object for the reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.State#getOutTransitions <em>Out Transitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Out Transitions</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.State#getOutTransitions()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_OutTransitions();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.State#getEntryPoint <em>Entry Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Entry Point</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.State#getEntryPoint()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_EntryPoint();

	/**
	 * Returns the meta object for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.State#ensureParametrisationConsistency() <em>Ensure Parametrisation Consistency</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Ensure Parametrisation Consistency</em>' operation.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.State#ensureParametrisationConsistency()
	 * @generated
	 */
	EOperation getState__EnsureParametrisationConsistency();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan <em>Plan</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Plan</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan
	 * @generated
	 */
	EClass getPlan();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getPriority <em>Priority</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Priority</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getPriority()
	 * @see #getPlan()
	 * @generated
	 */
	EAttribute getPlan_Priority();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getStates <em>States</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>States</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getStates()
	 * @see #getPlan()
	 * @generated
	 */
	EReference getPlan_States();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getTransitions <em>Transitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Transitions</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getTransitions()
	 * @see #getPlan()
	 * @generated
	 */
	EReference getPlan_Transitions();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getMinCardinality <em>Min Cardinality</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Min Cardinality</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getMinCardinality()
	 * @see #getPlan()
	 * @generated
	 */
	EAttribute getPlan_MinCardinality();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getMaxCardinality <em>Max Cardinality</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Cardinality</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getMaxCardinality()
	 * @see #getPlan()
	 * @generated
	 */
	EAttribute getPlan_MaxCardinality();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getSynchronisations <em>Synchronisations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Synchronisations</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getSynchronisations()
	 * @see #getPlan()
	 * @generated
	 */
	EReference getPlan_Synchronisations();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getEntryPoints <em>Entry Points</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entry Points</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getEntryPoints()
	 * @see #getPlan()
	 * @generated
	 */
	EReference getPlan_EntryPoints();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#isMasterPlan <em>Master Plan</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Master Plan</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#isMasterPlan()
	 * @see #getPlan()
	 * @generated
	 */
	EAttribute getPlan_MasterPlan();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getUtilityFunction <em>Utility Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Utility Function</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getUtilityFunction()
	 * @see #getPlan()
	 * @generated
	 */
	EAttribute getPlan_UtilityFunction();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getUtilityThreshold <em>Utility Threshold</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Utility Threshold</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getUtilityThreshold()
	 * @see #getPlan()
	 * @generated
	 */
	EAttribute getPlan_UtilityThreshold();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getVars <em>Vars</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Vars</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getVars()
	 * @see #getPlan()
	 * @generated
	 */
	EReference getPlan_Vars();

	/**
	 * Returns the meta object for the containment reference '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getPreCondition <em>Pre Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pre Condition</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getPreCondition()
	 * @see #getPlan()
	 * @generated
	 */
	EReference getPlan_PreCondition();

	/**
	 * Returns the meta object for the containment reference '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getRuntimeCondition <em>Runtime Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Runtime Condition</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getRuntimeCondition()
	 * @see #getPlan()
	 * @generated
	 */
	EReference getPlan_RuntimeCondition();

	/**
	 * Returns the meta object for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#calculateCardinalities() <em>Calculate Cardinalities</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Calculate Cardinalities</em>' operation.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#calculateCardinalities()
	 * @generated
	 */
	EOperation getPlan__CalculateCardinalities();

	/**
	 * Returns the meta object for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#ensureParametrisationConsistency() <em>Ensure Parametrisation Consistency</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Ensure Parametrisation Consistency</em>' operation.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#ensureParametrisationConsistency()
	 * @generated
	 */
	EOperation getPlan__EnsureParametrisationConsistency();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanType <em>Plan Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Plan Type</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanType
	 * @generated
	 */
	EClass getPlanType();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanType#getParametrisation <em>Parametrisation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parametrisation</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanType#getParametrisation()
	 * @see #getPlanType()
	 * @generated
	 */
	EReference getPlanType_Parametrisation();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanType#getPlans <em>Plans</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Plans</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanType#getPlans()
	 * @see #getPlanType()
	 * @generated
	 */
	EReference getPlanType_Plans();

	/**
	 * Returns the meta object for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanType#ensureParametrisationConsistency() <em>Ensure Parametrisation Consistency</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Ensure Parametrisation Consistency</em>' operation.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanType#ensureParametrisationConsistency()
	 * @generated
	 */
	EOperation getPlanType__EnsureParametrisationConsistency();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Rating <em>Rating</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rating</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Rating
	 * @generated
	 */
	EClass getRating();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.PostCondition <em>Post Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Post Condition</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.PostCondition
	 * @generated
	 */
	EClass getPostCondition();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.RuntimeCondition <em>Runtime Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Runtime Condition</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.RuntimeCondition
	 * @generated
	 */
	EClass getRuntimeCondition();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement <em>Plan Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Plan Element</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement
	 * @generated
	 */
	EClass getPlanElement();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement#getId()
	 * @see #getPlanElement()
	 * @generated
	 */
	EAttribute getPlanElement_Id();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement#getName()
	 * @see #getPlanElement()
	 * @generated
	 */
	EAttribute getPlanElement_Name();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement#getComment()
	 * @see #getPlanElement()
	 * @generated
	 */
	EAttribute getPlanElement_Comment();

	/**
	 * Returns the meta object for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement#generateID() <em>Generate ID</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Generate ID</em>' operation.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement#generateID()
	 * @generated
	 */
	EOperation getPlanElement__GenerateID();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Task <em>Task</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Task</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Task
	 * @generated
	 */
	EClass getTask();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Task#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Task#getDescription()
	 * @see #getTask()
	 * @generated
	 */
	EAttribute getTask_Description();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>EString To EString Map Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EString To EString Map Entry</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDefault="" keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueDefault="" valueDataType="org.eclipse.emf.ecore.EString"
	 * @generated
	 */
	EClass getEStringToEStringMapEntry();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEStringToEStringMapEntry()
	 * @generated
	 */
	EAttribute getEStringToEStringMapEntry_Key();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEStringToEStringMapEntry()
	 * @generated
	 */
	EAttribute getEStringToEStringMapEntry_Value();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Role <em>Role</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Role</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Role
	 * @generated
	 */
	EClass getRole();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Role#getCharacteristics <em>Characteristics</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Characteristics</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Role#getCharacteristics()
	 * @see #getRole()
	 * @generated
	 */
	EReference getRole_Characteristics();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.RoleSet <em>Role Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Role Set</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.RoleSet
	 * @generated
	 */
	EClass getRoleSet();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.RoleSet#getUsableWithPlanID <em>Usable With Plan ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Usable With Plan ID</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.RoleSet#getUsableWithPlanID()
	 * @see #getRoleSet()
	 * @generated
	 */
	EAttribute getRoleSet_UsableWithPlanID();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.RoleSet#isDefault <em>Default</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.RoleSet#isDefault()
	 * @see #getRoleSet()
	 * @generated
	 */
	EAttribute getRoleSet_Default();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.RoleSet#getMappings <em>Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Mappings</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.RoleSet#getMappings()
	 * @see #getRoleSet()
	 * @generated
	 */
	EReference getRoleSet_Mappings();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>ELong To Double Map Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ELong To Double Map Entry</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDefault="0" keyDataType="org.eclipse.emf.ecore.ELongObject"
	 *        valueDefault="0" valueDataType="org.eclipse.emf.ecore.EDoubleObject"
	 * @generated
	 */
	EClass getELongToDoubleMapEntry();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getELongToDoubleMapEntry()
	 * @generated
	 */
	EAttribute getELongToDoubleMapEntry_Key();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getELongToDoubleMapEntry()
	 * @generated
	 */
	EAttribute getELongToDoubleMapEntry_Value();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.RoleDefinitionSet <em>Role Definition Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Role Definition Set</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.RoleDefinitionSet
	 * @generated
	 */
	EClass getRoleDefinitionSet();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.RoleDefinitionSet#getRoles <em>Roles</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Roles</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.RoleDefinitionSet#getRoles()
	 * @see #getRoleDefinitionSet()
	 * @generated
	 */
	EReference getRoleDefinitionSet_Roles();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.RoleTaskMapping <em>Role Task Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Role Task Mapping</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.RoleTaskMapping
	 * @generated
	 */
	EClass getRoleTaskMapping();

	/**
	 * Returns the meta object for the map '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.RoleTaskMapping#getTaskPriorities <em>Task Priorities</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Task Priorities</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.RoleTaskMapping#getTaskPriorities()
	 * @see #getRoleTaskMapping()
	 * @generated
	 */
	EReference getRoleTaskMapping_TaskPriorities();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.RoleTaskMapping#getRole <em>Role</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Role</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.RoleTaskMapping#getRole()
	 * @see #getRoleTaskMapping()
	 * @generated
	 */
	EReference getRoleTaskMapping_Role();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Characteristic <em>Characteristic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Characteristic</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Characteristic
	 * @generated
	 */
	EClass getCharacteristic();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Characteristic#getWeight <em>Weight</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Weight</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Characteristic#getWeight()
	 * @see #getCharacteristic()
	 * @generated
	 */
	EAttribute getCharacteristic_Weight();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Characteristic#getCapability <em>Capability</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Capability</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Characteristic#getCapability()
	 * @see #getCharacteristic()
	 * @generated
	 */
	EReference getCharacteristic_Capability();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Characteristic#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Characteristic#getValue()
	 * @see #getCharacteristic()
	 * @generated
	 */
	EReference getCharacteristic_Value();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.TaskGraph <em>Task Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Task Graph</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.TaskGraph
	 * @generated
	 */
	EClass getTaskGraph();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.TaskGraph#getNodes <em>Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Nodes</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.TaskGraph#getNodes()
	 * @see #getTaskGraph()
	 * @generated
	 */
	EReference getTaskGraph_Nodes();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.TaskGraph#getEdges <em>Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Edges</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.TaskGraph#getEdges()
	 * @see #getTaskGraph()
	 * @generated
	 */
	EReference getTaskGraph_Edges();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Edge <em>Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Edge</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Edge
	 * @generated
	 */
	EClass getEdge();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Edge#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>From</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Edge#getFrom()
	 * @see #getEdge()
	 * @generated
	 */
	EReference getEdge_From();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Edge#getTo <em>To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>To</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Edge#getTo()
	 * @see #getEdge()
	 * @generated
	 */
	EReference getEdge_To();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.TaskWrapper <em>Task Wrapper</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Task Wrapper</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.TaskWrapper
	 * @generated
	 */
	EClass getTaskWrapper();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.TaskWrapper#getTask <em>Task</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Task</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.TaskWrapper#getTask()
	 * @see #getTaskWrapper()
	 * @generated
	 */
	EReference getTaskWrapper_Task();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.TaskWrapper#getMappings <em>Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Mappings</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.TaskWrapper#getMappings()
	 * @see #getTaskWrapper()
	 * @generated
	 */
	EReference getTaskWrapper_Mappings();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.InternalRoleTaskMapping <em>Internal Role Task Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Internal Role Task Mapping</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.InternalRoleTaskMapping
	 * @generated
	 */
	EClass getInternalRoleTaskMapping();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.InternalRoleTaskMapping#getRole <em>Role</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Role</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.InternalRoleTaskMapping#getRole()
	 * @see #getInternalRoleTaskMapping()
	 * @generated
	 */
	EReference getInternalRoleTaskMapping_Role();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.InternalRoleTaskMapping#getPriority <em>Priority</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Priority</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.InternalRoleTaskMapping#getPriority()
	 * @see #getInternalRoleTaskMapping()
	 * @generated
	 */
	EAttribute getInternalRoleTaskMapping_Priority();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Node <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Node
	 * @generated
	 */
	EClass getNode();

	/**
	 * Returns the meta object for the reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Node#getInEdge <em>In Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>In Edge</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Node#getInEdge()
	 * @see #getNode()
	 * @generated
	 */
	EReference getNode_InEdge();

	/**
	 * Returns the meta object for the reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Node#getOutEdge <em>Out Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Out Edge</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Node#getOutEdge()
	 * @see #getNode()
	 * @generated
	 */
	EReference getNode_OutEdge();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.TaskRepository <em>Task Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Task Repository</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.TaskRepository
	 * @generated
	 */
	EClass getTaskRepository();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.TaskRepository#getTasks <em>Tasks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Tasks</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.TaskRepository#getTasks()
	 * @see #getTaskRepository()
	 * @generated
	 */
	EReference getTaskRepository_Tasks();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.TaskRepository#getDefaultTask <em>Default Task</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Default Task</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.TaskRepository#getDefaultTask()
	 * @see #getTaskRepository()
	 * @generated
	 */
	EReference getTaskRepository_DefaultTask();

	/**
	 * Returns the meta object for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.TaskRepository#createDefaultTask() <em>Create Default Task</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Create Default Task</em>' operation.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.TaskRepository#createDefaultTask()
	 * @generated
	 */
	EOperation getTaskRepository__CreateDefaultTask();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Synchronisation <em>Synchronisation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Synchronisation</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Synchronisation
	 * @generated
	 */
	EClass getSynchronisation();

	/**
	 * Returns the meta object for the reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Synchronisation#getSynchedTransitions <em>Synched Transitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Synched Transitions</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Synchronisation#getSynchedTransitions()
	 * @see #getSynchronisation()
	 * @generated
	 */
	EReference getSynchronisation_SynchedTransitions();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Synchronisation#getTalkTimeout <em>Talk Timeout</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Talk Timeout</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Synchronisation#getTalkTimeout()
	 * @see #getSynchronisation()
	 * @generated
	 */
	EAttribute getSynchronisation_TalkTimeout();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Synchronisation#getSyncTimeout <em>Sync Timeout</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Sync Timeout</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Synchronisation#getSyncTimeout()
	 * @see #getSynchronisation()
	 * @generated
	 */
	EAttribute getSynchronisation_SyncTimeout();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Synchronisation#isFailOnSyncTimeOut <em>Fail On Sync Time Out</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fail On Sync Time Out</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Synchronisation#isFailOnSyncTimeOut()
	 * @see #getSynchronisation()
	 * @generated
	 */
	EAttribute getSynchronisation_FailOnSyncTimeOut();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Variable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variable</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Variable
	 * @generated
	 */
	EClass getVariable();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Variable#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Variable#getType()
	 * @see #getVariable()
	 * @generated
	 */
	EAttribute getVariable_Type();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Parametrisation <em>Parametrisation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parametrisation</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Parametrisation
	 * @generated
	 */
	EClass getParametrisation();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Parametrisation#getSubplan <em>Subplan</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Subplan</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Parametrisation#getSubplan()
	 * @see #getParametrisation()
	 * @generated
	 */
	EReference getParametrisation_Subplan();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Parametrisation#getSubvar <em>Subvar</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Subvar</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Parametrisation#getSubvar()
	 * @see #getParametrisation()
	 * @generated
	 */
	EReference getParametrisation_Subvar();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Parametrisation#getVar <em>Var</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Var</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Parametrisation#getVar()
	 * @see #getParametrisation()
	 * @generated
	 */
	EReference getParametrisation_Var();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.AnnotatedPlan <em>Annotated Plan</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Annotated Plan</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.AnnotatedPlan
	 * @generated
	 */
	EClass getAnnotatedPlan();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.AnnotatedPlan#getPlan <em>Plan</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Plan</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.AnnotatedPlan#getPlan()
	 * @see #getAnnotatedPlan()
	 * @generated
	 */
	EReference getAnnotatedPlan_Plan();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.AnnotatedPlan#isActivated <em>Activated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Activated</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.AnnotatedPlan#isActivated()
	 * @see #getAnnotatedPlan()
	 * @generated
	 */
	EAttribute getAnnotatedPlan_Activated();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Quantifier <em>Quantifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Quantifier</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Quantifier
	 * @generated
	 */
	EClass getQuantifier();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Quantifier#getScope <em>Scope</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Scope</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Quantifier#getScope()
	 * @see #getQuantifier()
	 * @generated
	 */
	EReference getQuantifier_Scope();

	/**
	 * Returns the meta object for the attribute list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Quantifier#getSorts <em>Sorts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Sorts</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Quantifier#getSorts()
	 * @see #getQuantifier()
	 * @generated
	 */
	EAttribute getQuantifier_Sorts();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.ForallAgents <em>Forall Agents</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Forall Agents</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.ForallAgents
	 * @generated
	 */
	EClass getForallAgents();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.IInhabitable <em>IInhabitable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IInhabitable</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.IInhabitable
	 * @generated
	 */
	EClass getIInhabitable();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Capability <em>Capability</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Capability</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Capability
	 * @generated
	 */
	EClass getCapability();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Capability#getCapValues <em>Cap Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Cap Values</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Capability#getCapValues()
	 * @see #getCapability()
	 * @generated
	 */
	EReference getCapability_CapValues();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.CapValue <em>Cap Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cap Value</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.CapValue
	 * @generated
	 */
	EClass getCapValue();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.CapabilityDefinitionSet <em>Capability Definition Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Capability Definition Set</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.CapabilityDefinitionSet
	 * @generated
	 */
	EClass getCapabilityDefinitionSet();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.CapabilityDefinitionSet#getCapabilities <em>Capabilities</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Capabilities</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.CapabilityDefinitionSet#getCapabilities()
	 * @see #getCapabilityDefinitionSet()
	 * @generated
	 */
	EReference getCapabilityDefinitionSet_Capabilities();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanningProblem <em>Planning Problem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Planning Problem</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanningProblem
	 * @generated
	 */
	EClass getPlanningProblem();

	/**
	 * Returns the meta object for the reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanningProblem#getPlans <em>Plans</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Plans</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanningProblem#getPlans()
	 * @see #getPlanningProblem()
	 * @generated
	 */
	EReference getPlanningProblem_Plans();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanningProblem#getPlanner <em>Planner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Planner</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanningProblem#getPlanner()
	 * @see #getPlanningProblem()
	 * @generated
	 */
	EReference getPlanningProblem_Planner();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanningProblem#getAlternativePlan <em>Alternative Plan</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Alternative Plan</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanningProblem#getAlternativePlan()
	 * @see #getPlanningProblem()
	 * @generated
	 */
	EReference getPlanningProblem_AlternativePlan();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanningProblem#getWaitPlan <em>Wait Plan</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Wait Plan</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanningProblem#getWaitPlan()
	 * @see #getPlanningProblem()
	 * @generated
	 */
	EReference getPlanningProblem_WaitPlan();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanningProblem#getUpdateRate <em>Update Rate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Update Rate</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanningProblem#getUpdateRate()
	 * @see #getPlanningProblem()
	 * @generated
	 */
	EAttribute getPlanningProblem_UpdateRate();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanningProblem#isDistributeProblem <em>Distribute Problem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Distribute Problem</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanningProblem#isDistributeProblem()
	 * @see #getPlanningProblem()
	 * @generated
	 */
	EAttribute getPlanningProblem_DistributeProblem();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanningProblem#getPlanningType <em>Planning Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Planning Type</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanningProblem#getPlanningType()
	 * @see #getPlanningProblem()
	 * @generated
	 */
	EAttribute getPlanningProblem_PlanningType();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanningProblem#getRequirements <em>Requirements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Requirements</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanningProblem#getRequirements()
	 * @see #getPlanningProblem()
	 * @generated
	 */
	EAttribute getPlanningProblem_Requirements();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanningProblem#getPlannerParams <em>Planner Params</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Planner Params</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanningProblem#getPlannerParams()
	 * @see #getPlanningProblem()
	 * @generated
	 */
	EAttribute getPlanningProblem_PlannerParams();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Planner <em>Planner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Planner</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Planner
	 * @generated
	 */
	EClass getPlanner();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Planner#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Planner#getType()
	 * @see #getPlanner()
	 * @generated
	 */
	EAttribute getPlanner_Type();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Planner#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Planner#getName()
	 * @see #getPlanner()
	 * @generated
	 */
	EAttribute getPlanner_Name();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Planner#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Parameters</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Planner#getParameters()
	 * @see #getPlanner()
	 * @generated
	 */
	EAttribute getPlanner_Parameters();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Planner#getCommand <em>Command</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Command</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Planner#getCommand()
	 * @see #getPlanner()
	 * @generated
	 */
	EAttribute getPlanner_Command();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Fluent <em>Fluent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Fluent</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Fluent
	 * @generated
	 */
	EClass getFluent();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Fluent#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Fluent#getName()
	 * @see #getFluent()
	 * @generated
	 */
	EAttribute getFluent_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Fluent#getFormula <em>Formula</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Formula</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Fluent#getFormula()
	 * @see #getFluent()
	 * @generated
	 */
	EReference getFluent_Formula();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.DomainDescription <em>Domain Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Domain Description</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.DomainDescription
	 * @generated
	 */
	EClass getDomainDescription();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.DomainDescription#getFluents <em>Fluents</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Fluents</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.DomainDescription#getFluents()
	 * @see #getDomainDescription()
	 * @generated
	 */
	EReference getDomainDescription_Fluents();

	/**
	 * Returns the meta object for the attribute list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.DomainDescription#getTypes <em>Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Types</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.DomainDescription#getTypes()
	 * @see #getDomainDescription()
	 * @generated
	 */
	EAttribute getDomainDescription_Types();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.DomainDescription#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.DomainDescription#getName()
	 * @see #getDomainDescription()
	 * @generated
	 */
	EAttribute getDomainDescription_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.DomainDescription#getConstants <em>Constants</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Constants</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.DomainDescription#getConstants()
	 * @see #getDomainDescription()
	 * @generated
	 */
	EReference getDomainDescription_Constants();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Planners <em>Planners</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Planners</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Planners
	 * @generated
	 */
	EClass getPlanners();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Planners#getPlanners <em>Planners</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Planners</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Planners#getPlanners()
	 * @see #getPlanners()
	 * @generated
	 */
	EReference getPlanners_Planners();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Planners#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Planners#getName()
	 * @see #getPlanners()
	 * @generated
	 */
	EAttribute getPlanners_Name();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>EString To EObject Map Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EString To EObject Map Entry</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueDataType="org.eclipse.emf.ecore.EJavaObject"
	 * @generated
	 */
	EClass getEStringToEObjectMapEntry();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEStringToEObjectMapEntry()
	 * @generated
	 */
	EAttribute getEStringToEObjectMapEntry_Key();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEStringToEObjectMapEntry()
	 * @generated
	 */
	EAttribute getEStringToEObjectMapEntry_Value();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.FluentParameters <em>Fluent Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Fluent Parameters</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.FluentParameters
	 * @generated
	 */
	EClass getFluentParameters();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.FluentParameters#getParameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Parameter</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.FluentParameters#getParameter()
	 * @see #getFluentParameters()
	 * @generated
	 */
	EAttribute getFluentParameters_Parameter();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.FluentParameters#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.FluentParameters#getType()
	 * @see #getFluentParameters()
	 * @generated
	 */
	EAttribute getFluentParameters_Type();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Constant <em>Constant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Constant</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Constant
	 * @generated
	 */
	EClass getConstant();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Constant#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Constant#getName()
	 * @see #getConstant()
	 * @generated
	 */
	EAttribute getConstant_Name();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Constant#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Constant#getType()
	 * @see #getConstant()
	 * @generated
	 */
	EAttribute getConstant_Type();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.BehaviourCreator <em>Behaviour Creator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Behaviour Creator</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.BehaviourCreator
	 * @generated
	 */
	EClass getBehaviourCreator();

	/**
	 * Returns the meta object for the reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.BehaviourCreator#getBehaviours <em>Behaviours</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Behaviours</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.BehaviourCreator#getBehaviours()
	 * @see #getBehaviourCreator()
	 * @generated
	 */
	EReference getBehaviourCreator_Behaviours();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.ConditionCreator <em>Condition Creator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Condition Creator</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.ConditionCreator
	 * @generated
	 */
	EClass getConditionCreator();

	/**
	 * Returns the meta object for the reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.ConditionCreator#getConditions <em>Conditions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Conditions</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.ConditionCreator#getConditions()
	 * @see #getConditionCreator()
	 * @generated
	 */
	EReference getConditionCreator_Conditions();

	/**
	 * Returns the meta object for the reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.ConditionCreator#getPlans <em>Plans</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Plans</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.ConditionCreator#getPlans()
	 * @see #getConditionCreator()
	 * @generated
	 */
	EReference getConditionCreator_Plans();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.UtilityFunctionCreator <em>Utility Function Creator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Utility Function Creator</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.UtilityFunctionCreator
	 * @generated
	 */
	EClass getUtilityFunctionCreator();

	/**
	 * Returns the meta object for the reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.UtilityFunctionCreator#getConditions <em>Conditions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Conditions</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.UtilityFunctionCreator#getConditions()
	 * @see #getUtilityFunctionCreator()
	 * @generated
	 */
	EReference getUtilityFunctionCreator_Conditions();

	/**
	 * Returns the meta object for the reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.UtilityFunctionCreator#getPlans <em>Plans</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Plans</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.UtilityFunctionCreator#getPlans()
	 * @see #getUtilityFunctionCreator()
	 * @generated
	 */
	EReference getUtilityFunctionCreator_Plans();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.ConstraintCreator <em>Constraint Creator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Constraint Creator</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.ConstraintCreator
	 * @generated
	 */
	EClass getConstraintCreator();

	/**
	 * Returns the meta object for the reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.ConstraintCreator#getConditions <em>Conditions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Conditions</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.ConstraintCreator#getConditions()
	 * @see #getConstraintCreator()
	 * @generated
	 */
	EReference getConstraintCreator_Conditions();

	/**
	 * Returns the meta object for the reference list '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.ConstraintCreator#getPlans <em>Plans</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Plans</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.ConstraintCreator#getPlans()
	 * @see #getConstraintCreator()
	 * @generated
	 */
	EReference getConstraintCreator_Plans();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.DomainBehaviour <em>Domain Behaviour</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Domain Behaviour</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.DomainBehaviour
	 * @generated
	 */
	EClass getDomainBehaviour();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.DomainCondition <em>Domain Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Domain Condition</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.DomainCondition
	 * @generated
	 */
	EClass getDomainCondition();

	/**
	 * Returns the meta object for enum '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanningType <em>Planning Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Planning Type</em>'.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanningType
	 * @generated
	 */
	EEnum getPlanningType();

	/**
	 * Returns the factory that creates the instances of the alicamodel.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the alicamodel.
	 * @generated
	 */
	AlicaFactory getAlicaFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.TransitionImpl <em>Transition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.TransitionImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getTransition()
		 * @generated
		 */
		EClass TRANSITION = eINSTANCE.getTransition();

		/**
		 * The meta object literal for the '<em><b>Msg</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSITION__MSG = eINSTANCE.getTransition_Msg();

		/**
		 * The meta object literal for the '<em><b>Pre Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITION__PRE_CONDITION = eINSTANCE.getTransition_PreCondition();

		/**
		 * The meta object literal for the '<em><b>In State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITION__IN_STATE = eINSTANCE.getTransition_InState();

		/**
		 * The meta object literal for the '<em><b>Out State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITION__OUT_STATE = eINSTANCE.getTransition_OutState();

		/**
		 * The meta object literal for the '<em><b>Synchronisation</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITION__SYNCHRONISATION = eINSTANCE.getTransition_Synchronisation();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.ConditionImpl <em>Condition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.ConditionImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getCondition()
		 * @generated
		 */
		EClass CONDITION = eINSTANCE.getCondition();

		/**
		 * The meta object literal for the '<em><b>Condition String</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONDITION__CONDITION_STRING = eINSTANCE.getCondition_ConditionString();

		/**
		 * The meta object literal for the '<em><b>Vars</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITION__VARS = eINSTANCE.getCondition_Vars();

		/**
		 * The meta object literal for the '<em><b>Quantifiers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITION__QUANTIFIERS = eINSTANCE.getCondition_Quantifiers();

		/**
		 * The meta object literal for the '<em><b>Plugin Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONDITION__PLUGIN_NAME = eINSTANCE.getCondition_PluginName();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITION__PARAMETERS = eINSTANCE.getCondition_Parameters();

		/**
		 * The meta object literal for the '<em><b>Ensure Variable Consistency</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation CONDITION___ENSURE_VARIABLE_CONSISTENCY__ABSTRACTPLAN = eINSTANCE.getCondition__EnsureVariableConsistency__AbstractPlan();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.PreConditionImpl <em>Pre Condition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.PreConditionImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getPreCondition()
		 * @generated
		 */
		EClass PRE_CONDITION = eINSTANCE.getPreCondition();

		/**
		 * The meta object literal for the '<em><b>Enabled</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PRE_CONDITION__ENABLED = eINSTANCE.getPreCondition_Enabled();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.EntryPointImpl <em>Entry Point</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.EntryPointImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getEntryPoint()
		 * @generated
		 */
		EClass ENTRY_POINT = eINSTANCE.getEntryPoint();

		/**
		 * The meta object literal for the '<em><b>Task</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTRY_POINT__TASK = eINSTANCE.getEntryPoint_Task();

		/**
		 * The meta object literal for the '<em><b>Success Required</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTRY_POINT__SUCCESS_REQUIRED = eINSTANCE.getEntryPoint_SuccessRequired();

		/**
		 * The meta object literal for the '<em><b>State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTRY_POINT__STATE = eINSTANCE.getEntryPoint_State();

		/**
		 * The meta object literal for the '<em><b>Min Cardinality</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTRY_POINT__MIN_CARDINALITY = eINSTANCE.getEntryPoint_MinCardinality();

		/**
		 * The meta object literal for the '<em><b>Max Cardinality</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTRY_POINT__MAX_CARDINALITY = eINSTANCE.getEntryPoint_MaxCardinality();

		/**
		 * The meta object literal for the '<em><b>Plan</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTRY_POINT__PLAN = eINSTANCE.getEntryPoint_Plan();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.TerminalStateImpl <em>Terminal State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.TerminalStateImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getTerminalState()
		 * @generated
		 */
		EClass TERMINAL_STATE = eINSTANCE.getTerminalState();

		/**
		 * The meta object literal for the '<em><b>Post Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TERMINAL_STATE__POST_CONDITION = eINSTANCE.getTerminalState_PostCondition();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.SuccessStateImpl <em>Success State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.SuccessStateImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getSuccessState()
		 * @generated
		 */
		EClass SUCCESS_STATE = eINSTANCE.getSuccessState();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.FailureStateImpl <em>Failure State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.FailureStateImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getFailureState()
		 * @generated
		 */
		EClass FAILURE_STATE = eINSTANCE.getFailureState();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AbstractPlanImpl <em>Abstract Plan</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AbstractPlanImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getAbstractPlan()
		 * @generated
		 */
		EClass ABSTRACT_PLAN = eINSTANCE.getAbstractPlan();

		/**
		 * The meta object literal for the '<em><b>Destination Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_PLAN__DESTINATION_PATH = eINSTANCE.getAbstractPlan_DestinationPath();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.BehaviourImpl <em>Behaviour</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.BehaviourImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getBehaviour()
		 * @generated
		 */
		EClass BEHAVIOUR = eINSTANCE.getBehaviour();

		/**
		 * The meta object literal for the '<em><b>Pre Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BEHAVIOUR__PRE_CONDITION = eINSTANCE.getBehaviour_PreCondition();

		/**
		 * The meta object literal for the '<em><b>Runtime Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BEHAVIOUR__RUNTIME_CONDITION = eINSTANCE.getBehaviour_RuntimeCondition();

		/**
		 * The meta object literal for the '<em><b>Post Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BEHAVIOUR__POST_CONDITION = eINSTANCE.getBehaviour_PostCondition();

		/**
		 * The meta object literal for the '<em><b>Frequency</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BEHAVIOUR__FREQUENCY = eINSTANCE.getBehaviour_Frequency();

		/**
		 * The meta object literal for the '<em><b>Vars</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BEHAVIOUR__VARS = eINSTANCE.getBehaviour_Vars();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.StateImpl <em>State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.StateImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getState()
		 * @generated
		 */
		EClass STATE = eINSTANCE.getState();

		/**
		 * The meta object literal for the '<em><b>Plans</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__PLANS = eINSTANCE.getState_Plans();

		/**
		 * The meta object literal for the '<em><b>In Plan</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__IN_PLAN = eINSTANCE.getState_InPlan();

		/**
		 * The meta object literal for the '<em><b>Parametrisation</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__PARAMETRISATION = eINSTANCE.getState_Parametrisation();

		/**
		 * The meta object literal for the '<em><b>In Transitions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__IN_TRANSITIONS = eINSTANCE.getState_InTransitions();

		/**
		 * The meta object literal for the '<em><b>Out Transitions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__OUT_TRANSITIONS = eINSTANCE.getState_OutTransitions();

		/**
		 * The meta object literal for the '<em><b>Entry Point</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__ENTRY_POINT = eINSTANCE.getState_EntryPoint();

		/**
		 * The meta object literal for the '<em><b>Ensure Parametrisation Consistency</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation STATE___ENSURE_PARAMETRISATION_CONSISTENCY = eINSTANCE.getState__EnsureParametrisationConsistency();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.PlanImpl <em>Plan</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.PlanImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getPlan()
		 * @generated
		 */
		EClass PLAN = eINSTANCE.getPlan();

		/**
		 * The meta object literal for the '<em><b>Priority</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLAN__PRIORITY = eINSTANCE.getPlan_Priority();

		/**
		 * The meta object literal for the '<em><b>States</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLAN__STATES = eINSTANCE.getPlan_States();

		/**
		 * The meta object literal for the '<em><b>Transitions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLAN__TRANSITIONS = eINSTANCE.getPlan_Transitions();

		/**
		 * The meta object literal for the '<em><b>Min Cardinality</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLAN__MIN_CARDINALITY = eINSTANCE.getPlan_MinCardinality();

		/**
		 * The meta object literal for the '<em><b>Max Cardinality</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLAN__MAX_CARDINALITY = eINSTANCE.getPlan_MaxCardinality();

		/**
		 * The meta object literal for the '<em><b>Synchronisations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLAN__SYNCHRONISATIONS = eINSTANCE.getPlan_Synchronisations();

		/**
		 * The meta object literal for the '<em><b>Entry Points</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLAN__ENTRY_POINTS = eINSTANCE.getPlan_EntryPoints();

		/**
		 * The meta object literal for the '<em><b>Master Plan</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLAN__MASTER_PLAN = eINSTANCE.getPlan_MasterPlan();

		/**
		 * The meta object literal for the '<em><b>Utility Function</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLAN__UTILITY_FUNCTION = eINSTANCE.getPlan_UtilityFunction();

		/**
		 * The meta object literal for the '<em><b>Utility Threshold</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLAN__UTILITY_THRESHOLD = eINSTANCE.getPlan_UtilityThreshold();

		/**
		 * The meta object literal for the '<em><b>Vars</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLAN__VARS = eINSTANCE.getPlan_Vars();

		/**
		 * The meta object literal for the '<em><b>Pre Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLAN__PRE_CONDITION = eINSTANCE.getPlan_PreCondition();

		/**
		 * The meta object literal for the '<em><b>Runtime Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLAN__RUNTIME_CONDITION = eINSTANCE.getPlan_RuntimeCondition();

		/**
		 * The meta object literal for the '<em><b>Calculate Cardinalities</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PLAN___CALCULATE_CARDINALITIES = eINSTANCE.getPlan__CalculateCardinalities();

		/**
		 * The meta object literal for the '<em><b>Ensure Parametrisation Consistency</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PLAN___ENSURE_PARAMETRISATION_CONSISTENCY = eINSTANCE.getPlan__EnsureParametrisationConsistency();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.PlanTypeImpl <em>Plan Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.PlanTypeImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getPlanType()
		 * @generated
		 */
		EClass PLAN_TYPE = eINSTANCE.getPlanType();

		/**
		 * The meta object literal for the '<em><b>Parametrisation</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLAN_TYPE__PARAMETRISATION = eINSTANCE.getPlanType_Parametrisation();

		/**
		 * The meta object literal for the '<em><b>Plans</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLAN_TYPE__PLANS = eINSTANCE.getPlanType_Plans();

		/**
		 * The meta object literal for the '<em><b>Ensure Parametrisation Consistency</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PLAN_TYPE___ENSURE_PARAMETRISATION_CONSISTENCY = eINSTANCE.getPlanType__EnsureParametrisationConsistency();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.RatingImpl <em>Rating</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.RatingImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getRating()
		 * @generated
		 */
		EClass RATING = eINSTANCE.getRating();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.PostConditionImpl <em>Post Condition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.PostConditionImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getPostCondition()
		 * @generated
		 */
		EClass POST_CONDITION = eINSTANCE.getPostCondition();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.RuntimeConditionImpl <em>Runtime Condition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.RuntimeConditionImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getRuntimeCondition()
		 * @generated
		 */
		EClass RUNTIME_CONDITION = eINSTANCE.getRuntimeCondition();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.PlanElementImpl <em>Plan Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.PlanElementImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getPlanElement()
		 * @generated
		 */
		EClass PLAN_ELEMENT = eINSTANCE.getPlanElement();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLAN_ELEMENT__ID = eINSTANCE.getPlanElement_Id();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLAN_ELEMENT__NAME = eINSTANCE.getPlanElement_Name();

		/**
		 * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLAN_ELEMENT__COMMENT = eINSTANCE.getPlanElement_Comment();

		/**
		 * The meta object literal for the '<em><b>Generate ID</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PLAN_ELEMENT___GENERATE_ID = eINSTANCE.getPlanElement__GenerateID();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.TaskImpl <em>Task</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.TaskImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getTask()
		 * @generated
		 */
		EClass TASK = eINSTANCE.getTask();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TASK__DESCRIPTION = eINSTANCE.getTask_Description();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.EStringToEStringMapEntryImpl <em>EString To EString Map Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.EStringToEStringMapEntryImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getEStringToEStringMapEntry()
		 * @generated
		 */
		EClass ESTRING_TO_ESTRING_MAP_ENTRY = eINSTANCE.getEStringToEStringMapEntry();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ESTRING_TO_ESTRING_MAP_ENTRY__KEY = eINSTANCE.getEStringToEStringMapEntry_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ESTRING_TO_ESTRING_MAP_ENTRY__VALUE = eINSTANCE.getEStringToEStringMapEntry_Value();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.RoleImpl <em>Role</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.RoleImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getRole()
		 * @generated
		 */
		EClass ROLE = eINSTANCE.getRole();

		/**
		 * The meta object literal for the '<em><b>Characteristics</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROLE__CHARACTERISTICS = eINSTANCE.getRole_Characteristics();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.RoleSetImpl <em>Role Set</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.RoleSetImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getRoleSet()
		 * @generated
		 */
		EClass ROLE_SET = eINSTANCE.getRoleSet();

		/**
		 * The meta object literal for the '<em><b>Usable With Plan ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ROLE_SET__USABLE_WITH_PLAN_ID = eINSTANCE.getRoleSet_UsableWithPlanID();

		/**
		 * The meta object literal for the '<em><b>Default</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ROLE_SET__DEFAULT = eINSTANCE.getRoleSet_Default();

		/**
		 * The meta object literal for the '<em><b>Mappings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROLE_SET__MAPPINGS = eINSTANCE.getRoleSet_Mappings();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.ELongToDoubleMapEntryImpl <em>ELong To Double Map Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.ELongToDoubleMapEntryImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getELongToDoubleMapEntry()
		 * @generated
		 */
		EClass ELONG_TO_DOUBLE_MAP_ENTRY = eINSTANCE.getELongToDoubleMapEntry();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ELONG_TO_DOUBLE_MAP_ENTRY__KEY = eINSTANCE.getELongToDoubleMapEntry_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ELONG_TO_DOUBLE_MAP_ENTRY__VALUE = eINSTANCE.getELongToDoubleMapEntry_Value();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.RoleDefinitionSetImpl <em>Role Definition Set</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.RoleDefinitionSetImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getRoleDefinitionSet()
		 * @generated
		 */
		EClass ROLE_DEFINITION_SET = eINSTANCE.getRoleDefinitionSet();

		/**
		 * The meta object literal for the '<em><b>Roles</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROLE_DEFINITION_SET__ROLES = eINSTANCE.getRoleDefinitionSet_Roles();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.RoleTaskMappingImpl <em>Role Task Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.RoleTaskMappingImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getRoleTaskMapping()
		 * @generated
		 */
		EClass ROLE_TASK_MAPPING = eINSTANCE.getRoleTaskMapping();

		/**
		 * The meta object literal for the '<em><b>Task Priorities</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROLE_TASK_MAPPING__TASK_PRIORITIES = eINSTANCE.getRoleTaskMapping_TaskPriorities();

		/**
		 * The meta object literal for the '<em><b>Role</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROLE_TASK_MAPPING__ROLE = eINSTANCE.getRoleTaskMapping_Role();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.CharacteristicImpl <em>Characteristic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.CharacteristicImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getCharacteristic()
		 * @generated
		 */
		EClass CHARACTERISTIC = eINSTANCE.getCharacteristic();

		/**
		 * The meta object literal for the '<em><b>Weight</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHARACTERISTIC__WEIGHT = eINSTANCE.getCharacteristic_Weight();

		/**
		 * The meta object literal for the '<em><b>Capability</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHARACTERISTIC__CAPABILITY = eINSTANCE.getCharacteristic_Capability();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHARACTERISTIC__VALUE = eINSTANCE.getCharacteristic_Value();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.TaskGraphImpl <em>Task Graph</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.TaskGraphImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getTaskGraph()
		 * @generated
		 */
		EClass TASK_GRAPH = eINSTANCE.getTaskGraph();

		/**
		 * The meta object literal for the '<em><b>Nodes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK_GRAPH__NODES = eINSTANCE.getTaskGraph_Nodes();

		/**
		 * The meta object literal for the '<em><b>Edges</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK_GRAPH__EDGES = eINSTANCE.getTaskGraph_Edges();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.EdgeImpl <em>Edge</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.EdgeImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getEdge()
		 * @generated
		 */
		EClass EDGE = eINSTANCE.getEdge();

		/**
		 * The meta object literal for the '<em><b>From</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDGE__FROM = eINSTANCE.getEdge_From();

		/**
		 * The meta object literal for the '<em><b>To</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDGE__TO = eINSTANCE.getEdge_To();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.TaskWrapperImpl <em>Task Wrapper</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.TaskWrapperImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getTaskWrapper()
		 * @generated
		 */
		EClass TASK_WRAPPER = eINSTANCE.getTaskWrapper();

		/**
		 * The meta object literal for the '<em><b>Task</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK_WRAPPER__TASK = eINSTANCE.getTaskWrapper_Task();

		/**
		 * The meta object literal for the '<em><b>Mappings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK_WRAPPER__MAPPINGS = eINSTANCE.getTaskWrapper_Mappings();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.InternalRoleTaskMappingImpl <em>Internal Role Task Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.InternalRoleTaskMappingImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getInternalRoleTaskMapping()
		 * @generated
		 */
		EClass INTERNAL_ROLE_TASK_MAPPING = eINSTANCE.getInternalRoleTaskMapping();

		/**
		 * The meta object literal for the '<em><b>Role</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INTERNAL_ROLE_TASK_MAPPING__ROLE = eINSTANCE.getInternalRoleTaskMapping_Role();

		/**
		 * The meta object literal for the '<em><b>Priority</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INTERNAL_ROLE_TASK_MAPPING__PRIORITY = eINSTANCE.getInternalRoleTaskMapping_Priority();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.NodeImpl <em>Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.NodeImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getNode()
		 * @generated
		 */
		EClass NODE = eINSTANCE.getNode();

		/**
		 * The meta object literal for the '<em><b>In Edge</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE__IN_EDGE = eINSTANCE.getNode_InEdge();

		/**
		 * The meta object literal for the '<em><b>Out Edge</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE__OUT_EDGE = eINSTANCE.getNode_OutEdge();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.TaskRepositoryImpl <em>Task Repository</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.TaskRepositoryImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getTaskRepository()
		 * @generated
		 */
		EClass TASK_REPOSITORY = eINSTANCE.getTaskRepository();

		/**
		 * The meta object literal for the '<em><b>Tasks</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK_REPOSITORY__TASKS = eINSTANCE.getTaskRepository_Tasks();

		/**
		 * The meta object literal for the '<em><b>Default Task</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK_REPOSITORY__DEFAULT_TASK = eINSTANCE.getTaskRepository_DefaultTask();

		/**
		 * The meta object literal for the '<em><b>Create Default Task</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TASK_REPOSITORY___CREATE_DEFAULT_TASK = eINSTANCE.getTaskRepository__CreateDefaultTask();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.SynchronisationImpl <em>Synchronisation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.SynchronisationImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getSynchronisation()
		 * @generated
		 */
		EClass SYNCHRONISATION = eINSTANCE.getSynchronisation();

		/**
		 * The meta object literal for the '<em><b>Synched Transitions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYNCHRONISATION__SYNCHED_TRANSITIONS = eINSTANCE.getSynchronisation_SynchedTransitions();

		/**
		 * The meta object literal for the '<em><b>Talk Timeout</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYNCHRONISATION__TALK_TIMEOUT = eINSTANCE.getSynchronisation_TalkTimeout();

		/**
		 * The meta object literal for the '<em><b>Sync Timeout</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYNCHRONISATION__SYNC_TIMEOUT = eINSTANCE.getSynchronisation_SyncTimeout();

		/**
		 * The meta object literal for the '<em><b>Fail On Sync Time Out</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYNCHRONISATION__FAIL_ON_SYNC_TIME_OUT = eINSTANCE.getSynchronisation_FailOnSyncTimeOut();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.VariableImpl <em>Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.VariableImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getVariable()
		 * @generated
		 */
		EClass VARIABLE = eINSTANCE.getVariable();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VARIABLE__TYPE = eINSTANCE.getVariable_Type();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.ParametrisationImpl <em>Parametrisation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.ParametrisationImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getParametrisation()
		 * @generated
		 */
		EClass PARAMETRISATION = eINSTANCE.getParametrisation();

		/**
		 * The meta object literal for the '<em><b>Subplan</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAMETRISATION__SUBPLAN = eINSTANCE.getParametrisation_Subplan();

		/**
		 * The meta object literal for the '<em><b>Subvar</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAMETRISATION__SUBVAR = eINSTANCE.getParametrisation_Subvar();

		/**
		 * The meta object literal for the '<em><b>Var</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAMETRISATION__VAR = eINSTANCE.getParametrisation_Var();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AnnotatedPlanImpl <em>Annotated Plan</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AnnotatedPlanImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getAnnotatedPlan()
		 * @generated
		 */
		EClass ANNOTATED_PLAN = eINSTANCE.getAnnotatedPlan();

		/**
		 * The meta object literal for the '<em><b>Plan</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ANNOTATED_PLAN__PLAN = eINSTANCE.getAnnotatedPlan_Plan();

		/**
		 * The meta object literal for the '<em><b>Activated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ANNOTATED_PLAN__ACTIVATED = eINSTANCE.getAnnotatedPlan_Activated();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.QuantifierImpl <em>Quantifier</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.QuantifierImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getQuantifier()
		 * @generated
		 */
		EClass QUANTIFIER = eINSTANCE.getQuantifier();

		/**
		 * The meta object literal for the '<em><b>Scope</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QUANTIFIER__SCOPE = eINSTANCE.getQuantifier_Scope();

		/**
		 * The meta object literal for the '<em><b>Sorts</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUANTIFIER__SORTS = eINSTANCE.getQuantifier_Sorts();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.ForallAgentsImpl <em>Forall Agents</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.ForallAgentsImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getForallAgents()
		 * @generated
		 */
		EClass FORALL_AGENTS = eINSTANCE.getForallAgents();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.IInhabitable <em>IInhabitable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.IInhabitable
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getIInhabitable()
		 * @generated
		 */
		EClass IINHABITABLE = eINSTANCE.getIInhabitable();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.CapabilityImpl <em>Capability</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.CapabilityImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getCapability()
		 * @generated
		 */
		EClass CAPABILITY = eINSTANCE.getCapability();

		/**
		 * The meta object literal for the '<em><b>Cap Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CAPABILITY__CAP_VALUES = eINSTANCE.getCapability_CapValues();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.CapValueImpl <em>Cap Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.CapValueImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getCapValue()
		 * @generated
		 */
		EClass CAP_VALUE = eINSTANCE.getCapValue();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.CapabilityDefinitionSetImpl <em>Capability Definition Set</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.CapabilityDefinitionSetImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getCapabilityDefinitionSet()
		 * @generated
		 */
		EClass CAPABILITY_DEFINITION_SET = eINSTANCE.getCapabilityDefinitionSet();

		/**
		 * The meta object literal for the '<em><b>Capabilities</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CAPABILITY_DEFINITION_SET__CAPABILITIES = eINSTANCE.getCapabilityDefinitionSet_Capabilities();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.PlanningProblemImpl <em>Planning Problem</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.PlanningProblemImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getPlanningProblem()
		 * @generated
		 */
		EClass PLANNING_PROBLEM = eINSTANCE.getPlanningProblem();

		/**
		 * The meta object literal for the '<em><b>Plans</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLANNING_PROBLEM__PLANS = eINSTANCE.getPlanningProblem_Plans();

		/**
		 * The meta object literal for the '<em><b>Planner</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLANNING_PROBLEM__PLANNER = eINSTANCE.getPlanningProblem_Planner();

		/**
		 * The meta object literal for the '<em><b>Alternative Plan</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLANNING_PROBLEM__ALTERNATIVE_PLAN = eINSTANCE.getPlanningProblem_AlternativePlan();

		/**
		 * The meta object literal for the '<em><b>Wait Plan</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLANNING_PROBLEM__WAIT_PLAN = eINSTANCE.getPlanningProblem_WaitPlan();

		/**
		 * The meta object literal for the '<em><b>Update Rate</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLANNING_PROBLEM__UPDATE_RATE = eINSTANCE.getPlanningProblem_UpdateRate();

		/**
		 * The meta object literal for the '<em><b>Distribute Problem</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLANNING_PROBLEM__DISTRIBUTE_PROBLEM = eINSTANCE.getPlanningProblem_DistributeProblem();

		/**
		 * The meta object literal for the '<em><b>Planning Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLANNING_PROBLEM__PLANNING_TYPE = eINSTANCE.getPlanningProblem_PlanningType();

		/**
		 * The meta object literal for the '<em><b>Requirements</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLANNING_PROBLEM__REQUIREMENTS = eINSTANCE.getPlanningProblem_Requirements();

		/**
		 * The meta object literal for the '<em><b>Planner Params</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLANNING_PROBLEM__PLANNER_PARAMS = eINSTANCE.getPlanningProblem_PlannerParams();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.PlannerImpl <em>Planner</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.PlannerImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getPlanner()
		 * @generated
		 */
		EClass PLANNER = eINSTANCE.getPlanner();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLANNER__TYPE = eINSTANCE.getPlanner_Type();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLANNER__NAME = eINSTANCE.getPlanner_Name();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLANNER__PARAMETERS = eINSTANCE.getPlanner_Parameters();

		/**
		 * The meta object literal for the '<em><b>Command</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLANNER__COMMAND = eINSTANCE.getPlanner_Command();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.FluentImpl <em>Fluent</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.FluentImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getFluent()
		 * @generated
		 */
		EClass FLUENT = eINSTANCE.getFluent();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FLUENT__NAME = eINSTANCE.getFluent_Name();

		/**
		 * The meta object literal for the '<em><b>Formula</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FLUENT__FORMULA = eINSTANCE.getFluent_Formula();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.DomainDescriptionImpl <em>Domain Description</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.DomainDescriptionImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getDomainDescription()
		 * @generated
		 */
		EClass DOMAIN_DESCRIPTION = eINSTANCE.getDomainDescription();

		/**
		 * The meta object literal for the '<em><b>Fluents</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOMAIN_DESCRIPTION__FLUENTS = eINSTANCE.getDomainDescription_Fluents();

		/**
		 * The meta object literal for the '<em><b>Types</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOMAIN_DESCRIPTION__TYPES = eINSTANCE.getDomainDescription_Types();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOMAIN_DESCRIPTION__NAME = eINSTANCE.getDomainDescription_Name();

		/**
		 * The meta object literal for the '<em><b>Constants</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOMAIN_DESCRIPTION__CONSTANTS = eINSTANCE.getDomainDescription_Constants();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.PlannersImpl <em>Planners</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.PlannersImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getPlanners()
		 * @generated
		 */
		EClass PLANNERS = eINSTANCE.getPlanners();

		/**
		 * The meta object literal for the '<em><b>Planners</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLANNERS__PLANNERS = eINSTANCE.getPlanners_Planners();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLANNERS__NAME = eINSTANCE.getPlanners_Name();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.EStringToEObjectMapEntryImpl <em>EString To EObject Map Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.EStringToEObjectMapEntryImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getEStringToEObjectMapEntry()
		 * @generated
		 */
		EClass ESTRING_TO_EOBJECT_MAP_ENTRY = eINSTANCE.getEStringToEObjectMapEntry();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ESTRING_TO_EOBJECT_MAP_ENTRY__KEY = eINSTANCE.getEStringToEObjectMapEntry_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ESTRING_TO_EOBJECT_MAP_ENTRY__VALUE = eINSTANCE.getEStringToEObjectMapEntry_Value();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.FluentParametersImpl <em>Fluent Parameters</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.FluentParametersImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getFluentParameters()
		 * @generated
		 */
		EClass FLUENT_PARAMETERS = eINSTANCE.getFluentParameters();

		/**
		 * The meta object literal for the '<em><b>Parameter</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FLUENT_PARAMETERS__PARAMETER = eINSTANCE.getFluentParameters_Parameter();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FLUENT_PARAMETERS__TYPE = eINSTANCE.getFluentParameters_Type();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.ConstantImpl <em>Constant</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.ConstantImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getConstant()
		 * @generated
		 */
		EClass CONSTANT = eINSTANCE.getConstant();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSTANT__NAME = eINSTANCE.getConstant_Name();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSTANT__TYPE = eINSTANCE.getConstant_Type();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.BehaviourCreatorImpl <em>Behaviour Creator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.BehaviourCreatorImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getBehaviourCreator()
		 * @generated
		 */
		EClass BEHAVIOUR_CREATOR = eINSTANCE.getBehaviourCreator();

		/**
		 * The meta object literal for the '<em><b>Behaviours</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BEHAVIOUR_CREATOR__BEHAVIOURS = eINSTANCE.getBehaviourCreator_Behaviours();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.ConditionCreatorImpl <em>Condition Creator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.ConditionCreatorImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getConditionCreator()
		 * @generated
		 */
		EClass CONDITION_CREATOR = eINSTANCE.getConditionCreator();

		/**
		 * The meta object literal for the '<em><b>Conditions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITION_CREATOR__CONDITIONS = eINSTANCE.getConditionCreator_Conditions();

		/**
		 * The meta object literal for the '<em><b>Plans</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITION_CREATOR__PLANS = eINSTANCE.getConditionCreator_Plans();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.UtilityFunctionCreatorImpl <em>Utility Function Creator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.UtilityFunctionCreatorImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getUtilityFunctionCreator()
		 * @generated
		 */
		EClass UTILITY_FUNCTION_CREATOR = eINSTANCE.getUtilityFunctionCreator();

		/**
		 * The meta object literal for the '<em><b>Conditions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UTILITY_FUNCTION_CREATOR__CONDITIONS = eINSTANCE.getUtilityFunctionCreator_Conditions();

		/**
		 * The meta object literal for the '<em><b>Plans</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UTILITY_FUNCTION_CREATOR__PLANS = eINSTANCE.getUtilityFunctionCreator_Plans();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.ConstraintCreatorImpl <em>Constraint Creator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.ConstraintCreatorImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getConstraintCreator()
		 * @generated
		 */
		EClass CONSTRAINT_CREATOR = eINSTANCE.getConstraintCreator();

		/**
		 * The meta object literal for the '<em><b>Conditions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTRAINT_CREATOR__CONDITIONS = eINSTANCE.getConstraintCreator_Conditions();

		/**
		 * The meta object literal for the '<em><b>Plans</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTRAINT_CREATOR__PLANS = eINSTANCE.getConstraintCreator_Plans();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.DomainBehaviourImpl <em>Domain Behaviour</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.DomainBehaviourImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getDomainBehaviour()
		 * @generated
		 */
		EClass DOMAIN_BEHAVIOUR = eINSTANCE.getDomainBehaviour();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.DomainConditionImpl <em>Domain Condition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.DomainConditionImpl
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getDomainCondition()
		 * @generated
		 */
		EClass DOMAIN_CONDITION = eINSTANCE.getDomainCondition();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanningType <em>Planning Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanningType
		 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.impl.AlicaPackageImpl#getPlanningType()
		 * @generated
		 */
		EEnum PLANNING_TYPE = eINSTANCE.getPlanningType();

	}

} //AlicaPackage
