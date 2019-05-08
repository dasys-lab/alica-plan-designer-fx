package de.uni_kassel.vs.cn.generator;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.Optional;

/**
 * This class serves an own implementation for EMF proxy resolution.
 * You can read up more in the EMF JavaDocs at http://download.eclipse.org/modeling/emf/emf/javadoc/2.8.0/org/eclipse/emf/ecore/impl/BasicEObjectImpl.html#eResolveProxy(org.eclipse.emf.ecore.InternalEObject)
 */
public class AlicaProxyResolvationHelper {

    public static EObject doResolve(EObject resolver, InternalEObject proxy) {
        String resourceURI = resolver.eResource().getURI().toString();
        String eProxyURI = proxy.eProxyURI().toString();
        AlicaResourceSet alicaResourceSet = AlicaResourceSet.getInstance();

        if(eProxyURI.startsWith("#")) {
            proxy.eSetProxyURI(URI.createURI(resourceURI + eProxyURI));
            return EcoreUtil.resolve(proxy, resolver);
        } else {
            EObject resolve = EcoreUtil.resolve(proxy, resolver);
            if (resolve.eIsProxy() && eProxyURI.startsWith("taskrepository.tsk")) {
                Resource resource = alicaResourceSet.getResources().stream().filter(e -> e.getURI().toString().startsWith("taskrepository.tsk")).findAny().get();
                return EcoreUtil.resolve(proxy,resource.getContents().get(0));
            } else if(resolve.eIsProxy() == false) {
                return resolve;
            } else {
                Optional<Resource> resourceOptional = alicaResourceSet
                        .getResources()
                        .stream()
                        .filter(e -> e.getURI().toString().contains("/" + eProxyURI.replaceAll("#.*", "")))
                        .findAny();
                Resource resource = null;

                if (eProxyURI.toString().contains(".beh")) {
                    Optional<Resource> first = alicaResourceSet
                            .getResources()
                            .stream()
                            .filter(e -> {
                                String s = eProxyURI;
                                s = s.substring(0, s.lastIndexOf('#'));
                                return e.getURI().toFileString().contains(s);
                            })
                            .findFirst();
                    long id;
                    resource = first.get();
                    String idString = eProxyURI.substring(eProxyURI.lastIndexOf('#') + 1);
                    proxy.eSetProxyURI(URI.createURI(resource.getURI().toString() + "#" + idString));
                    if (EcoreUtil.resolve(proxy, resource).eIsProxy()) {
                        id = ((Behaviour) first.get().getContents().get(0)).getId();
                        proxy.eSetProxyURI(URI.createURI(resource.getURI().toString() + "#" + id));
                    }

                } else {
                    resource = resourceOptional.get();
                    proxy.eSetProxyURI(URI.createURI(resource.getURI().toString() + eProxyURI.replaceAll(".*#","#")));
                }
                return EcoreUtil.resolve(proxy, resource);
            }
        }
    }
}
