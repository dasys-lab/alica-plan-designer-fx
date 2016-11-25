package de.uni_kassel.vs.cn.planDesigner.alica.xml;

import de.uni_kassel.vs.cn.planDesigner.alica.AlicaPackage;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUIExtensionModelPackage;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.util.PmlUIExtensionModelResourceFactoryImpl;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.util.PmlUIExtensionModelResourceImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

/**
 * Created by marci on 08.11.16.
 */
public class EMFModelUtils {


    /**
     * Initializes EMF context, adds filetypes which can be read via means of EMF
     */
    @SuppressWarnings("unused")
    public static void initializeEMF() {
        // initialize the model
        EClass alicaPackageEClass = AlicaPackage.eINSTANCE.eClass();
        EClass uiExtensionEClass = PmlUIExtensionModelPackage.eINSTANCE.eClass();
        Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put("pml", new XMIResourceFactoryImpl());
        m.put("pmlex", new PmlUIExtensionModelResourceFactoryImpl());
    }

    /**
     * Loads given file from disk
     * @param file the file to load must not be null
     * @return the returned {@link EObject}
     * @throws IOException if loading fails because of nonexistence or if problems happen while reading
     */
     public static <T extends EObject> T loadAlicaFileFromDisk(File file) throws IOException {
        ResourceSet resourceSet = new ResourceSetImpl();
        Resource loadedResource = resourceSet.createResource(URI
                .createURI(file.getAbsolutePath()));
        // XXX This part is strange PmlUIExtensionModelResourceImpl implements XMIResourceImpl, but a cast to it causes a exception
        if (loadedResource instanceof PmlUIExtensionModelResourceImpl) {
            loadedResource.load(((PmlUIExtensionModelResourceImpl) loadedResource).getDefaultLoadOptions());
        } else {
            loadedResource.load(((XMIResourceImpl) loadedResource).getDefaultLoadOptions());
        }
        return (T) loadedResource.getContents().get(0);
    }

    /**
     * Saves the given alicaObject in the file.
     * @param file the given file
     * @param alicaObject the object to save
     * @param <T> The type of {@link EObject} to save
     * @throws IOException 
     */
    public static <T extends EObject> void saveAlicaFile(File file, T alicaObject) throws IOException {
        // create a resource
        ResourceSet resourceSet = new ResourceSetImpl();
        Resource resource = resourceSet.createResource(URI
                .createURI(file.getAbsolutePath()));
        // Get the first model element and cast it to the right type, in my
        // example everything is hierarchical included in this first node
        resource.getContents().add(alicaObject);

        // now save the content.
        resource.save(Collections.EMPTY_MAP);
    }
}
