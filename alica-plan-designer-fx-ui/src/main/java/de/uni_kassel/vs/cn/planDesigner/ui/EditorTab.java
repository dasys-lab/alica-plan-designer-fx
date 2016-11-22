package de.uni_kassel.vs.cn.planDesigner.ui;

import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import javafx.scene.control.Tab;
import org.eclipse.emf.ecore.EObject;

import java.io.File;

/**
 * Created by marci on 18.11.16.
 */
public class EditorTab<T extends EObject> extends Tab {

    private T editable;
    private File file;

    public EditorTab(T editable, File file) {
        super(file.getName());
        this.editable = editable;
        this.file = file;
        if (editable instanceof Plan) {
            // TODO provide content
        }
    }

    public File getFile() {
        return file;
    }
}
