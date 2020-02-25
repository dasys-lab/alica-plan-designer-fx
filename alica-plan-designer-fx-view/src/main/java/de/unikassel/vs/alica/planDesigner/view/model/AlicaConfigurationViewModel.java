package de.unikassel.vs.alica.planDesigner.view.model;

public class AlicaConfigurationViewModel {
    private String activeAlicaConfViewModel;
    private String plansFolderViewModel;
    private String taskFolderViewModel;
    private String rolesFolderVieModel;

    // ---- Properties ----
    private int teamTimeOutViewModel;
    private int assignmentProtectionTimeViewModel;
    private int minBroadcastFrequencyViewModel;
    private int maxBroadcastFrequencyViewModel;
    private int engineFrequencyViewModel;
    private int maxRuleApplicationsViewModel;
    private boolean silentStartViewModel;
    private boolean allowIdlingViewModel;
    private boolean useStaticRolesViewModel;
    private int maxEpsPerPlanViewModel;

    // ---- TeamBlackList ----
    private boolean initiallyFullViewModel;

    // ---- StatusMessages ----
    private boolean statusMessagesEnabledViewModel;
    private int frequencyViewModel;

    // ---- EventLogging ----
    private boolean eventLoggingEnabledViewModel;
    private String LogFolderViewModel;

    // ---- CycleDetection ----
    private boolean cycleDetectionEnabledViewModel;
    private int cycleCountViewModel;
    private int minimalAuthorityTimeIntervalViewModel;
    private int maximalAuthorityTimeIntervalViewModel;
    private double intervalIncreaseFactorViewModel;
    private double intervalDecreaseFactorViewModel;
    private int messageTimeIntervalViewModel;
    private int messageWaitTimeIntervalViewModel;
    private int historySizeViewModel;

    // ---- CSPSolving ----
    private boolean enableCommunicationViewModel;
    private int communicationFrequencyViewModel;
    private double seedMergingThresholdViewModel;
    private int conflictThresholdViewModel;
    private int seedTTL4CommunicationViewModel;
    private int seedTTL4UsageViewModel;
    private int maxSolveTimeViewModel;
    private int maxFunctionEvaluationsViewModel;

    // ---- Getter ----

    public String getActiveAlicaConfViewModel() {
        return activeAlicaConfViewModel;
    }

    public String getPlansFolderViewModel() {
        return plansFolderViewModel;
    }

    public String getTaskFolderViewModel() {
        return taskFolderViewModel;
    }

    public String getRolesFolderVieModel() {
        return rolesFolderVieModel;
    }

    public int getTeamTimeOutViewModel() {
        return teamTimeOutViewModel;
    }

    public int getAssignmentProtectionTimeViewModel() {
        return assignmentProtectionTimeViewModel;
    }

    public int getMinBroadcastFrequencyViewModel() {
        return minBroadcastFrequencyViewModel;
    }

    public int getMaxBroadcastFrequencyViewModel() {
        return maxBroadcastFrequencyViewModel;
    }

    public int getEngineFrequencyViewModel() {
        return engineFrequencyViewModel;
    }

    public int getMaxRuleApplicationsViewModel() {
        return maxRuleApplicationsViewModel;
    }

    public boolean isSilentStartViewModel() {
        return silentStartViewModel;
    }

    public boolean isAllowIdlingViewModel() {
        return allowIdlingViewModel;
    }

    public boolean isUseStaticRolesViewModel() {
        return useStaticRolesViewModel;
    }

    public int getMaxEpsPerPlanViewModel() {
        return maxEpsPerPlanViewModel;
    }

    public boolean isInitiallyFullViewModel() {
        return initiallyFullViewModel;
    }

    public boolean isStatusMessagesEnabledViewModel() {
        return statusMessagesEnabledViewModel;
    }

    public int getFrequencyViewModel() {
        return frequencyViewModel;
    }

    public boolean isEventLoggingEnabledViewModel() {
        return eventLoggingEnabledViewModel;
    }

    public String getLogFolderViewModel() {
        return LogFolderViewModel;
    }

    public boolean isCycleDetectionEnabledViewModel() {
        return cycleDetectionEnabledViewModel;
    }

    public int getCycleCountViewModel() {
        return cycleCountViewModel;
    }

    public int getMinimalAuthorityTimeIntervalViewModel() {
        return minimalAuthorityTimeIntervalViewModel;
    }

    public int getMaximalAuthorityTimeIntervalViewModel() {
        return maximalAuthorityTimeIntervalViewModel;
    }

    public double getIntervalIncreaseFactorViewModel() {
        return intervalIncreaseFactorViewModel;
    }

    public double getIntervalDecreaseFactorViewModel() {
        return intervalDecreaseFactorViewModel;
    }

    public int getMessageTimeIntervalViewModel() {
        return messageTimeIntervalViewModel;
    }

    public int getMessageWaitTimeIntervalViewModel() {
        return messageWaitTimeIntervalViewModel;
    }

    public int getHistorySizeViewModel() {
        return historySizeViewModel;
    }

    public boolean isEnableCommunicationViewModel() {
        return enableCommunicationViewModel;
    }

    public int getCommunicationFrequencyViewModel() {
        return communicationFrequencyViewModel;
    }

    public double getSeedMergingThresholdViewModel() {
        return seedMergingThresholdViewModel;
    }

    public int getConflictThresholdViewModel() {
        return conflictThresholdViewModel;
    }

    public int getSeedTTL4CommunicationViewModel() {
        return seedTTL4CommunicationViewModel;
    }

    public int getSeedTTL4UsageViewModel() {
        return seedTTL4UsageViewModel;
    }

    public int getMaxSolveTimeViewModel() {
        return maxSolveTimeViewModel;
    }

    public int getMaxFunctionEvaluationsViewModel() {
        return maxFunctionEvaluationsViewModel;
    }



    // ---- Setter ----

    public void setActiveAlicaConfViewModel(String activeAlicaConfViewModel) {
        this.activeAlicaConfViewModel = activeAlicaConfViewModel;
    }

    public void setPlansFolderViewModel(String plansFolderViewModel) {
        this.plansFolderViewModel = plansFolderViewModel;
    }

    public void setTaskFolderViewModel(String taskFolderViewModel) {
        this.taskFolderViewModel = taskFolderViewModel;
    }

    public void setRolesFolderVieModel(String rolesFolderVieModel) {
        this.rolesFolderVieModel = rolesFolderVieModel;
    }

    public void setTeamTimeOutViewModel(int teamTimeOutViewModel) {
        this.teamTimeOutViewModel = teamTimeOutViewModel;
    }

    public void setAssignmentProtectionTimeViewModel(int assignmentProtectionTimeViewModel) {
        this.assignmentProtectionTimeViewModel = assignmentProtectionTimeViewModel;
    }

    public void setMinBroadcastFrequencyViewModel(int minBroadcastFrequencyViewModel) {
        this.minBroadcastFrequencyViewModel = minBroadcastFrequencyViewModel;
    }

    public void setMaxBroadcastFrequencyViewModel(int maxBroadcastFrequencyViewModel) {
        this.maxBroadcastFrequencyViewModel = maxBroadcastFrequencyViewModel;
    }

    public void setEngineFrequencyViewModel(int engineFrequencyViewModel) {
        this.engineFrequencyViewModel = engineFrequencyViewModel;
    }

    public void setMaxRuleApplicationsViewModel(int maxRuleApplicationsViewModel) {
        this.maxRuleApplicationsViewModel = maxRuleApplicationsViewModel;
    }

    public void setSilentStartViewModel(boolean silentStartViewModel) {
        this.silentStartViewModel = silentStartViewModel;
    }

    public void setAllowIdlingViewModel(boolean allowIdlingViewModel) {
        this.allowIdlingViewModel = allowIdlingViewModel;
    }

    public void setUseStaticRolesViewModel(boolean useStaticRolesViewModel) {
        this.useStaticRolesViewModel = useStaticRolesViewModel;
    }

    public void setMaxEpsPerPlanViewModel(int maxEpsPerPlanViewModel) {
        this.maxEpsPerPlanViewModel = maxEpsPerPlanViewModel;
    }

    public void setInitiallyFullViewModel(boolean initiallyFullViewModel) {
        this.initiallyFullViewModel = initiallyFullViewModel;
    }

    public void setStatusMessagesEnabledViewModel(boolean statusMessagesEnabledViewModel) {
        this.statusMessagesEnabledViewModel = statusMessagesEnabledViewModel;
    }

    public void setFrequencyViewModel(int frequencyViewModel) {
        this.frequencyViewModel = frequencyViewModel;
    }

    public void setEventLoggingEnabledViewModel(boolean eventLoggingEnabledViewModel) {
        this.eventLoggingEnabledViewModel = eventLoggingEnabledViewModel;
    }

    public void setLogFolderViewModel(String logFolderViewModel) {
        LogFolderViewModel = logFolderViewModel;
    }

    public void setCycleDetectionEnabledViewModel(boolean cycleDetectionEnabledViewModel) {
        this.cycleDetectionEnabledViewModel = cycleDetectionEnabledViewModel;
    }

    public void setCycleCountViewModel(int cycleCountViewModel) {
        this.cycleCountViewModel = cycleCountViewModel;
    }

    public void setMinimalAuthorityTimeIntervalViewModel(int minimalAuthorityTimeIntervalViewModel) {
        this.minimalAuthorityTimeIntervalViewModel = minimalAuthorityTimeIntervalViewModel;
    }

    public void setMaximalAuthorityTimeIntervalViewModel(int maximalAuthorityTimeIntervalViewModel) {
        this.maximalAuthorityTimeIntervalViewModel = maximalAuthorityTimeIntervalViewModel;
    }

    public void setIntervalIncreaseFactorViewModel(double intervalIncreaseFactorViewModel) {
        this.intervalIncreaseFactorViewModel = intervalIncreaseFactorViewModel;
    }

    public void setIntervalDecreaseFactorViewModel(double intervalDecreaseFactorViewModel) {
        this.intervalDecreaseFactorViewModel = intervalDecreaseFactorViewModel;
    }

    public void setMessageTimeIntervalViewModel(int messageTimeIntervalViewModel) {
        this.messageTimeIntervalViewModel = messageTimeIntervalViewModel;
    }

    public void setMessageWaitTimeIntervalViewModel(int messageWaitTimeIntervalViewModel) {
        this.messageWaitTimeIntervalViewModel = messageWaitTimeIntervalViewModel;
    }

    public void setHistorySizeViewModel(int historySizeViewModel) {
        this.historySizeViewModel = historySizeViewModel;
    }

    public void setEnableCommunicationViewModel(boolean enableCommunicationViewModel) {
        this.enableCommunicationViewModel = enableCommunicationViewModel;
    }

    public void setCommunicationFrequencyViewModel(int communicationFrequencyViewModel) {
        this.communicationFrequencyViewModel = communicationFrequencyViewModel;
    }

    public void setSeedMergingThresholdViewModel(double seedMergingThresholdViewModel) {
        this.seedMergingThresholdViewModel = seedMergingThresholdViewModel;
    }

    public void setConflictThresholdViewModel(int conflictThresholdViewModel) {
        this.conflictThresholdViewModel = conflictThresholdViewModel;
    }

    public void setSeedTTL4CommunicationViewModel(int seedTTL4CommunicationViewModel) {
        this.seedTTL4CommunicationViewModel = seedTTL4CommunicationViewModel;
    }

    public void setSeedTTL4UsageViewModel(int seedTTL4UsageViewModel) {
        this.seedTTL4UsageViewModel = seedTTL4UsageViewModel;
    }

    public void setMaxSolveTimeViewModel(int maxSolveTimeViewModel) {
        this.maxSolveTimeViewModel = maxSolveTimeViewModel;
    }

    public void setMaxFunctionEvaluationsViewModel(int maxFunctionEvaluationsViewModel) {
        this.maxFunctionEvaluationsViewModel = maxFunctionEvaluationsViewModel;
    }
}
