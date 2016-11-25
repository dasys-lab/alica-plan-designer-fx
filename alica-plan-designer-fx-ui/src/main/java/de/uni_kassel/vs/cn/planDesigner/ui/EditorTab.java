package de.uni_kassel.vs.cn.planDesigner.ui;

import javafx.scene.control.Tab;
import org.eclipse.emf.ecore.EObject;

import java.io.File;

/**
 * Created by marci on 18.11.16.
 */
public abstract class EditorTab<T extends EObject> extends Tab {

    private T editable;
    private File file;

    public EditorTab(T editable, File file) {
        super(file.getName());
        this.editable = editable;
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public abstract void save();

    public T getEditable() {
        return editable;
    }
}
