package de.uni_kassel.vs.cn.planDesigner.modelmanagement;

import de.uni_kassel.vs.cn.planDesigner.events.ModelOperationType;

public class ModelModificationQuery {

    protected ModelOperationType operationType;
    protected String absoluteDirectory;
    protected String modelElementType;
    protected String name;

    public ModelModificationQuery (ModelOperationType operationType, String absoluteDirectory, String modelElementType, String name) {
        this.operationType = operationType;
        this.absoluteDirectory = absoluteDirectory;
        this.modelElementType = modelElementType;
        this.name = name;
    }

    public String getAbsoluteDirectory() {
        return absoluteDirectory;
    }

    public String getName() {
        return name;
    }

    public String getModelElementType() {
        return modelElementType;
    }

    public ModelOperationType getOperationType() {
        return operationType;
    }
}
