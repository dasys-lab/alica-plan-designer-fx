#include "BehaviourCreator.h"
#include "engine/BasicBehaviour.h"
#include "testfxBehaviour.h"

namespace alica {

BehaviourCreator::BehaviourCreator() {}

BehaviourCreator::~BehaviourCreator() {}

std::shared_ptr<BasicBehaviour>
BehaviourCreator::createBehaviour(long behaviourConfId) {
  switch (behaviourConfId) {
  case 1564163003865:
    return std::make_shared<testfxBehaviour>();
    break;
  default:
    std::cerr << "BehaviourCreator: Unknown behaviour requested: "
              << behaviourConfId << std::endl;
    throw new std::exception();
    break;
  }
}
} // namespace alica
