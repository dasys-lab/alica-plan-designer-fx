package de.uni_kassel.vs.cn.planDesigner.aggregatedModel;

import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtensionMap;

/**
 * Created by marci on 01.12.16.
 */
public class PlanModelVisualisationObject {
    private Plan plan;
    private PmlUiExtensionMap pmlUiExtensionMap;

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public PmlUiExtensionMap getPmlUiExtensionMap() {
        return pmlUiExtensionMap;
    }

    public void setPmlUiExtensionMap(PmlUiExtensionMap pmlUiExtensionMap) {
        this.pmlUiExtensionMap = pmlUiExtensionMap;
    }
}
