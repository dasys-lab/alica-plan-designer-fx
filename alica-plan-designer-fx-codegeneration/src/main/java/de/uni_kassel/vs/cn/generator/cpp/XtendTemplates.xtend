package de.uni_kassel.vs.cn.generator.cpp

import de.uni_kassel.vs.cn.planDesigner.alica.Behaviour
import java.util.List
import java.util.Map
import de.uni_kassel.vs.cn.planDesigner.alica.Plan
import de.uni_kassel.vs.cn.planDesigner.alica.Condition
import de.uni_kassel.vs.cn.planDesigner.alica.PreCondition
import de.uni_kassel.vs.cn.planDesigner.alica.PostCondition
import de.uni_kassel.vs.cn.planDesigner.alica.RuntimeCondition
import de.uni_kassel.vs.cn.generator.IConstraintCodeGenerator

/**
 * Created by marci on 11.05.17.
 */
class XtendTemplates {

    private Map<String, String> protectedRegions;

    public def void setProtectedRegions (Map<String, String> regions) {
        protectedRegions = regions;
    }

    def String behaviourCreatorHeader()'''
#ifndef BEHAVIOURCREATOR_H_
#define BEHAVIOURCREATOR_H_
#include <engine/IBehaviourCreator.h>

#include <memory>
#include <iostream>

namespace alica
{

    class BasicBehaviour;

    class BehaviourCreator : public IBehaviourCreator
    {
        public:
            BehaviourCreator();
            virtual ~BehaviourCreator();
            virtual shared_ptr<BasicBehaviour> createBehaviour(long behaviourConfId);
    };

} /* namespace alica */

#endif /* BEHAVIOURCREATOR_H_ */
'''

    def String behaviourCreatorSource(List<Behaviour> behaviours)'''
using namespace std;

#include "BehaviourCreator.h"
#include "engine/BasicBehaviour.h"
«FOR beh : behaviours»
#include  "«beh.destinationPath»/«beh.name».h"
«ENDFOR»
namespace alica
{

    BehaviourCreator::BehaviourCreator()
    {
    }

    BehaviourCreator::~BehaviourCreator()
    {
    }

    shared_ptr<BasicBehaviour> BehaviourCreator::createBehaviour(long behaviourConfId)
    {
        switch(behaviourConfId)
        {
            «FOR beh : behaviours»
                case «beh.id»
                // TODO something for behaviour configurations has to be done here
                return make_shared<«beh.name»>();
                break;
            «ENDFOR»
            default:
            cerr << "BehaviourCreator: Unknown behaviour requested: " << behaviourConfId << endl;
            throw new exception();
            break;
        }
    }
}
'''

    def String behaviourHeader(Behaviour behaviour)'''
#ifndef «behaviour.name»_H_
#define «behaviour.name»_H_

#include "DomainBehaviour.h"
/*PROTECTED REGION ID(inc«behaviour.id») ENABLED START*/
«IF (protectedRegions.containsKey("inc" + behaviour.id))»
«protectedRegions.get("inc" + behaviour.id)»
«ELSE»
//Add additional includes here
«ENDIF»
/*PROTECTED REGION END*/

namespace alica
{
    class «behaviour.name» : public DomainBehaviour
    {
        public:
            «behaviour.name»();
            virtual ~«behaviour.name»();
            virtual void run(void* msg);
            /*PROTECTED REGION ID(pub«behaviour.id») ENABLED START*/
            «IF (protectedRegions.containsKey("pub" + behaviour.id))»
«protectedRegions.get("pub" + behaviour.id)»
            «ELSE»
                //Add additional public methods here
            «ENDIF»
            /*PROTECTED REGION END*/
        protected:
            virtual void initialiseParameters();
            /*PROTECTED REGION ID(pro«behaviour.id») ENABLED START*/
            «IF (protectedRegions.containsKey("pro" + behaviour.id))»
«protectedRegions.get("pro" + behaviour.id)»
            «ELSE»
                //Add additional protected methods here
            «ENDIF»
            /*PROTECTED REGION END*/
        private:
        /*PROTECTED REGION ID(priv«behaviour.id») ENABLED START*/
            «IF (protectedRegions.containsKey("priv" + behaviour.id))»
«protectedRegions.get("priv" + behaviour.id)»
            «ELSE»
                //Add additional private methods here
            «ENDIF»
        /*PROTECTED REGION END*/
    };
} /* namespace alica */

#endif /* «behaviour.name»_H_ */
'''

    def String behaviourSource(Behaviour behaviour) '''
using namespace std;
#include "«behaviour.destinationPath»/«behaviour.name».h"

/*PROTECTED REGION ID(inccpp«behaviour.id») ENABLED START*/
    //Add additional includes here
    «IF (protectedRegions.containsKey("inccpp" + behaviour.id))»
«protectedRegions.get("inccpp" + behaviour.id)»
    «ELSE»
        //Add additional includes here
    «ENDIF»
/*PROTECTED REGION END*/

namespace alica
{
    /*PROTECTED REGION ID(staticVars«behaviour.id») ENABLED START*/
    «IF (protectedRegions.containsKey("staticVars" + behaviour.id))»
«protectedRegions.get("staticVars" + behaviour.id)»
    «ELSE»
        //initialise static variables here
    «ENDIF»
    /*PROTECTED REGION END*/

    «behaviour.name»::«behaviour.name»() : DomainBehaviour("«behaviour.name»")
    {
        /*PROTECTED REGION ID(con«behaviour.id») ENABLED START*/
        «IF (protectedRegions.containsKey("con" + behaviour.id))»
«protectedRegions.get("con" + behaviour.id)»
        «ELSE»
            //Add additional options here
        «ENDIF»
        /*PROTECTED REGION END*/

    }
    «behaviour.name»::~«behaviour.name»()
    {
        /*PROTECTED REGION ID(dcon«behaviour.id») ENABLED START*/
        «IF (protectedRegions.containsKey("dcon" + behaviour.id))»
«protectedRegions.get("dcon" + behaviour.id)»
        «ELSE»
            //Add additional options here
        «ENDIF»
        /*PROTECTED REGION END*/

    }
    void «behaviour.name»::run(void* msg)
    {
        /*PROTECTED REGION ID(run«behaviour.id») ENABLED START*/
        «IF (protectedRegions.containsKey("run" + behaviour.id))»
«protectedRegions.get("run" + behaviour.id)»
        «ELSE»
            //Add additional options here
        «ENDIF»
        /*PROTECTED REGION END*/

    }
    void «behaviour.name»::initialiseParameters()
    {
        /*PROTECTED REGION ID(initialiseParameters«behaviour.id») ENABLED START*/
        «IF (protectedRegions.containsKey("run" + behaviour.id))»
«protectedRegions.get("run" + behaviour.id)»
        «ELSE»
            //Add additional options here
        «ENDIF»

        /*PROTECTED REGION END*/

    }
    /*PROTECTED REGION ID(methods«behaviour.id») ENABLED START*/
        «IF (protectedRegions.containsKey("methods" + behaviour.id))»
«protectedRegions.get("methods" + behaviour.id)»
        «ELSE»
            //Add additional options here
        «ENDIF»
    /*PROTECTED REGION END*/

} /* namespace alica */
'''

    def String utilityFunctionCreatorHeader()'''
#ifndef UTILITYFUNCTIONCREATOR_H_
#define UTILITYFUNCTIONCREATOR_H_

#include <engine/IUtilityCreator.h>
#include <memory>

namespace alica
{

    class UtilityFunctionCreator : public IUtilityCreator
    {
        public:
        virtual ~UtilityFunctionCreator();
        UtilityFunctionCreator();
        shared_ptr<BasicUtilityFunction> createUtility(long utilityfunctionConfId);
    };

} /* namespace alica */

#endif /* UTILITYFUNCTIONCREATOR_H_ */
'''


    def String utilityFunctionCreatorSource(List<Plan> plans)'''
#include <iostream>
#include "UtilityFunctionCreator.h"
«FOR p : plans»
#include  "«p.destinationPath»/«p.name»«p.id».h"
«ENDFOR»
using namespace std;
using namespace alicaAutogenerated;
namespace alica
{

    UtilityFunctionCreator::~UtilityFunctionCreator()
    {
    }

    UtilityFunctionCreator::UtilityFunctionCreator()
    {
    }


    shared_ptr<BasicUtilityFunction> UtilityFunctionCreator::createUtility(long utilityfunctionConfId)
    {
        switch(utilityfunctionConfId)
        {
            «FOR p : plans»
            case «p.id»:
                return make_shared<UtilityFunction«p.id»>();
                break;
            «ENDFOR»
            default:
            cerr << "UtilityFunctionCreator: Unknown utility requested: " << utilityfunctionConfId << endl;
            throw new exception();
            break;
        }
    }


}
'''

    def String conditionCreatorHeader()'''
#ifndef CONDITIONCREATOR_H_
#define CONDITIONCREATOR_H_

using namespace std;

#include "engine/IConditionCreator.h"
#include <memory>
#include "iostream"

namespace alica
{
    class BasicCondition;

    class ConditionCreator : public IConditionCreator
    {
        public:
        ConditionCreator();
        virtual ~ConditionCreator();
        shared_ptr<BasicCondition> createConditions(long conditionConfId);
    };

} /* namespace alica */

#endif
'''

    def String conditionCreatorSource(List<Plan> plans, List<Condition> conditions) '''
#include "ConditionCreator.h"
«FOR p : plans»
    #include  "«p.destinationPath»/«p.name»«p.id».h"
«ENDFOR»

using namespace alicaAutogenerated;
namespace alica
{

    ConditionCreator::ConditionCreator()
    {
    }
    ConditionCreator::~ConditionCreator()
    {
    }

    shared_ptr<BasicCondition> ConditionCreator::createConditions(long conditionConfId)
    {
        switch (conditionConfId)
        {
            «FOR con : conditions»
            case «con.id»:
            «IF (con instanceof PreCondition)»
                «IF (con.abstractPlan == null)»
                    return make_shared<TransitionCondition«con.id»>();
                «ELSE»
                    return make_shared<PreCondition«con.id»>();
                «ENDIF»
            «ENDIF»
            «IF (con instanceof PostCondition)»
                return make_shared<PostCondition«con.id»>();
            «ENDIF»
            «IF (con instanceof RuntimeCondition)»
                return make_shared<RunTimeCondition«con.id»>();
            «ENDIF»
                break;
            «ENDFOR»
            default:
            cerr << "ConditionCreator: Unknown condition id requested: " << conditionConfId << endl;
            throw new exception();
            break;
        }
    }
}
'''

    def String constraintCreatorHeader()'''
#ifndef CONSTRAINTCREATOR_H_
#define CONSTRAINTCREATOR_H_

#include <engine/IConstraintCreator.h>
#include <memory>

namespace alica
{

    class ConstraintCreator : public IConstraintCreator
    {
        public:
        ConstraintCreator();
        virtual ~ConstraintCreator();
        shared_ptr<BasicConstraint> createConstraint(long constraintConfId);
    };

} /* namespace alica */
#endif /* CONSTRAINTCREATOR_H_ */
'''

    def String constraintCreatorSource(List<Plan> plans, List<Condition> conditions)'''
#include "ConstraintCreator.h"
#include <iostream>

«FOR p : plans»
#include  "«p.destinationPath»/constraints/«p.name»«p.id»Constraints.h"
«ENDFOR»
using namespace std;
using namespace alicaAutogenerated;

namespace alica
{

    ConstraintCreator::ConstraintCreator()
    {
    }

    ConstraintCreator::~ConstraintCreator()
    {
    }


    shared_ptr<BasicConstraint> ConstraintCreator::createConstraint(long constraintConfId)
    {
        switch(constraintConfId)
        {
            «FOR c : conditions»
                «IF (c.vars.size > 0) || (c.quantifiers.size > 0)»
                    case «c.id»:
                    return make_shared<Constraint«c.id»>();
                    break;
                «ENDIF»
            «ENDFOR»
            default:
            cerr << "ConstraintCreator: Unknown constraint requested: " << constraintConfId << endl;
            throw new exception();
            break;
        }
    }


}
'''

    def String constraintsHeader(Plan plan)'''
#ifndef «plan.name»CONSTRAINT_H_
#define «plan.name»_H_
#include "engine/BasicConstraint.h"
#include <memory>

using namespace std;
using namespace alica;

namespace alica
{
    class ProblemDescriptor;
    class RunningPlan;
}

namespace alicaAutogenerated
{

    «IF (plan.preCondition !== null)»
        «IF (plan.preCondition.abstractPlan == null)»
        «IF (plan.preCondition.vars.size > 0) || (plan.preCondition.quantifiers.size > 0)»

            class Constraint«plan.preCondition.id» : public BasicConstraint
            {
                void getConstraint(shared_ptr<ProblemDescriptor> c, shared_ptr<RunningPlan> rp);
            };
        «ELSE»
            class Constraint«plan.preCondition.id» : public BasicConstraint
            {
                void getConstraint(shared_ptr<ProblemDescriptor> c, shared_ptr<RunningPlan> rp);
            };
        «ENDIF»
        «ENDIF»
    «ENDIF»
    «IF (plan.runtimeCondition !== null)»
    «IF (plan.runtimeCondition.vars.size > 0) || (plan.runtimeCondition.quantifiers.size > 0)»
        class Constraint«plan.runtimeCondition.id» : public BasicConstraint
        {
            void getConstraint(shared_ptr<ProblemDescriptor> c, shared_ptr<RunningPlan> rp);
        };
    «ENDIF»
    «ENDIF»
    «FOR transition : plan.transitions»
    «IF (transition.preCondition !== null)»
    «IF (transition.preCondition.vars.size > 0) || (transition.preCondition.quantifiers.size > 0)»
    class Constraint«transition.preCondition.id» : public BasicConstraint
    {
        void getConstraint(shared_ptr<ProblemDescriptor> c, shared_ptr<RunningPlan> rp);
    };
    «ENDIF»
    «ENDIF»
    «ENDFOR»
} /* namespace alica */

#endif /* «plan.name»CONSTRAINT_H_ */
'''

    def String constraintsSource(Plan plan, IConstraintCodeGenerator constraintCodeGenerator) '''
#include "«plan.destinationPath»/constraints/«plan.name»«plan.id»Constraints.h"
using namespace std;
using namespace alica;
/*PROTECTED REGION ID(ch«plan.id») ENABLED START*/
        «IF (protectedRegions.containsKey("ch" + plan.id))»
«protectedRegions.get("ch" + plan.id)»
        «ELSE»
            //Add additional options here
        «ENDIF»
/*PROTECTED REGION END*/


namespace alicaAutogenerated
{
    //Plan:«plan.name»
    /*
    * Tasks: «FOR  planEntryPoint : plan.entryPoints»
    * - EP:«planEntryPoint.id» : «planEntryPoint.task.name» («planEntryPoint.task.id»)«ENDFOR»
    *
    * States:«FOR state : plan.states»
    * - «state.name» («state.id»)«ENDFOR»
    *
    * Vars:«FOR variable : plan.vars»
    * - «variable.name» («variable.id») «ENDFOR»
    */
    «constraintCodeGenerator.constraintPlanCheckingMethods(plan)»
    «FOR state : plan.states»
        «constraintCodeGenerator.constraintStateCheckingMethods(state)»
	«ENDFOR»
}
'''

    def String domainBehaviourHeader() '''
#ifndef DomainBehaviour_H_
#define DomainBehaviour_H_

#include "engine/BasicBehaviour.h"

namespace alica
{
    class DomainBehaviour : public BasicBehaviour
    {
        public:
        DomainBehaviour(string name);
        virtual ~DomainBehaviour();
    };
} /* namespace alica */

#endif /* DomainBehaviour_H_ */
'''

    def String domainBehaviourSource() '''
#include "DomainBehaviour.h"

namespace alica
{
    DomainBehaviour::DomainBehaviour(string name) : BasicBehaviour(name)
    {
    }

    DomainBehaviour::~DomainBehaviour()
    {
    }
} /* namespace alica */
'''

    def String domainConditionHeader() '''
#ifndef DomainBehaviour_H_
#define DomainBehaviour_H_

#include "engine/BasicCondition.h"

namespace alica
{
    class DomainCondition : public BasicCondition
    {
        public:
        DomainCondition();
        virtual ~DomainCondition();
    };
} /* namespace alica */

#endif /* DomainBehaviour_H_ */
'''

    def String domainConditionSource() '''
#include "DomainCondition.h"

namespace alica
{
    DomainCondition::DomainCondition() : BasicCondition()
    {
    }

    DomainCondition::~DomainCondition()
    {
    }
} /* namespace alica */
'''

    def String planHeader(Plan plan) '''
#ifndef «plan.name»_H_
#define «plan.name»_H_

#include "DomainCondition.h"
#include "engine/BasicUtilityFunction.h"
#include "engine/UtilityFunction.h"
#include "engine/DefaultUtilityFunction.h"
/*PROTECTED REGION ID(incl«plan.id») ENABLED START*/
«IF (protectedRegions.containsKey("incl" + plan.id))»
«protectedRegions.get("incl" + plan.id)»
«ELSE»
//Add additional options here
«ENDIF»
/*PROTECTED REGION END*/
using namespace alica;

namespace alicaAutogenerated
{
    /*PROTECTED REGION ID(meth«plan.id») ENABLED START*/
    «IF (protectedRegions.containsKey("meth" + plan.id))»
«protectedRegions.get("meth" + plan.id)»
    «ELSE»
        //Add additional options here
    «ENDIF»
    /*PROTECTED REGION END*/
    class UtilityFunction«plan.id» : public BasicUtilityFunction
    {
        shared_ptr<UtilityFunction> getUtilityFunction(Plan* plan);
    };
    «IF (plan.preCondition !== null)»
        class PreCondition«plan.preCondition.id» : public DomainCondition
        {
            bool evaluate(shared_ptr<RunningPlan> rp);
        };
    «ENDIF»
    «IF (plan.runtimeCondition !== null)»
        class RunTimeCondition«plan.runtimeCondition.id» : public DomainCondition
        {
            bool evaluate(shared_ptr<RunningPlan> rp);
        };
    «ENDIF»
    «FOR s : plan.states»
        «FOR transition : s.outTransitions»
            class TransitionCondition«transition.preCondition.id» : public DomainCondition
            {
                bool evaluate(shared_ptr<RunningPlan> rp);
            };
        «ENDFOR»
    «ENDFOR»
} /* namespace alica */

#endif
'''

    def String planSource(Plan plan, IConstraintCodeGenerator constraintCodeGenerator) '''
#include "«plan.destinationPath»/«plan.name»«plan.id».h"
using namespace alica;
/*PROTECTED REGION ID(eph«plan.id») ENABLED START*/
«IF (protectedRegions.containsKey("eph" + plan.id))»
«protectedRegions.get("eph" + plan.id)»
«ELSE»
    //Add additional options here
«ENDIF»
/*PROTECTED REGION END*/

namespace alicaAutogenerated
{
    //Plan:«plan.name»
    «constraintCodeGenerator.expressionsPlanCheckingMethods(plan)»
    /* generated comment
        «FOR entryPoint : plan.entryPoints»
        Task: «entryPoint.task.name»  -> EntryPoint-ID: «entryPoint.id»
        «ENDFOR»
    */
    shared_ptr<UtilityFunction> UtilityFunction«plan.id»::getUtilityFunction(Plan* plan)
    {
       /*PROTECTED REGION ID(«plan.id») ENABLED START*/
       «IF (protectedRegions.containsKey("eph" + plan.id))»
«protectedRegions.get(plan.id)»
       «ELSE»
       «IF Plan.isInstance(this)»
            shared_ptr<UtilityFunction> defaultFunction = make_shared<DefaultUtilityFunction>(plan);
            return defaultFunction;
        «ELSE»
            return null;
        «ENDIF»
       «ENDIF»
        /*PROTECTED REGION END*/
    }
    «FOR state : plan.states»
		«constraintCodeGenerator.expressionsStateCheckingMethods(state)»
	«ENDFOR»
}
'''
}
