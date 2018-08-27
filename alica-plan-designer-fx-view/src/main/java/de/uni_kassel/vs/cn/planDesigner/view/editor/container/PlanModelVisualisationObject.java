package de.uni_kassel.vs.cn.planDesigner.view.editor.container;

import de.uni_kassel.vs.cn.planDesigner.view.model.PlanViewModel;

public class PlanModelVisualisationObject {
    private final PlanViewModel plan;
//    private final PmlUiExtensionMap pmlUiExtensionMap;

    public PlanModelVisualisationObject(PlanViewModel plan) {
        this.plan = plan;
//        this.pmlUiExtensionMap = pmlUiExtensionMap;
    }

    public PlanViewModel getPlanId() {
        return plan;
    }

//    public PmlUiExtensionMap getPmlUiExtensionMap() {
//        return pmlUiExtensionMap;
//    }
}
