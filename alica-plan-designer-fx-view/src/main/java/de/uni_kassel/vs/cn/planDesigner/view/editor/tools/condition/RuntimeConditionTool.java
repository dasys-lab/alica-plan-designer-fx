package de.uni_kassel.vs.cn.planDesigner.view.editor.tools.condition;

import de.uni_kassel.vs.cn.planDesigner.view.Types;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tools.DraggableHBox;
import de.uni_kassel.vs.cn.planDesigner.view.model.PlanViewModel;
import javafx.scene.control.TabPane;

public class RuntimeConditionTool extends AbstractConditionTool {

    public RuntimeConditionTool(TabPane workbench, PlanViewModel plan) {
        super(workbench, plan);
    }

    @Override
    protected void initHandlerMap() {

    }

    @Override
    public DraggableHBox createToolUI() {
        DraggableHBox draggableHBox = new DraggableHBox();
        draggableHBox.setIcon(Types.RUNTIMECONDITION);
        setDraggableHBox(draggableHBox);
        return draggableHBox;
    }

}
