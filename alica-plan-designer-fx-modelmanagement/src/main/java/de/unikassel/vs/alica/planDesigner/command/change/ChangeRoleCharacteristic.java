package de.unikassel.vs.alica.planDesigner.command.change;

import de.unikassel.vs.alica.planDesigner.alicamodel.Characteristic;
import de.unikassel.vs.alica.planDesigner.alicamodel.Role;
import de.unikassel.vs.alica.planDesigner.alicamodel.Task;
import de.unikassel.vs.alica.planDesigner.command.ChangeAttributeCommand;
import de.unikassel.vs.alica.planDesigner.command.Command;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import javafx.util.Pair;

import java.util.Map;

public class ChangeRoleCharacteristic extends ChangeAttributeCommand {

    private final String elementType;
    private final Role role;
    private final String attribute;
    private final Map<String, Long> relatedObjects;
    private final Characteristic characteristic;
    private final Object newValue;
    private final Object oldValue;

    public ChangeRoleCharacteristic(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager, mmq);
        this.elementType = mmq.getElementType();
        this.characteristic = (Characteristic) modelManager.getPlanElement(mmq.getElementId());
        this.role = (Role) modelManager.getPlanElement(mmq.getParentId());
        this.attribute = mmq.getAttributeName();
        this.newValue = mmq.getNewValue();
        this.oldValue =characteristic.getName();
        this.relatedObjects = mmq.getRelatedObjects();
    }

    @Override
    public void doCommand() {
        this.modelManager.changeAttribute(this.role, elementType, attribute, newValue, oldValue);
        this.fireEvent(this.role, this.elementType, this.attribute);
    }

    @Override
    public void undoCommand() {
        this.modelManager.changeAttribute(this.role, elementType, attribute, newValue, oldValue);
        this.fireEvent(this.role, this.elementType, this.attribute);
    }
}
