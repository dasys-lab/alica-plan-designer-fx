package de.uni_kassel.vs.cn.planDesigner.ui.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.PlanModelVisualisationObject;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtensionMap;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.PlanEditorPane;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tools.PLDToolBar;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by marci on 23.11.16.
 */
public class PlanTab extends EditorTab<Plan> {

    private final HBox planVisualization;
    private final PLDToolBar pldToolBar;
    private final PlanEditorPane planEditorPane;
    private PmlUiExtensionMap pmlUiExtensionMap;
    private final String uiExtensionMapPath;

    public PlanTab(Plan editable, Path filePath, CommandStack commandStack) {
        super(editable, filePath, commandStack);
        String absolutePath = filePath.toString();
        uiExtensionMapPath = absolutePath.substring(0, absolutePath.lastIndexOf(".")) + ".pmlex";

        try {
            pmlUiExtensionMap = EMFModelUtils.loadAlicaFileFromDisk(new File(uiExtensionMapPath));
        } catch (IOException e) {
            // TODO create message window if file could not be opened
            e.printStackTrace();
        }
        planEditorPane = new PlanEditorPane(new PlanModelVisualisationObject(getEditable(), pmlUiExtensionMap), this);

        StackPane planContent = new StackPane(planEditorPane);
        planContent.setPadding(new Insets(50, 50, 50, 50));
        planContent.setManaged(true);

        planEditorPane.setManaged(true);

        pldToolBar = new PLDToolBar(MainController.getInstance().getEditorTabPane());
        ScrollPane scrollPane = new ScrollPane(planContent);
        scrollPane.setFitToHeight(true);
        HBox hBox = new HBox(scrollPane, pldToolBar);
        hBox.setHgrow(scrollPane, Priority.ALWAYS);

        planVisualization = hBox;
        planVisualization.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        setContent(planVisualization);
    }

    public PlanEditorPane getPlanEditorPane() {
        return planEditorPane;
    }

    @Override
    public void save() {
        try {
            EMFModelUtils.saveAlicaFile(getEditable());
            EMFModelUtils.saveAlicaFile(pmlUiExtensionMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
