package de.uni_kassel.vs.cn.planDesigner.uiextensionmodel;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement;

import java.util.HashMap;

public class PmlUiExtensionMap {
    protected HashMap<PlanElement, PmlUiExtension> extensionHashMap = null;


    public HashMap<PlanElement, PmlUiExtension> getExtension() {
        if(extensionHashMap == null) {
            extensionHashMap = new HashMap<PlanElement, PmlUiExtension>();
        }
        return extensionHashMap;
    }

    public void setExtensionHashMap( HashMap<PlanElement, PmlUiExtension> newExtension){
        this.extensionHashMap = newExtension;
    }

    /**
     * Method for simplifying the access to the {@link PmlUiExtension}-objects.
     *
     * Whenever a {@link PlanElement}, which has no PmlUiExtension, is
     * requested, a  PmlUiExtension is created and put into the map. This Method
     * may not be required later, when PmlUiExtensions are saved, loaded and created
     * automatically
     *
     * @param planElement  the {@link PlanElement} to find a {@link PmlUiExtension} for
     * @return  the corresponding {@link PmlUiExtension} or a new one
     */
    public PmlUiExtension getPmlUiExtensionOrCreateNew(PlanElement planElement){
        PmlUiExtension pmlUiExtension = getExtension().get(planElement);
        if(pmlUiExtension == null){
            pmlUiExtension = new PmlUiExtension();
            getExtension().put(planElement, pmlUiExtension);
        }
        return pmlUiExtension;
    }
}
