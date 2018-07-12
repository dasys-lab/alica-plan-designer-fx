package de.uni_kassel.vs.cn.planDesigner.view.editor.tools.condition;

import de.uni_kassel.vs.cn.planDesigner.view.Types;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tools.DraggableHBox;
import javafx.scene.control.TabPane;

public class PreConditionTool extends AbstractConditionTool {

    public PreConditionTool(TabPane workbench) {
        super(workbench);
    }

    @Override
    protected void initHandlerMap() {

    }

    @Override
    public DraggableHBox createToolUI() {
        DraggableHBox draggableHBox = new DraggableHBox();
        draggableHBox.setIcon(Types.PRECONDITION);
        return draggableHBox;
    }

}
