/**
 */
package de.uni_kassel.vs.cn.planDesigner.alica.impl;

import de.uni_kassel.vs.cn.planDesigner.alica.AlicaPackage;
import de.uni_kassel.vs.cn.planDesigner.alica.EntryPoint;
import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.PreCondition;
import de.uni_kassel.vs.cn.planDesigner.alica.RuntimeCondition;
import de.uni_kassel.vs.cn.planDesigner.alica.State;
import de.uni_kassel.vs.cn.planDesigner.alica.Synchronisation;
import de.uni_kassel.vs.cn.planDesigner.alica.Transition;
import de.uni_kassel.vs.cn.planDesigner.alica.Variable;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the alica object '<em><b>Plan</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alica.impl.PlanImpl#getPriority <em>Priority</em>}</li>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alica.impl.PlanImpl#getStates <em>States</em>}</li>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alica.impl.PlanImpl#getTransitions <em>Transitions</em>}</li>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alica.impl.PlanImpl#getMinCardinality <em>Min Cardinality</em>}</li>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alica.impl.PlanImpl#getMaxCardinality <em>Max Cardinality</em>}</li>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alica.impl.PlanImpl#getSynchronisations <em>Synchronisations</em>}</li>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alica.impl.PlanImpl#getEntryPoints <em>Entry Points</em>}</li>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alica.impl.PlanImpl#isMasterPlan <em>Master Plan</em>}</li>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alica.impl.PlanImpl#getUtilityFunction <em>Utility Function</em>}</li>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alica.impl.PlanImpl#getUtilityThreshold <em>Utility Threshold</em>}</li>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alica.impl.PlanImpl#getVars <em>Vars</em>}</li>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alica.impl.PlanImpl#getPreCondition <em>Pre Condition</em>}</li>
 *   <li>{@link de.uni_kassel.vs.cn.planDesigner.alica.impl.PlanImpl#getRuntimeCondition <em>Runtime Condition</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PlanImpl extends AbstractPlanImpl implements Plan {
	/**
	 * The default value of the '{@link #getPriority() <em>Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPriority()
	 * @generated
	 * @ordered
	 */
	protected static final double PRIORITY_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getPriority() <em>Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPriority()
	 * @generated
	 * @ordered
	 */
	protected double priority = PRIORITY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getStates() <em>States</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStates()
	 * @generated
	 * @ordered
	 */
	protected EList<State> states;

	/**
	 * The cached value of the '{@link #getTransitions() <em>Transitions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransitions()
	 * @generated
	 * @ordered
	 */
	protected EList<Transition> transitions;

	/**
	 * The default value of the '{@link #getMinCardinality() <em>Min Cardinality</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinCardinality()
	 * @generated
	 * @ordered
	 */
	protected static final int MIN_CARDINALITY_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getMinCardinality() <em>Min Cardinality</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinCardinality()
	 * @generated
	 * @ordered
	 */
	protected int minCardinality = MIN_CARDINALITY_EDEFAULT;

	/**
	 * The default value of the '{@link #getMaxCardinality() <em>Max Cardinality</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxCardinality()
	 * @generated
	 * @ordered
	 */
	protected static final int MAX_CARDINALITY_EDEFAULT = 2147483647;

	/**
	 * The cached value of the '{@link #getMaxCardinality() <em>Max Cardinality</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxCardinality()
	 * @generated
	 * @ordered
	 */
	protected int maxCardinality = MAX_CARDINALITY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSynchronisations() <em>Synchronisations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSynchronisations()
	 * @generated
	 * @ordered
	 */
	protected EList<Synchronisation> synchronisations;

	/**
	 * The cached value of the '{@link #getEntryPoints() <em>Entry Points</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntryPoints()
	 * @generated
	 * @ordered
	 */
	protected EList<EntryPoint> entryPoints;

	/**
	 * The default value of the '{@link #isMasterPlan() <em>Master Plan</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMasterPlan()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MASTER_PLAN_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMasterPlan() <em>Master Plan</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMasterPlan()
	 * @generated
	 * @ordered
	 */
	protected boolean masterPlan = MASTER_PLAN_EDEFAULT;

	/**
	 * The default value of the '{@link #getUtilityFunction() <em>Utility Function</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUtilityFunction()
	 * @generated
	 * @ordered
	 */
	protected static final String UTILITY_FUNCTION_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getUtilityFunction() <em>Utility Function</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUtilityFunction()
	 * @generated
	 * @ordered
	 */
	protected String utilityFunction = UTILITY_FUNCTION_EDEFAULT;

	/**
	 * The default value of the '{@link #getUtilityThreshold() <em>Utility Threshold</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUtilityThreshold()
	 * @generated
	 * @ordered
	 */
	protected static final double UTILITY_THRESHOLD_EDEFAULT = 0.1;

	/**
	 * The cached value of the '{@link #getUtilityThreshold() <em>Utility Threshold</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUtilityThreshold()
	 * @generated
	 * @ordered
	 */
	protected double utilityThreshold = UTILITY_THRESHOLD_EDEFAULT;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PlanImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AlicaPackage.Literals.PLAN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getPriority() {
		return priority;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPriority(double newPriority) {
		double oldPriority = priority;
		priority = newPriority;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AlicaPackage.PLAN__PRIORITY, oldPriority, priority));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<State> getStates() {
		if (states == null) {
			states = new EObjectContainmentWithInverseEList<State>(State.class, this, AlicaPackage.PLAN__STATES, AlicaPackage.STATE__IN_PLAN);
		}
		return states;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Transition> getTransitions() {
		if (transitions == null) {
			transitions = new EObjectContainmentEList<Transition>(Transition.class, this, AlicaPackage.PLAN__TRANSITIONS);
		}
		return transitions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getMinCardinality() {
		return minCardinality;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMinCardinality(int newMinCardinality) {
		int oldMinCardinality = minCardinality;
		minCardinality = newMinCardinality;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AlicaPackage.PLAN__MIN_CARDINALITY, oldMinCardinality, minCardinality));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getMaxCardinality() {
		return maxCardinality;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxCardinality(int newMaxCardinality) {
		int oldMaxCardinality = maxCardinality;
		maxCardinality = newMaxCardinality;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AlicaPackage.PLAN__MAX_CARDINALITY, oldMaxCardinality, maxCardinality));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Synchronisation> getSynchronisations() {
		if (synchronisations == null) {
			synchronisations = new EObjectContainmentEList<Synchronisation>(Synchronisation.class, this, AlicaPackage.PLAN__SYNCHRONISATIONS);
		}
		return synchronisations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EntryPoint> getEntryPoints() {
		if (entryPoints == null) {
			entryPoints = new EObjectContainmentWithInverseEList<EntryPoint>(EntryPoint.class, this, AlicaPackage.PLAN__ENTRY_POINTS, AlicaPackage.ENTRY_POINT__PLAN);
		}
		return entryPoints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isMasterPlan() {
		return masterPlan;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMasterPlan(boolean newMasterPlan) {
		boolean oldMasterPlan = masterPlan;
		masterPlan = newMasterPlan;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AlicaPackage.PLAN__MASTER_PLAN, oldMasterPlan, masterPlan));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUtilityFunction() {
		return utilityFunction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUtilityFunction(String newUtilityFunction) {
		String oldUtilityFunction = utilityFunction;
		utilityFunction = newUtilityFunction;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AlicaPackage.PLAN__UTILITY_FUNCTION, oldUtilityFunction, utilityFunction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getUtilityThreshold() {
		return utilityThreshold;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUtilityThreshold(double newUtilityThreshold) {
		double oldUtilityThreshold = utilityThreshold;
		utilityThreshold = newUtilityThreshold;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AlicaPackage.PLAN__UTILITY_THRESHOLD, oldUtilityThreshold, utilityThreshold));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Variable> getVars() {
		if (vars == null) {
			vars = new EObjectContainmentEList<Variable>(Variable.class, this, AlicaPackage.PLAN__VARS);
		}
		return vars;
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AlicaPackage.PLAN__PRE_CONDITION, oldPreCondition, newPreCondition);
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
				msgs = ((InternalEObject)preCondition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AlicaPackage.PLAN__PRE_CONDITION, null, msgs);
			if (newPreCondition != null)
				msgs = ((InternalEObject)newPreCondition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AlicaPackage.PLAN__PRE_CONDITION, null, msgs);
			msgs = basicSetPreCondition(newPreCondition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AlicaPackage.PLAN__PRE_CONDITION, newPreCondition, newPreCondition));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AlicaPackage.PLAN__RUNTIME_CONDITION, oldRuntimeCondition, newRuntimeCondition);
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
				msgs = ((InternalEObject)runtimeCondition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AlicaPackage.PLAN__RUNTIME_CONDITION, null, msgs);
			if (newRuntimeCondition != null)
				msgs = ((InternalEObject)newRuntimeCondition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AlicaPackage.PLAN__RUNTIME_CONDITION, null, msgs);
			msgs = basicSetRuntimeCondition(newRuntimeCondition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AlicaPackage.PLAN__RUNTIME_CONDITION, newRuntimeCondition, newRuntimeCondition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void calculateCardinalities() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
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
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AlicaPackage.PLAN__STATES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getStates()).basicAdd(otherEnd, msgs);
			case AlicaPackage.PLAN__ENTRY_POINTS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getEntryPoints()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AlicaPackage.PLAN__STATES:
				return ((InternalEList<?>)getStates()).basicRemove(otherEnd, msgs);
			case AlicaPackage.PLAN__TRANSITIONS:
				return ((InternalEList<?>)getTransitions()).basicRemove(otherEnd, msgs);
			case AlicaPackage.PLAN__SYNCHRONISATIONS:
				return ((InternalEList<?>)getSynchronisations()).basicRemove(otherEnd, msgs);
			case AlicaPackage.PLAN__ENTRY_POINTS:
				return ((InternalEList<?>)getEntryPoints()).basicRemove(otherEnd, msgs);
			case AlicaPackage.PLAN__VARS:
				return ((InternalEList<?>)getVars()).basicRemove(otherEnd, msgs);
			case AlicaPackage.PLAN__PRE_CONDITION:
				return basicSetPreCondition(null, msgs);
			case AlicaPackage.PLAN__RUNTIME_CONDITION:
				return basicSetRuntimeCondition(null, msgs);
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
			case AlicaPackage.PLAN__PRIORITY:
				return getPriority();
			case AlicaPackage.PLAN__STATES:
				return getStates();
			case AlicaPackage.PLAN__TRANSITIONS:
				return getTransitions();
			case AlicaPackage.PLAN__MIN_CARDINALITY:
				return getMinCardinality();
			case AlicaPackage.PLAN__MAX_CARDINALITY:
				return getMaxCardinality();
			case AlicaPackage.PLAN__SYNCHRONISATIONS:
				return getSynchronisations();
			case AlicaPackage.PLAN__ENTRY_POINTS:
				return getEntryPoints();
			case AlicaPackage.PLAN__MASTER_PLAN:
				return isMasterPlan();
			case AlicaPackage.PLAN__UTILITY_FUNCTION:
				return getUtilityFunction();
			case AlicaPackage.PLAN__UTILITY_THRESHOLD:
				return getUtilityThreshold();
			case AlicaPackage.PLAN__VARS:
				return getVars();
			case AlicaPackage.PLAN__PRE_CONDITION:
				return getPreCondition();
			case AlicaPackage.PLAN__RUNTIME_CONDITION:
				return getRuntimeCondition();
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
			case AlicaPackage.PLAN__PRIORITY:
				setPriority((Double)newValue);
				return;
			case AlicaPackage.PLAN__STATES:
				getStates().clear();
				getStates().addAll((Collection<? extends State>)newValue);
				return;
			case AlicaPackage.PLAN__TRANSITIONS:
				getTransitions().clear();
				getTransitions().addAll((Collection<? extends Transition>)newValue);
				return;
			case AlicaPackage.PLAN__MIN_CARDINALITY:
				setMinCardinality((Integer)newValue);
				return;
			case AlicaPackage.PLAN__MAX_CARDINALITY:
				setMaxCardinality((Integer)newValue);
				return;
			case AlicaPackage.PLAN__SYNCHRONISATIONS:
				getSynchronisations().clear();
				getSynchronisations().addAll((Collection<? extends Synchronisation>)newValue);
				return;
			case AlicaPackage.PLAN__ENTRY_POINTS:
				getEntryPoints().clear();
				getEntryPoints().addAll((Collection<? extends EntryPoint>)newValue);
				return;
			case AlicaPackage.PLAN__MASTER_PLAN:
				setMasterPlan((Boolean)newValue);
				return;
			case AlicaPackage.PLAN__UTILITY_FUNCTION:
				setUtilityFunction((String)newValue);
				return;
			case AlicaPackage.PLAN__UTILITY_THRESHOLD:
				setUtilityThreshold((Double)newValue);
				return;
			case AlicaPackage.PLAN__VARS:
				getVars().clear();
				getVars().addAll((Collection<? extends Variable>)newValue);
				return;
			case AlicaPackage.PLAN__PRE_CONDITION:
				setPreCondition((PreCondition)newValue);
				return;
			case AlicaPackage.PLAN__RUNTIME_CONDITION:
				setRuntimeCondition((RuntimeCondition)newValue);
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
			case AlicaPackage.PLAN__PRIORITY:
				setPriority(PRIORITY_EDEFAULT);
				return;
			case AlicaPackage.PLAN__STATES:
				getStates().clear();
				return;
			case AlicaPackage.PLAN__TRANSITIONS:
				getTransitions().clear();
				return;
			case AlicaPackage.PLAN__MIN_CARDINALITY:
				setMinCardinality(MIN_CARDINALITY_EDEFAULT);
				return;
			case AlicaPackage.PLAN__MAX_CARDINALITY:
				setMaxCardinality(MAX_CARDINALITY_EDEFAULT);
				return;
			case AlicaPackage.PLAN__SYNCHRONISATIONS:
				getSynchronisations().clear();
				return;
			case AlicaPackage.PLAN__ENTRY_POINTS:
				getEntryPoints().clear();
				return;
			case AlicaPackage.PLAN__MASTER_PLAN:
				setMasterPlan(MASTER_PLAN_EDEFAULT);
				return;
			case AlicaPackage.PLAN__UTILITY_FUNCTION:
				setUtilityFunction(UTILITY_FUNCTION_EDEFAULT);
				return;
			case AlicaPackage.PLAN__UTILITY_THRESHOLD:
				setUtilityThreshold(UTILITY_THRESHOLD_EDEFAULT);
				return;
			case AlicaPackage.PLAN__VARS:
				getVars().clear();
				return;
			case AlicaPackage.PLAN__PRE_CONDITION:
				setPreCondition((PreCondition)null);
				return;
			case AlicaPackage.PLAN__RUNTIME_CONDITION:
				setRuntimeCondition((RuntimeCondition)null);
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
			case AlicaPackage.PLAN__PRIORITY:
				return priority != PRIORITY_EDEFAULT;
			case AlicaPackage.PLAN__STATES:
				return states != null && !states.isEmpty();
			case AlicaPackage.PLAN__TRANSITIONS:
				return transitions != null && !transitions.isEmpty();
			case AlicaPackage.PLAN__MIN_CARDINALITY:
				return minCardinality != MIN_CARDINALITY_EDEFAULT;
			case AlicaPackage.PLAN__MAX_CARDINALITY:
				return maxCardinality != MAX_CARDINALITY_EDEFAULT;
			case AlicaPackage.PLAN__SYNCHRONISATIONS:
				return synchronisations != null && !synchronisations.isEmpty();
			case AlicaPackage.PLAN__ENTRY_POINTS:
				return entryPoints != null && !entryPoints.isEmpty();
			case AlicaPackage.PLAN__MASTER_PLAN:
				return masterPlan != MASTER_PLAN_EDEFAULT;
			case AlicaPackage.PLAN__UTILITY_FUNCTION:
				return UTILITY_FUNCTION_EDEFAULT == null ? utilityFunction != null : !UTILITY_FUNCTION_EDEFAULT.equals(utilityFunction);
			case AlicaPackage.PLAN__UTILITY_THRESHOLD:
				return utilityThreshold != UTILITY_THRESHOLD_EDEFAULT;
			case AlicaPackage.PLAN__VARS:
				return vars != null && !vars.isEmpty();
			case AlicaPackage.PLAN__PRE_CONDITION:
				return preCondition != null;
			case AlicaPackage.PLAN__RUNTIME_CONDITION:
				return runtimeCondition != null;
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
			case AlicaPackage.PLAN___CALCULATE_CARDINALITIES:
				calculateCardinalities();
				return null;
			case AlicaPackage.PLAN___ENSURE_PARAMETRISATION_CONSISTENCY:
				ensureParametrisationConsistency();
				return null;
		}
		return super.eInvoke(operationID, arguments);
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
		result.append(" (priority: ");
		result.append(priority);
		result.append(", minCardinality: ");
		result.append(minCardinality);
		result.append(", maxCardinality: ");
		result.append(maxCardinality);
		result.append(", masterPlan: ");
		result.append(masterPlan);
		result.append(", utilityFunction: ");
		result.append(utilityFunction);
		result.append(", utilityThreshold: ");
		result.append(utilityThreshold);
		result.append(')');
		return result.toString();
	}

} //PlanImpl
