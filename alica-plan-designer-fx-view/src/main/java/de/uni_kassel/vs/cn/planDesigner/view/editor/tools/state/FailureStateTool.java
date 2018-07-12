package de.uni_kassel.vs.cn.planDesigner.view.editor.tools.state;

import de.uni_kassel.vs.cn.planDesigner.view.Types;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tools.DraggableHBox;
import javafx.scene.control.TabPane;

public class FailureStateTool extends StateTool {

    public FailureStateTool(TabPane workbench) {
        super(workbench);
    }

    @Override
    public DraggableHBox createToolUI() {
        DraggableHBox draggableHBox = new DraggableHBox();
        draggableHBox.setIcon(Types.FAILURESTATE);
        return draggableHBox;
    }

}
