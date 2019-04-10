package de.unikassel.vs.alica.planDesigner.view.model;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

public class RoleViewModel extends PlanElementViewModel {

    private RoleSetViewModel roleSetViewModel;
    private ObservableMap<TaskViewModel, Float> taskPriorities;

    public RoleViewModel (long id, String name, String type) {
        super(id, name, type);
        this.uiPropertyList.clear();
        this.uiPropertyList.addAll(Arrays.asList("name", "id", "comment"));
    }

    public RoleSetViewModel getRoleSetViewModel() {
        return roleSetViewModel;
    }

    public void setRoleSetViewModel(RoleSetViewModel roleSetViewModel) {
        this.roleSetViewModel = roleSetViewModel;
    }

    public void setTaskPriorities(ObservableMap<TaskViewModel, Float> taskPriorities) {
        this.taskPriorities = taskPriorities;
    }
    public void setTaskPriority(HashMap<Long, Float> taskPriorities) {
        this.taskPriorities.clear();
        ObservableList<TaskViewModel> taskViewModels = this.getRoleSetViewModel().getTaskRepository().getTaskViewModels();

        taskPriorities.forEach((t, p) -> {
            taskViewModels.forEach(tvm -> {
                if (tvm.getId() == t)
                    this.taskPriorities.put(tvm, p);
            });
        });
    }

    public Float getTaskPriority(long taskID) {
        Optional<TaskViewModel> task = taskPriorities.keySet().stream().filter(t -> t.getId() == taskID).findFirst();
        return task.isPresent() ?  taskPriorities.get(task.get()) : null;

    }

//    public void registerListener(IGuiModificationHandler handler) {
//        super.registerListener(handler);
//        this.taskPriorities.addListener((MapChangeListener<TaskViewModel, Float>) change -> {
////            fireGUIAttributeChangeEvent(handler, change, "TaskPriority", change.getKey().getName());
//            fireGUIAttributeChangeEvent(handler, change, taskPriorities.getClass().getSimpleName(), "taskPriorities");
//        });
//    }

    public void setTaskPriority(TaskViewModel task, float priority) {

        if(priority != roleSetViewModel.getDefaultPriority()) {
            taskPriorities.put(task, priority);
        }
    }

    public ObservableMap<TaskViewModel, Float> getTaskPriorities() {
        return taskPriorities;
    }
}
