/**
 */
package de.uni_kassel.vs.cn.planDesigner.alica;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Behaviour</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link alica.Behaviour#getPreCondition <em>Pre Condition</em>}</li>
 *   <li>{@link alica.Behaviour#getRuntimeCondition <em>Runtime Condition</em>}</li>
 *   <li>{@link alica.Behaviour#getPostCondition <em>Post Condition</em>}</li>
 *   <li>{@link alica.Behaviour#getFrequency <em>Frequency</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.uni_kassel.vs.cn.planDesigner.alica.AlicaPackage#getBehaviour()
 * @model
 * @generated
 */
public interface Behaviour extends AbstractPlan {
	/**
	 * Returns the value of the '<em><b>Pre Condition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pre Condition</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pre Condition</em>' reference.
	 * @see #setPreCondition(PreCondition)
	 * @see de.uni_kassel.vs.cn.planDesigner.alica.AlicaPackage#getBehaviour_PreCondition()
	 * @model
	 * @generated
	 */
	PreCondition getPreCondition();

	/**
	 * Sets the value of the '{@link alica.Behaviour#getPreCondition <em>Pre Condition</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pre Condition</em>' reference.
	 * @see #getPreCondition()
	 * @generated
	 */
	void setPreCondition(PreCondition value);

	/**
	 * Returns the value of the '<em><b>Runtime Condition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Runtime Condition</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Runtime Condition</em>' reference.
	 * @see #setRuntimeCondition(RuntimeCondition)
	 * @see de.uni_kassel.vs.cn.planDesigner.alica.AlicaPackage#getBehaviour_RuntimeCondition()
	 * @model
	 * @generated
	 */
	RuntimeCondition getRuntimeCondition();

	/**
	 * Sets the value of the '{@link alica.Behaviour#getRuntimeCondition <em>Runtime Condition</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Runtime Condition</em>' reference.
	 * @see #getRuntimeCondition()
	 * @generated
	 */
	void setRuntimeCondition(RuntimeCondition value);

	/**
	 * Returns the value of the '<em><b>Post Condition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Post Condition</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Post Condition</em>' reference.
	 * @see #setPostCondition(PostCondition)
	 * @see de.uni_kassel.vs.cn.planDesigner.alica.AlicaPackage#getBehaviour_PostCondition()
	 * @model
	 * @generated
	 */
	PostCondition getPostCondition();

	/**
	 * Sets the value of the '{@link alica.Behaviour#getPostCondition <em>Post Condition</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Post Condition</em>' reference.
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
	 * Sets the value of the '{@link alica.Behaviour#getFrequency <em>Frequency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Frequency</em>' attribute.
	 * @see #getFrequency()
	 * @generated
	 */
	void setFrequency(int value);

} // Behaviour
