package de.unikassel.vs.alica.planDesigner.view.editor.tools.state;

import de.unikassel.vs.alica.planDesigner.PlanDesignerApplication;
import de.unikassel.vs.alica.planDesigner.controller.ErrorWindowController;
import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.controller.NameAndCommentDialogController;
import de.unikassel.vs.alica.planDesigner.events.GuiChangePositionEvent;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.AbstractTool;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.DraggableHBox;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaIcon;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseDragEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The {@link StateTool} is used for adding new states to the currently edited plan.
 */
public class StateTool extends AbstractTool {

    public StateTool(TabPane workbench, PlanTab planTab) {
        super(workbench, planTab);
    }

    /**
     * Creating a handler, that creates an event to request the creation of a new State.
     */
    @Override
    protected void initHandlerMap() {
        I18NRepo i18NRepo = I18NRepo.getInstance();

        if (customHandlerMap.isEmpty()) {
            customHandlerMap.put(MouseDragEvent.MOUSE_DRAG_RELEASED, (EventHandler<MouseDragEvent>) event -> {
                planEditorTabPane.getScene().setCursor(previousCursor);

                // Calculate the relative coordinates of the event
                Point2D eventTargetCoordinates = getLocalCoordinatesFromEvent(event);
                // If the event is not valid (because it happened outside of the editor) don't do anything
                if(eventTargetCoordinates == null){
                    event.consume();
                    return;
                }

                AtomicReference<String> nameReference = new AtomicReference<>();
                AtomicReference<String> commentReference = new AtomicReference<>();

                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("nameAndCommentDialog.fxml"));
                try{
                    Parent rootOfDialog = loader.load();
                    NameAndCommentDialogController controller = loader.getController();
                    controller.setNameReference(nameReference);
                    controller.setCommentReference(commentReference);
                    Stage stage = new Stage();
                    stage.setResizable(false);
                    stage.setTitle(i18NRepo.getString("label.choose.name"));
                    stage.setScene(new Scene(rootOfDialog));
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(PlanDesignerApplication.getPrimaryStage());
                    stage.showAndWait();
                } catch (IOException e) {
                    System.err.println("An exception occurred in the Name-Selection-Window");
                    e.printStackTrace();
                }

                if(nameReference.get().equals("")){
                    ErrorWindowController.createErrorWindow(i18NRepo.getString("label.error.state.noName"), null);
                    return;
                }

                // Get the handler
                IGuiModificationHandler handler = MainWindowController.getInstance().getGuiModificationHandler();

                // Create an event. In this case use a GuiChangePositionEvent, because it can also hold the coordinates
                // of the event
                GuiChangePositionEvent guiEvent = createEvent();
                guiEvent.setName(nameReference.get());
                guiEvent.setComment(commentReference.get());
                guiEvent.setNewX((int) eventTargetCoordinates.getX());
                guiEvent.setNewY((int) eventTargetCoordinates.getY());
                guiEvent.setParentId(getPlanTab().getPlan().getId());
                handler.handle(guiEvent);

                //End phase manually, because dialog-window seems to prevent automatic end of phase
                endPhase();
            });
            customHandlerMap.put(MouseDragEvent.MOUSE_DRAG_ENTERED, (EventHandler<MouseDragEvent>) event ->
                    planEditorTabPane.getScene().setCursor(getImageCursor()));
        }
    }

    @Override
    public DraggableHBox createToolUI() {
        DraggableHBox draggableHBox = new DraggableHBox();
        draggableHBox.setIcon(Types.STATE);
        setDraggableHBox(draggableHBox);
        return draggableHBox;
    }

    /**
     * Create an event. In this case use a GuiChangePositionEvent, because it can also hold the coordinates
     * of the event
     *
     * @return  the created event
     */
    protected GuiChangePositionEvent createEvent(){
        return new GuiChangePositionEvent(GuiEventType.ADD_ELEMENT, Types.STATE, null);
    }

    /**
     * Create an {@link ImageCursor} with the symbol of this tool.
     *
     * @return  the cursor representing this tool
     */
    protected ImageCursor getImageCursor(){
        return new ImageCursor(new AlicaIcon(Types.STATE, AlicaIcon.Size.SMALL));
    }
}
