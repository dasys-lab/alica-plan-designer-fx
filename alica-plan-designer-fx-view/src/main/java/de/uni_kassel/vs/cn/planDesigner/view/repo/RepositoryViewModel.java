package de.uni_kassel.vs.cn.planDesigner.view.repo;

import de.uni_kassel.vs.cn.planDesigner.view.Types;
import de.uni_kassel.vs.cn.planDesigner.view.model.PlanViewModel;
import de.uni_kassel.vs.cn.planDesigner.view.model.TaskRepositoryViewModel;
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

    public void removePlan(long id) {
        for(ViewModelElement plan : plans) {
            if(plan.getId() == id) {
                this.plans.remove(plan);
                break;
            }
        }
    }

    public void removeBehaviour(long id) {
        for(ViewModelElement behaviour : behaviours) {
            if(behaviour.getId() == id) {
                this.behaviours.remove(behaviour);
                break;
            }
        }
    }

    public void removePlanType(long id) {
        for(ViewModelElement planType : planTypes) {
            if(planType.getId() == id) {
                this.planTypes.remove(planType);
                break;
            }
        }
    }

    public void removeTask(long id) {
        for(ViewModelElement task : tasks) {
            if(task.getId() == id) {
                this.tasks.remove(task);
                break;
            }
        }
    }

    public void setRepositoryTabPane(RepositoryTabPane repositoryTabPane) {
        this.repositoryTabPane = repositoryTabPane;
    }

    public ObservableList<ViewModelElement> getPlans() {
        return plans;
    }

    public void addElement(ViewModelElement viewModelElement) {
        switch (viewModelElement.getType()) {
            case Types.MASTERPLAN:
            case Types.PLAN:
                this.plans.add(viewModelElement);
                break;
            case Types.BEHAVIOUR:
                this.behaviours.add(viewModelElement);
                break;
            case Types.PLANTYPE:
                this.planTypes.add(viewModelElement);
                break;
            case Types.TASK:
                this.tasks.add(viewModelElement);
                break;
            case Types.TASKREPOSITORY:
                this.tasks.clear();
                for (ViewModelElement task : ((TaskRepositoryViewModel) viewModelElement).getTaskViewModels()) {
                    this.tasks.add(task);
                }
                break;
        }
    }

    public void removeElement(ViewModelElement viewModelElement) {
        switch (viewModelElement.getType()) {
            case Types.MASTERPLAN:
            case Types.PLAN:
                this.plans.remove(viewModelElement);
                break;
            case Types.BEHAVIOUR:
                this.behaviours.remove(viewModelElement);
                break;
            case Types.PLANTYPE:
                this.planTypes.remove(viewModelElement);
                break;
            case Types.TASK:
                this.tasks.remove(viewModelElement);
                break;
            case Types.TASKREPOSITORY:
                this.tasks.clear();
                break;
        }
    }
}
