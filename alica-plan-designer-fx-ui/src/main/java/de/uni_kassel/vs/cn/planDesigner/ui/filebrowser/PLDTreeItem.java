package de.uni_kassel.vs.cn.planDesigner.ui.filebrowser;

import de.uni_kassel.vs.cn.planDesigner.common.FileWrapper;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by marci on 11.11.16.
 */
public class PLDTreeItem extends TreeItem<FileWrapper> {
    public PLDTreeItem(FileWrapper value, Node graphic) {
        super(value, graphic);
        if (value.unwrap().isDirectory()) {
            for (File content : value.unwrap().listFiles()) {
                Image listItemImage = getImageForFileType(content);
                if (listItemImage == null) {
                    continue;
                }
                getChildren().add(new PLDTreeItem(new FileWrapper(content), new ImageView(listItemImage)));
            }
            getChildren().sort(Comparator.comparing(o -> o.getValue().unwrap().toURI().toString()));
        }
    }

    public void updateDirectory(WatchEvent.Kind kind, Path child) {
        FileWrapper value = getValue();
        File newFile = child.toFile();
        List<TreeItem<FileWrapper>> collect = getChildren()
                .stream()
                .filter(e -> newFile.getAbsolutePath().contains(e.getValue().unwrap().getAbsolutePath()))
                .collect(Collectors.toList());
        if (kind.equals(StandardWatchEventKinds.ENTRY_CREATE) && newFile.isDirectory() && collect.size() == 1) {
            ((PLDTreeItem)collect.get(0)).updateDirectory(kind, child);
            return;
        }

        if (collect.size() == 1 && collect.get(0).getChildren().stream().noneMatch(e -> newFile.getAbsolutePath().contains(e.getValue().unwrap().getAbsolutePath()))) {
            if (kind.equals(StandardWatchEventKinds.ENTRY_MODIFY) || kind.equals(StandardWatchEventKinds.ENTRY_DELETE)) {
                if (kind.equals(StandardWatchEventKinds.ENTRY_DELETE)) {
                    if (collect.get(0).getValue().unwrap().equals(newFile)) {
                        getChildren().remove(collect.get(0));
                    } else {
                        ((PLDTreeItem)collect.get(0)).updateDirectory(kind, child);
                    }
                }
                if (kind.equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
                    for (File content : value.unwrap().listFiles()) {

                        boolean isAlreadyKnownToTreeItem = getChildren().stream().anyMatch(e -> e.getValue().unwrap().equals(content));
                        if (isAlreadyKnownToTreeItem == false) {
                            Image listItemImage = getImageForFileType(content);
                            if (listItemImage == null) {
                                continue;
                            }
                            getChildren().add(new PLDTreeItem(new FileWrapper(content), new ImageView(listItemImage)));
                            getChildren().sort(Comparator.comparing(o -> o.getValue().unwrap().toURI().toString()));
                        }
                    }
                }
            } else if (kind.equals(StandardWatchEventKinds.ENTRY_CREATE)) {
                boolean isAlreadyKnownToTreeItem = getChildren().stream().anyMatch(e -> e.getValue().unwrap().equals(newFile));
                if (isAlreadyKnownToTreeItem == false && newFile.getParentFile().equals(value.unwrap())) {
                    Image listItemImage = getImageForFileType(newFile);
                    if (listItemImage != null) {
                        getChildren().add(new PLDTreeItem(new FileWrapper(newFile), new ImageView(listItemImage)));
                        getChildren().sort(Comparator.comparing(o -> o.getValue().unwrap().toURI().toString()));
                    }
                } else {
                    ((PLDTreeItem)collect.get(0)).updateDirectory(kind, child);
                }
            }
        } else if (newFile.isDirectory() && newFile.getParentFile().equals(value.unwrap())){
            getChildren().add(new PLDTreeItem(new FileWrapper(newFile), new ImageView(getImageForFileType(newFile))));
            getChildren().sort(Comparator.comparing(o -> o.getValue().unwrap().toURI().toString()));
        } else if(newFile.getParentFile().equals(value.unwrap()) && newFile.toString().endsWith("pmlex") == false
                && getChildren().stream().noneMatch(e -> e.getValue().unwrap().equals(newFile))) {
            getChildren().add(new PLDTreeItem(new FileWrapper(newFile), new ImageView(getImageForFileType(newFile))));
            getChildren().sort(Comparator.comparing(o -> o.getValue().unwrap().toURI().toString()));
        } else {
            collect.forEach(e -> ((PLDTreeItem)e).updateDirectory(kind, child));
        }
        setExpanded(true);
    }

    private Image getImageForFileType(File content) {
        Image listItemImage;
        if (content.getName().endsWith(".beh")) {
            listItemImage = new Image((getClass().getClassLoader().getResourceAsStream("images/behaviour24x24.png")));
        } else if (content.getName().endsWith(".pml")) {
            listItemImage = new Image((getClass().getClassLoader().getResourceAsStream("images/plan24x24.png")));
        } else if (content.getName().endsWith(".pty")) {
            listItemImage = new Image((getClass().getClassLoader().getResourceAsStream("images/planTyp24x24.png")));
        } else if (content.getName().endsWith(".tsk")) {
            listItemImage = new Image((getClass().getClassLoader().getResourceAsStream("images/tasks24x24.png")));
        } else if (content.isDirectory()) {
            listItemImage = new Image((getClass().getClassLoader().getResourceAsStream("images/folder24x24.png")));
        } else  {
            return null;
        }
        return listItemImage;
    }
}
