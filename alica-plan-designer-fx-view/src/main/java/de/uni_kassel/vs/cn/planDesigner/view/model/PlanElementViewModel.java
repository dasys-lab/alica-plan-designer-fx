package de.uni_kassel.vs.cn.planDesigner.view.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PlanElementViewModel extends ViewModelElement {

    protected final StringProperty comment = new SimpleStringProperty();

    public PlanElementViewModel (long id, String name, String type) {
        super(id, name, type);
    }

    public final StringProperty commentProperty() {return comment; }
    public void setComment(String comment) {
        this.comment.setValue(comment);
    }
    public String getComment() {
        return comment.get();
    }
}
