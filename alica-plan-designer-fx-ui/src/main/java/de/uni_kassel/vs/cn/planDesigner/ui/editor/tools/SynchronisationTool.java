package de.uni_kassel.vs.cn.planDesigner.ui.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.PlanModelVisualisationObject;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.add.AddSynchronisationToPlan;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.change.ChangePosition;
import de.uni_kassel.vs.cn.planDesigner.alica.Synchronisation;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.AbstractPlanElementContainer;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.StateContainer;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.PlanTab;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

import java.util.HashMap;
import java.util.Map;

import static de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils.getAlicaFactory;

/**
 * Created by marci on 08.03.17.
 */
public class SynchronisationTool extends AbstractTool<Synchronisation> {

    private HashMap<EventType, EventHandler> eventHandlerMap = new HashMap<>();
    private Node visualRepresentation;
    private boolean initial = true;

    public SynchronisationTool(TabPane workbench) {
        super(workbench);
    }

    @Override
    public Synchronisation createNewObject() {
        return getAlicaFactory().createSynchronisation();
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

                    if (event.getGestureSource() != workbench) {
                        visualRepresentation.setTranslateX(x);
                        visualRepresentation.setTranslateY(y);
                    }
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
                    if (((Node)event.getTarget()).getParent() instanceof AbstractPlanElementContainer == false &&
                            event.getTarget() instanceof StackPane == false) {
                        event.consume();
                        endPhase();
                        return;
                    }
                    ((PlanTab)workbench.getSelectionModel().getSelectedItem()).getPlanEditorGroup().getChildren().remove(visualRepresentation);
                    PlanModelVisualisationObject planModelVisualisationObject = ((PlanTab) workbench.getSelectionModel().getSelectedItem()).getPlanEditorGroup().getPlanModelVisualisationObject();
                    AddSynchronisationToPlan command = new AddSynchronisationToPlan(createNewObject(),
                            planModelVisualisationObject);
                    MainController.getInstance()
                            .getCommandStack()
                            .storeAndExecute(command);
                    MainController.getInstance()
                            .getCommandStack()
                            .storeAndExecute(new ChangePosition(command.getNewlyCreatedPmlUiExtension(), command.getElementToEdit(),
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
