package de.uni_kassel.vs.cn.planDesigner.uiextensionmodel;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;

public class PlanModelVisualisationObject {
    private final Plan plan;
    private final PmlUiExtensionMap pmlUiExtensionMap;

    public PlanModelVisualisationObject(Plan plan, PmlUiExtensionMap pmlUiExtensionMap) {
        this.plan = plan;
        this.pmlUiExtensionMap = pmlUiExtensionMap;
    }

    public Plan getPlan() {
        return plan;
    }

    public PmlUiExtensionMap getPmlUiExtensionMap() {
        return pmlUiExtensionMap;
    }
}
