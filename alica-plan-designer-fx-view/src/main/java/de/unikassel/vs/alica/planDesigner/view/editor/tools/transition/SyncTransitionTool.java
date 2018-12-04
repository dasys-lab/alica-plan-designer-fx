package de.unikassel.vs.alica.planDesigner.view.editor.tools.transition;

import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.AbstractTool;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.DraggableHBox;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;

public class SyncTransitionTool extends AbstractTool {

    private boolean initial = true;
//    private SynchronizationContainer start;
//    private TransitionContainer finish;

    public SyncTransitionTool(TabPane workbench, PlanTab planTab) {
        super(workbench, planTab);
    }

//    public SyncTransition createNewObject() {
//        return new SyncTransition();
//    }

    public void draw() {
        PlanTab selectedItem = (PlanTab) planEditorTabPane.getSelectionModel().getSelectedItem();
//        if (selectedItem == null) {
//            ChangeListener<Tab> listener = new TabChangeListener(planEditorTabPane);
//            planEditorTabPane.getSelectionModel().selectedItemProperty().addListener(listener);
//        } else {
//            (selectedItem).getPlanEditorGroup().setupPlanVisualisation();
//        }
    }

    @Override
    protected void initHandlerMap() {
        if (customHandlerMap.isEmpty()) {
            customHandlerMap.put(MouseEvent.MOUSE_CLICKED, event -> {
//                if(((Node)event.getTarget()).getParent() instanceof SynchronizationContainer) {
//                    if (initial) {
//                        start = (SynchronizationContainer) ((Node)event.getTarget()).getParent();
//                        initial = false;
//                    } else {
//                        initial = true;
//                        endTool();
//                    }
//                } else if (((Node)event.getTarget()).getParent() instanceof TransitionContainer && initial == false) {
//                    finish = (TransitionContainer) ((Node)event.getTarget()).getParent();
//                    initial = true;
//
//                    AddTransitionToSynchronisation command =
//                            new AddTransitionToSynchronisation(start.getModelElementId(), finish.getModelElementId(),
//                                    ((AbstractEditorTab<PlanElement>) planEditorTabPane.getSelectionModel().getSelectedItem()).getEditable());
//                    MainWindowController.getInstance()
//                            .getCommandStack()
//                            .storeAndExecute(command);
//                    start = null;
//                    finish = null;
//                    endTool();
//                } else {
//                    initial = true;
//                    endTool();
//                }

            });

            customHandlerMap.put(MouseEvent.MOUSE_MOVED, event -> {
                if (event.getTarget() instanceof Node == false) {
                    event.consume();
                    return;
                }
                Node target = (Node) event.getTarget();

//                if (initial) {
//                    if (target.getParent() instanceof SynchronizationContainer == false) {
//                        if (target.getScene().getCursor().equals(PlanDesigner.FORBIDDEN_CURSOR) == false) {
//                            previousCursor = target.getScene().getCursor();
//                            target.getScene().setCursor(PlanDesigner.FORBIDDEN_CURSOR);
//                        }
//                    } else {
//                        target.getScene().setCursor(previousCursor);
//                    }
//                } else {
//                    if (((Node)event.getTarget()).getParent() instanceof TransitionContainer == false) {
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
        DraggableHBox draggableHBox = new DraggableHBox();
        draggableHBox.setIcon(Types.SYNCTRANSITION);
        return draggableHBox;
    }

//    /**
//     * This is a pseudo class because sync transitions are no real objects.
//     */
//    static final class SyncTransition extends PlanElementImpl {
//
//    }
//
//
//    private class SyncTransitionHBox extends DraggableHBox<SyncTransition> {
//        public SyncTransitionHBox() {
//            super(SyncTransitionTool.this.createNewObject(), SyncTransitionTool.this);
//            setOnDragDetected(Event::consume);
//            setOnMouseClicked(event -> startTool());
//        }
//    }
}
