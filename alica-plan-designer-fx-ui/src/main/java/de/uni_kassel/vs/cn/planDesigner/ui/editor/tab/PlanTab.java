package de.uni_kassel.vs.cn.planDesigner.ui.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.PlanModelVisualisationObject;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;
import de.uni_kassel.vs.cn.planDesigner.common.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.controller.ErrorWindowController;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtensionMap;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.PlanEditorGroup;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.AbstractPlanElementContainer;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tools.PLDToolBar;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EContentAdapter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marci on 23.11.16.
 */
public class PlanTab extends AbstractEditorTab<Plan> {

    private PlanEditorGroup planEditorGroup;
    private ConditionHBox conditionHBox;
    private PlanModelVisualisationObject planModelVisualisationObject;
    private PmlUiExtensionMap pmlUiExtensionMap;
    private PLDToolBar pldToolBar;
    private StackPane planContent;
    private ScrollPane scrollPane;

    public PlanTab(Pair<Plan, Path> planPathPair, CommandStack commandStack) {
        super(planPathPair , commandStack);

        String absolutePath = planPathPair.getValue().toFile().toString();
        String uiExtensionMapPath = absolutePath.substring(0, absolutePath.lastIndexOf(".")) + ".pmlex";
        URI relativeURI = EMFModelUtils.createRelativeURI(new File(uiExtensionMapPath));
        setPmlUiExtensionMap((PmlUiExtensionMap) EMFModelUtils.getAlicaResourceSet().getResource(relativeURI, false).getContents().get(0));

        draw(planPathPair, commandStack);

        EContentAdapter adapter = new EContentAdapter() {
            public void notifyChanged(Notification n) {
                draw(planPathPair, commandStack);
            }
        };

        planModelVisualisationObject.getPlan().eAdapters().add(adapter);
    }

    private void draw(Pair<Plan, Path> planPathPair, CommandStack commandStack) {
        planModelVisualisationObject = new PlanModelVisualisationObject(getEditable(), getPmlUiExtensionMap());
        planEditorGroup = new PlanEditorGroup(planModelVisualisationObject, this);
        planContent = new StackPane(planEditorGroup);
        planContent.setPadding(new Insets(50, 50, 50, 50));
        planContent.setManaged(true);

        planEditorGroup.setManaged(true);

        pldToolBar = new PLDToolBar(MainController.getInstance().getEditorTabPane());
        scrollPane = new ScrollPane(planContent);
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

    public PlanEditorGroup getPlanEditorGroup() {
        return planEditorGroup;
    }

    public ConditionHBox getConditionHBox() {
        return conditionHBox;
    }

    public PLDToolBar getPldToolBar() {
        return pldToolBar;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setupPlanVisualisation() {
        planEditorGroup.setupPlanVisualisation();
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

    @Override
    protected void initSelectedPlanElement(Pair<Plan, Path> editablePathPair) {
        super.initSelectedPlanElement(editablePathPair);
        contentProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                newValue.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                    if (event.getTarget() == planContent) {
                        List<Pair<PlanElement, AbstractPlanElementContainer>> plan = new ArrayList<>();
                        plan.add(new Pair<>(getEditable(), null));
                        getSelectedPlanElement().set(plan);
                    }
                });
            }
        });
    }
}
