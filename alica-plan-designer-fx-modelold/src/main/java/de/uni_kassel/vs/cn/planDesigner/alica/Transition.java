/**
 */
package de.uni_kassel.vs.cn.planDesigner.alicamodel;


/**
 * <!-- begin-user-doc -->
 * A representation of the alicamodel object '<em><b>Transition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Transition#getMsg <em>Msg</em>}</li>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Transition#getPreCondition <em>Pre Condition</em>}</li>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Transition#getInState <em>In State</em>}</li>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Transition#getOutState <em>Out State</em>}</li>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Transition#getSynchronisation <em>Synchronisation</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.AlicaPackage#getTransition()
 * @model
 * @generated
 */
public interface Transition extends PlanElement {
	/**
	 * Returns the value of the '<em><b>Msg</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Msg</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Msg</em>' attribute.
	 * @see #setMsg(String)
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.AlicaPackage#getTransition_Msg()
	 * @model default=""
	 * @generated
	 */
	String getMsg();

	/**
	 * Sets the value of the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Transition#getMsg <em>Msg</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Msg</em>' attribute.
	 * @see #getMsg()
	 * @generated
	 */
	void setMsg(String value);

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
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.AlicaPackage#getTransition_PreCondition()
	 * @model containment="true" required="true"
	 * @generated
	 */
	PreCondition getPreCondition();

	/**
	 * Sets the value of the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Transition#getPreCondition <em>Pre Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pre Condition</em>' containment reference.
	 * @see #getPreCondition()
	 * @generated
	 */
	void setPreCondition(PreCondition value);

	/**
	 * Returns the value of the '<em><b>In State</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.State#getOutTransitions <em>Out Transitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>In State</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>In State</em>' reference.
	 * @see #setInState(State)
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.AlicaPackage#getTransition_InState()
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.State#getOutTransitions
	 * @model opposite="outTransitions"
	 * @generated
	 */
	State getInState();

	/**
	 * Sets the value of the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Transition#getInState <em>In State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>In State</em>' reference.
	 * @see #getInState()
	 * @generated
	 */
	void setInState(State value);

	/**
	 * Returns the value of the '<em><b>Out State</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.State#getInTransitions <em>In Transitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Out State</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Out State</em>' reference.
	 * @see #setOutState(State)
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.AlicaPackage#getTransition_OutState()
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.State#getInTransitions
	 * @model opposite="inTransitions"
	 * @generated
	 */
	State getOutState();

	/**
	 * Sets the value of the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Transition#getOutState <em>Out State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Out State</em>' reference.
	 * @see #getOutState()
	 * @generated
	 */
	void setOutState(State value);

	/**
	 * Returns the value of the '<em><b>Synchronisation</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Synchronisation#getSynchedTransitions <em>Synched Transitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Synchronisation</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Synchronisation</em>' reference.
	 * @see #setSynchronisation(Synchronisation)
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.AlicaPackage#getTransition_Synchronisation()
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.Synchronisation#getSynchedTransitions
	 * @model opposite="synchedTransitions"
	 * @generated
	 */
	Synchronisation getSynchronisation();

	/**
	 * Sets the value of the '{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Transition#getSynchronisation <em>Synchronisation</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Synchronisation</em>' reference.
	 * @see #getSynchronisation()
	 * @generated
	 */
	void setSynchronisation(Synchronisation value);

} // Transition
