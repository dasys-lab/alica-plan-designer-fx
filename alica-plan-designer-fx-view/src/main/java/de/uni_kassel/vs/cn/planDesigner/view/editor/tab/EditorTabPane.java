package de.uni_kassel.vs.cn.planDesigner.view.editor.tab;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.nio.file.Path;
import java.util.Optional;

public class EditorTabPane extends TabPane {

    public void openTab(Path filePath) {
        Tab tab = getTabs()
                .stream()
                .filter(e -> e != null && ((AbstractEditorTab) e).getFilePath().equals(filePath))
                .findFirst()
                .orElse(createNewTab(filePath));

        if (this.getTabs().contains(tab) == false) {
            getTabs().add(tab);
            getSelectionModel().select(tab);
        } else {
            Optional<Tab> result = getTabs().stream().filter(e ->  e != null && e.equals(tab)).findFirst();
            if (result.isPresent()) {
                getSelectionModel().select(result.get());
            }
        }
        this.requestFocus();
    }

    private Tab createNewTab(Path filePath) {
        String filePathAsString = filePath.toString();
//        switch (filePathAsString.substring(filePathAsString.lastIndexOf('.') + 1)) {
//            case "pml":
//                Pair<Plan, Path> planPathPair = RepositoryViewModel
//                        .getInstance()
//                        .getPlans()
//                        .stream()
//                        .filter(e -> e.getValue().equals(filePath))
//                        .findFirst().get();
//                return new PlanTab(planPathPair, commandStack);
//            case "tsk":
//                List<Pair<TaskRepository, Path>> taskRepositoryPathPair = RepositoryViewModel.getInstance().getTaskRepository();
//                return new TaskRepositoryTab(taskRepositoryPathPair.get(0), commandStack);
//            case "pty":
//                Pair<PlanType, Path> plantypePathPair = RepositoryViewModel
//                        .getInstance()
//                        .getPlanTypes()
//                        .stream()
//                        .filter(e -> e.getValue().equals(filePath))
//                        .findFirst().get();
//                return new PlanTypeTab(plantypePathPair, commandStack);
//            case "beh":
//                Pair<Behaviour, Path> behaviourPathPair = RepositoryViewModel
//                        .getInstance()
//                        .getBehaviours()
//                        .stream()
//                        .filter(e -> e.getValue().equals(filePath))
//                        .findFirst().get();
//                return new BehaviourTab(behaviourPathPair, commandStack);
//            default:
//                return null;
//
//        }
        return null;
    }
}
