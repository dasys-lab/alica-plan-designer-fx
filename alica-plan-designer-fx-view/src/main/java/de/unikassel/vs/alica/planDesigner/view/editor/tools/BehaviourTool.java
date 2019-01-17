package de.unikassel.vs.alica.planDesigner.view.editor.tools;

import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseDragEvent;


public class BehaviourTool extends AbstractTool {

    public BehaviourTool(TabPane workbench, PlanTab planTab, ToggleGroup group) {
        super(workbench, planTab, group);
    }

    @Override
    public ToolButton createToolUI() {
        ToolButton toolButton = new ToolButton();
        toolButton.setIcon(Types.BEHAVIOUR);
        setToolButton(toolButton);
        return toolButton;
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
    protected void initHandlerMap() {
        if (customHandlerMap.isEmpty()) {
            customHandlerMap.put(MouseDragEvent.MOUSE_DRAG_OVER, event -> {
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

            customHandlerMap.put(MouseDragEvent.MOUSE_DRAG_RELEASED, event -> {
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
//                        endTool();
//                        return;
//                    }
//
//                    AddAbstractPlanToState command =
//                            new AddAbstractPlanToState(newBehaviour, stateContainer.getModelElementId());
//                    MainWindowController.getInstance()
//                            .getCommandStack()
//                            .storeAndExecute(command);
//                }
                endTool();
            });
        }
    }
}
