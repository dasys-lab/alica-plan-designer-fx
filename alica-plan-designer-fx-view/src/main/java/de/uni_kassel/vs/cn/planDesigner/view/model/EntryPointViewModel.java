package de.uni_kassel.vs.cn.planDesigner.view.model;

public class EntryPointViewModel extends PlanElementViewModel {

    protected StateViewModel state;
    protected PlanElementViewModel task;

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
        this.task = task;
    }
}
