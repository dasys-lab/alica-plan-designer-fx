package de.uni_kassel.vs.cn.planDesigner.ui.repo;

import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tools.AbstractPlanTool;
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
    private AbstractPlanTool dragTool;

    public RepositoryTab(List<Pair<T, Path>> objects, AbstractPlanTool dragTool, String typeName) {
        this.dragTool = dragTool;
        List<RepositoryHBox<T>> hBoxes = objects
                .stream()
                .map(pair -> {
                    RepositoryHBox<T> tRepositoryHBox = new RepositoryHBox<>(pair.getKey(), pair.getValue(), dragTool);
                    return tRepositoryHBox;
                })
                .collect(Collectors.toList());
        setText(typeName);
        ObservableList<RepositoryHBox<T>> hBoxObservableList = FXCollections.observableArrayList(hBoxes);
        hBoxObservableList.sort(Comparator.comparing(o -> o.getObject().getName()));
        setContent(new ListView<>(hBoxObservableList));
    }

    public RepositoryTab(Pair<List<T>, Path> pair, AbstractPlanTool dragTool, String typeName) {
        this.dragTool = dragTool;
        List<RepositoryHBox<T>> hBoxes = pair.getKey()
                .stream()
                .map(t -> {
                    RepositoryHBox<T> tRepositoryHBox = new RepositoryHBox<>(t, pair.getValue(), dragTool);
                    return tRepositoryHBox;
                })
                .collect(Collectors.toList());
        setText(typeName);
        ObservableList<RepositoryHBox<T>> hBoxObservableList = FXCollections.observableArrayList(hBoxes);
        hBoxObservableList.sort(Comparator.comparing(o -> o.getObject().getName()));
        setContent(new ListView<>(hBoxObservableList));
    }
}
