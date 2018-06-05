package de.uni_kassel.vs.cn.planDesigner.view.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.command.CommandStack;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Pair;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by marci on 07.04.17.
 */
public class BehaviourTab extends AbstractEditorTab<Behaviour> {

    private BehaviourWindowController controller;

    public BehaviourTab(Pair<Behaviour, Path> behaviourPathPair, CommandStack commandStack) {
        super(behaviourPathPair, commandStack);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("behaviourWindow.fxml"));
        try {
            Parent window = fxmlLoader.load();
            controller = fxmlLoader.getController();
            controller.setCommandStack(commandStack);
            controller.setBehaviour(behaviourPathPair.getKey());
            controller.setBehaviourTab(this);
            setContent(window);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
