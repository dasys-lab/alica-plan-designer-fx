package de.uni_kassel.vs.cn.planDesigner.alica;

import java.util.ArrayList;

public class Role extends PlanElement {

    protected ArrayList<Characteristic> characteristics;

    public Role() {
        characteristics = new ArrayList<>();
    }

    public ArrayList<Characteristic> getCharacteristics() {
        return this.characteristics;
    }

}
