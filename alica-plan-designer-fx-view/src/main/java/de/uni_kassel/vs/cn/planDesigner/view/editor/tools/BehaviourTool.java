package de.uni_kassel.vs.cn.planDesigner.view.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.PlanDesigner;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.command.add.AddAbstractPlanToState;
import de.uni_kassel.vs.cn.planDesigner.controller.CreateNewDialogController;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.view.editor.container.StateContainer;
import de.uni_kassel.vs.cn.planDesigner.view.editor.container.TerminalStateContainer;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.PlanTab;
import de.uni_kassel.vs.cn.planDesigner.view.menu.NewResourceMenu;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseDragEvent;

import java.util.HashMap;
import java.util.Map;

import static de.uni_kassel.vs.cn.generator.EMFModelUtils.getAlicaFactory;

/**
 * Created by marci on 05.01.17.
 */
public class BehaviourTool extends AbstractTool<Behaviour> {

    private HashMap<EventType, EventHandler> eventHandlerMap = new HashMap<>();
    private Cursor previousCursor;

    public BehaviourTool(TabPane workbench) {
        super(workbench);
    }

    @Override
    public Behaviour createNewObject() {
        return getAlicaFactory().createBehaviour();
    }

    @Override
    public void draw() {
        ((PlanTab)workbench.getSelectionModel().getSelectedItem()).getPlanEditorGroup().setupPlanVisualisation();
    }

    @Override
    protected Map<EventType, EventHandler> toolRequiredHandlers() {
        if (eventHandlerMap.isEmpty()) {
            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_OVER, event -> {
                if (event.getTarget() instanceof Node == false) {
                    event.consume();
                    return;
                }
                Node target = (Node) event.getTarget();

                if (((Node) event.getTarget()).getParent() instanceof TerminalStateContainer ||
                        ((Node) event.getTarget()).getParent() instanceof StateContainer == false) {
                    if (target.getScene().getCursor().equals(PlanDesigner.FORBIDDEN_CURSOR) == false) {
                        previousCursor = target.getScene().getCursor();
                        target.getScene().setCursor(PlanDesigner.FORBIDDEN_CURSOR);
                    }
                } else {
                    target.getScene().setCursor(previousCursor);
                }
            });

            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_RELEASED, event -> {
                if(((Node)event.getTarget()).getParent() instanceof StateContainer &&
                        ((Node)event.getTarget()).getParent() instanceof TerminalStateContainer == false) {
                    StateContainer stateContainer = (StateContainer) ((Node)event.getTarget()).getParent();
                    CreateNewDialogController newDialogController =
                            NewResourceMenu.createFileDialog(((PlanTab) workbench.getSelectionModel().getSelectedItem())
                                    .getFilePath().getParent().toFile(),
                            getAlicaFactory().createBehaviour().eClass());
                    Behaviour newBehaviour = (Behaviour) newDialogController.getCreatedObject();

                    // don't do anything if no new behaviour was created
                    if (newBehaviour == null) {
                        endPhase();
                        return;
                    }

                    AddAbstractPlanToState command =
                            new AddAbstractPlanToState(newBehaviour, stateContainer.getContainedElement());
                    MainController.getInstance()
                            .getCommandStack()
                            .storeAndExecute(command);
                }
                endPhase();
            });
        }
        return eventHandlerMap;
    }
}
