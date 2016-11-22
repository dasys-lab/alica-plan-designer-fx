package de.uni_kassel.vs.cn.planDesigner.alica.xml;

import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.configuration.Configuration;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by marci on 08.11.16.
 */
public class EMFModelReader {

    private Configuration configuration;

    /**
     * Searches for given planFileName in the configured {@link Configuration#plansPath}
     * @param planFileName
     * @return
     * @throws IOException
     */
    public Plan readPlan(String planFileName) throws IOException {
        String planPath = configuration.getPlansPath() + File.separator + planFileName;
        ResourceSet resSet = new ResourceSetImpl();
        Resource resource = resSet.createResource(URI.createURI(planPath));
        resource.load(((XMIResourceImpl) resource).getDefaultLoadOptions());
        return (Plan) resource.getContents().get(0);
    }
}
