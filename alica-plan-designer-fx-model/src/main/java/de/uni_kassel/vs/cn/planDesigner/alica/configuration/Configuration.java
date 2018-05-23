package de.uni_kassel.vs.cn.planDesigner.alica.configuration;

import java.io.*;
import java.util.Properties;

public class Configuration {

    public static final String FILE_ENDING = ".properties";

    private String name;
    private Properties properties;
    private File file;

    public Configuration(String name) {
        this.name = name;
        this.properties = new Properties();
    }

    public Configuration(String name, Properties properties) {
        this.name = name;
        this.properties = properties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String replaceBashConstants(String pathWithConstants) {
        try {
            String bashConstant = pathWithConstants
                    .substring(pathWithConstants.indexOf("$"), pathWithConstants.indexOf(File.separator));
            String bashConstantValue = System.getenv().get(bashConstant.substring(1));
            return pathWithConstants.replace(bashConstant, bashConstantValue);
        } catch (Exception e) {
            return pathWithConstants;
        }
    }

    public String getRolesPath() {
        return properties.getProperty("rolesPath");
    }

    public void setRolesPath(String rolesPath) {
        properties.setProperty("rolesPath", replaceBashConstants(rolesPath));
    }

    public String getPlansPath() {
        return properties.getProperty("plansPath");
    }

    public void setPlansPath(String plansPath) {
        properties.setProperty("plansPath", replaceBashConstants(plansPath));
    }

    public String getGenSrcPath() {
        return properties.getProperty("genSrcPath");
    }

    public void setGenSrcPath(String genSrcPath) {
        properties.setProperty("genSrcPath", replaceBashConstants(genSrcPath));
    }

    public String getTasksPath() {
        return properties.getProperty("tasksPath");
    }

    public void setTasksPath(String tasksPath) {
        properties.setProperty("tasksPath", replaceBashConstants(tasksPath));
    }

    public String getPluginsPath() {
        return properties.getProperty("pluginsPath");
    }

    public void setPluginsPath(String pluginsPath) {
        properties.setProperty("tasksPath", replaceBashConstants(pluginsPath));
    }

    public String getDefaultPluginName() {
        return properties.getProperty("defaultPluginName");
    }

    public void setDefaultPluginName(String pluginName) {
        properties.setProperty("defaultPlugin", pluginName);
    }

    public void setFile(File file) {
        file = file;
    }

    public boolean store() {
        if (file == null)
        {
            return false;
        }
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream stream = new FileOutputStream(file);
            properties.store(stream, name + FILE_ENDING + " configuration file");
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean load() {
        if (file == null || !file.exists())
        {
            return false;
        }

        FileInputStream stream = null;
        try {
            stream = new FileInputStream(file);
            properties.load(stream);
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
