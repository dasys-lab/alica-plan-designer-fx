package de.uni_kassel.vs.cn.planDesigner.ui.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.PlanDesigner;
import de.uni_kassel.vs.cn.planDesigner.alica.EntryPoint;
import de.uni_kassel.vs.cn.planDesigner.common.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.controller.EntryPointCreatorDialogController;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.EntryPointContainer;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.PlanTab;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils.getAlicaFactory;

/**
 * Created by marci on 05.01.17.
 */
public class EntryPointTool extends AbstractTool<EntryPoint> {

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
                    ((PlanTab)workbench.getSelectionModel().getSelectedItem()).getPlanEditorPane().getChildren().remove(visualRepresentation);
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("entryPointCreatorDialog.fxml"));
                    try {
                        Parent rootOfDialog = fxmlLoader.load();
                        EntryPointCreatorDialogController controller = fxmlLoader.getController();
                        controller.setX((int) event.getX());
                        controller.setY((int) event.getY());
                        Stage stage = new Stage();
                        stage.setResizable(false);
                        stage.setTitle(I18NRepo.getString("label.choose.task"));
                        stage.setScene(new Scene(rootOfDialog));
                        stage.initModality(Modality.WINDOW_MODAL);
                        stage.initOwner(PlanDesigner.getPrimaryStage());
                        stage.showAndWait();
                        draw();
                        initial = true;
                    } catch (IOException e) {
                        // if the helper window is not loadable something is really wrong here
                        e.printStackTrace();
                        System.exit(1);
                    }

                }
            });
        }
        return eventHandlerMap;
    }
}
