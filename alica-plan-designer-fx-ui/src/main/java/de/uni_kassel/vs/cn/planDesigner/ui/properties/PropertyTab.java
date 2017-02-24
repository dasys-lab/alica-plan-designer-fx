package de.uni_kassel.vs.cn.planDesigner.ui.properties;

import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.EditorTab;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextInputControl;
import org.eclipse.emf.ecore.impl.EAttributeImpl;
import org.eclipse.emf.ecore.impl.EReferenceImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Created by marci on 27.11.16.
 */
public class PropertyTab extends Tab {

    private PlanElement selectedPlanElement;
    private EditorTab<PlanElement> activeEditorTab;

    public PropertyTab(EditorTab<PlanElement> activeEditorTab) {
        this.activeEditorTab = activeEditorTab;
        this.selectedPlanElement = activeEditorTab.getSelectedPlanElement().getValue().getKey();
        setText("Properties");
        activeEditorTab.getSelectedPlanElement().addListener((observable, oldValue, newValue) -> {
            selectedPlanElement = newValue.getKey();
            createTabContent();
        });
        createTabContent();
    }

    private void createTabContent() {
        ObservableList<PropertyHBox<PlanElement>> propertyHBoxList = FXCollections.observableList(new ArrayList<>());//getStandardPropertyHBoxes(selectedPlanElement);

        selectedPlanElement
                .eClass()
                .getEAllStructuralFeatures()
                .forEach(eStructuralFeature -> {
                    if (!eStructuralFeature.getName().equalsIgnoreCase("parametrisation") && !eStructuralFeature.getName().equalsIgnoreCase("inplan")) {
                        if(eStructuralFeature.getClass().equals(EAttributeImpl.class)) {
                            if (eStructuralFeature.getName().equalsIgnoreCase("comment")) {
                                propertyHBoxList.add(new PropertyHBox<PlanElement>(selectedPlanElement, eStructuralFeature.getName()){
                                    @Override
                                    protected TextInputControl createTextField(PlanElement object, String propertyName) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
                                        return new PropertyTextArea(object, propertyName);
                                    }
                                });
                            } else {
                                propertyHBoxList.add(new PropertyHBox<>(selectedPlanElement, eStructuralFeature.getName()));
                            }
                        } else if (eStructuralFeature.getClass().equals(EReferenceImpl.class)) {

                        }
                    }
                });

        setContent(new ListView<>(propertyHBoxList));/*
        if (selectedPlanElement instanceof Plan) {
            ListView<PropertyHBox<Plan>> propertyListView = new ListView<>();
            ObservableList<PropertyHBox<Plan>> newList = getStandardPropertyHBoxes((Plan) selectedPlanElement);
            newList.add(new PropertyHBox<>((Plan) selectedPlanElement, "masterPlan"));
            newList.add(new PropertyHBox<>((Plan) selectedPlanElement, "priority"));
            propertyListView.setItems(newList);
            setContent(propertyListView);
        } else {
            setContent(new ListView<>(propertyHBoxList));
        }*/
    }

    private <T extends PlanElement> ObservableList<PropertyHBox<T>> getStandardPropertyHBoxes(T selectedPlanElement) {
        ObservableList<PropertyHBox<T>> propertyHBoxList = FXCollections.observableList(new ArrayList<>());
        propertyHBoxList.add(new PropertyHBox<>(selectedPlanElement, "id"));
        propertyHBoxList.add(new PropertyHBox<>(selectedPlanElement, "name"));
        propertyHBoxList.add(new PropertyHBox<>(selectedPlanElement, "comment"));
        return propertyHBoxList;
    }

}
