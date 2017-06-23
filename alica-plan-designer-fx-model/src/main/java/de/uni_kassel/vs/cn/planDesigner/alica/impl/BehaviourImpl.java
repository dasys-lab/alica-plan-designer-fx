/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.uni_kassel.vs.cn.planDesigner.alica.impl;

import de.uni_kassel.vs.cn.planDesigner.alica.AlicaPackage;
import de.uni_kassel.vs.cn.planDesigner.alica.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alica.PostCondition;
import de.uni_kassel.vs.cn.planDesigner.alica.PreCondition;
import de.uni_kassel.vs.cn.planDesigner.alica.RuntimeCondition;
import de.uni_kassel.vs.cn.planDesigner.alica.Variable;
import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Behaviour</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link alica.impl.BehaviourImpl#getPreCondition <em>Pre Condition</em>}</li>
 *   <li>{@link alica.impl.BehaviourImpl#getRuntimeCondition <em>Runtime Condition</em>}</li>
 *   <li>{@link alica.impl.BehaviourImpl#getPostCondition <em>Post Condition</em>}</li>
 *   <li>{@link alica.impl.BehaviourImpl#getFrequency <em>Frequency</em>}</li>
 *   <li>{@link alica.impl.BehaviourImpl#getVars <em>Vars</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BehaviourImpl extends AbstractPlanImpl implements Behaviour {
	/**
	 * The cached value of the '{@link #getPreCondition() <em>Pre Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreCondition()
	 * @generated
	 * @ordered
	 */
	protected PreCondition preCondition;

	/**
	 * The cached value of the '{@link #getRuntimeCondition() <em>Runtime Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRuntimeCondition()
	 * @generated
	 * @ordered
	 */
	protected RuntimeCondition runtimeCondition;

	/**
	 * The cached value of the '{@link #getPostCondition() <em>Post Condition</em>}' containment reference.
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
	 * The cached value of the '{@link #getVars() <em>Vars</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVars()
	 * @generated
	 * @ordered
	 */
	protected EList<Variable> vars;

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
		return preCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPreCondition(PreCondition newPreCondition, NotificationChain msgs) {
		PreCondition oldPreCondition = preCondition;
		preCondition = newPreCondition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AlicaPackage.BEHAVIOUR__PRE_CONDITION, oldPreCondition, newPreCondition);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPreCondition(PreCondition newPreCondition) {
		if (newPreCondition != preCondition) {
			NotificationChain msgs = null;
			if (preCondition != null)
				msgs = ((InternalEObject)preCondition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AlicaPackage.BEHAVIOUR__PRE_CONDITION, null, msgs);
			if (newPreCondition != null)
				msgs = ((InternalEObject)newPreCondition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AlicaPackage.BEHAVIOUR__PRE_CONDITION, null, msgs);
			msgs = basicSetPreCondition(newPreCondition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AlicaPackage.BEHAVIOUR__PRE_CONDITION, newPreCondition, newPreCondition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuntimeCondition getRuntimeCondition() {
		return runtimeCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRuntimeCondition(RuntimeCondition newRuntimeCondition, NotificationChain msgs) {
		RuntimeCondition oldRuntimeCondition = runtimeCondition;
		runtimeCondition = newRuntimeCondition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AlicaPackage.BEHAVIOUR__RUNTIME_CONDITION, oldRuntimeCondition, newRuntimeCondition);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRuntimeCondition(RuntimeCondition newRuntimeCondition) {
		if (newRuntimeCondition != runtimeCondition) {
			NotificationChain msgs = null;
			if (runtimeCondition != null)
				msgs = ((InternalEObject)runtimeCondition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AlicaPackage.BEHAVIOUR__RUNTIME_CONDITION, null, msgs);
			if (newRuntimeCondition != null)
				msgs = ((InternalEObject)newRuntimeCondition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AlicaPackage.BEHAVIOUR__RUNTIME_CONDITION, null, msgs);
			msgs = basicSetRuntimeCondition(newRuntimeCondition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AlicaPackage.BEHAVIOUR__RUNTIME_CONDITION, newRuntimeCondition, newRuntimeCondition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PostCondition getPostCondition() {
		return postCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPostCondition(PostCondition newPostCondition, NotificationChain msgs) {
		PostCondition oldPostCondition = postCondition;
		postCondition = newPostCondition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AlicaPackage.BEHAVIOUR__POST_CONDITION, oldPostCondition, newPostCondition);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPostCondition(PostCondition newPostCondition) {
		if (newPostCondition != postCondition) {
			NotificationChain msgs = null;
			if (postCondition != null)
				msgs = ((InternalEObject)postCondition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AlicaPackage.BEHAVIOUR__POST_CONDITION, null, msgs);
			if (newPostCondition != null)
				msgs = ((InternalEObject)newPostCondition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AlicaPackage.BEHAVIOUR__POST_CONDITION, null, msgs);
			msgs = basicSetPostCondition(newPostCondition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AlicaPackage.BEHAVIOUR__POST_CONDITION, newPostCondition, newPostCondition));
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
	public EList<Variable> getVars() {
		if (vars == null) {
			vars = new EObjectContainmentEList<Variable>(Variable.class, this, AlicaPackage.BEHAVIOUR__VARS);
		}
		return vars;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AlicaPackage.BEHAVIOUR__PRE_CONDITION:
				return basicSetPreCondition(null, msgs);
			case AlicaPackage.BEHAVIOUR__RUNTIME_CONDITION:
				return basicSetRuntimeCondition(null, msgs);
			case AlicaPackage.BEHAVIOUR__POST_CONDITION:
				return basicSetPostCondition(null, msgs);
			case AlicaPackage.BEHAVIOUR__VARS:
				return ((InternalEList<?>)getVars()).basicRemove(otherEnd, msgs);
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
			case AlicaPackage.BEHAVIOUR__PRE_CONDITION:
				return getPreCondition();
			case AlicaPackage.BEHAVIOUR__RUNTIME_CONDITION:
				return getRuntimeCondition();
			case AlicaPackage.BEHAVIOUR__POST_CONDITION:
				return getPostCondition();
			case AlicaPackage.BEHAVIOUR__FREQUENCY:
				return getFrequency();
			case AlicaPackage.BEHAVIOUR__VARS:
				return getVars();
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
			case AlicaPackage.BEHAVIOUR__VARS:
				getVars().clear();
				getVars().addAll((Collection<? extends Variable>)newValue);
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
			case AlicaPackage.BEHAVIOUR__VARS:
				getVars().clear();
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
			case AlicaPackage.BEHAVIOUR__VARS:
				return vars != null && !vars.isEmpty();
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
