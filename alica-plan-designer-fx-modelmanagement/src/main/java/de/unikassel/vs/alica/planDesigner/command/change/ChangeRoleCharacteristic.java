package de.unikassel.vs.alica.planDesigner.command.change;

import de.unikassel.vs.alica.planDesigner.alicamodel.Characteristic;
import de.unikassel.vs.alica.planDesigner.alicamodel.Role;
import de.unikassel.vs.alica.planDesigner.alicamodel.Task;
import de.unikassel.vs.alica.planDesigner.command.Command;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import javafx.util.Pair;

import java.util.Map;

public class ChangeRoleCharacteristic extends Command {

    private final String elementType;
    private final Role role;
    private final String attribute;
    private final Map<String, Long> relatedObjects;
    private final Characteristic characteristic;
    private final Object newValue;
    private final Object oldValue;
//    private final Pair<Task, String> newValue;
//    private final Pair<Task, String> oldValue;

    public ChangeRoleCharacteristic(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager, mmq);
        this.elementType = mmq.getElementType();
        this.role = (Role) modelManager.getPlanElement(mmq.getElementId());
        this.characteristic = this.role.getCharacteristic("move");
        this.attribute = mmq.getAttributeName();
        this.newValue = mmq.getNewValue();
        this.oldValue =characteristic.getName();
        this.relatedObjects = mmq.getRelatedObjects();
//        String value = this.relatedObjects.keySet().iterator().next();
//        Task task = (Task) this.modelManager.getPlanElement(this.relatedObjects.get(value));
//        this.newValue = new Pair<>(task, value);
//        String oldPriority = ""+ this.role.getPriority(task);
//        this.oldValue = new Pair<>(task, oldPriority);
    }

    @Override
    public void doCommand() {
//        this.role.addCharacteristic((Characteristic) this.newValue.getKey(), Float.valueOf((String)this.newValue.getValue()));
        this.fireEvent(ModelEventType.ELEMENT_ATTRIBUTE_CHANGED, this.role);
    }

    @Override
    public void undoCommand() {
//        this.role.addCharacteristic((Characteristic) this.oldValue.getKey(), Float.valueOf((String)this.oldValue.getValue()));
        this.fireEvent(ModelEventType.ELEMENT_ATTRIBUTE_CHANGED, this.role);
    }
}
