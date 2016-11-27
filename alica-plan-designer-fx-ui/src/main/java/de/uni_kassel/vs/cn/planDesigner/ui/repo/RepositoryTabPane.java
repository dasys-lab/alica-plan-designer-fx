package de.uni_kassel.vs.cn.planDesigner.ui.repo;

import de.uni_kassel.vs.cn.planDesigner.PlanDesigner;
import javafx.scene.control.TabPane;

/**
 * Created by marci on 25.11.16.
 */
public class RepositoryTabPane extends TabPane {

    public void init() {
        getTabs().clear();
        getTabs().addAll(new RepositoryTab<>(PlanDesigner.allAlicaFiles.getPlans()),
                new RepositoryTab<>(PlanDesigner.allAlicaFiles.getPlanTypes()),
                new RepositoryTab<>(PlanDesigner.allAlicaFiles.getBehaviours()),
                new RepositoryTab<>(PlanDesigner.allAlicaFiles.getTasks()));

    }
}
