package de.unikassel.vs.alica.planDesigner.controller;

import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IConfigurationEventHandler;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IPluginEventHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.KeyCode;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This Controller holds the logic for the configuration window.
 * The configuration window allows for editing the paths for plans, roles, source generation and tasks.
 */
public class ConfigurationWindowController implements Initializable {

//--------------------------------------------------------------------------------------------
//  FXML INJECTED
//--------------------------------------------------------------------------------------------

    // ---- EXTERNAL TOOLS ----
    @FXML
    private TitledPane externalToolsTitledPane;

    @FXML
    private Label sourceCodeEditorLabel;
    @FXML
    private TextField sourceCodeEditorTextField;
    @FXML
    private Button sourceCodeEditorFileButton;

    @FXML
    private Label clangFormatLabel;
    @FXML
    private TextField clangFormatTextField;
    @FXML
    private Button clangFormatFileButton;

    @FXML
    private Label alicaEngineRunnerLabel;
    @FXML
    private TextField alicaEngineRunnerTextField;
    @FXML
    private Button alicaEngineRunnerFileButton;


    // ---- CONFIGURATION MANAGEMENT ----
    @FXML
    private TitledPane workspaceManagementTitledPane;

    // ----
    @FXML
    private Label availableWorkspacesLabel;
    @FXML
    private ListView<String> availableWorkspacesListView;

    @FXML
    private Label activeWorkspaceLabel;
    @FXML
    private Label activeConfLabel;


    @FXML
    private Label plansFolderLabel;
    @FXML
    private TextField plansFolderTextField;
    @FXML
    private Button plansFolderFileButton;


    @FXML
    private Label rolesFolderLabel;
    @FXML
    private TextField rolesFolderTextField;
    @FXML
    private Button rolesFolderFileButton;


    @FXML
    private Label tasksFolderLabel;
    @FXML
    private TextField tasksFolderTextField;
    @FXML
    private Button tasksFolderFileButton;


    @FXML
    private Label genSourceFolderLabel;
    @FXML
    private TextField genSourceFolderTextField;
    @FXML
    private Button genSourceFolderFileButton;

    @FXML
    private Label pluginsFolderLabel;
    @FXML
    private TextField pluginsFolderTextField;
    @FXML
    private Button pluginsFolderFileButton;

    @FXML
    private Label defaultPluginLabel;
    @FXML
    private ComboBox<String> defaultPluginComboBox;


    // ---- BUTTONS ----
    @FXML
    private Button activeButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button saveAndExitButton;

//--------------------------------------------------------------------------------------------
//  FIELDS
//--------------------------------------------------------------------------------------------
    private IConfigurationEventHandler configEventHandler;
    private IPluginEventHandler pluginEventHandler;

//--------------------------------------------------------------------------------------------
//  GETTER & SETTER
//--------------------------------------------------------------------------------------------
    // ---- GETTER ----
    public String getPluginsFolder() {
        return pluginsFolderTextField.getText();
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
    public String getSourceFolder() {
        return genSourceFolderTextField.getText();
    }
    public IPluginEventHandler getPluginEventHandler(){
        return pluginEventHandler;
    }
    public IConfigurationEventHandler getConfigEventHandler() { return configEventHandler; }

    public String getSelectedConfName() {
        return this.availableWorkspacesListView.getSelectionModel().getSelectedItem();
    }
    public String getDefaultPluginName () {
        String defaultPluginName = defaultPluginComboBox.getSelectionModel().getSelectedItem();
        if (defaultPluginName == null || defaultPluginName.isEmpty()) {
            defaultPluginName = defaultPluginComboBox.getPromptText();
        }
        return defaultPluginName;
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
    public void setSourceFolder(String sourceFolder) {
        genSourceFolderTextField.setText(sourceFolder);
    }
    public void setPluginsFolder(String pluginsFolder) {
        pluginsFolderTextField.setText(pluginsFolder);
    }
    public void setClangFormat(String clangFormatPath) {
        clangFormatTextField.setText(clangFormatPath);
    }

    public void setSourceCodeEditor(String sourceCodeEditorPath) {
        sourceCodeEditorTextField.setText(sourceCodeEditorPath);
    }

    public void setAlicaEngineExecutable(String alicaEngineExecutable) {
        alicaEngineRunnerTextField.setText(alicaEngineExecutable);
    }

    public void setHandler(IConfigurationEventHandler configEventHandler) {
        this.configEventHandler = configEventHandler;
    }

    public void setPluginEventHandler (IPluginEventHandler pluginEventHandler) {
        this.pluginEventHandler = pluginEventHandler;
    }

    public void setExternalToolValue(TextField tf) {
        if (tf == sourceCodeEditorTextField) {
            configEventHandler.setEditorExecutablePath(tf.getText());
        } else if (tf == clangFormatTextField) {
            configEventHandler.setClangFormatPath(tf.getText());
        }
    }

    public void setAvailableConfigs(List<String> configNames) {
        availableWorkspacesListView.getItems().clear();
        for (String confName : configNames) {
            availableWorkspacesListView.getItems().add(confName);
        }
        // for adding a new configuration, the empty entry is necessary and specially handled
        availableWorkspacesListView.getItems().add("");
    }

    public void setAvailablePlugins(List<String> pluginNames) {
        defaultPluginComboBox.getItems().clear();
        for  (String pluginName : pluginNames) {
            defaultPluginComboBox.getItems().add(pluginName);
        }
    }

    public void setDefaultPlugin(String defaultPluginName) {
        defaultPluginComboBox.setPromptText("");
        for (String pluginName : defaultPluginComboBox.getItems()) {
            if (pluginName.equals(defaultPluginName)) {
                defaultPluginComboBox.setValue(defaultPluginName);
                defaultPluginComboBox.setPromptText(defaultPluginName);
            }
        }
    }

//--------------------------------------------------------------------------------------------
//  INTERFACE IMPLEMENTATIONS
//--------------------------------------------------------------------------------------------

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // strings
        initLabelTexts();

        // file chooser buttons
        initFileChooserButtons();

        // external tools
        clangFormatTextField.setOnKeyReleased(configEventHandler);
        sourceCodeEditorTextField.setOnKeyReleased(configEventHandler);
        configEventHandler.updateExternalTools();

        // show available configurations in the list
        setupAvailableConfigurationsListView();

        // show available plugins in the combo box
        setupAvailablePluginsComboBox();

        configEventHandler.selectConfiguration(null);

        activeButton.setOnAction(e -> configEventHandler.selectConfiguration(getSelectedConfName()));
        saveButton.setOnAction(e -> onSave(false));
        saveAndExitButton.setOnAction(e -> onSave(true));
    }

//--------------------------------------------------------------------------------------------
//  SETUP GUI
//--------------------------------------------------------------------------------------------
    /**
     * Sets all label's value of the configuration window, according to the currently configured locale.
     */
    private void initLabelTexts() {
        // labels
        I18NRepo i18NRepo = I18NRepo.getInstance();
        externalToolsTitledPane.setText(i18NRepo.getString("label.config.externalTools"));
        clangFormatLabel.setText(i18NRepo.getString("label.config.clangFormatter") + ":");
        sourceCodeEditorLabel.setText(i18NRepo.getString("label.config.sourceCodeEditor") + ":");
        alicaEngineRunnerLabel.setText(i18NRepo.getString("label.config.alicaEngineExecutable") + ":");

        workspaceManagementTitledPane.setText(i18NRepo.getString("label.config.configurationManagement"));
        availableWorkspacesLabel.setText(i18NRepo.getString("label.config.availableConfigurations") + ":");
        activeWorkspaceLabel.setText(i18NRepo.getString("label.config.activeWorkspace") + ":");
        plansFolderLabel.setText(i18NRepo.getString("label.config.planFolder") + ":");
        rolesFolderLabel.setText(i18NRepo.getString("label.config.rolesFolder") + ":");
        genSourceFolderLabel.setText(i18NRepo.getString("label.config.genSourceFolder") + ":");
        tasksFolderLabel.setText(i18NRepo.getString("label.config.tasksFolder") + ":");
        pluginsFolderLabel.setText(i18NRepo.getString("label.config.pluginsFolder") + ":");
        defaultPluginLabel.setText(i18NRepo.getString("label.config.plugin.defaultPlugin") + ":");

        // buttons
        plansFolderFileButton.setText(i18NRepo.getString("label.config.fileButton"));
        rolesFolderFileButton.setText(i18NRepo.getString("label.config.fileButton"));
        tasksFolderFileButton.setText(i18NRepo.getString("label.config.fileButton"));
        genSourceFolderFileButton.setText(i18NRepo.getString("label.config.fileButton"));
        pluginsFolderFileButton.setText(i18NRepo.getString("label.config.fileButton"));

        sourceCodeEditorFileButton.setText(i18NRepo.getString("label.config.fileButton"));
        clangFormatFileButton.setText(i18NRepo.getString("label.config.fileButton"));
        activeButton.setText(i18NRepo.getString("action.active"));
        saveButton.setText(i18NRepo.getString("action.save"));
        saveAndExitButton.setText(i18NRepo.getString("action.saveExit"));
    }

    private void initFileChooserButtons() {
        plansFolderFileButton.setOnAction(e -> makeDirectoryChooserField(plansFolderTextField));
        rolesFolderFileButton.setOnAction(e -> makeDirectoryChooserField(rolesFolderTextField));
        tasksFolderFileButton.setOnAction(e -> makeDirectoryChooserField(tasksFolderTextField));
        genSourceFolderFileButton.setOnAction(e -> makeDirectoryChooserField(genSourceFolderTextField));
        sourceCodeEditorFileButton.setOnAction(e -> makeFileChooserField(sourceCodeEditorTextField));
        clangFormatFileButton.setOnAction(e -> makeFileChooserField(clangFormatTextField));
        alicaEngineRunnerFileButton.setOnAction(e -> makeFileChooserField(alicaEngineRunnerTextField));
        pluginsFolderFileButton.setOnAction(e -> {
            makeDirectoryChooserField(pluginsFolderTextField);
            updateAvailablePlugins();
        });
    }

    private void setupAvailableConfigurationsListView() {
        availableWorkspacesListView.setItems(FXCollections.observableArrayList());
        availableWorkspacesListView.setEditable(true);
        availableWorkspacesListView.setCellFactory(TextFieldListCell.forListView());
        availableWorkspacesListView.setOnEditCommit(configEventHandler);
        availableWorkspacesListView.setOnMouseClicked(configEventHandler);
        availableWorkspacesListView.getSelectionModel().selectedItemProperty().addListener(configEventHandler);

        availableWorkspacesListView.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.DELETE){
                ListView listView = (ListView) e.getTarget();
                String confName = listView.getSelectionModel().getSelectedItems().get(0).toString();
                configEventHandler.delete(confName);
            }
        });

        configEventHandler.updateAvailableConfigurations();
        configEventHandler.showSelectedConfiguration();
        availableWorkspacesListView.requestFocus();
    }

    private void setupAvailablePluginsComboBox() {
        defaultPluginComboBox.setItems(FXCollections.observableArrayList());
        defaultPluginComboBox.getSelectionModel().selectedItemProperty().addListener(pluginEventHandler);
        defaultPluginComboBox.valueProperty().addListener(pluginEventHandler);
        updateAvailablePlugins();
    }

//--------------------------------------------------------------------------------------------
//  PRIVATE METHODS
//--------------------------------------------------------------------------------------------
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

//--------------------------------------------------------------------------------------------
//  PUBLIC METHODS
//--------------------------------------------------------------------------------------------
    /**
     * Writes everything possible to disk.
     */
    public void onSave(boolean exit) {
        String selectedItem = availableWorkspacesListView.getSelectionModel().getSelectedItem();
        configEventHandler.save(selectedItem);
        if (exit) {
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        }
    }

    public void updateAvailablePlugins() {
        pluginEventHandler.updateAvailablePlugins();
    }

    /**
     * Selects active config by name on list
     **/
    public void selectActiveConfig(String selectedConfName) {
        activeConfLabel.setText(selectedConfName);
        //availableWorkspacesListView.getSelectionModel().select(selectedConfName);
    }

    public void disableConfigInput(boolean disable) {
        plansFolderTextField.setDisable(disable);
        rolesFolderTextField.setDisable(disable);
        tasksFolderTextField.setDisable(disable);
        plansFolderTextField.setDisable(disable);
        genSourceFolderTextField.setDisable(disable);
        pluginsFolderTextField.setDisable(disable);
        defaultPluginComboBox.setDisable(disable);

        plansFolderFileButton.setDisable(disable);
        rolesFolderFileButton.setDisable(disable);
        tasksFolderFileButton.setDisable(disable);
        plansFolderFileButton.setDisable(disable);
        genSourceFolderFileButton.setDisable(disable);
        pluginsFolderFileButton.setDisable(disable);
    }
}
