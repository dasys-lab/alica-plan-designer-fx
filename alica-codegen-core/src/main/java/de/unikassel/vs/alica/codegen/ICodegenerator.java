package de.unikassel.vs.alica.codegen;

import de.unikassel.vs.alica.planDesigner.alicamodel.AbstractPlan;
import de.unikassel.vs.alica.planDesigner.alicamodel.Behaviour;
import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;

import java.io.File;
import java.util.List;

public interface ICodegenerator {
    void generate();
    void generate(AbstractPlan abstractPlan);
    void generate(Plan plan);
    void generate(Behaviour behaviour);
}
