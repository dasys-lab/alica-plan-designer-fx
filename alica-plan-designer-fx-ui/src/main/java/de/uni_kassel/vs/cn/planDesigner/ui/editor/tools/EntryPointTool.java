package de.uni_kassel.vs.cn.planDesigner.ui.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.PlanDesigner;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.add.AddEntryPointInPlan;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.change.ChangePosition;
import de.uni_kassel.vs.cn.planDesigner.alica.EntryPoint;
import de.uni_kassel.vs.cn.planDesigner.alica.Task;
import de.uni_kassel.vs.cn.planDesigner.common.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.ui.components.TaskComboBox;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.PlanTab;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.EntryPointContainer;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

import static de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils.getAlicaFactory;

/**
 * Created by marci on 05.01.17.
 */
public class EntryPointTool extends Tool<EntryPoint> {

    private Map<EventType, EventHandler> eventHandlerMap = new HashMap<>();
    private boolean initial = true;
    private Node visualRepresentation;

    public EntryPointTool(TabPane workbench) {
        super(workbench);
    }

    @Override
    public EntryPoint createNewObject() {
        return getAlicaFactory().createEntryPoint();
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
                        visualRepresentation = new Circle(event.getX(),event.getY(), 10, new EntryPointContainer().getVisualisationColor());
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
                    endPhase();
                    Stage stage = new Stage();
                    stage.setTitle(I18NRepo.getString("label.choose.task"));
                    TaskComboBox taskComboBox = new TaskComboBox();
                    Button button = new Button();
                    button.setText(I18NRepo.getString("action.confirm"));
                    button.setOnAction(e -> {
                        Task selectedItem = taskComboBox.getSelectionModel().getSelectedItem();
                        if (selectedItem != null) {
                            ((PlanTab)workbench.getSelectionModel().getSelectedItem()).getPlanEditorPane().getChildren().remove(visualRepresentation);
                            AddEntryPointInPlan command = new AddEntryPointInPlan(((PlanTab)workbench.getSelectionModel().getSelectedItem()).getPlanEditorPane().getPlanModelVisualisationObject());
                            MainController.getInstance()
                                    .getCommandStack()
                                    .storeAndExecute(command);
                            command.getElementToEdit().setTask(selectedItem);
                            MainController.getInstance()
                                    .getCommandStack()
                                    .storeAndExecute(new ChangePosition(command.getNewlyCreatedPmlUiExtension(),command.getElementToEdit(),
                                            (int) (event.getX()),
                                            (int) (event.getY())));
                            stage.close();
                            draw();
                        }
                    });
                    stage.setScene(new Scene(new HBox(taskComboBox, button)));
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(PlanDesigner.getPrimaryStage());
                    stage.showAndWait();
                    initial = true;
                }
            });
        }
        return eventHandlerMap;
    }
}
