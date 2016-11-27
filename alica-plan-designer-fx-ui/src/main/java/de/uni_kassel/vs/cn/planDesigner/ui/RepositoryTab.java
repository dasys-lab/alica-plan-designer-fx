package de.uni_kassel.vs.cn.planDesigner.ui;

import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.util.Pair;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by marci on 25.11.16.
 */
public class RepositoryTab<T extends PlanElement> extends Tab {
    public RepositoryTab(List<Pair<T, Path>> objects) {
        List<RepositoryHBox<T>> hBoxes = objects
                .stream()
                .map(pair -> new RepositoryHBox<T>(pair.getKey(), pair.getValue()))
                .collect(Collectors.toList());
        setContent(new ListView<HBox>(FXCollections.observableArrayList(hBoxes)));
    }

    public RepositoryTab(Pair<List<T>, Path> pair) {
        List<RepositoryHBox<T>> hBoxes = pair.getKey()
                .stream()
                .map(t -> new RepositoryHBox<T>(t, pair.getValue()))
                .collect(Collectors.toList());
        setContent(new ScrollPane(new ListView<HBox>(FXCollections.observableArrayList(hBoxes))));
    }
}
