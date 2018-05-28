package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.planDesigner.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.command.add.AddPostConditionToBehaviour;
import de.uni_kassel.vs.cn.planDesigner.command.add.AddPreConditionToBehaviour;
import de.uni_kassel.vs.cn.planDesigner.command.add.AddRuntimeConditionToBehaviour;
import de.uni_kassel.vs.cn.planDesigner.command.delete.RemovePostConditionFromBehaviour;
import de.uni_kassel.vs.cn.planDesigner.command.delete.RemovePreConditionFromBehaviour;
import de.uni_kassel.vs.cn.planDesigner.command.delete.RemoveRuntimeConditionFromBehaviour;
import de.uni_kassel.vs.cn.planDesigner.alica.*;
import de.uni_kassel.vs.cn.planDesigner.alica.impl.PostConditionImpl;
import de.uni_kassel.vs.cn.planDesigner.alica.impl.PreConditionImpl;
import de.uni_kassel.vs.cn.planDesigner.alica.impl.RuntimeConditionImpl;
import de.uni_kassel.vs.cn.generator.EMFModelUtils;
import de.uni_kassel.vs.cn.planDesigner.common.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.AbstractEditorTab;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.BehaviourTab;
import de.uni_kassel.vs.cn.planDesigner.view.properties.PropertyTab;
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

    private Behaviour behaviour;

    private CommandStack commandStack;
    private BehaviourConditionVBox<PreCondition> preConditionVBox;
    private BehaviourConditionVBox<PostCondition> postConditionVBox;
    private BehaviourConditionVBox<RuntimeCondition> runtimeConditionVBox;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        preConditionVBox = new BehaviourConditionVBox<>(EMFModelUtils.getAlicaFactory().createPreCondition(), preConditionOptions);
        postConditionVBox = new BehaviourConditionVBox<>(EMFModelUtils.getAlicaFactory().createPostCondition(), postConditionOptions);
        runtimeConditionVBox = new BehaviourConditionVBox<>(EMFModelUtils.getAlicaFactory().createRuntimeCondition(), runtimeConditionOptions);

    }

    public Behaviour getBehaviour() {
        return behaviour;
    }

    public void setBehaviour(Behaviour behaviour) {
        this.behaviour = behaviour;
        PreCondition preCondition = behaviour.getPreCondition();
        if (preCondition != null) {
            preConditionVBox.setCondition(preCondition);
        }

        PostCondition postCondition = behaviour.getPostCondition();
        if (postCondition != null) {
            postConditionVBox.setCondition(postCondition);
        }

        RuntimeCondition runtimeCondition = behaviour.getRuntimeCondition();
        if (runtimeCondition != null) {
            runtimeConditionVBox.setCondition(runtimeCondition);
        }
    }

    public CommandStack getCommandStack() {
        return commandStack;
    }

    public void setCommandStack(CommandStack commandStack) {
        this.commandStack = commandStack;
    }

    private class BehaviourConditionVBox<T extends Condition> {
        private final CheckBox checkBox;
        private T condition;

        @SuppressWarnings("unchecked")
        public BehaviourConditionVBox(T object, VBox vBox) {
            super();
            condition = object;
            checkBox = new CheckBox(I18NRepo.getInstance().getString("label.add.condition"));
            checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    if (condition.getClass().equals(PreConditionImpl.class)) {
                        if (condition != getBehaviour().getPreCondition()) {
                            commandStack.storeAndExecute(new AddPreConditionToBehaviour((PreCondition) condition, getBehaviour()));
                        }
                    } else if (condition.getClass().equals(PostConditionImpl.class)) {
                        if (condition != getBehaviour().getPostCondition()) {
                            commandStack.storeAndExecute(new AddPostConditionToBehaviour((PostCondition) condition, getBehaviour()));
                        }
                    } else if(condition.getClass().equals(RuntimeConditionImpl.class)) {
                        if (condition != getBehaviour().getRuntimeCondition()) {
                            commandStack.storeAndExecute(new AddRuntimeConditionToBehaviour((RuntimeCondition) condition, getBehaviour()));
                        }
                    }
                } else {
                    if (condition.getClass().equals(PreConditionImpl.class)) {
                        commandStack.storeAndExecute(new RemovePreConditionFromBehaviour(getBehaviour()));
                    } else if (condition.getClass().equals(PostConditionImpl.class)) {
                        commandStack.storeAndExecute(new RemovePostConditionFromBehaviour(getBehaviour()));
                    } else if(condition.getClass().equals(RuntimeConditionImpl.class)) {
                        commandStack.storeAndExecute(new RemoveRuntimeConditionFromBehaviour(getBehaviour()));
                    }
                }
            });
            AbstractEditorTab<Behaviour> abstractEditorTab = new AbstractEditorTab<Behaviour>(behaviour) {

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
                        return condition;
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
                            getSelectedEditorTabPlanElement().getClass().equals(condition.getClass())) {
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

        public void setCondition(T condition) {
            if (condition != null) {
                this.condition = condition;
                checkBox.setSelected(true);
            }
        }
    }

    public void setBehaviourTab(BehaviourTab behaviourTab) {
        this.behaviourTab = behaviourTab;
    }
}
