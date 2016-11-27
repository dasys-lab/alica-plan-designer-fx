package de.uni_kassel.vs.cn.planDesigner.ui.editor;

import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.File;
import java.io.IOException;

/**
 * Created by marci on 18.11.16.
 */
public class EditorTabPane extends TabPane {

    public void openTab(File file) throws IOException {
        Tab tab = getTabs()
                .stream()
                .filter(e -> ((EditorTab) e).getFile().equals(file))
                .findFirst()
                .orElse(new PlanTab(EMFModelUtils.loadAlicaFileFromDisk(file), file));

        if(this.getTabs().contains(tab) == false) {
            getTabs().add(tab);
        }

    }

}
