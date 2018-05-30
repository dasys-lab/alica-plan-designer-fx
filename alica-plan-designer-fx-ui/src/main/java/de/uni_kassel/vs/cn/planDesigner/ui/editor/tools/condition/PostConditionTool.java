package de.uni_kassel.vs.cn.planDesigner.ui.editor.tools.condition;

import de.uni_kassel.vs.cn.generator.plugin.PluginManager;
import de.uni_kassel.vs.cn.planDesigner.PlanDesigner;
import de.uni_kassel.vs.cn.planDesigner.alica.PostCondition;
import de.uni_kassel.vs.cn.planDesigner.alica.TerminalState;
import de.uni_kassel.vs.cn.planDesigner.command.add.AddPostConditionToTerminalState;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.TerminalStateContainer;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;

import java.util.Map;

import static de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils.getAlicaFactory;

/**
 * Created by marci on 01.03.17.
 */
public class PostConditionTool extends AbstractConditionTool<PostCondition> {


    public PostConditionTool(TabPane workbench) {
        super(workbench);
    }

    @Override
    public PostCondition createNewObject() {
        return getAlicaFactory().createPostCondition();
    }

    @Override
    protected Map<EventType, EventHandler> toolRequiredHandlers() {
        if (eventHandlerMap.isEmpty()) {

            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_OVER, (EventHandler<MouseDragEvent>) event -> {
                if (event.getTarget() instanceof Node == false) {
                    event.consume();
                    return;
                }
                if (((Node)event.getTarget()).getParent() instanceof TerminalStateContainer == false) {
                    workbench.getScene().setCursor(PlanDesigner.FORBIDDEN_CURSOR);
                } else {
                    workbench.getScene().setCursor(toolCursor);
                }
            });


            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_RELEASED, (EventHandler<MouseDragEvent>) event -> {
                if (event.getTarget() instanceof Node
                        && ((Node)event.getTarget()).getParent() instanceof TerminalStateContainer) {
                    PostCondition newCondition = createNewObject();
                    newCondition.setPluginName(PluginManager.getInstance().getActivePlugin().getName());
                        TerminalStateContainer terminalStateContainer = (TerminalStateContainer) ((Node)event.getTarget()).getParent();
                        AddPostConditionToTerminalState command = new AddPostConditionToTerminalState(newCondition,
                                (TerminalState) terminalStateContainer.getContainedElement());
                        MainController.getInstance()
                                .getCommandStack()
                                .storeAndExecute(command);
                }
                endPhase();
            });

            eventHandlerMap.put(MouseEvent.MOUSE_RELEASED, (EventHandler<MouseEvent>) event -> endPhase());
        }
        return eventHandlerMap;
    }
}
