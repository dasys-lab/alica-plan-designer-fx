package de.uni_kassel.vs.cn.planDesigner.ui.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.alica.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.controller.BehaviourWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by marci on 07.04.17.
 */
public class BehaviourTab extends AbstractEditorTab<Behaviour> {
    public BehaviourTab(Behaviour editable, Path filePath, CommandStack commandStack) {
        super(editable, filePath, commandStack);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("behaviourWindow.fxml"));
        try {
            Parent window = fxmlLoader.load();
            BehaviourWindowController controller = fxmlLoader.getController();
            controller.setCommandStack(commandStack);
            controller.setBehaviour(editable);
            setContent(window);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
