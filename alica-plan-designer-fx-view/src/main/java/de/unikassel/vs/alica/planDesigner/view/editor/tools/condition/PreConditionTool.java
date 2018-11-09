package de.unikassel.vs.alica.planDesigner.view.editor.tools.condition;

import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.DraggableHBox;
import javafx.scene.control.TabPane;

public class PreConditionTool extends AbstractConditionTool {

    public PreConditionTool(TabPane workbench, PlanTab planTab) {
        super(workbench, planTab);
    }

    @Override
    protected void initHandlerMap() {

    }

    @Override
    public DraggableHBox createToolUI() {
        DraggableHBox draggableHBox = new DraggableHBox();
        draggableHBox.setIcon(Types.PRECONDITION);
        setDraggableHBox(draggableHBox);
        return draggableHBox;
    }

}
