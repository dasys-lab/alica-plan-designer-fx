package de.unikassel.vs.alica.planDesigner.view.editor.tools.state;

import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.ToolButton;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaCursor;
import javafx.geometry.Point2D;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;


public class SuccessStateTool extends StateTool {
    public SuccessStateTool(TabPane workbench, PlanTab planTab, ToggleGroup group) {
        super(workbench, planTab, group);
        this.stateType = Types.SUCCESSSTATE;
    }

    @Override
    public ToolButton createToolUI() {
        ToolButton toolButton = new ToolButton();
        toolButton.setIcon(stateType);
        setToolButton(toolButton);
        imageCursor = new AlicaCursor(AlicaCursor.Type.successstate, 8, 8);
        forbiddenCursor = new AlicaCursor(AlicaCursor.Type.forbidden_successstate, 8, 8);
        addCursor = new AlicaCursor(AlicaCursor.Type.add_successstate, 8, 8);
        return toolButton;
    }

}
