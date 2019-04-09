package de.unikassel.vs.alica.planDesigner.view.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Arrays;

public class EntryPointViewModel extends PlanElementViewModel {

    protected final SimpleBooleanProperty successRequired = new SimpleBooleanProperty(this, "successRequired", false);
    protected final SimpleIntegerProperty minCardinality = new SimpleIntegerProperty(this, "minCardinality", 0);
    protected final SimpleIntegerProperty maxCardinality = new SimpleIntegerProperty(this, "maxCardinality", 0);
    protected final SimpleObjectProperty<TaskViewModel> task = new SimpleObjectProperty<>(this, "task", null);
    protected final SimpleObjectProperty<StateViewModel> state = new SimpleObjectProperty<>(this, "state", null);
    protected final SimpleObjectProperty<PlanViewModel> plan = new SimpleObjectProperty<>(this, "plan", null);

    public EntryPointViewModel(long id, String name, String type) {

        super(id, name, type);

        this.uiPropertyList.clear();
        this.uiPropertyList.addAll(Arrays.asList("name", "id", "comment", "successRequired", "minCardinality", "maxCardinality"));
    }

    public StateViewModel getState() {
        return state.getValue();
    }

    public void setState(StateViewModel state) {
        this.state.setValue(state);
    }

    public TaskViewModel getTask() { return task.get(); }

    public SimpleObjectProperty<TaskViewModel> taskProperty() { return task; }

    public void setTask(TaskViewModel task) { this.task.set(task); }

    public boolean isSuccessRequired() {
        return this.successRequired.get();
    }
}
