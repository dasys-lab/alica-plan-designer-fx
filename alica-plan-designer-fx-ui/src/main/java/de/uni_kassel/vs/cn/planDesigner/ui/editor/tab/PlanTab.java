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
public class PlanTab extends AbstractEditorTab<Plan> {

    private final PlanEditorPane planEditorPane;
    private final ConditionHBox conditionHBox;
    private PmlUiExtensionMap pmlUiExtensionMap;

    public PlanTab(Plan editable, Path filePath, CommandStack commandStack) {
        super(editable, filePath, commandStack);
        String absolutePath = filePath.toString();
        String uiExtensionMapPath = absolutePath.substring(0, absolutePath.lastIndexOf(".")) + ".pmlex";

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

        PLDToolBar pldToolBar = new PLDToolBar(MainController.getInstance().getEditorTabPane());
        ScrollPane scrollPane = new ScrollPane(planContent);
        scrollPane.setFitToHeight(true);
        HBox hBox = new HBox(scrollPane, pldToolBar);
        conditionHBox = new ConditionHBox(editable, selectedPlanElement, commandStack);
        VBox vBox = new VBox(conditionHBox,hBox);
        VBox.setVgrow(scrollPane,Priority.ALWAYS);
        VBox.setVgrow(hBox,Priority.ALWAYS);
        HBox.setHgrow(scrollPane, Priority.ALWAYS);

        hBox.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        setContent(vBox);
    }

    public PlanEditorPane getPlanEditorPane() {
        return planEditorPane;
    }

    public ConditionHBox getConditionHBox() {
        return conditionHBox;
    }

    @Override
    public void save() {
        try {
            EMFModelUtils.saveAlicaFile(getEditable());
            EMFModelUtils.saveAlicaFile(pmlUiExtensionMap);
        } catch (IOException e) {
            // TODO show error dialog
            e.printStackTrace();
        }
    }
}
