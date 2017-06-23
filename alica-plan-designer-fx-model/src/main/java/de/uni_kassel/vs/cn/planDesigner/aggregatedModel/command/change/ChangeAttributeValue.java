package de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.change;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.alica.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanType;
import de.uni_kassel.vs.cn.planDesigner.alica.util.AllAlicaFiles;
import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtensionMap;
import javafx.util.Pair;
import org.apache.commons.beanutils.BeanUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;

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
            oldValue = (T) BeanUtils.getProperty(getElementToEdit(), attribute);
            if (attribute.equals("name")) {
                Path path = null;
                if(getElementToEdit() instanceof Plan) {
                    Pair<Plan, Path> planPathPair = AllAlicaFiles.getInstance().getPlans()
                            .stream()
                            .filter(e -> e.getKey().equals(getElementToEdit()))
                            .findFirst()
                            .get();
                    path = planPathPair.getValue();
                    AllAlicaFiles.getInstance().getPlans().remove(planPathPair);
                    AllAlicaFiles.getInstance().getPlans().add(new Pair<>((Plan) getElementToEdit(), getNewFilePath(path).toPath()));
                    getElementToEdit().eResource().setURI(URI.createURI(getElementToEdit().eResource().getURI()
                            .toString().replace(path.getFileName().toString(),getNewFilePath(path).getName())));
                    final Path finalPath = path;

                    Resource resourcePMLEX = EMFModelUtils
                            .getAlicaResourceSet()
                            .getResources()
                            .stream()
                            .filter(e -> e.getURI().toFileString().endsWith(finalPath.getFileName().toString() + "ex"))
                            .findFirst().get();

                    resourcePMLEX.setURI(URI.createURI(resourcePMLEX.getURI()
                            .toString().replace(path.getFileName().toString()+ "ex",getNewFilePath(path).getName() + "ex")));
                    new File(finalPath.toString() + "ex").renameTo(new File(finalPath.toString().replace(finalPath.getFileName()+ "",getNewFilePath(path).getName()+ "ex")));
                }

                if (getElementToEdit() instanceof PlanType) {
                    Pair<PlanType, Path> planTypePathPair = AllAlicaFiles.getInstance().getPlanTypes()
                            .stream()
                            .filter(e -> e.getKey().equals(getElementToEdit()))
                            .findFirst()
                            .get();
                    path = planTypePathPair.getValue();
                    AllAlicaFiles.getInstance().getPlanTypes().remove(planTypePathPair);
                    AllAlicaFiles.getInstance().getPlanTypes().add(new Pair<>((PlanType) getElementToEdit(), getNewFilePath(path).toPath()));
                }

                if (getElementToEdit() instanceof Behaviour) {
                    Pair<Behaviour, Path> behaviourPathPair = AllAlicaFiles.getInstance().getBehaviours()
                            .stream()
                            .filter(e -> e.getKey().equals(getElementToEdit()))
                            .findFirst()
                            .get();
                    path = behaviourPathPair.getValue();
                    AllAlicaFiles.getInstance().getBehaviours().remove(behaviourPathPair);
                    AllAlicaFiles.getInstance().getBehaviours().add(new Pair<>((Behaviour) getElementToEdit(), getNewFilePath(path).toPath()));
                }

                if (path != null) {
                    getElementToEdit().eResource().setURI(URI.createURI(getElementToEdit().eResource().getURI()
                            .toString().replace(path.getFileName().toString(),getNewFilePath(path).getName())));
                    File newFilePath = getNewFilePath(path);
                    path.toFile().renameTo(newFilePath);

                    EMFModelUtils
                            .getAlicaResourceSet()
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
                }

            }
            BeanUtils.setProperty(getElementToEdit(), attribute, newValue);

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
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
