package de.uni_kassel.vs.cn.planDesigner.view.editor.tab;


import de.uni_kassel.vs.cn.planDesigner.view.filebrowser.TreeViewModelElement;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class BehaviourTab extends AbstractPlanTab {

//    private BehaviourWindowController controller;

    public BehaviourTab(TreeViewModelElement behaviour) {
        super(behaviour.getId());

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("behaviourWindow.fxml"));
        try {
            Parent window = fxmlLoader.load();
//            controller = fxmlLoader.getController();
//            controller.setCommandStack(commandStack);
//            controller.setBehaviour(behaviourPathPair.getKey());
//            controller.setBehaviourTab(this);
            setContent(window);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
