package de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.delete;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.EAbstractPlanType;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.GeneratedSourcesManager;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.alica.*;
import de.uni_kassel.vs.cn.planDesigner.alica.util.RepoViewBackend;
import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtensionMap;
import javafx.util.Pair;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import java.io.File;
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

    // not always used
    private PmlUiExtensionMap pmlUiExtensionMap;

    public DeleteAbstractPlan(AbstractPlan element) {
        super(element, element);
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
                    // HACK the pmluiextensionmap is more of a list of pairs,
                    // which means removing a key removes only one entry with the named key.
                    // The EMF documentation hints at this with the description of removeKey() which says it removes an entry.
                    while(pmlUiExtensionMap.getExtension().containsKey(getElementToEdit())) {
                        pmlUiExtensionMap.getExtension().removeKey(getElementToEdit());
                    }
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

        GeneratedSourcesManager generatedSourcesManager = GeneratedSourcesManager.get();
        switch (planType) {
            case PLAN:
                Pair<Plan, Path> planPathPair = RepoViewBackend.getInstance().getPlans()
                        .stream()
                        .filter(e -> e.getKey().equals(getElementToEdit()))
                        .findFirst()
                        .get();
                path = planPathPair.getValue();
                RepoViewBackend.getInstance().getPlans().remove(planPathPair);
                File pmlEx = new File(path.toFile().toString() + "ex");
                List<Resource> pmlUiExt = EMFModelUtils.getAlicaResourceSet()
                        .getResources()
                        .stream()
                        .filter(e -> new File(path.toFile().toString() + "ex").toString()
                                .endsWith(e.getURI().toFileString()))
                        .collect(Collectors.toList());
                pmlUiExtensionMap = (PmlUiExtensionMap) pmlUiExt.get(0).getContents().get(0);
                EMFModelUtils.getAlicaResourceSet().getResources().remove(pmlUiExt.get(0));
                try {
                    Files.delete(planPathPair.getValue());
                    Files.delete(pmlEx.toPath());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                generatedSourcesManager.getAllGeneratedFilesForAbstractPlan(planPathPair.getKey())
                .forEach(File::delete);
                break;
            case PLANTYPE:
                Pair<PlanType, Path> planTypePathPair = RepoViewBackend.getInstance().getPlanTypes()
                        .stream()
                        .filter(e -> e.getKey().equals(getElementToEdit()))
                        .findFirst()
                        .get();
                path = planTypePathPair.getValue();
                RepoViewBackend.getInstance().getPlans().remove(planTypePathPair);
                try {
                    Files.delete(planTypePathPair.getValue());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case BEHAVIOUR:
                Pair<Behaviour, Path> behaviourPathPair = RepoViewBackend.getInstance().getBehaviours()
                        .stream()
                        .filter(e -> e.getKey().equals(getElementToEdit()))
                        .findFirst()
                        .get();
                path = behaviourPathPair.getValue();
                RepoViewBackend.getInstance().getBehaviours().remove(behaviourPathPair);
                try {
                    Files.delete(behaviourPathPair.getValue());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                generatedSourcesManager.getAllGeneratedFilesForAbstractPlan(behaviourPathPair.getKey())
                    .forEach(File::delete);
                break;
        }
    }

    @Override
    public void undoCommand() {
        try {
            EMFModelUtils.createAlicaFile(getElementToEdit(), false, path.toFile());
            if (pmlUiExtensionMap != null) {
                EMFModelUtils.createAlicaFile(pmlUiExtensionMap, false, new File(path.toFile().toString() + "ex"));
            }
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
