package de.uni_kassel.vs.cn.planDesigner.command.change;

import de.uni_kassel.vs.cn.generator.AlicaResourceSet;
import de.uni_kassel.vs.cn.planDesigner.alica.*;
import de.uni_kassel.vs.cn.generator.AlicaModelUtils;
import de.uni_kassel.vs.cn.planDesigner.view.repo.RepositoryViewModel;
import de.uni_kassel.vs.cn.generator.EMFModelUtils;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.controller.ErrorWindowController;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtensionMap;
import de.uni_kassel.vs.cn.planDesigner.view.repo.RepositoryTab;
import de.uni_kassel.vs.cn.planDesigner.view.repo.RepositoryTabPane;
import javafx.util.Pair;
import org.apache.commons.beanutils.BeanUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Created by marci on 17.03.17.
 */
public class ChangeAttributeValue<T> extends AbstractCommand<PlanElement> {

    private String attribute;

    private T newValue;

    private T oldValue;

    public ChangeAttributeValue(PlanElement element, String attribute, T newValue, PlanElement affectedPlan) {
        super(element, affectedPlan);
        this.attribute = attribute;
        this.newValue = newValue;
    }

    @Override
    public void doCommand() {
        try {
            RepositoryViewModel repositoryViewModel = RepositoryViewModel.getInstance();
            oldValue = (T) BeanUtils.getProperty(getElementToEdit(), attribute);
            BeanUtils.setProperty(getElementToEdit(), attribute, newValue);
            if (attribute.equals("masterPlan")) {
                reinitializeRepoTabs();
            }
            if (attribute.equals("name")) {
                if (AlicaModelUtils.containsIllegalCharacter(newValue.toString())) {
                    throw new RuntimeException("Illegal name for element");
                }
                Path path = null;
                if(getElementToEdit() instanceof Plan) {
                    Pair<Plan, Path> planPathPair = repositoryViewModel.getPlans()
                            .stream()
                            .filter(e -> e.getKey().equals(getElementToEdit()))
                            .findFirst()
                            .get();
                    path = planPathPair.getValue();
                    repositoryViewModel.getPlans().remove(planPathPair);
                    repositoryViewModel.getPlans().add(new Pair<>((Plan) getElementToEdit(), getNewFilePath(path).toPath()));
                    getElementToEdit().eResource().setURI(URI.createURI(getElementToEdit().eResource().getURI()
                            .toString().replace(path.getFileName().toString(),getNewFilePath(path).getName())));
                    final Path finalPath = path;

                    Resource resourcePMLEX = AlicaResourceSet.getInstance()
                            .getResources()
                            .stream()
                            .filter(e -> e.getURI().toFileString().endsWith(finalPath.getFileName().toString() + "ex"))
                            .findFirst().get();

                    resourcePMLEX.setURI(URI.createURI(resourcePMLEX.getURI()
                            .toString().replace(path.getFileName().toString()+ "ex",getNewFilePath(path).getName() + "ex")));
                    new File(finalPath.toString() + "ex").renameTo(new File(finalPath.toString().replace(finalPath.getFileName()+ "",getNewFilePath(path).getName()+ "ex")));
                }

                if (getElementToEdit() instanceof PlanType) {
                    Pair<PlanType, Path> planTypePathPair = repositoryViewModel.getPlanTypes()
                            .stream()
                            .filter(e -> e.getKey().equals(getElementToEdit()))
                            .findFirst()
                            .get();
                    path = planTypePathPair.getValue();
                    repositoryViewModel.getPlanTypes().remove(planTypePathPair);
                    repositoryViewModel.getPlanTypes().add(new Pair<>((PlanType) getElementToEdit(), getNewFilePath(path).toPath()));
                }

                if (getElementToEdit() instanceof Behaviour) {
                    Pair<Behaviour, Path> behaviourPathPair = RepositoryViewModel.getInstance().getBehaviours()
                            .stream()
                            .filter(e -> e.getKey().equals(getElementToEdit()))
                            .findFirst()
                            .get();
                    path = behaviourPathPair.getValue();
                    repositoryViewModel.getBehaviours().remove(behaviourPathPair);
                    repositoryViewModel.getBehaviours().add(new Pair<>((Behaviour) getElementToEdit(), getNewFilePath(path).toPath()));
                }

                if (getElementToEdit() instanceof Task) {
                    RepositoryViewModel.getInstance()
                            .getTaskRepository()
                            .get(0).getKey().getTasks().remove(getElementToEdit());
                    repositoryViewModel.getTaskRepository()
                            .get(0).getKey().getTasks().add((Task) getElementToEdit());
                }

                if (path != null) {
                    getElementToEdit().eResource().setURI(URI.createURI(getElementToEdit().eResource().getURI()
                            .toString().replace(path.getFileName().toString(),getNewFilePath(path).getName())));
                    File newFilePath = getNewFilePath(path);
                    path.toFile().renameTo(newFilePath);

                    AlicaResourceSet.getInstance()
                            .getResources()
                            .forEach(e -> {
                                EObject content = e
                                        .getContents()
                                        .get(0);
                                if (content instanceof Plan) {
                                    if (((Plan)content)
                                            .getStates()
                                            .stream()
                                            .anyMatch(f -> f.getPlans().contains(getElementToEdit()))) {
                                        try {
                                            EMFModelUtils.saveAlicaFile(content);
                                        } catch (IOException e1) {
                                            throw new RuntimeException(e1);
                                        }
                                    }
                                }

                                if (content instanceof PlanType) {
                                    if (((PlanType)content)
                                            .getPlans()
                                            .stream()
                                            .filter(f -> f.getPlan().equals(getElementToEdit()))
                                            .count() > 0) {
                                        try {
                                            EMFModelUtils.saveAlicaFile(content);
                                        } catch (IOException e1) {
                                            throw new RuntimeException(e1);
                                        }
                                    }
                                }

                                if (content instanceof PmlUiExtensionMap) {
                                    if (((PmlUiExtensionMap) content)
                                            .getExtension()
                                            .entrySet()
                                            .stream()
                                            .filter(f -> f.getKey().equals(getElementToEdit()))
                                            .count() > 0) {
                                        try {
                                            EMFModelUtils.saveAlicaFile(content);
                                        } catch (IOException e1) {
                                            throw new RuntimeException(e1);
                                        }
                                    }
                                }
                            });
                } else {
                    if (getElementToEdit() instanceof Task) {
                        EMFModelUtils.saveAlicaFile(RepositoryViewModel.getInstance().getTaskRepository().get(0).getKey());
                        reinitializeRepoTabs();
                    }
                }

            }

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void reinitializeRepoTabs() {
        RepositoryTabPane repositoryTabPane = MainController.getInstance().getRepositoryTabPane();
        String previousSelectedTabTypeName =  ((RepositoryTab<?>)repositoryTabPane.getSelectionModel()
                .getSelectedItem()).getTypeName();
        repositoryTabPane.init();
        Optional<RepositoryTab> repositoryTab = repositoryTabPane.getTabs().stream()
                .map(t -> (RepositoryTab) t)
                .filter(t -> previousSelectedTabTypeName.equals(t.getTypeName()))
                .findFirst();
        if (repositoryTab.isPresent()) {
            repositoryTabPane.getSelectionModel().select(repositoryTab.get());
        } else {
            // if this happens something went wrong.
            ErrorWindowController
                    .createErrorWindow(I18NRepo.getInstance().getString("label.error.repoview"), null);
        }
    }

    private File getNewFilePath(Path path) {
        return new File(path.toFile().getAbsolutePath().replace("/" + (path.getFileName()
                                .toString().substring(0, path.getFileName().toString().lastIndexOf('.'))) + ".",
                "/" + ((String) newValue) + "."));
    }

    @Override
    public void undoCommand() {
        try {
            BeanUtils.setProperty(getElementToEdit(), attribute, oldValue);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getCommandString() {
        return null;
    }
}
