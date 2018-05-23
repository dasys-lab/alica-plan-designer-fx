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
    private static final String ACTIVE_DOMAIN_CONF = "activeDomainConfig";
    private static final String DOMAIN_CONFIGS = "domainConfigs";
    private static final String CLANG_FORMAT_PATH = "clangFormatPath";
    private static final String EDITOR_EXEC_PATH = "editorExecutablePath";

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

            // loadFromDisk default values for mainConfig.properties
            mainConfigProperties.setProperty(CLANG_FORMAT_PATH, "clang-format");
            mainConfigProperties.setProperty(EDITOR_EXEC_PATH, "gedit");
            mainConfigProperties.setProperty(DOMAIN_CONFIGS, "");
            mainConfigProperties.setProperty(ACTIVE_DOMAIN_CONF, "");
        } else {
            // loadFromDisk values from mainConfig.properties file in $HOME/.planDesigner/configurations.properties
            try {
                InputStream is = new FileInputStream(mainConfigFile);
                mainConfigProperties.load(is);
                is.close();
            } catch (IOException e) {
                LOG.fatal("Could not loadFromDisk " + mainConfigFile.toString() + " although it exists.", e);
                e.printStackTrace();
            }

            String workspaceNames = mainConfigProperties.getProperty(DOMAIN_CONFIGS);
            String[] split = workspaceNames.split(",");
            for (String workspaceName : split) {
                if (!workspaceName.isEmpty()) {
                    configurations.add(new Configuration(workspaceName));
                }
            }
        }

        // set active workspace
        String activeConfiguration = mainConfigProperties.getProperty(ACTIVE_DOMAIN_CONF);
        if (!activeConfiguration.isEmpty()) {
            for (Configuration conf : configurations) {
                if (activeConfiguration.equals(conf.getName())) {
                    mainConfigProperties.setProperty(ACTIVE_DOMAIN_CONF, activeConfiguration);
                }
            }
        }
    }

    public void writeToDisk()
    {
        saveMainConfigFile();
        for (Configuration conf : configurations)
        {
            conf.writeToDisk();
        }
    }

    private void saveMainConfigFile() {
        try {
            mainConfigProperties.store(new FileOutputStream(mainConfigFile), " Plan Designer - main configuration file");
        } catch (IOException e) {
            LOG.error("Could not save " + mainConfigFile.toString() + "!", e);
            throw new RuntimeException(e);
        }
    }

    // CONFIGURATION MANAGEMENT SECTION

    public List<String> getConfigurationNames() {
        List<String> configurationNames = new ArrayList<String>();
        for (Configuration conf : configurations)
        {
            configurationNames.add(conf.getName());
        }
        return configurationNames;
    }

    public boolean addConfiguration(String confName)
    {
        for (Configuration conf : configurations) {
            if (conf.getName().equals(confName)) {
                return false;
            }
        }

        configurations.add(new Configuration(confName));
        String domainConfigs = mainConfigProperties.getProperty(DOMAIN_CONFIGS);
        if (domainConfigs == null || domainConfigs.isEmpty())
        {
            mainConfigProperties.setProperty(DOMAIN_CONFIGS, confName);
        }
        else
        {
            mainConfigProperties.setProperty(DOMAIN_CONFIGS, domainConfigs + "," + confName);
        }
        LOG.info("Added new configuration " + confName);
        return true;
    }

    public boolean renameConfiguration(String oldConfName, String newConfName) {
        for (Configuration conf : configurations) {
            if (conf.getName().equals(oldConfName)) {
                conf.setName(newConfName);
                return true;
            }
        }
        return false;
    }

    public boolean removeConfiguration(String confName) {
        for (Configuration conf : configurations) {
            if (conf.getName().equals(confName)) {
                conf.removeFromDisk();
                return configurations.remove(conf);
            }
        }
        return false;
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
        String activeWsName = mainConfigProperties.getProperty(ACTIVE_DOMAIN_CONF);
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

    // EXTERNAL TOOLS SECTION

    public String getClangFormatPath() {
        return mainConfigProperties.getProperty(CLANG_FORMAT_PATH);
    }

    public void setClangFormatPath(String clangFormatPath) {
        if (clangFormatPath == null) {
            mainConfigProperties.setProperty(CLANG_FORMAT_PATH, "");
        } else {
            mainConfigProperties.setProperty(CLANG_FORMAT_PATH, clangFormatPath);
        }
    }

    public String getEditorExecutablePath() {
        return mainConfigProperties.getProperty(EDITOR_EXEC_PATH);
    }

    public void setEditorExecutablePath(String editorExecutablePath) {
        if (editorExecutablePath == null) {
            mainConfigProperties.setProperty(EDITOR_EXEC_PATH, "");
        } else {
            mainConfigProperties.setProperty(EDITOR_EXEC_PATH, editorExecutablePath);
        }
    }
}
