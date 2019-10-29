package de.unikassel.vs.alica.planDesigner.handlerinterfaces;

public interface IAlicaHandler {
    void handleAlicaMessageReceived(long senderId, String masterPlan, String currentPlan, String currentState,
                                    String currentRole, String currentTask, long[] agentIdsWithMe);
    boolean runAlica(String name, String masterplan, String roleset);
    void stopAlica() throws InterruptedException;
}
