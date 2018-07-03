package  de.uni_kassel.vs.cn.planDesigner.view.editor.tab.behaviourTab;

import de.uni_kassel.vs.cn.planDesigner.common.ViewModelElement;
import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import javafx.beans.InvalidationListener;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class BehaviourConditionVBox {
    private final CheckBox checkBox;
    private ViewModelElement condition;

    @SuppressWarnings("unchecked")
    public BehaviourConditionVBox(ViewModelElement condition, VBox vBox) {
        super();
        this.condition = condition;
        checkBox = new CheckBox(I18NRepo.getInstance().getString("label.add.condition"));
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue) {
//                if (condition.getClass().equals(PreConditionImpl.class)) {
//                    if (condition != getBehaviour().getPreCondition()) {
//                        commandStack.storeAndExecute(new AddPreConditionToBehaviour((PreCondition) condition, getBehaviour()));
//                    }
//                } else if (condition.getClass().equals(PostConditionImpl.class)) {
//                    if (condition != getBehaviour().getPostCondition()) {
//                        commandStack.storeAndExecute(new AddPostConditionToBehaviour((PostCondition) condition, getBehaviour()));
//                    }
//                } else if(condition.getClass().equals(RuntimeConditionImpl.class)) {
//                    if (condition != getBehaviour().getRuntimeCondition()) {
//                        commandStack.storeAndExecute(new AddRuntimeConditionToBehaviour((RuntimeCondition) condition, getBehaviour()));
//                    }
//                }
//            } else {
//                if (condition.getClass().equals(PreConditionImpl.class)) {
//                    commandStack.storeAndExecute(new RemovePreConditionFromBehaviour(getBehaviour()));
//                } else if (condition.getClass().equals(PostConditionImpl.class)) {
//                    commandStack.storeAndExecute(new RemovePostConditionFromBehaviour(getBehaviour()));
//                } else if(condition.getClass().equals(RuntimeConditionImpl.class)) {
//                    commandStack.storeAndExecute(new RemoveRuntimeConditionFromBehaviour(getBehaviour()));
//                }
//            }
        });

//        PropertyTab propertyTab = new PropertyTab((AbstractEditorTab) abstractEditorTab, getCommandStack()) {
//
//            private boolean selected;
//            @Override
//            protected void addListenersForActiveTab(AbstractEditorTab<PlanElement> activeEditorTab) {
//                    /*
//                    if (object.getClass().equals(PreConditionImpl.class) && getBehaviour().getPreCondition() != null) {
//                        selected = true;
//                        setObject((T) getBehaviour().getPreCondition());
//                    } else if (object.getClass().equals(PostConditionImpl.class) && getBehaviour().getPostCondition() != null) {
//                        selected = true;
//                        setObject((T) getBehaviour().getPostCondition());
//                    } else if(object.getClass().equals(RuntimeConditionImpl.class) && getBehaviour().getRuntimeCondition() != null) {
//                        selected = true;
//                        setObject((T) getBehaviour().getRuntimeCondition());
//                    }*/
//                checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
//                    selected = newValue;
//                    createTabContent();
//                });
//            }
//
//            @Override
//            public PlanElement getSelectedEditorTabPlanElement() {
//                if (selected) {
//                    return condition;
//                } else {
//                    return null;
//                }
//            }
//
//            @Override
//            public CommandStack getCommandStack() {
//                return BehaviourWindowController.this.getCommandStack();
//            }
//
//            @Override
//            protected void createTabContent() {
//                if (getPropertyHBoxList() == null) {
//                    setPropertyHBoxList(FXCollections.observableList(new ArrayList<>()));
//                }
//                if (getSelectedEditorTabPlanElement() != null &&
//                        getSelectedEditorTabPlanElement().getClass().equals(condition.getClass())) {
//                    super.createTabContent();
//                } else {
//                    getPropertyHBoxList().clear();
//                }
//            }
//        };

//        ListView<Node> nodeListView = new ListView<>(propertyTab.getPropertyHBoxList());
//        nodeListView.getItems().addListener((InvalidationListener) observable -> {
//            if (nodeListView.getItems().size() == 0) {
//                nodeListView.setVisible(false);
//            } else {
//                nodeListView.setVisible(true);
//            }
//        });
//        vBox.getChildren().addAll(checkBox, nodeListView);
//        VBox.setVgrow(checkBox, Priority.ALWAYS);
//        VBox.setVgrow(nodeListView, Priority.ALWAYS);
    }

    public void setCondition(ViewModelElement condition) {
        if (condition != null) {
            this.condition = condition;
            checkBox.setSelected(true);
        }
    }
}