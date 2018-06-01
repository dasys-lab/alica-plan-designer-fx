package de.uni_kassel.vs.cn.planDesigner.view.repo;

public class ViewModelElement {
    protected long id;
    protected String name;
    protected String type;

    public ViewModelElement(long id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
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
}
