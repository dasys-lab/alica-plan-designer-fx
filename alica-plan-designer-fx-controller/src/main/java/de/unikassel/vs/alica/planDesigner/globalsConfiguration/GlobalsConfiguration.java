package de.unikassel.vs.alica.planDesigner.globalsConfiguration;

import de.unikassel.vs.alica.planDesigner.controller.GlobalsConfWindowController;

import java.util.ArrayList;
import java.util.List;

public class GlobalsConfiguration {
    private List<GlobalsConfWindowController> listOfTeams= new ArrayList<>();

    // ---- Team ----
    private String activeGlobals;
    private Double diameterRobot;

    // ---- TeamDefault ----
    private String defaultRole;
    private String speed;

    // ---- RolePriority ----
    private Integer assistent;

    // ---- Getter & Setter ----

    public List<GlobalsConfWindowController> getListOfTeams() {
        return listOfTeams;
    }

    public void setListOfTeams(List<GlobalsConfWindowController> listOfTeams) {
        this.listOfTeams = listOfTeams;
    }

    public String getActiveGlobals() {
        return activeGlobals;
    }

    public void setActiveGlobals(String activeGlobals) {
        this.activeGlobals = activeGlobals;
    }

    public Double getDiameterRobot() {
        return diameterRobot;
    }

    public void setDiameterRobot(Double diameterRobot) {
        this.diameterRobot = diameterRobot;
    }

    public String getDefaultRole() {
        return defaultRole;
    }

    public void setDefaultRole(String defaultRole) {
        this.defaultRole = defaultRole;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public Integer getAssistent() {
        return assistent;
    }

    public void setAssistent(Integer assistent) {
        this.assistent = assistent;
    }

    public void setDefaultConfiguration() {
        diameterRobot = 500.0;
        defaultRole = "Assistant";
        speed = "Fast";
        assistent = 1;
        listOfTeams.clear();
        listOfTeams.add(new GlobalsConfWindowController(1, "donatello", "Assistant", "Fast"));
        listOfTeams.add(new GlobalsConfWindowController(2, "leonardo", "Assistant", "Fast"));
        listOfTeams.add(new GlobalsConfWindowController(3, "raphael", "Assistant", "Fast"));
        listOfTeams.add(new GlobalsConfWindowController(4, "workpc", "Assistant", "Fast"));
        listOfTeams.add(new GlobalsConfWindowController(5, "homepc", "Assistant", "Fast"));
    }
}
