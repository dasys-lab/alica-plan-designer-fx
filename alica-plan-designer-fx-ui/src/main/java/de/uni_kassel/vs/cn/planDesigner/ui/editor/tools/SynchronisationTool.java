package de.uni_kassel.vs.cn.planDesigner.ui.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.add.AddStateInPlan;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.add.AddSynchronisationToPlan;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.change.ChangePosition;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.alica.Synchronisation;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.StateContainer;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.PlanTab;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseDragEvent;
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
        ((PlanTab)workbench.getSelectionModel().getSelectedItem()).getPlanEditorPane().setupPlanVisualisation();
    }

    @Override
    protected Map<EventType, EventHandler> toolRequiredHandlers() {
        if (eventHandlerMap.isEmpty()) {
            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_OVER, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    if (initial) {
                        visualRepresentation.setLayoutX(event.getX());
                        visualRepresentation.setLayoutY(event.getY());
                        initial = false;
                    }

                    if (event.getGestureSource() != workbench) {
                        visualRepresentation.setTranslateX(event.getX());
                        visualRepresentation.setTranslateY(event.getY());
                    }
                    System.out.println("X: " + event.getX() + " Y: " + event.getY());
                    event.consume();
                }
            });
            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_ENTERED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    if (event.getGestureSource() != workbench && visualRepresentation == null) {
                        visualRepresentation = new Circle(event.getX(),event.getY(), 10, new StateContainer().getVisualisationColor());
                        ((PlanTab)workbench.getSelectionModel().getSelectedItem()).getPlanEditorPane().getChildren().add(visualRepresentation);
                    }
                    event.consume();
                }
            });

            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_EXITED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    ((PlanTab)workbench.getSelectionModel().getSelectedItem()).getPlanEditorPane().getChildren().remove(visualRepresentation);
                    visualRepresentation = null;
                }
            });


            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_RELEASED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    ((PlanTab)workbench.getSelectionModel().getSelectedItem()).getPlanEditorPane().getChildren().remove(visualRepresentation);
                    AddSynchronisationToPlan command = new AddSynchronisationToPlan(createNewObject(),
                            ((PlanTab)workbench.getSelectionModel().getSelectedItem()).getPlanEditorPane().getPlanModelVisualisationObject());
                    MainController.getInstance()
                            .getCommandStack()
                            .storeAndExecute(command);
                    MainController.getInstance()
                            .getCommandStack()
                            .storeAndExecute(new ChangePosition(command.getNewlyCreatedPmlUiExtension(), command.getElementToEdit(),
                                    (int) (event.getX()),
                                    (int) (event.getY())));
                    endPhase();
                    initial = true;
                }
            });
        }
        return eventHandlerMap;
    }
}
