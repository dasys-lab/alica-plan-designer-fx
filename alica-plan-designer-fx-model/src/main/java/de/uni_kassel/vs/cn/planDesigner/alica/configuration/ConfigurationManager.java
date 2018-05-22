package de.uni_kassel.vs.cn.planDesigner.alica.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public final class ConfigurationManager {

    // SINGLETON
    private static volatile ConfigurationManager instance;
    public static ConfigurationManager getInstance() {
        if (instance == null) {
            synchronized (ConfigurationManager.class) {
                if (instance == null) {
                    instance = new ConfigurationManager();
                }
            }
        }

        return instance;
    }

    private static final Logger LOG = LogManager.getLogger(ConfigurationManager.class);
    private static final String MAIN_CONFIG_FILENAME = "mainConfig.properties";
    private static final String CONFIG_FOLDERNAME = ".planDesigner";

    private Properties mainConfigProperties;
    private File mainConfigFile;

    private List<Workspace> workspaces;

    private ConfigurationManager() {
        mainConfigProperties = new Properties();
        workspaces = new ArrayList<>();

        // check for home dir
        String homeDirString = System.getProperty("user.home");
        File homeDirectory = new File(homeDirString);
        if (!homeDirectory.exists()) {
            throw new RuntimeException("Unable to find home directory!");
        }

        // check for .planDesigner in home dir
        File planDesignerConfigFolder = Paths.get(homeDirString, CONFIG_FOLDERNAME).toFile();
        if (!planDesignerConfigFolder.exists()) {
            planDesignerConfigFolder.mkdir();
        }


        mainConfigFile = Paths.get(homeDirString, CONFIG_FOLDERNAME, MAIN_CONFIG_FILENAME).toFile();
        if (!mainConfigFile.exists()) {
            System.out.println("ConfigurationManager: " + MAIN_CONFIG_FILENAME + " does not exist!");

            // load default values for mainConfig.properties
            mainConfigProperties.setProperty("clangFormatPath", "clang-format");
            mainConfigProperties.setProperty("editorExecutablePath", "gedit");
            mainConfigProperties.setProperty("workspaces", "");
            mainConfigProperties.setProperty("activeWorkspace", "");
        } else {
            // load values from mainConfig.properties file in $HOME/.planDesigner/workspaces.properties
            InputStream is = null;
            try {
                is = new FileInputStream(mainConfigFile);
                mainConfigProperties.load(is);
                is.close();
            } catch (IOException e) {
                LOG.fatal("Could not find mainConfig.properties after trying to create it.", e);
                e.printStackTrace();
            }

            String workspaceNames = mainConfigProperties.getProperty("workspaces");
            String[] split = workspaceNames.split(",");
            for (String workspaceName : split) {
                if (!workspaceName.isEmpty()) {
                    workspaces.add(loadWorkspace(workspaceName));
                }
            }
        }

        // set active workspace
        String activeWs = mainConfigProperties.getProperty("activeWorkspace");
        if (!activeWs.isEmpty()) {
            for (Workspace ws : workspaces) {
                if (ws.getName() == activeWs) {
                    setActiveWorkspace(ws);
                }
            }
        }
    }

    public void addWorkspace(Workspace workspace) {
        workspaces.add(workspace);
        mainConfigProperties.setProperty("workspaces", mainConfigProperties.getProperty("workspaces") + "," + workspace.getName());
        saveMainConfigFile();
        saveWorkspaceToFile(workspace.getName());
        LOG.info("Added new workspace " + workspace.getName());
    }

    public void saveMainConfigFile() {
        try {
            mainConfigProperties.store(new FileOutputStream(new File(MAIN_CONFIG_FILENAME)), " main configuration file");
        } catch (IOException e) {
            LOG.error("Could not save " + MAIN_CONFIG_FILENAME + "!", e);
            throw new RuntimeException(e);
        }
    }

    public void saveWorkspaceToFile(String wsName) {
        try {
            File workspaceFile = new File(wsName + ".properties");
            if (workspaceFile.exists() == false) {
                workspaceFile.createNewFile();
            }

            Configuration configuration = getWorkspace(wsName).getConfiguration();

            Properties props = new Properties();
            props.setProperty("plansPath", configuration.getPlansPath());
            props.setProperty("rolesPath", configuration.getRolesPath());
            props.setProperty("miscPath", configuration.getTasksPath());
            props.setProperty("expressionValidatorsPath", configuration.getGenSrcPath());
            props.setProperty("pluginPath", configuration.getPluginPath());
            FileOutputStream out = new FileOutputStream(workspaceFile);
            props.store(out, wsName + "configuration file");
            out.close();
        } catch (IOException e) {
            LOG.error("Could not save workspace configuration for " + wsName, e);
            throw new RuntimeException(e);
        }
    }

    public List<Workspace> getWorkspaces() {
        return workspaces;
    }

    public Workspace getWorkspace(String wsName) {
        for (Workspace ws : workspaces) {
            if (ws.getName() == wsName) {
                return ws;
            }
        }
        return null;
    }

    public Workspace getActiveWorkspace() {
        return loadWorkspace(mainConfigProperties.getProperty("activeWorkspace"));
    }

    public void setActiveWorkspace(Workspace activeWorkspace) {
        mainConfigProperties.setProperty("activeWorkspace", activeWorkspace.getName());
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
        configuration.setGenSrcPath(props.getProperty("expressionValidatorsPath"));
        configuration.setTasksPath(props.getProperty("miscPath"));
        return new Workspace(workspaceName, configuration);
    }

    public String getClangFormatPath() {
        return (String) mainConfigProperties.get("clangFormatPath");
    }

    public void setClangFormatPath(String clangFormatPath) {
        if (clangFormatPath == null) {
            mainConfigProperties.setProperty("clangFormatPath", "");
        } else {
            mainConfigProperties.setProperty("clangFormatPath", clangFormatPath);
        }
        saveMainConfigFile();
    }

    public String getEditorExecutablePath() {
        return (String) mainConfigProperties.get("editorExecutablePath");
    }

    public void setEditorExecutablePath(String editorExecutablePath) {
        if (editorExecutablePath == null) {
            mainConfigProperties.setProperty("editorExecutablePath", "");
        } else {
            mainConfigProperties.setProperty("editorExecutablePath", editorExecutablePath);
        }
        saveMainConfigFile();
    }
}
