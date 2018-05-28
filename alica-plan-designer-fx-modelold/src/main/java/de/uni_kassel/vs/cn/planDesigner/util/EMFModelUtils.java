package de.uni_kassel.vs.cn.generator;

import de.uni_kassel.vs.cn.planDesigner.alica.*;
import de.uni_kassel.vs.cn.generator.configuration.Configuration;
import de.uni_kassel.vs.cn.generator.configuration.ConfigurationManager;
import de.uni_kassel.vs.cn.planDesigner.alica.impl.AlicaFactoryImpl;
import de.uni_kassel.vs.cn.planDesigner.alica.impl.AlicaPackageImpl;
import de.uni_kassel.vs.cn.planDesigner.alica.util.AlicaSerializationHelper;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUIExtensionModelFactory;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUIExtensionModelPackage;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtensionMap;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.impl.PmlUIExtensionModelPackageImpl;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.util.PmlUIExtensionModelResourceFactoryImpl;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This utility class serves EMF functionality to use and initialize.
 * It serves as a shorthand for longer initializations and function calls.
 */
public class EMFModelUtils {

    private static final Logger LOG = LogManager.getLogger(EMFModelUtils.class);
    private static AlicaResourceSet alicaResourceSet;

    /**
     * Initializes EMF context, adds filetypes which can be read via means of EMF
     */
    public static void initializeEMF() {
        // initialize the alica
        EClass alicaPackageEClass = AlicaPackage.eINSTANCE.eClass();
        EClass uiExtensionEClass = PmlUIExtensionModelPackage.eINSTANCE.eClass();
        Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        URIConverter extensibleURIConverter = ExtensibleURIConverterImpl.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        // Initialize the alica and the extensionUI package
        AlicaPackageImpl.init();
        PmlUIExtensionModelPackageImpl.init();
        m.put("pml", new XMIResourceFactoryImpl());
        m.put("pmlex", new PmlUIExtensionModelResourceFactoryImpl());
        m.put("beh", new XMIResourceFactoryImpl());
        m.put("pty", new XMIResourceFactoryImpl());
        m.put("tsk", new XMIResourceFactoryImpl());
        alicaResourceSet = AlicaResourceSet.getInstance();
        LOG.info("EMF Base Classes initialized");
    }

    /**
     * Loads given file from disk
     *
     * @param file the file to loadFromDisk must not be null
     * @return the returned {@link EObject}
     * @throws IOException if loading fails because of nonexistence or if problems happen while reading
     */
    public static <T extends EObject> T loadAlicaFileFromDisk(File file) throws IOException {
        Configuration configuration = ConfigurationManager.getInstance().getActiveConfiguration();
        String relativePath = file.getAbsolutePath()
                .replace(configuration.getPlansPath() + "/", "")
                .replace(configuration.getRolesPath() + "/", "")
                .replace(configuration.getTasksPath() + "/", "");
        URI uri = URI
                .createURI(relativePath);

        Resource resourceFromPool = alicaResourceSet.getResource(uri, false);
        if (resourceFromPool == null) {
            Resource loadedResource = alicaResourceSet.createResource(uri);
            loadedResource.load(new FileInputStream(file), AlicaSerializationHelper.getInstance().getLoadSaveOptions());
            T t = (T) loadedResource.getContents().get(0);
            if (t instanceof Plan) {
                URI pmlExURI = URI
                        .createURI(relativePath.replace("pml", "pmlex"));
                Resource loadedPmlUiExtension = alicaResourceSet.createResource(pmlExURI);
                try {
                    loadedPmlUiExtension.load(new FileInputStream(file.getAbsolutePath().replace("pml", "pmlex")),
                            AlicaSerializationHelper.getInstance().getLoadSaveOptions());
                } catch (FileNotFoundException e) {
                    // remove not completely loaded resource from the AlicaResourceSet
                    loadedPmlUiExtension.delete(AlicaSerializationHelper.getInstance().getLoadSaveOptions());
                    createPmlEx(file, uri);
                }
                loadedPmlUiExtension.setURI(pmlExURI);

            }
            return t;
        } else {
            return (T) resourceFromPool.getContents().get(0);
        }
    }


    public static <T extends EObject> T reloadAlicaFileFromDisk(File file) throws IOException {
        URI uri = createRelativeURI(file);

        alicaResourceSet.getResources().remove(alicaResourceSet.getResource(uri, false));
        Resource loadedResource = alicaResourceSet.createResource(uri);
        loadedResource.load(new FileInputStream(file), AlicaSerializationHelper.getInstance().getLoadSaveOptions());
        T t = (T) loadedResource.getContents().get(0);
        EcoreUtil.resolveAll(loadedResource);
        return t;
    }

    public static URI createRelativeURI(File file) {
        Configuration configuration = ConfigurationManager.getInstance().getActiveConfiguration();
        String relativePath = file.getAbsolutePath()
                .replace(configuration.getPlansPath() + "/", "")
                .replace(configuration.getRolesPath() + "/", "")
                .replace(configuration.getTasksPath() + "/", "");
        return URI
                .createURI(relativePath);
    }

    /**
     * Creates the best files, Alica Files. Or to be more specific {@link Resource} objects (which are written to disk).
     *
     * @param emptyObject
     * @param file
     * @param <T>
     * @throws IOException
     */
    public static <T extends EObject> Resource createAlicaFile(T emptyObject, boolean createPmlEx, File file) throws IOException {
        Resource resource = alicaResourceSet.createResource(URI.createURI(file.getAbsolutePath()));
        resource.getContents().add(emptyObject);
        URI relativeURI = createRelativeURI(file);

        resource.setURI(relativeURI);

        if (emptyObject instanceof Plan) {
            // TODO ALLALICAFILES IS BROKEN WITHOUT UNIQUE KEYS
            RepoViewBackend.getInstance()
                    .getPlans()
                    .add(new Pair<>((Plan) emptyObject, file.toPath()));
            if (createPmlEx) {
                createPmlEx(file, relativeURI);
            }
        } else if (emptyObject instanceof PlanType) {
            RepoViewBackend.getInstance().getPlanTypes().add(new Pair<>((PlanType) emptyObject,
                    file.toPath()));
        } else if (emptyObject instanceof Behaviour) {
            RepoViewBackend.getInstance().getBehaviours().add(new Pair<>((Behaviour) emptyObject,
                    file.toPath()));
        }

        // set destinationPath when resource is created
        setDestinationPath(emptyObject, file);

        resource.setTrackingModification(true);
        resource.save(AlicaSerializationHelper.getInstance().getLoadSaveOptions());
        LOG.info("Successfully created Alica file " + file.getAbsolutePath());
        return resource;
    }

    /**
     * Creates a pmlex when given an pml path
     *
     * @param file        a file with path to the pml NOT pmlex
     * @param relativeURI a uri to the pml
     * @throws IOException
     */
    private static void createPmlEx(File file, URI relativeURI) throws IOException {
        Resource pmlexResource = alicaResourceSet.createResource(URI.createURI(file.getAbsolutePath().replace(".pml", ".pmlex")));
        pmlexResource.getContents().add(getPmlUiExtensionModelFactory().createPmlUiExtensionMap());
        pmlexResource.setURI(URI.createURI(relativeURI.toString().replace(".pml", ".pmlex")));
        pmlexResource.save(AlicaSerializationHelper.getInstance().getLoadSaveOptions());
    }

    /**
     * Creates the best files, Alica Files. Or to be more specific {@link Resource} objects (which are written to disk).
     *
     * @param emptyObject
     * @param targetDir
     * @param pmlex
     * @param <T>
     * @throws IOException
     */
    public static <T extends EObject> Resource moveAlicaFile(T emptyObject, PmlUiExtensionMap pmlex, File targetDir) throws IOException {
        RepoViewBackend repoViewBackend = RepoViewBackend.getInstance();
        Resource resource = emptyObject.eResource();
        resource.setURI(createRelativeURI(targetDir));

        if (emptyObject instanceof Plan) {
            repoViewBackend
                    .getPlans()
                    .add(new Pair<>((Plan) emptyObject, targetDir.toPath()));
            Resource pmlexResource = pmlex.eResource();
            pmlexResource.setURI(URI.createURI(createRelativeURI(targetDir).toString().replace(".pml", ".pmlex")));
            pmlexResource.save(AlicaSerializationHelper.getInstance().getLoadSaveOptions());
        } else if (emptyObject instanceof Behaviour) {
            repoViewBackend
                    .getBehaviours()
                    .add(new Pair<>((Behaviour) emptyObject, targetDir.toPath()));
        } else if (emptyObject instanceof PlanType) {
            repoViewBackend
                    .getPlanTypes()
                    .add(new Pair<>((PlanType) emptyObject, targetDir.toPath()));
        } else {
            //TODO tasks
        }

        // set destinationPath when resource is moved
        setDestinationPath(emptyObject, targetDir);

        resource.save(AlicaSerializationHelper.getInstance().getLoadSaveOptions());
        alicaResourceSet
                .getResources()
                .forEach(e -> {
                    try {
                        EMFModelUtils.saveAlicaFile(e.getContents().get(0));
                    } catch (IOException e1) {
                        throw new RuntimeException(e1);
                    }
                });
        return resource;
    }

    private static <T extends EObject> void setDestinationPath(T emptyObject, File targetDir) {
        if (emptyObject instanceof AbstractPlan) {
            Configuration configuration = ConfigurationManager.getInstance().getActiveConfiguration();
            String destinationPath = targetDir.getAbsolutePath().replace(configuration.getPlansPath(), "Plans");
            destinationPath = destinationPath.substring(0, destinationPath.lastIndexOf(File.separator));
            ((AbstractPlan) emptyObject).setDestinationPath(destinationPath);
        }
    }

    /**
     * Saves the given alicaObject in the file.
     *
     * @param alicaObject the object to save
     * @param <T>         The type of {@link EObject} to save
     * @throws IOException
     */
    public static synchronized <T extends EObject> void saveAlicaFile(T alicaObject) throws IOException {
        alicaObject.eResource().save(AlicaSerializationHelper.getInstance().getLoadSaveOptions());
        LOG.info("Saved Alica successfully to disk specifically: " + alicaObject.eResource().getURI());
    }

    public static AlicaFactory getAlicaFactory() {
        return AlicaFactoryImpl.init();
    }

    public static PmlUIExtensionModelFactory getPmlUiExtensionModelFactory() {
        return PmlUIExtensionModelFactory.eINSTANCE;
    }

    /**
     * Returns a list of distinct {@link AbstractPlan}s in which the given plan element is used.
     *
     * @param planToGetUsageInformationAbout
     * @return
     */
    public static List<AbstractPlan> getUsages(PlanElement planToGetUsageInformationAbout) {

        List<AbstractPlan> references = new ArrayList<>();

        alicaResourceSet.getResources().forEach(e -> {
            if (e.getContents().size() != 0) {


                EObject eObject = e.getContents().get(0);
                if (eObject instanceof Plan) {
                    ArrayList<State> states = new ArrayList<>();
                    ((Plan) eObject).getStates().forEach(f -> {
                        if (f.getPlans().contains(planToGetUsageInformationAbout) && references.contains(eObject) == false) {
                            references.add((AbstractPlan) eObject);
                        }
                    });

                    ((Plan) eObject).getEntryPoints().forEach(f -> {
                        if (f.getTask().equals(planToGetUsageInformationAbout) && references.contains(eObject) == false) {
                            references.add((AbstractPlan) eObject);
                        }
                    });
                }

                if (eObject instanceof PlanType) {
                    PlanType planType = (PlanType) eObject;
                    planType.getPlans().forEach(f -> {
                        if (f.getPlan().equals(planToGetUsageInformationAbout) && references.contains(eObject) == false) {
                            references.add((AbstractPlan) eObject);
                        }
                    });
                }
            } else {
             //   System.out.println("HMMM: " + e.getURI().toString());
            }
        });
        return references;
    }
}
