package de.unikassel.vs.alica.planDesigner.view.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PlanElementViewModel extends ViewModelElement {

    protected final StringProperty comment = new SimpleStringProperty();

    private final IntegerProperty xPosition = new SimpleIntegerProperty();
    private final IntegerProperty yPosition = new SimpleIntegerProperty();

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

    public final IntegerProperty xPositionProperty(){
        return xPosition;
    }
    public void setXPosition(int x){
        xPosition.set(x);
    }
    public int getXPosition(){
        return xPosition.get();
    }

    public final IntegerProperty yPositionProperty() {
        return yPosition;
    }
    public void setYPosition(int y){
        yPosition.set(y);
    }
    public int getYPosition(){
        return yPosition.get();
    }



}
