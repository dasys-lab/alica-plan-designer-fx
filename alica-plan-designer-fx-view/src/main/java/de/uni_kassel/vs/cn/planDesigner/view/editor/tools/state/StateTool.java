package de.uni_kassel.vs.cn.planDesigner.view.editor.tools.state;

import de.uni_kassel.vs.cn.planDesigner.controller.MainWindowController;
import de.uni_kassel.vs.cn.planDesigner.events.GuiChangePositionEvent;
import de.uni_kassel.vs.cn.planDesigner.events.GuiEventType;
import de.uni_kassel.vs.cn.planDesigner.events.GuiModificationEvent;
import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.uni_kassel.vs.cn.planDesigner.view.Types;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tools.AbstractTool;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tools.DraggableHBox;
import de.uni_kassel.vs.cn.planDesigner.view.model.PlanViewModel;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseDragEvent;

import java.lang.annotation.ElementType;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@link StateTool} is used for adding new states to the currently edited plan.
 */
public class StateTool extends AbstractTool {

    private HashMap<EventType, EventHandler> eventHandlerMap = new HashMap<>();

    public StateTool(TabPane workbench, PlanViewModel plan) {
        super(workbench, plan);
    }

    @Override
    protected void initHandlerMap() {

    }

    @Override
    public DraggableHBox createToolUI() {
        DraggableHBox draggableHBox = new DraggableHBox();
        draggableHBox.setIcon(Types.STATE);
        setDraggableHBox(draggableHBox);
        return draggableHBox;
    }

    /**
     * Creating a handler, that creates an event to request the creation of a new {@link de.uni_kassel.vs.cn.planDesigner.alicamodel.State}.
     *
     * @return  a map containing the handler
     */
    @Override
    protected Map<EventType, EventHandler> getCustomHandlerMap() {
        if (eventHandlerMap.isEmpty()) {
            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_RELEASED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    IGuiModificationHandler handler = MainWindowController.getInstance().getGuiModificationHandler();

                    //Name can be null, because it is ignored by the command anyway
                    GuiModificationEvent guiEvent = new GuiModificationEvent(GuiEventType.ADD_ELEMENT, Types.STATE, null);
                    guiEvent.setParentId(getPlan().getId());
                    handler.handle(guiEvent);






//                    updateLocalCoords(event);
//                    if (((Node) event.getTarget()).getParent() instanceof AbstractPlanElementContainer == false &&
//                            event.getTarget() instanceof StackPane == false) {
//                        event.consume();
//                        endPhase();
//                        return;
//                    }
//                    AddStateInPlan command = new AddStateInPlan(planModelVisualisationObject,
//                            createNewObject());
//                    MainWindowController.getInstance()
//                            .getCommandStack()
//                            .storeAndExecute(command);
//
//                    MainWindowController.getInstance()
//                            .getCommandStack()
//                            .storeAndExecute(new ChangePosition(command.getNewlyCreatedPmlUiExtension(), command.getElementToEdit(),
//                                    (int) (localCoord.getX()),
//                                    (int) (localCoord.getY()), planModelVisualisationObject.getPlan()));
//                    endPhase();
                }
            });
        }
        return eventHandlerMap;
    }

}
