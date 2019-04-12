package de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab;

import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.container.AbstractPlanContainer;
import de.unikassel.vs.alica.planDesigner.view.editor.container.Container;
import de.unikassel.vs.alica.planDesigner.view.editor.container.DraggableEditorElement;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.AbstractPlanTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.EditorTabPane;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.EditorToolBar;
import de.unikassel.vs.alica.planDesigner.view.model.PlanElementViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.PlanViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.SerializableViewModel;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.HashMap;

public class PlanTab extends AbstractPlanTab {

    private PlanEditorGroup planEditorGroup;
    private EditorToolBar editorToolBar;
    private StackPane planContent;
    private ScrollPane scrollPane;

    public PlanTab(SerializableViewModel serializableViewModel, EditorTabPane editorTabPane) {
        super(serializableViewModel, editorTabPane.getGuiModificationHandler());
        draw();
        this.planContent.addEventFilter(MouseEvent.MOUSE_CLICKED, getMouseClickedEventHandler());
    }

    /**
     * Allows to select the plan itself by clicking on the scene and not on some container element.
     */
    private EventHandler<MouseEvent> getMouseClickedEventHandler() {
        return event -> {
            if (event.getTarget() == planContent) {
                this.planEditorGroup.getPlanEditorTab().selectPlan((PlanViewModel) this.getSerializableViewModel());
                event.consume();
            }
        };
    }

    private void draw() {
        planEditorGroup = new PlanEditorGroup((PlanViewModel) serializableViewModel, this);
        planContent = new StackPane(planEditorGroup);
        planContent.setPadding(new Insets(50, 50, 50, 50));
        planContent.setManaged(true);
        planEditorGroup.setManaged(true);

        scrollPane = new ScrollPane(planContent);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        HBox.setHgrow(scrollPane, Priority.ALWAYS);
        VBox.setVgrow(scrollPane,Priority.ALWAYS);

        editorToolBar = new EditorToolBar(MainWindowController.getInstance().getEditorTabPane(), this);

        HBox scrollPaneAndToolBarHBox = new HBox(scrollPane, editorToolBar);
        scrollPaneAndToolBarHBox.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        VBox.setVgrow(scrollPaneAndToolBarHBox,Priority.ALWAYS);

        splitPane.getItems().add(0, scrollPaneAndToolBarHBox);
    }

    /**
     * Fire an event, notifying the {@link IGuiModificationHandler}, that an object has changed its position.
     *
     * @param planElementContainer  the container of the object, that changed its position
     * @param type  the type of object, that changed its position
     * @param newX  the new x-coordinate
     * @param newY  the new y-coordinate
     */
    public void fireChangePositionEvent(DraggableEditorElement planElementContainer, String type, double newX, double newY) {
        GuiModificationEvent event = new GuiModificationEvent(GuiEventType.CHANGE_POSITION, type, planElementContainer.getPlanElementViewModel().getName());
        event.setElementId(planElementContainer.getPlanElementViewModel().getId());
        event.setParentId(serializableViewModel.getId());
        event.setX((int) newX);
        event.setY((int) newY);
        guiModificationHandler.handle(event);
    }

    public void fireModificationEvent(GuiEventType eventType, String elementType, String name, HashMap<String, Long> relatedElements, int x, int y) {
        GuiModificationEvent event = new GuiModificationEvent(eventType, elementType, name);
        event.setX(x);
        event.setY(y);
        event.setParentId(getSerializableViewModel().getId());
        event.setRelatedObjects(relatedElements);
        guiModificationHandler.handle(event);
    }

    public void fireModificationEvent(GuiEventType eventType, String elementType, String name, HashMap<String, Long> relatedElements) {
        GuiModificationEvent event = new GuiModificationEvent(eventType, elementType, name);
        event.setParentId(getSerializableViewModel().getId());
        event.setRelatedObjects(relatedElements);
        guiModificationHandler.handle(event);
    }

    public EditorToolBar getEditorToolBar() {
        return editorToolBar;
    }

    public GuiModificationEvent handleDelete() {
        Container selectedContainer = this.selectedContainer.get();
        if (selectedContainer == null) {
            return null;
        }
        PlanElementViewModel planElementViewModel = selectedContainer.getPlanElementViewModel();
        switch(planElementViewModel.getType()) {
            case Types.BEHAVIOUR:
            case Types.PLAN:
            case Types.PLANTYPE:
            case Types.CONFIGURATION:
                GuiModificationEvent event = new GuiModificationEvent(GuiEventType.REMOVE_ELEMENT, planElementViewModel.getType(), planElementViewModel.getName());
                event.setParentId(((AbstractPlanContainer)selectedContainer).getParentStateContainer().getState().getId());
                event.setElementId(planElementViewModel.getId());
                return event;
            case Types.STATE:
            case Types.SUCCESSSTATE:
            case Types.FAILURESTATE:
                event = new GuiModificationEvent(GuiEventType.DELETE_ELEMENT, planElementViewModel.getType(), planElementViewModel.getName());
                event.setParentId(planElementViewModel.getParentId());
                event.setElementId(planElementViewModel.getId());
                return event;
            default:
                System.err.println("PlanTab: Selected element type " + planElementViewModel.getType() + " is not handled!");
                return null;
        }
    }

    public void save() {
        save(Types.PLAN);
    }

    public StackPane getPlanContent() {
        return planContent;
    }
    public PlanEditorGroup getPlanEditorGroup() {
        return planEditorGroup;
    }

}
