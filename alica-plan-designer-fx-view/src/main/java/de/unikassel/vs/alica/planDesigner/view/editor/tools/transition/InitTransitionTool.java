package de.unikassel.vs.alica.planDesigner.view.editor.tools.transition;

import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.container.EntryPointContainer;
import de.unikassel.vs.alica.planDesigner.view.editor.container.StateContainer;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.AbstractTool;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.DraggableHBox;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.EditorToolBar;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.ToolButton;
import de.unikassel.vs.alica.planDesigner.view.model.EntryPointViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.StateViewModel;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaCursor;
import javafx.scene.layout.VBox;

import java.util.HashMap;


public class InitTransitionTool extends AbstractTool {

    private boolean initial = true;
    private EntryPointContainer inEntryPoint;
    private StateViewModel state;

    public InitTransitionTool(TabPane workbench, PlanTab planTab, ToggleGroup group) {
        super(workbench, planTab, group);
    }

//    public InitStateConnection createNewObject() {
//        return new InitStateConnection();
//    }

    @Override
    public ToolButton createToolUI() {
        ToolButton toolButton = new ToolButton();
        toolButton.setIcon(Types.INITSTATECONNECTION);
        setToolButton(toolButton);
        imageCursor = new AlicaCursor(AlicaCursor.Type.initstateconnection);
        forbiddenCursor = new AlicaCursor(AlicaCursor.Type.forbidden_initstateconnection);
        addCursor = new AlicaCursor(AlicaCursor.Type.add_initstateconnection);
        return toolButton;
    }

    public void draw() {
        // ((PlanTab) planEditorTabPane.getSelectionModel().getSelectedItem()).getPlanEditorGroup().setupPlanVisualisation();
    }

    @Override
    protected void initHandlerMap() {
        if (customHandlerMap.isEmpty()) {
            customHandlerMap.put(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Node target = (Node) event.getTarget();
                    Parent parent = target.getParent();
                    if(initial){
                        if (parent instanceof EntryPointContainer) {
                            setCursor(addCursor);
                        } else {
                            setCursor(forbiddenCursor);
                        }
                    } else {
                        if (parent instanceof StateContainer) {
                            setCursor(addCursor);
                        } else {
                            setCursor(forbiddenCursor);
                        }
                    }

                    if (parent instanceof DraggableHBox || parent instanceof EditorToolBar || parent instanceof VBox) {
                        setCursor(Cursor.DEFAULT);
                    }
                }
            });

            customHandlerMap.put(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    Node target = (Node) event.getTarget();
                    Parent parent = target.getParent();

                    if (parent instanceof DraggableHBox) {
                        endTool();
                    }

                    if (handleNonPrimaryButtonEvent(event)) {
                        return;
                    }

                    if (((Node) event.getTarget()).getParent() instanceof EntryPointContainer) {
                        if (initial) {
                            inEntryPoint =  ((EntryPointContainer) ((Node) event.getTarget()).getParent());
                            initial = false;
                        }
                    } else if (((Node) event.getTarget()).getParent() instanceof StateContainer && initial == false) {
                        state = ((StateContainer) ((Node) event.getTarget()).getParent()).getState();
                        initial = true;

                        IGuiModificationHandler guiModificationHandler = MainWindowController.getInstance().getGuiModificationHandler();

                        GuiModificationEvent guiEvent = new GuiModificationEvent(GuiEventType.ADD_ELEMENT, Types.INITSTATECONNECTION, null);

                        HashMap<String, Long> relatedObjects = new HashMap<>();
                        relatedObjects.put(StateViewModel.ENTRYPOINT, inEntryPoint.getViewModelElement().getId());
                        relatedObjects.put(EntryPointViewModel.STATE, state.getId());
                        guiEvent.setRelatedObjects(relatedObjects);
                        guiEvent.setParentId(InitTransitionTool.this.planTab.getSerializableViewModel().getId());
                        guiModificationHandler.handle(guiEvent);
                    }
                }
            });
        }
    }



//    /**
//     * This is a pseudo class because init transitions are no real objects.
//     */
//    static final class InitStateConnection extends PlanElementImpl {
//
//    }
}
