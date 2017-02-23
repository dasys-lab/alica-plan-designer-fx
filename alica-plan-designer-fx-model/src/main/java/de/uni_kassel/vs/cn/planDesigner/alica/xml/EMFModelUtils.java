package de.uni_kassel.vs.cn.planDesigner.alica.xml;

import de.uni_kassel.vs.cn.planDesigner.alica.AlicaFactory;
import de.uni_kassel.vs.cn.planDesigner.alica.AlicaPackage;
import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.configuration.Configuration;
import de.uni_kassel.vs.cn.planDesigner.alica.impl.AlicaFactoryImpl;
import de.uni_kassel.vs.cn.planDesigner.alica.impl.AlicaPackageImpl;
import de.uni_kassel.vs.cn.planDesigner.alica.impl.PlanImpl;
import de.uni_kassel.vs.cn.planDesigner.alica.util.AlicaResourceSet;
import de.uni_kassel.vs.cn.planDesigner.alica.util.AlicaSerializationHelper;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUIExtensionModelFactory;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUIExtensionModelPackage;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.impl.PmlUIExtensionModelPackageImpl;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.util.PmlUIExtensionModelResourceFactoryImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Created by marci on 08.11.16.
 */
public class EMFModelUtils {

    private static AlicaResourceSet alicaResourceSet;

    private static void initAlicaResourceSet() {
        alicaResourceSet = new AlicaResourceSet();
        AlicaResourceSet alicaResourceSet = new AlicaResourceSet();
		URIConverter uriConverter = ExtensibleURIConverterImpl.INSTANCE;
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
    }

    /**
     * Loads given file from disk
     * @param file the file to load must not be null
     * @return the returned {@link EObject}
     * @throws IOException if loading fails because of nonexistence or if problems happen while reading
     */
     public static <T extends EObject> T loadAlicaFileFromDisk(File file) throws IOException {
         String relativePath = file.getAbsolutePath()
                 .replace(new Configuration().getPlansPath() + "/","")
                 .replace(new Configuration().getRolesPath()+ "/","")
                 .replace(new Configuration().getMiscPath()+ "/","");
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
             }
             return t;
         } else {
             return (T) resourceFromPool.getContents().get(0);
         }
    }

    /**
     * Saves the given alicaObject in the file.
     * @param alicaObject the object to save
     * @param <T> The type of {@link EObject} to save
     * @throws IOException
     */
    public static <T extends EObject> void saveAlicaFile(T alicaObject) throws IOException {
        // TODO This outcommented code is resource creation it happens ONLY on file/resource creation
        // create a resource
        //Resource resource = alicaResourceSet.createResource(URI
        //        .createURI(file.getAbsolutePath()));
        // Get the first model element and cast it to the right type, in my
        // example everything is hierarchical included in this first node

        //resource.getContents().add(alicaObject);

        // now save the content.
        alicaObject.eResource().save(AlicaSerializationHelper.getInstance().getLoadSaveOptions());
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
}
