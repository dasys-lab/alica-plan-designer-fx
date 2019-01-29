package de.unikassel.vs.alica.planDesigner.view.editor.tools;

import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiChangePositionEvent;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanEditorGroup;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaCursor;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class SynchronizationTool extends AbstractTool {

    private Node visualRepresentation;
    private boolean initial = true;

    public SynchronizationTool(TabPane workbench, PlanTab planTab, ToggleGroup group) {
        super(workbench, planTab, group);
    }

    @Override
    public ToolButton createToolUI() {
        ToolButton tollButton = new ToolButton();
        tollButton.setIcon(Types.SYNCHRONIZATION);
        setToolButton(tollButton);
        imageCursor = new AlicaCursor(AlicaCursor.Type.synchronization);
        forbiddenCursor = new AlicaCursor(AlicaCursor.Type.forbidden_synchronization);
        addCursor = new AlicaCursor(AlicaCursor.Type.add_synchronization);
        return tollButton;
    }

    @Override
    protected void initHandlerMap() {
        I18NRepo i18NRepo = I18NRepo.getInstance();

        if (customHandlerMap.isEmpty()) {
            customHandlerMap.put(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Node target = (Node) event.getTarget();
                    Parent parent = target.getParent();
                    if (parent instanceof StackPane) {
                        setCursor(addCursor);
                    } else {
                        setCursor(forbiddenCursor);
                    }
                }
            });

            customHandlerMap.put(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    if (handleNonPrimaryButtonEvent(event)) {
                        return;
                    }

                    Point2D eventTargetCoordinates = getLocalCoordinatesFromEvent(event);
                    if (eventTargetCoordinates == null) {
                        event.consume();
                        return;
                    }

                    IGuiModificationHandler handler = MainWindowController.getInstance().getGuiModificationHandler();

                    GuiChangePositionEvent guiEvent = new GuiChangePositionEvent(GuiEventType.ADD_ELEMENT, Types.SYNCHRONIZATION, "Sync Default");
                    guiEvent.setComment("");
                    guiEvent.setNewX((int) eventTargetCoordinates.getX());
                    guiEvent.setNewY((int) eventTargetCoordinates.getY());
                    guiEvent.setParentId(getPlanTab().getSerializableViewModel().getId());
                    handler.handle(guiEvent);
                }
            });
        }

        /*
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
//                                    (int) (localCoord.getY()), planModelVisualisationObject.getPlanViewModel()));
//                    endTool();
                    initial = true;
                }
            });
        }*/
    }
}
