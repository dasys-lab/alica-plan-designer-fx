package de.unikassel.vs.alica.planDesigner.handlerinterfaces;

public interface IAlicaHandler {
    void handleAlicaMessageReceived(long senderId, String masterPlan, String currentPlan, String currentState,
                                    String currentRole, String currentTask, long[] agentsWithMe);
    boolean runAlica(String name, String masterPlan, String roleset);
    void stopAlica() throws InterruptedException;
}
