package de.uni_kassel.vs.cn.planDesigner.ui;

import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

        ObservableList<PropertyHBox<PlanElement>> propertyHBoxList = getStandardPropertyHBoxes(selectedPlanElement);

        if (selectedPlanElement instanceof Plan) {
            ListView<PropertyHBox<Plan>> propertyListView = new ListView<>();
            ObservableList<PropertyHBox<Plan>> newList = getStandardPropertyHBoxes((Plan) selectedPlanElement);
            newList.add(new PropertyHBox<>((Plan) selectedPlanElement, "masterPlan"));
            newList.add(new PropertyHBox<>((Plan) selectedPlanElement, "priority"));
            propertyListView.setItems(newList);
            setContent(propertyListView);
        } else {
            setContent(new ListView<>(propertyHBoxList));
        }
    }

    private <T extends PlanElement> ObservableList<PropertyHBox<T>> getStandardPropertyHBoxes(T selectedPlanElement) {
        ObservableList<PropertyHBox<T>> propertyHBoxList = FXCollections.observableList(new ArrayList<>());
        propertyHBoxList.add(new PropertyHBox<>(selectedPlanElement, "id"));
        propertyHBoxList.add(new PropertyHBox<>(selectedPlanElement, "name"));
        propertyHBoxList.add(new PropertyHBox<>(selectedPlanElement, "comment"));
        return propertyHBoxList;
    }
}
