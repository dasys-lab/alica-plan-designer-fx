package de.uni_kassel.vs.cn.planDesigner.ui.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.ui.editor.PlanEditorPane;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.PlanTab;
import javafx.geometry.Orientation;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;

/**
 * {@link PLDToolBar} is the toolbar on the side of the editor to add states, transitions and various other tools
 */
public class PLDToolBar extends ToolBar {
    public PLDToolBar(TabPane workbench) {
        super();
        setOrientation(Orientation.VERTICAL);
        getItems().add(new StateTool(workbench).createToolUI());
        getItems().add(new TransitionTool(workbench).createToolUI());
        getItems().add(new BehaviourTool(workbench).createToolUI());
        getItems().add(new EntryPointTool(workbench).createToolUI());
    }
}
