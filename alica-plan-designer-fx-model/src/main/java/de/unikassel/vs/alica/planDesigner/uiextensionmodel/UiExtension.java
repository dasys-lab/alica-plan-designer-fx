package de.unikassel.vs.alica.planDesigner.uiextensionmodel;

import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.alicamodel.PlanElement;

import java.util.HashMap;
import java.util.Set;

public class UiExtension {

    protected Plan plan;
    protected HashMap<PlanElement, UiElement> uiElementMap = null;

    /**
     * Default-constructor, only necessary for deserialization
     */
    private UiExtension() {
        this(null);
    }

    public UiExtension(Plan plan) {
        this.plan = plan;
        this.uiElementMap = new HashMap<>();
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public void replaceKey(PlanElement element) {
        for (PlanElement key : uiElementMap.keySet()) {
            if (key.getId() == element.getId()) {
                UiElement extension = uiElementMap.get(element);
                uiElementMap.remove(key);
                uiElementMap.put(element, extension);
                return;
            }
        }
    }

    public Set<PlanElement> getKeys() {
        return uiElementMap.keySet();
    }

    public void remove(PlanElement key) {
        this.uiElementMap.remove(key);
    }

    public void add(PlanElement key, UiElement value) {
        this.uiElementMap.put(key, value);
    }

    /**
     * Method for simplifying the access to the {@link UiElement}-objects.
     * <p>
     * Whenever a {@link PlanElement}, which has no UiElement, is
     * requested, a  UiElement is created and put into the map. This Method
     * may not be required later, when PmlUiExtensions are saved, loaded and created
     * automatically
     *
     * @param planElement the {@link PlanElement} to find a {@link UiElement} for
     * @return the corresponding {@link UiElement} or a new one
     */
    public UiElement getUiElement(PlanElement planElement) {
        UiElement uiElement = uiElementMap.get(planElement);
        if (uiElement == null) {
            uiElement = new UiElement();
            uiElementMap.put(planElement, uiElement);
            registerDirtyListeners(uiElement);
        }
        return uiElement;
    }

    private void registerDirtyListeners(UiElement extension) {
        extension.xProperty().addListener((observable, oldValue, newValue) ->
                plan.setDirty(true)
        );
        extension.yProperty().addListener((observable, oldValue, newValue) ->
                plan.setDirty(true)
        );
    }

}
