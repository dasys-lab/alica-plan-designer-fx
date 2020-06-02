package de.unikassel.vs.alica.planDesigner.controller;

import com.google.common.io.Files;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IAlicaConfigurationEventHandler;

import de.unikassel.vs.alica.planDesigner.view.model.AlicaConfigurationViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class AlicaConfWindowController implements Initializable {
    @FXML
    private TextField rolesFolderTextField;

    @FXML
    private Tab cycleDetectionTab;

    @FXML
    private Label seedTTL4UsageLabel;

    @FXML
    private TextField activAlicaTextField;

    @FXML
    private TextField minimalAuthorityTimeIntervalTextField;

    @FXML
    private Label logFolderLabel;

    @FXML
    private Label frequencyLabel;

    @FXML
    private Button setDefaultConfButton;

    @FXML
    private TextField conflictThresholdTextField;

    @FXML
    private TextField intervalIncreaseFactorTextField;

    @FXML
    private TextField messageTimeIntervalTextField;

    @FXML
    private CheckBox eventLoggingEnabledCheckBox;

    @FXML
    private Label useStaticRolesLabel;

    @FXML
    private Label eventLoggingEnabledLabel;

    @FXML
    private Label maxBroadcastFrequencyLabel;

    @FXML
    private Tab cSPSolvingTab;

    @FXML
    private Label intervalIncreaseFactorLabel;

    @FXML
    private Label statusMessagesEnabledLabel;

    @FXML
    private Tab propertiesTab;

    @FXML
    private Label maxSolveTimeLabel;

    @FXML
    private Label allowIdlingLabel;

    @FXML
    private CheckBox cycleDetectionEnabledCheckBox;

    @FXML
    private Tab configurationTab;

    @FXML
    private Label seedTTL4CommunicationLabel;

    @FXML
    private Label plansFolderLabel;

    @FXML
    private TextField seedTTL4UsageTextField;

    @FXML
    private Tab teamBlackListTab;

    @FXML
    private Label activAlicaLabel;

    @FXML
    private Button logFolderButton;

    @FXML
    private Label enableCommunicationLabel;

    @FXML
    private TextField minBroadcastFrequencyTextField;

    @FXML
    private Label maxFunctionEvaluationsLabel;

    @FXML
    private Label cycleDetectionEnabledLabel;

    @FXML
    private TextField communicationFrequencyTextField;

    @FXML
    private Label communicationFrequencyLabel;

    @FXML
    private TextField frequencyTextField;

    @FXML
    private Label cycleCountLabel;

    @FXML
    private Label rolesFolderLabel;

    @FXML
    private Label engineFrequencyLabel;

    @FXML
    private Label seedMergingThresholdLabel;

    @FXML
    private TextField logFolderTextField;

    @FXML
    private TextField maxEpsPerPlanTextField;

    @FXML
    private TextField seedMergingThresholdTextField;

    @FXML
    private Button rolesFolderFileButton;

    @FXML
    private TextField plansFolderTextField;

    @FXML
    private CheckBox allowIdlingCheckBox;

    @FXML
    private Label intervalDecreaseFactorLabel;

    @FXML
    private CheckBox useStaticRolesCheckBox;

    @FXML
    private Label assignmentProtectionTimeLabel;

    @FXML
    private Label silentStartLabel;

    @FXML
    private Label conflictThresholdLabel;

    @FXML
    private Button plansFolderFileButton;

    @FXML
    private Label maxRuleApplicationsLabel;

    @FXML
    private TextField engineFrequencyTextField;

    @FXML
    private Button saveButton;

    @FXML
    private Label maximalAuthorityTimeIntervalLabel;

    @FXML
    private TextField messageWaitTimeIntervalTextField;

    @FXML
    private CheckBox enableCommunicationCheckBox;

    @FXML
    private TextField maxSolveTimeTextField;

    @FXML
    private TextField assignmentProtectionTimeTextField;

    @FXML
    private TextField intervalDecreaseFactorTextField;

    @FXML
    private Button tasksFolderFileButton;

    @FXML
    private Tab eventLoggingTab;

    @FXML
    private Label tasksFolderLabel;

    @FXML
    private Label minBroadcastFrequencyLabel;

    @FXML
    private TextField maxRuleApplicationsTextField;

    @FXML
    private Label initiallyFullLabel;

    @FXML
    private TextField seedTTL4CommunicationTextField;

    @FXML
    private Label teamTimeOutLabel;

    @FXML
    private CheckBox initiallyFullCheckBox;

    @FXML
    private TextField maxBroadcastFrequencyTextField;

    @FXML
    private Label historySizeLabel;

    @FXML
    private Tab statusMessagesTab;

    @FXML
    private Button exitButton;

    @FXML
    private CheckBox statusMessagesEnabledCheckBox;

    @FXML
    private TextField cycleCountTextField;

    @FXML
    private Label maxEpsPerPlanLabel;

    @FXML
    private TextField historySizeTextField;

    @FXML
    private TextField maxFunctionEvaluationsTextField;

    @FXML
    private TextField maximalAuthorityTimeIntervalTextField;

    @FXML
    private TextField tasksFolderTextField;

    @FXML
    private CheckBox silentStartCheckBox;

    @FXML
    private Label minimalAuthorityTimeIntervalLabel;

    @FXML
    private Button activAlicaButton;

    @FXML
    private Label messageWaitTimeIntervalLabel;

    @FXML
    private TextField teamTimeOutTextField;

    @FXML
    private Label messageTimeIntervalLabel;


    public IAlicaConfigurationEventHandler alicaConfigurationEventHandler;
    public AlicaConfigurationViewModel alicaConfigurationViewModel = new AlicaConfigurationViewModel();
    private MainWindowController mainWindowController;
    private String alicaConfigPath;

    // ---- GETTER ----
    public IAlicaConfigurationEventHandler getAlicaConfigurationEventHandler() {
        return alicaConfigurationEventHandler;
    }

    public AlicaConfigurationViewModel getAlicaConfigurationViewModel() {
        return alicaConfigurationViewModel;
    }

    public String getTasksFolder() {
        return tasksFolderTextField.getText();
    }

    public String getPlansFolder() {
        return plansFolderTextField.getText();
    }

    public String getRolesFolder() {
        return rolesFolderTextField.getText();
    }

    public String getActivAlica() {
        return activAlicaTextField.getText();
    }

    public String getLogFolder() {
        return logFolderTextField.getText();
    }

    public Integer getTeamTimeOut() {
        return Integer.parseInt(teamTimeOutTextField.getText());
    }

    public Integer getAssignmentProtectionTime() {
        return Integer.parseInt(assignmentProtectionTimeTextField.getText());
    }

    public Integer getMinBroadcastFrequency() {
        return Integer.parseInt(minBroadcastFrequencyTextField.getText());
    }

    public Integer getMaxBroadcastFrequency() {
        return Integer.parseInt(maxBroadcastFrequencyTextField.getText());
    }

    public Integer getEngineFrequency() {
        return Integer.parseInt(engineFrequencyTextField.getText());
    }

    public Integer getMaxRuleApplications() {
        return Integer.parseInt(maxRuleApplicationsTextField.getText());
    }

    public Boolean getSilentStart() {
        return silentStartCheckBox.isSelected();
    }

    public Boolean getAllowIdling() {
        return allowIdlingCheckBox.isSelected();
    }

    public Boolean getUseStaticRoles() {
        return useStaticRolesCheckBox.isSelected();
    }

    public Integer getMaxEpsPerPlan() {
        return Integer.parseInt(maxEpsPerPlanTextField.getText());
    }

    public Boolean getInitiallyFull() {
        return initiallyFullCheckBox.isSelected();
    }

    public Boolean getStatusMessagesEnabled() {
        return statusMessagesEnabledCheckBox.isSelected();
    }

    public Integer getFrequency() {
        return Integer.parseInt(frequencyTextField.getText());
    }

    public Boolean getEventLoggingEnabled() {
        return eventLoggingEnabledCheckBox.isSelected();
    }

    public Boolean getCycleDetectionEnabled() {
        return cycleDetectionEnabledCheckBox.isSelected();
    }

    public Integer getCycleCount() {
        return Integer.parseInt(cycleCountTextField.getText());
    }

    public Integer getMinimalAuthorityTimeInterval() {
        return Integer.parseInt(minimalAuthorityTimeIntervalTextField.getText());
    }

    public Integer getMaximalAuthorityTimeInterval() {
        return Integer.parseInt(maximalAuthorityTimeIntervalTextField.getText());
    }

    public Double getIntervalIncreaseFactor() {
        return Double.parseDouble(intervalIncreaseFactorTextField.getText());
    }

    public Double getIntervalDecreaseFactor() {
        return Double.parseDouble(intervalDecreaseFactorTextField.getText());
    }

    public Integer getMessageTimeInterval() {
        return Integer.parseInt(messageTimeIntervalTextField.getText());
    }

    public Integer getMessageWaitTimeInterval() {
        return Integer.parseInt(messageWaitTimeIntervalTextField.getText());
    }

    public Integer getHistorySize() {
        return Integer.parseInt(historySizeTextField.getText());
    }

    public Boolean getEnableCommunication() {
        return enableCommunicationCheckBox.isSelected();
    }

    public Integer getCommunicationFrequency() {
        return Integer.parseInt(communicationFrequencyTextField.getText());
    }

    public Double getSeedMergingThreshold() {
        return Double.parseDouble(seedMergingThresholdTextField.getText());
    }

    public Integer getConflictThreshold() {
        return Integer.parseInt(conflictThresholdTextField.getText());
    }

    public Integer getSeedTTL4Communication() {
        return Integer.parseInt(seedTTL4CommunicationTextField.getText());
    }

    public Integer getSeedTTL4Usage() {
        return Integer.parseInt(seedTTL4UsageTextField.getText());
    }

    public Integer getMaxSolveTime() {
        return Integer.parseInt(maxSolveTimeTextField.getText());
    }

    public Integer getMaxFunctionEvaluations() {
        return Integer.parseInt(maxFunctionEvaluationsTextField.getText());
    }

    // ---- SETTER ---
    public void setPlansFolder(String plansFolder) {
        plansFolderTextField.setText(plansFolder);
    }

    public void setRolesFolder(String rolesFolder) {
        rolesFolderTextField.setText(rolesFolder);
    }

    public void setTasksFolder(String tasksFolder) {
        tasksFolderTextField.setText(tasksFolder);
    }

    public void setActivAlica(String activeAlica) {
        activAlicaTextField.setText(activeAlica);
    }

    public void setLogFolder(String logFolder) {
        logFolderTextField.setText(logFolder);
    }

    public void setTeamTimeOut(Integer teamTimeOut) {
        teamTimeOutTextField.setText(teamTimeOut.toString());
    }

    public void setAssignmentProtectionTime(Integer assignmentProtectionTime) {
        assignmentProtectionTimeTextField.setText(assignmentProtectionTime.toString());
    }

    public void setMinBroadcastFrequency(Integer minBroadcastFrequency) {
        minBroadcastFrequencyTextField.setText(minBroadcastFrequency.toString());
    }

    public void setMaxBroadcastFrequency(Integer maxBroadcastFrequency) {
        maxBroadcastFrequencyTextField.setText(maxBroadcastFrequency.toString());
    }

    public void setEngineFrequency(Integer engineFrequency) {
        engineFrequencyTextField.setText(engineFrequency.toString());
    }

    public void setMaxRuleApplications(Integer maxRuleApplications) {
        maxRuleApplicationsTextField.setText(maxRuleApplications.toString());
    }

    public void setSilentStart(Boolean silentStart) {
        silentStartCheckBox.setSelected(silentStart);
    }

    public void setAllowIdling(Boolean allowIdling) {
        allowIdlingCheckBox.setSelected(allowIdling);
    }

    public void setUseStaticRoles(Boolean useStaticRoles) {
        useStaticRolesCheckBox.setSelected(useStaticRoles);
    }

    public void setMaxEpsPerPlan(Integer maxEpsPerPlan) {
        maxEpsPerPlanTextField.setText(maxEpsPerPlan.toString());
    }

    public void setInitiallyFull(Boolean initiallyFull) {
        initiallyFullCheckBox.setSelected(initiallyFull);
    }

    public void setStatusMessagesEnabled(Boolean statusMessagesEnabled) {
        statusMessagesEnabledCheckBox.setSelected(statusMessagesEnabled);
    }

    public void setFrequency(Integer frequency) {
        frequencyTextField.setText(frequency.toString());
    }

    public void setEventLoggingEnabled(Boolean eventLoggingEnabled) {
        eventLoggingEnabledCheckBox.setSelected(eventLoggingEnabled);
    }

    public void setCycleDetectionEnabled(Boolean cycleDetectionEnabled) {
        cycleDetectionEnabledCheckBox.setSelected(cycleDetectionEnabled);
    }

    public void setCycleCount(Integer cycleCount) {
        cycleCountTextField.setText(cycleCount.toString());
    }

    public void setMinimalAuthorityTimeInterval(Integer minimalAuthorityTimeInterval) {
        minimalAuthorityTimeIntervalTextField.setText(minimalAuthorityTimeInterval.toString());
    }

    public void setMaximalAuthorityTimeInterval(Integer maximalAuthorityTimeInterval) {
        maximalAuthorityTimeIntervalTextField.setText(maximalAuthorityTimeInterval.toString());
    }

    public void setIntervalIncreaseFactor(Double intervalIncreaseFactor) {
        intervalIncreaseFactorTextField.setText(intervalIncreaseFactor.toString());
    }

    public void setIntervalDecreaseFactor(Double intervalDecreaseFactor) {
        intervalDecreaseFactorTextField.setText(intervalDecreaseFactor.toString());
    }

    public void setMessageTimeInterval(Integer messageTimeInterval) {
        messageTimeIntervalTextField.setText(messageTimeInterval.toString());
    }

    public void setMessageWaitTimeInterval(Integer messageWaitTimeInterval) {
        messageWaitTimeIntervalTextField.setText(messageWaitTimeInterval.toString());
    }

    public void setHistorySize(Integer historySize) {
        historySizeTextField.setText(historySize.toString());
    }

    public void setEnableCommunication(Boolean enableCommunication) {
        enableCommunicationCheckBox.setSelected(enableCommunication);
    }

    public void setCommunicationFrequency(Integer communicationFrequency) {
        communicationFrequencyTextField.setText(communicationFrequency.toString());
    }

    public void setSeedMergingThreshold(Double seedMergingThreshold) {
        seedMergingThresholdTextField.setText(seedMergingThreshold.toString());
    }

    public void setConflictThreshold(Integer conflictThreshold) {
        conflictThresholdTextField.setText(conflictThreshold.toString());
    }

    public void setSeedTTL4Communication(Integer seedTTL4Communication) {
        seedTTL4CommunicationTextField.setText(seedTTL4Communication.toString());
    }

    public void setSeedTTL4Usage(Integer seedTTL4Usage) {
        seedTTL4UsageTextField.setText(seedTTL4Usage.toString());
    }

    public void setMaxSolveTime(Integer maxSolveTime) {
        maxSolveTimeTextField.setText(maxSolveTime.toString());
    }

    public void setMaxFunctionEvaluations(Integer maxFunctionEvaluations) {
        maxFunctionEvaluationsTextField.setText(maxFunctionEvaluations.toString());
    }
    public void setHandler(IAlicaConfigurationEventHandler alicaConfigurationEventHandler) {
        this.alicaConfigurationEventHandler = alicaConfigurationEventHandler;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.mainWindowController = MainWindowController.getInstance();

        initFileChooserButtons();

        saveButton.setOnAction(e -> onSave());
        exitButton.setOnAction(e -> onExit());
        setDefaultConfButton.setOnAction(e -> loadDefaultAlicaConf());

        activAlicaTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            String extension = Files.getFileExtension(newValue);
            if (!(extension.equals("conf"))) {
                setActivAlica("Wrong File. Please load .conf File.");
                return;
            }
                setActivAlica(newValue);
            try {
                loadAlicaConfFromFile(newValue);
            } catch (IOException e) {
                e.printStackTrace();
            }
            updateViewModel();
            alicaConfigurationEventHandler.updateModel(alicaConfigurationViewModel);
        });
    }

    private void loadAlicaConfFromFile(String confPath) throws IOException {
        String yamlAlicaConf = alicaConfigurationEventHandler.loadAlicaConf(confPath);
        Yaml yaml = new Yaml();

        Map<String , Object> yamlMaps = yaml.load(yamlAlicaConf);
        Map<String , Object> alicaMaps = (Map<String, Object>) yamlMaps.get("Alica");
        Map<String , Object> teamBlackListMap = (Map<String, Object>) alicaMaps.get("TeamBlackList");
        Map<String , Object> statusMessages = (Map<String, Object>) alicaMaps.get("StatusMessages");
        Map<String , Object> eventLogging = (Map<String, Object>) alicaMaps.get("EventLogging");
        Map<String , Object> cycleDetection = (Map<String, Object>) alicaMaps.get("CycleDetection");
        Map<String , Object> cspSolving = (Map<String, Object>) alicaMaps.get("CSPSolving");

        setPlansFolder((String) alicaMaps.get("PlanDir"));
        setTasksFolder((String) alicaMaps.get("RoleDir"));
        setRolesFolder((String) alicaMaps.get("TaskDir"));

        // ---- Properties ----
        setTeamTimeOut(Integer.parseInt(alicaMaps.get("TeamTimeOut").toString()));
        setAssignmentProtectionTime(Integer.parseInt(alicaMaps.get("AssignmentProtectionTime").toString()));
        setMinBroadcastFrequency(Integer.parseInt(alicaMaps.get("MinBroadcastFrequency").toString()));
        setMaxBroadcastFrequency(Integer.parseInt(alicaMaps.get("MaxBroadcastFrequency").toString()));
        setEngineFrequency(Integer.parseInt(alicaMaps.get("EngineFrequency").toString()));
        setMaxRuleApplications(Integer.parseInt(alicaMaps.get("MaxRuleApplications").toString()));
        setSilentStart(Boolean.parseBoolean(alicaMaps.get("SilentStart").toString()));
        setAllowIdling(Boolean.parseBoolean(alicaMaps.get("AllowIdling").toString()));
        setUseStaticRoles(Boolean.parseBoolean(alicaMaps.get("UseStaticRoles").toString()));
        setMaxEpsPerPlan(Integer.parseInt(alicaMaps.get("MaxEpsPerPlan").toString()));

        // ---- TeamBlackList ----
        setInitiallyFull(Boolean.parseBoolean(teamBlackListMap.get("InitiallyFull").toString()));

        // ---- StatusMessages ----
        setStatusMessagesEnabled(Boolean.parseBoolean(statusMessages.get("Enabled").toString()));
        setFrequency(Integer.parseInt(statusMessages.get("Frequency").toString()));

        // ---- EventLogging ----
        setEventLoggingEnabled(Boolean.parseBoolean(eventLogging.get("Enabled").toString()));
        setLogFolder(eventLogging.get("LogFolder").toString());

        // ---- CycleDetection ----
        setCycleDetectionEnabled(Boolean.parseBoolean(cycleDetection.get("Enabled").toString()));
        setCycleCount(Integer.parseInt(cycleDetection.get("CycleCount").toString()));
        setMinimalAuthorityTimeInterval(Integer.parseInt(cycleDetection.get("MinimalAuthorityTimeInterval").toString()));
        setMaximalAuthorityTimeInterval(Integer.parseInt(cycleDetection.get("MaximalAuthorityTimeInterval").toString()));
        setIntervalIncreaseFactor(Double.parseDouble(cycleDetection.get("IntervalIncreaseFactor").toString()));
        setIntervalDecreaseFactor(Double.parseDouble(cycleDetection.get("IntervalDecreaseFactor").toString()));
        setMessageTimeInterval(Integer.parseInt(cycleDetection.get("MessageTimeInterval").toString()));
        setMessageWaitTimeInterval(Integer.parseInt(cycleDetection.get("MessageWaitTimeInterval").toString()));
        setHistorySize(Integer.parseInt(cycleDetection.get("HistorySize").toString()));

        // ---- CSPSolving ----
        setEnableCommunication(Boolean.parseBoolean(cspSolving.get("EnableCommunication").toString()));
        setCommunicationFrequency(Integer.parseInt(cspSolving.get("CommunicationFrequency").toString()));
        setSeedMergingThreshold(Double.parseDouble(cspSolving.get("SeedMergingThreshold").toString()));
        setConflictThreshold(Integer.parseInt(cspSolving.get("ConflictThreshold").toString()));
        setSeedTTL4Communication(Integer.parseInt(cspSolving.get( "SeedTTL4Communication").toString()));
        setSeedTTL4Usage(Integer.parseInt(cspSolving.get("SeedTTL4Usage").toString()));
        setMaxSolveTime(Integer.parseInt(cspSolving.get("MaxSolveTime").toString()));
        setMaxFunctionEvaluations(Integer.parseInt(cspSolving.get("MaxFunctionEvaluations").toString()));
    }

    private void updateViewModel() {
        alicaConfigurationViewModel.setActiveAlicaConfViewModel(getActivAlica());
        alicaConfigurationViewModel.setPlansFolderViewModel(getPlansFolder());
        alicaConfigurationViewModel.setTaskFolderViewModel(getTasksFolder());
        alicaConfigurationViewModel.setRolesFolderVieModel(getRolesFolder());

        // ---- Properties ----
        alicaConfigurationViewModel.setTeamTimeOutViewModel(getTeamTimeOut());
        alicaConfigurationViewModel.setAssignmentProtectionTimeViewModel(getAssignmentProtectionTime());
        alicaConfigurationViewModel.setMinBroadcastFrequencyViewModel(getMinBroadcastFrequency());
        alicaConfigurationViewModel.setMaxBroadcastFrequencyViewModel(getMaxBroadcastFrequency());
        alicaConfigurationViewModel.setEngineFrequencyViewModel(getEngineFrequency());
        alicaConfigurationViewModel.setMaxRuleApplicationsViewModel(getMaxRuleApplications());
        alicaConfigurationViewModel.setSilentStartViewModel(getSilentStart());
        alicaConfigurationViewModel.setAllowIdlingViewModel(getAllowIdling());
        alicaConfigurationViewModel.setUseStaticRolesViewModel(getUseStaticRoles());
        alicaConfigurationViewModel.setMaxEpsPerPlanViewModel(getMaxEpsPerPlan());

        // ---- TeamBlackList ----
        alicaConfigurationViewModel.setInitiallyFullViewModel(getInitiallyFull());

        // ---- StatusMessages ----
        alicaConfigurationViewModel.setStatusMessagesEnabledViewModel(getStatusMessagesEnabled());
        alicaConfigurationViewModel.setFrequencyViewModel(getFrequency());

        // ---- EventLogging ----
        alicaConfigurationViewModel.setEventLoggingEnabledViewModel(getEventLoggingEnabled());
        alicaConfigurationViewModel.setLogFolderViewModel(getLogFolder());

        // ---- CycleDetection ----
        alicaConfigurationViewModel.setCycleDetectionEnabledViewModel(getCycleDetectionEnabled());
        alicaConfigurationViewModel.setCycleCountViewModel(getCycleCount());
        alicaConfigurationViewModel.setMinimalAuthorityTimeIntervalViewModel(getMinimalAuthorityTimeInterval());
        alicaConfigurationViewModel.setMaximalAuthorityTimeIntervalViewModel(getMaximalAuthorityTimeInterval());
        alicaConfigurationViewModel.setIntervalIncreaseFactorViewModel(getIntervalIncreaseFactor());
        alicaConfigurationViewModel.setIntervalDecreaseFactorViewModel(getIntervalDecreaseFactor());
        alicaConfigurationViewModel.setMessageTimeIntervalViewModel(getMessageTimeInterval());
        alicaConfigurationViewModel.setMessageWaitTimeIntervalViewModel(getMessageWaitTimeInterval());
        alicaConfigurationViewModel.setHistorySizeViewModel(getHistorySize());

        // ---- CSPSolving ----
        alicaConfigurationViewModel.setEnableCommunicationViewModel(getEnableCommunication());
        alicaConfigurationViewModel.setCommunicationFrequencyViewModel(getCommunicationFrequency());
        alicaConfigurationViewModel.setSeedMergingThresholdViewModel(getSeedMergingThreshold());
        alicaConfigurationViewModel.setConflictThresholdViewModel(getConflictThreshold());
        alicaConfigurationViewModel.setSeedTTL4CommunicationViewModel(getSeedTTL4Communication());
        alicaConfigurationViewModel.setSeedTTL4UsageViewModel(getSeedTTL4Usage());
        alicaConfigurationViewModel.setMaxSolveTimeViewModel(getMaxSolveTime());
        alicaConfigurationViewModel.setMaxFunctionEvaluationsViewModel(getMaxFunctionEvaluations());
    }

    private void onSave() {
        File activeAlicaFolder = new File(mainWindowController.getPlansPath());
        updateViewModel();
        if(alicaConfigurationViewModel.getActiveAlicaConfViewModel() == null ||
                alicaConfigurationViewModel.getActiveAlicaConfViewModel().equals("") ||
                alicaConfigurationViewModel.getActiveAlicaConfViewModel().equals("Wrong File. Please load .conf File.")){
            alicaConfigurationViewModel.setActiveAlicaConfViewModel(activeAlicaFolder.getParentFile().getAbsolutePath() + "/Alica.conf");
        }
        Boolean onlyIfSave = alicaConfigurationEventHandler.save(alicaConfigurationViewModel);
        if(onlyIfSave){
            setActivAlica(alicaConfigurationViewModel.getActiveAlicaConfViewModel());
        }
    }

    private void loadDefaultAlicaConf() {
        alicaConfigurationViewModel = alicaConfigurationEventHandler.setDefaultConfiguration(alicaConfigurationViewModel);
        updateGui(alicaConfigurationViewModel);
        setPlansFolder(mainWindowController.getPlansPath());
        setRolesFolder(mainWindowController.getRolesPath());
        setTasksFolder(mainWindowController.getTasksPath());
    }

    public void loadDefaultAlicaConfNoGui() {
        alicaConfigurationViewModel = alicaConfigurationEventHandler.setDefaultConfiguration(alicaConfigurationViewModel);
    }

    private void updateGui(AlicaConfigurationViewModel alicaConfigurationViewModel) {
        setPlansFolder(alicaConfigurationViewModel.getPlansFolderViewModel());
        setRolesFolder(alicaConfigurationViewModel.getRolesFolderVieModel());
        setTasksFolder(alicaConfigurationViewModel.getTaskFolderViewModel());

        // ---- Properties ----
        setTeamTimeOut(alicaConfigurationViewModel.getTeamTimeOutViewModel());
        setAssignmentProtectionTime(alicaConfigurationViewModel.getAssignmentProtectionTimeViewModel());
        setMinBroadcastFrequency(alicaConfigurationViewModel.getMinBroadcastFrequencyViewModel());
        setMaxBroadcastFrequency(alicaConfigurationViewModel.getMaxBroadcastFrequencyViewModel());
        setEngineFrequency(alicaConfigurationViewModel.getEngineFrequencyViewModel());
        setMaxRuleApplications(alicaConfigurationViewModel.getMaxRuleApplicationsViewModel());
        setSilentStart(alicaConfigurationViewModel.isSilentStartViewModel());
        setAllowIdling(alicaConfigurationViewModel.isAllowIdlingViewModel());
        setUseStaticRoles(alicaConfigurationViewModel.isUseStaticRolesViewModel());
        setMaxEpsPerPlan(alicaConfigurationViewModel.getMaxEpsPerPlanViewModel());

        // ---- TeamBlackList ----
        setInitiallyFull(alicaConfigurationViewModel.isInitiallyFullViewModel());

        // ---- StatusMessages ----
        setStatusMessagesEnabled(alicaConfigurationViewModel.isStatusMessagesEnabledViewModel());
        setFrequency(alicaConfigurationViewModel.getFrequencyViewModel());

        // ---- EventLogging ----
        setEventLoggingEnabled(alicaConfigurationViewModel.isEventLoggingEnabledViewModel());
        setLogFolder(alicaConfigurationViewModel.getLogFolderViewModel());

        // ---- CycleDetection ----
        setCycleDetectionEnabled(alicaConfigurationViewModel.isCycleDetectionEnabledViewModel());
        setCycleCount(alicaConfigurationViewModel.getCycleCountViewModel());
        setMinimalAuthorityTimeInterval(alicaConfigurationViewModel.getMinimalAuthorityTimeIntervalViewModel());
        setMaximalAuthorityTimeInterval(alicaConfigurationViewModel.getMaximalAuthorityTimeIntervalViewModel());
        setIntervalIncreaseFactor(alicaConfigurationViewModel.getIntervalIncreaseFactorViewModel());
        setIntervalDecreaseFactor(alicaConfigurationViewModel.getIntervalDecreaseFactorViewModel());
        setMessageTimeInterval(alicaConfigurationViewModel.getMessageTimeIntervalViewModel());
        setMessageWaitTimeInterval(alicaConfigurationViewModel.getMessageWaitTimeIntervalViewModel());
        setHistorySize(alicaConfigurationViewModel.getHistorySizeViewModel());

        // ---- CSPSolving ----
        setEnableCommunication(alicaConfigurationViewModel.isEnableCommunicationViewModel());
        setCommunicationFrequency(alicaConfigurationViewModel.getCommunicationFrequencyViewModel());
        setSeedMergingThreshold(alicaConfigurationViewModel.getSeedMergingThresholdViewModel());
        setConflictThreshold(alicaConfigurationViewModel.getConflictThresholdViewModel());
        setSeedTTL4Communication(alicaConfigurationViewModel.getSeedTTL4CommunicationViewModel());
        setSeedTTL4Usage(alicaConfigurationViewModel.getSeedTTL4UsageViewModel());
        setMaxSolveTime(alicaConfigurationViewModel.getMaxSolveTimeViewModel());
        setMaxFunctionEvaluations(alicaConfigurationViewModel.getMaxFunctionEvaluationsViewModel());
    }

    private void onExit() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    private void initFileChooserButtons() {
        plansFolderFileButton.setOnAction(e -> makeDirectoryChooserField(plansFolderTextField));
        rolesFolderFileButton.setOnAction(e -> makeDirectoryChooserField(rolesFolderTextField));
        tasksFolderFileButton.setOnAction(e -> makeDirectoryChooserField(tasksFolderTextField));
        activAlicaButton.setOnAction(e -> makeFileChooserField(activAlicaTextField));
        logFolderButton.setOnAction(e -> makeDirectoryChooserField(logFolderTextField));
    }

    private void makeDirectoryChooserField(TextField textField) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(null);
        if (file != null) {
            textField.setText(file.getAbsolutePath());
        }
    }

    private void makeFileChooserField(TextField textField) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            textField.setText(file.getAbsolutePath());
        }
    }
}
