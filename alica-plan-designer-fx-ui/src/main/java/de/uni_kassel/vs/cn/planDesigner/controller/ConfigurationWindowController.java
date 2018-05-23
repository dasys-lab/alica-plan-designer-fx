package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.generator.plugin.PluginManager;
import de.uni_kassel.vs.cn.planDesigner.alica.configuration.Configuration;
import de.uni_kassel.vs.cn.planDesigner.handler.ConfigurationListViewHandler;
import de.uni_kassel.vs.cn.planDesigner.alica.configuration.ConfigurationManager;
import de.uni_kassel.vs.cn.planDesigner.common.I18NRepo;
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


    // WORKSPACE MANAGEMENT GUI PART
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
    private ConfigurationListViewHandler configListViewEventHandler;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        configManager = ConfigurationManager.getInstance();
        configListViewEventHandler = new ConfigurationListViewHandler(this);
        // strings
        initLabelTexts();

        // file chooser buttons
        initFileChooserButtons();

        // external tools
        clangFormatTextField.setText(configManager.getClangFormatPath());
        sourceCodeEditorTextField.setText(configManager.getEditorExecutablePath());

        // available workspaces
        setupAvailableWorkspaceListView(configManager);

        // active workspace
        loadWorkspace(availableWorkspacesListView.getSelectionModel().getSelectedItem());

        saveButton.setOnAction(e -> onSave());
/*
        defaultPluginComboBox.setItems(FXCollections.observableArrayList(PluginManager.getInstance().getAvailablePlugins()));
        defaultPluginComboBox.setButtonCell(new StringListCell<IPlugin<?>>() {
            @Override
            protected void updateItem(IPlugin<?> item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getName());
                } else {
                    setText(null);
                }
            }
        });
        defaultPluginComboBox.getSelectionModel().select(PluginManager.getInstance().getActivePlugin());

        defaultPluginComboBox.setSelectionModel(new SingleSelectionModel<IPlugin<?>>() {
            @Override
            protected IPlugin<?> getModelItem(int index) {
                if (index == -1) {
                    return null;
                }
                return PluginManager.getInstance().getAvailablePlugins().get(index);
            }

            @Override
            protected int getItemCount() {
                return PluginManager.getInstance().getAvailablePlugins().size();
            }
        });

        defaultPluginComboBox.setCellFactory(new Callback<ListView<IPlugin<?>>, ListCell<IPlugin<?>>>() {
            @Override
            public ListCell<IPlugin<?>> call(ListView<IPlugin<?>> param) {
                return new ListCell<IPlugin<?>>() {
                    @Override
                    protected void updateItem(IPlugin<?> item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getName());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });

        workspaceComboBox.setItems(FXCollections.observableArrayList(configManager.getConfigurations()));
        workspaceComboBox.setButtonCell(new ListCell<Configuration>() {
            @Override
            protected void updateItem(Configuration item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getName());
                } else {
                    setText(null);
                }
            }
        });
        workspaceComboBox.setSelectionModel(new SingleSelectionModel<Configuration>() {
            @Override
            protected Configuration getModelItem(int index) {
                if (index == -1) {
                    return null;
                }
                return configManager.getConfigurations().get(index);
            }

            @Override
            protected int getItemCount() {
                return configManager.getConfigurations().size();
            }
        });
        workspaceComboBox.setCellFactory(new Callback<ListView<Configuration>, ListCell<Configuration>>() {
            @Override
            public ListCell<Configuration> call(ListView<Configuration> param) {
                return new ListCell<Configuration>() {
                    @Override
                    protected void updateItem(Configuration item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getName());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });

        workspaceComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Configuration>() {
            @Override
            public void changed(ObservableValue<? extends Configuration> observable, Configuration oldValue, Configuration newValue) {
                if (newValue != null) {
                    Configuration configuration = newValue.getConfiguration();
                    plansPathTextField.setText(configuration.getPlansPath());
                    rolesPathTextField.setText(configuration.getRolesPath());
                    pluginPathTextField.setText(configuration.getPluginPath());
                    miscPathTextField.setText(configuration.getTasksPath());
                    expressionsPathTextField.setText(configuration.getGenSrcPath());
                }
            }
        });*/
    }

    public void storeWorkspace(String wsName) {
        System.out.println("Storing " + wsName);

        Configuration conf = configManager.getConfiguration(wsName);
        if (conf == null)
        {
            conf = new Configuration(wsName);
            configManager.addConfiguration(conf);
        }

        conf.setPlansPath(plansFolderTextField.getText());
        conf.setRolesPath(rolesFolderTextField.getText());
        conf.setTasksPath(tasksFolderTextField.getText());
        conf.setGenSrcPath(genSourceFolderTextField.getText());
        conf.setPluginsPath(pluginsFolderTextField.getText());
        String selectedPluginsName = defaultPluginComboBox.getSelectionModel().getSelectedItem();
        if(selectedPluginsName != null && !selectedPluginsName.isEmpty()) {
            conf.setDefaultPluginName(selectedPluginsName);
        }
    }

    /**
     * Fills the gui with the values of the given workspace
     * @param wsName
     */
    public void loadWorkspace(String wsName) {
        System.out.println("Loading " + wsName);

        Configuration conf = configManager.getConfiguration(wsName);
        if (conf == null)
        {
            return;
        }

        plansFolderTextField.setText(conf.getPlansPath());
        rolesFolderTextField.setText(conf.getRolesPath());
        tasksFolderTextField.setText(conf.getTasksPath());
        genSourceFolderTextField.setText(conf.getGenSrcPath());
        pluginsFolderTextField.setText(conf.getPluginsPath());
        defaultPluginComboBox.setItems(PluginManager.getInstance().getAvailablePluginNames());
        System.out.println("Default Plugin in Conf: " + conf.getDefaultPluginName());
        defaultPluginComboBox.getSelectionModel().select(conf.getDefaultPluginName());
    }

    /**
     * Saves all {@link TextField} and {@link ComboBox} values from the configuration window.
     */
    public void onSave() {
        // store values of currently selected workspace into the workspaces configuration object
        String selectedWsName = availableWorkspacesListView.getSelectionModel().getSelectedItem();
        if (selectedWsName != null && !selectedWsName.isEmpty()) {
            storeWorkspace(selectedWsName);
            configManager.getConfiguration(selectedWsName).store();
        }
    }

    private void setupAvailableWorkspaceListView(ConfigurationManager configManager) {
        List<Configuration> wsList = configManager.getConfigurations();
        ObservableList<String> wsNameList = FXCollections.observableArrayList();
        for (Configuration ws : wsList) {
            wsNameList.add(ws.getName());
        }
        wsNameList.add("");
        availableWorkspacesListView.setItems(wsNameList);
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

        workspaceManagementTitledPane.setText(i18NRepo.getString("label.config.workspaceManagement"));
        availableWorkspacesLabel.setText(i18NRepo.getString("label.config.availableWorkspacesLabel") + ":");
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

    private static class StringListCell<T> extends ListCell<T> {
    }
}
