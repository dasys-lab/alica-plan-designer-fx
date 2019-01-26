package de.unikassel.vs.alica.planDesigner.view.editor.tools.transition;

import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiChangeAttributeEvent;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.container.AbstractPlanElementContainer;
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

import java.util.LinkedList;

public class SyncTransitionTool extends AbstractTool {

    private int initial = 0;
    private LinkedList<Point2D> bendPoints;
    private SynchronizationViewModel sync;
    private TransitionViewModel trans;

    private Cursor bendPointCursor = new AlicaCursor(AlicaCursor.Type.bendpoint_transition);

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
                if ((parent instanceof TransitionContainer && trans == null) || (parent instanceof SynchronizationContainer && sync == null)) {
                    setCursor(addCursor);
                } else if (target instanceof StackPane){
                    if (initial > 1) {
                        setCursor(bendPointCursor);
                    }
                    else {
                        setCursor(imageCursor);
                    }
                } else {
                    setCursor(forbiddenCursor);
                }
            }
        });


        customHandlerMap.put(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getTarget() instanceof ToolButton) {
                    return;
                }
                if (initial > 1) {
                    Node target = (Node) event.getTarget();
                    Node parent = target.getParent();
                    if ((parent instanceof TransitionContainer && trans == null) || (parent instanceof SynchronizationContainer && sync == null)) {
                        // SET ENDPOINT
                        if (parent instanceof TransitionContainer) {
                            // SET STARTPOINT
                            trans = (TransitionViewModel) ((AbstractPlanElementContainer) parent).getModelElement();
                        } else {
                            sync = (SynchronizationViewModel) ((AbstractPlanElementContainer) parent).getModelElement();
                        }

                        IGuiModificationHandler handler = MainWindowController.getInstance().getGuiModificationHandler();

                        GuiChangeAttributeEvent guiEvent = new GuiChangeAttributeEvent(GuiEventType.CHANGE_ELEMENT, Types.SYNCTRANSITION, null);
                        guiEvent.setElementId(sync.getId());

                        guiEvent.setAttributeName(Types.TRANSITION);
                        guiEvent.setNewValue(trans.getId());

                        guiEvent.setParentId(getPlanTab().getSerializableViewModel().getId());
                        handler.handle(guiEvent);

                        /*
                        for (Point2D point : bendPoints) {
                            GuiChangePositionEvent bendEvent = new GuiChangePositionEvent(GuiEventType.ADD_ELEMENT, Types.BENDPOINT, null);
                            bendEvent.setNewX((int) point.getX());
                            bendEvent.setNewY((int) point.getY());
                            bendEvent.setParentId(SyncTransitionTool.this.getPlanTab().getSerializableViewModel().getId());
                            HashMap<String, Long> related = new HashMap<>();
                            related.put(Types.TRANSITION, transitionID);
                            bendEvent.setRelatedObjects(related);
                            handler.handle(bendEvent);
                        }*/

                        reset();
                    } else {
                        // ADD BENDPOINT
                        Point2D eventTargetCoordinates = getLocalCoordinatesFromEvent(event);

                        if (eventTargetCoordinates == null) {
                            event.consume();
                        }
                        bendPoints.add(eventTargetCoordinates);
                    }
                } else {
                    Node target = (Node) event.getTarget();
                    Node parent = target.getParent();
                    if (!(target instanceof ToolButton)){
                        initial = 1;
                        try {
                            target = (Node) event.getTarget();
                            if (parent instanceof TransitionContainer) {
                                // SET STARTPOINT
                                trans = (TransitionViewModel) ((AbstractPlanElementContainer) parent).getModelElement();
                            } else if (parent instanceof SynchronizationContainer) {
                                sync = (SynchronizationViewModel) ((AbstractPlanElementContainer) parent).getModelElement();
                            }
                        } catch (ClassCastException e) {
                            e.printStackTrace();
                        }
                    }
                }
                initial++;
            }
        });
    }

    @Override
    public void endTool() {
        reset();
        super.endTool();
    }

    private void reset() {
        initial = 0;
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
