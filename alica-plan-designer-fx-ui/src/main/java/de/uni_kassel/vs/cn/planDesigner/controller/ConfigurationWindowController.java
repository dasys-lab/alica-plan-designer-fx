package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.generator.plugin.IPlugin;
import de.uni_kassel.vs.cn.generator.plugin.PluginManager;
import de.uni_kassel.vs.cn.planDesigner.alica.configuration.Configuration;
import de.uni_kassel.vs.cn.planDesigner.handler.ConfigurationListViewHandler;
import de.uni_kassel.vs.cn.planDesigner.alica.configuration.ConfigurationManager;
import de.uni_kassel.vs.cn.planDesigner.alica.configuration.Workspace;
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

        workspaceComboBox.setItems(FXCollections.observableArrayList(configManager.getWorkspaces()));
        workspaceComboBox.setButtonCell(new ListCell<Workspace>() {
            @Override
            protected void updateItem(Workspace item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getName());
                } else {
                    setText(null);
                }
            }
        });
        workspaceComboBox.setSelectionModel(new SingleSelectionModel<Workspace>() {
            @Override
            protected Workspace getModelItem(int index) {
                if (index == -1) {
                    return null;
                }
                return configManager.getWorkspaces().get(index);
            }

            @Override
            protected int getItemCount() {
                return configManager.getWorkspaces().size();
            }
        });
        workspaceComboBox.setCellFactory(new Callback<ListView<Workspace>, ListCell<Workspace>>() {
            @Override
            public ListCell<Workspace> call(ListView<Workspace> param) {
                return new ListCell<Workspace>() {
                    @Override
                    protected void updateItem(Workspace item, boolean empty) {
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

        workspaceComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Workspace>() {
            @Override
            public void changed(ObservableValue<? extends Workspace> observable, Workspace oldValue, Workspace newValue) {
                if (newValue != null) {
                    Configuration configuration = newValue.getConfiguration();
                    plansPathTextField.setText(configuration.getPlansPath());
                    rolesPathTextField.setText(configuration.getRolesPath());
                    pluginPathTextField.setText(configuration.getPluginPath());
                    miscPathTextField.setText(configuration.getMiscPath());
                    expressionsPathTextField.setText(configuration.getExpressionValidatorsPath());
                }
            }
        });*/


        saveButton.setOnAction(e -> onSave());
    }

    public void storeWorkspace(String wsName) {
        Workspace ws = configManager.getWorkspace(wsName);
        if (ws == null)
        {
            return;
        }
        System.out.println("Storing " + wsName);
        Configuration conf = ws.getConfiguration();
        conf.setPlansPath(plansFolderTextField.getText());
        conf.setRolesPath(rolesFolderTextField.getText());
        conf.setMiscPath(tasksFolderTextField.getText());
        conf.setExpressionValidatorsPath(genSourceFolderTextField.getText());
        conf.setPluginPath(pluginsFolderTextField.getText());
        String selectedPluginsName = defaultPluginComboBox.getSelectionModel().getSelectedItem();
        if(selectedPluginsName != null && !selectedPluginsName.isEmpty()) {
            conf.setDefaultPlugin(selectedPluginsName);
        }
    }

    /**
     * Fills the gui with the values of the given workspace
     * @param wsName
     */
    public void loadWorkspace(String wsName) {
        Workspace ws = configManager.getWorkspace(wsName);
        if (ws == null)
        {
            return;
        }

        System.out.println("Loading " + wsName);
        Configuration conf = ws.getConfiguration();
        plansFolderTextField.setText(conf.getPlansPath());
        rolesFolderTextField.setText(conf.getRolesPath());
        tasksFolderTextField.setText(conf.getMiscPath());
        genSourceFolderTextField.setText(conf.getExpressionValidatorsPath());
        pluginsFolderTextField.setText(conf.getPluginPath());
        defaultPluginComboBox.setItems(PluginManager.getInstance().getAvailablePluginNames());
        defaultPluginComboBox.getSelectionModel().select(conf.getDefaultPlugin());
    }

    /**
     * Saves all {@link TextField} and {@link ComboBox} values from the configuration window.
     */
    public void onSave() {
        // store values of currently selected workspace into the workspaces configuration object
        String selectedWsName = availableWorkspacesListView.getSelectionModel().getSelectedItem();
        if (selectedWsName != null && !selectedWsName.isEmpty()) {
            storeWorkspace(selectedWsName);
        }

        // TODO serialize the workspace to file
        configManager.saveWorkspaceToFile(selectedWsName);

       /* Workspace selectedWorkspace = workspaceComboBox.getSelectionModel().getSelectedItem();
        ConfigurationManager configManager = ConfigurationManager.getInstance();
        if (selectedWorkspace != null) {
            // store configuration in selected workspace
            Configuration configuration = new Configuration();
            configuration.setPluginPath(pluginsFolderTextField.getText());
            configuration.setPlansPath(plansFolderTextField.getText());
            configuration.setMiscPath(tasksFolderTextField.getText());
            configuration.setRolesPath(rolesFolderTextField.getText());
            configuration.setExpressionValidatorsPath(genSourceFolderTextField.getText());
            selectedWorkspace.setConfiguration(configuration);
            // set active workspace
            configManager.setActiveWorkspace(selectedWorkspace);
        }

        configManager.setClangFormatPath(clangFormatTextField.getText());
        configManager.setEditorExecutablePath(sourceCodeEditorTextField.getText());

        if (configManager.getWorkspaces().contains(selectedWorkspace)) {
            configManager.saveWorkspaceConfiguration(selectedWorkspace);

            // Update available plugins in case, the plugin path has changed
            PluginManager pluginManager = PluginManager.getInstance();
            pluginManager.updateAvailablePlugins();
            defaultPluginComboBox.setItems(FXCollections
                    .observableArrayList(pluginManager.getAvailablePlugins()));
        } else if (selectedWorkspace != null) {
            configManager.addWorkspace(selectedWorkspace);
        }*/
    }

    private void setupAvailableWorkspaceListView(ConfigurationManager configManager) {
        List<Workspace> wsList = configManager.getWorkspaces();
        ObservableList<String> wsNameList = FXCollections.observableArrayList();
        for (Workspace ws : wsList) {
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
