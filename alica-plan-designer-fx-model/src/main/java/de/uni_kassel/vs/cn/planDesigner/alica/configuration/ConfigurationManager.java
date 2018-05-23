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
    private static final String MAIN_CONFIG_FILENAME = "mainConfig";

    /**
     * Not the domain config folder (etc). Its for plan designer only.
     */
    private static final String CONFIG_FOLDERNAME = ".planDesigner";

    private Properties mainConfigProperties;
    private File mainConfigFile;
    private File planDesignerConfigFolder;

    private List<Configuration> configurations;

    private ConfigurationManager() {
        mainConfigProperties = new Properties();
        configurations = new ArrayList<>();

        // check for home dir
        String homeDirString = System.getProperty("user.home");
        File homeDirectory = new File(homeDirString);
        if (!homeDirectory.exists()) {
            throw new RuntimeException("Unable to find home directory!");
        }

        // check for .planDesigner in home dir
        planDesignerConfigFolder = Paths.get(homeDirString, CONFIG_FOLDERNAME).toFile();
        if (!planDesignerConfigFolder.exists()) {
            if (!planDesignerConfigFolder.mkdir()) {
                throw new RuntimeException("Unable to create " + CONFIG_FOLDERNAME + " in home directory!");
            }
        }


        mainConfigFile = Paths.get(planDesignerConfigFolder.toString(), MAIN_CONFIG_FILENAME + Configuration.FILE_ENDING).toFile();
        if (!mainConfigFile.exists()) {
            LOG.info(mainConfigFile.toString() + " does not exist!");

            // load default values for mainConfig.properties
            mainConfigProperties.setProperty("clangFormatPath", "clang-format");
            mainConfigProperties.setProperty("editorExecutablePath", "gedit");
            mainConfigProperties.setProperty("workspaces", "");
            mainConfigProperties.setProperty("activeWorkspace", "");
        } else {
            // load values from mainConfig.properties file in $HOME/.planDesigner/configurations.properties
            try {
                InputStream is = new FileInputStream(mainConfigFile);
                mainConfigProperties.load(is);
                is.close();
            } catch (IOException e) {
                LOG.fatal("Could not load " + mainConfigFile.toString() + " although it exists.", e);
                e.printStackTrace();
            }

            String workspaceNames = mainConfigProperties.getProperty("workspaces");
            String[] split = workspaceNames.split(",");
            for (String workspaceName : split) {
                if (!workspaceName.isEmpty()) {
                    configurations.add(new Configuration(workspaceName));
                }
            }
        }

        // set active workspace
        String activeWs = mainConfigProperties.getProperty("activeWorkspace");
        if (!activeWs.isEmpty()) {
            for (Configuration ws : configurations) {
                if (activeWs.equals(ws.getName())) {
                    setActiveConfiguration(ws);
                }
            }
        }
    }

    public void addConfiguration(Configuration configuration) {
        configurations.add(configuration);
        mainConfigProperties.setProperty("workspaces", mainConfigProperties.getProperty("workspaces") + "," + configuration.getName());
        saveMainConfigFile();
        if (!configuration.store())
        {
            LOG.error("Unable so save configuration " + configuration.getName());
        }
        LOG.info("Added new configuration " + configuration.getName());
    }

    private void saveMainConfigFile() {
        try {
            mainConfigProperties.store(new FileOutputStream(mainConfigFile), " main configuration file");
        } catch (IOException e) {
            LOG.error("Could not save " + mainConfigFile.toString() + "!", e);
            throw new RuntimeException(e);
        }
    }

    public List<Configuration> getConfigurations() {
        return configurations;
    }

    public Configuration getConfiguration(String confName) {
        for (Configuration conf : configurations) {
            if (conf.getName().equals(confName)) {
                return conf;
            }
        }
        return null;
    }

    public Configuration getActiveConfiguration() {
        String activeWsName = mainConfigProperties.getProperty("activeWorkspace");
        if (activeWsName.isEmpty()) {
            return null;
        }

        for (Configuration conf : configurations) {
            if (conf.getName().equals(activeWsName)) {
                return conf;
            }
        }
        return null;
    }

    private void setActiveConfiguration(Configuration activeConfiguration) {
        System.out.println("Configuration: " + activeConfiguration.getName());
        mainConfigProperties.setProperty("activeWorkspace", activeConfiguration.getName());
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
