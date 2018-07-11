package de.uni_kassel.vs.cn.planDesigner.view.editor.tab.planTab;

import de.uni_kassel.vs.cn.planDesigner.controller.MainWindowController;
import de.uni_kassel.vs.cn.planDesigner.events.GuiModificationEvent;
import de.uni_kassel.vs.cn.planDesigner.view.editor.container.PlanModelVisualisationObject;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.AbstractPlanTab;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.ConditionHBox;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tools.EditorToolBar;
import de.uni_kassel.vs.cn.planDesigner.view.model.ViewModelElement;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class PlanTab extends AbstractPlanTab {

//    private PlanEditorGroup planEditorGroup;
    private ConditionHBox conditionHBox;
    private PlanModelVisualisationObject planModelVisualisationObject;
//    private PmlUiExtensionMap pmlUiExtensionMap;
    private EditorToolBar editorToolBar;
    private StackPane planContent;
    private ScrollPane scrollPane;

    public PlanTab(ViewModelElement plan) {
        super(plan);

//        String absolutePath = planPathPair.getValue().toFile().toString();
//        String uiExtensionMapPath = absolutePath.substring(0, absolutePath.lastIndexOf(".")) + ".pmlex";
//        URI relativeURI = EMFModelUtils.createRelativeURI(new File(uiExtensionMapPath));
//        setPmlUiExtensionMap((PmlUiExtensionMap) AlicaResourceSet.getInstance().getResource(relativeURI, false).getContents().get(0));

        draw(plan);
    }

    private void draw(ViewModelElement plan) {
        planModelVisualisationObject = new PlanModelVisualisationObject(plan.getId());
//        planEditorGroup = new PlanEditorGroup(planModelVisualisationObject, this);
//        planContent = new StackPane(planEditorGroup);
        planContent.setPadding(new Insets(50, 50, 50, 50));
        planContent.setManaged(true);

//        planEditorGroup.setManaged(true);

        editorToolBar = new EditorToolBar(MainWindowController.getInstance().getEditorTabPane());
        scrollPane = new ScrollPane(planContent);
        scrollPane.setFitToHeight(true);
        HBox hBox = new HBox(scrollPane, editorToolBar);
        conditionHBox = new ConditionHBox(plan, selectedPlanElements);
        VBox vBox = new VBox(conditionHBox,hBox);
        VBox.setVgrow(scrollPane,Priority.ALWAYS);
        VBox.setVgrow(hBox,Priority.ALWAYS);
        HBox.setHgrow(scrollPane, Priority.ALWAYS);

        hBox.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        setContent(vBox);
    }

//    public PlanEditorGroup getPlanEditorGroup() {
//        return planEditorGroup;
//    }

    public ConditionHBox getConditionHBox() {
        return conditionHBox;
    }

    public EditorToolBar getEditorToolBar() {
        return editorToolBar;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

//    public void setupPlanVisualisation() {
//        planEditorGroup.setupPlanVisualisation();
//    }

    @Override
    public GuiModificationEvent handleDelete() {
        return null;
    }

    @Override
    public void save() {
//        super.save();
//        try {
//            EMFModelUtils.saveAlicaFile(getPmlUiExtensionMap());
//        } catch (IOException e) {
//            ErrorWindowController.createErrorWindow(I18NRepo.getInstance().getString("label.error.save.pmlex"), e);
//        }
    }

//    public PmlUiExtensionMap getPmlUiExtensionMap() {
//        return pmlUiExtensionMap;
//    }
//
//    public void setPmlUiExtensionMap(PmlUiExtensionMap pmlUiExtensionMap) {
//        this.pmlUiExtensionMap = pmlUiExtensionMap;
//        planModelVisualisationObject = new PlanModelVisualisationObject(getEditable(), pmlUiExtensionMap);
//    }

    public PlanModelVisualisationObject getPlanModelVisualisationObject() {
        return planModelVisualisationObject;
    }

    protected void initSelectedPlanElement(ViewModelElement viewModelElement) {
        super.initSelectedPlanElements(viewModelElement);
        contentProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                newValue.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                    if (event.getTarget() == planContent) {
//                        List<Pair<PlanElement, AbstractPlanElementContainer>> plan = new ArrayList<>();
//                        plan.add(new Pair<>(getEditable(), null));
//                        getSelectedPlanElements().set(plan);
                    }
                });
            }
        });
    }
}
