package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.generator.plugin.PluginManager;
import de.uni_kassel.vs.cn.planDesigner.handler.ConfigurationEventHandler;
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

    private ConfigurationManager configManager;
    private ConfigurationEventHandler configListViewEventHandler;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configManager = ConfigurationManager.getInstance();
        configListViewEventHandler = new ConfigurationEventHandler(this);

        // strings
        initLabelTexts();

        // file chooser buttons
        initFileChooserButtons();

        // external tools
        setupExternalTools();

        // show available configurations in the list
        setupAvailableConfigurationsListView();

        // show the values of the selected configuration
        showSelectedConfiguration();

        saveButton.setOnAction(e -> onSave());

        // TODO handle plugin drop down box
    }

    /**
     * Writes everything possible to disk.
     */
    public void onSave() {
        storeConfiguration(availableWorkspacesListView.getSelectionModel().getSelectedItem());
        configManager.writeToDisk();
    }

    public void setExternalToolValue(TextField tf) {
        if (tf == sourceCodeEditorTextField) {
            configManager.setEditorExecutablePath(tf.getText());
        } else if (tf == clangFormatTextField) {
            configManager.setClangFormatPath(tf.getText());
        }
    }

    public boolean removeConfiguration(String confName)
    {
        return configManager.removeConfiguration(confName);
    }

    public boolean renameConfiguration(String oldConfName, String newConfName) {
        return configManager.renameConfiguration(oldConfName, newConfName);
    }

    public boolean addConfiguration(String confName)
    {
        return configManager.addConfiguration(confName);
    }

    /**
     * Stores the current gui values into the corresponding configuration object.
     *
     * @param confName identifies the configuration object for storing the values
     */
    public void storeConfiguration(String confName) {
        Configuration conf = configManager.getConfiguration(confName);
        if (conf == null) {
            return;
        }

        conf.setPlansPath(plansFolderTextField.getText());
        conf.setRolesPath(rolesFolderTextField.getText());
        conf.setTasksPath(tasksFolderTextField.getText());
        conf.setGenSrcPath(genSourceFolderTextField.getText());
        conf.setPluginsPath(pluginsFolderTextField.getText());
        conf.setDefaultPluginName(defaultPluginComboBox.getSelectionModel().getSelectedItem());
    }

    /**
     * Fills the gui with the values of the currently selected configuration.
     */
    public void showSelectedConfiguration() {
        String selectedConfName = availableWorkspacesListView.getSelectionModel().getSelectedItem();
        configManager.setActiveConfiguration(selectedConfName);
        Configuration conf = configManager.getConfiguration(selectedConfName);
        if (conf == null) {
            return;
        }

        plansFolderTextField.setText(conf.getPlansPath());
        rolesFolderTextField.setText(conf.getRolesPath());
        tasksFolderTextField.setText(conf.getTasksPath());
        genSourceFolderTextField.setText(conf.getGenSrcPath());
        pluginsFolderTextField.setText(conf.getPluginsPath());
        defaultPluginComboBox.setItems(PluginManager.getInstance().getAvailablePluginNames());
        defaultPluginComboBox.getSelectionModel().select(conf.getDefaultPluginName());
    }

    private void setupExternalTools () {
        clangFormatTextField.setText(configManager.getClangFormatPath());
        clangFormatTextField.setOnKeyReleased(configListViewEventHandler);

        sourceCodeEditorTextField.setText(configManager.getEditorExecutablePath());
        sourceCodeEditorTextField.setOnKeyReleased(configListViewEventHandler);
    }

    private void setupAvailableConfigurationsListView() {
        ObservableList<String> confNameList = FXCollections.observableArrayList(configManager.getConfigurationNames());
        // for adding a new configuration, the empty entry is necessary and specially handled
        confNameList.add("");
        availableWorkspacesListView.setItems(confNameList);
        availableWorkspacesListView.setEditable(true);
        availableWorkspacesListView.setCellFactory(TextFieldListCell.forListView());
        availableWorkspacesListView.setOnEditCommit(configListViewEventHandler);
        availableWorkspacesListView.setOnMouseClicked(configListViewEventHandler);
        availableWorkspacesListView.getSelectionModel().selectedItemProperty().addListener(configListViewEventHandler);
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
