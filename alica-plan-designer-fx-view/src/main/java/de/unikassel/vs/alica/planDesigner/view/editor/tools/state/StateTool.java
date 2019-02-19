package de.unikassel.vs.alica.planDesigner.view.editor.tools.state;

import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.AbstractTool;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.ToolButton;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaCursor;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

/**
 * The {@link StateTool} is used for adding new states to the currently edited plan.
 */
public class StateTool extends AbstractTool {

    // Type for sending the event of the tool
    protected String stateType;

    public StateTool(TabPane workbench, PlanTab planTab, ToggleGroup group) {
        super(workbench, planTab, group);
        stateType = Types.STATE;
    }

    /**
     * Creating a guiModificationHandler, that creates an event to request the creation of a new State.
     */
    @Override
    protected void initHandlerMap() {
        if (customHandlerMap.isEmpty()) {
            customHandlerMap.put(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Node target = (Node) event.getTarget();
                    Parent parent = target.getParent();
                    if (parent instanceof StackPane) {
                        setCursor(addCursor);
                    } else {
                        setCursor(forbiddenCursor);
                    }
                }
            });

            customHandlerMap.put(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    if(handleNonPrimaryButtonEvent(event)){
                        return;
                    }

                    // Calculate the relative coordinates of the event
                    Point2D eventTargetCoordinates = getLocalCoordinatesFromEvent(event);
                    // If the event is not valid (because it happened outside of the editor) don't do anything
                    if (eventTargetCoordinates == null) {
                        event.consume();
                        return;
                    }

                    fireEvent(eventTargetCoordinates);
                }
            });
        }
    }

    protected void fireEvent(Point2D eventTargetCoordinates) {
        // Get the guiModificationHandler
        IGuiModificationHandler handler = MainWindowController.getInstance().getGuiModificationHandler();

        // Create an event. In this case use a GuiChangePositionEvent, because it can also hold the coordinates
        // of the event
        I18NRepo i18NRepo = I18NRepo.getInstance();
        GuiModificationEvent guiEvent = new GuiModificationEvent(GuiEventType.ADD_ELEMENT, stateType, i18NRepo.getString("label.state.defaultName"));
        guiEvent.setComment("");
        guiEvent.setX((int) eventTargetCoordinates.getX());
        guiEvent.setY((int) eventTargetCoordinates.getY());
        guiEvent.setParentId(planTab.getSerializableViewModel().getId());
        handler.handle(guiEvent);
    }

    @Override
    public ToolButton createToolUI() {
        ToolButton tollButton = new ToolButton();
        tollButton.setIcon(Types.STATE);
        setToolButton(tollButton);
        imageCursor = new AlicaCursor(AlicaCursor.Type.state);
        forbiddenCursor = new AlicaCursor(AlicaCursor.Type.forbidden_state);
        addCursor = new AlicaCursor(AlicaCursor.Type.add_state);
        return tollButton;
    }
}
