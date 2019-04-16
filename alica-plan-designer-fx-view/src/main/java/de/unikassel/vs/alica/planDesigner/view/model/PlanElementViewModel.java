package de.unikassel.vs.alica.planDesigner.view.model;

import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Arrays;

public class PlanElementViewModel extends ViewModelElement {

    protected final SimpleStringProperty name = new SimpleStringProperty(null, "name", "");
    protected final SimpleStringProperty comment = new SimpleStringProperty(null, "comment", "");

    private final SimpleIntegerProperty xPosition = new SimpleIntegerProperty(null, "xPosition", 0);
    private final SimpleIntegerProperty yPosition = new SimpleIntegerProperty(null, "yPosition", 0);

    public PlanElementViewModel (long id, String name, String type) {
        super(id, name, type);

        this.uiPropertyList.clear();
        this.uiPropertyList.addAll(Arrays.asList("name", "id", "comment", "relativeDirectory"));
    }

    public void registerListener(IGuiModificationHandler handler) {
        super.registerListener(handler);
        comment.addListener((observable, oldValue, newValue) -> {
            fireGUIAttributeChangeEvent(handler, newValue, comment.getClass().getSimpleName(), comment.getName());
        });
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
