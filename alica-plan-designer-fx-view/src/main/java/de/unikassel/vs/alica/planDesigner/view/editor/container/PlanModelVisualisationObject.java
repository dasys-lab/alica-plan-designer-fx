package de.unikassel.vs.alica.planDesigner.view.editor.container;

import de.unikassel.vs.alica.planDesigner.view.model.PlanViewModel;

public class PlanModelVisualisationObject {
    private final PlanViewModel plan;
//    private final PmlUiExtensionMap pmlUiExtensionMap;

    public PlanModelVisualisationObject(PlanViewModel plan) {
        this.plan = plan;
//        this.pmlUiExtensionMap = pmlUiExtensionMap;
    }

    public PlanViewModel getPlan() {
        return plan;
    }

//    public PmlUiExtensionMap getPmlUiExtensionMap() {
//        return pmlUiExtensionMap;
//    }
}
