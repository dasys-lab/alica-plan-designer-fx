package de.uni_kassel.vs.cn.generator;

import de.uni_kassel.vs.cn.planDesigner.alica.AlicaPackage;
import de.uni_kassel.vs.cn.planDesigner.alica.impl.AlicaPackageImpl;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUIExtensionModelPackage;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.impl.PmlUIExtensionModelPackageImpl;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.util.PmlUIExtensionModelResourceFactoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import java.io.IOException;
import java.util.Map;

/**
 * This Codegenerator console application (re)generates all plans/behaviours and terminates afterwards.
 */
public class StandaloneCodegenerator {
    private static final Logger LOG = LogManager.getLogger(StandaloneCodegenerator.class);
    private static AlicaResourceSet alicaResourceSet;

    private static void initAlicaResourceSet() {
        alicaResourceSet = new AlicaResourceSet();
        AlicaResourceSet alicaResourceSet = new AlicaResourceSet();
        URIConverter uriConverter = ExtensibleURIConverterImpl.INSTANCE;

        alicaResourceSet.getResources().forEach(e -> e.setTrackingModification(true));
    }

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

    public static void main(String[] args) throws IOException {
        initializeEMF();
        Codegenerator codegenerator = new Codegenerator();
        codegenerator.generate();
    }
}
