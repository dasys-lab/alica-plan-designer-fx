package de.unikassel.vs.alica.planDesigner.view.editor.tools.state;

import de.unikassel.vs.alica.planDesigner.events.GuiChangePositionEvent;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.DraggableHBox;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.ToolButton;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaCursor;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;

public class FailureStateTool extends StateTool {

    public FailureStateTool(TabPane workbench, PlanTab planTab, ToggleGroup group) {
        super(workbench, planTab, group);
    }

    @Override
    public ToolButton createToolUI() {
        ToolButton toolButton = new ToolButton();
        toolButton.setIcon(Types.FAILURESTATE);
        setToolButton(toolButton);
        imageCursor = new AlicaCursor(AlicaCursor.Type.failurestate);
        forbiddenCursor = new AlicaCursor(AlicaCursor.Type.forbidden_failurestate);
        addCursor = new AlicaCursor(AlicaCursor.Type.add_failurestate);
        return toolButton;
    }


    @Override
    protected GuiChangePositionEvent createEvent(){
        return new GuiChangePositionEvent(GuiEventType.ADD_ELEMENT, Types.FAILURESTATE, null);
    }
}
