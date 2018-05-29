package de.uni_kassel.vs.cn.planDesigner.view.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.view.repo.RepositoryViewModel;
import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.controller.ErrorWindowController;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.view.editor.container.AbstractPlanElementContainer;
import de.uni_kassel.vs.cn.planDesigner.view.editor.container.AbstractPlanHBox;
import de.uni_kassel.vs.cn.planDesigner.view.editor.container.StateContainer;
import de.uni_kassel.vs.cn.planDesigner.view.editor.container.TransitionContainer;
import de.uni_kassel.vs.cn.planDesigner.view.repo.RepositoryTab;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Tab;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public abstract class AbstractEditorTab extends Tab {

    private Observer observer;

    private Pair<Long, Path> editablePathPair;



    protected SimpleObjectProperty<List<Pair<Long, AbstractPlanElementContainer>>> selectedPlanElement;

    public AbstractEditorTab(Long planElementId) {
        selectedPlanElement = new SimpleObjectProperty<>(new ArrayList<>());
        selectedPlanElement.get().add(new Pair<>(planElementId, null));
    }

    // TODO: Review necessary, due to MVC pattern adaption.
    public AbstractEditorTab(Pair<Long, Path> editablePathPair) {
        super(editablePathPair.getValue().getFileName().toString());

        RepositoryViewModel repositoryViewModel = RepositoryViewModel.getInstance();

        this.editablePathPair = editablePathPair;
        initSelectedPlanElement(editablePathPair);

        // create observer for dirty flag and add it to the command stack
        observer = (o, arg) -> {
            if (((CommandStack) o).isAbstractPlanInItsCurrentFormSaved(getEditable())) {
                setText(getEditablePathPair().getValue().getFileName().toString());
            } else {
                setText(getEditablePathPair().getValue().getFileName() + "*");
            }
        };
        if (commandStack != null) {
            commandStack.addObserver(observer);
        }

        // handle close tab
        setClosable(true);
        setOnCloseRequest(e -> {
            commandStack.deleteObserver(observer);
            if (editablePathPair.getKey() instanceof TaskRepository) {
                return;
            }
            try {

                File file = repositoryViewModel.getPathForAbstractPlan((AbstractPlan) getEditable()).toFile();
                EObject key = EMFModelUtils.reloadAlicaFileFromDisk(file);
                if (editablePathPair.getKey() instanceof Plan) {
                    Pair<Plan, Path> planPathPair = repositoryViewModel.getPlans()
                            .stream()
                            .filter(pair -> pair.getKey().getId() == editablePathPair.getKey().getId())
                            .findFirst().orElse(null);
                    repositoryViewModel.getPlans().remove(planPathPair);
                    repositoryViewModel.getPlans().add(new Pair<>((Plan) key, planPathPair.getValue()));
                    File pmlexFile = new File(file.getAbsolutePath().replace(".pml", ".pmlex"));
                    ((PlanTab) this).setPmlUiExtensionMap(EMFModelUtils.reloadAlicaFileFromDisk(pmlexFile));
                }
                if (editablePathPair.getKey() instanceof Behaviour) {
                    Pair<Behaviour, Path> behaviourPathPair = repositoryViewModel.getBehaviours()
                            .stream()
                            .filter(pair -> pair.getKey().getId() == editablePathPair.getKey().getId())
                            .findFirst().orElse(null);
                    repositoryViewModel.getBehaviours().remove(behaviourPathPair);
                    repositoryViewModel.getBehaviours().add(new Pair<>((Behaviour) key, behaviourPathPair.getValue()));
                }
                if (editablePathPair.getKey() instanceof PlanType) {
                    Pair<PlanType, Path> planTypePathPair = repositoryViewModel.getPlanTypes()
                            .stream()
                            .filter(pair -> pair.getKey().getId() == editablePathPair.getKey().getId())
                            .findFirst().orElse(null);
                    repositoryViewModel.getPlanTypes().remove(planTypePathPair);
                    repositoryViewModel.getPlanTypes().add(new Pair<>((PlanType) key, planTypePathPair.getValue()));
                }
                MainController.getInstance().getRepositoryTabPane().getTabs().forEach(tab -> {
                    if (tab instanceof RepositoryTab) {
                        ((RepositoryTab) tab).sort();
                    }
                });
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    /**
     * initialization for the selected element property, which indicates what elements are selected.
     * specializations for specific selections of {@link AbstractPlanElementContainer}s can be found under
     * {@link AbstractPlanElementContainer#getMouseClickedEventHandler(PlanElement)}
     *
     * @param editablePathPair
     */
    // TODO: Review necessary, due to MVC pattern adaption.
    protected void initSelectedPlanElement(Pair<Long, Path> editablePathPair) {
        selectedPlanElement = new SimpleObjectProperty<>(new ArrayList<>());
        selectedPlanElement.get().add(new Pair<>(editablePathPair.getKey(), null));
        selectedPlanElement.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                DropShadow value = createSelectedEffect();
                if (newValue.size() == 1 && newValue.get(0).getKey() instanceof AbstractPlan
                        && newValue.get(0).getValue() != null
                        && newValue.get(0).getValue() instanceof StateContainer) {
                    ((StateContainer) newValue.get(0).getValue()).getStatePlans()
                            .stream()
                            .filter(abstractPlanHBox -> abstractPlanHBox.getAbstractPlan()
                                    .equals(newValue.get(0).getKey()))
                            .findFirst().orElseGet(() -> new AbstractPlanHBox(newValue.get(0).getKey(),
                            (StateContainer) newValue.get(0).getValue())).setEffect(createSelectedEffect());

                } else {
                    newValue.forEach(selectedPlanElementPair -> {
                        AbstractPlanElementContainer planElementContainer = selectedPlanElementPair.getValue();
                        if (planElementContainer != null) {
                            planElementContainer.setEffect(value);
                        }
                    });
                }
                if (newValue.size() == 1 && newValue.get(0).getValue() instanceof TransitionContainer) {
                    ((TransitionContainer) newValue.get(0).getValue()).setPotentialDraggableNodesVisible(true);
                }
            }

            if ((oldValue != null)) {
                oldValue.forEach(selectedPlanElementPair -> {
                    AbstractPlanElementContainer planElementContainer = selectedPlanElementPair.getValue();
                    if (planElementContainer != null) {
                        // this is weird! If I use planElementContainer.setEffectToStandard() nothing happens..
                        if (planElementContainer.getContainedElement() == oldValue.get(0).getKey()) {
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
                });

                if (oldValue.size() == 1 && oldValue.get(0).getValue() instanceof TransitionContainer) {
                    ((TransitionContainer) oldValue.get(0).getValue()).setPotentialDraggableNodesVisible(false);
                }
            }
        });
    }

    private DropShadow createSelectedEffect() {
        DropShadow value = new DropShadow(StateContainer.STATE_RADIUS, new Color(0, 0.4, 0.9, 0.9));
        value.setBlurType(BlurType.ONE_PASS_BOX);
        value.setSpread(0.45);
        return value;
    }

    public Path getFilePath() {
        return editablePathPair.getValue();
    }

    // TODO: Review necessary, due to MVC pattern adaption.
    public void save() {
        try {
            setText(getText().replace("*", ""));
            //EMFModelUtils.saveAlicaFile(getEditable());
            //getCommandStack().setSavedForAbstractPlan(editablePathPair.getKey());
        } catch (IOException e) {
            ErrorWindowController.createErrorWindow(I18NRepo.getInstance().getString("label.error.save"), e);
        }
    }

    public Long getEditable() {
        return editablePathPair.getKey();
    }

    public SimpleObjectProperty<List<Pair<Long, AbstractPlanElementContainer>>> getSelectedPlanElement() {
        return selectedPlanElement;
    }

    private void setEditablePathPair(Pair<Long, Path> editablePathPair) {
        this.editablePathPair = editablePathPair;
    }

    public Pair<Long, Path> getEditablePathPair() {
        return editablePathPair;
    }
}
