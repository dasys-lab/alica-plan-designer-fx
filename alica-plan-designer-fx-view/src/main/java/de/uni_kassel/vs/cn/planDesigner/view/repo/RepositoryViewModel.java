package de.uni_kassel.vs.cn.planDesigner.view.repo;

import de.uni_kassel.vs.cn.planDesigner.view.Types;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * This class functions as backend for the repository view.
 * <p>
 * This class contains Lists of all Plans, PlanTypes, Behaviours and Tasks as ViewModelElement
 */
public final class RepositoryViewModel {

    private ObservableList<ViewModelElement> plans;
    private ObservableList<ViewModelElement> planTypes;
    private ObservableList<ViewModelElement> behaviours;
    private ObservableList<ViewModelElement> tasks;

    private RepositoryTabPane repositoryTabPane;

    public RepositoryViewModel() {
        plans = FXCollections.observableArrayList(new ArrayList<>());
        planTypes = FXCollections.observableArrayList(new ArrayList<>());
        behaviours = FXCollections.observableArrayList(new ArrayList<>());
        tasks = FXCollections.observableArrayList(new ArrayList<>());
    }

    public void initGuiContent() {
        if (repositoryTabPane == null) {
            return;
        }

        repositoryTabPane.clearGuiContent();
        repositoryTabPane.addPlans(plans);
        repositoryTabPane.addPlanTypes(planTypes);
        repositoryTabPane.addTasks(tasks);
        repositoryTabPane.addBehaviours(behaviours);
        initListeners();
    }

    public void initListeners() {
        plans.addListener(new ListChangeListener<ViewModelElement>() {
            @Override
            public void onChanged(Change<? extends ViewModelElement> c) {
                repositoryTabPane.clearPlansTab();
                repositoryTabPane.addPlans(plans);
            }
        });
        planTypes.addListener(new ListChangeListener<ViewModelElement>() {
            @Override
            public void onChanged(Change<? extends ViewModelElement> c) {
                repositoryTabPane.clearPlanTypesTab();
                repositoryTabPane.addPlanTypes(planTypes);
            }
        });
        behaviours.addListener(new ListChangeListener<ViewModelElement>() {
            @Override
            public void onChanged(Change<? extends ViewModelElement> c) {
                repositoryTabPane.clearBehavioursTab();
                repositoryTabPane.addBehaviours(behaviours);
            }
        });
        tasks.addListener(new ListChangeListener<ViewModelElement>() {
            @Override
            public void onChanged(Change<? extends ViewModelElement> c) {
                repositoryTabPane.clearTasksTab();
                repositoryTabPane.addTasks(tasks);
            }
        });
    }

    public void addPlan(ViewModelElement plan) {
        this.plans.add(plan);
    }

    public void removePlan(ViewModelElement plan) {
        this.plans.remove(plan);
    }

    public void addBehaviour(ViewModelElement behaviour) {
        this.behaviours.add(behaviour);
    }

    public void removeBehaviour(ViewModelElement behaviour) {
        this.behaviours.remove(behaviour);
    }

    public void addPlanType(ViewModelElement planType) {
        this.planTypes.add(planType);
    }

    public void removePlanType(ViewModelElement planType) {
        this.planTypes.remove(planType);
    }

    public void addTask(ViewModelElement task) {
        this.tasks.add(task);
    }

    public void removeTask(ViewModelElement task) {
        this.tasks.remove(task);
    }

    public void setRepositoryTabPane(RepositoryTabPane repositoryTabPane) {
        this.repositoryTabPane = repositoryTabPane;
    }

    public ObservableList<ViewModelElement> getPlans() {
        return plans;
    }
}
