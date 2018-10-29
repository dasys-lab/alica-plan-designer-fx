package de.uni_kassel.vs.cn.planDesigner.view.editor.tools.condition;

import de.uni_kassel.vs.cn.planDesigner.view.Types;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.ConditionHBox;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.planTab.PlanTab;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tools.AbstractTool;
import de.uni_kassel.vs.cn.planDesigner.view.img.AlicaIcon;
import de.uni_kassel.vs.cn.planDesigner.view.img.AlicaIcon.Size;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;

import java.util.HashMap;
import java.util.Map;


public abstract class AbstractConditionTool extends AbstractTool {

    protected Map<EventType, EventHandler> eventHandlerMap = new HashMap<>();
    protected Node visualRepresentation;

    public AbstractConditionTool(TabPane workbench) {
        super(workbench);
    }

    public void draw() {
        ((PlanTab) planEditorTabPane.getSelectionModel().getSelectedItem()).getConditionHBox().setupConditionVisualisation();
    }

    @Override
    protected Node getPlanEditorTabPane() {
        return ((PlanTab)(planEditorTabPane.getSelectionModel().getSelectedItem())).getConditionHBox();
    }

    @Override
    protected Map<EventType, EventHandler> getCustomHandlerMap() {
        if (eventHandlerMap.isEmpty()) {
            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_ENTERED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    if (event.getTarget() instanceof ConditionHBox && visualRepresentation == null) {
                        //TODO change later to fitting type
                        visualRepresentation = new ImageView(new AlicaIcon(Types.PRECONDITION, Size.SMALL));
                        ((ConditionHBox)event.getTarget()).getChildren().add(visualRepresentation);
                    }
                    event.consume();
                }
            });

            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_EXITED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    if (visualRepresentation != null) {
                        ((ConditionHBox)event.getSource()).getChildren().remove(visualRepresentation);
                        visualRepresentation = null;
                    }
                }
            });


            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_RELEASED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    if (event.getTarget() instanceof ConditionHBox) {
                        ((ConditionHBox)event.getTarget()).getChildren().remove(visualRepresentation);
//                        Condition newCondition = createNewObject();
//                        newCondition.setPluginName(PluginManager.getInstance().getDefaultPlugin().getName());
//                        if (newCondition instanceof PostCondition == false) {
//                            AddConditionToPlan command = new AddConditionToPlan(((PlanTab) planEditorTabPane.getSelectionModel().getSelectedItem()).getEditable(),
//                                    newCondition);
//                            MainWindowController.getInstance()
//                                    .getCommandStack()
//                                    .storeAndExecute(command);
//                        }
                    }
                    endPhase();
                }
            });
        }
        return eventHandlerMap;
    }
}
