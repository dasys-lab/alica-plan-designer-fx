package de.uni_kassel.vs.cn.planDesigner.uiextensionmodel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class PositionedElement {

    protected final SimpleIntegerProperty xPos = new SimpleIntegerProperty();
    protected final SimpleIntegerProperty yPos = new SimpleIntegerProperty();

    public int getXPos() {return this.xPos.get();}

    public void setXPos(int xPos) {this.xPos.set(xPos);}

    public SimpleIntegerProperty xPosProperty() {
        return xPos;
    }

    public int getYPos() {
        return this.yPos.get();
    }

    public void setYPos(int yPos) {
        this.yPos.set(yPos);
    }

    public SimpleIntegerProperty yPosProperty() {
        return yPos;
    }
}
