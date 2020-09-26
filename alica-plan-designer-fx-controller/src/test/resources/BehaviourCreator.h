#pragma once

#include <memory>
#include <engine/IBehaviourCreator.h>

namespace alica {
    class BasicBehaviour;

    class BehaviourCreator: public IBehaviourCreator {
        public:
            BehaviourCreator();
            virtual ~BehaviourCreator();
            virtual std::shared_ptr<BasicBehaviour> createBehaviour(int64_t behaviourId, void* context);
    };
}
