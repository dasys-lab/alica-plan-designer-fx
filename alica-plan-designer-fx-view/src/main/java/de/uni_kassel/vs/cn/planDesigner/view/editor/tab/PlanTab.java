package de.uni_kassel.vs.cn.planDesigner.view.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.controller.MainWindowController;
import de.uni_kassel.vs.cn.planDesigner.events.GuiModificationEvent;
import de.uni_kassel.vs.cn.planDesigner.view.editor.container.PlanModelVisualisationObject;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tools.EditorToolBar;
import de.uni_kassel.vs.cn.planDesigner.view.model.ViewModelElement;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PlanTab extends AbstractPlanTab {

    private Group planEditorGroup;
    private ConditionHBox conditionHBox;
    private PlanModelVisualisationObject planModelVisualisationObject;
    //    private PmlUiExtensionMap pmlUiExtensionMap;
    private EditorToolBar editorToolBar;
    private StackPane planContent;
    private ScrollPane scrollPane;

    public PlanTab(ViewModelElement viewModelElement) {
        super(viewModelElement);
        String plansPath = MainWindowController.getInstance().getPlansPath();
        String absolutePath = Paths.get(plansPath, viewModelElement.getRelativeDirectory(), viewModelElement.getName() + ".pml")
                .toFile().toString();
        String uiExtensionMapPath = absolutePath.substring(0, absolutePath.lastIndexOf(".")) + ".pmlex";
        Path relativePath = Paths.get(viewModelElement.getRelativeDirectory(), viewModelElement.getName() + ".pml");
//        URI relativeURI = EMFModelUtils.createRelativeURI(new File(uiExtensionMapPath));
//        setPmlUiExtensionMap((PmlUiExtensionMap) AlicaResourceSet.getInstance().getResource(relativeURI, false).getContents().get(0));

        draw(viewModelElement);

//        EContentAdapter adapter = new EContentAdapter() {
//            public void notifyChanged(Notification n) {
//                draw(viewModelElement, commandStack);
//            }
//        };
//
//        planModelVisualisationObject.getPlan().eAdapters().onAddElement(adapter);
    }

    private void draw(ViewModelElement planPathPair) {
        planModelVisualisationObject = new PlanModelVisualisationObject(getViewModelElement().getId());
        planEditorGroup = new Group();
        planContent = new StackPane(planEditorGroup);
        planContent.setPadding(new Insets(50, 50, 50, 50));
        planContent.setManaged(true);

        planEditorGroup.setManaged(true);
//
        editorToolBar = new EditorToolBar(MainWindowController.getInstance().getEditorTabPane());
        scrollPane = new ScrollPane(planContent);
        scrollPane.setFitToHeight(true);
        HBox hBox = new HBox(scrollPane, editorToolBar);
        conditionHBox = new ConditionHBox((ViewModelElement) getViewModelElement(), selectedPlanElements);
        VBox vBox = new VBox(conditionHBox, hBox);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        VBox.setVgrow(hBox, Priority.ALWAYS);
        HBox.setHgrow(scrollPane, Priority.ALWAYS);

        hBox.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        setContent(vBox);
    }

//    public PlanEditorGroup getPlanEditorGroup() {
//        return planEditorGroup;
//    }
//
//    public ConditionHBox getConditionHBox() {
//        return conditionHBox;
//    }
//
//    public PLDToolBar getPldToolBar() {
//        return editorToolBar;
//    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

//    public void setupPlanVisualisation() {
//        planEditorGroup.setupPlanVisualisation();
//    }

    @Override
    public void save() {
        super.save();
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

//    public PlanModelVisualisationObject getPlanModelVisualisationObject() {
//        return planModelVisualisationObject;
//    }

    protected void initSelectedPlanElement(ViewModelElement viewModelElement) {
        super.initSelectedPlanElements(viewModelElement);
        contentProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null) {
//                newValue.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
//                    if (event.getTarget() == planContent) {
//                        List<Pair<PlanElement, AbstractPlanElementContainer>> plan = new ArrayList<>();
//                        plan.onAddElement(new Pair<>(getEditable(), null));
//                        getSelectedPlanElements().set(plan);
//                    }
//                });
//            }
        });
    }

    @Override
    public GuiModificationEvent handleDelete() {
        System.err.println("PlanTab: Not implemented!");
        return null;
    }
}
