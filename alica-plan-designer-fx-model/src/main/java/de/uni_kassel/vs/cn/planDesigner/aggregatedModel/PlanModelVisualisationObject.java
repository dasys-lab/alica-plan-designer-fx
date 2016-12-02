package de.uni_kassel.vs.cn.planDesigner.aggregatedModel;

import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtensionMap;

/**
 * Created by marci on 01.12.16.
 */
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
