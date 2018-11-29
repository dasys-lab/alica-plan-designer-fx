package de.unikassel.vs.alica.planDesigner.view.editor.tools.transition;

import de.unikassel.vs.alica.planDesigner.events.GuiChangePositionEvent;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.container.StateContainer;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.AbstractTool;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.DraggableHBox;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaIcon;
import de.unikassel.vs.alica.planDesigner.view.model.StateViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.TransitionViewModel;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;

import java.util.HashMap;
import java.util.LinkedList;

public class TransitionTool extends AbstractTool {

    private int initial = 0;
    private LinkedList<Point2D> bendPoints = new LinkedList<>();

    private StateViewModel inState;

    public TransitionTool(TabPane workbench, PlanTab planTab) {
        super(workbench, planTab);
    }

    @Override
    protected void initHandlerMap() {
        if (customHandlerMap.isEmpty()) {
            ImageCursor imageCursor = new ImageCursor(new AlicaIcon(Types.TRANSITION, AlicaIcon.Size.SMALL), 0, 8);
            ImageCursor forbiddenCursor = new ImageCursor(new AlicaIcon("FORBIDDEN", AlicaIcon.Size.SMALL), 8, 8);
            ImageCursor addCursor = new ImageCursor(new AlicaIcon("ADD", AlicaIcon.Size.SMALL), 8, 8);

            customHandlerMap.put(MouseDragEvent.MOUSE_DRAG_ENTERED, (EventHandler<MouseDragEvent>) event -> {
                planEditorTabPane.getScene().setCursor(imageCursor);
            });

            customHandlerMap.put(MouseDragEvent.MOUSE_DRAG_OVER, (EventHandler<MouseDragEvent>) event -> {
                Node target = (Node) event.getTarget();
                if (!(target.getParent() instanceof StateContainer)) {
                    planEditorTabPane.getScene().setCursor(forbiddenCursor);
                } else {
                    planEditorTabPane.getScene().setCursor(imageCursor);
                }
            });

            customHandlerMap.put(MouseDragEvent.MOUSE_DRAG_RELEASED, (EventHandler<MouseDragEvent>) event -> {
                initial = 1;
                Node target = (Node) event.getTarget();
                if ((target.getParent() instanceof StateContainer)) {
                    // SET STARTPOINT
                    inState = ((StateContainer) ((Node)event.getTarget()).getParent()).getState();
                }
            });

            customHandlerMap.put(MouseEvent.MOUSE_CLICKED, (EventHandler<MouseEvent>) event -> {
                if (initial > 1) {
                    Node target = (Node) event.getTarget();
                    Node parent = target.getParent();
                    if (parent instanceof StateContainer) {
                        // SET ENDPOINT
                        StateViewModel outState = ((StateContainer) ((Node) event.getTarget()).getParent()).getState();

                        IGuiModificationHandler handler = MainWindowController.getInstance().getGuiModificationHandler();

                        GuiModificationEvent guiEvent = new GuiModificationEvent(GuiEventType.ADD_ELEMENT, Types.TRANSITION, null);

                        HashMap<String, Long> relatedObjects = new HashMap<>();
                        relatedObjects.put(TransitionViewModel.INSTATE, inState.getId());
                        relatedObjects.put(TransitionViewModel.OUTSTATE, outState.getId());

                        guiEvent.setRelatedObjects(relatedObjects);
                        guiEvent.setParentId(getPlanTab().getPlan().getId());
                        handler.handle(guiEvent);

                        long transitionID = 0;
                        for (TransitionViewModel transition : getPlanTab().getPlan().getTransitions()) {
                            if (transition.getOutState().getId() == outState.getId()
                             && transition.getInState().getId() == inState.getId()) {
                                transitionID = transition.getId();
                            }
                        }

                        for (Point2D point : bendPoints) {
                            GuiChangePositionEvent bendEvent = new GuiChangePositionEvent(GuiEventType.ADD_ELEMENT, Types.BENDPOINT, null);
                            bendEvent.setNewX((int) point.getX());
                            bendEvent.setNewY((int) point.getY());
                            bendEvent.setParentId(getPlanTab().getPlan().getId());
                            HashMap<String, Long> related = new HashMap<>();
                            related.put(Types.TRANSITION, transitionID);
                            bendEvent.setRelatedObjects(related);
                            handler.handle(bendEvent);
                        }

                        endPhase();
                    } else {
                        // ADD BENDPOINT
                        Point2D eventTargetCoordinates = getLocalCoordinatesFromEvent(event);

                        if (eventTargetCoordinates == null) {
                            event.consume();
                        }
                        bendPoints.add(eventTargetCoordinates);
                    }
                }
                initial++;
            });

            customHandlerMap.put(MouseEvent.MOUSE_MOVED, (EventHandler<MouseEvent>) event -> {
                if(inState != null) {
                    Node target = (Node) event.getTarget();
                    if (target.getParent() instanceof StateContainer) {
                        planEditorTabPane.getScene().setCursor(imageCursor);
                    } else {
                        planEditorTabPane.getScene().setCursor(addCursor);
                    }
                }
            });

            customHandlerMap.put(KeyEvent.KEY_RELEASED, (EventHandler<KeyEvent>) event -> {
                if (initial != 0) {
                    if (event.getCode() == KeyCode.ESCAPE) {
                        endPhase();
                    }
                }
            });

            defaultHandlers().remove(MouseDragEvent.MOUSE_RELEASED);
        }
    }

    @Override
    public void endPhase() {
        super.endPhase();
        initial = 0;
        inState = null;
        bendPoints.clear();
        planEditorTabPane.getScene().setCursor(previousCursor);
        getDraggableHBox().setEffect(null);
    }

    @Override
    public DraggableHBox createToolUI() {
        DraggableHBox draggableHBox = new DraggableHBox();
        draggableHBox.setIcon(Types.TRANSITION);
        setDraggableHBox(draggableHBox);
        return draggableHBox;
    }
}
