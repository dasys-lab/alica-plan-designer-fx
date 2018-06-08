package de.uni_kassel.vs.cn.planDesigner.events;

public class ResourceCreationEvent {

    protected String name;
    protected String absoluteDirectory;
    protected String type;

    public ResourceCreationEvent(String name, String absoluteDirectory, String type) {
        this.name = name;
        this.absoluteDirectory = absoluteDirectory;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getAbsoluteDirectory() {
        return absoluteDirectory;
    }
}
