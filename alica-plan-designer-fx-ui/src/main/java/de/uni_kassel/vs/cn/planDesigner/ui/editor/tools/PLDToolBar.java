package de.uni_kassel.vs.cn.planDesigner.ui.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.ui.editor.tools.condition.PostConditionTool;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tools.condition.PreConditionTool;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tools.condition.RuntimeConditionTool;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tools.state.FailureStateTool;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tools.state.StateTool;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tools.state.SuccessStateTool;
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
        getItems().add(new SuccessStateTool(workbench).createToolUI());
        getItems().add(new FailureStateTool(workbench).createToolUI());
        getItems().add(new RuntimeConditionTool(workbench).createToolUI());
        getItems().add(new PreConditionTool(workbench).createToolUI());
        getItems().add(new PostConditionTool(workbench).createToolUI());
        getItems().add(new InitTransitionTool(workbench).createToolUI());
        getItems().add(new SynchronisationTool(workbench).createToolUI());
        getItems().add(new SyncTransitionTool(workbench).createToolUI());
    }
}
