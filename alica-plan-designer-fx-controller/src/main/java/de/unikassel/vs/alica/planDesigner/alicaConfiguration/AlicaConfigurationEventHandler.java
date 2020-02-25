package de.unikassel.vs.alica.planDesigner.alicaConfiguration;

import de.unikassel.vs.alica.generator.CodeGeneratorAlicaConf;
import de.unikassel.vs.alica.planDesigner.controller.AlicaConfWindowController;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IAlicaConfigurationEventHandler;
import de.unikassel.vs.alica.planDesigner.view.model.AlicaConfigurationViewModel;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.scene.control.ListView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class AlicaConfigurationEventHandler  implements IAlicaConfigurationEventHandler<ListView.EditEvent<String>> {
    private AlicaConfWindowController alicaConfWindowController;
    private AlicaConfigurationManager alicaConfigurationManager;
    private AlicaConfiguration alicaConfiguration = new AlicaConfiguration();

    public AlicaConfigurationEventHandler(AlicaConfWindowController alicaConfWindowController, AlicaConfigurationManager alicaConfigurationManager) {
        this.alicaConfWindowController = alicaConfWindowController;
        this.alicaConfigurationManager = alicaConfigurationManager;
    }

    @Override
    public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {

    }

    @Override
    public void handle(Event event) {
    }

    @Override
    public AlicaConfigurationViewModel setDefaultConfiguration(AlicaConfigurationViewModel alicaConfigurationViewModel) {
        alicaConfiguration.setDefaultConfiguration();

        // ---- Properties ----
        alicaConfigurationViewModel.setTeamTimeOutViewModel(alicaConfiguration.getTeamTimeOut());
        alicaConfigurationViewModel.setAssignmentProtectionTimeViewModel(alicaConfiguration.getAssignmentProtectionTime());
        alicaConfigurationViewModel.setMinBroadcastFrequencyViewModel(alicaConfiguration.getMinBroadcastFrequency());
        alicaConfigurationViewModel.setMaxBroadcastFrequencyViewModel(alicaConfiguration.getMaxBroadcastFrequency());
        alicaConfigurationViewModel.setEngineFrequencyViewModel(alicaConfiguration.getEngineFrequency());
        alicaConfigurationViewModel.setMaxRuleApplicationsViewModel(alicaConfiguration.getMaxRuleApplications());
        alicaConfigurationViewModel.setSilentStartViewModel(alicaConfiguration.isSilentStart());
        alicaConfigurationViewModel.setAllowIdlingViewModel(alicaConfiguration.isAllowIdling());
        alicaConfigurationViewModel.setUseStaticRolesViewModel(alicaConfiguration.isUseStaticRoles());
        alicaConfigurationViewModel.setMaxEpsPerPlanViewModel(alicaConfiguration.getMaxEpsPerPlan());

        // ---- TeamBlackList ----
        alicaConfigurationViewModel.setInitiallyFullViewModel(alicaConfiguration.isInitiallyFull());

        // ---- StatusMessages ----
        alicaConfigurationViewModel.setStatusMessagesEnabledViewModel(alicaConfiguration.isStatusMessagesEnabled());
        alicaConfigurationViewModel.setFrequencyViewModel(alicaConfiguration.getFrequency());

        // ---- EventLogging ----
        alicaConfigurationViewModel.setEventLoggingEnabledViewModel(alicaConfiguration.isEventLoggingEnabled());
        alicaConfigurationViewModel.setLogFolderViewModel(alicaConfiguration.getLogFolder());

        // ---- CycleDetection ----
        alicaConfigurationViewModel.setCycleDetectionEnabledViewModel(alicaConfiguration.isCycleDetectionEnabled());
        alicaConfigurationViewModel.setCycleCountViewModel(alicaConfiguration.getCycleCount());
        alicaConfigurationViewModel.setMinimalAuthorityTimeIntervalViewModel(alicaConfiguration.getMinimalAuthorityTimeInterval());
        alicaConfigurationViewModel.setMaximalAuthorityTimeIntervalViewModel(alicaConfiguration.getMaximalAuthorityTimeInterval());
        alicaConfigurationViewModel.setIntervalIncreaseFactorViewModel(alicaConfiguration.getIntervalIncreaseFactor());
        alicaConfigurationViewModel.setIntervalDecreaseFactorViewModel(alicaConfiguration.getIntervalDecreaseFactor());
        alicaConfigurationViewModel.setMessageTimeIntervalViewModel(alicaConfiguration.getMessageTimeInterval());
        alicaConfigurationViewModel.setMessageWaitTimeIntervalViewModel(alicaConfiguration.getMessageWaitTimeInterval());
        alicaConfigurationViewModel.setHistorySizeViewModel(alicaConfiguration.getHistorySize());

        // ---- CSPSolving ----
        alicaConfigurationViewModel.setEnableCommunicationViewModel(alicaConfiguration.isEnableCommunication());
        alicaConfigurationViewModel.setCommunicationFrequencyViewModel(alicaConfiguration.getCommunicationFrequency());
        alicaConfigurationViewModel.setSeedMergingThresholdViewModel(alicaConfiguration.getSeedMergingThreshold());
        alicaConfigurationViewModel.setConflictThresholdViewModel(alicaConfiguration.getConflictThreshold());
        alicaConfigurationViewModel.setSeedTTL4CommunicationViewModel(alicaConfiguration.getSeedTTL4Communication());
        alicaConfigurationViewModel.setSeedTTL4UsageViewModel(alicaConfiguration.getSeedTTL4Usage());
        alicaConfigurationViewModel.setMaxSolveTimeViewModel(alicaConfiguration.getMaxSolveTime());
        alicaConfigurationViewModel.setMaxFunctionEvaluationsViewModel(alicaConfiguration.getMaxFunctionEvaluations());

        return alicaConfigurationViewModel;
    }

    @Override
    public void updateModel(AlicaConfigurationViewModel alicaConfigurationViewModel) {
        alicaConfiguration.setActiveAlicaConf(alicaConfigurationViewModel.getActiveAlicaConfViewModel());
        alicaConfiguration.setPlansFolder(alicaConfigurationViewModel.getPlansFolderViewModel());
        alicaConfiguration.setRolesFolder(alicaConfigurationViewModel.getRolesFolderVieModel());
        alicaConfiguration.setTaskFolder(alicaConfigurationViewModel.getTaskFolderViewModel());

        // ---- Properties ----
        alicaConfiguration.setTeamTimeOut(alicaConfigurationViewModel.getTeamTimeOutViewModel());
        alicaConfiguration.setAssignmentProtectionTime(alicaConfigurationViewModel.getAssignmentProtectionTimeViewModel());
        alicaConfiguration.setMinBroadcastFrequency(alicaConfigurationViewModel.getMinBroadcastFrequencyViewModel());
        alicaConfiguration.setMaxBroadcastFrequency(alicaConfigurationViewModel.getMaxBroadcastFrequencyViewModel());
        alicaConfiguration.setEngineFrequency(alicaConfigurationViewModel.getEngineFrequencyViewModel());
        alicaConfiguration.setMaxRuleApplications(alicaConfigurationViewModel.getMaxRuleApplicationsViewModel());
        alicaConfiguration.setSilentStart(alicaConfigurationViewModel.isSilentStartViewModel());
        alicaConfiguration.setAllowIdling(alicaConfigurationViewModel.isAllowIdlingViewModel());
        alicaConfiguration.setUseStaticRoles(alicaConfigurationViewModel.isUseStaticRolesViewModel());
        alicaConfiguration.setMaxEpsPerPlan(alicaConfigurationViewModel.getMaxEpsPerPlanViewModel());

        // ---- TeamBlackList ----
        alicaConfiguration.setInitiallyFull(alicaConfigurationViewModel.isInitiallyFullViewModel());

        // ---- StatusMessages ----
        alicaConfiguration.setStatusMessagesEnabled(alicaConfigurationViewModel.isStatusMessagesEnabledViewModel());
        alicaConfiguration.setFrequency(alicaConfigurationViewModel.getFrequencyViewModel());

        // ---- EventLogging ----
        alicaConfiguration.setEventLoggingEnabled(alicaConfigurationViewModel.isEventLoggingEnabledViewModel());
        alicaConfiguration.setLogFolder(alicaConfigurationViewModel.getLogFolderViewModel());

        // ---- CycleDetection ----
        alicaConfiguration.setCycleDetectionEnabled(alicaConfigurationViewModel.isCycleDetectionEnabledViewModel());
        alicaConfiguration.setCycleCount(alicaConfigurationViewModel.getCycleCountViewModel());
        alicaConfiguration.setMinimalAuthorityTimeInterval(alicaConfigurationViewModel.getMinimalAuthorityTimeIntervalViewModel());
        alicaConfiguration.setMaximalAuthorityTimeInterval (alicaConfigurationViewModel.getMaximalAuthorityTimeIntervalViewModel());
        alicaConfiguration.setIntervalIncreaseFactor (alicaConfigurationViewModel.getIntervalIncreaseFactorViewModel());
        alicaConfiguration.setIntervalDecreaseFactor (alicaConfigurationViewModel.getIntervalDecreaseFactorViewModel());
        alicaConfiguration.setMessageTimeInterval (alicaConfigurationViewModel.getMessageTimeIntervalViewModel());
        alicaConfiguration.setMessageWaitTimeInterval (alicaConfigurationViewModel.getMessageWaitTimeIntervalViewModel());
        alicaConfiguration.setHistorySize (alicaConfigurationViewModel.getHistorySizeViewModel());

        // ---- CSPSolving ----
        alicaConfiguration.setEnableCommunication (alicaConfigurationViewModel.isEnableCommunicationViewModel());
        alicaConfiguration.setCommunicationFrequency (alicaConfigurationViewModel.getCommunicationFrequencyViewModel());
        alicaConfiguration.setSeedMergingThreshold (alicaConfigurationViewModel.getSeedMergingThresholdViewModel());
        alicaConfiguration.setConflictThreshold (alicaConfigurationViewModel.getConflictThresholdViewModel());
        alicaConfiguration.setSeedTTL4Communication (alicaConfigurationViewModel.getSeedTTL4CommunicationViewModel());
        alicaConfiguration.setSeedTTL4Usage (alicaConfigurationViewModel.getSeedTTL4UsageViewModel());
        alicaConfiguration.setMaxSolveTime (alicaConfigurationViewModel.getMaxSolveTimeViewModel());
        alicaConfiguration.setMaxFunctionEvaluations (alicaConfigurationViewModel.getMaxFunctionEvaluationsViewModel());
    }

    @Override
    public void save(AlicaConfigurationViewModel alicaConfigurationViewModel) {
        updateModel(alicaConfigurationViewModel);
        File alicaConfFile = new File(alicaConfiguration.getActiveAlicaConf());
        try {
            Files.deleteIfExists(alicaConfFile.toPath());
            alicaConfFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> output = new ArrayList<>();

        output.add("[Alica]");

        // ---- Properties ----
        output.add("\tTeamTimeOut = " + alicaConfiguration.getTeamTimeOut());
        output.add("\tAssignmentProtectionTime = " + alicaConfiguration.getAssignmentProtectionTime());
        output.add("\tMinBroadcastFrequency = " + alicaConfiguration.getMinBroadcastFrequency());
        output.add("\tMaxBroadcastFrequency = " + alicaConfiguration.getMaxBroadcastFrequency());
        output.add("\tEngineFrequency = " + alicaConfiguration.getEngineFrequency());
        output.add("\tMaxRuleApplications = " + alicaConfiguration.getMaxRuleApplications());
        output.add("\tSilentStart = " + alicaConfiguration.isSilentStart());
        output.add("\tAllowIdling = " + alicaConfiguration.isAllowIdling());
        output.add("\tUseStaticRoles = " + alicaConfiguration.isUseStaticRoles());
        output.add("\tMaxEpsPerPlan = " + alicaConfiguration.getMaxEpsPerPlan());
        output.add("\tPlanDir = " + alicaConfiguration.getPlansFolder());
        output.add("\tRoleDir = " + alicaConfiguration.getRolesFolder());
        output.add("\tTaskDir = " + alicaConfiguration.getTaskFolder());

        // ---- TeamBlackList ----
        output.add("\t[TeamBlackList]");
        output.add("\t\tInitiallyFull = " + alicaConfiguration.isInitiallyFull());
        output.add("\t[!TeamBlackList]");

        // ---- StatusMessages ----
        output.add("\t[StatusMessages]");
        output.add("\t\tEnabled = " + alicaConfiguration.isStatusMessagesEnabled());
        output.add("\t\tFrequency = " + alicaConfiguration.getFrequency());
        output.add("\t[!StatusMessages]");

        // ---- EventLogging ----
        output.add("\t[EventLogging]");
        output.add("\t\tEnabled = " + alicaConfiguration.isEventLoggingEnabled());
        output.add("\t\tLogFolder = " + alicaConfiguration.getLogFolder());
        output.add("\t[!EventLogging]");

        // ---- CycleDetection ----
        output.add("\t[CycleDetection]");
        output.add("\t\tEnabled = " + alicaConfiguration.isCycleDetectionEnabled());
        output.add("\t\tCycleCount = " + alicaConfiguration.getCycleCount());
        output.add("\t\tMinimalAuthorityTimeInterval = " + alicaConfiguration.getMinimalAuthorityTimeInterval());
        output.add("\t\tMaximalAuthorityTimeInterval = " + alicaConfiguration.getMaximalAuthorityTimeInterval());
        output.add("\t\tIntervalIncreaseFactor = " + alicaConfiguration.getIntervalIncreaseFactor());
        output.add("\t\tIntervalDecreaseFactor = " + alicaConfiguration.getIntervalDecreaseFactor());
        output.add("\t\tMessageTimeInterval = " + alicaConfiguration.getMessageTimeInterval());
        output.add("\t\tMessageWaitTimeInterval = " + alicaConfiguration.getMessageWaitTimeInterval());
        output.add("\t\tHistorySize = " + alicaConfiguration.getHistorySize());
        output.add("\t[!CycleDetection]");

        // ---- CSPSolving ----
        output.add("\t[CSPSolving]");
        output.add("\t\tEnableCommunication = " + alicaConfiguration.isEnableCommunication());
        output.add("\t\tCommunicationFrequency = " + alicaConfiguration.getCommunicationFrequency());
        output.add("\t\tSeedMergingThreshold = " + alicaConfiguration.getSeedMergingThreshold());
        output.add("\t\tConflictThreshold = " + alicaConfiguration.getConflictThreshold());
        output.add("\t\tSeedTTL4Communication = " + alicaConfiguration.getSeedTTL4Communication());
        output.add("\t\tSeedTTL4Usage = " + alicaConfiguration.getSeedTTL4Usage());
        output.add("\t\tMaxSolveTime = " + alicaConfiguration.getMaxSolveTime());
        output.add("\t\tMaxFunctionEvaluations = " + alicaConfiguration.getMaxFunctionEvaluations());
        output.add("\t[!CSPSolving]");

        output.add("[!Alica]");

        FileWriter writer;
        try {
            writer = new FileWriter(alicaConfiguration.getActiveAlicaConf());
            for(String str: output) {
                writer.write(str + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String loadAlicaConf(String confPath) throws IOException {
        CodeGeneratorAlicaConf conf = new CodeGeneratorAlicaConf();
        String yamlAlicaConf = conf.generateAlicaConf(confPath);

        return yamlAlicaConf;
    }
}
