package de.uni_kassel.vs.cn.planDesigner.alica.xml;

import de.uni_kassel.vs.cn.planDesigner.alica.*;
import de.uni_kassel.vs.cn.planDesigner.alica.configuration.Configuration;
import de.uni_kassel.vs.cn.planDesigner.alica.configuration.WorkspaceManager;
import de.uni_kassel.vs.cn.planDesigner.alica.impl.AlicaFactoryImpl;
import de.uni_kassel.vs.cn.planDesigner.alica.impl.AlicaPackageImpl;
import de.uni_kassel.vs.cn.planDesigner.alica.util.AlicaResourceSet;
import de.uni_kassel.vs.cn.planDesigner.alica.util.AlicaSerializationHelper;
import de.uni_kassel.vs.cn.planDesigner.alica.util.AllAlicaFiles;
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
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by marci on 08.11.16.
 */
public class EMFModelUtils {

    private static final Logger LOG = LogManager.getLogger(EMFModelUtils.class);
    private static AlicaResourceSet alicaResourceSet;
    private static Configuration configuration = new WorkspaceManager().getActiveWorkspace().getConfiguration();

    @SuppressWarnings("unused")
    private static void initAlicaResourceSet() {
        alicaResourceSet = new AlicaResourceSet();
        AlicaResourceSet alicaResourceSet = new AlicaResourceSet();
		URIConverter uriConverter = ExtensibleURIConverterImpl.INSTANCE;

		alicaResourceSet.getResources().forEach(e -> e.setTrackingModification(true));
    }

    /**
     * Initializes EMF context, adds filetypes which can be read via means of EMF
     */
    @SuppressWarnings("unused")
    public static void initializeEMF() {
        // initialize the model
        EClass alicaPackageEClass = AlicaPackage.eINSTANCE.eClass();
        EClass uiExtensionEClass = PmlUIExtensionModelPackage.eINSTANCE.eClass();
        Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        URIConverter extensibleURIConverter = ExtensibleURIConverterImpl.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        // Initialize the model and the extensionUI package
        AlicaPackageImpl.init();
        PmlUIExtensionModelPackageImpl.init();
        m.put("pml", new XMIResourceFactoryImpl());
        m.put("pmlex", new PmlUIExtensionModelResourceFactoryImpl());
        m.put("beh", new XMIResourceFactoryImpl());
        m.put("pty", new XMIResourceFactoryImpl());
        m.put("tsk", new XMIResourceFactoryImpl());
        if (alicaResourceSet == null) {
            initAlicaResourceSet();
        }
        LOG.info("EMF Base Classes initialized");
    }

    /**
     * Loads given file from disk
     * @param file the file to load must not be null
     * @return the returned {@link EObject}
     * @throws IOException if loading fails because of nonexistence or if problems happen while reading
     */
     public static <T extends EObject> T loadAlicaFileFromDisk(File file) throws IOException {
         String relativePath = file.getAbsolutePath()
                 .replace(configuration.getPlansPath() + "/","")
                 .replace(configuration.getRolesPath()+ "/","")
                 .replace(configuration.getMiscPath()+ "/","");
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
                 loadedPmlUiExtension.load(new FileInputStream(file.getAbsolutePath().replace("pml", "pmlex")), AlicaSerializationHelper.getInstance().getLoadSaveOptions());
                 loadedPmlUiExtension.setURI(pmlExURI);
             }
             return t;
         } else {
             return (T) resourceFromPool.getContents().get(0);
         }
    }


    public static <T extends EObject> T reloadAlicaFileFromDisk(File file) throws IOException {
        String relativePath = file.getAbsolutePath()
                .replace(configuration.getPlansPath() + "/","")
                .replace(configuration.getRolesPath()+ "/","")
                .replace(configuration.getMiscPath()+ "/","");
        URI uri = URI
                .createURI(relativePath);

        alicaResourceSet.getResources().remove(alicaResourceSet.getResource(uri, false));
        Resource loadedResource = alicaResourceSet.createResource(uri);
        loadedResource.load(new FileInputStream(file), AlicaSerializationHelper.getInstance().getLoadSaveOptions());
        T t = (T) loadedResource.getContents().get(0);
        EcoreUtil.resolveAll(loadedResource);
        return t;
    }

    /**
     * Creates the best files, Alica Files. Or to be more specific {@link Resource} objects (which are written to disk).
     * @param emptyObject
     * @param file
     * @param <T>
     * @throws IOException
     */
    public static <T extends EObject> Resource createAlicaFile(T emptyObject, boolean createPmlEx, File file) throws IOException {
        Resource resource = alicaResourceSet.createResource(URI.createURI(file.getAbsolutePath()));
        resource.getContents().add(emptyObject);
        String relativePath = file.getAbsolutePath()
                .replace(configuration.getPlansPath() + "/","")
                .replace(configuration.getRolesPath()+ "/","")
                .replace(configuration.getMiscPath()+ "/","");
        URI relativeURI = URI
                .createURI(relativePath);

        resource.setURI(relativeURI);

        if (emptyObject instanceof Plan) {
            // TODO ALLALICAFILES IS BROKEN WITHOUT UNIQUE KEYS
            AllAlicaFiles.getInstance()
                    .getPlans()
                    .add(new Pair<>((Plan) emptyObject, file.toPath()));
            if (createPmlEx) {
                Resource pmlexResource = alicaResourceSet.createResource(URI.createURI(file.getAbsolutePath().replace(".pml", ".pmlex")));
                pmlexResource.getContents().add(getPmlUiExtensionModelFactory().createPmlUiExtensionMap());
                pmlexResource.setURI(URI.createURI(relativeURI.toString().replace(".pml", ".pmlex")));
                pmlexResource.save(AlicaSerializationHelper.getInstance().getLoadSaveOptions());
            }
        } else if (emptyObject instanceof PlanType) {
            AllAlicaFiles.getInstance().getPlanTypes().add(new Pair<>((PlanType) emptyObject,
                    file.toPath()));
        } else if (emptyObject instanceof Behaviour) {
            AllAlicaFiles.getInstance().getBehaviours().add(new Pair<>((Behaviour) emptyObject,
                    file.toPath()));
        }

        // set destinationPath when resource is created
        if (emptyObject instanceof AbstractPlan) {
            ((AbstractPlan) emptyObject).setDestinationPath(file.getAbsolutePath().replace(configuration.getPlansPath(),"Plans/"));
        }
        resource.setTrackingModification(true);
        resource.save(AlicaSerializationHelper.getInstance().getLoadSaveOptions());
        LOG.info("Successfully created Alica file " + file.getAbsolutePath());
        return resource;
    }

    /**
     * Creates the best files, Alica Files. Or to be more specific {@link Resource} objects (which are written to disk).
     * @param emptyObject
     * @param targetDir
     * @param pmlex
     * @param <T>
     * @throws IOException
     */
    public static <T extends EObject> Resource moveAlicaFile(T emptyObject, PmlUiExtensionMap pmlex, File targetDir) throws IOException {
        Resource resource = emptyObject.eResource();
        resource.setURI(URI.createURI(targetDir.getAbsolutePath()));
        if (emptyObject instanceof Plan) {
            AllAlicaFiles.getInstance()
                    .getPlans()
                    .add(new Pair<>((Plan) emptyObject, targetDir.toPath()));
            Resource pmlexResource = pmlex.eResource();
            pmlexResource.setURI(URI.createURI(targetDir.getAbsolutePath().replace(".pml", ".pmlex")));
            pmlexResource.save(AlicaSerializationHelper.getInstance().getLoadSaveOptions());
        }
        resource.save(AlicaSerializationHelper.getInstance().getLoadSaveOptions());
        getAlicaResourceSet()
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

    /**
     * Saves the given alicaObject in the file.
     * @param alicaObject the object to save
     * @param <T> The type of {@link EObject} to save
     * @throws IOException
     */
    public static <T extends EObject> void saveAlicaFile(T alicaObject) throws IOException {
        alicaObject.eResource().save(AlicaSerializationHelper.getInstance().getLoadSaveOptions());
        LOG.info("Saved Alica successfully to disk specifically: " + alicaObject.eResource().getURI());
    }

    public static AlicaResourceSet getAlicaResourceSet() {
        if (alicaResourceSet == null) {
            initAlicaResourceSet();
        }
        return alicaResourceSet;
    }

    public static AlicaFactory getAlicaFactory() {
        return AlicaFactoryImpl.init();
    }

    public static PmlUIExtensionModelFactory getPmlUiExtensionModelFactory() {
        return PmlUIExtensionModelFactory.eINSTANCE;
    }

    /**
     * Returns a list of distinct {@link AbstractPlan}s in which the given plan element is used.
     * @param planToGetUsageInformationAbout
     * @return
     */
    public static List<AbstractPlan> getUsages(PlanElement planToGetUsageInformationAbout) {

        List<AbstractPlan> references = new ArrayList<>();

        EMFModelUtils
                .getAlicaResourceSet().getResources().forEach(e -> {
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
        });
        return references;
    }
}
