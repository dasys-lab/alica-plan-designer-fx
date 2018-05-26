package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.generator.AlicaResourceSet;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.EAbstractPlanType;
import de.uni_kassel.vs.cn.generator.GeneratedSourcesManager;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.alica.*;
import de.uni_kassel.vs.cn.generator.RepoViewBackend;
import de.uni_kassel.vs.cn.generator.EMFModelUtils;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtensionMap;
import javafx.util.Pair;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by marci on 05.04.17.
 */
public class DeleteAbstractPlan extends AbstractCommand<AbstractPlan> {

    private EAbstractPlanType planType;
    private Map<Plan, List<State>> referencedStateListForBackup;
    private Map<PmlUiExtensionMap, List<PmlUiExtension>> referencesInStyleFiles;
    private Map<PlanType, AnnotatedPlan> referencedAnnotatedPlansForBackup;
    private Map<Parametrisation, Parametrisation> referencedParametrisationForBackup;
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
        referencedParametrisationForBackup = new HashMap<>();
    }

    @Override
    public void doCommand() {

        // Remove from AlicaResourceSet
        AlicaResourceSet.getInstance().getResources().forEach(e -> {
            boolean[] saveForDeletionConfirmation = {false};
            EObject eObject = e.getContents().get(0);
            if (eObject instanceof Plan) {
                ArrayList<State> states = new ArrayList<>();
                Plan plan = (Plan) eObject;
                referencedStateListForBackup.put(plan, states);
                plan.getStates().forEach(state -> {
                    if (state.getPlans().remove(getElementToEdit())) {
                        states.add(state);
                        saveForDeletionConfirmation[0] = true;
                    }

                    state.getParametrisation().forEach(parametrisation -> {
                        Parametrisation params = EcoreUtil.copy(parametrisation);
                        referencedParametrisationForBackup.put(parametrisation, params);
                        boolean[] parametrisationChanged = {false};
                        if(parametrisation.getSubplan() != null && parametrisation.getSubplan().equals(getElementToEdit())) {
                            parametrisation.setSubplan(null);
                            parametrisationChanged[0] = true;
                        }

                        if(getElementToEdit() instanceof Behaviour) {
                            Behaviour behaviour = (Behaviour) getElementToEdit();
                            behaviour.getVars().forEach(variable -> {
                                if(parametrisation.getSubvar() != null && parametrisation.getSubvar().equals(variable)) {
                                    parametrisation.setSubvar(null);
                                    parametrisationChanged[0] = true;
                                }
                            });
                        }

                        if(getElementToEdit() instanceof Plan) {
                            Plan innerPlan = (Plan) getElementToEdit();
                            innerPlan.getVars().forEach(variable -> {
                                if(parametrisation.getSubvar() != null && parametrisation.getSubvar().equals(variable)) {
                                    parametrisation.setSubvar(null);
                                    parametrisationChanged[0] = true;
                                }
                            });
                        }

                        if(!parametrisationChanged[0]) {
                            referencedParametrisationForBackup.remove(state);
                        }
                    });
                });
                if (states.isEmpty()) {
                    referencedStateListForBackup.remove(eObject);
                }
            }

            if (eObject instanceof PlanType) {
                PlanType planType = (PlanType) eObject;
                Optional<AnnotatedPlan> annotatedPlan = planType.getPlans()
                        .stream()
                        .filter(ap -> ap.getPlan().equals(getElementToEdit()))
                        .findFirst();

                if (annotatedPlan.isPresent()) {
                    referencedAnnotatedPlansForBackup.put(planType, annotatedPlan.get());
                    planType.getPlans().remove(annotatedPlan);
                    saveForDeletionConfirmation[0] = true;
                }
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
                    // The pmluiextensionmap is more of a list of pairs,
                    // which means removing a key removes only one entry with the named key.
                    // The EMF documentation hints at this with the description of removeKey() which says it removes an entry.
                    while (pmlUiExtensionMap.getExtension().containsKey(getElementToEdit())) {
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
        AlicaResourceSet.getInstance().getResources().remove(getElementToEdit().eResource());

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
                List<Resource> pmlUiExt = AlicaResourceSet.getInstance()
                        .getResources()
                        .stream()
                        .filter(e -> new File(path.toFile().toString() + "ex").toString()
                                .endsWith(e.getURI().toFileString()))
                        .collect(Collectors.toList());
                pmlUiExtensionMap = (PmlUiExtensionMap) pmlUiExt.get(0).getContents().get(0);
                AlicaResourceSet.getInstance().getResources().remove(pmlUiExt.get(0));
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
                        key.getPlans().add(e.getValue());
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

            referencedParametrisationForBackup
                    .entrySet()
                    .forEach(entry -> {
                        Parametrisation key = entry.getKey();
                        key.setSubvar(entry.getValue().getSubvar());
                        key.setSubplan(entry.getValue().getSubplan());
                        key.setVar(entry.getValue().getVar());
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
