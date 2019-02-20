package de.unikassel.vs.alica.generator.cpp

import de.unikassel.vs.alica.generator.IConstraintCodeGenerator
import de.unikassel.vs.alica.planDesigner.alicamodel.Behaviour
import de.unikassel.vs.alica.planDesigner.alicamodel.Condition
import de.unikassel.vs.alica.planDesigner.alicamodel.Plan
import de.unikassel.vs.alica.planDesigner.alicamodel.PostCondition
import de.unikassel.vs.alica.planDesigner.alicamodel.PreCondition
import de.unikassel.vs.alica.planDesigner.alicamodel.RuntimeCondition
import java.util.List
import java.util.Map
import de.unikassel.vs.alica.planDesigner.alicamodel.Transition
import de.unikassel.vs.alica.planDesigner.alicamodel.EntryPoint
import de.unikassel.vs.alica.planDesigner.alicamodel.State
import de.unikassel.vs.alica.planDesigner.alicamodel.Variable

class XtendTemplates {

    private Map<String, String> protectedRegions;

    public def void setProtectedRegions (Map<String, String> regions) {
        protectedRegions = regions;
    }

    def String behaviourCreatorHeader()'''
#pragma once
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
            virtual std::shared_ptr<BasicBehaviour> createBehaviour(long behaviourConfId);
    };

} /* namespace alica */
'''

    def String behaviourCreatorSource(List<Behaviour> behaviours)'''
#include "BehaviourCreator.h"
#include "engine/BasicBehaviour.h"
«FOR beh : behaviours»
«IF (beh.relativeDirectory.isEmpty)»
#include "«beh.name».h"
«ELSE»
#include  "«beh.relativeDirectory»/«beh.name».h"
«ENDIF»
«ENDFOR»

namespace alica
{

    BehaviourCreator::BehaviourCreator()
    {
    }

    BehaviourCreator::~BehaviourCreator()
    {
    }

    std::shared_ptr<BasicBehaviour> BehaviourCreator::createBehaviour(long behaviourConfId)
    {
        switch(behaviourConfId)
        {
            «FOR beh : behaviours»
                case «beh.id»:
                return std::make_shared<«beh.name»>();
                break;
            «ENDFOR»
            default:
            std::cerr << "BehaviourCreator: Unknown behaviour requested: " << behaviourConfId << std::endl;
            throw new std::exception();
            break;
        }
    }
}
'''

    def String behaviourHeader(Behaviour behaviour)'''
#pragma once

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
            //Add additional protected methods here
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
        /*PROTECTED REGION ID(prv«behaviour.id») ENABLED START*/
            «IF (protectedRegions.containsKey("prv" + behaviour.id))»
«protectedRegions.get("prv" + behaviour.id)»
            «ELSE»
            //Add additional private methods here
            «ENDIF»
        /*PROTECTED REGION END*/
    };
} /* namespace alica */
'''

    def String behaviourSource(Behaviour behaviour) '''
«IF (behaviour.relativeDirectory.isEmpty)»
#include "«behaviour.name».h"
«ELSE»
#include  "«behaviour.relativeDirectory»/«behaviour.name».h"
«ENDIF»
#include <memory>

/*PROTECTED REGION ID(inccpp«behaviour.id») ENABLED START*/
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
        «IF (protectedRegions.containsKey("initialiseParameters" + behaviour.id))»
«protectedRegions.get("initialiseParameters" + behaviour.id)»
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
#pragma once

#include <engine/IUtilityCreator.h>
#include <memory>

namespace alica
{

    class UtilityFunctionCreator : public IUtilityCreator
    {
        public:
        virtual ~UtilityFunctionCreator();
        UtilityFunctionCreator();
        std::shared_ptr<BasicUtilityFunction> createUtility(long utilityfunctionConfId);
    };

} /* namespace alica */
'''


    def String utilityFunctionCreatorSource(List<Plan> plans)'''
#include "UtilityFunctionCreator.h"
«FOR p : plans»
«IF (p.relativeDirectory.isEmpty)»
#include "«p.name»«p.id».h"
«ELSE»
#include  "«p.relativeDirectory»/«p.name»«p.id».h"
«ENDIF»
«ENDFOR»
#include <iostream>

using namespace alicaAutogenerated;

namespace alica
{

    UtilityFunctionCreator::~UtilityFunctionCreator()
    {
    }

    UtilityFunctionCreator::UtilityFunctionCreator()
    {
    }


    std::shared_ptr<BasicUtilityFunction> UtilityFunctionCreator::createUtility(long utilityfunctionConfId)
    {
        switch(utilityfunctionConfId)
        {
            «FOR p : plans»
            case «p.id»:
                return std::make_shared<UtilityFunction«p.id»>();
                break;
            «ENDFOR»
            default:
            std::cerr << "UtilityFunctionCreator: Unknown utility requested: " << utilityfunctionConfId << std::endl;
            throw new std::exception();
            break;
        }
    }


}
'''

    def String conditionCreatorHeader()'''
#pragma once

#include <engine/IConditionCreator.h>
#include <memory>
#include <iostream>

namespace alica
{
    class BasicCondition;

    class ConditionCreator : public IConditionCreator
    {
        public:
        ConditionCreator();
        virtual ~ConditionCreator();
        std::shared_ptr<BasicCondition> createConditions(long conditionConfId);
    };

} /* namespace alica */
'''
    def String conditionCreatorSource(List<Plan> plans, List<Condition> conditions) '''
#include "ConditionCreator.h"
«FOR p : plans»
«IF (p.relativeDirectory.isEmpty)»
#include "«p.name»«p.id».h"
«ELSE»
#include  "«p.relativeDirectory»/«p.name»«p.id».h"
«ENDIF»
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

    std::shared_ptr<BasicCondition> ConditionCreator::createConditions(long conditionConfId)
    {
        switch (conditionConfId)
        {
            «FOR con : conditions»
            case «con.id»:
            «IF (con instanceof PreCondition)»
                    return std::make_shared<PreCondition«con.id»>();
            «ENDIF»
            «IF (con instanceof PostCondition)»
                return std::make_shared<PostCondition«con.id»>();
            «ENDIF»
            «IF (con instanceof RuntimeCondition)»
                return std::make_shared<RunTimeCondition«con.id»>();
            «ENDIF»
                break;
            «ENDFOR»
            default:
            std::cerr << "ConditionCreator: Unknown condition id requested: " << conditionConfId << std::endl;
            throw new std::exception();
            break;
        }
    }
}
'''

    def String constraintCreatorHeader()'''
#pragma once

#include <engine/IConstraintCreator.h>
#include <memory>

namespace alica
{

    class ConstraintCreator : public IConstraintCreator
    {
        public:
        ConstraintCreator();
        virtual ~ConstraintCreator();
        std::shared_ptr<BasicConstraint> createConstraint(long constraintConfId);
    };

} /* namespace alica */
'''

    def String constraintCreatorSource(List<Plan> plans, List<Condition> conditions)'''
#include "ConstraintCreator.h"

«FOR plan : plans»
«IF (plan.relativeDirectory.isEmpty)»
#include "constraints/«plan.name»«plan.id»Constraints.h"
«ELSE»
#include "«plan.relativeDirectory»/constraints/«plan.name»«plan.id»Constraints.h"
«ENDIF»
«ENDFOR»

#include <iostream>

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
                «IF (c.variables.size > 0) || (c.quantifiers.size > 0)»
                    case «c.id»:
                    return std::make_shared<Constraint«c.id»>();
                    break;
                «ENDIF»
            «ENDFOR»
            default:
            std::cerr << "ConstraintCreator: Unknown constraint requested: " << constraintConfId << std::endl;
            throw new std::exception();
            break;
        }
    }


}
'''

    def String constraintsHeader(Plan plan)'''
#pragma once

#include <engine/BasicConstraint.h>
#include <memory>
#include <iostream>

using namespace alica;

namespace alica
{
    class ProblemDescriptor;
    class RunningPlan;
}

namespace alicaAutogenerated
{

    «IF (plan.preCondition !== null)»
        class Constraint«plan.preCondition.id» : public BasicConstraint
        {
            void getConstraint(std::shared_ptr<ProblemDescriptor> c, std::shared_ptr<RunningPlan> rp);
        };
    «ENDIF»
    «IF (plan.runtimeCondition !== null)»
    «IF (plan.runtimeCondition.variables.size > 0) || (plan.runtimeCondition.quantifiers.size > 0)»
        class Constraint«plan.runtimeCondition.id» : public BasicConstraint
        {
            void getConstraint(std::shared_ptr<ProblemDescriptor> c, std::shared_ptr<RunningPlan> rp);
        };
    «ENDIF»
    «ENDIF»
    «var  List<Transition> trans = plan.transitions»
    «FOR transition : trans»
    «IF (transition.preCondition !== null)»
    «IF (transition.preCondition.variables.size > 0) || (transition.preCondition.quantifiers.size > 0)»
    class Constraint«transition.preCondition.id» : public BasicConstraint
    {
        void getConstraint(shared_ptr<std::ProblemDescriptor> c, std::shared_ptr<RunningPlan> rp);
    };
    «ENDIF»
    «ENDIF»
    «ENDFOR»
} /* namespace alica */
'''

    def String constraintsSource(Plan plan, IConstraintCodeGenerator constraintCodeGenerator) '''
«IF (plan.relativeDirectory.isEmpty)»
#include "constraints/«plan.name»«plan.id»Constraints.h"
«ELSE»
#include "«plan.relativeDirectory»/constraints/«plan.name»«plan.id»Constraints.h"
«ENDIF»
/*PROTECTED REGION ID(ch«plan.id») ENABLED START*/
        «IF (protectedRegions.containsKey("ch" + plan.id))»
«protectedRegions.get("ch" + plan.id)»
        «ELSE»
            //Add additional options here
        «ENDIF»
/*PROTECTED REGION END*/

using namespace alica;

namespace alicaAutogenerated
{
    //Plan:«plan.name»
    /*
    * Tasks: «var List<EntryPoint> entryPoints = plan.entryPoints» «FOR  planEntryPoint : entryPoints»
    * - EP:«planEntryPoint.id» : «planEntryPoint.task.name» («planEntryPoint.task.id»)«ENDFOR»
    *
    * States:«var List<State> states = plan.states» «FOR state : states»
    * - «state.name» («state.id»)«ENDFOR»
    *
    * Vars:«var List<Variable> variables = plan.variables» «FOR variable : variables»
    * - «variable.name» («variable.id») «ENDFOR»
    */
    «constraintCodeGenerator.constraintPlanCheckingMethods(plan)»
    «FOR state : states»
        «constraintCodeGenerator.constraintStateCheckingMethods(state)»
	«ENDFOR»
}
'''

    def String domainBehaviourHeader() '''
#pragma once

#include <engine/BasicBehaviour.h>
#include <string>
/*PROTECTED REGION ID(domainBehaviourHeaderHead) ENABLED START*/
        «IF (protectedRegions.containsKey("domainBehaviourHeaderHead"))»
«protectedRegions.get("domainBehaviourHeaderHead")»
        «ELSE»
            //Add additional options here
        «ENDIF»
/*PROTECTED REGION END*/

namespace alica
{
    class DomainBehaviour : public BasicBehaviour
    {
        public:
        DomainBehaviour(std::string name);
        virtual ~DomainBehaviour();

        /*PROTECTED REGION ID(domainBehaviourClassDecl) ENABLED START*/
«IF (protectedRegions.containsKey("domainBehaviourClassDecl"))»
«protectedRegions.get("domainBehaviourClassDecl")»
«ELSE»
        //Add additional options here
«ENDIF»
        /*PROTECTED REGION END*/
    };
} /* namespace alica */
'''

    def String domainBehaviourSource() '''
#include "DomainBehaviour.h"
/*PROTECTED REGION ID(domainBehaviourSrcHeaders) ENABLED START*/
«IF (protectedRegions.containsKey("domainBehaviourSrcHeaders"))»
«protectedRegions.get("domainBehaviourSrcHeaders")»
«ELSE»
//Add additional options here
«ENDIF»
/*PROTECTED REGION END*/

namespace alica
{
    DomainBehaviour::DomainBehaviour(std::string name) : BasicBehaviour(name)
    {
        /*PROTECTED REGION ID(domainBehaviourConstructor) ENABLED START*/
«IF (protectedRegions.containsKey("domainBehaviourConstructor"))»
«protectedRegions.get("domainBehaviourConstructor")»
«ELSE»
        //Add additional options here
«ENDIF»
        /*PROTECTED REGION END*/
    }

    DomainBehaviour::~DomainBehaviour()
    {
        /*PROTECTED REGION ID(domainBehaviourDestructor) ENABLED START*/
«IF (protectedRegions.containsKey("domainBehaviourDestructor"))»
«protectedRegions.get("domainBehaviourDestructor")»
«ELSE»
        //Add additional options here
«ENDIF»
        /*PROTECTED REGION END*/
    }

    /*PROTECTED REGION ID(domainBehaviourMethods) ENABLED START*/
«IF (protectedRegions.containsKey("domainBehaviourMethods"))»
«protectedRegions.get("domainBehaviourMethods")»
«ELSE»
    //Add additional options here
«ENDIF»
    /*PROTECTED REGION END*/
} /* namespace alica */
'''

    def String domainConditionHeader() '''
#pragma once

#include <engine/BasicCondition.h>
/*PROTECTED REGION ID(domainHeaderAdditional) ENABLED START*/
«IF (protectedRegions.containsKey("domainHeaderAdditional"))»
«protectedRegions.get("domainHeaderAdditional")»
«ELSE»
        //Add additional options here
«ENDIF»
/*PROTECTED REGION END*/

namespace alica
{
    class DomainCondition : public BasicCondition
    {
        public:
        DomainCondition();
        virtual ~DomainCondition();

        /*PROTECTED REGION ID(domainHeader) ENABLED START*/
«IF (protectedRegions.containsKey("domainHeader"))»
«protectedRegions.get("domainHeader")»
«ELSE»
        //Add additional options here
«ENDIF»

        /*PROTECTED REGION END*/

protected:
                /*PROTECTED REGION ID(protectedDomainHeader) ENABLED START*/
«IF (protectedRegions.containsKey("protectedDomainHeader"))»
«protectedRegions.get("protectedDomainHeader")»
«ELSE»
        //Add additional options here
«ENDIF»

        /*PROTECTED REGION END*/

private:
                /*PROTECTED REGION ID(privateDomainHeader) ENABLED START*/
«IF (protectedRegions.containsKey("privateDomainHeader"))»
«protectedRegions.get("privateDomainHeader")»
«ELSE»
        //Add additional options here
«ENDIF»
        /*PROTECTED REGION END*/
    };
} /* namespace alica */
'''

    def String domainConditionSource() '''
#include "DomainCondition.h"
/*PROTECTED REGION ID(domainSourceHeaders) ENABLED START*/
«IF (protectedRegions.containsKey("domainSourceHeaders"))»
«protectedRegions.get("domainSourceHeaders")»
«ELSE»
        //Add additional options here
«ENDIF»
/*PROTECTED REGION END*/

namespace alica
{
    DomainCondition::DomainCondition() : BasicCondition()
    {
        /*PROTECTED REGION ID(domainSourceConstructor) ENABLED START*/
«IF (protectedRegions.containsKey("domainSourceConstructor"))»
«protectedRegions.get("domainSourceConstructor")»
«ELSE»
        //Add additional options here
«ENDIF»
        /*PROTECTED REGION END*/
    }

    DomainCondition::~DomainCondition()
    {
        /*PROTECTED REGION ID(domainSourceDestructor) ENABLED START*/
«IF (protectedRegions.containsKey("domainSourceDestructor"))»
«protectedRegions.get("domainSourceDestructor")»
«ELSE»
        //Add additional options here
«ENDIF»
        /*PROTECTED REGION END*/
    }

    /*PROTECTED REGION ID(additionalMethodsDomainCondition) ENABLED START*/
«IF (protectedRegions.containsKey("additionalMethodsDomainCondition"))»
«protectedRegions.get("additionalMethodsDomainCondition")»
«ELSE»
        //Add additional methods here
«ENDIF»
    /*PROTECTED REGION END*/
} /* namespace alica */
'''

    def String planHeader(Plan plan) '''
#pragma once

#include "DomainCondition.h"
#include <engine/BasicUtilityFunction.h>
#include <engine/UtilityFunction.h>
#include <engine/DefaultUtilityFunction.h>
/*PROTECTED REGION ID(incl«plan.id») ENABLED START*/
«IF (protectedRegions.containsKey("incl" + plan.id))»
«protectedRegions.get("incl" + plan.id)»
«ELSE»
//Add additional includes here
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
            bool evaluate(std::shared_ptr<RunningPlan> rp);
        };
    «ENDIF»
    «IF (plan.runtimeCondition !== null)»
        class RunTimeCondition«plan.runtimeCondition.id» : public DomainCondition
        {
            bool evaluate(std::shared_ptr<RunningPlan> rp);
        };
    «ENDIF»
    «var List<State> states = plan.states»
    «FOR s : states»
        «var List<Transition> transitions = s.outTransitions»
        «FOR transition : transitions»
            «IF transition.preCondition !== null»
                class PreCondition«transition.preCondition.id» : public DomainCondition
                {
                    bool evaluate(std::shared_ptr<RunningPlan> rp);
                };
            «ENDIF»
        «ENDFOR»
    «ENDFOR»
} /* namespace alica */
'''

    def String planSource(Plan plan, IConstraintCodeGenerator constraintCodeGenerator) '''
«IF (plan.relativeDirectory.isEmpty)»
#include "«plan.name»«plan.id».h"
«ELSE»
#include "«plan.relativeDirectory»/«plan.name»«plan.id».h"
«ENDIF»
/*PROTECTED REGION ID(eph«plan.id») ENABLED START*/
«IF (protectedRegions.containsKey("eph" + plan.id))»
«protectedRegions.get("eph" + plan.id)»
«ELSE»
    //Add additional options here
«ENDIF»
/*PROTECTED REGION END*/

using namespace alica;

namespace alicaAutogenerated
{
    //Plan:«plan.name»
    «constraintCodeGenerator.expressionsPlanCheckingMethods(plan)»
    /* generated comment
        «var List<EntryPoint> entryPoints = plan.entryPoints»
        «FOR entryPoint : entryPoints»
        Task: «entryPoint.task.name»  -> EntryPoint-ID: «entryPoint.id»
        «ENDFOR»
    */
    shared_ptr<UtilityFunction> UtilityFunction«plan.id»::getUtilityFunction(Plan* plan)
    {
       /*PROTECTED REGION ID(«plan.id») ENABLED START*/
       «IF (protectedRegions.containsKey(plan.id))»
«protectedRegions.get(plan.id)»
       «ELSE»
            std::shared_ptr<UtilityFunction> defaultFunction = std::make_shared<DefaultUtilityFunction>(plan);
            return defaultFunction;
       «ENDIF»
        /*PROTECTED REGION END*/
    }
    «var List<State> states = plan.states»
    «FOR state : states»
		«constraintCodeGenerator.expressionsStateCheckingMethods(state)»
	«ENDFOR»
}
'''
}
