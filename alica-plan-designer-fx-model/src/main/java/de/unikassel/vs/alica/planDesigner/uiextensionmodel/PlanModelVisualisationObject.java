package de.unikassel.vs.alica.planDesigner.uiextensionmodel;

import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.alicamodel.PlanElement;

import java.util.HashMap;
import java.util.Set;

public class PlanModelVisualisationObject {
    private Plan plan; //Can not be final due to the deserialization-process
    protected HashMap<PlanElement, PmlUiExtension> extensionMap = null;

    /**
     * Default-constructor, only necessary for deserialization
     */
    private PlanModelVisualisationObject() {
        this(null);
    }

    public PlanModelVisualisationObject(Plan plan) {
        this.plan = plan;
        this.extensionMap = new HashMap<>();
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public void replaceKey(PlanElement element) {
        for (PlanElement key : extensionMap.keySet()) {
            if (key.getId() == element.getId()) {
                PmlUiExtension extension = extensionMap.get(element);
                extensionMap.remove(key);
                extensionMap.put(element, extension);
                return;
            }
        }

    }

    public Set<PlanElement> getKeys() {
        return extensionMap.keySet();
    }

    public PmlUiExtension getPmlUiExtension(PlanElement element) {
        return extensionMap.get(element);
    }

    public void put(PlanElement element, PmlUiExtension pmlUiExtension) {
        this.extensionMap.put(element, pmlUiExtension);
    }

    public void removePlanElement(PlanElement element) {
        this.extensionMap.remove(element);
    }

    /**
     * Method for simplifying the access to the {@link PmlUiExtension}-objects.
     * <p>
     * Whenever a {@link PlanElement}, which has no PmlUiExtension, is
     * requested, a  PmlUiExtension is created and put into the map. This Method
     * may not be required later, when PmlUiExtensions are saved, loaded and created
     * automatically
     *
     * @param planElement the {@link PlanElement} to find a {@link PmlUiExtension} for
     * @return the corresponding {@link PmlUiExtension} or a new one
     */
    public PmlUiExtension getPmlUiExtensionOrCreateNew(PlanElement planElement) {
        PmlUiExtension pmlUiExtension = extensionMap.get(planElement);
        if (pmlUiExtension == null) {
            pmlUiExtension = new PmlUiExtension();
            extensionMap.put(planElement, pmlUiExtension);
        }
        return pmlUiExtension;
    }

}
