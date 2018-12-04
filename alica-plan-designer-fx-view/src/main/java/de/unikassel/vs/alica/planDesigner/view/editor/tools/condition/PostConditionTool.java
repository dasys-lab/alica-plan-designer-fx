package de.unikassel.vs.alica.planDesigner.view.editor.tools.condition;

import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.ConditionHBox;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.DraggableHBox;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;

public class PostConditionTool extends AbstractConditionTool {

    public PostConditionTool(TabPane workbench, PlanTab planTab) {
        super(workbench, planTab);
    }

    @Override
    public DraggableHBox createToolUI() {
        DraggableHBox draggableHBox = new DraggableHBox();
        draggableHBox.setIcon(Types.POSTCONDITION);
        setDraggableHBox(draggableHBox);
        return draggableHBox;
    }

//    @Override
//    public Condition createNewObject() {
//        return getAlicaFactory().createPostCondition();
//    }

    @Override
    protected void initHandlerMap(){
        if (customHandlerMap.isEmpty()) {
            customHandlerMap.put(MouseDragEvent.MOUSE_DRAG_ENTERED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    if (event.getTarget() instanceof Node == false) {
                        event.consume();
                        return;
                    }
//                    if (((Node)event.getTarget()).getParent() instanceof TerminalStateContainer && visualRepresentation == null) {
//                        visualRepresentation = new ImageView(new AlicaIcon(createNewObject().getClass().getSimpleName()));
//                        ((TerminalStateContainer)event.getTarget()).getChildren().add(visualRepresentation);
//                    } else if (((Node)event.getTarget()).getParent() instanceof AbstractPlanElementContainer
//                            || event.getTarget() instanceof ConditionHBox) {
//                            Cursor cursor = ((Node) event.getTarget()).getScene().getCursor();
//                        if (cursor.equals(PlanDesigner.FORBIDDEN_CURSOR) == false) {
//                            previousCursor = cursor;
//                            ((Node)event.getTarget()).getScene().setCursor(PlanDesigner.FORBIDDEN_CURSOR);
//                        }
//                    }
                    event.consume();
                }
            });

            customHandlerMap.put(MouseDragEvent.MOUSE_DRAG_EXITED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    if (event.getTarget() instanceof Node == false) {
                        event.consume();
                        return;
                    }
//                    if (((Node)event.getSource()).getParent() instanceof TerminalStateContainer == false) {
//                        ((Node)event.getSource()).getScene().setCursor(previousCursor);
//                    }
                    if (visualRepresentation != null) {
//                        ((TerminalStateContainer)event.getSource()).getChildren().remove(visualRepresentation);
                        visualRepresentation = null;
                    }
                }
            });


            customHandlerMap.put(MouseDragEvent.MOUSE_DRAG_RELEASED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    if (event.getSource() instanceof ConditionHBox) {
                        ((ConditionHBox)event.getSource()).getChildren().remove(visualRepresentation);
//                        Condition newCondition = createNewObject();
//                        newCondition.setPluginName(PluginManager.getInstance().getDefaultPlugin().getName());
//                        if (newCondition instanceof PostCondition == false) {
//                            AddConditionToPlan command = new AddConditionToPlan(((PlanTab) planEditorTabPane.getSelectionModel().getSelectedItem()).getEditable(),
//                                    newCondition);
//                            MainWindowController.getInstance()
//                                    .getCommandStack()
//                                    .storeAndExecute(command);
//                        }
                    }
                    endTool();
                }
            });

            customHandlerMap.put(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    endTool();
                }
            });
        }
    }
}
