package de.uni_kassel.vs.cn.planDesigner.ui.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.alica.EntryPoint;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.TabPane;

import java.util.HashMap;
import java.util.Map;

import static de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils.getAlicaFactory;

/**
 * Created by marci on 05.01.17.
 */
public class EntryPointTool extends Tool<EntryPoint> {
    public EntryPointTool(TabPane workbench) {
        super(workbench);
    }

    @Override
    public EntryPoint createNewObject() {
        return getAlicaFactory().createEntryPoint();
    }

    @Override
    public void draw() {

    }

    @Override
    protected Map<EventType, EventHandler> toolRequiredHandlers() {
        return new HashMap<>();
    }
}
