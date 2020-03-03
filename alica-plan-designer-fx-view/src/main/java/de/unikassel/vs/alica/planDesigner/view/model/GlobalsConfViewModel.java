package de.unikassel.vs.alica.planDesigner.view.model;

import de.unikassel.vs.alica.planDesigner.controller.GlobalsConfWindowController;

import java.util.ArrayList;
import java.util.List;

public class GlobalsConfViewModel {
    private List<GlobalsConfWindowController> listOfTeamsViewModel= new ArrayList<>();
    // ---- Team ----
    private String activeGlobalsViewModel;
    private Double diameterRobotViewModel;


    // ---- TeamDefault ----
    private String defaultRoleViewModel;
    private String speedViewModel;

    // ---- RolePriority ----
    private Integer assistentViewModel;


    // ---- Getter & Setter ----
    public List<GlobalsConfWindowController> getListOfTeamsViewModel() {
        return listOfTeamsViewModel;
    }

    public void setListOfTeamsViewModel(List<GlobalsConfWindowController> listOfTeamsViewModel) {
        this.listOfTeamsViewModel = listOfTeamsViewModel;
    }

    public String getActiveGlobalsViewModel() {
        return activeGlobalsViewModel;
    }

    public void setActiveGlobalsViewModel(String activeGlobalsViewModel) {
        this.activeGlobalsViewModel = activeGlobalsViewModel;
    }

    public Double getDiameterRobotViewModel() {
        return diameterRobotViewModel;
    }

    public void setDiameterRobotViewModel(Double diameterRobotViewModel) {
        this.diameterRobotViewModel = diameterRobotViewModel;
    }

    public String getDefaultRoleViewModel() {
        return defaultRoleViewModel;
    }

    public void setDefaultRoleViewModel(String defaultRoleViewModel) {
        this.defaultRoleViewModel = defaultRoleViewModel;
    }

    public String getSpeedViewModel() {
        return speedViewModel;
    }

    public void setSpeedViewModel(String speedViewModel) {
        this.speedViewModel = speedViewModel;
    }

    public Integer getAssistentViewModel() {
        return assistentViewModel;
    }

    public void setAssistentViewModel(Integer assistentViewModel) {
        this.assistentViewModel = assistentViewModel;
    }
}
