package de.uni_kassel.vs.cn.planDesigner.alica.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by marci on 09.06.17.
 */
public class WorkspaceManager {
    private static final Logger LOG = LogManager.getLogger(WorkspaceManager.class);
    private static List<Workspace> workspaces;
    private static File workspacesFile;
    private static Properties workspacesProperties;

    public WorkspaceManager() {
        if (workspacesProperties == null) {
            workspacesProperties = new Properties();
        }
    }

    public void init() {
        workspaces = new ArrayList<>();
        loadWorkspaces();
    }

    public void addWorkspace(Workspace workspace) {
        workspaces.add(workspace);
        workspacesProperties.setProperty("workspaces", workspacesProperties.getProperty("workspaces") + ","+ workspace.getName());
        saveWorkspacesFile();
        saveWorkspaceConfiguration(workspace);
        LOG.info("Added new workspace " + workspace.getName());
    }

    public void saveWorkspacesFile() {
        try {
            workspacesProperties.store(new FileOutputStream(new File("workspaces.properties")), "configuration file");
        } catch (IOException e) {
            LOG.error("Could not save workspaces.properties!", e);
            throw new RuntimeException(e);
        }
    }

    public void saveWorkspaceConfiguration(Workspace workspace) {
        try {
            File workspaceFile = new File(workspace.getName() + ".properties");
            if (workspaceFile.exists() == false) {
                workspaceFile.createNewFile();
            }

            Configuration configuration = workspace.getConfiguration();

            Properties props = new Properties();
            props.setProperty("plansPath", configuration.getPlansPath());
            props.setProperty("rolesPath", configuration.getRolesPath());
            props.setProperty("miscPath", configuration.getMiscPath());
            props.setProperty("expressionValidatorsPath", configuration.getExpressionValidatorsPath());
            props.setProperty("pluginPath", configuration.getPluginPath());
            FileOutputStream out = new FileOutputStream(workspaceFile);
            props.store(out, workspace.getName() + "configuration file");
            out.close();
        } catch (IOException e) {
            LOG.error("Could not save workspace configuration for " + workspace.getName(), e);
            throw new RuntimeException(e);
        }
    }

    public List<Workspace> getWorkspaces() {
        return workspaces;
    }

    public Workspace getActiveWorkspace() {
        InputStream is = null;
        try {
            workspacesFile = new File("workspaces.properties");
            is = new FileInputStream(workspacesFile);
        } catch (FileNotFoundException e) {
            createDefaultConfigurationFiles();
        }

        try {
            workspacesProperties.load(is);
            is.close();
        } catch (IOException e) {
            LOG.error("Could not load workspace", e);
        }


        return loadWorkspace(workspacesProperties.getProperty("activeWorkspace"));
    }

    public void setActiveWorkspace(Workspace activeWorkspace) {
        workspacesProperties.setProperty("activeWorkspace", activeWorkspace.getName());
    }

    private void loadWorkspaces() {
        workspacesProperties = new Properties();
        InputStream is = null;
        try {
            workspacesFile = new File("workspaces.properties");
            is = new FileInputStream(workspacesFile);
        } catch (FileNotFoundException e) {
            createDefaultConfigurationFiles();
            workspacesFile = new File("workspaces.properties");
            try {
                is = new FileInputStream(workspacesFile);
            } catch (FileNotFoundException e1) {
                LOG.fatal("Could not find workspaces.properties after trying to create it.", e1);
            }
        }

        try {
            workspacesProperties.load(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String workspaceNames = workspacesProperties.getProperty("workspaces", "default");
        String[] split = workspaceNames.split(",");
        for (String workspaceName : split) {
            workspaces.add(loadWorkspace(workspaceName));
        }

        setActiveWorkspace(workspaces
                .stream()
                .filter(e -> e.getName().equals(workspacesProperties.getProperty("activeWorkspace")))
                .findFirst().orElse(null));

    }

    private void createDefaultConfigurationFiles() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("test");
        ResourceBundle workspaceBundle = ResourceBundle.getBundle("workspaces");
        Properties workspaceProperties = getPropertiesFromResourceBundle(workspaceBundle);
        Properties defaultProperties = getPropertiesFromResourceBundle(resourceBundle);
        try {
            File workspacesFile = new File("workspaces.properties");
            workspacesFile.createNewFile();
            File defaultConfigFile = new File("default.properties");
            defaultConfigFile.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(defaultConfigFile);
            FileOutputStream workspacesOutputStream = new FileOutputStream(workspacesFile);

            workspaceProperties.store(workspacesOutputStream, "workspaces configuration");
            defaultProperties.store(fileOutputStream, "default properties");
            fileOutputStream.close();
        } catch (IOException e1) {
            LOG.fatal("Could not create default configuration files", e1);
            throw new RuntimeException("Could not create default configuration file");
        }
    }

    private Properties getPropertiesFromResourceBundle(ResourceBundle resourceBundle) {
        Properties properties = new Properties();
        resourceBundle.keySet().forEach(f -> properties.setProperty(f, resourceBundle.getString(f)));
        return properties;
    }

    private Workspace loadWorkspace(String workspaceName) {
        Properties props = new Properties();
        InputStream is = null;
        try {
            is = new FileInputStream(new File(workspaceName + ".properties"));
        } catch (FileNotFoundException e) {
            LOG.error("Could not find file: " + workspaceName + ".properties", e);
            throw new RuntimeException(e);
        }

        try {
            props.load(is);
        } catch (IOException e) {
            LOG.error("Could not load properties from file: " + workspaceName + ".properties", e);
            throw new RuntimeException(e);
        }

        Configuration configuration = new Configuration();
        configuration.setPlansPath(props.getProperty("plansPath"));
        configuration.setRolesPath(props.getProperty("rolesPath"));
        configuration.setPluginPath(props.getProperty("pluginPath"));
        configuration.setExpressionValidatorsPath(props.getProperty("expressionValidatorsPath"));
        configuration.setMiscPath(props.getProperty("miscPath"));
        return new Workspace(workspaceName, configuration);

    }

    public String getClangFormatPath() {
        return (String) workspacesProperties.get("clangFormatPath");
    }

    public void setClangFormatPath(String clangFormatPath) {
        if (clangFormatPath == null) {
            workspacesProperties.setProperty("clangFormatPath", "");
        } else {
            workspacesProperties.setProperty("clangFormatPath", clangFormatPath);
        }
        saveWorkspacesFile();
    }

    public String getEditorExecutablePath() {
        return (String) workspacesProperties.get("editorExecutablePath");
    }

    public void setEditorExecutablePath(String editorExecutablePath) {
        if (editorExecutablePath == null) {
            workspacesProperties.setProperty("editorExecutablePath", "");
        } else {
            workspacesProperties.setProperty("editorExecutablePath", editorExecutablePath);
        }
        saveWorkspacesFile();
    }
}
