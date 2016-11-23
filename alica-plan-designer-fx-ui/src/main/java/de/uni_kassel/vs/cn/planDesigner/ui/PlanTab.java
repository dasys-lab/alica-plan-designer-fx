package de.uni_kassel.vs.cn.planDesigner.ui;

import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtensionMap;
import javafx.scene.canvas.Canvas;

import java.io.File;
import java.io.IOException;

/**
 * Created by marci on 23.11.16.
 */
public class PlanTab extends EditorTab<Plan> {

    private final Canvas planVisualization;
    private PmlUiExtensionMap pmlUiExtensionMap;

    public PlanTab(Plan editable, File file) {
        super(editable, file);
        String absolutePath = file.getAbsolutePath();
        String uiExtensionMapPath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));

        try {
            pmlUiExtensionMap = EMFModelUtils.loadAlicaFileFromDisk(new File(uiExtensionMapPath));
        } catch (IOException e) {
            // TODO create message window if file could not be opened
            e.printStackTrace();
        }
        planVisualization = new Canvas(500, 500);
        setContent(planVisualization);
    }

    @Override
    public void save() {

    }
}
