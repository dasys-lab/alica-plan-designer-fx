package de.unikassel.vs.alica.planDesigner.view.editor.tools.state;

import de.unikassel.vs.alica.planDesigner.events.GuiChangePositionEvent;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.DraggableHBox;
import javafx.scene.control.TabPane;



public class SuccessStateTool extends StateTool {
    public SuccessStateTool(TabPane workbench, PlanTab planTab) {
        super(workbench, planTab);
    }

    @Override
    public DraggableHBox createToolUI() {
        DraggableHBox draggableHBox = new DraggableHBox();
        draggableHBox.setIcon(Types.SUCCESSSTATE);
        setDraggableHBox(draggableHBox);
        return draggableHBox;
    }

    @Override
    protected GuiChangePositionEvent createEvent(){
        return new GuiChangePositionEvent(GuiEventType.ADD_ELEMENT, Types.SUCCESSSTATE, null);
    }

}
