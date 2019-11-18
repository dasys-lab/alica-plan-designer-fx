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
            virtual std::shared_ptr<BasicBehaviour> createBehaviour(long behaviourId);
    };

} /* namespace alica */
