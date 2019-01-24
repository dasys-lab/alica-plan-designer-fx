package de.unikassel.vs.alica.planDesigner.view.editor.tools.state;

import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiChangePositionEvent;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.AbstractTool;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.ToolButton;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaCursor;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

/**
 * The {@link StateTool} is used for adding new states to the currently edited plan.
 */
public class StateTool extends AbstractTool {

    public StateTool(TabPane workbench, PlanTab planTab, ToggleGroup group) {
        super(workbench, planTab, group);
    }

    /**
     * Creating a guiModificationHandler, that creates an event to request the creation of a new State.
     */
    @Override
    protected void initHandlerMap() {
        I18NRepo i18NRepo = I18NRepo.getInstance();

        if (customHandlerMap.isEmpty()) {
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
                }
            });

            customHandlerMap.put(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    if(handleNonPrimaryButtonEvent(event)){
                        return;
                    }

                    // Calculate the relative coordinates of the event
                    Point2D eventTargetCoordinates = getLocalCoordinatesFromEvent(event);
                    // If the event is not valid (because it happened outside of the editor) don't do anything
                    if (eventTargetCoordinates == null) {
                        event.consume();
                        return;
                    }

                    /*
                    AtomicReference<String> nameReference = new AtomicReference<>();
                    AtomicReference<String> commentReference = new AtomicReference<>();

                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("nameAndCommentDialog.fxml"));
                    try {
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

                    if (nameReference.get().equals("")) {
                        ErrorWindowController.createErrorWindow(i18NRepo.getString("label.error.state.noName"), null);
                        return;
                    }
                    */


                    // Get the guiModificationHandler
                    IGuiModificationHandler handler = MainWindowController.getInstance().getGuiModificationHandler();

                    // Create an event. In this case use a GuiChangePositionEvent, because it can also hold the coordinates
                    // of the event
                    GuiChangePositionEvent guiEvent = createEvent();
                    guiEvent.setName(i18NRepo.getString("label.state.defaultName"));
                    guiEvent.setComment("");
                    guiEvent.setNewX((int) eventTargetCoordinates.getX());
                    guiEvent.setNewY((int) eventTargetCoordinates.getY());
                    guiEvent.setParentId(getPlanTab().getSerializableViewModel().getId());
                    handler.handle(guiEvent);
                }
            });
        }
    }

    @Override
    public ToolButton createToolUI() {
        ToolButton tollButton = new ToolButton();
        tollButton.setIcon(Types.STATE);
        setToolButton(tollButton);
        imageCursor = new AlicaCursor(AlicaCursor.Type.state);
        forbiddenCursor = new AlicaCursor(AlicaCursor.Type.forbidden_state);
        addCursor = new AlicaCursor(AlicaCursor.Type.add_state);
        return tollButton;
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
}
