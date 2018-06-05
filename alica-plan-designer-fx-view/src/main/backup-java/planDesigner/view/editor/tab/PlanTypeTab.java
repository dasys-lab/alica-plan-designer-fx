package de.uni_kassel.vs.cn.planDesigner.view.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanType;
import de.uni_kassel.vs.cn.planDesigner.command.CommandStack;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Pair;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by marci on 17.03.17.
 */
public class PlanTypeTab extends AbstractEditorTab<PlanType> {

    private PlanTypeWindowController controller;

    public PlanTypeTab(Pair<PlanType, Path> pair, CommandStack commandStack) {
        super(pair, commandStack);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("plantypeWindow.fxml"));
        try {
            Parent window = fxmlLoader.load();
            controller = fxmlLoader.getController();
            controller.setCommandStack(commandStack);
            controller.setPlanType(pair.getKey());
            controller.setPlanTypeTab(this);
            setContent(window);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refresh() {
        controller.refresh();
    }
}
