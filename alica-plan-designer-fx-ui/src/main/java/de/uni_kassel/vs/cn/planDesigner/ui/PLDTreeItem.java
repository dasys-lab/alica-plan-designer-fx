package de.uni_kassel.vs.cn.planDesigner.ui;

import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

/**
 * Created by marci on 11.11.16.
 */
public class PLDTreeItem extends TreeItem<FileWrapper> {
    public PLDTreeItem(FileWrapper value, Node graphic) {
        super(value, graphic);
        if (value.unwrap().isDirectory()) {
            setExpanded(true);
            for (File content : value.unwrap().listFiles()) {
                Image listItemImage = null;
                if (content.getName().endsWith(".beh")) {
                    listItemImage = new Image((getClass().getClassLoader().getResourceAsStream("images/behaviour24x24.png")));
                } else if (content.getName().endsWith(".pml")) {
                    listItemImage = new Image((getClass().getClassLoader().getResourceAsStream("images/plan24x24.png")));
                } else if (content.getName().endsWith(".pty")) {
                    listItemImage = new Image((getClass().getClassLoader().getResourceAsStream("images/planTyp24x24.png")));
                } else if (content.getName().endsWith("pmlex") || content.getName().startsWith(".")) {
                    continue;
                } else {
                    listItemImage = new Image((getClass().getClassLoader().getResourceAsStream("images/folder24x24.png")));
                }
                getChildren().add(new PLDTreeItem(new FileWrapper(content), new ImageView(listItemImage)));
            }
        }
    }

}
