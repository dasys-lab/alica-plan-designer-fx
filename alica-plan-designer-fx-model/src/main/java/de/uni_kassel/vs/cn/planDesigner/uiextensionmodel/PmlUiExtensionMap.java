package de.uni_kassel.vs.cn.planDesigner.uiextensionmodel;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement;

import java.util.HashMap;

public class PmlUiExtensionMap {
    protected HashMap<PlanElement, PmlUiExtension> extensionHashMap = null;

    public HashMap<PlanElement, PmlUiExtension> getExtension() {
        if(extensionHashMap == null) {
            return new HashMap<PlanElement, PmlUiExtension>();
        }
        return extensionHashMap;
    }
}
