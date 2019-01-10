package de.unikassel.vs.alica.planDesigner.view.editor.tools;

import de.unikassel.vs.alica.planDesigner.controller.EntryPointCreatorDialogController;
import de.unikassel.vs.alica.planDesigner.controller.ErrorWindowController;
import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiChangePositionEvent;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaCursor;
import de.unikassel.vs.alica.planDesigner.view.model.TaskViewModel;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.HashMap;


public class EntryPointTool extends AbstractTool {

    private static final Logger LOG = LogManager.getLogger(EntryPointTool.class);
    private boolean initial = true;
    private Node visualRepresentation;

    public EntryPointTool(TabPane workbench, PlanTab planTab) {
        super(workbench, planTab);
    }

    @Override
    public DraggableHBox createToolUI() {
        DraggableHBox draggableHBox = new DraggableHBox();
        draggableHBox.setIcon(Types.ENTRYPOINT);
        setDraggableHBox(draggableHBox);
        imageCursor = new AlicaCursor(AlicaCursor.Type.entrypoint);
        forbiddenCursor = new AlicaCursor(AlicaCursor.Type.forbidden_entrypoint);
        addCursor = new AlicaCursor(AlicaCursor.Type.add_entrypoint);
        return draggableHBox;
    }

//    @Override
//    public EntryPoint createNewObject() {
//        return getAlicaFactory().createEntryPoint();
//    }
//
//    @Override
//    public void draw() {
//        ((PlanTab) planEditorTabPane.getSelectionModel().getSelectedItem()).getPlanEditorGroup().setupPlanVisualisation();
//    }

    @Override
    protected void initHandlerMap() {

        if(customHandlerMap.isEmpty()){

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

                    I18NRepo i18NRepo = I18NRepo.getInstance();

                    if(handleNonPrimaryButtonEvent(event)){
                        return;
                    }
                    Point2D localCoordinates = getLocalCoordinatesFromEvent(event);
                    if (localCoordinates == null){
                        event.consume();
                        return;
                    }

                    TaskViewModel task = EntryPointCreatorDialogController.createEntryPointCreatorDialog();
                    if(task == null){
                        ErrorWindowController.createErrorWindow(i18NRepo.getString("label.error.entryPoint.noTask"), null);
                        endTool();
                        return;
                    }

                    GuiChangePositionEvent guiEvent = new GuiChangePositionEvent(GuiEventType.ADD_ELEMENT, Types.ENTRYPOINT, null);
                    guiEvent.setNewX((int) localCoordinates.getX());
                    guiEvent.setNewY((int) localCoordinates.getY());
                    guiEvent.setParentId(getPlanTab().getSerializableViewModel().getId());
                    HashMap<String, Long> related = new HashMap<>();
                    related.put(Types.TASK, task.getId());
                    guiEvent.setRelatedObjects(related);

                    IGuiModificationHandler handler = MainWindowController.getInstance().getGuiModificationHandler();
                    handler.handle(guiEvent);
                }
            });
        }
    }
}
