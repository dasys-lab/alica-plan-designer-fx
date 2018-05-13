package de.uni_kassel.vs.cn.planDesigner.ui.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.PlanEditorGroup;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.AbstractPlanElementContainer;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.AbstractEditorTab;
import de.uni_kassel.vs.cn.planDesigner.ui.img.AlicaIcon;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The {@link AbstractTool} interface provides methods for the tools in the {@link PLDToolBar}.
 * It helps to generalize the usage of these tools for the following workflow:
 * tool is selected (start of the phase) -> Event handler for special actions on the {@link PlanEditorGroup}
 * are registered -> The actions are performed. A new model object is created.
 * Or the actions are aborted. -> The phase ends. The event handlers will be removed. and the editor is usable as before.
 *
 * @param <T> type of the model object this tool is associated with
 */
@SuppressWarnings("unchecked")
public abstract class AbstractTool<T extends PlanElement> {

    protected TabPane workbench;
    protected Cursor originalCursor;
    protected Point2D localCoord;
    protected DragableHBox<T> dragableHBox;
    private boolean recentlyDone;
    private HashMap<EventType, EventHandler> defaultHandlers;

    public AbstractTool(TabPane workbench) {
        this.workbench = workbench;
    }

    public abstract T createNewObject();
    public abstract void draw();
    protected abstract Map<EventType, EventHandler> toolRequiredHandlers();

    protected Node getWorkbench() {
        return workbench;
    }

    protected Map<EventType, EventHandler> defaultHandlers() {
        if (defaultHandlers == null) {
            defaultHandlers = new HashMap<>();
            defaultHandlers.put(MouseEvent.MOUSE_DRAGGED, (event) -> {
                MouseEvent e = (MouseEvent) event;
                if (e.getSceneX() + 5 > getWorkbench().getScene().getWidth()
                        || e.getSceneY() + 5 > getWorkbench().getScene().getHeight()
                        || e.getSceneX() - 5 < 0 || e.getSceneY() - 5 < 0) {
                    endPhase();
                }
            });
        }
        return defaultHandlers;
    }

    public DragableHBox<T> createToolUI() {
        dragableHBox = new DragableHBox<>(createNewObject(), this);
        return dragableHBox;
    }

    public void startPhase() {
        toolRequiredHandlers()
                .entrySet()
                .forEach(entry -> getWorkbench().getScene().addEventFilter(entry.getKey(), entry.getValue()));
        defaultHandlers()
                .entrySet()
                .forEach(entry -> getWorkbench().getScene().addEventFilter(entry.getKey(), entry.getValue()));

        originalCursor = workbench.getScene().getCursor();
        DropShadow value = new DropShadow(10, Color.GREY);
        value.setSpread(0.5);
        dragableHBox.setEffect(value);
        workbench.getScene().setCursor(new ImageCursor(new AlicaIcon(createNewObject().getClass())));
    }

    public void endPhase() {
        dragableHBox.setEffect(null);
        toolRequiredHandlers()
                .entrySet()
                .forEach(entry -> getWorkbench().getScene().removeEventFilter(entry.getKey(), entry.getValue()));
        defaultHandlers()
                .entrySet()
                .forEach(entry -> getWorkbench().getScene().removeEventFilter(entry.getKey(), entry.getValue()));
        draw();
        workbench.getScene().setCursor(originalCursor);
        AbstractEditorTab selectedItem = (AbstractEditorTab) MainController.getInstance().getEditorTabPane().getSelectionModel()
                .getSelectedItem();
        if (selectedItem != null) {
            List<Pair<PlanElement, AbstractPlanElementContainer>> noSelection = new ArrayList<>();
            noSelection.add(new Pair<>(null, null));
            selectedItem
                    .getSelectedPlanElement().set(noSelection);
        }
        setRecentlyDone(true);
    }

    public boolean updateLocalCoords(MouseDragEvent event) {
        if (event.getTarget() instanceof Scene) {
            localCoord = new Point2D(event.getX(), event.getY());
            return false;
        }

        if (event.getTarget() == null || event.getTarget() instanceof Node == false) {
            return true;
        }

        if (event.getTarget() instanceof StackPane && ((StackPane) event.getTarget()).getChildren().size() > 0 &&
                ((StackPane) event.getTarget()).getChildren().get(0) instanceof PlanEditorGroup) {
            localCoord =
                    ((StackPane) event.getTarget()).getChildren().get(0)
                            .sceneToLocal(event.getX(), event.getY());
        } else if (((Node)event.getTarget()).getParent() instanceof AbstractPlanElementContainer) {
            localCoord = ((Node)((Node)event.getTarget()).getParent().getParent())
                    .sceneToLocal(event.getX(), event.getY());
        } else {
            return true;
        }
        return false;
    }

    public boolean isRecentlyDone() {
        return recentlyDone;
    }

    public void setRecentlyDone(boolean recentlyDone) {
        this.recentlyDone = recentlyDone;
    }
}
