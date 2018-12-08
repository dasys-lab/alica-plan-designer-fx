package de.unikassel.vs.alica.planDesigner.view.editor.tools.state;

import de.unikassel.vs.alica.planDesigner.events.GuiChangePositionEvent;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.DraggableHBox;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaCursor;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaIcon;
import javafx.scene.ImageCursor;
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
        imageCursor = new AlicaCursor(AlicaCursor.Type.failurestate);
        forbiddenCursor = new AlicaCursor(AlicaCursor.Type.forbidden_failurestate);
        addCursor = new AlicaCursor(AlicaCursor.Type.add_failurestate);
        return draggableHBox;
    }


    @Override
    protected GuiChangePositionEvent createEvent(){
        return new GuiChangePositionEvent(GuiEventType.ADD_ELEMENT, Types.FAILURESTATE, null);
    }
}
