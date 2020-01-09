package de.unikassel.vs.alica.planDesigner.view.model;

import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import javafx.beans.property.*;

import java.util.Arrays;

public class EntryPointViewModel extends PlanElementViewModel {

    protected final SimpleBooleanProperty successRequired = new SimpleBooleanProperty(this, "successRequired", false);
    protected final SimpleIntegerProperty minCardinality = new SimpleIntegerProperty(this, "minCardinality", 0);
    //maxCardinality change to StringProperty, you can set * for Integer.Max
    protected final SimpleStringProperty maxCardinality = new SimpleStringProperty(this, "maxCardinality", "");
    protected final SimpleObjectProperty<TaskViewModel> task = new SimpleObjectProperty<>(this, "task", null);
    protected final SimpleObjectProperty<StateViewModel> state = new SimpleObjectProperty<>(this, "state", null);
    protected final SimpleObjectProperty<PlanViewModel> plan = new SimpleObjectProperty<>(this, "plan", null);

    public EntryPointViewModel(long id, String name, String type) {

        super(id, name, type);

        this.uiPropertyList.clear();
        this.uiPropertyList.addAll(Arrays.asList("name", "id", "comment", "successRequired", "minCardinality", "maxCardinality"));
    }

    public void registerListener(IGuiModificationHandler handler) {
        super.registerListener(handler);
        successRequired.addListener((observable, oldValue, newValue) -> {
            fireGUIAttributeChangeEvent(handler, newValue, successRequired.getClass().getSimpleName(), successRequired.getName());
        });
        minCardinality.addListener((observable, oldValue, newValue) -> {
            fireGUIAttributeChangeEvent(handler, newValue, minCardinality.getClass().getSimpleName(), minCardinality.getName());
        });
        maxCardinality.addListener((observable, oldValue, newValue) -> {
            //Only numbers and * for Integer.Max as Input for MaxCardinality
            int maxCardinalityInt;
            if(newValue.length() > 0 ){
                if(newValue.matches("[*]")){
                    maxCardinalityInt = Integer.MAX_VALUE;
                    fireGUIAttributeChangeEvent(handler, maxCardinalityInt, maxCardinality.getClass().getSimpleName(), maxCardinality.getName());
                }
                if(newValue.matches("^[0-9]*$")) {
                    if(newValue.length() >= 10){
                        maxCardinalityInt = Integer.MAX_VALUE;
                    } else {
                        maxCardinalityInt = Integer.parseInt(newValue);
                    }
                    fireGUIAttributeChangeEvent(handler, maxCardinalityInt, maxCardinality.getClass().getSimpleName(), maxCardinality.getName());
                }
            }
        });
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

    public final SimpleBooleanProperty successRequiredProperty() {return successRequired; }
    public void setSuccessRequired(boolean successRequired) {
        this.successRequired.setValue(successRequired);
    }
    public boolean isSuccessRequired() {
        return successRequired.get();
    }

    public final SimpleIntegerProperty minCardinalityProperty() {return minCardinality; }
    public void setMinCardinality(int minCardinality) {this.minCardinality.setValue(minCardinality);}
    public int getMinCardinality() {return minCardinality.get();}

    public final SimpleStringProperty maxCardinalityProperty() {return maxCardinality; }
    public void setMaxCardinality(String maxCardinality) {this.maxCardinality.setValue(maxCardinality);}
    public String getMaxCardinality() {return maxCardinality.get();}
}
