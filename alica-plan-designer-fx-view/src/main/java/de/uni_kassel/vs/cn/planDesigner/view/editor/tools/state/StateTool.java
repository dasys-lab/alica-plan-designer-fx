package de.uni_kassel.vs.cn.planDesigner.view.editor.tools.state;

import de.uni_kassel.vs.cn.planDesigner.controller.MainWindowController;
import de.uni_kassel.vs.cn.planDesigner.events.GuiChangePositionEvent;
import de.uni_kassel.vs.cn.planDesigner.events.GuiEventType;
import de.uni_kassel.vs.cn.planDesigner.events.GuiModificationEvent;
import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.uni_kassel.vs.cn.planDesigner.view.Types;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.planTab.PlanTab;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tools.AbstractTool;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tools.DraggableHBox;
import de.uni_kassel.vs.cn.planDesigner.view.model.PlanViewModel;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseDragEvent;

import java.lang.annotation.ElementType;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@link StateTool} is used for adding new states to the currently edited plan.
 */
public class StateTool extends AbstractTool {

    public StateTool(TabPane workbench, PlanTab planTab) {
        super(workbench, planTab);
    }



    /**
     * Creating a handler, that creates an event to request the creation of a new State.
     */
    @Override
    protected void initHandlerMap() {
        if (customHandlerMap.isEmpty()) {
            customHandlerMap.put(MouseDragEvent.MOUSE_DRAG_RELEASED, (EventHandler<MouseDragEvent>) event -> {

                // Calculate the relative coordinates of the event
                Point2D eventTargetCoordinates = getLocalCoordinatesFromEvent(event);
                // If the event is not valid (because it happened outside of the editor) don't do anything
                if(eventTargetCoordinates == null){
                    event.consume();
                    return;
                }

                // Get the handler
                IGuiModificationHandler handler = MainWindowController.getInstance().getGuiModificationHandler();

                // Create an event. In this case use a GuiChangePositionEvent, because it can also hold the coordinates
                // of the event
                GuiChangePositionEvent guiEvent = createEvent();
                guiEvent.setNewX((int) eventTargetCoordinates.getX());
                guiEvent.setNewY((int) eventTargetCoordinates.getY());
                guiEvent.setParentId(getPlanTab().getPlan().getId());
                handler.handle(guiEvent);
            });
        }
    }

    @Override
    public DraggableHBox createToolUI() {
        DraggableHBox draggableHBox = new DraggableHBox();
        draggableHBox.setIcon(Types.STATE);
        setDraggableHBox(draggableHBox);
        return draggableHBox;
    }

    /**
     * Create an event. In this case use a GuiChangePositionEvent, because it can also hold the coordinates
     * of the event
     *
     * @return  the created event
     */
    protected GuiChangePositionEvent createEvent(){
        return new GuiChangePositionEvent(GuiEventType.ADD_ELEMENT, Types.STATE, null);
    }

}
