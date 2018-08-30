package de.uni_kassel.vs.cn.planDesigner.alicamodel;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javafx.beans.property.*;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class PlanElement {
    public static final String forbiddenCharacters = ".*[\\./\\*\\\\$§?\\[\\]!{}\\-äüö#\"%~'ÄÖÜß@,]+.*";
    protected static int PLAN_ELEMENT_COUNTER = 0;
    protected  long id;
    protected  SimpleStringProperty name;
    protected  SimpleStringProperty comment;
    @JsonIgnore
    protected SimpleBooleanProperty dirty = new SimpleBooleanProperty();

    public PlanElement() {
        this.id = generateId();
        this.name = new SimpleStringProperty();
        this.comment = new SimpleStringProperty();
    }

    public PlanElement(long id) {
        this.id = id;
        this.name = new SimpleStringProperty();
        this.comment = new SimpleStringProperty();
    }

    public long getId() {
        return id;
    }

    protected long generateId() {
        return System.currentTimeMillis() + PLAN_ELEMENT_COUNTER++;
    }

    public String getName() {
        if (name.get() == null || name.get().isEmpty())
        {
            return Long.toString(id);
        }
        return name.get();
    }

    public void setName(String name) {
        if (name.matches(forbiddenCharacters)) {
            name.replaceAll(forbiddenCharacters, "");
        } else {
            this.name.set(name);
        }
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getComment() {
        return comment.get();
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }

    public SimpleStringProperty commentProperty() {
        return comment;
    }

    public void setDirty(boolean dirty) {
        this.dirty.set(dirty);
    }

    public boolean getDirty() {
        return dirty.get();
    }

    public SimpleBooleanProperty dirtyProperty() {
        return dirty;
    }
}
