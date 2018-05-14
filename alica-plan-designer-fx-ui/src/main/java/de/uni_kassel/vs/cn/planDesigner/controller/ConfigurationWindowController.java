package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.generator.plugin.IPlugin;
import de.uni_kassel.vs.cn.generator.plugin.PluginManager;
import de.uni_kassel.vs.cn.planDesigner.alica.configuration.Configuration;
import de.uni_kassel.vs.cn.planDesigner.alica.configuration.Workspace;
import de.uni_kassel.vs.cn.planDesigner.alica.configuration.WorkspaceManager;
import de.uni_kassel.vs.cn.planDesigner.common.I18NRepo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This Controller holds the logic for the configuration window.
 * The configuration window allows for editing the paths for plans, roles, expressions and misc.
 *
 *
 */
public class ConfigurationWindowController implements Initializable {

    @FXML
    private TextField plansPathTextField;

    @FXML
    private TextField rolesPathTextField;

    @FXML
    private TextField expressionsPathTextField;

    @FXML
    private TextField pluginPathTextField;

    @FXML
    private TextField miscPathTextField;

    @FXML
    private TextField workspaceNameField;

    @FXML
    private TextField editorExecutablePathTextField;

    @FXML
    private TextField clangFormatPathTextField;

    @FXML
    private ComboBox<Workspace> workspaceComboBox;

    @FXML
    private ComboBox<IPlugin<?>> activePluginComboBox;

    @FXML
    private Label plansPathLabel;

    @FXML
    private Label rolesPathLabel;

    @FXML
    private Label miscPathLabel;

    @FXML
    private Label pluginPathLabel;

    @FXML
    private Label expressionsPathLabel;

    @FXML
    private Label workspaceLabel;

    @FXML
    private Label addWorkspaceLabel;

    @FXML
    private Label editorExecutablePathLabel;

    @FXML
    private Label activePluginLabel;

    @FXML
    private Label clangFormatPathLabel;

    @FXML
    private Button saveButton;

    @FXML
    private Button addWorkspaceButton;

    @FXML
    private Button plansPathFileButton;

    @FXML
    private Button rolesPathFileButton;

    @FXML
    private Button miscPathFileButton;

    @FXML
    private Button expressionsPathFileButton;

    @FXML
    private Button pluginPathFileButton;

    @FXML
    private Button editorExecutablePathFileButton;

    @FXML
    private Button clangFormatPathFileButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initLabelTexts();
        initFileChooserButtons();



        addWorkspaceButton.setOnAction(e -> {
            if (workspaceNameField.getText() != null && workspaceNameField.getText().length() != 0) {
                new WorkspaceManager().addWorkspace(new Workspace(workspaceNameField.getText(), new Configuration()));
                workspaceComboBox.setItems(FXCollections.observableArrayList(new WorkspaceManager().getWorkspaces()));
            }
        });
        // TODO refactor
        activePluginComboBox.setItems(FXCollections.observableArrayList(PluginManager.getInstance().getAvailablePlugins()));
        activePluginComboBox.setButtonCell(new StringListCell<IPlugin<?>>() {
            @Override
            protected void updateItem(IPlugin<?> item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getPluginName());
                } else {
                    setText(null);
                }
            }
        });
        activePluginComboBox.getSelectionModel().select(PluginManager.getInstance().getActivePlugin());

        activePluginComboBox.setSelectionModel(new SingleSelectionModel<IPlugin<?>>() {
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
        activePluginComboBox.setCellFactory(new Callback<ListView<IPlugin<?>>, ListCell<IPlugin<?>>>() {
            @Override
            public ListCell<IPlugin<?>> call(ListView<IPlugin<?>> param) {
                return new ListCell<IPlugin<?>>() {
                    @Override
                    protected void updateItem(IPlugin<?> item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getPluginName());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
        workspaceComboBox.setItems(FXCollections.observableArrayList(new WorkspaceManager().getWorkspaces()));
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
                return new WorkspaceManager().getWorkspaces().get(index);
            }

            @Override
            protected int getItemCount() {
                return new WorkspaceManager().getWorkspaces().size();
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
        });
        clangFormatPathTextField.setText(new WorkspaceManager().getClangFormatPath());
        editorExecutablePathTextField.setText(new WorkspaceManager().getEditorExecutablePath());

        saveButton.setOnAction(e -> onSave());
    }

    private void initFileChooserButtons() {
        plansPathFileButton.setOnAction(e -> makeDirectoryChooserField(plansPathTextField));
        rolesPathFileButton.setOnAction(e -> makeDirectoryChooserField(rolesPathTextField));
        miscPathFileButton.setOnAction(e -> makeDirectoryChooserField(miscPathTextField));
        pluginPathFileButton.setOnAction(e -> makeDirectoryChooserField(pluginPathTextField));
        expressionsPathFileButton.setOnAction(e -> makeDirectoryChooserField(expressionsPathTextField));
        editorExecutablePathFileButton.setOnAction(e -> makeFileChooserField(editorExecutablePathTextField));
        clangFormatPathFileButton.setOnAction(e -> makeFileChooserField(clangFormatPathTextField));
    }

    private void initLabelTexts() {
        plansPathLabel.setText(I18NRepo.getString("label.config.plan"));
        rolesPathLabel.setText(I18NRepo.getString("label.config.roles"));
        addWorkspaceLabel.setText(I18NRepo.getString("label.config.add"));
        expressionsPathLabel.setText(I18NRepo.getString("label.config.expressions"));
        miscPathLabel.setText(I18NRepo.getString("label.config.misc"));
        pluginPathLabel.setText(I18NRepo.getString("label.config.plugin"));
        workspaceLabel.setText(I18NRepo.getString("label.config.workspace"));
        activePluginLabel.setText(I18NRepo.getString("label.config.plugin.active"));
        saveButton.setText(I18NRepo.getString("action.save"));
        clangFormatPathLabel.setText(I18NRepo.getString("label.config.clangFormatPath"));
        editorExecutablePathLabel.setText(I18NRepo.getString("label.config.editorExecutablePath"));
        addWorkspaceButton.setText(I18NRepo.getString("action.config.add"));
        plansPathFileButton.setText(I18NRepo.getString("label.config.fileButton"));
        rolesPathFileButton.setText(I18NRepo.getString("label.config.fileButton"));
        miscPathFileButton.setText(I18NRepo.getString("label.config.fileButton"));
        pluginPathFileButton.setText(I18NRepo.getString("label.config.fileButton"));
        expressionsPathFileButton.setText(I18NRepo.getString("label.config.fileButton"));
        editorExecutablePathFileButton.setText(I18NRepo.getString("label.config.fileButton"));
        clangFormatPathFileButton.setText(I18NRepo.getString("label.config.fileButton"));
    }

    /**
     * Saves all {@link TextField} and {@link ComboBox} values from the configuration window.
     */
    public void onSave() {
        Workspace selectedWorkspace = workspaceComboBox.getSelectionModel().getSelectedItem();
        Configuration configuration = new Configuration();
        configuration.setPluginPath(pluginPathTextField.getText());
        configuration.setPlansPath(plansPathTextField.getText());
        configuration.setMiscPath(miscPathTextField.getText());
        configuration.setRolesPath(rolesPathTextField.getText());
        configuration.setExpressionValidatorsPath(expressionsPathTextField.getText());

        if (selectedWorkspace != null) {
            selectedWorkspace.setConfiguration(configuration);
        }

        WorkspaceManager workspaceManager = new WorkspaceManager();
        workspaceManager.setClangFormatPath(clangFormatPathTextField.getText());
        workspaceManager.setEclipsePath(editorExecutablePathTextField.getText());

        if (workspaceManager.getWorkspaces().contains(selectedWorkspace)) {
            workspaceManager.saveWorkspaceConfiguration(selectedWorkspace);

            // Update available plugins in case, the plugin path has changed
            PluginManager pluginManager = PluginManager.getInstance();
            pluginManager.updateAvailablePlugins();
            activePluginComboBox.setItems(FXCollections
                    .observableArrayList(pluginManager.getAvailablePlugins()));
        } else if (selectedWorkspace != null) {
            workspaceManager.addWorkspace(selectedWorkspace);
        }

        if (selectedWorkspace != null) {
            workspaceManager.setActiveWorkspace(selectedWorkspace);
        }
    }

    private void makeDirectoryChooserField(TextField textField) {
        DirectoryChooser fileChooser = new DirectoryChooser();
        File file = fileChooser.showDialog(null);
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
