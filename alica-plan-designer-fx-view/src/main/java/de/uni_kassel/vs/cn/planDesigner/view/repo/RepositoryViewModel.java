package de.uni_kassel.vs.cn.planDesigner.view.repo;

import de.uni_kassel.vs.cn.planDesigner.view.model.ViewModelElement;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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

    public void removePlan(long id) {
        for(ViewModelElement plan : plans) {
            if(plan.getId() == id) {
                this.plans.remove(plan);
                break;
            }
        }
    }

    public void addBehaviour(ViewModelElement behaviour) {
        this.behaviours.add(behaviour);
    }

    public void removeBehaviour(long id) {
        for(ViewModelElement behaviour : behaviours) {
            if(behaviour.getId() == id) {
                this.behaviours.remove(behaviour);
                break;
            }
        }
    }

    public void addPlanType(ViewModelElement planType) {
        this.planTypes.add(planType);
    }

    public void removePlanType(long id) {
        for(ViewModelElement planType : planTypes) {
            if(planType.getId() == id) {
                this.planTypes.remove(planType);
                break;
            }
        }
    }

    public void addTask(ViewModelElement task) {
        this.tasks.add(task);
    }

    public void removeTask(long id) {
        for(ViewModelElement task : tasks) {
            if(task.getId() == id) {
                this.tasks.remove(task);
                break;
            }
        }
    }

    public void clearTasks() {
        this.tasks.clear();
    }

    public void setRepositoryTabPane(RepositoryTabPane repositoryTabPane) {
        this.repositoryTabPane = repositoryTabPane;
    }

    public ObservableList<ViewModelElement> getPlans() {
        return plans;
    }
}
