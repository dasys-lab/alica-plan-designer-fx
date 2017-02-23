package de.uni_kassel.vs.cn.planDesigner.ui.editor;

import de.uni_kassel.vs.cn.planDesigner.PlanDesigner;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.alica.TaskRepository;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.util.Pair;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

/**
 * Created by marci on 18.11.16.
 */
public class EditorTabPane extends TabPane {

    private CommandStack commandStack;

    public void openTab(Path filePath) throws IOException {
        Tab tab = getTabs()
                .stream()
                .filter(e -> ((EditorTab) e).getFilePath().equals(filePath))
                .findFirst()
                .orElse(createNewTab(filePath));

        if (this.getTabs().contains(tab) == false) {
            getTabs().add(tab);
            getSelectionModel().select(tab);
        } else {
            getSelectionModel().select(getTabs().stream().filter(e -> e.equals(tab)).findFirst().orElse(null));
        }

    }

    private Tab createNewTab(Path filePath) {
        String filePathAsString = filePath.toString();
        switch (filePathAsString.substring(filePathAsString.indexOf('.') + 1)) {
            case "pml":
                Pair<Plan, Path> planPathPair = PlanDesigner
                        .allAlicaFiles
                        .getPlans()
                        .stream()
                        .filter(e -> e.getValue().equals(filePath))
                        .findFirst().get();
                return new PlanTab(planPathPair.getKey(), planPathPair.getValue(), commandStack);
            case "tsk":
                List<Pair<TaskRepository, Path>> taskRepositoryPathPair = PlanDesigner.allAlicaFiles.getTaskRepository();
                return new TaskRepositoryTab(taskRepositoryPathPair.get(0).getKey(),
                        taskRepositoryPathPair.get(0).getValue(), commandStack);
            default:
                return null;

        }
    }

    public void setCommandStack(CommandStack commandStack) {
        this.commandStack = commandStack;
    }
}
