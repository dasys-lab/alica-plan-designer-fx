package de.unikassel.vs.alica.planDesigner.view.editor.tools.transition;

import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.container.BendpointContainer;
import de.unikassel.vs.alica.planDesigner.view.editor.container.StateContainer;
import de.unikassel.vs.alica.planDesigner.view.editor.container.TransitionContainer;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.AbstractTool;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.ToolButton;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaCursor;
import de.unikassel.vs.alica.planDesigner.view.model.PlanViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.StateViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.TransitionViewModel;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.util.HashMap;
import java.util.LinkedList;

import static java.lang.Math.*;

public class TransitionTool extends AbstractTool {

    private LinkedList<Point2D> bendPoints = new LinkedList<>();

    private StateViewModel inState = null;
    private Cursor bendPointCursor = new AlicaCursor(AlicaCursor.Type.bendpoint_transition, 8, 8);
    private Cursor bendPointDeleteCursor = new AlicaCursor(AlicaCursor.Type.bendpoint_transition_delete, 8, 8);

    public TransitionTool(TabPane workbench, PlanTab planTab, ToggleGroup group) {
        super(workbench, planTab, group);
    }

    @Override
    protected void initHandlerMap() {
        if (customHandlerMap.isEmpty()) {
            customHandlerMap.put(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Node target = (Node) event.getTarget();
                    Parent parent = target.getParent();
                    if (parent instanceof StateContainer)  {
                        //no transition to itself
                        if(inState == ((StateContainer) parent).getState()){
                            setCursor(forbiddenCursor);
                        } else { setCursor(addCursor); }
                        //no duplicate transition allowed
                        if (inState != null) {
                            if (((StateContainer) parent).getState().getInTransitions().size() != 0 && inState.getOutTransitions().size() != 0) {
                                for (TransitionViewModel transitionViewModel : ((StateContainer) parent).getState().getInTransitions()) {
                                    if (transitionViewModel.getInState() == inState) {
                                        setCursor(forbiddenCursor);
                                    }
                                }
                            }
                        } else { setCursor(addCursor); }
                    } else if (target instanceof StackPane) {
                        if (inState != null) {
                            setCursor(bendPointCursor);
                        } else {
                            setCursor(imageCursor);
                        }
                    }else if (parent instanceof BendpointContainer){
                        if (inState == null) {
                            setCursor(bendPointDeleteCursor);
                        } else {
                            setCursor(forbiddenCursor);
                        }
                    }else if (parent instanceof TransitionContainer){
                        if (inState == null) {
                            setCursor(bendPointCursor);
                        } else {
                            setCursor(forbiddenCursor);
                        }
                    } else {
                        setCursor(forbiddenCursor);
                    }
                    //System.out.println(target.getClass().getName());
                }
            });

            customHandlerMap.put(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getTarget() instanceof ToolButton || !(event.getTarget() instanceof Node) ||
                            event.getButton() == MouseButton.SECONDARY) {
                        return;
                    }

                    Node target = (Node) event.getTarget();
                    Node parent = target.getParent();
                    IGuiModificationHandler handler = MainWindowController.getInstance().getGuiModificationHandler();

                    if (inState != null && inState != ((StateContainer) parent).getState()) {
                        if (parent instanceof StateContainer) {
                            // SET ENDPOINT
                            StateViewModel outState = ((StateContainer) parent).getState();
                            //no duplicate transition allowed
                            for (TransitionViewModel transitionViewModel: outState.getInTransitions()) {
                                if(inState.getOutTransitions().contains(transitionViewModel)){
                                    return;
                                }
                            }
                            GuiModificationEvent guiEvent = new GuiModificationEvent(GuiEventType.ADD_ELEMENT, Types.TRANSITION, null);

                            HashMap<String, Long> relatedObjects = new HashMap<>();
                            relatedObjects.put(Types.INSTATE, inState.getId());
                            relatedObjects.put(Types.OUTSTATE, outState.getId());

                            guiEvent.setRelatedObjects(relatedObjects);
                            guiEvent.setParentId(TransitionTool.this.planTab.getSerializableViewModel().getId());
                            handler.handle(guiEvent);

                            long transitionID = 0;
                            for (TransitionViewModel transition : ((PlanViewModel) TransitionTool.this.planTab.getSerializableViewModel()).getTransitions()) {
                                if (transition.getOutState().getId() == outState.getId()
                                        && transition.getInState().getId() == inState.getId()) {
                                    transitionID = transition.getId();
                                }
                            }

                            for (Point2D point : bendPoints) {
                                GuiModificationEvent bendEvent = new GuiModificationEvent(GuiEventType.ADD_ELEMENT, Types.BENDPOINT, null);
                                bendEvent.setX((int) point.getX());
                                bendEvent.setY((int) point.getY());
                                bendEvent.setParentId(TransitionTool.this.planTab.getSerializableViewModel().getId());
                                HashMap<String, Long> related = new HashMap<>();
                                related.put(Types.TRANSITION, transitionID);
                                bendEvent.setRelatedObjects(related);
                                handler.handle(bendEvent);
                            }

                            TransitionTool.this.reset();
                        } else {
                            // ADD BENDPOINT ON CREATE
                            Point2D eventTargetCoordinates = TransitionTool.this.getLocalCoordinatesFromEvent(event);
                            if (eventTargetCoordinates == null) {
                                event.consume();
                            }
                            bendPoints.add(eventTargetCoordinates);
                        }
                    } else {
                        if (parent instanceof BendpointContainer) {
                            // REMOVE BENDPOINT
                            BendpointContainer bpC = (BendpointContainer) parent;
                            TransitionContainer tC = bpC.getTransitionContainer();

                            GuiModificationEvent deleteBendPointEvent = new GuiModificationEvent(GuiEventType.DELETE_ELEMENT, Types.BENDPOINT, null);
                            deleteBendPointEvent.setElementId(tC.getContainedElement().getId());
                            HashMap<String, Long> bendpoint = new HashMap<String, Long>();
                            bendpoint.put(bpC.getContainedElement().getType(), bpC.getContainedElement().getId());
                            deleteBendPointEvent.setRelatedObjects(bendpoint);
                            deleteBendPointEvent.setParentId(TransitionTool.this.planTab.getSerializableViewModel().getId());
                            handler.handle(deleteBendPointEvent);

                        }else if (parent instanceof TransitionContainer){
                            // ADD BENDPOINT
                            Point2D point = TransitionTool.this.getLocalCoordinatesFromEvent(event);
                            if (point == null) {
                                event.consume();
                            }

                            TransitionContainer transition = (TransitionContainer) parent;

                            GuiModificationEvent bendEvent = new GuiModificationEvent(GuiEventType.ADD_ELEMENT, Types.BENDPOINT, null);
                            bendEvent.setX((int) point.getX());
                            bendEvent.setY((int) point.getY());
                            bendEvent.setParentId(TransitionTool.this.planTab.getSerializableViewModel().getId());

                            // get the index of the bendpoint
                            long index = 0;

                            if(transition.getBendpoints().size()==0){

                            } else {
                                Point2D p1 = new Point2D(transition.getContainedElement().getInState().getXPosition(), transition.getContainedElement().getInState().getYPosition());
                                Point2D p2 = new Point2D(transition.getBendpoints().get(0).getLayoutX(), transition.getBendpoints().get(0).getLayoutY());
                                Point2D p3 = new Point2D((float) point.getX(), (float) point.getY());
                                double distance = distancePointToLineSegment(p1, p3, p2);
                                if(distance < 10){

                                } else {
                                    if(transition.getBendpoints().size() == 1){
                                        index = 1;
                                    } else {
                                        for (int i = 1; i < transition.getBendpoints().size(); i++) {
                                            p1 = p2;
                                            p2 = new Point2D(transition.getBendpoints().get(i).getLayoutX(), transition.getBendpoints().get(i).getLayoutY());
                                            double distancee = distancePointToLineSegment(p1, p3, p2);
                                            if (distancee < 10) {
                                                index = i;
                                                break;
                                            }
                                        }
                                        if (index == 0) {
                                            index = transition.getBendpoints().size();
                                        }
                                    }
                                }
                            }

                            HashMap<String, Long> related = new HashMap<>();
                            related.put(Types.TRANSITION, ((TransitionContainer) parent).getContainedElement().getId());
                            related.put("index", index);
                            bendEvent.setRelatedObjects(related);
                            handler.handle(bendEvent);
                        } else {
                            try {
                                if (parent instanceof StateContainer) {
                                    // SET STARTPOINT
                                    inState = ((StateContainer) parent).getState();
                                }
                            } catch (ClassCastException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
        }
    }

    private double distancePointToLineSegment(Point2D a, Point2D p, Point2D b) {

        //Vector A-B
        double abx = b.getX() - a.getX();
        double aby = b.getY() - a.getY();

        //Vector A-P
        double apx = p.getX() - a.getX();
        double apy = p.getY() - a.getY();

        double angle1 = atan2(apy, apx);
        double angle2 = atan2(aby, abx);

        double alpha = angle1 - angle2;
        if(alpha < -PI){
            alpha += 2.0*PI;
        } else if(alpha > PI){
            alpha -= 2.0*PI;
        }

        double distanceAtoP = sqrt(apx*apx + apy*apy);
        if(alpha >PI/2 || alpha < -PI/2){
            return distanceAtoP;
        }

        double dist1 = cos(alpha) *distanceAtoP;
        if(dist1 > sqrt(abx*abx + aby*aby)){
            return sqrt(pow(p.getX() - b.getY(), 2)+ pow(p.getY() - b.getY(), 2));
        } else {
            return abs(sin(alpha)) * distanceAtoP;
        }
    }

    @Override
    public void endTool() {
        reset();
        super.endTool();
    }

    private void reset() {
        inState = null;
        bendPoints.clear();
    }

    @Override
    public ToolButton createToolUI() {
        ToolButton toolButton = new ToolButton();
        Tooltip tooltip = new Tooltip(Types.TRANSITION);
        toolButton.setTooltip(tooltip);
        toolButton.setIcon(Types.TRANSITION);
        setToolButton(toolButton);

        imageCursor = new AlicaCursor(AlicaCursor.Type.transition, 8, 8);
        forbiddenCursor = new AlicaCursor(AlicaCursor.Type.forbidden_transition, 8, 8);
        addCursor = new AlicaCursor(AlicaCursor.Type.add_transition, 8, 8);
        return toolButton;
    }
}
