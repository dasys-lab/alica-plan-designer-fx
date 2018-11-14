package de.unikassel.vs.alica.planDesigner.view.editor.tools.state;

import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiChangePositionEvent;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.AbstractTool;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.DraggableHBox;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaIcon;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.ImageCursor;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseDragEvent;

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
                planEditorTabPane.getScene().setCursor(previousCursor);

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
            customHandlerMap.put(MouseDragEvent.MOUSE_DRAG_ENTERED, (EventHandler<MouseDragEvent>) event -> {
                planEditorTabPane.getScene().setCursor(getImageCursor());
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

    /**
     * Create an {@link ImageCursor} with the symbol of this tool.
     *
     * @return  the cursor representing this tool
     */
    protected ImageCursor getImageCursor(){
        return new ImageCursor(new AlicaIcon(Types.STATE, AlicaIcon.Size.SMALL));
    }
}
