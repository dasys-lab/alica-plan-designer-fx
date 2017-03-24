/**
 */
package de.uni_kassel.vs.cn.planDesigner.alica.impl;

import de.uni_kassel.vs.cn.planDesigner.alica.AlicaPackage;
import de.uni_kassel.vs.cn.planDesigner.alica.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alica.PostCondition;
import de.uni_kassel.vs.cn.planDesigner.alica.PreCondition;
import de.uni_kassel.vs.cn.planDesigner.alica.RuntimeCondition;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Behaviour</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alica.impl.BehaviourImpl#getPreCondition <em>Pre Condition</em>}</li>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alica.impl.BehaviourImpl#getRuntimeCondition <em>Runtime Condition</em>}</li>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alica.impl.BehaviourImpl#getPostCondition <em>Post Condition</em>}</li>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alica.impl.BehaviourImpl#getFrequency <em>Frequency</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BehaviourImpl extends AbstractPlanImpl implements Behaviour {
	/**
	 * The cached value of the '{@link #getPreCondition() <em>Pre Condition</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreCondition()
	 * @generated
	 * @ordered
	 */
	protected PreCondition preCondition;

	/**
	 * The cached value of the '{@link #getRuntimeCondition() <em>Runtime Condition</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRuntimeCondition()
	 * @generated
	 * @ordered
	 */
	protected RuntimeCondition runtimeCondition;

	/**
	 * The cached value of the '{@link #getPostCondition() <em>Post Condition</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPostCondition()
	 * @generated
	 * @ordered
	 */
	protected PostCondition postCondition;

	/**
	 * The default value of the '{@link #getFrequency() <em>Frequency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFrequency()
	 * @generated
	 * @ordered
	 */
	protected static final int FREQUENCY_EDEFAULT = 30;

	/**
	 * The cached value of the '{@link #getFrequency() <em>Frequency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFrequency()
	 * @generated
	 * @ordered
	 */
	protected int frequency = FREQUENCY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BehaviourImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AlicaPackage.Literals.BEHAVIOUR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PreCondition getPreCondition() {
		if (preCondition != null && preCondition.eIsProxy()) {
			InternalEObject oldPreCondition = (InternalEObject)preCondition;
			preCondition = (PreCondition)eResolveProxy(oldPreCondition);
			if (preCondition != oldPreCondition) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, AlicaPackage.BEHAVIOUR__PRE_CONDITION, oldPreCondition, preCondition));
			}
		}
		return preCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PreCondition basicGetPreCondition() {
		return preCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPreCondition(PreCondition newPreCondition) {
		PreCondition oldPreCondition = preCondition;
		preCondition = newPreCondition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AlicaPackage.BEHAVIOUR__PRE_CONDITION, oldPreCondition, preCondition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuntimeCondition getRuntimeCondition() {
		if (runtimeCondition != null && runtimeCondition.eIsProxy()) {
			InternalEObject oldRuntimeCondition = (InternalEObject)runtimeCondition;
			runtimeCondition = (RuntimeCondition)eResolveProxy(oldRuntimeCondition);
			if (runtimeCondition != oldRuntimeCondition) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, AlicaPackage.BEHAVIOUR__RUNTIME_CONDITION, oldRuntimeCondition, runtimeCondition));
			}
		}
		return runtimeCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuntimeCondition basicGetRuntimeCondition() {
		return runtimeCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRuntimeCondition(RuntimeCondition newRuntimeCondition) {
		RuntimeCondition oldRuntimeCondition = runtimeCondition;
		runtimeCondition = newRuntimeCondition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AlicaPackage.BEHAVIOUR__RUNTIME_CONDITION, oldRuntimeCondition, runtimeCondition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PostCondition getPostCondition() {
		if (postCondition != null && postCondition.eIsProxy()) {
			InternalEObject oldPostCondition = (InternalEObject)postCondition;
			postCondition = (PostCondition)eResolveProxy(oldPostCondition);
			if (postCondition != oldPostCondition) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, AlicaPackage.BEHAVIOUR__POST_CONDITION, oldPostCondition, postCondition));
			}
		}
		return postCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PostCondition basicGetPostCondition() {
		return postCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPostCondition(PostCondition newPostCondition) {
		PostCondition oldPostCondition = postCondition;
		postCondition = newPostCondition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AlicaPackage.BEHAVIOUR__POST_CONDITION, oldPostCondition, postCondition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getFrequency() {
		return frequency;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFrequency(int newFrequency) {
		int oldFrequency = frequency;
		frequency = newFrequency;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AlicaPackage.BEHAVIOUR__FREQUENCY, oldFrequency, frequency));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AlicaPackage.BEHAVIOUR__PRE_CONDITION:
				if (resolve) return getPreCondition();
				return basicGetPreCondition();
			case AlicaPackage.BEHAVIOUR__RUNTIME_CONDITION:
				if (resolve) return getRuntimeCondition();
				return basicGetRuntimeCondition();
			case AlicaPackage.BEHAVIOUR__POST_CONDITION:
				if (resolve) return getPostCondition();
				return basicGetPostCondition();
			case AlicaPackage.BEHAVIOUR__FREQUENCY:
				return getFrequency();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case AlicaPackage.BEHAVIOUR__PRE_CONDITION:
				setPreCondition((PreCondition)newValue);
				return;
			case AlicaPackage.BEHAVIOUR__RUNTIME_CONDITION:
				setRuntimeCondition((RuntimeCondition)newValue);
				return;
			case AlicaPackage.BEHAVIOUR__POST_CONDITION:
				setPostCondition((PostCondition)newValue);
				return;
			case AlicaPackage.BEHAVIOUR__FREQUENCY:
				setFrequency((Integer)newValue);
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
			case AlicaPackage.BEHAVIOUR__PRE_CONDITION:
				setPreCondition((PreCondition)null);
				return;
			case AlicaPackage.BEHAVIOUR__RUNTIME_CONDITION:
				setRuntimeCondition((RuntimeCondition)null);
				return;
			case AlicaPackage.BEHAVIOUR__POST_CONDITION:
				setPostCondition((PostCondition)null);
				return;
			case AlicaPackage.BEHAVIOUR__FREQUENCY:
				setFrequency(FREQUENCY_EDEFAULT);
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
			case AlicaPackage.BEHAVIOUR__PRE_CONDITION:
				return preCondition != null;
			case AlicaPackage.BEHAVIOUR__RUNTIME_CONDITION:
				return runtimeCondition != null;
			case AlicaPackage.BEHAVIOUR__POST_CONDITION:
				return postCondition != null;
			case AlicaPackage.BEHAVIOUR__FREQUENCY:
				return frequency != FREQUENCY_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (frequency: ");
		result.append(frequency);
		result.append(')');
		return result.toString();
	}

} //BehaviourImpl
