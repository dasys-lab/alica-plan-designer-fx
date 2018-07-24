package de.uni_kassel.vs.cn.planDesigner.view.filebrowser;

import de.uni_kassel.vs.cn.planDesigner.view.model.ViewModelElement;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;

public class FileTreeItem extends TreeItem<File> {

    private ViewModelElement viewModelElement;

    public FileTreeItem(File value, Node graphic, ViewModelElement viewModelElement) {
        super(value, graphic);
        this.viewModelElement = viewModelElement;
        updateDirectories();
    }

    public void updateDirectories() {
        if (!getValue().isDirectory()) {
            return;
        }

        //Add items for new files
        for (File content : getValue().listFiles()) {
            if (!content.isDirectory()) {
                continue;
            }
            // Check if child already exists
            boolean childExists = false;
            ObservableList<TreeItem<File>> children = getChildren();
            for (int i = children.size() - 1; i >= 0; i--) {
                try {
                    if (children.get(i).getValue().getCanonicalPath().equals(content.getCanonicalPath()))
                    {
                        // child already exists, so just trigger update directories
                        ((FileTreeItem) children.get(i)).updateDirectories();
                        childExists = true;
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (!childExists) {
                getChildren().add(new FileTreeItem(content, new ImageView(new Image((getClass().getClassLoader()
                        .getResourceAsStream("images/folder24x24.png")))), null));
            }
        }

        //remove items with no file
//        for(int i = getChildren().size() -1 ; i >= 0; i--) {
//            if(!getChildren().get(i).getValue().exists()) {
//                getChildren().remove(i);
//            }
//        }
        getChildren().sort(Comparator.comparing(o -> o.getValue().toURI().toString()));
    }

    public ViewModelElement getViewModelElement() {
        return viewModelElement;
    }


}
