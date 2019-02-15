package de.unikassel.vs.alica.planDesigner.view.editor.tools.transition;

import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.container.SynchronizationContainer;
import de.unikassel.vs.alica.planDesigner.view.editor.container.TransitionContainer;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.AbstractTool;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.ToolButton;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaCursor;
import de.unikassel.vs.alica.planDesigner.view.model.SynchronizationViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.TransitionViewModel;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

import java.util.HashMap;

public class SyncTransitionTool extends AbstractTool {

    private SynchronizationViewModel sync;
    private TransitionViewModel trans;


    public SyncTransitionTool(TabPane workbench, PlanTab planTab, ToggleGroup group) {
        super(workbench, planTab, group);
    }

    @Override
    protected void initHandlerMap() {
        customHandlerMap.put(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Node target = (Node) event.getTarget();
                Parent parent = target.getParent();
                if (sync == null) {
                    if (parent instanceof SynchronizationContainer) {
                        setCursor(addCursor);
                    } else {
                        setCursor(forbiddenCursor);
                    }
                } else {
                    if (parent instanceof TransitionContainer) {
                        setCursor(addCursor);
                    } else {
                        setCursor(forbiddenCursor);
                    }
                }
            }
        });


        customHandlerMap.put(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getTarget() instanceof Scene) {
                    return;
                }

                Node target = (Node) event.getTarget();
                Node parent = target.getParent();

                if (sync == null && !(parent instanceof SynchronizationContainer)) {
                    // need to select synchronisation first
                    return;
                }

                if (parent instanceof SynchronizationContainer) {
                    if (sync != null) {
                        // selected a potentially new synchronisation, so reset state of the tool
                        reset();
                    }
                    sync = (SynchronizationViewModel) ((SynchronizationContainer) parent).getViewModelElement();
                    return;
                }

                if (parent instanceof TransitionContainer) {
                    trans = (TransitionViewModel) ((TransitionContainer) parent).getViewModelElement();
                    HashMap<String, Long> related = new HashMap<>();
                    related.put(Types.SYNCHRONIZATION, sync.getId());
                    related.put(Types.TRANSITION, trans.getId());
                    planTab.fireModificationEvent(GuiEventType.ADD_ELEMENT, Types.SYNCTRANSITION, null, related);
                    return;
                }
            }
        });
    }

    @Override
    public void endTool() {
        reset();
        super.endTool();
    }

    private void reset() {
        sync = null;
        trans = null;
    }

    @Override
    public ToolButton createToolUI() {
        ToolButton toolButton = new ToolButton();
        toolButton.setIcon(Types.SYNCTRANSITION);
        setToolButton(toolButton);
        imageCursor = new AlicaCursor(AlicaCursor.Type.synctransition);
        forbiddenCursor = new AlicaCursor(AlicaCursor.Type.forbidden_synctransition);
        addCursor = new AlicaCursor(AlicaCursor.Type.add_synctransition);
        return toolButton;
    }
}
