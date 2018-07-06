package de.uni_kassel.vs.cn.planDesigner.view.editor.tools;

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

    public EditorToolBar(TabPane workbench) {
        super();
        setOrientation(Orientation.VERTICAL);
        tools = new ArrayList<>();

//        StateTool stateTool = new StateTool(workbench);
//        getItems().onAddElement(stateTool.createToolUI());
//        TransitionTool transitionTool = new TransitionTool(workbench);
//        getItems().onAddElement(transitionTool.createToolUI());
//        BehaviourTool behaviourTool = new BehaviourTool(workbench);
//        getItems().onAddElement(behaviourTool.createToolUI());
//        EntryPointTool entryPointTool = new EntryPointTool(workbench);
//        getItems().onAddElement(entryPointTool.createToolUI());
//        SuccessStateTool successStateTool = new SuccessStateTool(workbench);
//        getItems().onAddElement(successStateTool.createToolUI());
//        FailureStateTool failureStateTool = new FailureStateTool(workbench);
//        getItems().onAddElement(failureStateTool.createToolUI());
//        RuntimeConditionTool runtimeConditionTool = new RuntimeConditionTool(workbench);
//        getItems().onAddElement(runtimeConditionTool.createToolUI());
//        PreConditionTool preConditionTool = new PreConditionTool(workbench);
//        getItems().onAddElement(preConditionTool.createToolUI());
//        PostConditionTool postConditionTool = new PostConditionTool(workbench);
//        getItems().onAddElement(postConditionTool.createToolUI());
//        InitTransitionTool initTransitionTool = new InitTransitionTool(workbench);
//        getItems().onAddElement(initTransitionTool.createToolUI());
//        SynchronisationTool synchronisationTool = new SynchronisationTool(workbench);
//        getItems().onAddElement(synchronisationTool.createToolUI());
//        SyncTransitionTool syncTransitionTool = new SyncTransitionTool(workbench);
//        getItems().onAddElement(syncTransitionTool.createToolUI());
//
//        tools.addAll(Arrays.asList(stateTool, transitionTool, behaviourTool, entryPointTool, successStateTool,
//                failureStateTool, runtimeConditionTool, preConditionTool, postConditionTool, initTransitionTool,
//                synchronisationTool, syncTransitionTool));

    }

    public AbstractTool getRecentlyDoneTool() {
        return tools.stream().filter(AbstractTool::isRecentlyDone).findFirst().orElse(null);
    }
}
