package de.unikassel.vs.alica.planDesigner.view.model;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

public class RoleViewModel extends PlanElementViewModel {

    private RoleSetViewModel roleSetViewModel;
    private ObservableMap<TaskViewModel, Float> taskPrioritieViewModels = FXCollections.observableHashMap();
    private ObservableList<CharacteristicViewModel> characteristicViewModels = FXCollections.observableArrayList();

    public RoleViewModel (long id, String name, String type) {
        super(id, name, type);
        this.uiPropertyList.clear();
        this.uiPropertyList.addAll(Arrays.asList("name", "id", "comment"));
        this.characteristicViewModels.addListener(new ListChangeListener<CharacteristicViewModel>() {
            @Override
            public void onChanged(Change<? extends CharacteristicViewModel> c) {
                ObservableList<? extends CharacteristicViewModel> list = c.getList();
                CharacteristicViewModel model = list.get(list.size()-1);
                System.out.println("RVM: add characteristic view model: " + model.nameProperty());
                updateView();
            }
        });
    }

    private void updateView() {

    }

    public RoleSetViewModel getRoleSetViewModel() {
        return roleSetViewModel;
    }

    public void setRoleSetViewModel(RoleSetViewModel roleSetViewModel) {
        this.roleSetViewModel = roleSetViewModel;
    }

    public void setTaskPrioritieViewModels(ObservableMap<TaskViewModel, Float> taskPrioritieViewModels) {
        this.taskPrioritieViewModels = taskPrioritieViewModels;
    }

    public void setTaskPriority(HashMap<Long, Float> taskPriorities) {
        this.taskPrioritieViewModels.clear();
        ObservableList<TaskViewModel> taskViewModels = this.getRoleSetViewModel().getTaskRepository().getTaskViewModels();

        taskPriorities.forEach((t, p) -> {
            taskViewModels.forEach(tvm -> {
                if (tvm.getId() == t)
                    this.taskPrioritieViewModels.put(tvm, p);
            });
        });
    }

    public Float getTaskPriority(long taskID) {
        Optional<TaskViewModel> task = taskPrioritieViewModels.keySet().stream().filter(t -> t.getId() == taskID).findFirst();
        return task.isPresent() ?  taskPrioritieViewModels.get(task.get()) : null;

    }

//    public void registerListener(IGuiModificationHandler handler) {
//        super.registerListener(handler);
//        this.taskPrioritieViewModels.addListener((MapChangeListener<TaskViewModel, Float>) change -> {
////            fireGUIAttributeChangeEvent(handler, change, "TaskPriority", change.getKey().getName());
//            fireGUIAttributeChangeEvent(handler, change, taskPrioritieViewModels.getClass().getSimpleName(), "taskPrioritieViewModels");
//        });
//    }

    public ObservableMap<TaskViewModel, Float> getTaskPrioritieViewModels() {
        return taskPrioritieViewModels;
    }
    public void setTaskPriority(TaskViewModel task, float priority) {

        if(priority != roleSetViewModel.getDefaultPriority()) {
            taskPrioritieViewModels.put(task, priority);
        }
    }

    public ObservableList<CharacteristicViewModel> getCharacteristicViewModels() {
        return characteristicViewModels;
    }
    public void addRoleCharacteristic(CharacteristicViewModel characteristic) {
        characteristicViewModels.add(characteristic);
    }
    public String getRoleCharacteristicValue(long id) {

        for (CharacteristicViewModel characteristic : characteristicViewModels) {

            if (characteristic.getId() == id)
                return characteristic.valueProperty().getValue();
        }
        return null;
    }

    public void addChangeCharacteristicsListener(ListChangeListener listener) {
        this.characteristicViewModels.addListener(listener);
    }
}
