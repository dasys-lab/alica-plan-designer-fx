package de.unikassel.vs.alica.planDesigner.view.editor.tools;

import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaCursor;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class SynchronizationTool extends AbstractTool {

    public SynchronizationTool(TabPane workbench, PlanTab planTab, ToggleGroup group) {
        super(workbench, planTab, group);
    }

    @Override
    public ToolButton createToolUI() {
        ToolButton toolButton = new ToolButton();
        Tooltip tooltip = new Tooltip(Types.SYNCHRONISATION);
        toolButton.setTooltip(tooltip);
        toolButton.setIcon(Types.SYNCHRONISATION);
        toolButton.setId("SynchronizationToolButton");
        setToolButton(toolButton);
        imageCursor = new AlicaCursor(AlicaCursor.Type.synchronisation, 8, 8);
        forbiddenCursor = new AlicaCursor(AlicaCursor.Type.forbidden_synchronisation, 8, 8);
        addCursor = new AlicaCursor(AlicaCursor.Type.add_synchronisation, 8, 8);
        return toolButton;
    }

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

                    if (handleNonPrimaryButtonEvent(event)) {
                        return;
                    }

                    Point2D eventTargetCoordinates = getLocalCoordinatesFromEvent(event);
                    if (eventTargetCoordinates == null) {
                        event.consume();
                        return;
                    }

                    IGuiModificationHandler handler = MainWindowController.getInstance().getGuiModificationHandler();

                    GuiModificationEvent guiEvent = new GuiModificationEvent(GuiEventType.ADD_ELEMENT, Types.SYNCHRONISATION, "Sync Default");
                    guiEvent.setComment("");
                    guiEvent.setX((int) eventTargetCoordinates.getX());
                    guiEvent.setY((int) eventTargetCoordinates.getY());
                    guiEvent.setParentId(planTab.getSerializableViewModel().getId());
                    handler.handle(guiEvent);
                }
            });
        }
    }
}
