/**
 */
package de.uni_kassel.vs.cn.planDesigner.alicamodel;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the alicamodel object '<em><b>Role Definition Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alicamodel.RoleDefinitionSet#getRoles <em>Roles</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.AlicaPackage#getRoleDefinitionSet()
 * @model
 * @generated
 */
public interface RoleDefinitionSet extends PlanElement {
	/**
	 * Returns the value of the '<em><b>Roles</b></em>' containment reference list.
	 * The list contents are of type {@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Role}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Roles</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Roles</em>' containment reference list.
	 * @see de.uni_kassel.vs.cn.planDesigner.alicamodel.AlicaPackage#getRoleDefinitionSet_Roles()
	 * @model containment="true"
	 * @generated
	 */
	EList<Role> getRoles();

} // RoleDefinitionSet
