package de.unikassel.vs.alica.planDesigner.view.editor.tools;

import de.unikassel.vs.alica.planDesigner.view.editor.container.AbstractPlanElementContainer;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;

import de.unikassel.vs.alica.planDesigner.view.img.AlicaCursor;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@link AbstractTool} interface provides methods for the tools in the {@link EditorToolBar}.
 * It helps to generalize the usage of these tools for the following workflow:
 * tool is selected (start of the phase) -> Event handlerinterfaces for special actions on the PlanEditorGroup
 * are registered -> The actions are performed. A new alicamodel object is created.
 * Or the actions are aborted. -> The phase ends. The event handlers will be removed. and the editor is usable as before.
 */
public abstract class AbstractTool {
    protected TabPane planEditorTabPane;
    private PlanTab planTab;
    // Contains Icon and Text and triggers the drag events (start and stop).
    private DraggableHBox draggableHBox;
    // Shadow Effect set on draggableHBox when dragged
    private static final DropShadow dropShadowEffect = new DropShadow(10, Color.GREY);
    private HashMap<EventType, EventHandler> defaultHandlerMap;
    protected HashMap<EventType, EventHandler> customHandlerMap;
    protected Cursor previousCursor;

    private boolean recentlyDone;
    private boolean activated;

    protected ImageCursor imageCursor;
    protected ImageCursor forbiddenCursor;
    protected ImageCursor addCursor;

    public AbstractTool(TabPane planEditorTabPane, PlanTab planTab) {
        this.planEditorTabPane = planEditorTabPane;
        this.planTab = planTab;
        dropShadowEffect.setSpread(0.5);
    }

    protected abstract void initHandlerMap();

    public abstract DraggableHBox createToolUI();

    protected Node getPlanEditorTabPane() {
        return planEditorTabPane;
    }

    protected Map<EventType, EventHandler> defaultHandlers() {
        if (defaultHandlerMap == null) {
            defaultHandlerMap = new HashMap<>();
            defaultHandlerMap.put(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode() == KeyCode.ESCAPE) {
                        if (activated) {
                            endTool();
                        }
                    }
                }
            });
        }
        return defaultHandlerMap;
    }

    protected final  Map<EventType, EventHandler> getCustomHandlerMap() {
        if(customHandlerMap == null){
            customHandlerMap = new HashMap<>();
        }
        if (customHandlerMap.isEmpty()) {
            this.initHandlerMap();
        }
        return customHandlerMap;
    }

    public void startTool() {
        draggableHBox.setEffect(dropShadowEffect);
        for (Map.Entry<EventType, EventHandler> entry : getCustomHandlerMap().entrySet()) {
            planEditorTabPane.getScene().addEventFilter(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<EventType, EventHandler> entry : defaultHandlers().entrySet()) {
            planEditorTabPane.getScene().addEventFilter(entry.getKey(), entry.getValue());
        }

        previousCursor = getCursor();
        setCursor(imageCursor);
        activated = !activated;
    }

    public void endTool() {
        draggableHBox.setEffect(null);
        for (Map.Entry<EventType, EventHandler> entry : getCustomHandlerMap().entrySet()) {
            getPlanEditorTabPane().getScene().removeEventFilter(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<EventType, EventHandler> entry : defaultHandlers().entrySet()) {
            getPlanEditorTabPane().getScene().removeEventFilter(entry.getKey(), entry.getValue());
        }
        setCursor(Cursor.DEFAULT);
        setRecentlyDone(true);
        activated = !activated;
    }

    public boolean isRecentlyDone() {
        return recentlyDone;
    }

    public void setRecentlyDone(boolean recentlyDone) {
        this.recentlyDone = recentlyDone;
    }

    public void setDraggableHBox(DraggableHBox draggableHBox){
        draggableHBox.setOnMouseClicked(event -> {
            if (activated) {
                endTool();
            } else {
                startTool();
            }
        });

        /*
        draggableHBox.setOnDragDetected(event -> {
            draggableHBox.startFullDrag();
            this.startTool();
            event.consume();
        });*/

        this.draggableHBox = draggableHBox;
        this.draggableHBox.setOnDragDone(Event::consume);
    }
    public DraggableHBox getDraggableHBox() {
        return draggableHBox;
    }

    public PlanTab getPlanTab() {
        return planTab;
    }

    /**
     * Transform the coordinates contained in a {@link MouseDragEvent} into coordinates relative to the {@link StackPane}
     * that represents the editor.
     *
     * @param event  the {@link MouseDragEvent} containing the base coordinates
     * @return  the relative coordinates or null, if the drag was released outside of the editor
     */
    protected Point2D getLocalCoordinatesFromEvent(MouseEvent event){
        //If the events target is the editor, calculate the local coordinates
        if(event.getTarget() != null && isMouseDragEventOnValidTarget(event)){
            return planTab.getPlanEditorGroup().sceneToLocal(event.getX(), event.getY());
        }
        //Otherwise just return null
        return null;
    }

    private boolean isMouseDragEventOnValidTarget(MouseEvent event){
        //The target may be the StackPane itself
        return event.getTarget() == planTab.getPlanContent()
                //Or one of the children of its children
                || planTab.getPlanEditorGroup().getChildren()
                .stream().flatMap(container -> ((Pane) container).getChildren().stream())
                .anyMatch(x -> x == event.getTarget());
    }

    protected ViewModelElement getElementFromEvent(Event event){
        return planTab.getPlanEditorGroup().getChildren().stream()
                .filter(container -> ((Pane) container).getChildren().contains(event.getTarget()))
                .findFirst().map(node -> ((AbstractPlanElementContainer)node).getModelElement()).orElse(null);
    }

    public void setCursor(Cursor cursor) {
        planEditorTabPane.getScene().setCursor(cursor);
    }

    private Cursor getCursor() {
        return planEditorTabPane.getScene().getCursor();
    }
}
