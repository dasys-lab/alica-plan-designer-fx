package de.uni_kassel.vs.cn.planDesigner.view.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.controller.MainWindowController;
import de.uni_kassel.vs.cn.planDesigner.view.editor.container.StateContainer;
import de.uni_kassel.vs.cn.planDesigner.view.editor.container.TerminalStateContainer;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseDragEvent;

public class RepositoryTool extends AbstractTool {

    private Cursor previousCursor;

    public RepositoryTool(TabPane workbench) {
        super(workbench);
    }

    @Override
    protected void initHandlerMap () {
        previousCursor = workbench.getScene().getCursor();
        customHandlerMap.put(MouseDragEvent.MOUSE_DRAG_OVER, new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent event) {
                if (event.getTarget() == null || ((Node) event.getTarget()).getParent() instanceof StateContainer == false ||
                        ((Node) event.getTarget()).getParent() instanceof TerminalStateContainer) {
                    if (workbench.getScene().getCursor().equals(PlanDesigner.FORBIDDEN_CURSOR) == false) {
                        previousCursor = workbench.getScene().getCursor();
                        workbench.getScene().setCursor(PlanDesigner.FORBIDDEN_CURSOR);
                    }
                } else {
                    workbench.getScene().setCursor(previousCursor);
                }
                event.consume();
            }
        });


        customHandlerMap.put(MouseDragEvent.MOUSE_DRAG_RELEASED, new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent event) {
                if (event.getTarget() != null && ((Node) event.getTarget()).getParent() instanceof StateContainer &&
                        ((Node) event.getTarget()).getParent() instanceof TerminalStateContainer == false) {
                    StateContainer stateContainer = (StateContainer) ((Node) event.getTarget()).getParent();
                    AddAbstractPlanToState command = new AddAbstractPlanToState(modelElementId, stateContainer.getContainedElement());
                    MainWindowController.getInstance()
                            .getCommandStack()
                            .storeAndExecute(command);
                    draw();
                }

                endPhase();
            }
        });
    }


}
