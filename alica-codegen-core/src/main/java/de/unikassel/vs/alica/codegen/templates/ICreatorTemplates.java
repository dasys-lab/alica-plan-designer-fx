package de.unikassel.vs.alica.codegen.templates;

import de.unikassel.vs.alica.planDesigner.alicamodel.Behaviour;
import de.unikassel.vs.alica.planDesigner.alicamodel.Condition;
import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;

import java.util.List;

public interface ICreatorTemplates {
    String behaviourCreator(List<Behaviour> behaviours);
    String conditionCreator(List<Plan> plans, List<Behaviour> behaviours, List<Condition> conditions);
    String constraintCreator(List<Plan> plans, List<Behaviour> behaviours, List<Condition> conditions);
    String utilityFunctionCreator(List<Plan> plans);
}
