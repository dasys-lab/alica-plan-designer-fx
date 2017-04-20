package de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.delete;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.EAbstractPlanType;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.alica.*;
import de.uni_kassel.vs.cn.planDesigner.alica.util.AllAlicaFiles;
import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtensionMap;
import javafx.util.Pair;
import org.eclipse.emf.ecore.EObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by marci on 05.04.17.
 */
public class DeleteAbstractPlan extends AbstractCommand<AbstractPlan> {

    private EAbstractPlanType planType;
    private Map<Plan, List<State>> referencedStateListForBackup;
    private Map<PmlUiExtensionMap, List<PmlUiExtension>> referencesInStyleFiles;
    private Map<PlanType, List<AnnotatedPlan>> referencedAnnotatedPlansForBackup;
    private Path path;

    public DeleteAbstractPlan(AbstractPlan element) {
        super(element);
        if (element instanceof Behaviour) {
            planType = EAbstractPlanType.BEHAVIOUR;
        }

        if (element instanceof PlanType) {
            planType = EAbstractPlanType.PLANTYPE;
        }

        if (element instanceof Plan) {
            planType = EAbstractPlanType.PLAN;
        }
        referencedAnnotatedPlansForBackup = new HashMap<>();
        referencedStateListForBackup = new HashMap<>();
        referencesInStyleFiles = new HashMap<>();
    }

    @Override
    public void doCommand() {
        EMFModelUtils
                .getAlicaResourceSet().getResources().forEach(e -> {
                    boolean[] saveForDeletionConfirmation = {false};
            EObject eObject = e.getContents().get(0);
            if (eObject instanceof Plan) {
                ArrayList<State> states = new ArrayList<>();
                referencedStateListForBackup.put((Plan) eObject, states);
                ((Plan) eObject).getStates().forEach(f -> {
                    if (f.getPlans().contains(getElementToEdit())) {
                        states.add(f);
                        saveForDeletionConfirmation[0] = true;
                    }
                    f.getPlans().remove(getElementToEdit());
                });
                if (states.isEmpty()) {
                    referencedStateListForBackup.remove(eObject);
                }
            }

            if (eObject instanceof PlanType) {
                List<AnnotatedPlan> plansToRemove = new ArrayList<>();
                PlanType planType = (PlanType) eObject;
                planType.getPlans().forEach(f -> {
                    if (f.getPlan().equals(getElementToEdit())) {
                        plansToRemove.add(f);
                        saveForDeletionConfirmation[0] = true;
                    }
                });
                referencedAnnotatedPlansForBackup.put(planType, plansToRemove);
                planType.getPlans().removeAll(plansToRemove);
            }

            if (eObject instanceof PmlUiExtensionMap) {
                PmlUiExtensionMap pmlUiExtensionMap = (PmlUiExtensionMap) eObject;
                List<PmlUiExtension> pmlUiExtensions = pmlUiExtensionMap
                        .getExtension()
                        .entrySet()
                        .stream()
                        .filter(f -> f.getKey().equals(getElementToEdit()))
                        .map(f -> f.getValue())
                        .collect(Collectors.toList());
                if (pmlUiExtensions.size() > 0) {
                    referencesInStyleFiles.put(pmlUiExtensionMap, pmlUiExtensions);
                    pmlUiExtensionMap.getExtension().removeKey(getElementToEdit());
                    saveForDeletionConfirmation[0] = true;
                }
            }

            if (saveForDeletionConfirmation[0]) {
                try {
                    EMFModelUtils.saveAlicaFile(eObject);
                } catch (IOException e1) {
                    throw new RuntimeException(e1);
                }
            }
        });
        EMFModelUtils.getAlicaResourceSet().getResources().remove(getElementToEdit().eResource());

        switch (planType) {
            case PLAN:
                Pair<Plan, Path> planPathPair = AllAlicaFiles.getInstance().getPlans()
                        .stream()
                        .filter(e -> e.getKey().equals(getElementToEdit()))
                        .findFirst()
                        .get();
                path = planPathPair.getValue();
                try {
                    Files.delete(planPathPair.getValue());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case PLANTYPE:
                Pair<PlanType, Path> planTypePathPair = AllAlicaFiles.getInstance().getPlanTypes()
                        .stream()
                        .filter(e -> e.getKey().equals(getElementToEdit()))
                        .findFirst()
                        .get();
                path = planTypePathPair.getValue();
                try {
                    Files.delete(planTypePathPair.getValue());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case BEHAVIOUR:
                Pair<Behaviour, Path> behaviourPathPair = AllAlicaFiles.getInstance().getBehaviours()
                        .stream()
                        .filter(e -> e.getKey().equals(getElementToEdit()))
                        .findFirst()
                        .get();
                path = behaviourPathPair.getValue();
                try {
                    Files.delete(behaviourPathPair.getValue());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }

    @Override
    public void undoCommand() {
        try {
            EMFModelUtils.createAlicaFile(getElementToEdit(), path.toFile());
            referencedStateListForBackup
                    .entrySet()
                    .forEach(e -> {
                        Plan key = e.getKey();
                        key
                            .getStates()
                            .stream()
                            .filter(f -> e.getValue().contains(f))
                            .forEach(f -> f.getPlans().add(getElementToEdit()));
                        try {
                            EMFModelUtils.saveAlicaFile(key);
                        } catch (IOException e1) {
                            throw new RuntimeException(e1);
                        }
                    });
            referencedAnnotatedPlansForBackup
                    .entrySet()
                    .forEach(e -> {
                        PlanType key = e.getKey();
                        key.getPlans().addAll(e.getValue());
                        try {
                            EMFModelUtils.saveAlicaFile(key);
                        } catch (IOException e1) {
                            throw new RuntimeException(e1);
                        }
                    });

            referencesInStyleFiles
                    .entrySet()
                    .forEach(e -> {
                        PmlUiExtensionMap key = e.getKey();
                        e.getValue().forEach(f -> key.getExtension().put(getElementToEdit(), f));
                        try {
                            EMFModelUtils.saveAlicaFile(key);
                        } catch (IOException e1) {
                            throw new RuntimeException(e1);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getCommandString() {
        return "Delete " + getElementToEdit().getName();
    }
}
