package de.uni_kassel.vs.cn.planDesigner.ui.editor.tab;

import de.uni_kassel.vs.cn.generator.RepoViewBackend;
import de.uni_kassel.vs.cn.planDesigner.alica.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanType;
import de.uni_kassel.vs.cn.planDesigner.alica.TaskRepository;
import de.uni_kassel.vs.cn.planDesigner.command.CommandStack;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.util.Pair;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

/**
 * Created by marci on 18.11.16.
 */
public class EditorTabPane extends TabPane {

    private CommandStack commandStack;

    //FIXME closing tabs leads to NPE
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
        switch (filePathAsString.substring(filePathAsString.lastIndexOf('.') + 1)) {
            case "pml":
                Pair<Plan, Path> planPathPair = RepoViewBackend
                        .getInstance()
                        .getPlans()
                        .stream()
                        .filter(e -> e.getValue().equals(filePath))
                        .findFirst().get();
                return new PlanTab(planPathPair, commandStack);
            case "tsk":
                List<Pair<TaskRepository, Path>> taskRepositoryPathPair = RepoViewBackend.getInstance().getTaskRepository();
                return new TaskRepositoryTab(taskRepositoryPathPair.get(0), commandStack);
            case "pty":
                Pair<PlanType, Path> plantypePathPair = RepoViewBackend
                        .getInstance()
                        .getPlanTypes()
                        .stream()
                        .filter(e -> e.getValue().equals(filePath))
                        .findFirst().get();
                return new PlanTypeTab(plantypePathPair, commandStack);
            case "beh":
                Pair<Behaviour, Path> behaviourPathPair = RepoViewBackend
                        .getInstance()
                        .getBehaviours()
                        .stream()
                        .filter(e -> e.getValue().equals(filePath))
                        .findFirst().get();
                return new BehaviourTab(behaviourPathPair, commandStack);
            default:
                return null;

        }
    }

    public void setCommandStack(CommandStack commandStack) {
        this.commandStack = commandStack;
    }
}
