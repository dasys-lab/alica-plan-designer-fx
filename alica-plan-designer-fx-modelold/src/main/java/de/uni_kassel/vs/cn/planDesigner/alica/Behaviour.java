/**
 */
package de.uni_kassel.vs.cn.planDesigner.alica;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the alica object '<em><b>Behaviour</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alica.Behaviour#getPreCondition <em>Pre Condition</em>}</li>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alica.Behaviour#getRuntimeCondition <em>Runtime Condition</em>}</li>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alica.Behaviour#getPostCondition <em>Post Condition</em>}</li>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alica.Behaviour#getFrequency <em>Frequency</em>}</li>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alica.Behaviour#getVars <em>Vars</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.uni_kassel.vs.cn.planDesigner.alica.AlicaPackage#getBehaviour()
 * @model
 * @generated
 */
public interface Behaviour extends AbstractPlan {
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
	 * @see de.uni_kassel.vs.cn.planDesigner.alica.AlicaPackage#getBehaviour_PreCondition()
	 * @model containment="true"
	 * @generated
	 */
	PreCondition getPreCondition();

	/**
	 * Sets the value of the '{@link de.uni_kassel.vs.cn.planDesigner.alica.Behaviour#getPreCondition <em>Pre Condition</em>}' containment reference.
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
	 * @see de.uni_kassel.vs.cn.planDesigner.alica.AlicaPackage#getBehaviour_RuntimeCondition()
	 * @model containment="true"
	 * @generated
	 */
	RuntimeCondition getRuntimeCondition();

	/**
	 * Sets the value of the '{@link de.uni_kassel.vs.cn.planDesigner.alica.Behaviour#getRuntimeCondition <em>Runtime Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Runtime Condition</em>' containment reference.
	 * @see #getRuntimeCondition()
	 * @generated
	 */
	void setRuntimeCondition(RuntimeCondition value);

	/**
	 * Returns the value of the '<em><b>Post Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Post Condition</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Post Condition</em>' containment reference.
	 * @see #setPostCondition(PostCondition)
	 * @see de.uni_kassel.vs.cn.planDesigner.alica.AlicaPackage#getBehaviour_PostCondition()
	 * @model containment="true"
	 * @generated
	 */
	PostCondition getPostCondition();

	/**
	 * Sets the value of the '{@link de.uni_kassel.vs.cn.planDesigner.alica.Behaviour#getPostCondition <em>Post Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Post Condition</em>' containment reference.
	 * @see #getPostCondition()
	 * @generated
	 */
	void setPostCondition(PostCondition value);

	/**
	 * Returns the value of the '<em><b>Frequency</b></em>' attribute.
	 * The default value is <code>"30"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Frequency</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Frequency</em>' attribute.
	 * @see #setFrequency(int)
	 * @see de.uni_kassel.vs.cn.planDesigner.alica.AlicaPackage#getBehaviour_Frequency()
	 * @model default="30"
	 * @generated
	 */
	int getFrequency();

	/**
	 * Sets the value of the '{@link de.uni_kassel.vs.cn.planDesigner.alica.Behaviour#getFrequency <em>Frequency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Frequency</em>' attribute.
	 * @see #getFrequency()
	 * @generated
	 */
	void setFrequency(int value);

	/**
	 * Returns the value of the '<em><b>Vars</b></em>' containment reference list.
	 * The list contents are of type {@link de.uni_kassel.vs.cn.planDesigner.alica.Variable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Vars</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vars</em>' containment reference list.
	 * @see de.uni_kassel.vs.cn.planDesigner.alica.AlicaPackage#getBehaviour_Vars()
	 * @model containment="true"
	 * @generated
	 */
	EList<Variable> getVars();

} // Behaviour
