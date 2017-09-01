package de.uni_kassel.vs.cn.planDesigner.ui.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.alica.*;
import de.uni_kassel.vs.cn.planDesigner.alica.util.AllAlicaFiles;
import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;
import de.uni_kassel.vs.cn.planDesigner.common.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.controller.ErrorWindowController;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.AbstractPlanElementContainer;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.StateContainer;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.TransitionContainer;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Tab;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import org.eclipse.emf.ecore.EObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Observer;

/**
 * Created by marci on 18.11.16.
 */
public abstract class AbstractEditorTab<T extends PlanElement> extends Tab {

    private Observer observer;
    private Pair<T, Path> editablePathPair;
    private CommandStack commandStack;
    protected SimpleObjectProperty<Pair<PlanElement, AbstractPlanElementContainer>> selectedPlanElement;

    public AbstractEditorTab() {
        selectedPlanElement = new SimpleObjectProperty<>(new Pair<>(editablePathPair.getKey(), null));
    }

    public AbstractEditorTab(Pair<T, Path> editablePathPair, CommandStack commandStack) {
        super(editablePathPair.getValue().getFileName().toString());
        AllAlicaFiles.getInstance().findListByType(editablePathPair).addListener(new ListChangeListener<Pair<T, Path>>() {
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
        selectedPlanElement = new SimpleObjectProperty<>(new Pair<>(editablePathPair.getKey(), null));
        this.commandStack = commandStack;
        selectedPlanElement.addListener(new ChangeListener<Pair<PlanElement, AbstractPlanElementContainer>>() {
            private Effect previousEffect;
            @Override
            public void changed(ObservableValue<? extends Pair<PlanElement, AbstractPlanElementContainer>> observable, Pair<PlanElement, AbstractPlanElementContainer> oldValue, Pair<PlanElement, AbstractPlanElementContainer> newValue) {
                if (oldValue != null && oldValue.getValue() != null) {
                    oldValue.getValue().setEffect(previousEffect);
                    if (oldValue.getValue() instanceof TransitionContainer) {
                        ((TransitionContainer)oldValue.getValue()).setPotentialDraggableNodesVisible(false);
                    }
                }
                if(newValue != null && newValue.getValue() != null) {
                    previousEffect = newValue.getValue().getEffect();
                    DropShadow value = new DropShadow(StateContainer.STATE_RADIUS * 2, Color.GRAY);
                    value.setSpread(0.9);
                    newValue.getValue().setEffect(value);
                    if (newValue.getValue() instanceof TransitionContainer) {
                        ((TransitionContainer)newValue.getValue()).setPotentialDraggableNodesVisible(true);
                    }
                }
            }
        });
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
                    File file = AllAlicaFiles.getInstance().getPathForAbstractPlan((AbstractPlan) getEditable()).toFile();
                    EObject key = EMFModelUtils.reloadAlicaFileFromDisk(file);
                    if (editablePathPair.getKey() instanceof Plan) {
                        Pair<Plan, Path> planPathPair = AllAlicaFiles.getInstance().getPlans()
                                .stream()
                                .filter(pair -> pair.getKey().getId() == editablePathPair.getKey().getId())
                                .findFirst().orElse(null);
                        Path value = planPathPair.getValue();
                        AllAlicaFiles.getInstance().getPlans().remove(planPathPair);
                        AllAlicaFiles.getInstance().getPlans().add(new Pair<>((Plan) key, value));
                        File pmlexFile = new File(file.getAbsolutePath().replace(".pml", ".pmlex"));
                        ((PlanTab)this).setPmlUiExtensionMap(EMFModelUtils.reloadAlicaFileFromDisk(pmlexFile));
                    }
                    if (editablePathPair.getKey() instanceof Behaviour) {
                        Pair<Behaviour, Path> planPathPair = AllAlicaFiles.getInstance().getBehaviours()
                                .stream()
                                .filter(pair -> pair.getKey().getId() == editablePathPair.getKey().getId())
                                .findFirst().orElse(null);
                        Path value = planPathPair.getValue();
                        AllAlicaFiles.getInstance().getBehaviours().remove(planPathPair);
                        AllAlicaFiles.getInstance().getBehaviours().add(new Pair<>((Behaviour) key, value));
                    }

                    if (editablePathPair.getKey() instanceof PlanType) {
                        Pair<PlanType, Path> planPathPair = AllAlicaFiles.getInstance().getPlanTypes()
                                .stream()
                                .filter(pair -> pair.getKey().getId() == editablePathPair.getKey().getId())
                                .findFirst().orElse(null);
                        Path value = planPathPair.getValue();
                        AllAlicaFiles.getInstance().getPlanTypes().remove(planPathPair);
                        AllAlicaFiles.getInstance().getPlanTypes().add(new Pair<>((PlanType) key, value));
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
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

    public SimpleObjectProperty<Pair<PlanElement, AbstractPlanElementContainer>> getSelectedPlanElement() {
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
