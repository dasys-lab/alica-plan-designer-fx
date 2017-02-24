package de.uni_kassel.vs.cn.planDesigner.ui.properties;

import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.common.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.EditorTab;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputControl;
import org.eclipse.emf.ecore.impl.EAttributeImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Created by marci on 27.11.16.
 */
public class PropertyTab extends AbstractPropertyTab {

    public PropertyTab(EditorTab<PlanElement> activeEditorTab) {
        super(activeEditorTab);
        setText(I18NRepo.getString("label.properties"));
    }

    @Override
    protected void createTabContent() {
        ObservableList<Node> propertyHBoxList = FXCollections.observableList(new ArrayList<>());//getStandardPropertyHBoxes(selectedPlanElement);

        selectedPlanElement
                .eClass()
                .getEAllStructuralFeatures()
                .forEach(eStructuralFeature -> {
                    if (!eStructuralFeature.getName().equalsIgnoreCase("parametrisation") && !eStructuralFeature.getName().equalsIgnoreCase("inplan")) {
                        if(eStructuralFeature.getClass().equals(EAttributeImpl.class)) {
                            if (eStructuralFeature.getName().equalsIgnoreCase("comment")) {
                                propertyHBoxList.add(new PropertyHBox<PlanElement>(selectedPlanElement, eStructuralFeature.getName(),
                                        ((EAttributeImpl) eStructuralFeature).getEAttributeType().getInstanceClass()){
                                    @Override
                                    protected TextInputControl createTextField(PlanElement object, String propertyName) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
                                        return new PropertyTextArea(object, propertyName);
                                    }
                                });
                            } else {
                                propertyHBoxList.add(new PropertyHBox<>(selectedPlanElement, eStructuralFeature.getName(),
                                        ((EAttributeImpl) eStructuralFeature).getEAttributeType().getInstanceClass()));
                            }
                        }
                    }
                });


        setContent(new ListView<>(propertyHBoxList));
    }

}
