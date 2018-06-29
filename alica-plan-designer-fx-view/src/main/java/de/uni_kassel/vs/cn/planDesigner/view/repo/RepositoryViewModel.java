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
    }

    public void addPlan(ViewModelElement plan) {
        this.plans.add(plan);
        if (repositoryTabPane != null) {
            this.repositoryTabPane.addPlan(plan);
        }
    }

    public void addBehaviour(ViewModelElement behaviour) {
        this.behaviours.add(behaviour);
        if (repositoryTabPane != null) {
            this.repositoryTabPane.addBehaviour(behaviour);
        }
    }

    public void addPlanType(ViewModelElement planType) {
        this.planTypes.add(planType);
        if (repositoryTabPane != null) {
            this.repositoryTabPane.addPlanType(planType);
        }
    }

    public void addTask(ViewModelElement task) {
        this.tasks.add(task);
        if (repositoryTabPane != null) {
            this.repositoryTabPane.addTask(task);
        }
    }

    public void setRepositoryTabPane(RepositoryTabPane repositoryTabPane) {
        this.repositoryTabPane = repositoryTabPane;
    }

    public ObservableList<ViewModelElement> getPlans() {
        return plans;
    }

    public void removePlanElement(ViewModelElement elementToDelete) {
        if (elementToDelete.getType().equals(Types.PLAN) ||
                elementToDelete.getType().equals(Types.MASTERPLAN))
        {
            for (ViewModelElement element : plans) {
                if(element.getId() == elementToDelete.getId()) {
                    plans.remove(element);
                    break;
                }
            }
        }
        else if (elementToDelete.getType().equals(Types.BEHAVIOUR))
        {
            for (ViewModelElement element : behaviours) {
                if(element.getId() == elementToDelete.getId()) {
                    behaviours.remove(element);
                    break;
                }
            }
        }
        else if (elementToDelete.getType().equals(Types.PLANTYPE))
        {
            for (ViewModelElement element : planTypes) {
                if(element.getId() == elementToDelete.getId()) {
                    planTypes.remove(element);
                    break;
                }
            }
        }
        else if (elementToDelete.getType().equals(Types.TASK))
        {
            for (ViewModelElement element : tasks) {
                if(element.getId() == elementToDelete.getId()) {
                    tasks.remove(element);
                    break;
                }
            }
        }
    }
}
