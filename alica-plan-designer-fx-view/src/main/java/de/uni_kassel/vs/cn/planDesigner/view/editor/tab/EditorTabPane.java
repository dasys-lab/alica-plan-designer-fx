package de.uni_kassel.vs.cn.planDesigner.view.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.filebrowser.TreeViewModelElement;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.util.Pair;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class EditorTabPane extends TabPane {

    public void openTab(TreeViewModelElement treeViewModelElement) {
        Tab tab = getTabs()
                .stream()
                .filter(e -> e != null && ((AbstractEditorTab) e).getEditable().equals(treeViewModelElement.getId()))
                .findFirst()
                .orElse(createNewTab(treeViewModelElement));

        if (this.getTabs().contains(tab) == false) {
            getTabs().add(tab);
            getSelectionModel().select(tab);
        } else {
            Optional<Tab> result = getTabs().stream().filter(e -> e != null && e.equals(tab)).findFirst();
            if (result.isPresent()) {
                getSelectionModel().select(result.get());
            }
        }
        this.requestFocus();
    }

    private Tab createNewTab(TreeViewModelElement treeViewModelElement) {
        String type = treeViewModelElement.getType();
        I18NRepo i18NRepo = I18NRepo.getInstance();
        if (type == i18NRepo.getString("alicatype.masterplan") || type == i18NRepo.getString("alicatype.plan")) {
            return new PlanTab(treeViewModelElement);
        } else if (type == i18NRepo.getString("alicatype.taskrepository")) {
            return new TaskRepositoryTab(treeViewModelElement);
        } else {
            return null;
        }


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

    }
}
