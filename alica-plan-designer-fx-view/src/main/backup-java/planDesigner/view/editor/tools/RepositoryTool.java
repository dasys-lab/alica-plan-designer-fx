package de.uni_kassel.vs.cn.planDesigner.view.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.controller.MainWindowController;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseDragEvent;

/**
 * Tool for inserting Plans, Behaviours, PlanTypes, or Tasks from the Repository View
 * into an opened Plan in the PlanEditorTabPane.
 */
public class RepositoryTool extends AbstractTool {

    public RepositoryTool(TabPane parentTabPane) {
        super(parentTabPane);
    }

    @Override
    protected void initHandlerMap() {
        previousCursor = Cursor.DEFAULT;
        customHandlerMap.put(MouseDragEvent.MOUSE_DRAG_OVER, new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent event) {
                if (event.getTarget() == null
                        || !(((Node) event.getTarget()).getParent() instanceof StateContainer)
                        || ((Node) event.getTarget()).getParent() instanceof TerminalStateContainer) {
                    if (!planEditorTabPane.getScene().getCursor().equals(MainWindowController.FORBIDDEN_CURSOR)) {
                        previousCursor = planEditorTabPane.getScene().getCursor();
                        planEditorTabPane.getScene().setCursor(MainWindowController.FORBIDDEN_CURSOR);
                    }
                } else {
                    planEditorTabPane.getScene().setCursor(previousCursor);
                }
                event.consume();
            }
        });


        customHandlerMap.put(MouseDragEvent.MOUSE_DRAG_RELEASED, new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent event) {
                if (event.getTarget() == null) {
                    endPhase();
                    event.consume();
                    return;
                }

                Parent parent = ((Node) event.getTarget()).getParent();
                if (parent instanceof StateContainer && !(parent instanceof TerminalStateContainer)) {
                    // Put the repository view element (plan, behaviour, plantype, or task) into a state...
                    // TODO: exception for task...

                    StateContainer stateContainer = (StateContainer) parent;
                    // TODO: determine the source of the drag event, via debugging and adapt the creation of the command accordingly
                    //event.getGestureSource();
                    //AddAbstractPlanToState command = new AddAbstractPlanToState(modelElementId, stateContainer.getModelElementId());
                    //MainWindowController.getInstance().getCommandStack().storeAndExecute(command);
                }
            }
        });
    }


}
