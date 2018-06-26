package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.*;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.PmlUiExtension;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.PmlUiExtensionMap;

import java.nio.file.Path;
import java.util.*;

public class DeleteAbstractPlan extends AbstractCommand {

    private String type;
    private Map<Plan, List<State>> referencedStateListForBackup;
    private Map<PmlUiExtensionMap, List<PmlUiExtension>> referencesInStyleFiles;
    private Map<PlanType, Plan> referencedAnnotatedPlansForBackup;
    private Map<Parametrisation, Variable> referencedParametrisationForBackup;
    private Path path;
    private AbstractPlan abstractPlan;

    // not always used
    private PmlUiExtensionMap pmlUiExtensionMap;

    public DeleteAbstractPlan(ModelManager manager, AbstractPlan abstractPlan) {
        super(manager);
        if (abstractPlan instanceof Behaviour) {
            type = "behaviour";
        }

        if (abstractPlan instanceof PlanType) {
            type = "plantype";
        }

        if (abstractPlan instanceof Plan) {
            type = "plan";
        }
        referencedAnnotatedPlansForBackup = new HashMap<>();
        referencedStateListForBackup = new HashMap<>();
        referencesInStyleFiles = new HashMap<>();
        referencedParametrisationForBackup = new HashMap<>();
        this.abstractPlan = abstractPlan;
    }

    @Override
    public void doCommand() {

        // Remove from AlicaResourceSet
        modelManager.getPlanElements().forEach(element -> {
            boolean[] saveForDeletionConfirmation = {false};
            if (element instanceof Plan) {
                ArrayList<State> states = new ArrayList<>();
                Plan plan = (Plan) element;
                referencedStateListForBackup.put(plan, states);
                plan.getStates().forEach(state -> {
                    if (state.getPlans().remove(abstractPlan)) {
                        states.add(state);
                        saveForDeletionConfirmation[0] = true;
                    }

                    state.getParametrisations().forEach(parametrisation -> {
                        if(parametrisation.getSubPlan() != null && parametrisation.getSubPlan().equals(abstractPlan)) {
                            referencedParametrisationForBackup.put(parametrisation, parametrisation.getSubVariable());
                            parametrisation.setSubPlan(null);
                        }

                        if(abstractPlan instanceof Behaviour) {
                            Behaviour behaviour = (Behaviour) abstractPlan;
                            behaviour.getVariables().forEach(variable -> {
                                if(parametrisation.getSubVariable() != null && parametrisation.getSubVariable().equals(variable)) {
                                    parametrisation.setSubVariable(null);
                                }
                            });
                        }

                        if(abstractPlan instanceof Plan) {
                            Plan innerPlan = (Plan) abstractPlan;
                            innerPlan.getVariables().forEach(variable -> {
                                if(parametrisation.getSubVariable() != null && parametrisation.getSubVariable().equals(variable)) {
                                    parametrisation.setSubVariable(null);
                                }
                            });
                        }
                    });
                });
                if (states.isEmpty()) {
                    referencedStateListForBackup.remove(element);
                }
            }

            if (element instanceof PlanType) {
                PlanType planType = (PlanType) element;
                Optional<Plan> annotatedPlan = planType.getPlans()
                        .stream()
                        .filter(ap -> ap.equals(abstractPlan))
                        .findFirst();

                if (annotatedPlan.isPresent()) {
                    referencedAnnotatedPlansForBackup.put(planType, annotatedPlan.get());
                    planType.getPlans().remove(annotatedPlan);
                    saveForDeletionConfirmation[0] = true;
                }
            }

            //TODO handle PmlUiExtentions in modelmanager?
//            if (element instanceof PmlUiExtensionMap) {
//                PmlUiExtensionMap pmlUiExtensionMap = (PmlUiExtensionMap) element;
//                List<PmlUiExtension> pmlUiExtensions = pmlUiExtensionMap
//                        .getExtension()
//                        .entrySet()
//                        .stream()
//                        .filter(f -> f.getKey().equals(plan))
//                        .map(f -> f.getValue())
//                        .collect(Collectors.toList());
//                if (pmlUiExtensions.size() > 0) {
//                    referencesInStyleFiles.put(pmlUiExtensionMap, pmlUiExtensions);
//                    // The pmluiextensionmap is more of a list of pairs,
//                    // which means removing a key removes only one entry with the named key.
//                    // The EMF documentation hints at this with the description of removeKey() which says it removes an entry.
//                    while (pmlUiExtensionMap.getExtension().containsKey(plan)) {
//                        pmlUiExtensionMap.getExtension().remove(plan);
//                    }
//                    saveForDeletionConfirmation[0] = true;
//                }
//            }

            if (saveForDeletionConfirmation[0]) {
                // TODO introduce save command
//                try {
//                    EMFModelUtils.saveAlicaFile(eObject);
//                } catch (IOException e1) {
//                    throw new RuntimeException(e1);
//                }
            }
        });

        modelManager.removeAbstractPlan(abstractPlan);

        //TODO commads for file deletion
//        GeneratedSourcesManager generatedSourcesManager = GeneratedSourcesManager.get();
//        switch (modelElementType) {
//            case "plan":
//                Pair<Plan, Path> planPathPair = RepositoryViewModel.getInstance().getPlans()
//                        .stream()
//                        .filter(e -> e.getKey().equals(elementToEdit))
//                        .findFirst()
//                        .get();
//                path = planPathPair.getValue();
//                RepositoryViewModel.getInstance().getPlans().remove(planPathPair);
//                File pmlEx = new File(path.toFile().toString() + "ex");
//                List<Resource> pmlUiExt = AlicaResourceSet.getInstance()
//                        .getResources()
//                        .stream()
//                        .filter(e -> new File(path.toFile().toString() + "ex").toString()
//                                .endsWith(e.getURI().toFileString()))
//                        .collect(Collectors.toList());
//                pmlUiExtensionMap = (PmlUiExtensionMap) pmlUiExt.get(0).getContents().get(0);
//                AlicaResourceSet.getInstance().getResources().remove(pmlUiExt.get(0));
//                try {
//                    Files.delete(planPathPair.getValue());
//                    Files.delete(pmlEx.toPath());
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//
//                generatedSourcesManager.getAllGeneratedFilesForAbstractPlan(planPathPair.getKey())
//                        .forEach(File::delete);
//                break;
//            case "plantype":
//                Pair<PlanType, Path> planTypePathPair = modelManager.getPlanTypes()
//                        .stream()
//                        .filter(e -> e.getKey().equals(elementToEdit))
//                        .findFirst()
//                        .get();
//                path = planTypePathPair.getValue();
//                RepositoryViewModel.getInstance().getPlans().remove(planTypePathPair);
//                try {
//                    Files.delete(planTypePathPair.getValue());
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                break;
//            case "behaviour":
//                Pair<Behaviour, Path> behaviourPathPair = modelManager.getBehaviours()
//                        .stream()
//                        .filter(e -> e.getKey().equals(elementToEdit))
//                        .findFirst()
//                        .get();
//                path = behaviourPathPair.getValue();
//                RepositoryViewModel.getInstance().getBehaviours().remove(behaviourPathPair);
//                try {
//                    Files.delete(behaviourPathPair.getValue());
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                generatedSourcesManager.getAllGeneratedFilesForAbstractPlan(behaviourPathPair.getKey())
//                        .forEach(File::delete);
//                break;
//        }
    }

    @Override
    public void undoCommand() {
//        try {
//            EMFModelUtils.createAlicaFile(elementToEdit, false, path.toFile());
//            if (pmlUiExtensionMap != null) {
//                EMFModelUtils.createAlicaFile(pmlUiExtensionMap, false, new File(path.toFile().toString() + "ex"));
//            }
            referencedStateListForBackup
                    .entrySet()
                    .forEach(e -> {
                        Plan key = e.getKey();
                        key
                                .getStates()
                                .stream()
                                .filter(f -> e.getValue().contains(f))
                                .forEach(f -> f.getPlans().add(abstractPlan));
                        // TODO introduce save command
//                        try {
//                            EMFModelUtils.saveAlicaFile(key);
//                        } catch (IOException e1) {
//                            throw new RuntimeException(e1);
//                        }
                    });
            referencedAnnotatedPlansForBackup
                    .entrySet()
                    .forEach(e -> {
                        PlanType key = e.getKey();
                        key.getPlans().add(e.getValue());
                        // TODO introduce save command
//                        try {
//                            EMFModelUtils.saveAlicaFile(key);
//                        } catch (IOException e1) {
//                            throw new RuntimeException(e1);
//                        }
                    });

            referencesInStyleFiles
                    .entrySet()
                    .forEach(e -> {
                        PmlUiExtensionMap key = e.getKey();
                        e.getValue().forEach(f -> key.getExtension().put(abstractPlan, f));
                        // TODO introduce save command
//                        try {
//                            EMFModelUtils.saveAlicaFile(key);
//                        } catch (IOException e1) {
//                            throw new RuntimeException(e1);
//                        }
                    });

            referencedParametrisationForBackup
                    .entrySet()
                    .forEach(entry -> {
                        Parametrisation key = entry.getKey();
                        key.setSubVariable(entry.getValue());
                        key.setSubPlan(entry.getKey().getSubPlan());
                        key.setVariable(entry.getKey().getVariable());
                        // TODO introduce save command
//                        try {
//                            EMFModelUtils.saveAlicaFile(key);
//                        } catch (IOException e1) {
//                            throw new RuntimeException(e1);
//                        }
                    });
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
}
