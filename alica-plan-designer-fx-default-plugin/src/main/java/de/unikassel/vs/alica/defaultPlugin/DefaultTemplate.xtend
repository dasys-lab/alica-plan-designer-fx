package de.unikassel.vs.alica.defaultPlugin;

import de.unikassel.vs.alica.planDesigner.alicamodel.Plan
import de.unikassel.vs.alica.planDesigner.alicamodel.State
import java.util.Map
import java.util.List
import de.unikassel.vs.alica.planDesigner.alicamodel.EntryPoint
import de.unikassel.vs.alica.planDesigner.alicamodel.AbstractPlan

class DefaultTemplate {

    private Map<String, String> protectedRegions;

    public def void setProtectedRegions (Map<String, String> regions) {
        protectedRegions = regions;
    }

    def String expressionsStateCheckingMethods(State state) '''
«FOR transition : state.outTransitions»
«IF (transition.preCondition !== null && transition.preCondition.pluginName == "DefaultPlugin")»
			/*
			*
			* Transition:
			*   - Name: «transition.preCondition.name», ConditionString: «transition.preCondition.conditionString», Comment : «transition.comment»
			*
			* Plans in State: «var  List<AbstractPlan> plans = state.plans»
			*     «FOR plan : plans»
			*   - Plan - (Name): «plan.name», (PlanID): «plan.id» «ENDFOR»
			*
			* Tasks: «var  List<EntryPoint> entryPoints = state.parentPlan.entryPoints»
			*     «FOR planEntryPoint : entryPoints»
			*   - «planEntryPoint.task.name» («planEntryPoint.task.id») (Entrypoint: «planEntryPoint.id»)«ENDFOR»
			*
			* States: «var  List<State> states = state.parentPlan.states»
			*     «FOR stateOfInPlan : states»
			*   - «stateOfInPlan.name» («stateOfInPlan.id»)«ENDFOR»
			*
			* Vars:«FOR variable : transition.preCondition.variables»
			*	- «variable.name» («variable.id») «ENDFOR»
			*/
			bool PreCondition«transition.preCondition.id»::evaluate(shared_ptr<RunningPlan> rp)
			 {
				/*PROTECTED REGION ID(«transition.id») ENABLED START*/
                «IF (protectedRegions.containsKey(transition.id + ""))»
                    «protectedRegions.get(transition.id + "")»
                «ELSE»
                    return implement_me_«transition.id»;
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
          * Available Vars:«FOR variable : plan.runtimeCondition.variables»
          *	- «variable.name» («variable.id»)«ENDFOR»
          */
         bool RunTimeCondition«plan.runtimeCondition.id»::evaluate(shared_ptr<RunningPlan> rp)
         {
             /*PROTECTED REGION ID(«plan.runtimeCondition.id») ENABLED START*/
            «IF (protectedRegions.containsKey(plan.runtimeCondition.id + ""))»
                «protectedRegions.get(plan.runtimeCondition.id + "")»
            «ELSE»
                return please_implement_me_«plan.runtimeCondition.id»;
            «ENDIF»
             /*PROTECTED REGION END*/
          }
«ENDIF»
«IF (plan.preCondition !== null && plan.preCondition.pluginName == "DefaultPlugin")»
          //Check of PreCondition - (Name): «plan.preCondition.name», (ConditionString): «plan.preCondition.conditionString» , (Comment) : «plan.preCondition.comment»

          /*
          * Available Vars:«FOR variable : plan.preCondition.variables»
          *	- «variable.name» («variable.id»)«ENDFOR»
          */
         bool PreCondition«plan.preCondition.id»::evaluate(shared_ptr<RunningPlan> rp)
         {
             /*PROTECTED REGION ID(«plan.preCondition.id») ENABLED START*/
             «IF (protectedRegions.containsKey(plan.preCondition.id + ""))»
                «protectedRegions.get(plan.preCondition.id + "")»
            «ELSE»
                return please_implement_me_«plan.preCondition.id»;
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
«IF (plan.runtimeCondition.variables !== null)»
* Static Variables: «FOR variable : plan.runtimeCondition.variables»«variable.name» «ENDFOR»
«ENDIF»
* Domain Variables:
«IF (plan.runtimeCondition.quantifiers !== null)»
«FOR q : plan.runtimeCondition.quantifiers»
    * forall agents in «q.scope.name» let v = «q.sorts»
«ENDFOR»
«ENDIF»
*
*/
void Constraint«plan.runtimeCondition.id»::getConstraint(shared_ptr<ProblemDescriptor> c, shared_ptr<RunningPlan> rp) {
/*PROTECTED REGION ID(cc«plan.runtimeCondition.id») ENABLED START*/
    «IF (protectedRegions.containsKey("cc" + plan.runtimeCondition.id))»
        «protectedRegions.get("cc" + plan.runtimeCondition.id)»
    «ELSE»
        //Please describe your runtime constraint here
    «ENDIF»
/*PROTECTED REGION END*/
}
«ENDIF»

«IF (plan.preCondition !== null && plan.preCondition.pluginName == "DefaultPlugin" && ((plan.preCondition.variables.size > 0) || (plan.preCondition.quantifiers.size > 0)))»
/*
* PreCondition - (Name): «plan.preCondition.name»
* (ConditionString): «plan.preCondition.conditionString»
* Static Variables: «FOR variable : plan.preCondition.variables»«variable.name» «ENDFOR»
* Domain Variables:
«FOR q : plan.preCondition.quantifiers»
    * forall agents in «q.scope.name» let v = «q.sorts»
«ENDFOR»
*
*/
void Constraint«plan.preCondition.id»::getConstraint(shared_ptr<ProblemDescriptor> c, shared_ptr<RunningPlan> rp) {
/*PROTECTED REGION ID(cc«plan.preCondition.id») ENABLED START*/
    «IF (protectedRegions.containsKey("cc" + plan.preCondition.id))»
        «protectedRegions.get("cc" + plan.preCondition.id)»
    «ELSE»
        //Please describe your precondition constraint here
    «ENDIF»
/*PROTECTED REGION END*/
}
«ENDIF»

'''

    def String constraintStateCheckingMethods(State state) '''
// State: «state.name»
«FOR transition : state.outTransitions»
«IF (transition.preCondition != null && transition.preCondition.pluginName == "DefaultPlugin" && transition.preCondition.variables.size > 0)»
/*
* Transition:
* - Name: «transition.preCondition.name»
* - ConditionString: «transition.preCondition.conditionString»
*
* «var  List<AbstractPlan> plans = state.plans»
* Plans in State: «FOR plan : plans»
* - Plan - (Name): «plan.name», (PlanID): «plan.id» «ENDFOR»
* Static Variables: «FOR variable : transition.preCondition.variables»«variable.name» «ENDFOR»
* Domain Variables:
«IF transition.preCondition.quantifiers !== null && transition.preCondition.quantifiers.size > 0»
«FOR q : transition.preCondition.quantifiers»
* forall agents in «q.scope.name» let v = «q.sorts»
«ENDFOR»
«ENDIF»
*/
void Constraint«transition.preCondition.id»::getConstraint(shared_ptr<ProblemDescriptor> c, shared_ptr<RunningPlan> rp) {
/*PROTECTED REGION ID(cc«transition.preCondition.id») ENABLED START*/
	«IF (protectedRegions.containsKey("cc" + transition.preCondition.id))»
        «protectedRegions.get("cc" + transition.preCondition.id)»
    «ELSE»
        //Please describe your precondition constraint here
    «ENDIF»
/*PROTECTED REGION END*/
}
«ENDIF»
«ENDFOR»
'''
}