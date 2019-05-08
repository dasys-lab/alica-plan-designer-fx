/**
 */
package de.uni_kassel.vs.cn.planDesigner.alicamodel;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the alicamodel object '<em><b>Plan</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getPriority <em>Priority</em>}</li>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getStates <em>States</em>}</li>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getTransitions <em>Transitions</em>}</li>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getMinCardinality <em>Min Cardinality</em>}</li>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getMaxCardinality <em>Max Cardinality</em>}</li>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getSynchronisations <em>Synchronisations</em>}</li>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getEntryPoints <em>Entry Points</em>}</li>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#isMasterPlan <em>Master Plan</em>}</li>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getUtilityFunction <em>Utility Function</em>}</li>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getUtilityThreshold <em>Utility Threshold</em>}</li>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getVars <em>Vars</em>}</li>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getPreCondition <em>Pre Condition</em>}</li>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getRuntimeCondition <em>Runtime Condition</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.AlicaPackage#getPlan()
 * @model
 * @generated
 */
public interface Plan extends AbstractPlan {
	/**
	 * Returns the value of the '<em><b>Priority</b></em>' attribute.
	 * The default value is <code>"0.0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Priority</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Priority</em>' attribute.
	 * @see #setPriority(double)
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.AlicaPackage#getPlan_Priority()
	 * @model default="0.0"
	 * @generated
	 */
	double getPriority();

	/**
	 * Sets the value of the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getPriority <em>Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Priority</em>' attribute.
	 * @see #getPriority()
	 * @generated
	 */
	void setPriority(double value);

	/**
	 * Returns the value of the '<em><b>States</b></em>' containment reference list.
	 * The list contents are of type {@link de.uni_kassel.vs.cn.planDesigner.alicamodel.State}.
	 * It is bidirectional and its opposite is '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.State#getInPlan <em>In Plan</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>States</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>States</em>' containment reference list.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.AlicaPackage#getPlan_States()
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.State#getInPlan
	 * @model opposite="inPlan" containment="true"
	 * @generated
	 */
	EList<State> getStates();

	/**
	 * Returns the value of the '<em><b>Transitions</b></em>' containment reference list.
	 * The list contents are of type {@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Transition}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transitions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transitions</em>' containment reference list.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.AlicaPackage#getPlan_Transitions()
	 * @model containment="true"
	 * @generated
	 */
	EList<Transition> getTransitions();

	/**
	 * Returns the value of the '<em><b>Min Cardinality</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Min Cardinality</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Min Cardinality</em>' attribute.
	 * @see #setMinCardinality(int)
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.AlicaPackage#getPlan_MinCardinality()
	 * @model default="0"
	 * @generated
	 */
	int getMinCardinality();

	/**
	 * Sets the value of the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getMinCardinality <em>Min Cardinality</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min Cardinality</em>' attribute.
	 * @see #getMinCardinality()
	 * @generated
	 */
	void setMinCardinality(int value);

	/**
	 * Returns the value of the '<em><b>Max Cardinality</b></em>' attribute.
	 * The default value is <code>"2147483647"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Cardinality</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Cardinality</em>' attribute.
	 * @see #setMaxCardinality(int)
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.AlicaPackage#getPlan_MaxCardinality()
	 * @model default="2147483647"
	 * @generated
	 */
	int getMaxCardinality();

	/**
	 * Sets the value of the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getMaxCardinality <em>Max Cardinality</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Cardinality</em>' attribute.
	 * @see #getMaxCardinality()
	 * @generated
	 */
	void setMaxCardinality(int value);

	/**
	 * Returns the value of the '<em><b>Synchronisations</b></em>' containment reference list.
	 * The list contents are of type {@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Synchronisation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Synchronisations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Synchronisations</em>' containment reference list.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.AlicaPackage#getPlan_Synchronisations()
	 * @model containment="true"
	 * @generated
	 */
	EList<Synchronisation> getSynchronisations();

	/**
	 * Returns the value of the '<em><b>Entry Points</b></em>' containment reference list.
	 * The list contents are of type {@link de.uni_kassel.vs.cn.planDesigner.alicamodel.EntryPoint}.
	 * It is bidirectional and its opposite is '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.EntryPoint#getPlan <em>Plan</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entry Points</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entry Points</em>' containment reference list.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.AlicaPackage#getPlan_EntryPoints()
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.EntryPoint#getPlan
	 * @model opposite="plan" containment="true"
	 * @generated
	 */
	EList<EntryPoint> getEntryPoints();

	/**
	 * Returns the value of the '<em><b>Master Plan</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Master Plan</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Master Plan</em>' attribute.
	 * @see #setMasterPlan(boolean)
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.AlicaPackage#getPlan_MasterPlan()
	 * @model default="false"
	 * @generated
	 */
	boolean isMasterPlan();

	/**
	 * Sets the value of the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#isMasterPlan <em>Master Plan</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Master Plan</em>' attribute.
	 * @see #isMasterPlan()
	 * @generated
	 */
	void setMasterPlan(boolean value);

	/**
	 * Returns the value of the '<em><b>Utility Function</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Utility Function</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Utility Function</em>' attribute.
	 * @see #setUtilityFunction(String)
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.AlicaPackage#getPlan_UtilityFunction()
	 * @model default=""
	 * @generated
	 */
	String getUtilityFunction();

	/**
	 * Sets the value of the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getUtilityFunction <em>Utility Function</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Utility Function</em>' attribute.
	 * @see #getUtilityFunction()
	 * @generated
	 */
	void setUtilityFunction(String value);

	/**
	 * Returns the value of the '<em><b>Utility Threshold</b></em>' attribute.
	 * The default value is <code>"0.1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Utility Threshold</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Utility Threshold</em>' attribute.
	 * @see #setUtilityThreshold(double)
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.AlicaPackage#getPlan_UtilityThreshold()
	 * @model default="0.1"
	 * @generated
	 */
	double getUtilityThreshold();

	/**
	 * Sets the value of the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getUtilityThreshold <em>Utility Threshold</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Utility Threshold</em>' attribute.
	 * @see #getUtilityThreshold()
	 * @generated
	 */
	void setUtilityThreshold(double value);

	/**
	 * Returns the value of the '<em><b>Vars</b></em>' containment reference list.
	 * The list contents are of type {@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Variable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Vars</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vars</em>' containment reference list.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.AlicaPackage#getPlan_Vars()
	 * @model containment="true"
	 * @generated
	 */
	EList<Variable> getVars();

	/**
	 * Returns the value of the '<em><b>Pre Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pre Condition</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pre Condition</em>' containment reference.
	 * @see #setPreCondition(PreCondition)
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.AlicaPackage#getPlan_PreCondition()
	 * @model containment="true"
	 * @generated
	 */
	PreCondition getPreCondition();

	/**
	 * Sets the value of the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getPreCondition <em>Pre Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pre Condition</em>' containment reference.
	 * @see #getPreCondition()
	 * @generated
	 */
	void setPreCondition(PreCondition value);

	/**
	 * Returns the value of the '<em><b>Runtime Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Runtime Condition</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Runtime Condition</em>' containment reference.
	 * @see #setRuntimeCondition(RuntimeCondition)
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.AlicaPackage#getPlan_RuntimeCondition()
	 * @model containment="true"
	 * @generated
	 */
	RuntimeCondition getRuntimeCondition();

	/**
	 * Sets the value of the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan#getRuntimeCondition <em>Runtime Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Runtime Condition</em>' containment reference.
	 * @see #getRuntimeCondition()
	 * @generated
	 */
	void setRuntimeCondition(RuntimeCondition value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void calculateCardinalities();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void ensureParametrisationConsistency();

} // Plan
