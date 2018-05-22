package de.uni_kassel.vs.cn.planDesigner.alica.configuration;

import java.io.File;

public class Configuration {

    private String rolesPath;

    private String plansPath;

    /**
     * Path to generated source code
     */
    private String genSrcPath;

    private String tasksPath;

    private String pluginPath;

    private String defaultPluginName;

    public Configuration() {
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
        return rolesPath;
    }

    public void setRolesPath(String rolesPath) {
        this.rolesPath = replaceBashConstants(rolesPath);
    }

    public String getPlansPath() {
        return plansPath;
    }

    public void setPlansPath(String plansPath) {
        this.plansPath = replaceBashConstants(plansPath);
    }

    public String getGenSrcPath() {
        return genSrcPath;
    }

    public void setGenSrcPath(String genSrcPath) {
        this.genSrcPath = replaceBashConstants(genSrcPath);
    }

    public String getTasksPath() {
        return tasksPath;
    }

    public void setTasksPath(String tasksPath) {
        this.tasksPath = replaceBashConstants(tasksPath);
    }

    public String getPluginPath() {
        return pluginPath;
    }

    public void setPluginPath(String pluginPath) {
        this.pluginPath = replaceBashConstants(pluginPath);
    }

    public String getDefaultPlugin() {
        return defaultPluginName;
    }

    public void setDefaultPlugin(String pluginName) {
        defaultPluginName = pluginName;
    }
}
