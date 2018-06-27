package de.uni_kassel.vs.cn.planDesigner.modelmanagement;

import de.uni_kassel.vs.cn.planDesigner.events.ModelOperationType;

import java.io.File;

public class ModelModificationQuery {

    protected ModelOperationType operationType;
    protected String absoluteDirectory;
    protected String modelElementType;
    protected String name;

    public ModelModificationQuery(ModelOperationType operationType, String absolutePath) {
        this.operationType = operationType;
        int lastSeparatorIdx = absolutePath.lastIndexOf(File.separator);
        if (lastSeparatorIdx == -1)
            lastSeparatorIdx = 0;
        this.absoluteDirectory = absolutePath.substring(0, lastSeparatorIdx);
        int lastDotIdx = absolutePath.lastIndexOf('.');
        this.name = absolutePath.substring(lastSeparatorIdx + 1, lastDotIdx);
        String ending = absolutePath.substring(lastDotIdx + 1, absolutePath.length());
        switch (ending) {
            case FileSystemUtil.BEHAVIOUR_ENDING:
                modelElementType = Types.BEHAVIOUR;
                break;
            case FileSystemUtil.PLAN_ENDING:
                modelElementType = Types.PLAN;
                break;
            case FileSystemUtil.PLANTYPE_ENDING:
                modelElementType = Types.PLANTYPE;
                break;
            case FileSystemUtil.TASKREPOSITORY_ENDING:
                modelElementType = Types.TASKREPOSITORY;
                break;
            default:
                System.err.println("ModelModificationQuery: Unknown ending of file " + absolutePath);
                break;
        }
    }

    public ModelModificationQuery(ModelOperationType operationType, String absoluteDirectory, String modelElementType, String name) {
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
