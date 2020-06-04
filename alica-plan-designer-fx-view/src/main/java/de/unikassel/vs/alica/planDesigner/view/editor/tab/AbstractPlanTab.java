package de.unikassel.vs.alica.planDesigner.view.editor.tab;

import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.container.AbstractPlanContainer;
import de.unikassel.vs.alica.planDesigner.view.editor.container.Container;
import de.unikassel.vs.alica.planDesigner.view.model.PlanViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.SerializableViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.StateViewModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractPlanTab extends EditorTab {

    protected final SimpleObjectProperty<Container> selectedContainer = new SimpleObjectProperty<>();
    protected Map<Integer, Container> debugContainers = new HashMap<>();

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
        if (container != null) {
            container.setEffectToStandard();
        }
        // remember new selected container
        selectedContainer.set(containerToSelect);
        if (containerToSelect != null) {
            // set selected effect on new selected container
            containerToSelect.setCustomEffect(createSelectedEffect());
        }

        // update properties gui
        this.elementInformationPane.setViewModelElement(containerToSelect.getPlanElementViewModel());
    }

    public void addCurrentDebugContainer(Container container, int senderId) {
        if (container == debugContainers.get(senderId)) return;

        // reset effect from former active debug container
        Container c = debugContainers.get(senderId);
        if (c != null) {
            c.setEffectToStandard();
            Node node = c.getChildren().stream()
                    .filter(n -> n.getId() != null && n.getId().equals("labelSenderId"))
                    .findFirst().orElse(null);
            c.getChildren().remove(node);
        }

        // remember new active debug container
        debugContainers.put(senderId, container);
        if (container != null) {
            // set effect
            container.setCustomEffect(createActiveDebugEffect());

            // Label senderId
            Node node = container.getChildren().stream()
                    .filter(n -> n.getId() != null && n.getId().equals("labelSenderId"))
                    .findFirst()
                    .orElse(null);

            if (node == null) {
                // Container does not have a Label for the senderId yet, create it

                Label senderIdLabel = new Label("[" + senderId + "]");
                senderIdLabel.setTranslateX(-10);
                senderIdLabel.setTranslateY(-60);
                senderIdLabel.setUnderline(true);
                senderIdLabel.setId("labelSenderId");
                container.getChildren().add(senderIdLabel);
            } else {
                // Container does already have a Label, add senderId to its text
                Label label = (Label) node;
                String oldText = label.getText();
                if (!oldText.contains(""+senderId)) {
                    String newText = oldText.substring(0, oldText.length() - 1) + ", " + senderId + "]";
                    label.setText(newText);
                }
            }
        }

        // update properties gui
        //this.elementInformationPane.setViewModelElement(container.getPlanElementViewModel());
    }

    public void clearDebugContainers() {
        for (Container c : debugContainers.values()) {
            c.setEffectToStandard();
        }
        debugContainers.clear();
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
