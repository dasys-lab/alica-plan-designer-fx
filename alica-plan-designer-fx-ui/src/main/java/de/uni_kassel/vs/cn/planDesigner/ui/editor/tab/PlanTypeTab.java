package de.uni_kassel.vs.cn.planDesigner.ui.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanType;
import de.uni_kassel.vs.cn.planDesigner.controller.PlanTypeWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by marci on 17.03.17.
 */
public class PlanTypeTab extends AbstractEditorTab<PlanType> {

    public PlanTypeTab(PlanType editable, Path filePath, CommandStack commandStack) {
        super(editable, filePath, commandStack);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("plantypeWindow.fxml"));
        try {
            Parent window = fxmlLoader.load();
            PlanTypeWindowController controller = fxmlLoader.getController();
            controller.setCommandStack(commandStack);
            controller.setPlanType(editable);
            setContent(window);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
