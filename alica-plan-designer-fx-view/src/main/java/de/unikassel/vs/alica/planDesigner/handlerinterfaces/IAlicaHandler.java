package de.unikassel.vs.alica.planDesigner.handlerinterfaces;

public interface IAlicaHandler {
    void handleAlicaMessageReceived(long senderId, String masterPlan, String currentPlan, String currentState,
                                    String currentRole, String currentTask, long[] agentIdsWithMe);
    boolean runAlica();
    void stopAlica();
}
