package de.uni_kassel.vs.cn.planDesigner.ui.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.PlanEditorPane;
import javafx.event.EventHandler;
import javafx.event.EventType;

import java.util.Map;

/**
 * The {@link Tool} interface provides methods for the tools in the {@link PLDToolBar}.
 * It helps to generalize the usage of these tools for the following workflow:
 * tool is selected (start of the phase) -> Event handler for special actions on the {@link de.uni_kassel.vs.cn.planDesigner.ui.editor.PlanEditorPane}
 * are registered -> The actions are performed. A new model object is created.
 * Or the actions are aborted. -> The phase ends. The event handlers will be removed. and the editor is usable as before.
 *
 * @param <T> type of the model object this tool is associated with
 */
public abstract class Tool<T extends PlanElement> {

    protected PlanEditorPane workbench;

    public Tool(PlanEditorPane workbench) {
        this.workbench = workbench;
    }

    public abstract T createNewObject();
    public abstract void draw();
    protected abstract Map<EventType, EventHandler> toolRequiredHandlers();

    public DragableHBox<T> createToolUI() {
        return new DragableHBox<>(createNewObject(), this);
    }

    public void startPhase() {
        toolRequiredHandlers()
                .entrySet()
                .forEach(entry -> workbench.addEventFilter(entry.getKey(), entry.getValue()));
    }

    public void endPhase() {
        toolRequiredHandlers()
                .entrySet()
                .forEach(entry -> workbench.removeEventFilter(entry.getKey(), entry.getValue()));
        draw();
    }
}
