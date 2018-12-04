package de.unikassel.vs.alica.planDesigner.view.editor.tools.transition;

import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.AbstractTool;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.DraggableHBox;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;

public class InitTransitionTool extends AbstractTool {

    private boolean initial = true;
//    private EntryPointContainer start;
//    private StateContainer finish;

    public InitTransitionTool(TabPane workbench, PlanTab planTab) {
        super(workbench, planTab);
    }

//    public InitStateConnection createNewObject() {
//        return new InitStateConnection();
//    }

    public void draw() {
//        ((PlanTab) planEditorTabPane.getSelectionModel().getSelectedItem()).getPlanEditorGroup().setupPlanVisualisation();
    }

    @Override
    protected void initHandlerMap() {
        if (customHandlerMap.isEmpty()) {
            customHandlerMap.put(MouseEvent.MOUSE_CLICKED, event -> {
//                if(((Node)event.getTarget()).getParent() instanceof EntryPointContainer) {
//                    if (initial) {
//                        start = (EntryPointContainer) ((Node)event.getTarget()).getParent();
//                        initial = false;
//                    } else {
//                        endTool();
//                    }
//                } else if (((Node)event.getTarget()).getParent() instanceof StateContainer && initial == false) {
//                    finish = (StateContainer) ((Node)event.getTarget()).getParent();
//                    initial = true;
//                    SetStateForEntryPoint command = new SetStateForEntryPoint(start.getModelElementId(), finish.getModelElementId());
//                    MainWindowController.getInstance()
//                            .getCommandStack()
//                            .storeAndExecute(command);
//                } else {
//                    endTool();
//                }

                event.consume();
            });

            customHandlerMap.put(MouseEvent.MOUSE_MOVED, event -> {
                if (event.getTarget() instanceof Node == false) {
                    event.consume();
                    return;
                }
                Node target = (Node) event.getTarget();

//                if (initial) {
//                    if (((Node)event.getTarget()).getParent() instanceof EntryPointContainer == false) {
//                        if (target.getScene().getCursor().equals(PlanDesigner.FORBIDDEN_CURSOR) == false) {
//                            previousCursor = target.getScene().getCursor();
//                            target.getScene().setCursor(PlanDesigner.FORBIDDEN_CURSOR);
//                        }
//                    } else {
//                        target.getScene().setCursor(previousCursor);
//                    }
//                } else {
//                    if (((Node)event.getTarget()).getParent() instanceof StateContainer == false) {
//                        if (target.getScene().getCursor().equals(PlanDesigner.FORBIDDEN_CURSOR) == false) {
//                            previousCursor = target.getScene().getCursor();
//                            target.getScene().setCursor(PlanDesigner.FORBIDDEN_CURSOR);
//                        }
//                    } else {
//                        target.getScene().setCursor(previousCursor);
//                    }
//                }
                event.consume();
            });
        }
    }

    @Override
    public DraggableHBox createToolUI() {
        DraggableHBox draggableHBox = new InitStateConnectionHBox();
        setDraggableHBox(draggableHBox);
        return draggableHBox;
    }

    private class InitStateConnectionHBox extends DraggableHBox {
        public InitStateConnectionHBox() {
            setIcon(Types.INITSTATECONNECTION);
            setOnDragDetected(Event::consume);
            setOnMouseClicked(event -> startTool());
        }
    }

//    /**
//     * This is a pseudo class because init transitions are no real objects.
//     */
//    static final class InitStateConnection extends PlanElementImpl {
//
//    }
}
