package de.uni_kassel.vs.cn.planDesigner.ui;

import de.uni_kassel.vs.cn.planDesigner.alica.AlicaPackage;
import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import java.io.File;
import java.io.IOException;

/**
 * Created by marci on 18.11.16.
 */
public class EditorTabPane extends TabPane {

    public void openTab(File file) throws IOException {
        Tab tab = getTabs()
                .stream()
                .filter(e -> ((EditorTab) e).getFile().equals(file))
                .findFirst()
                .orElse(new EditorTab<>((Plan) loadPMLFromDisk(file), file));

        if(this.getTabs().contains(tab) == false) {
            getTabs().add(tab);
        }

    }

    private EObject loadPMLFromDisk(File file) throws IOException {
        ResourceSet resourceSet = new ResourceSetImpl();
        Resource loadedResource = resourceSet.createResource(URI
                .createURI(file.getAbsolutePath()));
        loadedResource.load(((XMIResourceImpl) loadedResource).getDefaultLoadOptions());
        return loadedResource.getContents().get(0);
    }
}
