/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.uni_kassel.vs.cn.planDesigner.alica;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link alica.Node#getInEdge <em>In Edge</em>}</li>
 *   <li>{@link alica.Node#getOutEdge <em>Out Edge</em>}</li>
 * </ul>
 * </p>
 *
 * @see alica.AlicaPackage#getNode()
 * @model
 * @generated
 */
public interface Node extends EObject {
	/**
	 * Returns the value of the '<em><b>In Edge</b></em>' reference list.
	 * The list contents are of type {@link alica.Edge}.
	 * It is bidirectional and its opposite is '{@link alica.Edge#getTo <em>To</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>In Edge</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>In Edge</em>' reference list.
	 * @see alica.AlicaPackage#getNode_InEdge()
	 * @see alica.Edge#getTo
	 * @model opposite="to"
	 * @generated
	 */
	EList<Edge> getInEdge();

	/**
	 * Returns the value of the '<em><b>Out Edge</b></em>' reference list.
	 * The list contents are of type {@link alica.Edge}.
	 * It is bidirectional and its opposite is '{@link alica.Edge#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Out Edge</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Out Edge</em>' reference list.
	 * @see alica.AlicaPackage#getNode_OutEdge()
	 * @see alica.Edge#getFrom
	 * @model opposite="from"
	 * @generated
	 */
	EList<Edge> getOutEdge();

} // Node
