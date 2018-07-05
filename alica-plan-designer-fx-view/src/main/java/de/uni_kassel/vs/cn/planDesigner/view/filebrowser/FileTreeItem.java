package de.uni_kassel.vs.cn.planDesigner.view.filebrowser;

import de.uni_kassel.vs.cn.planDesigner.view.model.ViewModelElement;
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

        for (File content : getValue().listFiles()) {
            if (!content.isDirectory()) {
                continue;
            }
            // Check if child already exists
            boolean childExists = false;
            for (TreeItem<File> child : getChildren()) {
                try {
                    if (child.getValue().getCanonicalPath().equals(content.getCanonicalPath()))
                    {
                        // child already exists, so just trigger update directories
                        ((FileTreeItem) child).updateDirectories();
                        childExists = true;
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
        getChildren().sort(Comparator.comparing(o -> o.getValue().toURI().toString()));
    }

    public ViewModelElement getViewModelElement() {
        return viewModelElement;
    }

//    private Image getImageForFileType(File content) {
//        Image listItemImage;
//        if (content.getKey().endsWith(".beh")) {
//            listItemImage = new Image((getClass().getClassLoader().getResourceAsStream("images/behaviour24x24.png")));
//        } else if (content.getKey().endsWith(".pml")) {
////            try {
////                Plan plan = (Plan)EMFModelUtils.loadAlicaFileFromDisk(content);
////                if (plan.isMasterPlan()) {
////                    listItemImage = new Image((getClass().getClassLoader().getResourceAsStream("images/masterplan24x24.png")));
////                } else {
//            listItemImage = new Image((getClass().getClassLoader().getResourceAsStream("images/plan24x24.png")));
////                }
////            } catch (IOException e1) {
////                e1.printStackTrace();
////                return null;
////            }
//        } else if (content.getKey().endsWith(".pty")) {
//            listItemImage = new Image((getClass().getClassLoader().getResourceAsStream("images/plantype24x24.png")));
//        } else if (content.getKey().endsWith(".tsk")) {
//            listItemImage = new Image((getClass().getClassLoader().getResourceAsStream("images/tasks24x24.png")));
//        } else if (content.isDirectory()) {
//            listItemImage = new Image((getClass().getClassLoader().getResourceAsStream("images/folder24x24.png")));
//        } else {
//            return null;
//        }
//        return listItemImage;
//    }
}
