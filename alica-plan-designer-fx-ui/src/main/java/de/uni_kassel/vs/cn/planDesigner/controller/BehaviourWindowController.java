package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.alica.*;
import de.uni_kassel.vs.cn.planDesigner.alica.impl.PostConditionImpl;
import de.uni_kassel.vs.cn.planDesigner.alica.impl.PreConditionImpl;
import de.uni_kassel.vs.cn.planDesigner.alica.impl.RuntimeConditionImpl;
import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;
import de.uni_kassel.vs.cn.planDesigner.common.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.AbstractEditorTab;
import de.uni_kassel.vs.cn.planDesigner.ui.properties.PropertyTab;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.File;
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

    private Behaviour behaviour;

    private CommandStack commandStack;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new BehaviourConditionVBox<>(EMFModelUtils.getAlicaFactory().createPreCondition(), preConditionOptions);
        new BehaviourConditionVBox<>(EMFModelUtils.getAlicaFactory().createPostCondition(), postConditionOptions);
        new BehaviourConditionVBox<>(EMFModelUtils.getAlicaFactory().createRuntimeCondition(), runtimeConditionOptions);

    }

    public Behaviour getBehaviour() {
        return behaviour;
    }

    public void setBehaviour(Behaviour behaviour) {
        this.behaviour = behaviour;
    }

    public CommandStack getCommandStack() {
        return commandStack;
    }

    public void setCommandStack(CommandStack commandStack) {
        this.commandStack = commandStack;
    }

    private class BehaviourConditionVBox<T extends Condition> {
        private T object;
        private final CheckBox checkBox;

        @SuppressWarnings("unchecked")
        public BehaviourConditionVBox(T object, VBox vBox) {
            super();
            this.object = object;
            checkBox = new CheckBox(I18NRepo.getString("label.add.condition"));
            checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == true) {
                    if (object.getClass().equals(PreConditionImpl.class)) {
                        getBehaviour().setPreCondition((PreCondition) object);
                    } else if (object.getClass().equals(PostConditionImpl.class)) {
                        getBehaviour().setPostCondition((PostCondition) object);
                    } else if(object.getClass().equals(RuntimeConditionImpl.class)) {
                        getBehaviour().setRuntimeCondition((RuntimeCondition) object);
                    }
                } else {
                    if (object.getClass().equals(PreConditionImpl.class)) {
                        getBehaviour().setPreCondition(null);
                    } else if (object.getClass().equals(PostConditionImpl.class)) {
                        getBehaviour().setPostCondition(null);
                    } else if(object.getClass().equals(RuntimeConditionImpl.class)) {
                        getBehaviour().setRuntimeCondition(null);
                    }
                }
            });
            AbstractEditorTab<Behaviour> abstractEditorTab = new AbstractEditorTab<Behaviour>(null, new File(".").toPath(), null) {

                @Override
                public CommandStack getCommandStack() {
                    return BehaviourWindowController.this.getCommandStack();
                }

                @Override
                public Behaviour getEditable() {
                    return getBehaviour();
                }
            };
            PropertyTab propertyTab = new PropertyTab((AbstractEditorTab) abstractEditorTab, getCommandStack()) {

                private boolean selected;
                @Override
                protected void addListenersForActiveTab(AbstractEditorTab<PlanElement> activeEditorTab) {
                    /*
                    if (object.getClass().equals(PreConditionImpl.class) && getBehaviour().getPreCondition() != null) {
                        selected = true;
                        setObject((T) getBehaviour().getPreCondition());
                    } else if (object.getClass().equals(PostConditionImpl.class) && getBehaviour().getPostCondition() != null) {
                        selected = true;
                        setObject((T) getBehaviour().getPostCondition());
                    } else if(object.getClass().equals(RuntimeConditionImpl.class) && getBehaviour().getRuntimeCondition() != null) {
                        selected = true;
                        setObject((T) getBehaviour().getRuntimeCondition());
                    }*/
                    checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                        selected = newValue;
                        createTabContent();
                    });
                }

                @Override
                public PlanElement getSelectedEditorTabPlanElement() {
                    if (selected) {
                        return object;
                    } else {
                        return null;
                    }
                }

                @Override
                public CommandStack getCommandStack() {
                    return BehaviourWindowController.this.getCommandStack();
                }

                @Override
                protected void createTabContent() {
                    if (getPropertyHBoxList() == null) {
                        setPropertyHBoxList(FXCollections.observableList(new ArrayList<>()));
                    }
                    if (getSelectedEditorTabPlanElement() != null &&
                            getSelectedEditorTabPlanElement().getClass().equals(object.getClass())) {
                        super.createTabContent();
                    } else {
                        getPropertyHBoxList().clear();
                    }
                }
            };

            ListView<Node> nodeListView = new ListView<>(propertyTab.getPropertyHBoxList());
            nodeListView.getItems().addListener((InvalidationListener) observable -> {
                if (nodeListView.getItems().size() == 0) {
                    nodeListView.setVisible(false);
                } else {
                    nodeListView.setVisible(true);
                }
            });
            vBox.getChildren().addAll(checkBox, nodeListView);
            VBox.setVgrow(checkBox, Priority.ALWAYS);
            VBox.setVgrow(nodeListView, Priority.ALWAYS);
        }

        public void setObject(T object) {
            this.object = object;
        }
    }
}
