package de.uni_kassel.vs.cn.planDesigner.view.editor.tab;


import de.uni_kassel.vs.cn.planDesigner.events.GuiModificationEvent;
import de.uni_kassel.vs.cn.planDesigner.view.filebrowser.TreeViewModelElement;
import de.uni_kassel.vs.cn.planDesigner.view.repo.ViewModelElement;
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

    @Override
    public GuiModificationEvent handleDelete() {
        System.err.println("BehaviourTab: Not implemented!");
        return null;
    }
}
