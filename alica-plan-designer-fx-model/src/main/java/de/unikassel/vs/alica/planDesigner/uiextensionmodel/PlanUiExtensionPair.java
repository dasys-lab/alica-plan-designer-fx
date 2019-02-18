package de.unikassel.vs.alica.planDesigner.uiextensionmodel;

import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.alicamodel.PlanElement;

import java.util.HashMap;
import java.util.Set;

public class PlanUiExtensionPair {
    protected Plan plan; //Can not be final due to the deserialization-process
    protected HashMap<PlanElement, UiExtension> extensionMap = null;

    /**
     * Default-constructor, only necessary for deserialization
     */
    private PlanUiExtensionPair() {
        this(null);
    }

    public PlanUiExtensionPair(Plan plan) {
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
                UiExtension extension = extensionMap.get(element);
                extensionMap.remove(key);
                extensionMap.put(element, extension);
                return;
            }
        }

    }

    public Set<PlanElement> getKeys() {
        return extensionMap.keySet();
    }

    public UiExtension getPmlUiExtension(PlanElement element) {
        return extensionMap.get(element);
    }

    public void put(PlanElement element, UiExtension uiExtension) {
        this.extensionMap.put(element, uiExtension);
    }

    public void remove(PlanElement element) {
        this.extensionMap.remove(element);
    }

    /**
     * Method for simplifying the access to the {@link UiExtension}-objects.
     * <p>
     * Whenever a {@link PlanElement}, which has no UiExtension, is
     * requested, a  UiExtension is created and put into the map. This Method
     * may not be required later, when PmlUiExtensions are saved, loaded and created
     * automatically
     *
     * @param planElement the {@link PlanElement} to find a {@link UiExtension} for
     * @return the corresponding {@link UiExtension} or a new one
     */
    public UiExtension getUiExtension(PlanElement planElement) {
        UiExtension uiExtension = extensionMap.get(planElement);
        if (uiExtension == null) {
            uiExtension = new UiExtension();
            extensionMap.put(planElement, uiExtension);
        }
        return uiExtension;
    }

}
