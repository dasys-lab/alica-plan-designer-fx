package de.uni_kassel.vs.cn.planDesigner.ui.repo;

import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.util.Pair;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by marci on 25.11.16.
 */
public class RepositoryTab<T extends PlanElement> extends Tab {
    public RepositoryTab(List<Pair<T, Path>> objects) {
        List<RepositoryHBox<T>> hBoxes = objects
                .stream()
                .map(pair -> new RepositoryHBox<>(pair.getKey(), pair.getValue()))
                .collect(Collectors.toList());
        String name = objects.get(0).getKey().eClass().getName();
        setText(name);
        ObservableList<RepositoryHBox<T>> hBoxObservableList = FXCollections.observableArrayList(hBoxes);
        hBoxObservableList.sort(Comparator.comparing(o -> o.getObject().getName()));
        setContent(new ListView<>(hBoxObservableList));
    }

    public RepositoryTab(Pair<List<T>, Path> pair) {
        List<RepositoryHBox<T>> hBoxes = pair.getKey()
                .stream()
                .map(t -> new RepositoryHBox<>(t, pair.getValue()))
                .collect(Collectors.toList());
        String name = pair.getKey().get(0).eClass().getName();
        setText(name);
        ObservableList<RepositoryHBox<T>> hBoxObservableList = FXCollections.observableArrayList(hBoxes);
        hBoxObservableList.sort(Comparator.comparing(o -> o.getObject().getName()));
        setContent(new ListView<>(hBoxObservableList));
    }
}
