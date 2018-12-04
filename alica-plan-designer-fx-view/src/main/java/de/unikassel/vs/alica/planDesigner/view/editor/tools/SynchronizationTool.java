package de.unikassel.vs.alica.planDesigner.view.editor.tools;

import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.container.AbstractPlanElementContainer;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.StackPane;

public class SynchronizationTool extends AbstractTool{

    private Node visualRepresentation;
    private boolean initial = true;

    public SynchronizationTool(TabPane workbench, PlanTab planTab) {
        super(workbench, planTab);
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
    protected void initHandlerMap(){
        if (customHandlerMap.isEmpty()) {
            customHandlerMap.put(MouseDragEvent.MOUSE_DRAG_OVER, new EventHandler<MouseDragEvent>() {
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
            customHandlerMap.put(MouseDragEvent.MOUSE_DRAG_ENTERED, new EventHandler<MouseDragEvent>() {
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

            customHandlerMap.put(MouseDragEvent.MOUSE_DRAG_EXITED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
//                    ((PlanTab) planEditorTabPane.getSelectionModel().getSelectedItem()).getPlanEditorGroup().getChildren().remove(visualRepresentation);
                    visualRepresentation = null;
                }
            });


            customHandlerMap.put(MouseDragEvent.MOUSE_DRAG_RELEASED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    if (((Node)event.getTarget()).getParent() instanceof AbstractPlanElementContainer == false &&
                            event.getTarget() instanceof StackPane == false) {
                        event.consume();
                        endTool();
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
//                    endTool();
                    initial = true;
                }
            });
        }
    }
}
