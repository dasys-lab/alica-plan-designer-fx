package de.unikassel.vs.alica.planDesigner.alicamodel;

import java.util.List;

public interface HasVariables {
    void addVariable(Variable variable);

    void removeVariable(Variable variable);

    List<Variable> getVariables();
}
