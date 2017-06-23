/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.uni_kassel.vs.cn.planDesigner.alica;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Condition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link alica.Condition#getConditionString <em>Condition String</em>}</li>
 *   <li>{@link alica.Condition#getAbstractPlan <em>Abstract Plan</em>}</li>
 *   <li>{@link alica.Condition#getVars <em>Vars</em>}</li>
 *   <li>{@link alica.Condition#getQuantifiers <em>Quantifiers</em>}</li>
 *   <li>{@link alica.Condition#getPluginName <em>Plugin Name</em>}</li>
 *   <li>{@link alica.Condition#getParameters <em>Parameters</em>}</li>
 * </ul>
 * </p>
 *
 * @see alica.AlicaPackage#getCondition()
 * @model abstract="true"
 * @generated
 */
public interface Condition extends PlanElement {
	/**
	 * Returns the value of the '<em><b>Condition String</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Condition String</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Condition String</em>' attribute.
	 * @see #setConditionString(String)
	 * @see alica.AlicaPackage#getCondition_ConditionString()
	 * @model default=""
	 * @generated
	 */
	String getConditionString();

	/**
	 * Sets the value of the '{@link alica.Condition#getConditionString <em>Condition String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Condition String</em>' attribute.
	 * @see #getConditionString()
	 * @generated
	 */
	void setConditionString(String value);

	/**
	 * Returns the value of the '<em><b>Abstract Plan</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Abstract Plan</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Abstract Plan</em>' reference.
	 * @see #setAbstractPlan(AbstractPlan)
	 * @see alica.AlicaPackage#getCondition_AbstractPlan()
	 * @model
	 * @generated
	 */
	AbstractPlan getAbstractPlan();

	/**
	 * Sets the value of the '{@link alica.Condition#getAbstractPlan <em>Abstract Plan</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Abstract Plan</em>' reference.
	 * @see #getAbstractPlan()
	 * @generated
	 */
	void setAbstractPlan(AbstractPlan value);

	/**
	 * Returns the value of the '<em><b>Vars</b></em>' reference list.
	 * The list contents are of type {@link alica.Variable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Vars</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vars</em>' reference list.
	 * @see alica.AlicaPackage#getCondition_Vars()
	 * @model
	 * @generated
	 */
	EList<Variable> getVars();

	/**
	 * Returns the value of the '<em><b>Quantifiers</b></em>' containment reference list.
	 * The list contents are of type {@link alica.Quantifier}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Quantifiers</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Quantifiers</em>' containment reference list.
	 * @see alica.AlicaPackage#getCondition_Quantifiers()
	 * @model containment="true"
	 * @generated
	 */
	EList<Quantifier> getQuantifiers();

	/**
	 * Returns the value of the '<em><b>Plugin Name</b></em>' attribute.
	 * The default value is <code>"DefaultPlugin"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Plugin Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Plugin Name</em>' attribute.
	 * @see #setPluginName(String)
	 * @see alica.AlicaPackage#getCondition_PluginName()
	 * @model default="DefaultPlugin"
	 * @generated
	 */
	String getPluginName();

	/**
	 * Sets the value of the '{@link alica.Condition#getPluginName <em>Plugin Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Plugin Name</em>' attribute.
	 * @see #getPluginName()
	 * @generated
	 */
	void setPluginName(String value);

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' map.
	 * The key is of type {@link String},
	 * and the value is of type {@link Object},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' map.
	 * @see alica.AlicaPackage#getCondition_Parameters()
	 * @model mapType="alica.EStringToEObjectMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EJavaObject>"
	 * @generated
	 */
	EMap<String, Object> getParameters();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void ensureVariableConsistency(AbstractPlan parentPlan);

} // Condition
