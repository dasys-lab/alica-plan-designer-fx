package de.uni_kassel.vs.cn.planDesigner.view.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.view.model.PlanViewModel;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
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
    private PlanViewModel plan;
    // Contains Icon and Text and triggers the drag events (start and stop).
    private DraggableHBox draggableHBox;
    // Shadow Effect set on draggableHBox when dragged
    private static final DropShadow dropShadowEffect = new DropShadow(10, Color.GREY);
    private HashMap<EventType, EventHandler> defaultHandlerMap;
    protected HashMap<EventType, EventHandler> customHandlerMap;
    protected Cursor previousCursor;


    private boolean recentlyDone;
    private EventHandler<? super ScrollEvent> onScrollInPlanTab;
    private ScrollPane.ScrollBarPolicy vBarPolicy;
    private ScrollPane.ScrollBarPolicy hBarPolicy;
    private double vmax;
    private double hmax;

    public AbstractTool(TabPane planEditorTabPane, PlanViewModel plan) {
        this.planEditorTabPane = planEditorTabPane;
        this.plan = plan;
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
            // The tool phase is ended, when the courser leaves the scene.
            defaultHandlerMap.put(MouseEvent.MOUSE_DRAGGED, (event) -> {
                MouseEvent e = (MouseEvent) event;
                if (e.getSceneX() + 5 > getPlanEditorTabPane().getScene().getWidth()
                        || e.getSceneY() + 5 > getPlanEditorTabPane().getScene().getHeight()
                        || e.getSceneX() - 5 < 0 || e.getSceneY() - 5 < 0) {
                    endPhase();
                }
            });

            //Listener, that ends a phase, when the mouse is released
            defaultHandlerMap.put(MouseDragEvent.MOUSE_RELEASED, (event) -> endPhase());
        }
        return defaultHandlerMap;
    }

    protected Map<EventType, EventHandler> getCustomHandlerMap() {
        if (customHandlerMap.isEmpty()) {
            this.initHandlerMap();
        }
        return customHandlerMap;
    }

    public void startPhase() {
        draggableHBox.setEffect(dropShadowEffect);
        getCustomHandlerMap()
                .entrySet()
                .forEach(entry -> planEditorTabPane.getScene().addEventFilter(entry.getKey(), entry.getValue()));
        defaultHandlers()
                .entrySet()
                .forEach(entry -> planEditorTabPane.getScene().addEventFilter(entry.getKey(), entry.getValue()));

        // deactivate scrolling, fixes scrolling to infinity when handling a tool
//        if (planEditorTabPane.getSelectionModel().getSelectedItem() instanceof PlanTab) {
//            PlanTab planTab = (PlanTab) planEditorTabPane.getSelectionModel().getSelectedItem();
//            onScrollInPlanTab = planTab.getScrollPane().getOnScroll();
//            vBarPolicy = planTab.getScrollPane().getHbarPolicy();
//            hBarPolicy = planTab.getScrollPane().getVbarPolicy();
//            planTab.getScrollPane().setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//            planTab.getScrollPane().setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//            vmax = planTab.getScrollPane().getVmax();
//            hmax = planTab.getScrollPane().getHmax();
//
//            planTab.getScrollPane().setVmax(0);
//            planTab.getScrollPane().setHmax(0);
//            planTab.getScrollPane().setOnScroll(Event::consume);
//            planTab.getPlanEditorGroup().setAutoSizeChildren(false);
//            planTab.getPlanEditorGroup().setManaged(false);
//        }

        previousCursor = planEditorTabPane.getScene().getCursor();
        // TODO: should be done in the derived tool classes' start phase methods
        //planEditorTabPane.getScene().setCursor(new ImageCursor(new AlicaIcon("special elementType of abstract tool")));
    }

    public void endPhase() {
        draggableHBox.setEffect(null);
        getCustomHandlerMap()
                .entrySet()
                .forEach(entry -> getPlanEditorTabPane().getScene().removeEventFilter(entry.getKey(), entry.getValue()));
        defaultHandlers()
                .entrySet()
                .forEach(entry -> getPlanEditorTabPane().getScene().removeEventFilter(entry.getKey(), entry.getValue()));
//        if (planEditorTabPane.getSelectionModel().getSelectedItem() instanceof PlanTab) {
//            // reactivate scrolling
//            PlanTab planTab = (PlanTab) planEditorTabPane.getSelectionModel().getSelectedItem();
//            planTab.getScrollPane().setOnScroll(onScrollInPlanTab);
//            planTab.getScrollPane().setVbarPolicy(vBarPolicy);
//            planTab.getScrollPane().setHbarPolicy(hBarPolicy);
//            planTab.getScrollPane().setVmax(vmax);
//            planTab.getScrollPane().setHmax(hmax);
//            planTab.getPlanEditorGroup().setAutoSizeChildren(true);
//            planTab.getPlanEditorGroup().setManaged(true);
//        }

        // TODO: fire event to signal successful termination of event
        //draw();
        planEditorTabPane.getScene().setCursor(previousCursor);
//        AbstractPlanTab selectedItem = (AbstractPlanTab) MainWindowController.getInstance().getEditorTabPane().getSelectionModel()
//                .getSelectedItem();
//        if (selectedItem != null) {
//            List<Pair<Long, AbstractPlanElementContainer>> noSelection = new ArrayList<>();
//            noSelection.onAddElement(new Pair<>(null, null));
//            selectedItem
//                    .getSelectedPlanElements().set(noSelection);
//        }
        setRecentlyDone(true);
    }

    public boolean isRecentlyDone() {
        return recentlyDone;
    }

    public void setRecentlyDone(boolean recentlyDone) {
        this.recentlyDone = recentlyDone;
    }

    public void setDraggableHBox(DraggableHBox draggableHBox){
        draggableHBox.setOnDragDetected(event -> {
            draggableHBox.startFullDrag();
            this.startPhase();
            event.consume();
        });

        this.draggableHBox = draggableHBox;
        this.draggableHBox.setOnDragDone(Event::consume);
    }
    public DraggableHBox getDraggableHBox() {
        return draggableHBox;
    }

    public PlanViewModel getPlan() {
        return plan;
    }
}
