package de.uni_kassel.vs.cn.planDesigner.view.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.view.editor.tools.condition.PostConditionTool;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tools.condition.PreConditionTool;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tools.condition.RuntimeConditionTool;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tools.state.FailureStateTool;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tools.state.StateTool;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tools.state.SuccessStateTool;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tools.transition.InitTransitionTool;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tools.transition.SyncTransitionTool;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tools.transition.TransitionTool;
import de.uni_kassel.vs.cn.planDesigner.view.model.PlanViewModel;
import javafx.geometry.Orientation;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * {@link EditorToolBar} is the toolbar on the side of the editor to onAddElement states, transitions and various other tools
 */
public class EditorToolBar extends ToolBar {
    protected List<AbstractTool> tools;

    public EditorToolBar(TabPane workbench, PlanViewModel plan) {
        super();
        setOrientation(Orientation.VERTICAL);
        tools = new ArrayList<>();

        StateTool stateTool = new StateTool(workbench, plan);
        getItems().add(stateTool.createToolUI());
        TransitionTool transitionTool = new TransitionTool(workbench, plan);
        getItems().add(transitionTool.createToolUI());
        BehaviourTool behaviourTool = new BehaviourTool(workbench, plan);
        getItems().add(behaviourTool.createToolUI());
        EntryPointTool entryPointTool = new EntryPointTool(workbench, plan);
        getItems().add(entryPointTool.createToolUI());
        SuccessStateTool successStateTool = new SuccessStateTool(workbench, plan);
        getItems().add(successStateTool.createToolUI());
        FailureStateTool failureStateTool = new FailureStateTool(workbench, plan);
        getItems().add(failureStateTool.createToolUI());
        RuntimeConditionTool runtimeConditionTool = new RuntimeConditionTool(workbench, plan);
        getItems().add(runtimeConditionTool.createToolUI());
        PreConditionTool preConditionTool = new PreConditionTool(workbench, plan);
        getItems().add(preConditionTool.createToolUI());
        PostConditionTool postConditionTool = new PostConditionTool(workbench, plan);
        getItems().add(postConditionTool.createToolUI());
        InitTransitionTool initTransitionTool = new InitTransitionTool(workbench, plan);
        getItems().add(initTransitionTool.createToolUI());
        SynchronizationTool synchronizationTool = new SynchronizationTool(workbench, plan);
        getItems().add(synchronizationTool.createToolUI());
        SyncTransitionTool syncTransitionTool = new SyncTransitionTool(workbench, plan);
        getItems().add(syncTransitionTool.createToolUI());

        tools.addAll(Arrays.asList(stateTool, transitionTool, behaviourTool, entryPointTool, successStateTool,
                failureStateTool, runtimeConditionTool, preConditionTool, postConditionTool, initTransitionTool,
                synchronizationTool, syncTransitionTool));

    }

    public AbstractTool getRecentlyDoneTool() {
        return tools.stream().filter(AbstractTool::isRecentlyDone).findFirst().orElse(null);
    }
}
