package de.unikassel.vs.alica.planDesigner.view.model;

public class EntryPointViewModel extends PlanElementViewModel {

    public static final String STATE = "state";

    protected StateViewModel state;
    protected TaskViewModel task;

    public EntryPointViewModel(long id, String name, String type) {
        super(id, name, type);
    }

    public StateViewModel getState() {
        return state;
    }

    public void setState(StateViewModel state) {
        this.state = state;
    }

    public PlanElementViewModel getTask() {
        return task;
    }

    public void setTask(PlanElementViewModel task) {
        this.task = (TaskViewModel) task;
    }
}
