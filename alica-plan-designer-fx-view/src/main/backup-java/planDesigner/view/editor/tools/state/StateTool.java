package de.uni_kassel.vs.cn.planDesigner.view.editor.tools.state;

import de.uni_kassel.vs.cn.planDesigner.controller.MainWindowController;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@link StateTool} is used for adding new states to the currently edited plan.
 */
public class StateTool extends AbstractTool {

    private HashMap<EventType, EventHandler> eventHandlerMap = new HashMap<>();

    public StateTool(TabPane workbench) {
        super(workbench);
    }

    @Override
    protected Map<EventType, EventHandler> getCustomHandlerMap() {
        if (eventHandlerMap.isEmpty()) {
            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_RELEASED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    updateLocalCoords(event);
                    if (((Node) event.getTarget()).getParent() instanceof AbstractPlanElementContainer == false &&
                            event.getTarget() instanceof StackPane == false) {
                        event.consume();
                        endPhase();
                        return;
                    }
                    AddStateInPlan command = new AddStateInPlan(planModelVisualisationObject,
                            createNewObject());
                    MainWindowController.getInstance()
                            .getCommandStack()
                            .storeAndExecute(command);

                    MainWindowController.getInstance()
                            .getCommandStack()
                            .storeAndExecute(new ChangePosition(command.getNewlyCreatedPmlUiExtension(), command.getElementToEdit(),
                                    (int) (localCoord.getX()),
                                    (int) (localCoord.getY()), planModelVisualisationObject.getPlan()));
                    endPhase();
                }
            });
        }
        return eventHandlerMap;
    }

}
