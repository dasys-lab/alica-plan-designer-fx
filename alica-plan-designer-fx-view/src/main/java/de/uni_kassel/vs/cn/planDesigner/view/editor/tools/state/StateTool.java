package de.uni_kassel.vs.cn.planDesigner.view.editor.tools.state;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.PlanModelVisualisationObject;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.State;
import de.uni_kassel.vs.cn.planDesigner.command.add.AddStateInPlan;
import de.uni_kassel.vs.cn.planDesigner.command.change.ChangePosition;
import de.uni_kassel.vs.cn.planDesigner.controller.MainWindowController;
import de.uni_kassel.vs.cn.planDesigner.view.editor.container.AbstractPlanElementContainer;
import de.uni_kassel.vs.cn.planDesigner.view.editor.container.StateContainer;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.PlanTab;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tools.AbstractTool;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

import java.util.HashMap;
import java.util.Map;

import static de.uni_kassel.vs.cn.generator.EMFModelUtils.getAlicaFactory;

/**
 * The {@link StateTool} is used for adding new states to the currently edited {@link de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan}.
 */
public class StateTool extends AbstractTool<State> {

    private HashMap<EventType, EventHandler> eventHandlerMap = new HashMap<>();
    private Node visualRepresentation;
    private boolean initial = true;

    public StateTool(TabPane workbench) {
        super(workbench);
    }

    @Override
    public State createNewObject() {
        return getAlicaFactory().createState();
    }

    @Override
    public void draw() {
        ((PlanTab)workbench.getSelectionModel().getSelectedItem()).getPlanEditorGroup().setupPlanVisualisation();
    }

    @Override
    protected Map<EventType, EventHandler> toolRequiredHandlers() {
        if (eventHandlerMap.isEmpty()) {
            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_OVER, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    localCoord = null;
                    if (updateLocalCoords(event)) return;

                    double x = localCoord.getX();
                    double y = localCoord.getY();
                    if (initial) {
                        visualRepresentation.setLayoutX(x);
                        visualRepresentation.setLayoutY(y);
                        initial = false;
                    }

                    visualRepresentation.setTranslateX(x);
                    visualRepresentation.setTranslateY(y);

                    System.out.println("X: " + x + " Y: " + y);
                    event.consume();
                }
            });
            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_ENTERED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    updateLocalCoords(event);
                    if (event.getGestureSource() != workbench && visualRepresentation == null) {
                        visualRepresentation = new Circle(localCoord.getX(),localCoord.getY(), 10, new StateContainer().getVisualisationColor());
                        ((PlanTab)workbench.getSelectionModel().getSelectedItem()).getPlanEditorGroup().getChildren().add(visualRepresentation);
                    }
                    event.consume();
                }
            });

            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_EXITED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    ((PlanTab)workbench.getSelectionModel().getSelectedItem()).getPlanEditorGroup().getChildren().remove(visualRepresentation);
                    visualRepresentation = null;
                }
            });


            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_RELEASED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    updateLocalCoords(event);
                    if (((Node)event.getTarget()).getParent() instanceof AbstractPlanElementContainer == false &&
                            event.getTarget() instanceof StackPane == false) {
                        event.consume();
                        endPhase();
                        return;
                    }

                    ((PlanTab)workbench.getSelectionModel().getSelectedItem()).getPlanEditorGroup().getChildren().remove(visualRepresentation);
                    PlanModelVisualisationObject planModelVisualisationObject = ((PlanTab) workbench.getSelectionModel().getSelectedItem()).getPlanEditorGroup().getPlanModelVisualisationObject();
                    AddStateInPlan command = new AddStateInPlan(planModelVisualisationObject,
                            createNewObject());
                    MainWindowController.getInstance()
                            .getCommandStack()
                            .storeAndExecute(command);
                    if (localCoord == null) {
                        localCoord = new Point2D(0,0);
                    }
                    MainWindowController.getInstance()
                            .getCommandStack()
                            .storeAndExecute(new ChangePosition(command.getNewlyCreatedPmlUiExtension(),command.getElementToEdit(),
                                    (int) (localCoord.getX()),
                                    (int) (localCoord.getY()), planModelVisualisationObject.getPlan()));
                    endPhase();
                    initial = true;
                }
            });
        }
        return eventHandlerMap;
    }

}
