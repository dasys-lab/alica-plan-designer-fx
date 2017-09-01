package de.uni_kassel.vs.cn.planDesigner.ui.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.PlanModelVisualisationObject;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;
import de.uni_kassel.vs.cn.planDesigner.common.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.controller.ErrorWindowController;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtensionMap;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.PlanEditorPane;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tools.PLDToolBar;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import org.eclipse.emf.common.util.URI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by marci on 23.11.16.
 */
public class PlanTab extends AbstractEditorTab<Plan> {

    private final PlanEditorPane planEditorPane;
    private final ConditionHBox conditionHBox;
    private PlanModelVisualisationObject planModelVisualisationObject;
    private PmlUiExtensionMap pmlUiExtensionMap;
    private final PLDToolBar pldToolBar;

    public PlanTab(Pair<Plan, Path> planPathPair, CommandStack commandStack) {
        super(planPathPair , commandStack);

        String absolutePath = planPathPair.getValue().toFile().toString();
        String uiExtensionMapPath = absolutePath.substring(0, absolutePath.lastIndexOf(".")) + ".pmlex";
        URI relativeURI = EMFModelUtils.createRelativeURI(new File(uiExtensionMapPath));
        setPmlUiExtensionMap((PmlUiExtensionMap) EMFModelUtils.getAlicaResourceSet().getResource(relativeURI, false).getContents().get(0));

        planModelVisualisationObject = new PlanModelVisualisationObject(getEditable(), getPmlUiExtensionMap());
        planEditorPane = new PlanEditorPane(planModelVisualisationObject, this);
        StackPane planContent = new StackPane(planEditorPane);
        planContent.setPadding(new Insets(50, 50, 50, 50));
        planContent.setManaged(true);

        planEditorPane.setManaged(true);

        pldToolBar = new PLDToolBar(MainController.getInstance().getEditorTabPane());
        ScrollPane scrollPane = new ScrollPane(planContent);
        scrollPane.setFitToHeight(true);
        HBox hBox = new HBox(scrollPane, pldToolBar);
        conditionHBox = new ConditionHBox(planPathPair.getKey(), selectedPlanElement, commandStack);
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

    public PLDToolBar getPldToolBar() {
        return pldToolBar;
    }

    public void setupPlanVisualisation() {
        planEditorPane.setupPlanVisualisation();
    }

    @Override
    public void save() {
        super.save();
        try {
            EMFModelUtils.saveAlicaFile(getPmlUiExtensionMap());
        } catch (IOException e) {
            ErrorWindowController.createErrorWindow(I18NRepo.getString("label.error.save.pmlex"), e);
        }
    }

    public PmlUiExtensionMap getPmlUiExtensionMap() {
        return pmlUiExtensionMap;
    }

    public void setPmlUiExtensionMap(PmlUiExtensionMap pmlUiExtensionMap) {
        this.pmlUiExtensionMap = pmlUiExtensionMap;
        planModelVisualisationObject = new PlanModelVisualisationObject(getEditable(), pmlUiExtensionMap);
    }

    public PlanModelVisualisationObject getPlanModelVisualisationObject() {
        return planModelVisualisationObject;
    }
}
