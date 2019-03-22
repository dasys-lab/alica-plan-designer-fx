package de.unikassel.vs.alica.planDesigner.view.editor.tab;

import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.editor.container.Container;
import de.unikassel.vs.alica.planDesigner.view.model.SerializableViewModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public abstract class AbstractPlanTab extends EditorTab {

    protected final SimpleObjectProperty<Container> selectedContainer = new SimpleObjectProperty<>();

    public AbstractPlanTab(SerializableViewModel serializableViewModel, IGuiModificationHandler handler) {
        super(serializableViewModel, handler);
    }

    public void setSelectedContainer(Container containerToSelect) {
        // reset effect of former selected container
        Container container = this.selectedContainer.get();
        if (container != null) {
            container.setEffectToStandard();
        }
        // remember new selected container
        selectedContainer.set(containerToSelect);
        if (containerToSelect != null) {
            // set selected effect on new selected container
            containerToSelect.setCustomEffect(createSelectedEffect());
            // update properties gui
            this.propertiesConditionsVariablesPane.setViewModelElement(containerToSelect.getPlanElementViewModel());
        }
    }

    private DropShadow createSelectedEffect() {
        DropShadow value = new DropShadow(20, new Color(0, 0.4, 0.9, 0.9));
        value.setBlurType(BlurType.ONE_PASS_BOX);
        value.setSpread(0.45);
        return value;
    }
}
