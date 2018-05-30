package de.uni_kassel.vs.cn.planDesigner.view.repo;

import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;

import java.util.Comparator;
import java.util.List;

/**
 * This class represents one tab of the Repository. It should be possible to drag elements of this
 * repository into the plan.
 */
public class RepositoryTab extends Tab {

    private String typeName;
    private ListView<RepositoryHBox> contentsListView;
    // TODO: should work for every kind (not only plans)
    private Comparator<RepositoryHBox> planComparator;

    public RepositoryTab (String typeName) {
        this.typeName = typeName;
        setText(typeName);
        planComparator = Comparator.comparing(o -> !o.getModelElementId().isMasterPlan());
        planComparator = planComparator.thenComparing(o -> o.getModelElementId().getName());
    }

    private void initComparator() {
        planComparator = Comparator.comparing(o -> !o.getObject().isMasterPlan());
        planComparator = planComparator.thenComparing(o -> o.getObject().getName());
    }

    public ListView<RepositoryHBox> getContentsListView() {
        return contentsListView;
    }

    public String getTypeName() {
        return typeName;
    }

    private void init(String typeName, List<RepositoryHBox> hBoxes) {
        initComparator();
        this.typeName = typeName;
        setText(typeName);
        hBoxObservableList = FXCollections.observableArrayList(hBoxes);
        sort();
        contentsListView = new ListView<>(hBoxObservableList);
        setContent(contentsListView);
    }

    public void sort() {
        if (typeName.equals("Plan")) {
            //TODO find a better way
            hBoxObservableList.sort((Comparator<? super RepositoryHBox>) (Object) planComparator);
        } else {
            hBoxObservableList.sort(Comparator.comparing(o -> o.getObject().getName()));
        }
    }
}
