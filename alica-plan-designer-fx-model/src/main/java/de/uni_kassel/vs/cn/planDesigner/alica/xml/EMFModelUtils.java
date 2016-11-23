package de.uni_kassel.vs.cn.planDesigner.alica.xml;

import de.uni_kassel.vs.cn.planDesigner.alica.AlicaPackage;
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
import java.util.Map;

/**
 * Created by marci on 08.11.16.
 */
public class EMFModelUtils {

    /**
     * Initializes EMF context, adds filetypes which can be read via means of EMF
     */
    public static void initializeEMF() {
        // initialize the model
        EClass eClass = AlicaPackage.eINSTANCE.eClass();
        Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put("pml", new XMIResourceFactoryImpl());
        m.put("pmlex", new XMIResourceFactoryImpl());
    }

    /**
     * Loads given file from disk
     * @param file
     * @return
     * @throws IOException
     */
     public static <T extends EObject> T loadAlicaFileFromDisk(File file) throws IOException {
        ResourceSet resourceSet = new ResourceSetImpl();
        Resource loadedResource = resourceSet.createResource(URI
                .createURI(file.getAbsolutePath()));
        loadedResource.load(((XMIResourceImpl) loadedResource).getDefaultLoadOptions());
        return (T) loadedResource.getContents().get(0);
    }
}
