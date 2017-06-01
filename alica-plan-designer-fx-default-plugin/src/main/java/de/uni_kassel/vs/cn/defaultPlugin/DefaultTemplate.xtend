package de.uni_kassel.vs.cn.defaultPlugin;

import de.uni_kassel.vs.cn.planDesigner.alica.State
import de.uni_kassel.vs.cn.planDesigner.alica.Plan
import java.util.Map

/**
 * Created by marci on 19.05.17.
 */
class DefaultTemplate {

    private Map<String, String> protectedRegions;

    public def void setProtectedRegions (Map<String, String> regions) {
        protectedRegions = regions;
    }

    def String expressionsStateCheckingMethods(State state) '''
«FOR transition : state.outTransitions»
«IF (transition.preCondition.pluginName == "DefaultPlugin")»
			/*
			*
			* Transition:
			*   - Name: «transition.preCondition.name», ConditionString: «transition.preCondition.conditionString», Comment : «transition.comment»
			*
			* Plans in State: «FOR plan : state.plans»
			*   - Plan - (Name): «plan.name», (PlanID): «plan.id» «ENDFOR»
			*
			* Tasks: «FOR planEntryPoint : state.inPlan.entryPoints»
			*   - «planEntryPoint.task.name» («planEntryPoint.task.id») (Entrypoint: «planEntryPoint.id»)«ENDFOR»
			*
			* States:«FOR stateOfInPlan : state.inPlan.states»
			*   - «stateOfInPlan.name» («stateOfInPlan.id»)«ENDFOR»
			*
			* Vars:«FOR variable : transition.preCondition.vars»
			*	- «variable.name» («variable.id») «ENDFOR»
			*/
			bool TransitionCondition«transition.preCondition.id»::evaluate(shared_ptr<RunningPlan> rp)
			 {
				/*PROTECTED REGION ID(«transition.id») ENABLED START*/
                «IF (protectedRegions.containsKey(transition.id + ""))»
                    «protectedRegions.get(transition.id + "")»
                «ELSE»
                    return false;
                «ENDIF»
				/*PROTECTED REGION END*/

			}
«ENDIF»
«ENDFOR»
'''

    def String expressionsPlanCheckingMethods(Plan plan) '''
«IF (plan.runtimeCondition !== null && plan.runtimeCondition.pluginName == "DefaultPlugin")»
          //Check of RuntimeCondition - (Name): «plan.runtimeCondition.name», (ConditionString): «plan.runtimeCondition.conditionString», (Comment) : «plan.runtimeCondition.comment»

          /*
          * Available Vars:«FOR variable : plan.runtimeCondition.vars»
          *	- «variable.name» («variable.id»)«ENDFOR»
          */
         bool RunTimeCondition«plan.runtimeCondition.id»::evaluate(shared_ptr<RunningPlan> rp)
         {
             /*PROTECTED REGION ID(«plan.runtimeCondition.id») ENABLED START*/
            «IF (protectedRegions.containsKey(plan.runtimeCondition.id + ""))»
                «protectedRegions.get(plan.runtimeCondition.id + "")»
            «ELSE»
                return true;
            «ENDIF»
             /*PROTECTED REGION END*/
          }
«ENDIF»
«IF (plan.preCondition !== null && plan.preCondition.pluginName == "DefaultPlugin")»
          //Check of PreCondition - (Name): «plan.preCondition.name», (ConditionString): «plan.preCondition.conditionString» , (Comment) : «plan.preCondition.comment»

          /*
          * Available Vars:«FOR variable : plan.preCondition.vars»
          *	- «variable.name» («variable.id»)«ENDFOR»
          */
         bool PreCondition«plan.preCondition.id»::evaluate(shared_ptr<RunningPlan> rp)
         {
             /*PROTECTED REGION ID(«plan.preCondition.id») ENABLED START*/
             «IF (protectedRegions.containsKey(plan.preCondition.id + ""))»
                «protectedRegions.get(plan.preCondition.id + "")»
            «ELSE»
                //--> "PreCondition:«plan.preCondition.id»  not implemented";
                return true;
            «ENDIF»
             /*PROTECTED REGION END*/
          }
«ENDIF»
'''

    def String constraintPlanCheckingMethods(Plan plan) '''
«IF (plan.runtimeCondition !== null && plan.runtimeCondition.pluginName == "DefaultPlugin")»
/*
* RuntimeCondition - (Name): «plan.runtimeCondition.name»
* (ConditionString): «plan.runtimeCondition.conditionString»
«IF (plan.runtimeCondition.vars !== null)»
* Static Variables: «FOR variable : plan.runtimeCondition.vars»«variable.name» «ENDFOR»
«ENDIF»
* Domain Variables:
«IF (plan.runtimeCondition.quantifiers !== null)»
«FOR q : plan.runtimeCondition.quantifiers»
    * forall agents in «q.scope.name» let v = «q.sorts»
«ENDFOR»
«ENDIF»
*
*/
void Constraint«plan.runtimeCondition.id»::getConstraint(shared_ptr<ConstraintDescriptor> c, shared_ptr<RunningPlan> rp) {
/*PROTECTED REGION ID(cc«plan.runtimeCondition.id») ENABLED START*/
    «IF (protectedRegions.containsKey("cc" + plan.runtimeCondition.id))»
        «protectedRegions.get("cc" + plan.runtimeCondition.id)»
    «ELSE»
        //Proteced
    «ENDIF»
/*PROTECTED REGION END*/
}
«ENDIF»

«IF (plan.preCondition !== null && plan.preCondition.pluginName == "DefaultPlugin" && ((plan.preCondition.vars.size > 0) || (plan.preCondition.quantifiers.size > 0)))»
/*
* PreCondition - (Name): «plan.preCondition.name»
* (ConditionString): «plan.preCondition.conditionString»
* Static Variables: «FOR variable : plan.preCondition.vars»«variable.name» «ENDFOR»
* Domain Variables:
«FOR q : plan.preCondition.quantifiers»
    * forall agents in «q.scope.name» let v = «q.sorts»
«ENDFOR»
*
*/
void Constraint«plan.preCondition.id»::getConstraint(shared_ptr<ConstraintDescriptor> c, shared_ptr<RunningPlan> rp) {
/*PROTECTED REGION ID(cc«plan.preCondition.id») ENABLED START*/
    «IF (protectedRegions.containsKey("cc" + plan.preCondition.id))»
        «protectedRegions.get("cc" + plan.preCondition.id)»
    «ELSE»
        //Proteced
    «ENDIF»
/*PROTECTED REGION END*/
}
«ENDIF»

'''

    def String constraintStateCheckingMethods(State state) '''
// State: «state.name»
«FOR transition : state.outTransitions»
«IF (transition.preCondition.pluginName == "DefaultPlugin" && transition.preCondition.vars.size > 0)»
/*
* Transition:
* - Name: «transition.preCondition.name»
* - ConditionString: «transition.preCondition.conditionString»
*
* Plans in State: «FOR plan : state.plans»
* - Plan - (Name): «plan.name», (PlanID): «plan.id» «ENDFOR»
* Static Variables: «FOR variable : transition.preCondition.vars»«variable.name» «ENDFOR»
* Domain Variables:
«IF transition.preCondition.quantifiers !== null && transition.preCondition.quantifiers.size > 0»
«FOR q : transition.preCondition.quantifiers»
* forall agents in «q.scope.name» let v = «q.sorts»
«ENDFOR»
«ENDIF»
*/
void Constraint«transition.preCondition.id»::getConstraint(shared_ptr<ConstraintDescriptor> c, shared_ptr<RunningPlan> rp) {
/*PROTECTED REGION ID(cc«transition.preCondition.id») ENABLED START*/
	«IF (protectedRegions.containsKey("cc" + transition.preCondition.id))»
        «protectedRegions.get("cc" + transition.preCondition.id)»
    «ELSE»
        //Proteced
    «ENDIF»
/*PROTECTED REGION END*/
}
«ENDIF»
«ENDFOR»
'''
}