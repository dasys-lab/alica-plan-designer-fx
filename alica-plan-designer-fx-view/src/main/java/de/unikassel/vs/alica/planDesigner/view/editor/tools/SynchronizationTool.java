package de.unikassel.vs.alica.planDesigner.view.editor.tools;

import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiChangePositionEvent;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaCursor;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class SynchronizationTool extends AbstractTool {

    private Node visualRepresentation;
    private boolean initial = true;

    public SynchronizationTool(TabPane workbench, PlanTab planTab, ToggleGroup group) {
        super(workbench, planTab, group);
    }

    @Override
    public ToolButton createToolUI() {
        ToolButton toolButton = new ToolButton();
        toolButton.setIcon(Types.SYNCHRONISATION);
        setToolButton(toolButton);
        imageCursor = new AlicaCursor(AlicaCursor.Type.synchronisation);
        forbiddenCursor = new AlicaCursor(AlicaCursor.Type.forbidden_synchronisation);
        addCursor = new AlicaCursor(AlicaCursor.Type.add_synchronisation);
        return toolButton;
    }

    @Override
    protected void initHandlerMap() {
        I18NRepo i18NRepo = I18NRepo.getInstance();

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

                    GuiChangePositionEvent guiEvent = new GuiChangePositionEvent(GuiEventType.ADD_ELEMENT, Types.SYNCHRONISATION, "Sync Default");
                    guiEvent.setComment("");
                    guiEvent.setNewX((int) eventTargetCoordinates.getX());
                    guiEvent.setNewY((int) eventTargetCoordinates.getY());
                    guiEvent.setParentId(planTab.getSerializableViewModel().getId());
                    handler.handle(guiEvent);
                }
            });
        }
    }
}
