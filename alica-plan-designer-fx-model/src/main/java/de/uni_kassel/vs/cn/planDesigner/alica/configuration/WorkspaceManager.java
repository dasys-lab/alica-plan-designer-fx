package de.uni_kassel.vs.cn.planDesigner.alica.configuration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by marci on 09.06.17.
 */
public class WorkspaceManager {
    private static List<Workspace> workspaces;
    private static File workspacesFile;
    private static Properties workspacesProperties;

    public void init() {
        workspaces = new ArrayList<>();
        loadWorkspaces();
    }

    public void addWorkspace(Workspace workspace) {
        workspaces.add(workspace);
        workspacesProperties.setProperty("workspaces", workspacesProperties.getProperty("workspaces") + ","+ workspace.getName());
        saveWorkspacesFile();
        saveWorkspaceConfiguration(workspace);
    }

    public void saveWorkspacesFile() {
        try {
            workspacesProperties.store(new FileOutputStream(new File("workspaces.properties")), "configuration file");
        } catch (IOException e) {
            e.printStackTrace();
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
        }
        catch (Exception e ) {
            e.printStackTrace();
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
        } catch (IOException e) {
            e.printStackTrace();
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
                e1.printStackTrace();
            }
        }

        try {
            workspacesProperties.load(is);
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
        } catch (IOException e1) {
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
            e.printStackTrace();
        }

        try {
            props.load(is);
        } catch (IOException e) {
            e.printStackTrace();
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
        workspacesProperties.setProperty("clangFormatPath", clangFormatPath);
        saveWorkspacesFile();
    }

    public String getEclipsePath() {
        return (String) workspacesProperties.get("eclipsePath");
    }

    public void setEclipsePath(String eclipsePath) {
        workspacesProperties.setProperty("eclipsePath", eclipsePath);
        saveWorkspacesFile();
    }
}
