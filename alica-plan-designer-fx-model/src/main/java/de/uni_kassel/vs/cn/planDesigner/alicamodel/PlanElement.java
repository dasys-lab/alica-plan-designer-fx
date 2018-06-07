package de.uni_kassel.vs.cn.planDesigner.alicamodel;

public class PlanElement {
    public static final String forbiddenCharacters = ".*[\\./\\*\\\\$§?\\[\\]!{}\\-äüö#\"%~'ÄÖÜß@]+.*";
    protected static int PLAN_ELEMENT_COUNTER = 0;
    protected long id;
    protected String name;
    protected String comment;
    protected boolean dirty;

    public PlanElement() {
        this.id = generateId();
    }

    public long getId() {
        return id;
    }

    protected long generateId() {
        return System.currentTimeMillis() + PLAN_ELEMENT_COUNTER++;
    }

    public String getName() {
        if (name.isEmpty())
        {
            return Long.toString(id);
        }
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
