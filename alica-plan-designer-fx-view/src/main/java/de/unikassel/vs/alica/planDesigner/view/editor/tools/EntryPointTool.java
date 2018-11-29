package de.unikassel.vs.alica.planDesigner.view.editor.tools;

import de.unikassel.vs.alica.planDesigner.PlanDesignerApplication;
import de.unikassel.vs.alica.planDesigner.controller.EntryPointCreatorDialogController;
import de.unikassel.vs.alica.planDesigner.controller.ErrorWindowController;
import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiChangePositionEvent;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.container.StateContainer;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaCursor;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaIcon;
import de.unikassel.vs.alica.planDesigner.view.model.StateViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.TaskViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;


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


        AtomicReference<GuiChangePositionEvent> eventReference = new AtomicReference<>();
        I18NRepo i18NRepo = I18NRepo.getInstance();


        if(customHandlerMap.isEmpty()){
            customHandlerMap.put(MouseDragEvent.MOUSE_DRAG_RELEASED, (EventHandler<MouseDragEvent>) event -> {
                Point2D localCoordinates = getLocalCoordinatesFromEvent(event);
                if(localCoordinates == null){
                    event.consume();
                    return;
                }

                GuiChangePositionEvent guiEvent = new GuiChangePositionEvent(GuiEventType.ADD_ELEMENT, Types.ENTRYPOINT, null);
                guiEvent.setNewX((int) localCoordinates.getX());
                guiEvent.setNewY((int) localCoordinates.getY());
                guiEvent.setParentId(getPlanTab().getPlan().getId());

                AtomicReference<TaskViewModel> task = new AtomicReference<>(null);
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("entryPointCreatorDialog.fxml"));
                try{
                    Parent rootOfDialog = loader.load();
                    EntryPointCreatorDialogController controller = loader.getController();
                    controller.setSelectedTaskReference(task);
                    Stage stage = new Stage();
                    stage.setResizable(false);
                    stage.setTitle(i18NRepo.getString("label.choose.task"));
                    stage.setScene(new Scene(rootOfDialog));
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(PlanDesignerApplication.getPrimaryStage());
                    stage.showAndWait();
                } catch (IOException e) {
                    System.err.println("An exception occurred in the Task-Selection-Window");
                    e.printStackTrace();
                }

                if(task.get() == null){
                    ErrorWindowController.createErrorWindow(i18NRepo.getString("label.error.entryPoint.noTask"), null);
                    //No task was chosen, so no EntryPoint will be created
                    eventReference.set(null);
                    endPhase();
                    return;
                }
                HashMap<String, Long> related = new HashMap<>();
                related.put(Types.TASK, task.get().getId());
                guiEvent.setRelatedObjects(related);

                eventReference.set(guiEvent);
            });
            customHandlerMap.put(MouseEvent.MOUSE_PRESSED, e -> {
                ViewModelElement elementFromEvent = getElementFromEvent(e);
                if(eventReference.get() != null && elementFromEvent != null
                     &&(   elementFromEvent.getType().equals(Types.STATE)
                        || elementFromEvent.getType().equals(Types.SUCCESSSTATE)
                        || elementFromEvent.getType().equals(Types.FAILURESTATE))) {

                    if(((StateViewModel) elementFromEvent).getEntryPoint() == null){
                        eventReference.get().getRelatedObjects().put(Types.STATE, elementFromEvent.getId());

                        IGuiModificationHandler handler = MainWindowController.getInstance().getGuiModificationHandler();
                        handler.handle(eventReference.get());
                    }else{
                        ErrorWindowController.createErrorWindow(i18NRepo.getString("label.error.entryPoint.stateAlreadyHasEntryPoint"), null);
                    }

                }else{
                    ErrorWindowController.createErrorWindow(i18NRepo.getString("label.error.entryPoint.noStateChosen"), null);
                }

                eventReference.set(null);
                // endPhase() needs to be called here manually, because the automatic call seems to be interfered by
                // the dialog-window opening
                endPhase();
            });


            //Cursor:

            customHandlerMap.put(MouseDragEvent.MOUSE_DRAG_ENTERED, (EventHandler<MouseDragEvent>) event ->
                    planEditorTabPane.getScene().setCursor(new AlicaCursor(AlicaCursor.Type.entrypoint)));

            customHandlerMap.put(MouseEvent.MOUSE_MOVED, (EventHandler<MouseEvent>) event -> {
                Node node = (Node) event.getTarget();
                if(node.getParent() instanceof StateContainer && ((StateContainer) node.getParent()).getState().getEntryPoint() == null){
                    planEditorTabPane.getScene().setCursor(new AlicaCursor(AlicaCursor.Type.add, 8, 8));
                }else{
                    planEditorTabPane.getScene().setCursor(new AlicaCursor(AlicaCursor.Type.forbidden, 8, 8));
                }
            });
        }
    }
}
