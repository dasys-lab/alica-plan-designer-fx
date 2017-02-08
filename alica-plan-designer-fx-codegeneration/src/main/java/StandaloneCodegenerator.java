import de.uni_kassel.vs.cn.planDesigner.alica.AlicaFactory;
import de.uni_kassel.vs.cn.planDesigner.alica.AlicaPackage;
import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.State;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

/**
 * Created by marci on 07.11.16.
 */
public class StandaloneCodegenerator {
    public static void main(String[] args) throws IOException {
        // initialize the model
        EClass eClass = AlicaPackage.eINSTANCE.eClass();

        // Retrieve the default factory singleton
        /*AlicaFactory factory = AlicaFactory.eINSTANCE;

        // create the content of the model via this program
        Plan myWeb = factory.createPlan();
        State page = factory.createState();
        page.setName("index");
        page.setComment("Main webpage");
        page.setInPlan(myWeb);*/
        //myWeb.getPages().add(page);

        // As of here we preparing to save the model content

        // Register the XMI resource factory for the .website extension

        Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put("website", new XMIResourceFactoryImpl());
        /*
        // Obtain a new resource set
        ResourceSet resSet = new ResourceSetImpl();

        // create a resource
        Resource resource = resSet.createResource(URI
                .createURI("website/My2.website"));
        // Get the first model element and cast it to the right type, in my
        // example everything is hierarchical included in this first node
        resource.getContents().add(myWeb);

        // now save the content.
        try {
            resource.save(Collections.EMPTY_MAP);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/ /*
        AlicaFactory alicaFactory = AlicaFactoryImpl.init();
        Plan plan = alicaFactory.createPlan();*/

        ResourceSet res2Set = new ResourceSetImpl();

        // create a resource
        Resource resource2 = res2Set.createResource(URI
                .createURI("website/My2.website"));
        resource2.load(((XMIResourceImpl) resource2).getDefaultLoadOptions());
        resource2.getContents().get(0).eClass();

    }
}
