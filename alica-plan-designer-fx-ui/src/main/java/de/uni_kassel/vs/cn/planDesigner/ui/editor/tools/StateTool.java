package de.uni_kassel.vs.cn.planDesigner.ui.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.PlanModelVisualisationObject;
import de.uni_kassel.vs.cn.planDesigner.alica.State;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUIExtensionModelFactory;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.EditorConstants;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.PlanEditorPane;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.HashMap;
import java.util.Map;

import static de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils.getAlicaFactory;

/**
 * The {@link StateTool} is used for adding new states to the currently edited {@link de.uni_kassel.vs.cn.planDesigner.alica.Plan}.
 */
public class StateTool extends Tool<State> {

    private HashMap<EventType, EventHandler> eventHandlerMap = new HashMap<>();
    private Node visualRepresentation;
    private boolean initial = true;

    public StateTool(PlanEditorPane workbench) {
        super(workbench);
    }

    @Override
    public State createNewObject() {
        return getAlicaFactory().createState();
    }

    @Override
    public void draw() {
        workbench.visualize();
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
                    event.consume();
                }
            });
            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_ENTERED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    if (event.getGestureSource() != workbench && visualRepresentation == null) {
                        visualRepresentation = new Circle(event.getX(),event.getY(), 10, Color.YELLOW);
                        workbench.getChildren().add(visualRepresentation);
                    }
                    event.consume();
                }
            });

            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_EXITED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    workbench.getChildren().remove(visualRepresentation);
                    visualRepresentation = null;
                }
            });


            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_RELEASED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    workbench.getChildren().remove(visualRepresentation);
                    visualRepresentation = null;
                    PlanModelVisualisationObject planModelVisualisationObject = workbench.getPlanModelVisualisationObject();
                    State newState = createNewObject();
                    newState.setName("MISSING NAME");
                    planModelVisualisationObject.getPlan().getStates().add(newState);

                    PmlUiExtension pmlUiExtension = PmlUIExtensionModelFactory.eINSTANCE.createPmlUiExtension();
                    pmlUiExtension.setXPos((int) (event.getX() - EditorConstants.PLAN_SHIFTING_PARAMETER));
                    pmlUiExtension.setYPos((int) (event.getY() - EditorConstants.PLAN_SHIFTING_PARAMETER));
                    planModelVisualisationObject.getPmlUiExtensionMap().getExtension().put(newState, pmlUiExtension);
                    endPhase();
                    initial = true;
                }
            });
        }
        return eventHandlerMap;
    }
}
