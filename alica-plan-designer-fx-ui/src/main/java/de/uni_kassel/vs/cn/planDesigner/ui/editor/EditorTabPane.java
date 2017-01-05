package de.uni_kassel.vs.cn.planDesigner.ui.editor;

import de.uni_kassel.vs.cn.planDesigner.PlanDesigner;
import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by marci on 18.11.16.
 */
public class EditorTabPane extends TabPane {

    public void openTab(Path filePath) throws IOException {
        Tab tab = getTabs()
                .stream()
                .filter(e -> ((EditorTab) e).getFilePath().equals(filePath))
                .findFirst()
                .orElse(createNewTab(filePath));

        if(this.getTabs().contains(tab) == false) {
            getTabs().add(tab);
            getSelectionModel().select(tab);
        } else {
            getSelectionModel().select(getTabs().stream().filter(e -> e.equals(tab)).findFirst().orElse(null));
        }

    }

    private Tab createNewTab(Path filePath) {
        Pair<Plan, Path> planPathPair = PlanDesigner
                .allAlicaFiles
                .getPlans()
                .stream()
                .filter(e -> e.getValue().equals(filePath))
                .findFirst()
                .orElse(null);
        return new PlanTab(planPathPair.getKey(), planPathPair.getValue());
    }

}
