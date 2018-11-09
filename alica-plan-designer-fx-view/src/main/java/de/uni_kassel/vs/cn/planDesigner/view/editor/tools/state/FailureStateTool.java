package de.uni_kassel.vs.cn.planDesigner.view.editor.tools.state;

import de.uni_kassel.vs.cn.planDesigner.events.GuiChangePositionEvent;
import de.uni_kassel.vs.cn.planDesigner.events.GuiEventType;
import de.uni_kassel.vs.cn.planDesigner.view.Types;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.planTab.PlanTab;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tools.DraggableHBox;
import de.uni_kassel.vs.cn.planDesigner.view.model.PlanViewModel;
import javafx.scene.control.TabPane;

public class FailureStateTool extends StateTool {

    public FailureStateTool(TabPane workbench, PlanTab planTab) {
        super(workbench, planTab);
    }

    @Override
    public DraggableHBox createToolUI() {
        DraggableHBox draggableHBox = new DraggableHBox();
        draggableHBox.setIcon(Types.FAILURESTATE);
        setDraggableHBox(draggableHBox);
        return draggableHBox;
    }


    @Override
    protected GuiChangePositionEvent createEvent(){
        return new GuiChangePositionEvent(GuiEventType.ADD_ELEMENT, Types.FAILURESTATE, null);
    }

}
