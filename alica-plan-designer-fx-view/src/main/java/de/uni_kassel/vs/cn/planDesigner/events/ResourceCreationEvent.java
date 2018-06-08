package de.uni_kassel.vs.cn.planDesigner.events;

public class ResourceCreationEvent {

    protected String absoluteDirectory;
    protected String type;
    protected String name;

    public ResourceCreationEvent(String absoluteDirectory, String type, String name) {
        this.absoluteDirectory = absoluteDirectory;
        this.type = type;
        this.name = name;
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
