package de.uni_kassel.vs.cn.planDesigner.alicamodel;

public class PlanElement {
    protected long id;
    protected String name;
    protected String comment;
    protected boolean dirty;

    public static final String forbiddenCharacters = ".*[\\./\\*\\\\$§?\\[\\]!{}\\-äüö#\"%~'ÄÖÜß@]+.*";

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public boolean setName(String name) {
        if (name.matches(forbiddenCharacters)) {
            return false;
        } else {
            this.name = name;
            return true;
        }
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public boolean getDirty() {
        return dirty;
    }
}
