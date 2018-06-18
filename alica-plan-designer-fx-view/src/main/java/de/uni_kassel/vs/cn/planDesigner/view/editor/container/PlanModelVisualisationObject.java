package de.uni_kassel.vs.cn.planDesigner.view.editor.container;

public class PlanModelVisualisationObject {
    private final Long planId;
//    private final PmlUiExtensionMap pmlUiExtensionMap;

    public PlanModelVisualisationObject(long planId) {
        this.planId = planId;
//        this.pmlUiExtensionMap = pmlUiExtensionMap;
    }

    public Long getPlanId() {
        return planId;
    }

//    public PmlUiExtensionMap getPmlUiExtensionMap() {
//        return pmlUiExtensionMap;
//    }
}
