package de.unikassel.vs.alica.planDesigner.events;

import java.util.HashMap;

public class ConfigEvent {

    //--------------------------------------------------------------------------------------------
    //  CONSTANTS
    //--------------------------------------------------------------------------------------------
    public static final String WINDOW_SETTINGS = "window";

    //--------------------------------------------------------------------------------------------
    //  FIELDS
    //--------------------------------------------------------------------------------------------
    private String type;
    private HashMap<String, Double> parameters;

    //--------------------------------------------------------------------------------------------
    //  CONSTRUCTORS
    //--------------------------------------------------------------------------------------------
    public ConfigEvent(String type) {
        this.type = type;
    }

    public ConfigEvent(String type, HashMap<String, Double> parameters) {
        this.type = type;
        this.parameters = parameters;
    }

    //--------------------------------------------------------------------------------------------
    //  GETTER
    //--------------------------------------------------------------------------------------------
    public String getType() {
        return type;
    }

    public HashMap<String, Double> getParameters() {
        return parameters;
    }

    //--------------------------------------------------------------------------------------------
    //  SETTER
    //--------------------------------------------------------------------------------------------
    public void setType(String type) {
        this.type = type;
    }



    public void setParameters(HashMap<String, Double> parameters) {
        this.parameters = parameters;
    }
}
