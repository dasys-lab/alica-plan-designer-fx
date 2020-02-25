package de.unikassel.vs.alica.planDesigner.alicaConfiguration;

public class AlicaConfiguration {

    private String activeAlicaConf;
    private String plansFolder;
    private String taskFolder;
    private String rolesFolder;

    // ---- Properties ----
    private int teamTimeOut;
    private int assignmentProtectionTime;
    private int minBroadcastFrequency;
    private int maxBroadcastFrequency;
    private int engineFrequency;
    private int maxRuleApplications;
    private boolean silentStart;
    private boolean allowIdling;
    private boolean useStaticRoles;
    private int maxEpsPerPlan;

    // ---- TeamBlackList ----
    private boolean initiallyFull;

    // ---- StatusMessages ----
    private boolean statusMessagesEnabled;
    private int frequency;

    // ---- EventLogging ----
    private boolean eventLoggingEnabled;
    private String logFolder;

    // ---- CycleDetection ----
    private boolean cycleDetectionEnabled;
    private int cycleCount;
    private int minimalAuthorityTimeInterval;
    private int maximalAuthorityTimeInterval;
    private double intervalIncreaseFactor;
    private double intervalDecreaseFactor;
    private int messageTimeInterval;
    private int messageWaitTimeInterval;
    private int historySize;

    // ---- CSPSolving ----
    private boolean enableCommunication;
    private int communicationFrequency;
    private double seedMergingThreshold;
    private int conflictThreshold;
    private int seedTTL4Communication;
    private int seedTTL4Usage;
    private int maxSolveTime;
    private int maxFunctionEvaluations;

    // ---- Getter ----

    public String getActiveAlicaConf() {
        return activeAlicaConf;
    }

    public String getPlansFolder() {
        return plansFolder;
    }

    public String getTaskFolder() {
        return taskFolder;
    }

    public String getRolesFolder() {
        return rolesFolder;
    }

    public int getTeamTimeOut() {
        return teamTimeOut;
    }

    public int getAssignmentProtectionTime() {
        return assignmentProtectionTime;
    }

    public int getMinBroadcastFrequency() {
        return minBroadcastFrequency;
    }

    public int getMaxBroadcastFrequency() {
        return maxBroadcastFrequency;
    }

    public int getEngineFrequency() {
        return engineFrequency;
    }

    public int getMaxRuleApplications() {
        return maxRuleApplications;
    }

    public boolean isSilentStart() {
        return silentStart;
    }

    public boolean isAllowIdling() {
        return allowIdling;
    }

    public boolean isUseStaticRoles() {
        return useStaticRoles;
    }

    public int getMaxEpsPerPlan() {
        return maxEpsPerPlan;
    }

    public boolean isInitiallyFull() {
        return initiallyFull;
    }

    public boolean isStatusMessagesEnabled() {
        return statusMessagesEnabled;
    }

    public int getFrequency() {
        return frequency;
    }

    public boolean isEventLoggingEnabled() {
        return eventLoggingEnabled;
    }

    public String getLogFolder() {
        return logFolder;
    }

    public boolean isCycleDetectionEnabled() {
        return cycleDetectionEnabled;
    }

    public int getCycleCount() {
        return cycleCount;
    }

    public int getMinimalAuthorityTimeInterval() {
        return minimalAuthorityTimeInterval;
    }

    public int getMaximalAuthorityTimeInterval() {
        return maximalAuthorityTimeInterval;
    }

    public double getIntervalIncreaseFactor() {
        return intervalIncreaseFactor;
    }

    public double getIntervalDecreaseFactor() {
        return intervalDecreaseFactor;
    }

    public int getMessageTimeInterval() {
        return messageTimeInterval;
    }

    public int getMessageWaitTimeInterval() {
        return messageWaitTimeInterval;
    }

    public int getHistorySize() {
        return historySize;
    }

    public boolean isEnableCommunication() {
        return enableCommunication;
    }

    public int getCommunicationFrequency() {
        return communicationFrequency;
    }

    public double getSeedMergingThreshold() {
        return seedMergingThreshold;
    }

    public int getConflictThreshold() {
        return conflictThreshold;
    }

    public int getSeedTTL4Communication() {
        return seedTTL4Communication;
    }

    public int getSeedTTL4Usage() {
        return seedTTL4Usage;
    }

    public int getMaxSolveTime() {
        return maxSolveTime;
    }

    public int getMaxFunctionEvaluations() {
        return maxFunctionEvaluations;
    }

    // ---- Setter ----

    public void setActiveAlicaConf(String activeAlicaConf) {
        this.activeAlicaConf = activeAlicaConf;
    }

    public void setPlansFolder(String plansFolder) {
        this.plansFolder = plansFolder;
    }

    public void setTaskFolder(String taskFolder) {
        this.taskFolder = taskFolder;
    }

    public void setRolesFolder(String rolesFolder) {
        this.rolesFolder = rolesFolder;
    }

    public void setTeamTimeOut(int teamTimeOut) {
        this.teamTimeOut = teamTimeOut;
    }

    public void setAssignmentProtectionTime(int assignmentProtectionTime) {
        this.assignmentProtectionTime = assignmentProtectionTime;
    }

    public void setMinBroadcastFrequency(int minBroadcastFrequency) {
        this.minBroadcastFrequency = minBroadcastFrequency;
    }

    public void setMaxBroadcastFrequency(int maxBroadcastFrequency) {
        this.maxBroadcastFrequency = maxBroadcastFrequency;
    }

    public void setEngineFrequency(int engineFrequency) {
        this.engineFrequency = engineFrequency;
    }

    public void setMaxRuleApplications(int maxRuleApplications) {
        this.maxRuleApplications = maxRuleApplications;
    }

    public void setSilentStart(boolean silentStart) {
        this.silentStart = silentStart;
    }

    public void setAllowIdling(boolean allowIdling) {
        this.allowIdling = allowIdling;
    }

    public void setUseStaticRoles(boolean useStaticRoles) {
        this.useStaticRoles = useStaticRoles;
    }

    public void setMaxEpsPerPlan(int maxEpsPerPlan) {
        this.maxEpsPerPlan = maxEpsPerPlan;
    }

    public void setInitiallyFull(boolean initiallyFull) {
        this.initiallyFull = initiallyFull;
    }

    public void setStatusMessagesEnabled(boolean statusMessagesEnabled) {
        this.statusMessagesEnabled = statusMessagesEnabled;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public void setEventLoggingEnabled(boolean eventLoggingEnabled) {
        this.eventLoggingEnabled = eventLoggingEnabled;
    }

    public void setLogFolder(String logFolder) {
        this.logFolder = logFolder;
    }

    public void setCycleDetectionEnabled(boolean cycleDetectionEnabled) {
        this.cycleDetectionEnabled = cycleDetectionEnabled;
    }

    public void setCycleCount(int cycleCount) {
        this.cycleCount = cycleCount;
    }

    public void setMinimalAuthorityTimeInterval(int minimalAuthorityTimeInterval) {
        this.minimalAuthorityTimeInterval = minimalAuthorityTimeInterval;
    }

    public void setMaximalAuthorityTimeInterval(int maximalAuthorityTimeInterval) {
        this.maximalAuthorityTimeInterval = maximalAuthorityTimeInterval;
    }

    public void setIntervalIncreaseFactor(double intervalIncreaseFactor) {
        this.intervalIncreaseFactor = intervalIncreaseFactor;
    }

    public void setIntervalDecreaseFactor(double intervalDecreaseFactor) {
        this.intervalDecreaseFactor = intervalDecreaseFactor;
    }

    public void setMessageTimeInterval(int messageTimeInterval) {
        this.messageTimeInterval = messageTimeInterval;
    }

    public void setMessageWaitTimeInterval(int messageWaitTimeInterval) {
        this.messageWaitTimeInterval = messageWaitTimeInterval;
    }

    public void setHistorySize(int historySize) {
        this.historySize = historySize;
    }

    public void setEnableCommunication(boolean enableCommunication) {
        this.enableCommunication = enableCommunication;
    }

    public void setCommunicationFrequency(int communicationFrequency) {
        this.communicationFrequency = communicationFrequency;
    }

    public void setSeedMergingThreshold(double seedMergingThreshold) {
        this.seedMergingThreshold = seedMergingThreshold;
    }

    public void setConflictThreshold(int conflictThreshold) {
        this.conflictThreshold = conflictThreshold;
    }

    public void setSeedTTL4Communication(int seedTTL4Communication) {
        this.seedTTL4Communication = seedTTL4Communication;
    }

    public void setSeedTTL4Usage(int seedTTL4Usage) {
        this.seedTTL4Usage = seedTTL4Usage;
    }

    public void setMaxSolveTime(int maxSolveTime) {
        this.maxSolveTime = maxSolveTime;
    }

    public void setMaxFunctionEvaluations(int maxFunctionEvaluations) {
        this.maxFunctionEvaluations = maxFunctionEvaluations;
    }


    public void setDefaultConfiguration() {
        // ---- Properties ----
        teamTimeOut = 2000;
        assignmentProtectionTime = 500;
        minBroadcastFrequency = 15;
        maxBroadcastFrequency = 15;
        engineFrequency = 30;
        maxRuleApplications = 20;
        silentStart = false;
        allowIdling = false;
        useStaticRoles = true;
        maxEpsPerPlan = 20;

        // ---- TeamBlackList ----
        initiallyFull = false;

        // ---- StatusMessages ----
        statusMessagesEnabled = true;
        frequency = 10;

        // ---- EventLogging ----
        eventLoggingEnabled = false;
        logFolder = "log/temp";

        // ---- CycleDetection ----
        cycleDetectionEnabled = true;
        cycleCount = 5;
        minimalAuthorityTimeInterval = 800;
        maximalAuthorityTimeInterval = 5000;
        intervalIncreaseFactor = 1.5;
        intervalDecreaseFactor = 0.999;
        messageTimeInterval = 60;
        messageWaitTimeInterval = 200;
        historySize = 45;

        // ---- CSPSolving ----
        enableCommunication = true;
        communicationFrequency = 10;
        seedMergingThreshold = 0.0015;
        conflictThreshold = 1000;
        seedTTL4Communication = 250;
        seedTTL4Usage = 5000;
        maxSolveTime = 25;
        maxFunctionEvaluations = 100000000;
    }
}
