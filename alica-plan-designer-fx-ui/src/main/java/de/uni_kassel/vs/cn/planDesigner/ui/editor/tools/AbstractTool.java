package de.uni_kassel.vs.cn.planDesigner.ui.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.TabPane;

import java.util.Map;

/**
 * The {@link AbstractTool} interface provides methods for the tools in the {@link PLDToolBar}.
 * It helps to generalize the usage of these tools for the following workflow:
 * tool is selected (start of the phase) -> Event handler for special actions on the {@link de.uni_kassel.vs.cn.planDesigner.ui.editor.PlanEditorPane}
 * are registered -> The actions are performed. A new model object is created.
 * Or the actions are aborted. -> The phase ends. The event handlers will be removed. and the editor is usable as before.
 *
 * @param <T> type of the model object this tool is associated with
 */
@SuppressWarnings("unchecked")
public abstract class AbstractTool<T extends PlanElement> {

    protected TabPane workbench;

    public AbstractTool(TabPane workbench) {
        this.workbench = workbench;
    }

    public abstract T createNewObject();
    public abstract void draw();
    protected abstract Map<EventType, EventHandler> toolRequiredHandlers();

    protected Node getWorkbench() {
        return workbench;
    }

    public DragableHBox<T> createToolUI() {
        return new DragableHBox<>(createNewObject(), this);
    }

    public void startPhase() {
        toolRequiredHandlers()
                .entrySet()
                .forEach(entry -> getWorkbench().addEventFilter(entry.getKey(), entry.getValue()));
    }

    public void endPhase() {
        toolRequiredHandlers()
                .entrySet()
                .forEach(entry -> getWorkbench().removeEventFilter(entry.getKey(), entry.getValue()));
        draw();
    }
}
