package de.uni_kassel.vs.cn.planDesigner.alica.configuration;

/**
 * Created by marci on 09.06.17.
 */
public class Workspace {

    private String name;
    private Configuration configuration;

    public Workspace(String name, Configuration configuration) {
        this.name = name;
        this.configuration = configuration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
