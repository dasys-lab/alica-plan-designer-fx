package de.uni_kassel.vs.cn.planDesigner.view.model;

public class ViewModelElement {
    protected long id;
    protected String name;
    protected String type;
    protected String relativeDirectory;
    protected long parentId;

    public ViewModelElement(long id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public ViewModelElement(long id, String name, String type, String relativeDirectory) {
        this(id, name, type);
        this.relativeDirectory = relativeDirectory;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getRelativeDirectory() {
        return relativeDirectory;
    }

    public void setParentId(long id) {
        this.parentId = id;
    }

    public long getParentId() {
        return parentId;
    }

    public String toString() {
        return type + ": " + name + "(" + id + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ViewModelElement) {
            ViewModelElement otherElement = (ViewModelElement) other;
            return this.getId() == otherElement.getId() && this.getName().equals(otherElement.getName());
        } else {
            return false;
        }
    }
}
