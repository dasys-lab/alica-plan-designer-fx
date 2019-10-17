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
    protected final SimpleObjectProperty<Container> currentDebugContainer = new SimpleObjectProperty<>();

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

    public void setSelectedContainer(Container containerToSelect) {
        // reset effect of former selected container
        Container container = this.selectedContainer.get();
        if (container != null && !container.equals(currentDebugContainer.get())) {
            container.setEffectToStandard();
        }
        // remember new selected container
        selectedContainer.set(containerToSelect);
        if (containerToSelect != null && !containerToSelect.equals(currentDebugContainer.get())) {
            // set selected effect on new selected container
            containerToSelect.setCustomEffect(createSelectedEffect());
        }

        // update properties gui
        this.elementInformationPane.setViewModelElement(containerToSelect.getPlanElementViewModel());
    }

    public void setCurrentDebugContainer(Container container, String senderId) {
        if (container == currentDebugContainer.get()) return;

        // reset effect from former active debug container
        Container c = currentDebugContainer.get();
        if (c != null) {
            c.setEffectToStandard();
        }

        // remember new active debug container
        currentDebugContainer.set(container);
        if (container != null) {
            // set effect
            container.setCustomEffect(createActiveDebugEffect());

//            Label senderIdLabel = new Label(senderId);
//            senderIdLabel.setTranslateX(-50);
//            senderIdLabel.setTranslateY(-50);
//            senderIdLabel.setUnderline(true);
//            container.getChildren().add(senderIdLabel);
        }

        // update properties gui
        this.elementInformationPane.setViewModelElement(container.getPlanElementViewModel());
    }

    private DropShadow createSelectedEffect() {
        DropShadow value = new DropShadow(20, new Color(0, 0.4, 0.9, 0.9));
        value.setBlurType(BlurType.ONE_PASS_BOX);
        value.setSpread(0.45);
        return value;
    }

    private DropShadow createActiveDebugEffect() {
        DropShadow value = new DropShadow(30, new Color(0.9, 0.2, 0.45, 0.95));
        value.setBlurType(BlurType.THREE_PASS_BOX);
        value.setSpread(0.6);
        return value;
    }
}
