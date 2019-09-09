package de.unikassel.vs.alica.planDesigner.handlerinterfaces;

public interface IAlicaMessageHandler {
    void handleAlicaMessageReceived(long senderId, String masterPlan, String currentPlan, String currentState,
                                    String currentRole, String currentTask, long[] agentIdsWithMe);
}
