package de.unikassel.vs.alica.planDesigner.command.change;

import de.unikassel.vs.alica.planDesigner.alicamodel.PlanElement;
import de.unikassel.vs.alica.planDesigner.alicamodel.Role;
import de.unikassel.vs.alica.planDesigner.alicamodel.Task;
import de.unikassel.vs.alica.planDesigner.command.Command;
import de.unikassel.vs.alica.planDesigner.events.ModelEvent;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class ChangeTaskPriority extends Command {

    private Map<String, Long> relatedObjects;
    private String attribute;
    private Role role;
    private String elementType;
    private Pair newValue;
    private Pair oldValue;

    public ChangeTaskPriority(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager, mmq);
        this.elementType = mmq.getElementType();
        this.role = (Role) modelManager.getPlanElement(mmq.getElementId());
        this.attribute = mmq.getAttributeName();
        this.relatedObjects = mmq.getRelatedObjects();
        String value = this.relatedObjects.keySet().iterator().next();
        Task task = (Task) this.modelManager.getPlanElement(this.relatedObjects.get(value));
        this.newValue = new Pair<>(task, value);
        String oldPriority = ""+ this.role.getPriority(task);
        this.oldValue = new Pair<>(task, oldPriority);
    }

    @Override
    public void doCommand() {
//        this.modelManager.changeAttribute(planElement, elementType, attribute, newValue, oldValue);
        this.role.addTaskPriority((Task) this.newValue.getKey(), Float.valueOf((String)this.newValue.getValue()));
        this.fireEvent(ModelEventType.ELEMENT_ATTRIBUTE_CHANGED, this.role);
    }

    @Override
    public void undoCommand() {
//        this.modelManager.changeAttribute(planElement, elementType, attribute, oldValue, newValue);
    }

    @Override
    protected void fireEvent(ModelEventType eventType, PlanElement planElement) {
        ModelEvent event = new ModelEvent(eventType, planElement, mmq.getElementType());
        event.setParentId(mmq.getParentId());
        event.setChangedAttribute(mmq.getAttributeName());
        event.setRelatedObjects(mmq.getRelatedObjects());
        HashMap<Long, Float> taskIdPriorities = new HashMap<>();
        this.role.getTaskPriorities().forEach((t, p)-> taskIdPriorities.put(t.getId(), p));
        event.setNewValue(taskIdPriorities);
        this.modelManager.fireEvent(event);
    }
}
