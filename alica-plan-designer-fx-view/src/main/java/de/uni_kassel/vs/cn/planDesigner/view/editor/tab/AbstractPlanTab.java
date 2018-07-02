package de.uni_kassel.vs.cn.planDesigner.view.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.common.ViewModelElement;
import de.uni_kassel.vs.cn.planDesigner.controller.IsDirtyWindowController;
import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.editor.container.AbstractPlanElementContainer;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPlanTab extends Tab implements IEditorTab{

    protected boolean dirty;
    protected SimpleObjectProperty<List<Pair<Long, AbstractPlanElementContainer>>> selectedPlanElements;

    private ViewModelElement viewModelElement;
    private ObservableList<Node> visualRepresentations;

    //TODO add to scene
    private final KeyCombination ctrlA = new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN);

    public AbstractPlanTab(Long planElementId) {
        selectedPlanElements = new SimpleObjectProperty<>(new ArrayList<>());
        selectedPlanElements.get().add(new Pair<>(planElementId, null));
    }

    // TODO: Review necessary, due to MVC pattern adaption.
    public AbstractPlanTab(ViewModelElement viewModelElement) {
        // set Tab Caption to name of file, represented by this Tab
        super(viewModelElement.getName());

        this.viewModelElement = viewModelElement;
        initSelectedPlanElements(viewModelElement);

        // add Ctrl+A handlerinterfaces to scene
//        EditorTabPane editorTabPane = MainWindowController.getInstance().getEditorTabPane();
//        editorTabPane.getScene().addEventHandler(KeyEvent.KEY_RELEASED, event -> {
//            if (ctrlA.match(event)) {
//                selectAllPlanElements(editorTabPane, event);
//            }
//        });

        // add close tab handlerinterfaces
        setClosable(true);
        setOnCloseRequest(e -> {
            // popup for trying to close dirty tab
            if (dirty) {
                IsDirtyWindowController.createIsDirtyWindow(this, e);
            }
        });
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

//    private void selectAllPlanElements(EditorTabPane editorTabPane, KeyEvent event) {
//        Tab selectedTab = editorTabPane.getSelectionModel().getSelectedItem();
//        if (selectedTab == null && !(selectedTab instanceof PlanTab)) {
//            return;
//        }
//
//        selectedPlanElements = new SimpleObjectProperty<>(FXCollections.observableArrayList());
//        visualRepresentations = FXCollections.observableArrayList();
//
//        selectedPlanElements.get().addListener(new ListChangeListener<Pair<Long, AbstractPlanElementContainer>>() {
//            @Override
//            public void onChanged(Change<? extends Pair<Long, AbstractPlanElementContainer>> change) {
//                while (change.next()) {
//                    change.getAddedSubList().forEach(o -> {
//                        o.getValue().setEffect(createSelectedEffect());
//                        visualRepresentations.add(o.getValue().getVisualRepresentation());
//                    });
//                }
//            }
//        });
//
//        PlanTab tab = (PlanTab) selectedTab;
//        tab.getPlanEditorGroup().getStateContainers().forEach(stateContainer -> {
//            selectedPlanElements.get()
//                    .add(new Pair<Long, AbstractPlanElementContainer>(stateContainer.getModelElementId(), stateContainer));
//        });
//        tab.getPlanEditorGroup().getEntryPointContainers().forEach(epContainer -> {
//            selectedPlanElements.get()
//                    .add(new Pair<Long, AbstractPlanElementContainer>(epContainer.getModelElementId(), epContainer));
//        });
//        tab.getPlanEditorGroup().getTransitionContainers().forEach(transitionContainer -> {
//            selectedPlanElements.get()
//                    .add(new Pair<Long, AbstractPlanElementContainer>(transitionContainer.getModelElementId(), transitionContainer));
//        });
//        tab.getPlanEditorGroup().getSynchronisationContainers().forEach(syncContainer -> {
//            selectedPlanElements.get()
//                    .add(new Pair<Long, AbstractPlanElementContainer>(syncContainer.getModelElementId(), syncContainer));
//        });
//    }

    /**
     * initialization for the selected element property, which indicates what elements are selected.
     * specializations for specific selections of {@link AbstractPlanElementContainer}s can be found under
     * AbstractPlanElementContainer#getMouseClickedEventHandler(PlanElement)
     *
     * @param editablePathPair
     */
    // TODO: Review necessary, due to MVC pattern adaption.
    protected void initSelectedPlanElements(ViewModelElement editablePathPair) {
//        selectedPlanElements = new SimpleObjectProperty<>(FXCollections.observableArrayList());
//        selectedPlanElements.get().add(new Pair<>(viewModelElement.getKey(), null));
//        selectedPlanElements.addListener((observable, oldValue, newValue) -> {
//            if (newValue == null) {
//                // TODO: cannot return here because this avoid deleting selectedEffect on oldValue
//                return;
//            }
//            // set selectedEffect on selected elements...
//
//            // ... for state container and its containing elements (really special)
//            DropShadow selectedEffect = createSelectedEffect();
//            if (newValue.size() == 1 && newValue.get(0).getKey() instanceof AbstractPlan
//                    && newValue.get(0).getValue() != null
//                    && newValue.get(0).getValue() instanceof StateContainer) {
//                ((StateContainer) newValue.get(0).getValue()).getStatePlans()
//                        .stream()
//                        .filter(abstractPlanHBox -> abstractPlanHBox.getAbstractPlan()
//                                .equals(newValue.get(0).getKey()))
//                        .findFirst().orElseGet(() -> new AbstractPlanHBox(newValue.get(0).getKey(),
//                        (StateContainer) newValue.get(0).getValue()))
//                        .setEffect(createSelectedEffect());
//            } else {
//                newValue.forEach(selectedPlanElementPair -> {
//                    AbstractPlanElementContainer planElementContainer = selectedPlanElementPair.getValue();
//                    if (planElementContainer != null) {
//                        planElementContainer.setEffect(selectedEffect);
//                    }
//                });
//            }
//            // ... for transitions
//            if (newValue.size() == 1 && newValue.get(0).getValue() instanceof TransitionContainer) {
//                ((TransitionContainer) newValue.get(0).getValue()).setPotentialDraggableNodesVisible(true);
//            }
//
//            if ((oldValue == null)) {
//                return;
//            }
//            oldValue.forEach(selectedPlanElementPair -> {
//                AbstractPlanElementContainer planElementContainer = selectedPlanElementPair.getValue();
//                if (planElementContainer != null) {
//                    // this is weird! If I use planElementContainer.setEffectToStandard() nothing happens..
//                    if (planElementContainer.getModelElementId() == oldValue.get(0).getKey()) {
//                        planElementContainer.setEffect(null);
//                    }
//                    if (planElementContainer instanceof StateContainer) {
//                        ((StateContainer) planElementContainer)
//                                .getStatePlans()
//                                .forEach(abstractPlanHBox -> {
//                                    if (abstractPlanHBox.getAbstractPlan() != newValue.get(0).getKey()) {
//                                        abstractPlanHBox.setEffect(null);
//                                    }
//                                });
//                    }
//                }
//                if (planElementContainer instanceof StateContainer) {
//                    ((StateContainer) planElementContainer)
//                            .getStatePlans()
//                            .forEach(abstractPlanHBox -> {
//                                if (abstractPlanHBox.getAbstractPlan() != newValue.get(0).getKey()) {
//                                    abstractPlanHBox.setEffect(null);
//                                }
//                            });
//                }
//            });
//
//            if (oldValue.size() == 1 && oldValue.get(0).getValue() instanceof TransitionContainer) {
//                ((TransitionContainer) oldValue.get(0).getValue()).setPotentialDraggableNodesVisible(false);
//            }
//        });
    }

    public void clearSelectedElements() {
        selectedPlanElements.get().forEach(element -> {
            if (element.getValue() == null) {
                return;
            }
            element.getValue().setEffect(null);
        });
        selectedPlanElements.get().clear();
        visualRepresentations.clear();
    }

    private DropShadow createSelectedEffect() {
        //DropShadow value = new DropShadow(StateContainer.STATE_RADIUS, new Color(0, 0.4, 0.9, 0.9));
        DropShadow value = new DropShadow(20, new Color(0, 0.4, 0.9, 0.9));
        value.setBlurType(BlurType.ONE_PASS_BOX);
        value.setSpread(0.45);
        return value;
    }

    public Path getFilePath() {
        I18NRepo i18NRepo = I18NRepo.getInstance();
        if (viewModelElement.getType() == i18NRepo.getString("alicatype.plan")) {
            return Paths.get(viewModelElement.getRelativeDirectory(), viewModelElement.getName() + ".pml");
        } else if (viewModelElement.getType() == i18NRepo.getString("alicatype.plantype")) {
            return Paths.get(viewModelElement.getRelativeDirectory(), viewModelElement.getName() + ".pty");
        } else if (viewModelElement.getType() == i18NRepo.getString("alicatype.behaviour")) {
            return Paths.get(viewModelElement.getRelativeDirectory(), viewModelElement.getName() + ".beh");
        } else if (viewModelElement.getType() == i18NRepo.getString("alicatype.taskrepository")) {
            return Paths.get(viewModelElement.getRelativeDirectory(), viewModelElement.getName() + ".tsk");
        } else {
            return null;
        }
    }

    // TODO: Review necessary, due to MVC pattern adaption.
    public void save() {
        System.err.println("AbstractPlanTab: Save() not implemented!");
        return;
    }

    public SimpleObjectProperty<List<Pair<Long, AbstractPlanElementContainer>>> getSelectedPlanElements() {
        return selectedPlanElements;
    }

    private void setViewModelElement(ViewModelElement viewModelElement) {
        this.viewModelElement = viewModelElement;
    }

    public ViewModelElement getViewModelElement() {
        return viewModelElement;
    }

    @Override
    public boolean representsViewModelElement(ViewModelElement viewModelElement) {
        return this.viewModelElement.equals(viewModelElement);
    }
}
