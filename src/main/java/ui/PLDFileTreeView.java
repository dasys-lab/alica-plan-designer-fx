package ui;

import configuration.Configuration;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

/**
 * Created by marci on 16.10.16.
 */
public final class PLDFileTreeView extends TreeView<File> {
    private static final Configuration configuration = new Configuration();

    public PLDFileTreeView(TreeItem<File> fileTreeItem) throws MalformedURLException, URISyntaxException {
        super(fileTreeItem);
    }

}
