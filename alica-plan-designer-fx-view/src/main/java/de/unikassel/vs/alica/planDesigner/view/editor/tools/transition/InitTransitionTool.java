package de.unikassel.vs.alica.planDesigner.view.editor.tools.transition;

import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.container.EntryPointContainer;
import de.unikassel.vs.alica.planDesigner.view.editor.container.StateContainer;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.AbstractTool;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.EditorToolBar;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.ToolButton;
import de.unikassel.vs.alica.planDesigner.view.model.StateViewModel;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaCursor;
import javafx.scene.layout.VBox;

import java.util.HashMap;


public class InitTransitionTool extends AbstractTool {

    private EntryPointContainer entryPoint;
    private StateViewModel state;

    public InitTransitionTool(TabPane workbench, PlanTab planTab, ToggleGroup group) {
        super(workbench, planTab, group);
    }

    @Override
    public ToolButton createToolUI() {
        ToolButton toolButton = new ToolButton();
        Tooltip tooltip = new Tooltip(Types.INITSTATECONNECTION);
        toolButton.setTooltip(tooltip);
        toolButton.setIcon(Types.INITSTATECONNECTION);
        toolButton.setId("InitTransitionToolButton");
        setToolButton(toolButton);
        imageCursor = new AlicaCursor(AlicaCursor.Type.initstateconnection, 8, 8);
        forbiddenCursor = new AlicaCursor(AlicaCursor.Type.forbidden_initstateconnection, 8, 8);
        addCursor = new AlicaCursor(AlicaCursor.Type.add_initstateconnection, 8, 8);
        return toolButton;
    }

    @Override
    protected void initHandlerMap() {
        if (customHandlerMap.isEmpty()) {
            customHandlerMap.put(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Parent parent = ((Node) event.getTarget()).getParent();
                    if (parent instanceof EntryPointContainer ) {
                        setCursor(addCursor);
                    } else if (parent instanceof  StateContainer) {
                        if (((StateContainer) parent).getState().getType() == Types.STATE) {
                            // Normal States
                            setCursor(addCursor);
                        } else {
                            // Terminal States
                            setCursor(forbiddenCursor);
                        }
                    } else if (parent instanceof ToggleButton || parent instanceof EditorToolBar || parent instanceof VBox) {
                        setCursor(Cursor.DEFAULT);
                    } else {
                        setCursor(forbiddenCursor);
                    }
                }
            });

            customHandlerMap.put(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (!isClickValid(event)) {
                        return;
                    }

                    Parent parent = ((Node) event.getTarget()).getParent();
                    if (parent instanceof EntryPointContainer) {
                        entryPoint =  (EntryPointContainer) parent;
                    } else if (parent instanceof StateContainer) {
                        if (((StateContainer) parent).getState().getType() == Types.STATE) {
                            // Only Normal States allowed
                            state = ((StateContainer) parent).getState();
                        }
                    }

                    // ep and state selected, so send event and reset ep and state for further use of the tool
                    if (entryPoint != null && state != null) {
                        IGuiModificationHandler guiModificationHandler = MainWindowController.getInstance().getGuiModificationHandler();
                        GuiModificationEvent guiEvent = new GuiModificationEvent(GuiEventType.ADD_ELEMENT, Types.INITSTATECONNECTION, null);
                        HashMap<String, Long> relatedObjects = new HashMap<>();
                        relatedObjects.put(Types.ENTRYPOINT, entryPoint.getPlanElementViewModel().getId());
                        relatedObjects.put(Types.STATE, state.getId());
                        guiEvent.setRelatedObjects(relatedObjects);
                        guiEvent.setParentId(InitTransitionTool.this.planTab.getSerializableViewModel().getId());
                        guiModificationHandler.handle(guiEvent);
                        entryPoint = null;
                        state = null;
                    }
                }
            });
        }
    }

    private boolean isClickValid(MouseEvent event) {
        if (!(event.getTarget() instanceof  Node)) {
            System.err.println("InitTransitionTool: Type clicked on does not match tool!");
            return false;
        }

        Parent parent = ((Node) event.getTarget()).getParent();
        if (parent instanceof ToggleButton) {
            endTool();
            return false;
        }

        if (handleNonPrimaryButtonEvent(event)) {
            return false;
        }

        return true;
    }
}
