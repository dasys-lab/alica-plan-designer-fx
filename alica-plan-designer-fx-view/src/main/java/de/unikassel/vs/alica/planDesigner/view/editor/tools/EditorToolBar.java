package de.unikassel.vs.alica.planDesigner.view.editor.tools;

import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.state.FailureStateTool;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.state.StateTool;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.state.SuccessStateTool;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.transition.InitTransitionTool;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.transition.SyncTransitionTool;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.transition.TransitionTool;
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

    public EditorToolBar(TabPane workbench, PlanTab plan) {
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
        InitTransitionTool initTransitionTool = new InitTransitionTool(workbench, plan);
        getItems().add(initTransitionTool.createToolUI());
        SynchronizationTool synchronizationTool = new SynchronizationTool(workbench, plan);
        getItems().add(synchronizationTool.createToolUI());
        SyncTransitionTool syncTransitionTool = new SyncTransitionTool(workbench, plan);
        getItems().add(syncTransitionTool.createToolUI());

        tools.addAll(Arrays.asList(stateTool, transitionTool, behaviourTool, entryPointTool, successStateTool,
                failureStateTool, initTransitionTool,
                synchronizationTool, syncTransitionTool));

    }

    public AbstractTool getRecentlyDoneTool() {
        return tools.stream().filter(AbstractTool::isRecentlyDone).findFirst().orElse(null);
    }
}
