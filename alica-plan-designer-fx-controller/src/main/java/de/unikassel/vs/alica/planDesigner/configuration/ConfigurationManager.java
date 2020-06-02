package de.unikassel.vs.alica.planDesigner.configuration;

import de.unikassel.vs.alica.generator.plugin.PluginManager;
import de.unikassel.vs.alica.planDesigner.controller.Controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public final class ConfigurationManager {



    //--------------------------------------------------------------------------------------------
    //  SINGLETON
    //--------------------------------------------------------------------------------------------
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

    //--------------------------------------------------------------------------------------------
    //  CONSTANTS
    //--------------------------------------------------------------------------------------------
    private static final Logger LOG = LogManager.getLogger(ConfigurationManager.class);

    //---- CONFIGFOLDER ----
    private static final String CONFIG_FOLDERNAME = ".planDesigner";

    //---- MAINCONFIG ----
    private static final String MAIN_CONFIG_FILENAME = "mainConfig";

    //-- PROPERTIES --
    private static final String ACTIVE_DOMAIN_CONF = "activeDomainConfig";
    private static final String DOMAIN_CONFIGS = "domainConfigs";
    private static final String CLANG_FORMAT_PATH = "clangFormatPath";
    private static final String EDITOR_EXEC_PATH = "editorExecutablePath";
    private static final String ALICA_ENGINE_PATH = "alicaEngineExecutablePath";

    //---- WINDOWCONFIG ----
    private static final String WINDOW_CONFIG_FILENAME = "windowConfig";

    //-- PROPERTIES --
    private static final String WINDOW_HEIGHT = "height";
    private static final String WINDOW_WIDTH = "width";
    private static final String WINDOW_X = "x";
    private static final String WINDOW_Y = "y";


    //--------------------------------------------------------------------------------------------
    //  FIELDS
    //--------------------------------------------------------------------------------------------
    private Controller controller;

    //---- CONFIGFOLDER ----
    private File planDesignerConfigFolder;

    //---- MAINCONFIG ----
    private Properties mainConfigProperties;
    private File mainConfigFile;
    private List<Configuration> configurations;
    private Configuration activeConfiguration;

    //---- WINDOWCONFIG ----
    private Properties windowConfigProperties;
    private File windowConfigFile;


    //--------------------------------------------------------------------------------------------
    //  CONSTRUCTOR
    //--------------------------------------------------------------------------------------------
    private ConfigurationManager() {
        mainConfigProperties = new Properties();
        windowConfigProperties = new Properties();
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

        //---- MAINCONFIG ----
        mainConfigFile = Paths.get(planDesignerConfigFolder.toString(), MAIN_CONFIG_FILENAME + Configuration.FILE_ENDING).toFile();
        if (!mainConfigFile.exists()) {
            LOG.info(mainConfigFile.toString() + " does not exist!");

            // loadFromDisk default values for mainConfig.properties
            mainConfigProperties.setProperty(CLANG_FORMAT_PATH, "clang-format");
            mainConfigProperties.setProperty(EDITOR_EXEC_PATH, "gedit");
            mainConfigProperties.setProperty(ALICA_ENGINE_PATH, "../devel/lib/test_robots_runner/runner");
            mainConfigProperties.setProperty(DOMAIN_CONFIGS, "");
            mainConfigProperties.setProperty(ACTIVE_DOMAIN_CONF, "");
            generateDefaultConfiguration();
        } else {
            try {
                InputStream is = new FileInputStream(mainConfigFile);
                mainConfigProperties.load(is);
                is.close();
            } catch (IOException e) {
                LOG.fatal("Could not loadFromDisk " + mainConfigFile.toString() + " although it exists.", e);
                e.printStackTrace();
            }

            String domainNames = mainConfigProperties.getProperty(DOMAIN_CONFIGS);
            String[] split = domainNames.split(",");
            for (String domainName : split) {
                if (!domainName.isEmpty()) {
                    Configuration conf = new Configuration(domainName, planDesignerConfigFolder);
                    if (conf.loadFromDisk()) {
                        configurations.add(conf);
                    } else {
                        LOG.error("Could not loadFromDisk " + conf.getName() + ".");
                        generateDefaultConfiguration();
                    }
                }
            }
        }

        //---- WINDOWCONFIG ----
        windowConfigFile = Paths.get(planDesignerConfigFolder.toString(), WINDOW_CONFIG_FILENAME + Configuration.FILE_ENDING).toFile();
        if (!windowConfigFile.exists()) {
            LOG.info(windowConfigFile.toString() + " does not exist!");

            // loadFromDisk default values for mainConfig.properties
            windowConfigProperties.setProperty(WINDOW_HEIGHT, "600");
            windowConfigProperties.setProperty(WINDOW_WIDTH, "800");
            windowConfigProperties.setProperty(WINDOW_X, "0");
            windowConfigProperties.setProperty(WINDOW_Y, "0");
        } else {
            try {
                InputStream is = new FileInputStream(windowConfigFile);
                windowConfigProperties.load(is);
                is.close();
            } catch (IOException e) {
                LOG.fatal("Could not loadFromDisk " + windowConfigFile.toString() + " although it exists.", e);
                e.printStackTrace();
            }
        }

        // set active configuration, if configured
        setActiveConfiguration(mainConfigProperties.getProperty(ACTIVE_DOMAIN_CONF));
    }

    private void generateDefaultConfiguration() {
        JFileChooser xFileChooser = new JFileChooser(  );
        xFileChooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
        xFileChooser.setMultiSelectionEnabled( false );
        xFileChooser.setDialogTitle("Choose a Folder, to generate default start Configuration!");
        xFileChooser.showOpenDialog( null );

        if(xFileChooser.getSelectedFile() != null) {
            String configurationName = "DefaultConfiguration";
            mainConfigProperties.setProperty(ACTIVE_DOMAIN_CONF, configurationName);
            mainConfigProperties.setProperty(DOMAIN_CONFIGS, configurationName);

            Path currentRelativePath = Paths.get("");
            String plugInPath = currentRelativePath.toAbsolutePath().toString() + "/alica-plan-designer-fx-default-plugin/target";

            File planFile = new File(String.valueOf(xFileChooser.getSelectedFile())+ "/plan");
            if (!planFile.exists()) planFile.mkdirs();

            File taskFile = new File(String.valueOf(xFileChooser.getSelectedFile())+ "/task");
            if (!taskFile.exists()) taskFile.mkdirs();

            File roleFile = new File(String.valueOf(xFileChooser.getSelectedFile())+ "/role");
            if (!roleFile.exists()) roleFile.mkdirs();

            File genFile = new File(String.valueOf(xFileChooser.getSelectedFile())+ "/autogenerated");
            if (!genFile.exists()) genFile.mkdirs();

            Configuration newConf = new Configuration("DefaultConfiguration", planDesignerConfigFolder);
            newConf.setPluginsPath(plugInPath);
            newConf.setDefaultPluginName("DefaultPlugin");
            newConf.setPlansPath(planFile.getPath());
            newConf.setTasksPath(taskFile.getPath());
            newConf.setRolesPath(roleFile.getPath());
            newConf.setGenSrcPath(genFile.getPath());
            newConf.setName(configurationName);

            configurations.add(newConf);
            try {
                mainConfigProperties.store(new FileOutputStream(mainConfigFile), " Plan Designer - main configuration file");
                newConf.writeToDisk();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void writeToDisk() {
        saveMainConfigFile();
        for (Configuration conf : configurations) {
            conf.writeToDisk();
        }
    }

    private void saveMainConfigFile() {
        try {
            // available domain configs
            String domainConfigs = "";
            for (Configuration conf : configurations) {
                domainConfigs += conf.getName() + ",";
            }
            if (!domainConfigs.isEmpty()) {
                domainConfigs = domainConfigs.substring(0, domainConfigs.length() - 1);
            }
            mainConfigProperties.setProperty(DOMAIN_CONFIGS, domainConfigs);

            // active config
            if (activeConfiguration == null) {
                mainConfigProperties.setProperty(ACTIVE_DOMAIN_CONF, "");
            } else {
                mainConfigProperties.setProperty(ACTIVE_DOMAIN_CONF, activeConfiguration.getName());
            }

            // actual write to file
            mainConfigProperties.store(new FileOutputStream(mainConfigFile), " Plan Designer - main configuration file");
        } catch (IOException e) {
            LOG.error("Could not save " + mainConfigFile.toString() + "!", e);
            throw new RuntimeException(e);
        }
    }

    // CONFIGURATION MANAGEMENT SECTION

    public List<String> getConfigurationNames() {
        List<String> configurationNames = new ArrayList<>();
        for (Configuration conf : configurations) {
            configurationNames.add(conf.getName());
        }
        return configurationNames;
    }

    public boolean addConfiguration(String confName) {
        for (Configuration conf : configurations) {
            if (conf.getName().equals(confName)) {
                return false;
            }
        }

        configurations.add(new Configuration(confName, planDesignerConfigFolder));
        LOG.info("Added new configuration " + confName);
        return true;
    }

    public boolean renameConfiguration(String oldConfName, String newConfName) {
        for (Configuration conf : configurations) {
            if (conf.getName().equals(oldConfName)) {
                conf.setName(newConfName);
                LOG.info("Renamed configuration " + oldConfName + " to " + newConfName);
                return true;
            }
        }
        return false;
    }

    public boolean removeConfiguration(String confName) {
        for (Configuration conf : configurations) {
            if (conf.getName().equals(confName)) {
                conf.removeFromDisk();
                configurations.remove(conf);
                LOG.info("Removed configuration " + confName);
                return true;
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
        return activeConfiguration;
    }

    public void updateConfigurationForGui(String confName){
        if (confName != null && !confName.isEmpty() && activeConfiguration != null && activeConfiguration.getName().equals(confName)) {
            for (Configuration conf : configurations) {
                if (conf.getName().equals(confName)) {
                    activeConfiguration = conf;
                    PluginManager.getInstance().updateAvailablePlugins(conf.getPluginsPath());
                    PluginManager.getInstance().setDefaultPlugin(conf.getDefaultPluginName());
                    if (controller != null) {
                        controller.handleConfigurationChanged();
                    }
                }
            }
        }
    }

    public boolean checkActive(String confName) {
        if(activeConfiguration.getName().equals(confName)){
            return true;
        }
        return false;
    }

    public boolean setActiveConfiguration(String confName) {
        if (activeConfiguration != null && activeConfiguration.getName().equals(confName)) {
            LOG.info("Active configuration already set to " + confName);
            return false;
        }
        if (confName != null && !confName.isEmpty()) {
            for (Configuration conf : configurations) {
                if (conf.getName().equals(confName)) {
                    activeConfiguration = conf;
                    PluginManager.getInstance().updateAvailablePlugins(conf.getPluginsPath());
                    PluginManager.getInstance().setDefaultPlugin(conf.getDefaultPluginName());
                    if (controller != null) {
                        controller.handleConfigurationChanged();
                    }
                    LOG.info("Set active configuration to " + confName);
                    return true;
                }
            }
        }
        LOG.error("No configuration with the name '" + confName + "' found!");
        activeConfiguration = null;
        return false;
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

    public String getAlicaEnginePath() {
        return mainConfigProperties.getProperty(ALICA_ENGINE_PATH);
    }

    public void setAlicaEnginePath(String alicaEnginePath) {
        if (alicaEnginePath == null) {
            mainConfigProperties.setProperty(ALICA_ENGINE_PATH, "");
        } else {
            mainConfigProperties.setProperty(ALICA_ENGINE_PATH, alicaEnginePath);
        }
    }

    // WINDOW TOOLS
    public void setWindowPreferences(Double height, Double width, Double x, Double y) {
        windowConfigProperties.setProperty(WINDOW_HEIGHT, String.valueOf(height));
        windowConfigProperties.setProperty(WINDOW_WIDTH, String.valueOf(width));
        windowConfigProperties.setProperty(WINDOW_X, String.valueOf(x));
        windowConfigProperties.setProperty(WINDOW_Y, String.valueOf(y));
    }

    public void saveWindowPreferences(Double height, Double width, Double x, Double y) {
        setWindowPreferences(height, width, x, y);
        try {
            // actual write to file
            windowConfigProperties.store(new FileOutputStream(windowConfigFile), " Plan Designer - window configuration file");
        } catch (IOException e) {
            LOG.error("Could not save " + windowConfigFile.toString() + "!", e);
            throw new RuntimeException(e);
        }
    }

    public Properties getWindowPreferences() {
        return windowConfigProperties;
    }

    public File getPlanDesignerConfigFolder() {
        return planDesignerConfigFolder;
    }
}
