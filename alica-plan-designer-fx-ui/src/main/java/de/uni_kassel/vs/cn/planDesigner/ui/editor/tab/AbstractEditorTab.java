package de.uni_kassel.vs.cn.planDesigner.ui.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.alica.*;
import de.uni_kassel.vs.cn.planDesigner.alica.util.RepoViewBackend;
import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;
import de.uni_kassel.vs.cn.planDesigner.common.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.controller.ErrorWindowController;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.AbstractPlanElementContainer;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.StateContainer;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.TransitionContainer;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Tab;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import org.eclipse.emf.ecore.EObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

/**
 * Created by marci on 18.11.16.
 */
public abstract class AbstractEditorTab<T extends PlanElement> extends Tab {

    private Observer observer;
    private Pair<T, Path> editablePathPair;
    private CommandStack commandStack;
    protected SimpleObjectProperty<List<Pair<PlanElement, AbstractPlanElementContainer>>> selectedPlanElement;

    public AbstractEditorTab() {
        selectedPlanElement = new SimpleObjectProperty<>(new ArrayList<>());
        selectedPlanElement.get().add(new Pair<>(editablePathPair.getKey(), null));
    }

    public AbstractEditorTab(Pair<T, Path> editablePathPair, CommandStack commandStack) {
        super(editablePathPair.getValue().getFileName().toString());
        RepoViewBackend.getInstance().findListByType(editablePathPair).addListener(new ListChangeListener<Pair<T, Path>>() {
            @Override
            public void onChanged(Change<? extends Pair<T, Path>> c) {
                c.next();
                if (c.getAddedSize() > 0) {
                    for (Pair<T, Path> pair : c.getAddedSubList()) {
                        if (pair.getKey().equals(editablePathPair.getKey())) {
                            setEditablePathPair(pair);
                            setText(editablePathPair.getValue().getFileName().toString());
                            break;
                        }
                    }

                }
            }
        });

        this.editablePathPair = editablePathPair;
        initSelectedPlanElement(editablePathPair);


        this.commandStack = commandStack;
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
        setClosable(true);
        setOnCloseRequest(e -> {
            commandStack.deleteObserver(observer);
            if (editablePathPair.getKey() instanceof TaskRepository == false) {
                try {
                    File file = RepoViewBackend.getInstance().getPathForAbstractPlan((AbstractPlan) getEditable()).toFile();
                    EObject key = EMFModelUtils.reloadAlicaFileFromDisk(file);
                    if (editablePathPair.getKey() instanceof Plan) {
                        Pair<Plan, Path> planPathPair = RepoViewBackend.getInstance().getPlans()
                                .stream()
                                .filter(pair -> pair.getKey().getId() == editablePathPair.getKey().getId())
                                .findFirst().orElse(null);
                        Path value = planPathPair.getValue();
                        RepoViewBackend.getInstance().getPlans().remove(planPathPair);
                        RepoViewBackend.getInstance().getPlans().add(new Pair<>((Plan) key, value));
                        File pmlexFile = new File(file.getAbsolutePath().replace(".pml", ".pmlex"));
                        ((PlanTab)this).setPmlUiExtensionMap(EMFModelUtils.reloadAlicaFileFromDisk(pmlexFile));
                    }
                    if (editablePathPair.getKey() instanceof Behaviour) {
                        Pair<Behaviour, Path> planPathPair = RepoViewBackend.getInstance().getBehaviours()
                                .stream()
                                .filter(pair -> pair.getKey().getId() == editablePathPair.getKey().getId())
                                .findFirst().orElse(null);
                        Path value = planPathPair.getValue();
                        RepoViewBackend.getInstance().getBehaviours().remove(planPathPair);
                        RepoViewBackend.getInstance().getBehaviours().add(new Pair<>((Behaviour) key, value));
                    }
                    if (editablePathPair.getKey() instanceof PlanType) {
                        Pair<PlanType, Path> planPathPair = RepoViewBackend.getInstance().getPlanTypes()
                                .stream()
                                .filter(pair -> pair.getKey().getId() == editablePathPair.getKey().getId())
                                .findFirst().orElse(null);
                        Path value = planPathPair.getValue();
                        RepoViewBackend.getInstance().getPlanTypes().remove(planPathPair);
                        RepoViewBackend.getInstance().getPlanTypes().add(new Pair<>((PlanType) key, value));
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        });
    }

    /**
     * initialization for the selected element property, which indicates what elements are selected.
     * specializations for specific selections of {@link AbstractPlanElementContainer}s can be found under
     * {@link AbstractPlanElementContainer#getMouseClickedEventHandler(PlanElement)}
     * @param editablePathPair
     */
    protected void initSelectedPlanElement(Pair<T, Path> editablePathPair) {
        selectedPlanElement = new SimpleObjectProperty<>(new ArrayList<>());
        selectedPlanElement.get().add(new Pair<>(editablePathPair.getKey(), null));
        selectedPlanElement.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                DropShadow value = new DropShadow(StateContainer.STATE_RADIUS, new Color(0,0.4,0.9,0.9));
                value.setBlurType(BlurType.ONE_PASS_BOX);
                value.setSpread(0.45);
                newValue.forEach(selectedPlanElementPair -> {
                    AbstractPlanElementContainer planElementContainer = selectedPlanElementPair.getValue();
                    if (planElementContainer != null) {
                        planElementContainer.setEffect(value);
                    }
                });
                if (newValue.size() == 1 && newValue.get(0).getValue() instanceof TransitionContainer) {
                    ((TransitionContainer)newValue.get(0).getValue()).setPotentialDraggableNodesVisible(true);
                }
            }

            if ((oldValue != null)) {
                    oldValue.forEach(selectedPlanElementPair -> {
                        AbstractPlanElementContainer planElementContainer = selectedPlanElementPair.getValue();
                        if (planElementContainer != null) {
                            // this is weird! If I use planElementContainer.setEffectToStandard() nothing happens..
                            planElementContainer.setEffect(null);
                        }
                    });

                if (oldValue.size() == 1 && oldValue.get(0).getValue() instanceof TransitionContainer) {
                    ((TransitionContainer)oldValue.get(0).getValue()).setPotentialDraggableNodesVisible(false);
                }
            }
        });
    }

    public Path getFilePath() {
        return editablePathPair.getValue();
    }

    public void save() {
        try {
            setText(getText().replace("*",""));
            EMFModelUtils.saveAlicaFile(getEditable());
            getCommandStack().setSavedForAbstractPlan(getEditable());
        } catch (IOException e) {
            ErrorWindowController.createErrorWindow(I18NRepo.getString("label.error.save"), e);
        }
    }

    public T getEditable() {
        return editablePathPair.getKey();
    }

    public SimpleObjectProperty<List<Pair<PlanElement, AbstractPlanElementContainer>>> getSelectedPlanElement() {
        return selectedPlanElement;
    }

    public CommandStack getCommandStack() {
        return commandStack;
    }

    private void setEditablePathPair(Pair<T, Path> editablePathPair) {
        this.editablePathPair = editablePathPair;
    }

    public Pair<T, Path> getEditablePathPair() {
        return editablePathPair;
    }
}
