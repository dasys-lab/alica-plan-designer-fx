package de.unikassel.vs.alica.planDesigner.view.editor.tab;

import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.container.AbstractPlanContainer;
import de.unikassel.vs.alica.planDesigner.view.editor.container.Container;
import de.unikassel.vs.alica.planDesigner.view.model.PlanViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.SerializableViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.StateViewModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public abstract class AbstractPlanTab extends EditorTab {

    protected final SimpleObjectProperty<Container> selectedContainer = new SimpleObjectProperty<>();

    public AbstractPlanTab(SerializableViewModel serializableViewModel, IGuiModificationHandler handler) {
        super(serializableViewModel, handler);
    }

    public void selectPlan(PlanViewModel plan) {
        Container container = this.selectedContainer.get();
        if (container != null) {
            container.setEffectToStandard();
        }
        selectedContainer.set(null);
        this.elementInformationPane.setViewModelElement(plan);
    }

    public void setSelectedContainer(Container containerToSelect, boolean debug) {
        // reset effect of former selected container
        Container container = this.selectedContainer.get();
        if (container != null) {
            container.setEffectToStandard();
        }
        // remember new selected container
        selectedContainer.set(containerToSelect);
        if (containerToSelect != null) {
            // set selected effect on new selected container
            containerToSelect.setCustomEffect(createSelectedEffect(debug));
        }

        // update properties gui
        this.elementInformationPane.setViewModelElement(containerToSelect.getPlanElementViewModel());
    }

    private DropShadow createSelectedEffect(boolean debug) {
        DropShadow value;
        if (debug) {
            value = new DropShadow(30, new Color(0.9, 0.2, 0.45, 0.95));
            value.setBlurType(BlurType.THREE_PASS_BOX);
            value.setSpread(0.6);
        } else {
            value = new DropShadow(20, new Color(0, 0.4, 0.9, 0.9));
            value.setBlurType(BlurType.ONE_PASS_BOX);
            value.setSpread(0.45);
        }
        return value;
    }
}
