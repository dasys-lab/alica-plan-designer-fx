package de.uni_kassel.vs.cn.planDesigner.ui.repo;

import de.uni_kassel.vs.cn.planDesigner.PlanDesigner;
import de.uni_kassel.vs.cn.planDesigner.alica.util.AllAlicaFiles;
import javafx.scene.control.TabPane;

/**
 * Created by marci on 25.11.16.
 */
public class RepositoryTabPane extends TabPane {
    public RepositoryTabPane() {
        getTabs().clear();
    }

    public void init() {
        getTabs().clear();
        getTabs().addAll(new RepositoryTab<>(AllAlicaFiles.getInstance().getPlans()),
                new RepositoryTab<>(AllAlicaFiles.getInstance().getPlanTypes()),
                new RepositoryTab<>(AllAlicaFiles.getInstance().getBehaviours()),
                new RepositoryTab<>(AllAlicaFiles.getInstance().getTasks()));

    }
}
