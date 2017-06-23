/**
 */
package de.uni_kassel.vs.cn.planDesigner.alica;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Plan</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link alica.AbstractPlan#getDestinationPath <em>Destination Path</em>}</li>
 * </ul>
 * </p>
 *
 * @see alica.AlicaPackage#getAbstractPlan()
 * @model abstract="true"
 * @generated
 */
public interface AbstractPlan extends PlanElement, IInhabitable {
	/**
	 * Returns the value of the '<em><b>Destination Path</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Destination Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Destination Path</em>' attribute.
	 * @see #setDestinationPath(String)
	 * @see alica.AlicaPackage#getAbstractPlan_DestinationPath()
	 * @model default=""
	 * @generated
	 */
	String getDestinationPath();

	/**
	 * Sets the value of the '{@link alica.AbstractPlan#getDestinationPath <em>Destination Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Destination Path</em>' attribute.
	 * @see #getDestinationPath()
	 * @generated
	 */
	void setDestinationPath(String value);

} // AbstractPlan
