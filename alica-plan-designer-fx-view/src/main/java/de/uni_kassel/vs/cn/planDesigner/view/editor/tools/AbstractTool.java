package de.uni_kassel.vs.cn.planDesigner.view.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.view.editor.PlanEditorGroup;
import de.uni_kassel.vs.cn.planDesigner.view.editor.container.AbstractPlanElementContainer;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.AbstractEditorTab;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.PlanTab;
import de.uni_kassel.vs.cn.planDesigner.view.img.AlicaIcon;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@link AbstractTool} interface provides methods for the tools in the {@link PLDToolBar}.
 * It helps to generalize the usage of these tools for the following workflow:
 * tool is selected (start of the phase) -> Event handler for special actions on the {@link PlanEditorGroup}
 * are registered -> The actions are performed. A new alicamodel object is created.
 * Or the actions are aborted. -> The phase ends. The event handlers will be removed. and the editor is usable as before.
 */
public abstract class AbstractTool {
    // Contains Icon and Text and triggers the drag events (start and stop).
    protected DraggableHBox draggableHBox;
    // Shadow Effect set on draggableHBox when dragged
    protected static final DropShadow dropShadowEffect = new DropShadow(10, Color.GREY);

    protected TabPane workbench;
    protected Cursor originalCursor;
    protected Point2D localCoord;

    private boolean recentlyDone;
    private HashMap<EventType, EventHandler> defaultHandlers;
    private EventHandler<? super ScrollEvent> onScrollInPlanTab;
    private ScrollPane.ScrollBarPolicy vBarPolicy;
    private ScrollPane.ScrollBarPolicy hBarPolicy;
    private double vmax;
    private double hmax;

    public AbstractTool(TabPane workbench) {
        this.workbench = workbench;
        dropShadowEffect.setSpread(0.5);
    }

    public abstract void draw();

    protected abstract Map<EventType, EventHandler> toolRequiredHandlers();

    public void setDraggableHBox(DraggableHBox draggableHBox) {
        this.draggableHBox = draggableHBox;
        this.draggableHBox.setOnDragDetected(event -> {
            this.draggableHBox.startFullDrag();
            this.startPhase();
            event.consume();

            dragTool.setActiveElement(modelElementId);
        });

        this.draggableHBox.setOnDragDone(Event::consume);
    }

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

    public void startPhase() {
        draggableHBox.setEffect(dropShadowEffect);
        toolRequiredHandlers()
                .entrySet()
                .forEach(entry -> getWorkbench().getScene().addEventFilter(entry.getKey(), entry.getValue()));
        defaultHandlers()
                .entrySet()
                .forEach(entry -> getWorkbench().getScene().addEventFilter(entry.getKey(), entry.getValue()));

        if (workbench.getSelectionModel().getSelectedItem() instanceof PlanTab) {
            // deactivate scrolling, fixes scrolling to infinity when handling a tool
            onScrollInPlanTab = ((PlanTab) workbench.getSelectionModel().getSelectedItem()).getScrollPane().getOnScroll();
            vBarPolicy = ((PlanTab) workbench.getSelectionModel().getSelectedItem()).getScrollPane().getHbarPolicy();
            hBarPolicy = ((PlanTab) workbench.getSelectionModel().getSelectedItem()).getScrollPane().getVbarPolicy();
            ((PlanTab) workbench.getSelectionModel().getSelectedItem()).getScrollPane().setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            ((PlanTab) workbench.getSelectionModel().getSelectedItem()).getScrollPane().setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            vmax = ((PlanTab) workbench.getSelectionModel().getSelectedItem()).getScrollPane().getVmax();
            hmax = ((PlanTab) workbench.getSelectionModel().getSelectedItem()).getScrollPane().getHmax();

            ((PlanTab) workbench.getSelectionModel().getSelectedItem()).getScrollPane().setVmax(0);
            ((PlanTab) workbench.getSelectionModel().getSelectedItem()).getScrollPane().setHmax(0);
            ((PlanTab) workbench.getSelectionModel().getSelectedItem()).getScrollPane().setOnScroll(Event::consume);
            ((PlanTab) workbench.getSelectionModel().getSelectedItem()).getPlanEditorGroup().setAutoSizeChildren(false);
            ((PlanTab) workbench.getSelectionModel().getSelectedItem()).getPlanEditorGroup().setManaged(false);
        }

        originalCursor = workbench.getScene().getCursor();
        workbench.getScene().setCursor(new ImageCursor(new AlicaIcon(createNewObject().getClass().getSimpleName())));
    }

    public void endPhase() {
        draggableHBox.setEffect(null);
        toolRequiredHandlers()
                .entrySet()
                .forEach(entry -> getWorkbench().getScene().removeEventFilter(entry.getKey(), entry.getValue()));
        defaultHandlers()
                .entrySet()
                .forEach(entry -> getWorkbench().getScene().removeEventFilter(entry.getKey(), entry.getValue()));
        if (workbench.getSelectionModel().getSelectedItem() instanceof PlanTab) {
            // reactivate scrolling
            ((PlanTab) workbench.getSelectionModel().getSelectedItem()).getScrollPane().setOnScroll(onScrollInPlanTab);
            ((PlanTab) workbench.getSelectionModel().getSelectedItem()).getScrollPane().setVbarPolicy(vBarPolicy);
            ((PlanTab) workbench.getSelectionModel().getSelectedItem()).getScrollPane().setHbarPolicy(hBarPolicy);
            ((PlanTab) workbench.getSelectionModel().getSelectedItem()).getScrollPane().setVmax(vmax);
            ((PlanTab) workbench.getSelectionModel().getSelectedItem()).getScrollPane().setHmax(hmax);
            ((PlanTab) workbench.getSelectionModel().getSelectedItem()).getPlanEditorGroup().setAutoSizeChildren(true);
            ((PlanTab) workbench.getSelectionModel().getSelectedItem()).getPlanEditorGroup().setManaged(true);

        }

        draw();
        workbench.getScene().setCursor(originalCursor);
        AbstractEditorTab selectedItem = (AbstractEditorTab) MainController.getInstance().getEditorTabPane().getSelectionModel()
                .getSelectedItem();
        if (selectedItem != null) {
            List<Pair<Long, AbstractPlanElementContainer>> noSelection = new ArrayList<>();
            noSelection.add(new Pair<>(null, null));
            selectedItem
                    .getSelectedPlanElements().set(noSelection);
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
        } else if (((Node) event.getTarget()).getParent() instanceof AbstractPlanElementContainer) {
            localCoord = ((Node) ((Node) event.getTarget()).getParent().getParent())
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
