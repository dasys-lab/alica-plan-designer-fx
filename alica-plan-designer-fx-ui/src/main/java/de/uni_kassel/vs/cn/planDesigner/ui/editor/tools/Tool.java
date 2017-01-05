package de.uni_kassel.vs.cn.planDesigner.ui.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;

/**
 * The {@link Tool} interface provides methods for the tools in the {@link PLDToolBar}.
 * It helps to generalize the usage of these tools for the following workflow:
 * tool is selected (start of the phase) -> Event handler for special actions on the {@link de.uni_kassel.vs.cn.planDesigner.ui.editor.PlanEditorPane}
 * are registered -> The actions are performed. A new model object is created.
 * Or the actions are aborted. -> The phase ends. The event handlers will be removed. and the editor is usable as before.
 * @param <T> type of the model object this tool is associated with
 */
public interface Tool<T extends PlanElement> {
    T createNewObject();
    DragableHBox<T> createToolUI();
    void startPhase();
    void endPhase();
    void draw();
}
