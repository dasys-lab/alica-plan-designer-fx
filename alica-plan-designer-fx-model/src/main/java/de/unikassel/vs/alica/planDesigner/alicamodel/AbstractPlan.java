package de.unikassel.vs.alica.planDesigner.alicamodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AbstractPlan extends SerializablePlanElement {

    protected final ArrayList<Variable> variables= new ArrayList<>();

    public AbstractPlan () {
        super();
    }

    public AbstractPlan (long id) {
        this.id = id;
    }

    public void registerDirtyFlag() {
        super.registerDirtyFlag();
    }

    public void addVariable(Variable variable) {
        variables.add(variable);
        variable.nameProperty().addListener((observable, oldValue, newValue) -> {
            this.setDirty(true);
        });
        variable.commentProperty().addListener((observable, oldValue, newValue) -> {
            this.setDirty(true);
        });
        variable.variableTypeProperty().addListener((observable, oldValue, newValue) -> {
            this.setDirty(true);
        });
        this.setDirty(true);
    }
    public void removeVariable(Variable variable) {
        variables.remove(variable);
        this.setDirty(true);
    }
    public List<Variable> getVariables() {
        return Collections.unmodifiableList(variables);
    }
}
