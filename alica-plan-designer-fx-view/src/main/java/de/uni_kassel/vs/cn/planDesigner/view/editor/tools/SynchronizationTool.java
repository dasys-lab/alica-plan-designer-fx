package de.uni_kassel.vs.cn.planDesigner.view.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.view.Types;
import de.uni_kassel.vs.cn.planDesigner.view.editor.container.AbstractPlanElementContainer;
import de.uni_kassel.vs.cn.planDesigner.view.model.PlanViewModel;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.StackPane;

import java.util.HashMap;
import java.util.Map;

public class SynchronizationTool extends AbstractTool{

    private HashMap<EventType, EventHandler> eventHandlerMap = new HashMap<>();
    private Node visualRepresentation;
    private boolean initial = true;

    public SynchronizationTool(TabPane workbench, PlanViewModel plan) {
        super(workbench, plan);
    }

    @Override
    protected void initHandlerMap() {

    }

    @Override
    public DraggableHBox createToolUI() {
        DraggableHBox draggableHBox = new DraggableHBox();
        draggableHBox.setIcon(Types.SYNCHRONIZATION);
        setDraggableHBox(draggableHBox);
        return draggableHBox;
    }

//    @Override
//    public Synchronisation createNewObject() {
//        return getAlicaFactory().createSynchronisation();
//    }
//
//    @Override
//    public void draw() {
//        ((PlanTab) planEditorTabPane.getSelectionModel().getSelectedItem()).getPlanEditorGroup().setupPlanVisualisation();
//    }

    @Override
    protected Map<EventType, EventHandler> getCustomHandlerMap() {
        if (eventHandlerMap.isEmpty()) {
            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_OVER, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
//                    localCoord = null;
//                    if (updateLocalCoords(event)) return;
//
//                    double x = localCoord.getX();
//                    double y = localCoord.getY();
//                    if (initial) {
//                        visualRepresentation.setLayoutX(x);
//                        visualRepresentation.setLayoutY(y);
//                        initial = false;
//                    }
//
//                    visualRepresentation.setTranslateX(x);
//                    visualRepresentation.setTranslateY(y);
//
//                    if (event.getGestureSource() != planEditorTabPane) {
//                        visualRepresentation.setTranslateX(x);
//                        visualRepresentation.setTranslateY(y);
//                    }
//                    System.out.println("X: " + x + " Y: " + y);
//                    event.consume();
                }
            });
            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_ENTERED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
//                    updateLocalCoords(event);
//                    if (event.getGestureSource() != planEditorTabPane && visualRepresentation == null) {
//                        visualRepresentation = new Circle(localCoord.getX(),localCoord.getY(), 10, new StateContainer().getVisualisationColor());
//                        ((PlanTab) planEditorTabPane.getSelectionModel().getSelectedItem()).getPlanEditorGroup().getChildren().add(visualRepresentation);
//                    }
                    event.consume();
                }
            });

            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_EXITED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
//                    ((PlanTab) planEditorTabPane.getSelectionModel().getSelectedItem()).getPlanEditorGroup().getChildren().remove(visualRepresentation);
                    visualRepresentation = null;
                }
            });


            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_RELEASED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    if (((Node)event.getTarget()).getParent() instanceof AbstractPlanElementContainer == false &&
                            event.getTarget() instanceof StackPane == false) {
                        event.consume();
                        endPhase();
                        return;
                    }
//                    ((PlanTab) planEditorTabPane.getSelectionModel().getSelectedItem()).getPlanEditorGroup().getChildren().remove(visualRepresentation);
//                    PlanModelVisualisationObject planModelVisualisationObject = ((PlanTab) planEditorTabPane.getSelectionModel().getSelectedItem()).getPlanEditorGroup().getPlanModelVisualisationObject();
//                    AddSynchronisationToPlan command = new AddSynchronisationToPlan(createNewObject(),
//                            planModelVisualisationObject);
//                    MainWindowController.getInstance()
//                            .getCommandStack()
//                            .storeAndExecute(command);
//                    MainWindowController.getInstance()
//                            .getCommandStack()
//                            .storeAndExecute(new ChangePosition(command.getNewlyCreatedPmlUiExtension(), command.getElementToEdit(),
//                                    (int) (localCoord.getX()),
//                                    (int) (localCoord.getY()), planModelVisualisationObject.getPlan()));
//                    endPhase();
                    initial = true;
                }
            });
        }
        return eventHandlerMap;
    }
}
