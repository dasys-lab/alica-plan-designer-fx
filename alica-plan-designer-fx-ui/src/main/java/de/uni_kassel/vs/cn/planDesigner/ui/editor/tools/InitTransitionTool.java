package de.uni_kassel.vs.cn.planDesigner.ui.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.change.SetStateForEntryPoint;
import de.uni_kassel.vs.cn.planDesigner.alica.impl.PlanElementImpl;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.EntryPointContainer;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.StateContainer;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.PlanTab;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by marci on 08.03.17.
 */
public class InitTransitionTool extends AbstractTool<InitTransitionTool.InitStateConnection> {

    private HashMap<EventType, EventHandler> eventHandlerMap = new HashMap<>();
    private boolean initial;
    private EntryPointContainer start;
    private StateContainer finish;

    public InitTransitionTool(TabPane workbench) {
        super(workbench);
    }

    @Override
    public InitStateConnection createNewObject() {
        return new InitStateConnection();
    }

    @Override
    public void draw() {
        PlanTab selectedItem = (PlanTab) workbench.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            ChangeListener<Tab> listener = new TabChangeListener(workbench);
            workbench.getSelectionModel().selectedItemProperty().addListener(listener);
        } else {
            (selectedItem).getPlanEditorPane().setupPlanVisualisation();
        }
    }

    @Override
    protected Map<EventType, EventHandler> toolRequiredHandlers() {
        if (eventHandlerMap.isEmpty()) {
            eventHandlerMap.put(MouseEvent.MOUSE_CLICKED, event -> {
                if(((Node)event.getTarget()).getParent() instanceof EntryPointContainer) {
                    if (initial) {
                        start = (EntryPointContainer) ((Node)event.getTarget()).getParent();
                        initial = false;
                    }
                } else if (((Node)event.getTarget()).getParent() instanceof StateContainer && initial == false) {
                    finish = (StateContainer) ((Node)event.getTarget()).getParent();
                    initial = true;
                    SetStateForEntryPoint command = new SetStateForEntryPoint(start.getContainedElement(), finish.getContainedElement());
                    MainController.getInstance()
                            .getCommandStack()
                            .storeAndExecute(command);
                    endPhase();
                }

            });
        }
        return eventHandlerMap;
    }

    @Override
    public DragableHBox<InitStateConnection> createToolUI() {
        return new InitTransitionTool.InitStateConnectionHBox();
    }

    private class InitStateConnectionHBox extends DragableHBox<InitStateConnection> {
        public InitStateConnectionHBox() {
            super(InitTransitionTool.this.createNewObject(), InitTransitionTool.this);
            setOnDragDetected(Event::consume);
            setOnMouseClicked(event -> startPhase());
        }
    }

    /**
     * This is a pseudo class because init transitions are no real objects.
     */
    static final class InitStateConnection extends PlanElementImpl {

    }
}
