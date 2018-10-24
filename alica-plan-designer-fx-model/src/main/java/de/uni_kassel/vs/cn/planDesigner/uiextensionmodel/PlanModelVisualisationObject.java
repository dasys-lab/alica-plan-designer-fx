package de.uni_kassel.vs.cn.planDesigner.uiextensionmodel;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;

public class PlanModelVisualisationObject {
    private Plan plan; //Can not be final due to the deserialization-process
    private final PmlUiExtensionMap pmlUiExtensionMap;

    /**
     * Default-constructor, only necessary for deserialization
     */
    private PlanModelVisualisationObject() {
        this(null, null);
    }

    public PlanModelVisualisationObject(Plan plan, PmlUiExtensionMap pmlUiExtensionMap) {
        this.plan = plan;
        this.pmlUiExtensionMap = pmlUiExtensionMap;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public PmlUiExtensionMap getPmlUiExtensionMap() {
        return pmlUiExtensionMap;
    }
}
