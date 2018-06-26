package de.uni_kassel.vs.cn.planDesigner.view.repo;

import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
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

    // SINGLETON
    private static volatile RepositoryViewModel instance;

    public static RepositoryViewModel getInstance() {
        if (instance == null) {
            synchronized (RepositoryViewModel.class) {
                if (instance == null) {
                    instance = new RepositoryViewModel();
                }
            }
        }
        return instance;
    }

    private ObservableList<ViewModelElement> plans;
    private ObservableList<ViewModelElement> planTypes;
    private ObservableList<ViewModelElement> behaviours;
    private ObservableList<ViewModelElement> tasks;

    private RepositoryTabPane repositoryTabPane;

    private I18NRepo i18NRepo;

    public RepositoryViewModel() {
        plans = FXCollections.observableArrayList(new ArrayList<>());
        planTypes = FXCollections.observableArrayList(new ArrayList<>());
        behaviours = FXCollections.observableArrayList(new ArrayList<>());
        tasks = FXCollections.observableArrayList(new ArrayList<>());
        i18NRepo = I18NRepo.getInstance();
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

    public ObservableList<ViewModelElement> getPlanTypes() {
        return planTypes;
    }

    public ObservableList<ViewModelElement> getBehaviours() {
        return behaviours;
    }

    public ObservableList<ViewModelElement> getTasks() {
        return tasks;
    }

    public void removePlanElement(ViewModelElement elementToDelete) {
        if (elementToDelete.getType().equals(i18NRepo.getString("alicatype.plan")) ||
                elementToDelete.getType().equals(i18NRepo.getString("alicatype.masterplan")))
        {
            for (ViewModelElement element : plans) {
                if(element.getId() == elementToDelete.getId()) {
                    plans.remove(element);
                    break;
                }
            }
        }
        else if (elementToDelete.getType().equals(i18NRepo.getString("alicatype.behaviour")))
        {
            for (ViewModelElement element : behaviours) {
                if(element.getId() == elementToDelete.getId()) {
                    behaviours.remove(element);
                    break;
                }
            }
        }
        else if (elementToDelete.getType().equals(i18NRepo.getString("alicatype.plantype")))
        {
            for (ViewModelElement element : planTypes) {
                if(element.getId() == elementToDelete.getId()) {
                    planTypes.remove(element);
                    break;
                }
            }
        }
        else if (elementToDelete.getType().equals(i18NRepo.getString("alicatype.task")))
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
