package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.planDesigner.common.ViewModelElement;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.behaviourTab.BehaviourConditionVBox;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.behaviourTab.BehaviourTab;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This class is a pseudo {@link javafx.scene.control.Tab} and should NEVER be added to a TabPane.
 */
public class BehaviourWindowController implements Initializable {

    @FXML
    private VBox preConditionOptions;

    @FXML
    private VBox postConditionOptions;

    @FXML
    private VBox runtimeConditionOptions;

    @FXML
    private BehaviourTab behaviourTab;

    private ViewModelElement behaviour;

    private BehaviourConditionVBox preConditionVBox;
    private BehaviourConditionVBox postConditionVBox;
    private BehaviourConditionVBox runtimeConditionVBox;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setBehaviourTab(BehaviourTab behaviourTab) {
        this.behaviourTab = behaviourTab;
    }

    public ViewModelElement getViewModelElement() {
        return behaviour;
    }

    public void setViewModelElement(ViewModelElement behaviour) {
        this.behaviour = behaviour;
//        PreCondition preCondition = behaviour.getPreCondition();
//        if (preCondition != null) {
//            preConditionVBox.setCondition(preCondition);
//        }
//
//        PostCondition postCondition = behaviour.getPostCondition();
//        if (postCondition != null) {
//            postConditionVBox.setCondition(postCondition);
//        }
//
//        RuntimeCondition runtimeCondition = behaviour.getRuntimeCondition();
//        if (runtimeCondition != null) {
//            runtimeConditionVBox.setCondition(runtimeCondition);
//        }
    }
}
