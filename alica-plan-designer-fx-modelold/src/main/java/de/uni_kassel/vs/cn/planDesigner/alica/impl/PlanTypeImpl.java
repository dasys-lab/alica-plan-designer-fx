/**
 */
package de.uni_kassel.vs.cn.planDesigner.alica.impl;

import de.uni_kassel.vs.cn.planDesigner.alica.AlicaPackage;
import de.uni_kassel.vs.cn.planDesigner.alica.AnnotatedPlan;
import de.uni_kassel.vs.cn.planDesigner.alica.Parametrisation;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanType;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the alica object '<em><b>Plan Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alica.impl.PlanTypeImpl#getParametrisation <em>Parametrisation</em>}</li>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alica.impl.PlanTypeImpl#getPlans <em>Plans</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PlanTypeImpl extends AbstractPlanImpl implements PlanType {
	/**
	 * The cached value of the '{@link #getParametrisation() <em>Parametrisation</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParametrisation()
	 * @generated
	 * @ordered
	 */
	protected EList<Parametrisation> parametrisation;

	/**
	 * The cached value of the '{@link #getPlans() <em>Plans</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlans()
	 * @generated
	 * @ordered
	 */
	protected EList<AnnotatedPlan> plans;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PlanTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AlicaPackage.Literals.PLAN_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Parametrisation> getParametrisation() {
		if (parametrisation == null) {
			parametrisation = new EObjectContainmentEList<Parametrisation>(Parametrisation.class, this, AlicaPackage.PLAN_TYPE__PARAMETRISATION);
		}
		return parametrisation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AnnotatedPlan> getPlans() {
		if (plans == null) {
			plans = new EObjectContainmentEList<AnnotatedPlan>(AnnotatedPlan.class, this, AlicaPackage.PLAN_TYPE__PLANS);
		}
		return plans;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void ensureParametrisationConsistency() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AlicaPackage.PLAN_TYPE__PARAMETRISATION:
				return ((InternalEList<?>)getParametrisation()).basicRemove(otherEnd, msgs);
			case AlicaPackage.PLAN_TYPE__PLANS:
				return ((InternalEList<?>)getPlans()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AlicaPackage.PLAN_TYPE__PARAMETRISATION:
				return getParametrisation();
			case AlicaPackage.PLAN_TYPE__PLANS:
				return getPlans();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case AlicaPackage.PLAN_TYPE__PARAMETRISATION:
				getParametrisation().clear();
				getParametrisation().addAll((Collection<? extends Parametrisation>)newValue);
				return;
			case AlicaPackage.PLAN_TYPE__PLANS:
				getPlans().clear();
				getPlans().addAll((Collection<? extends AnnotatedPlan>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case AlicaPackage.PLAN_TYPE__PARAMETRISATION:
				getParametrisation().clear();
				return;
			case AlicaPackage.PLAN_TYPE__PLANS:
				getPlans().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case AlicaPackage.PLAN_TYPE__PARAMETRISATION:
				return parametrisation != null && !parametrisation.isEmpty();
			case AlicaPackage.PLAN_TYPE__PLANS:
				return plans != null && !plans.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case AlicaPackage.PLAN_TYPE___ENSURE_PARAMETRISATION_CONSISTENCY:
				ensureParametrisationConsistency();
				return null;
		}
		return super.eInvoke(operationID, arguments);
	}

} //PlanTypeImpl
