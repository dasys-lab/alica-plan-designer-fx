package de.uni_kassel.vs.cn.planDesigner.ui.editor;

import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtensionMap;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.IOException;

/**
 * Created by marci on 23.11.16.
 */
public class PlanTab extends EditorTab<Plan> {

    private final HBox planVisualization;
    private final PLDToolBar pldToolBar;
    private final PlanEditorPane planContent;
    private PmlUiExtensionMap pmlUiExtensionMap;
    private final String uiExtensionMapPath;

    public PlanTab(Plan editable, File file) {
        super(editable, file);
        String absolutePath = file.getAbsolutePath();
        uiExtensionMapPath = absolutePath.substring(0, absolutePath.lastIndexOf(".")) + ".pmlex";
        pldToolBar = new PLDToolBar();

        try {
            pmlUiExtensionMap = EMFModelUtils.loadAlicaFileFromDisk(new File(uiExtensionMapPath));
        } catch (IOException e) {
            // TODO create message window if file could not be opened
            e.printStackTrace();
        }
        planContent = new PlanEditorPane();
        planContent.setManaged(true);
        ScrollPane scrollPane = new ScrollPane(planContent);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setManaged(true);
        planVisualization = new HBox(scrollPane,pldToolBar);
        setContent(planVisualization);
    }

    @Override
    public void save() {
        try {
            EMFModelUtils.saveAlicaFile(getFile(),getEditable());
            EMFModelUtils.saveAlicaFile(new File(uiExtensionMapPath), pmlUiExtensionMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
