package de.uni_kassel.vs.cn.planDesigner.alica.util;

import de.uni_kassel.vs.cn.planDesigner.alica.configuration.Configuration;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.impl.EObjectToPmlUiExtensionMapEntryImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.io.File;

/**
 * Created by marci on 16.12.16.
 */
public class AlicaProxyResolvationHelper {

    public static EObject doResolve(EObject resolver, InternalEObject proxy) {
        String resourceURI = resolver.eResource().getURI().toString();
        String eProxyURI = proxy.eProxyURI().toString();
        if (eProxyURI.startsWith("/") == false) {
            if (resolver instanceof EObjectToPmlUiExtensionMapEntryImpl) {
                resourceURI = resourceURI.replace(".pmlex",".pml");
                //proxy.eSetProxyURI(URI.createURI(resourceURI + proxy.eProxyURI()));
            }

            if(proxy.eProxyURI().toString().startsWith("#") == false) {
                eProxyURI=eProxyURI.replace(resourceURI.substring(resourceURI.lastIndexOf("/")+1),"");
            }

            if (eProxyURI.contains("..")) {
                boolean first = true;
                while (eProxyURI.contains("..")) {
                    if(first) {
                        resourceURI = resourceURI.substring(0, resourceURI.lastIndexOf("/"));
                        first = false;
                    }
                    resourceURI = resourceURI.substring(0, resourceURI.lastIndexOf("/"));
                    eProxyURI = eProxyURI.substring(eProxyURI.indexOf(".")+1,eProxyURI.length());
                    eProxyURI = eProxyURI.substring(eProxyURI.indexOf(".")+1,eProxyURI.length());
                    eProxyURI = eProxyURI.replace("//","/");
                }
                proxy.eSetProxyURI(URI.createURI(resourceURI + eProxyURI));
                eProxyURI = proxy.eProxyURI().toString();
            }

            String tempResourceURI = resourceURI.substring(0, resourceURI.lastIndexOf("/") + 1) + eProxyURI;
            String eProxyURIWithoutHashtag = eProxyURI;
            if(eProxyURI.contains("#")) {
                eProxyURIWithoutHashtag = eProxyURI.substring(0,eProxyURI.indexOf("#"));
            }
            if(new File(resourceURI + eProxyURIWithoutHashtag).exists()) {
                proxy.eSetProxyURI(URI.createURI(resourceURI + eProxyURI));
            } else if(new File(tempResourceURI).exists()) {
                proxy.eSetProxyURI(URI.createURI(tempResourceURI));
            } else if(tempResourceURI.contains("#") && new File(tempResourceURI.substring(0,tempResourceURI.indexOf("#"))).exists()) {
                proxy.eSetProxyURI(URI.createURI(tempResourceURI));
            }

            eProxyURI = proxy.eProxyURI().toString();

            if (eProxyURI.startsWith("/") == false) {
                if (eProxyURI.contains(".pml") || eProxyURI.contains(".beh") || eProxyURI.contains(".pty")) {
                    proxy.eSetProxyURI(URI.createURI(new Configuration().getPlansPath() + "/" + eProxyURI));
                } else {
                    throw new RuntimeException("KILL ME");
                }
            }
        }
        return EcoreUtil.resolve(proxy, resolver);
    }
}
