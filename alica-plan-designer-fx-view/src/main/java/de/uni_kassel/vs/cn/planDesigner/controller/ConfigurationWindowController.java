package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IConfigurationEventHandler;
import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This Controller holds the logic for the configuration window.
 * The configuration window allows for editing the paths for plans, roles, source generation and tasks.
 */
public class ConfigurationWindowController implements Initializable {

    // EXTERNAL TOOLS GUI PART
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


    // CONFIGURATION MANAGEMENT GUI PART
    @FXML
    private TitledPane workspaceManagementTitledPane;

    @FXML
    private Label availableWorkspacesLabel;
    @FXML
    private ListView<String> availableWorkspacesListView;


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


    @FXML
    private Button saveButton;

    private IConfigurationEventHandler configEventHandler;

    public void setHandler(IConfigurationEventHandler configEventHandler) {
        this.configEventHandler = configEventHandler;
    }

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

        // show the values of the selected configuration
        configEventHandler.showSelectedConfiguration();

        saveButton.setOnAction(e -> onSave());

        // TODO handle plugin drop down box
    }

    /**
     * Writes everything possible to disk.
     */
    public void onSave() {
        configEventHandler.save(availableWorkspacesListView.getSelectionModel().getSelectedItem());
    }

    public void setExternalToolValue(TextField tf) {
        if (tf == sourceCodeEditorTextField) {
            configEventHandler.setEditorExecutablePath(tf.getText());
        } else if (tf == clangFormatTextField) {
            configEventHandler.setClangFormatPath(tf.getText());
        }
    }

    public String getSelectedConfName() {
        return this.availableWorkspacesListView.getSelectionModel().getSelectedItem();
    }

    public String getPlansFolder() {
        return plansFolderTextField.getText();
    }

    public void setPlansFolder(String plansFolder) {
        plansFolderTextField.setText(plansFolder);
    }

    public String getRolesFolder() {
        return rolesFolderTextField.getText();
    }

    public void setRolesFolder(String rolesFolder) {
        rolesFolderTextField.setText(rolesFolder);
    }

    public String getTasksFolder() {
        return tasksFolderTextField.getText();
    }

    public void setTasksFolder(String tasksFolder) {
        tasksFolderTextField.setText(tasksFolder);
    }

    public String getSourceFolder() {
        return genSourceFolderTextField.getText();
    }

    public void setSourceFolder(String sourceFolder) {
        genSourceFolderTextField.setText(sourceFolder);
    }

    public String getPluginsFolder() {
        return pluginsFolderTextField.getText();
    }

    public void setPluginsFolder(String pluginsFolder) {
        pluginsFolderTextField.setText(pluginsFolder);
    }

    public void setPlugins(ObservableList<String> pluginNames) {
        defaultPluginComboBox.setItems(pluginNames);
    }

    public String getDefaultPluginName () {
        return defaultPluginComboBox.getSelectionModel().getSelectedItem();
    }

    public void selectDefaultPluginName(String defaultPlugin) {
        defaultPluginComboBox.getSelectionModel().select(defaultPlugin);
    }

    public void setClangFormat(String clangFormatPath) {
        clangFormatTextField.setText(clangFormatPath);
    }

    public void setSourceCodeEditor(String sourceCodeEditorPath) {
        sourceCodeEditorTextField.setText(sourceCodeEditorPath);
    }

    public void setAvailableConfigs(List<String> configNames) {
        availableWorkspacesListView.getItems().clear();
        for (String confName : configNames) {
            availableWorkspacesListView.getItems().add(confName);
        }
        // for adding a new configuration, the empty entry is necessary and specially handled
        availableWorkspacesListView.getItems().add("");
    }

    private void setupAvailableConfigurationsListView() {
        availableWorkspacesListView.setItems(FXCollections.observableArrayList());
        availableWorkspacesListView.setEditable(true);
        availableWorkspacesListView.setCellFactory(TextFieldListCell.forListView());
        availableWorkspacesListView.setOnEditCommit(configEventHandler);
        availableWorkspacesListView.setOnMouseClicked(configEventHandler);
        availableWorkspacesListView.getSelectionModel().selectedItemProperty().addListener(configEventHandler);
        configEventHandler.updateAvailableConfigurations();
    }

    /**
     * Sets all label's text of the configuration window, according to the currently configured locale.
     */
    private void initLabelTexts() {
        // labels
        I18NRepo i18NRepo = I18NRepo.getInstance();
        externalToolsTitledPane.setText(i18NRepo.getString("label.config.externalTools"));
        clangFormatLabel.setText(i18NRepo.getString("label.config.clangFormatter") + ":");
        sourceCodeEditorLabel.setText(i18NRepo.getString("label.config.sourceCodeEditor") + ":");

        workspaceManagementTitledPane.setText(i18NRepo.getString("label.config.configurationManagement"));
        availableWorkspacesLabel.setText(i18NRepo.getString("label.config.availableConfigurations") + ":");
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
        saveButton.setText(i18NRepo.getString("action.save"));
    }

    private void initFileChooserButtons() {
        plansFolderFileButton.setOnAction(e -> makeDirectoryChooserField(plansFolderTextField));
        rolesFolderFileButton.setOnAction(e -> makeDirectoryChooserField(rolesFolderTextField));
        tasksFolderFileButton.setOnAction(e -> makeDirectoryChooserField(tasksFolderTextField));
        pluginsFolderFileButton.setOnAction(e -> makeDirectoryChooserField(pluginsFolderTextField));
        genSourceFolderFileButton.setOnAction(e -> makeDirectoryChooserField(genSourceFolderTextField));
        sourceCodeEditorFileButton.setOnAction(e -> makeFileChooserField(sourceCodeEditorTextField));
        clangFormatFileButton.setOnAction(e -> makeFileChooserField(clangFormatTextField));
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
