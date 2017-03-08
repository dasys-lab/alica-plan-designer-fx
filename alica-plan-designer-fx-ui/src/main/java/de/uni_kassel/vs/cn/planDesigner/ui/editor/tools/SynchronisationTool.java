package de.uni_kassel.vs.cn.planDesigner.ui.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.TabPane;

import java.util.Map;

/**
 * Created by marci on 08.03.17.
 */
public class SynchronisationTool extends AbstractTool {

    public SynchronisationTool(TabPane workbench) {
        super(workbench);
    }

    @Override
    public PlanElement createNewObject() {
        return null;
    }

    @Override
    public void draw() {

    }

    @Override
    protected Map<EventType, EventHandler> toolRequiredHandlers() {
        return null;
    }
}
