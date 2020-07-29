package de.unikassel.vs.alica.codegen;

import de.unikassel.vs.alica.planDesigner.alicamodel.AbstractPlan;
import de.unikassel.vs.alica.planDesigner.alicamodel.Behaviour;

import java.io.File;
import java.util.List;

public interface IGeneratedSourcesManager {
    List<File> getGeneratedFilesForBehaviour(Behaviour behaviour);
    List<File> getGeneratedConditionFilesForPlan(AbstractPlan abstractPlan);
    List<File> getGeneratedConstraintFilesForPlan(AbstractPlan abstractPlan);
    List<File> getGeneratedConstraintFilesForBehaviour(Behaviour behaviour);
}
