package de.uni_kassel.vs.cn.planDesigner.ui.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.add.AddTransitionToSynchronisation;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.alica.impl.PlanElementImpl;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.SynchronisationContainer;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.TransitionContainer;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.AbstractEditorTab;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.PlanTab;
import javafx.beans.value.ChangeListener;
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
 * Created by marci on 22.03.17.
 */
public class SyncTransitionTool extends AbstractTool<SyncTransitionTool.SyncTransition> {

    private HashMap<EventType, EventHandler> eventHandlerMap = new HashMap<>();
    private boolean initial = true;
    private SynchronisationContainer start;
    private TransitionContainer finish;

    public SyncTransitionTool(TabPane workbench) {
        super(workbench);
    }

    @Override
    public SyncTransition createNewObject() {
        return new SyncTransition();
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
                if(((Node)event.getTarget()).getParent() instanceof SynchronisationContainer) {
                    if (initial) {
                        start = (SynchronisationContainer) ((Node)event.getTarget()).getParent();
                        initial = false;
                    }
                } else if (((Node)event.getTarget()).getParent() instanceof TransitionContainer && initial == false) {
                    finish = (TransitionContainer) ((Node)event.getTarget()).getParent();
                    initial = true;

                    AddTransitionToSynchronisation command =
                            new AddTransitionToSynchronisation(start.getContainedElement(), finish.getContainedElement(),
                                    ((AbstractEditorTab<PlanElement>)workbench.getSelectionModel().getSelectedItem()).getEditable());
                    MainController.getInstance()
                            .getCommandStack()
                            .storeAndExecute(command);
                    start = null;
                    finish = null;
                    endPhase();
                }

            });
        }
        return eventHandlerMap;
    }

    @Override
    public DragableHBox<SyncTransition> createToolUI() {
        return new SyncTransitionHBox();
    }

    /**
     * This is a pseudo class because synch transitions are no real objects.
     */
    static final class SyncTransition extends PlanElementImpl {

    }


    private class SyncTransitionHBox extends DragableHBox<SyncTransitionTool.SyncTransition> {
        public SyncTransitionHBox() {
            super(SyncTransitionTool.this.createNewObject(), SyncTransitionTool.this);
            setOnDragDetected(Event::consume);
            setOnMouseClicked(event -> startPhase());
        }
    }
}
