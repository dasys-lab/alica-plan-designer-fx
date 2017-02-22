package de.uni_kassel.vs.cn.planDesigner.alica.util;

import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.Optional;

/**
 * Created by marci on 16.12.16.
 */
public class AlicaProxyResolvationHelper {

    public static EObject doResolve(EObject resolver, InternalEObject proxy) {
        String resourceURI = resolver.eResource().getURI().toString();
        String eProxyURI = proxy.eProxyURI().toString();

        if(eProxyURI.startsWith("#")) {
            proxy.eSetProxyURI(URI.createURI(resourceURI + eProxyURI));
            return EcoreUtil.resolve(proxy, resolver);
        } else {
            EObject resolve = EcoreUtil.resolve(proxy, resolver);
            if (resolve.eIsProxy() && eProxyURI.startsWith("taskrepository.tsk")) {
                Resource resource = EMFModelUtils.getAlicaResourceSet().getResources().stream().filter(e -> e.getURI().toString().startsWith("taskrepository.tsk")).findAny().get();
                return EcoreUtil.resolve(proxy,resource.getContents().get(0));
            } else if(resolve.eIsProxy() == false) {
                return resolve;
            } else {
                Optional<Resource> resourceOptional = EMFModelUtils
                        .getAlicaResourceSet()
                        .getResources()
                        .stream()
                        .filter(e -> e.getURI().toString().contains("/" + eProxyURI.replaceAll("#.*", "")))
                        .findAny();
                Resource resource = resourceOptional.get();
                proxy.eSetProxyURI(URI.createURI(resource.getURI().toString() + eProxyURI.replaceAll(".*#","#")));
                return EcoreUtil.resolve(proxy, resource);
            }
        }
    }
}
