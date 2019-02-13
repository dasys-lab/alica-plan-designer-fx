package de.unikassel.vs.alica.planDesigner.view.editor.tools;

import de.unikassel.vs.alica.planDesigner.view.editor.container.AbstractPlanElementContainer;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

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
    protected PlanTab planTab;

    // Tool Button
    private ToolButton toolButton;

    private HashMap<EventType, EventHandler> defaultHandlerMap;
    protected HashMap<EventType, EventHandler> customHandlerMap;
    protected Cursor previousCursor;

    private boolean recentlyDone;

    protected ImageCursor imageCursor;
    protected ImageCursor forbiddenCursor;
    protected ImageCursor addCursor;

    private final ToggleGroup group;

    public AbstractTool(TabPane planEditorTabPane, PlanTab planTab, ToggleGroup group) {
        this.planEditorTabPane = planEditorTabPane;
        this.planTab = planTab;
        this.group = group;
    }

    protected abstract void initHandlerMap();

    public abstract ToolButton createToolUI();

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
                        if (toolButton.isSelected()) {
                            endTool();
                        }
                    }
                }
            });

            defaultHandlerMap.put(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getButton() == MouseButton.SECONDARY) {
                        if (toolButton.isSelected()) {
                            endTool();
                        }
                    }
                }
            });

            defaultHandlerMap.put(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Node target = (Node) event.getTarget();
                    Parent parent = target.getParent();
                    if (parent instanceof DraggableHBox || parent instanceof EditorToolBar || parent instanceof VBox) {
                        setCursor(Cursor.DEFAULT);
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
        toolButton.setSelected(true);
        for (Map.Entry<EventType, EventHandler> entry : getCustomHandlerMap().entrySet()) {
            planEditorTabPane.getScene().addEventFilter(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<EventType, EventHandler> entry : defaultHandlers().entrySet()) {
            planEditorTabPane.getScene().addEventFilter(entry.getKey(), entry.getValue());
        }

        previousCursor = getCursor();
    }

    public void endTool() {
        toolButton.setSelected(false);
        for (Map.Entry<EventType, EventHandler> entry : getCustomHandlerMap().entrySet()) {
            getPlanEditorTabPane().getScene().removeEventFilter(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<EventType, EventHandler> entry : defaultHandlers().entrySet()) {
            getPlanEditorTabPane().getScene().removeEventFilter(entry.getKey(), entry.getValue());
        }
        setCursor(Cursor.DEFAULT);
        setRecentlyDone(true);
    }

    public boolean isRecentlyDone() {
        return recentlyDone;
    }

    public void setRecentlyDone(boolean recentlyDone) {
        this.recentlyDone = recentlyDone;
    }

    public void setToolButton(ToolButton toolButton){
        toolButton.setToggleGroup(group);
        toolButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean buttonPressedBefore, Boolean buttonPressed) {
                if (buttonPressed) {
                    startTool();
                } else {
                    endTool();
                }
            }
        });
        this.toolButton = toolButton;
    }

    public ToggleButton getToolButton() {
        return toolButton;
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
                .findFirst().map(node -> ((AbstractPlanElementContainer)node).getViewModelElement()).orElse(null);
    }


    /**
     * React clicks of buttons, that are not the primary mouse button (usually left button).
     *
     * In most cases only a click of the primary button is supposed to trigger the associated action. Because a
     * {@link MouseEvent} does not differentiate the different buttons, this methods implements some standard behaviour.
     * A click of the secondary button (usually right) ends the phase and consumes the event. A click of any button that
     * is neither the primary nor the secondary button consumes the event. Only the primary button leaves the event
     * unconsumed and therefore enables further actions.
     * <br>
     * <i></b>IMPORTANT:</i> do not ignore returned value, because event might be consumed already!
     *
     * @param event  the {@link MouseEvent} to handle
     * @return  true, if the button was not the primary button
     */
    protected boolean handleNonPrimaryButtonEvent(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY){
            if(event.getButton() == MouseButton.SECONDARY){
                this.endTool();
            }
            event.consume();
            return true;
        }

        return false;
    }

    public void setCursor(Cursor cursor) {
        planEditorTabPane.getScene().setCursor(cursor);
    }

    private Cursor getCursor() {
        return planEditorTabPane.getScene().getCursor();
    }
}
