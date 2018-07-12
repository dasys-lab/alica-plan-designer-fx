package de.uni_kassel.vs.cn.planDesigner.view.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.view.Types;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseDragEvent;

import java.util.HashMap;
import java.util.Map;


public class BehaviourTool extends AbstractTool {

    private HashMap<EventType, EventHandler> eventHandlerMap = new HashMap<>();

    public BehaviourTool(TabPane workbench) {
        super(workbench);
    }

    @Override
    protected void initHandlerMap() {

    }

    @Override
    public DraggableHBox createToolUI() {
        DraggableHBox draggableHBox = new DraggableHBox();
        draggableHBox.setIcon(Types.BEHAVIOUR);
        return draggableHBox;
    }

//    @Override
//    public Behaviour createNewObject() {
//        return getAlicaFactory().createBehaviour();
//    }
//
//    @Override
//    public void draw() {
//        ((PlanTab) planEditorTabPane.getSelectionModel().getSelectedItem()).getPlanEditorGroup().setupPlanVisualisation();
//    }

    @Override
    protected Map<EventType, EventHandler> getCustomHandlerMap() {
        if (eventHandlerMap.isEmpty()) {
            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_OVER, event -> {
                if (event.getTarget() instanceof Node == false) {
                    event.consume();
                    return;
                }
                Node target = (Node) event.getTarget();

//                if (((Node) event.getTarget()).getParent() instanceof TerminalStateContainer ||
//                        ((Node) event.getTarget()).getParent() instanceof StateContainer == false) {
//                    if (target.getScene().getCursor().equals(PlanDesigner.FORBIDDEN_CURSOR) == false) {
//                        previousCursor = target.getScene().getCursor();
//                        target.getScene().setCursor(PlanDesigner.FORBIDDEN_CURSOR);
//                    }
//                } else {
//                    target.getScene().setCursor(previousCursor);
//                }
            });

            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_RELEASED, event -> {
//                if(((Node)event.getTarget()).getParent() instanceof StateContainer &&
//                        ((Node)event.getTarget()).getParent() instanceof TerminalStateContainer == false) {
//                    StateContainer stateContainer = (StateContainer) ((Node)event.getTarget()).getParent();
//                    CreateNewDialogController newDialogController =
//                            NewResourceMenu.createFileDialog(((PlanTab) planEditorTabPane.getSelectionModel().getSelectedItem())
//                                    .getFilePath().getParent().toFile(),
//                            getAlicaFactory().createBehaviour().eClass());
//                    Behaviour newBehaviour = (Behaviour) newDialogController.getCreatedObject();
//
//                    // don't do anything if no new behaviour was created
//                    if (newBehaviour == null) {
//                        endPhase();
//                        return;
//                    }
//
//                    AddAbstractPlanToState command =
//                            new AddAbstractPlanToState(newBehaviour, stateContainer.getModelElementId());
//                    MainWindowController.getInstance()
//                            .getCommandStack()
//                            .storeAndExecute(command);
//                }
                endPhase();
            });
        }
        return eventHandlerMap;
    }
}
