package de.uni_kassel.vs.cn.planDesigner.configuration;

import java.io.*;
import java.nio.file.Paths;
import java.util.Properties;

public class Configuration {

    public static final String FILE_ENDING = ".properties";

    private String name;
    private Properties properties;
    private File file;

    public Configuration(String name, File planDesignerConfigFolder) {
        this.name = name;
        this.properties = new Properties();
        file = Paths.get(planDesignerConfigFolder.toString(), name+FILE_ENDING).toFile();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        file.delete();
        file = Paths.get(file.getParent(), name+FILE_ENDING).toFile();
    }

    private String replaceBashConstants(String pathWithConstants) {
        if (pathWithConstants == null) {
            return "";
        }
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
        properties.setProperty("pluginsPath", replaceBashConstants(pluginsPath));
    }

    public String getDefaultPluginName() {
        return properties.getProperty("defaultPlugin");
    }

    public void setDefaultPluginName(String pluginName) {
        if (pluginName == null) {
            properties.setProperty("defaultPlugin", "");
        } else {
            properties.setProperty("defaultPlugin", pluginName);
        }
    }

    public boolean writeToDisk() {
        if (file == null) {
            return false;
        }
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream stream = new FileOutputStream(file);
            properties.store(stream, " Plan Designer - " + name + FILE_ENDING + " configuration file");
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean loadFromDisk() {
        if (file == null || !file.exists()) {
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

    public boolean removeFromDisk() {
        if (file != null && file.exists()) {
            return file.delete();
        }
        return false;
    }
}
