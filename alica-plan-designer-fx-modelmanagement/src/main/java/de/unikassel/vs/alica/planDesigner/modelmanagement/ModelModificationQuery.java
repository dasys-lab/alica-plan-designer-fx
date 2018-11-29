package de.unikassel.vs.alica.planDesigner.modelmanagement;

import de.unikassel.vs.alica.planDesigner.events.ModelQueryType;

import java.io.File;
import java.util.Map;

public class ModelModificationQuery {

    protected ModelQueryType queryType;
    protected String absoluteDirectory;

    protected String elementType;
    protected long elementId;
    protected String name;

    protected long parentId;

    protected String attributeName;
    protected String newValue;
    protected String attributeType;
    protected String comment;
    protected Map<String, Long> relatedObjects;

    public ModelModificationQuery(ModelQueryType queryType) {
        this.queryType = queryType;
    }

    public ModelModificationQuery(ModelQueryType queryType, String absolutePath) {
        this(queryType);
        int lastSeparatorIdx = absolutePath.lastIndexOf(File.separator);
        if (lastSeparatorIdx == -1) {
            lastSeparatorIdx = 0;
        }
        this.absoluteDirectory = absolutePath.substring(0, lastSeparatorIdx);
        int lastDotIdx = absolutePath.lastIndexOf('.');
        this.name = absolutePath.substring(lastSeparatorIdx + 1, lastDotIdx);
        String ending = absolutePath.substring(lastDotIdx + 1, absolutePath.length());
        switch (ending) {
            case FileSystemUtil.BEHAVIOUR_ENDING:
                elementType = Types.BEHAVIOUR;
                break;
            case FileSystemUtil.PLAN_ENDING:
                elementType = Types.PLAN;
                break;
            case FileSystemUtil.PLANTYPE_ENDING:
                elementType = Types.PLANTYPE;
                break;
            case FileSystemUtil.TASKREPOSITORY_ENDING:
                elementType = Types.TASKREPOSITORY;
                break;
            case FileSystemUtil.PLAN_EXTENSION_ENDING:
                elementType = Types.PLAN; //TODO: Handle plan-extension-ending - Files
                break;
            default:
                System.err.println("ModelModificationQuery: Unknown ending of file " + absolutePath);
                break;
        }
    }

    public ModelModificationQuery(ModelQueryType queryType, String absoluteDirectory, String elementType, String name) {
        this(queryType);
        this.absoluteDirectory = absoluteDirectory;
        this.elementType = elementType;
        this.name = name;
    }

    public ModelModificationQuery(ModelQueryType queryType, String elementType, String name) {
        this(queryType);
        this.elementType = elementType;
        this.name = name;
    }


    public String getAbsoluteDirectory() {
        return absoluteDirectory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getElementType() {
        return elementType;
    }

    public ModelQueryType getQueryType() {
        return queryType;
    }

    public long getParentId() {
        return this.parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public long getElementId() {
        return this.elementId;
    }

    public void setElementId(long id) {
        this.elementId = id;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }

    public Map<String, Long> getRelatedObjects() {
        return relatedObjects;
    }

    public void setRelatedObjects(Map<String, Long> relatedObjects) {
        this.relatedObjects = relatedObjects;
    }

    public String getComment(){
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
