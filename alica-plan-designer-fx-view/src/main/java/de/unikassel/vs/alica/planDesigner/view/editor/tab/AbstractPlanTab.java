package de.unikassel.vs.alica.planDesigner.view.editor.tab;

import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.editor.container.AbstractPlanElementContainer;
import de.unikassel.vs.alica.planDesigner.view.editor.container.AbstractPlanHBox;
import de.unikassel.vs.alica.planDesigner.view.editor.container.StateContainer;
import de.unikassel.vs.alica.planDesigner.view.editor.container.TransitionContainer;
import de.unikassel.vs.alica.planDesigner.view.model.SerializableViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.StateViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.List;

public abstract class AbstractPlanTab extends EditorTab {

    protected SimpleObjectProperty<List<Pair<ViewModelElement, AbstractPlanElementContainer>>> selectedPlanElements;

    public AbstractPlanTab(SerializableViewModel serializableViewModel, IGuiModificationHandler handler) {
        super(serializableViewModel, handler);
        initSelectedPlanElements(serializableViewModel);
    }

    public SimpleObjectProperty<List<Pair<ViewModelElement, AbstractPlanElementContainer>>> getSelectedPlanElements() {
        return selectedPlanElements;
    }

    /**
     * initialization for the selected element property, which indicates what elements are selected.
     * specializations for specific selections of {@link AbstractPlanElementContainer}s can be found under
     * AbstractPlanElementContainer#getMouseClickedEventHandler(PlanElement)
     *
     * @param editablePathPair
     */
    // TODO: Review necessary, due to MVC pattern adaption.
    protected void initSelectedPlanElements(ViewModelElement editablePathPair) {
          selectedPlanElements = new SimpleObjectProperty<>(FXCollections.observableArrayList());
          selectedPlanElements.get().add(new Pair<>(editablePathPair, null));
          selectedPlanElements.addListener((observable, oldValue, newValue) -> {
//            if (newValue == null) {
//                // TODO: cannot return here because this avoid deleting selectedEffect on oldValue
//                return;
//            }
            // set selectedEffect on selected elements...

            // ... for state container and its containing elements (really special)
            DropShadow selectedEffect = createSelectedEffect();
            if (newValue.size() == 1 && newValue.get(0).getKey() instanceof StateViewModel
                    && newValue.get(0).getValue() != null
                    && newValue.get(0).getValue() instanceof StateContainer) {
                ((StateContainer) newValue.get(0).getValue()).getStatePlans()
                        .stream()
                        .filter(abstractPlanHBox -> abstractPlanHBox.getAbstractPlan()
                                .equals(newValue.get(0).getKey()))
                        .findFirst().orElseGet(() -> new AbstractPlanHBox(newValue.get(0).getKey(),
                        (StateContainer) newValue.get(0).getValue()))
                        .setEffect(createSelectedEffect());
            } else {
                newValue.forEach(selectedPlanElementPair -> {
                    AbstractPlanElementContainer planElementContainer = selectedPlanElementPair.getValue();
                    if (planElementContainer != null) {
                        planElementContainer.setEffect(selectedEffect);
                    }
                });
            }
            // ... for transitions
            if (newValue.size() == 1 && newValue.get(0).getValue() instanceof TransitionContainer) {
                ((TransitionContainer) newValue.get(0).getValue()).setPotentialDraggableNodesVisible(true);
            }

            if ((oldValue == null)) {
                return;
            }
            oldValue.forEach(selectedPlanElementPair -> {
                AbstractPlanElementContainer planElementContainer = selectedPlanElementPair.getValue();
                if (planElementContainer != null) {
                    // this is weird! If I use planElementContainer.setEffectToStandard() nothing happens..
                    if (planElementContainer.getViewModelElement() == oldValue.get(0).getKey()) {
                        planElementContainer.setEffect(null);
                    }
                    if (planElementContainer instanceof StateContainer) {
                        ((StateContainer) planElementContainer)
                                .getStatePlans()
                                .forEach(abstractPlanHBox -> {
                                    if (abstractPlanHBox.getAbstractPlan() != newValue.get(0).getKey()) {
                                        abstractPlanHBox.setEffect(null);
                                    }
                                });
                    }
                }
                if (planElementContainer instanceof StateContainer) {
                    ((StateContainer) planElementContainer)
                            .getStatePlans()
                            .forEach(abstractPlanHBox -> {
                                if (abstractPlanHBox.getAbstractPlan() != newValue.get(0).getKey()) {
                                    abstractPlanHBox.setEffect(null);
                                }
                            });
                }
            });

            if (oldValue.size() == 1 && oldValue.get(0).getValue() instanceof TransitionContainer) {
                ((TransitionContainer) oldValue.get(0).getValue()).setPotentialDraggableNodesVisible(false);
            }
        });
    }

    public void clearSelectedElements() {
        selectedPlanElements.get().forEach(element -> {
            if (element.getValue() == null) {
                return;
            }
            element.getValue().setEffect(null);
        });
        selectedPlanElements.get().clear();
    }

    private DropShadow createSelectedEffect() {
        DropShadow value = new DropShadow(20, new Color(0, 0.4, 0.9, 0.9));
        value.setBlurType(BlurType.ONE_PASS_BOX);
        value.setSpread(0.45);
        return value;
    }
}
