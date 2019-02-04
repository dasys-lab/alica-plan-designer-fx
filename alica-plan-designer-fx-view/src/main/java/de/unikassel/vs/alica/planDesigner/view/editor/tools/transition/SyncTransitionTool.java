package de.unikassel.vs.alica.planDesigner.view.editor.tools.transition;

import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiChangePositionEvent;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.container.SynchronizationContainer;
import de.unikassel.vs.alica.planDesigner.view.editor.container.TransitionContainer;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.AbstractTool;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.ToolButton;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaCursor;
import de.unikassel.vs.alica.planDesigner.view.model.SynchronizationViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.TransitionViewModel;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.util.HashMap;
import java.util.LinkedList;

public class SyncTransitionTool extends AbstractTool {

    private SynchronizationViewModel sync;
    private TransitionViewModel trans;
    private LinkedList<Point2D> bendPoints;

    private Cursor bendPointCursor = new AlicaCursor(AlicaCursor.Type.bendpoint_synctransition);

    public SyncTransitionTool(TabPane workbench, PlanTab planTab, ToggleGroup group) {
        super(workbench, planTab, group);
        bendPoints = new LinkedList<>();
    }

    @Override
    protected void initHandlerMap() {
        customHandlerMap.put(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Node target = (Node) event.getTarget();
                Parent parent = target.getParent();
                if (sync == null) {
                    if (parent instanceof SynchronizationContainer) {
                        setCursor(addCursor);
                    } else {
                        setCursor(forbiddenCursor);
                    }
                } else {
                    if (parent instanceof TransitionContainer) {
                        setCursor(addCursor);
                    } else if (parent instanceof StackPane) {
                        setCursor(bendPointCursor);
                    } else {
                        setCursor(forbiddenCursor);
                    }
                }
            }
        });


        customHandlerMap.put(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Node target = (Node) event.getTarget();
                    Node parent = target.getParent();

                    if (target instanceof ToolButton) {
                        completeAddSync();
                    } else if (parent instanceof SynchronizationContainer) {
                        if (sync == null) {
                            sync = (SynchronizationViewModel) ((SynchronizationContainer) parent).getModelElement();
                        } else {
                            if (!sync.equals(((SynchronizationContainer) parent).getModelElement())) {
                                completeAddSync();
                                sync = (SynchronizationViewModel) ((SynchronizationContainer) parent).getModelElement();
                            } else {
                                completeAddSync();
                            }
                        }
                    } else if (parent instanceof TransitionContainer) {
                        if (sync != null) {
                            if (trans == null) {
                                trans = (TransitionViewModel) ((TransitionContainer) parent).getModelElement();
                                completeAddSync();
                            }
                        }
                    } else if (parent instanceof StackPane) {
                        if (sync != null) {
                            Point2D eventTargetCoordinates = getLocalCoordinatesFromEvent(event);
                            if (eventTargetCoordinates == null) {
                                event.consume();
                            }
                            bendPoints.add(eventTargetCoordinates);
                        }
                    }
                } catch (ClassCastException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void completeAddSync() {
        if (sync == null || trans == null) {
            return;
        }

        GuiModificationEvent event = new GuiModificationEvent(GuiEventType.ADD_ELEMENT, Types.SYNCTRANSITION, null);
        event.setElementId(sync.getId());
        event.setParentId(getPlanTab().getSerializableViewModel().getId());
        HashMap<String, Long> related = new HashMap<>();
        related.put(Types.TRANSITION, trans.getId());
        event.setRelatedObjects(related);

        IGuiModificationHandler handler = MainWindowController.getInstance().getGuiModificationHandler();
        handler.handle(event);

        // TODO handle bendpoints

        reset();
    }

    @Override
    public void endTool() {
        reset();
        super.endTool();
    }

    private void reset() {
        sync = null;
        trans = null;
        bendPoints.clear();
    }

    @Override
    public ToolButton createToolUI() {
        ToolButton toolButton = new ToolButton();
        toolButton.setIcon(Types.SYNCTRANSITION);
        setToolButton(toolButton);
        imageCursor = new AlicaCursor(AlicaCursor.Type.synctransition);
        forbiddenCursor = new AlicaCursor(AlicaCursor.Type.forbidden_synctransition);
        addCursor = new AlicaCursor(AlicaCursor.Type.add_synctransition);
        return toolButton;
    }
}
