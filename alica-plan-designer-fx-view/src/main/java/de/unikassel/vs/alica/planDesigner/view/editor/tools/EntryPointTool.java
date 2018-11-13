package de.unikassel.vs.alica.planDesigner.view.editor.tools;

import de.unikassel.vs.alica.planDesigner.PlanDesignerApplication;
import de.unikassel.vs.alica.planDesigner.controller.EntryPointCreatorDialogController;
import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiChangePositionEvent;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.model.TaskViewModel;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseDragEvent;
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
        if(customHandlerMap.isEmpty()){
            customHandlerMap.put(MouseDragEvent.MOUSE_DRAG_RELEASED, (EventHandler<MouseDragEvent>) event -> {
                Point2D localCoordinates = getLocalCoordinatesFromEvent(event);
                if(localCoordinates == null){
                    event.consume();
                    return;
                }
                IGuiModificationHandler handler = MainWindowController.getInstance().getGuiModificationHandler();

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
                    stage.setTitle(I18NRepo.getInstance().getString("label.choose.task"));
                    stage.setScene(new Scene(rootOfDialog));
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(PlanDesignerApplication.getPrimaryStage());
                    stage.showAndWait();
                } catch (IOException e) {
                    System.err.println("An exception occurred in the Task-Selection-Window");
                    e.printStackTrace();
                }

                // endPhase() needs to be called here manually, because the automatic call seems to be interfered by
                // the dialog-window opening
                endPhase();
                if(task.get() == null){
                    return;
                }
                HashMap<String, Long> related = new HashMap<>();
                related.put(Types.TASK, task.get().getId());
                guiEvent.setRelatedObjects(related);

                handler.handle(guiEvent);
            });
        }
    }
}
