package de.uni_kassel.vs.cn.planDesigner.ui;

import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marci on 27.11.16.
 */
public class PropertyTab extends Tab {

    private PlanElement selectedPlanElement;

    public PropertyTab(PlanElement selectedPlanElement) {
        this.selectedPlanElement = selectedPlanElement;
        setText("Properties");

        List<PropertyHBox<PlanElement>> propertyHBoxList = getStandardPropertyHBoxes(selectedPlanElement);

        if (selectedPlanElement instanceof Plan) {
            ListView<PropertyHBox<Plan>> propertyListView = new ListView<>();
            List<PropertyHBox<Plan>> newList = getStandardPropertyHBoxes((Plan) selectedPlanElement);
            newList.add(new PropertyHBox<>((Plan) selectedPlanElement, "masterPlan"));
            newList.add(new PropertyHBox<>((Plan) selectedPlanElement, "priority"));
            setContent(propertyListView);
        }
    }

    private <T extends PlanElement> List<PropertyHBox<T>> getStandardPropertyHBoxes(T selectedPlanElement) {
        List<PropertyHBox<T>> propertyHBoxList = new ArrayList<>();
        propertyHBoxList.add(new PropertyHBox<>(selectedPlanElement, "id"));
        propertyHBoxList.add(new PropertyHBox<>(selectedPlanElement, "name"));
        propertyHBoxList.add(new PropertyHBox<>(selectedPlanElement, "comment"));
        return propertyHBoxList;
    }
}
