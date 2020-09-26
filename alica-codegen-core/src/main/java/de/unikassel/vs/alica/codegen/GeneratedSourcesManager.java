package de.unikassel.vs.alica.codegen;

import de.unikassel.vs.alica.planDesigner.alicamodel.AbstractPlan;
import de.unikassel.vs.alica.planDesigner.alicamodel.Behaviour;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GeneratedSourcesManager implements IGeneratedSourcesManager {
    protected String sourcePath;
    private String editorExecutablePath;
    private Map<Long, Integer> linesForGeneratedElements;

    public GeneratedSourcesManager() {
        linesForGeneratedElements = new HashMap<>();
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setEditorExecutablePath(String editorExecutablePath) {
        this.editorExecutablePath = editorExecutablePath;
    }

    public String trimFileFromPath(String destinationPath) {
        if (destinationPath.lastIndexOf('.') > destinationPath.lastIndexOf(File.separator)) {
            return destinationPath.substring(0, destinationPath.lastIndexOf(File.separator) + 1);
        } else {
            return destinationPath;
        }
    }

    /**
     * delegate {@link Map#put(Object, Object)}
     *
     * @param modelElementId
     * @param lineNumber
     */
    public void putLineForModelElement(long modelElementId, Integer lineNumber) {
        linesForGeneratedElements.put(modelElementId, lineNumber);
    }

    /**
     * if code has not been generated this method returns 0
     *
     * @param modelElementId
     * @return line number of generated code
     */
    public int getLineNumberForGeneratedElement(long modelElementId) {
        return linesForGeneratedElements.getOrDefault(modelElementId, 0);
    }

    public abstract List<File> getGeneratedFilesForBehaviour(Behaviour behaviour);

    public abstract List<File> getGeneratedConditionFilesForPlan(AbstractPlan abstractPlan);

    public abstract List<File> getGeneratedConstraintFilesForPlan(AbstractPlan abstractPlan);

    public abstract List<File> getGeneratedConstraintFilesForBehaviour(Behaviour behaviour);
}
