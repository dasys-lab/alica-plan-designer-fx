package de.uni_kassel.vs.cn.planDesigner.ui.menu;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.delete.DeleteAbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.alica.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanType;
import de.uni_kassel.vs.cn.planDesigner.alica.util.AllAlicaFiles;
import de.uni_kassel.vs.cn.planDesigner.common.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.AbstractEditorTab;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.util.Pair;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Created by marci on 20.04.17.
 */
public class DeleteFileMenuItem extends MenuItem {
    private File toDelete;
    private CommandStack commandStack;

    public DeleteFileMenuItem(File toDelete) {
        super(I18NRepo.getString("label.menu.edit.delete"));
        this.toDelete = toDelete;
        setOnAction(e -> deleteFile());
    }

    public void deleteFile() {
        Tab tabToClose = null;
        Optional<Pair<Plan, Path>> first = AllAlicaFiles
                .getInstance()
                .getPlans()
                .stream()
                .filter(f -> f.getValue().toFile().equals(this.toDelete))
                .findFirst();
        if (first.isPresent()) {
            commandStack.storeAndExecute(new DeleteAbstractPlan(first.get().getKey()));
            AllAlicaFiles.getInstance().getPlans().remove(first.get());
            Optional<AbstractEditorTab<PlanElement>> tabOptional = MainController.getInstance()
                    .getEditorTabPane()
                    .getTabs()
                    .stream()
                    .map(e -> (AbstractEditorTab<PlanElement>) e)
                    .filter(e -> e.getEditable().equals(first.get().getKey()))
                    .findFirst();
            if (tabOptional.isPresent()) {
                tabToClose = tabOptional.get();
            }
        } else {
            Optional<Pair<PlanType, Path>> planTypePathPair = AllAlicaFiles
                    .getInstance()
                    .getPlanTypes()
                    .stream()
                    .filter(f -> f.getValue().toFile().equals(this.toDelete))
                    .findFirst();
            if (planTypePathPair.isPresent()) {
                commandStack.storeAndExecute(new DeleteAbstractPlan(planTypePathPair.get().getKey()));
                AllAlicaFiles.getInstance().getPlanTypes().remove(planTypePathPair.get());
                Optional<AbstractEditorTab<PlanElement>> tabOptional = MainController.getInstance()
                        .getEditorTabPane()
                        .getTabs()
                        .stream()
                        .map(e -> (AbstractEditorTab<PlanElement>) e)
                        .filter(e -> e.getEditable().equals(planTypePathPair.get().getKey()))
                        .findFirst();
                if (tabOptional.isPresent()) {
                    tabToClose = tabOptional.get();
                }
            } else {
                Optional<Pair<Behaviour, Path>> behaviourPathPair = AllAlicaFiles.getInstance()
                        .getBehaviours()
                        .stream()
                        .filter(f -> f.getValue().toFile().equals(this.toDelete))
                        .findFirst();
                if (behaviourPathPair.isPresent()) {
                    commandStack.storeAndExecute(new DeleteAbstractPlan(behaviourPathPair.get().getKey()));
                    AllAlicaFiles.getInstance().getBehaviours().remove(behaviourPathPair.get());
                    Optional<AbstractEditorTab<PlanElement>> tabOptional = MainController.getInstance()
                            .getEditorTabPane()
                            .getTabs()
                            .stream()
                            .map(e -> (AbstractEditorTab<PlanElement>) e)
                            .filter(e -> e.getEditable().equals(behaviourPathPair.get().getKey()))
                            .findFirst();
                    if (tabOptional.isPresent()) {
                        tabToClose = tabOptional.get();
                    }

                } else {
                    throw new RuntimeException("EAT SHIT AND DIE");
                }
            }
        }
        if (tabToClose != null) {
            MainController.getInstance().getEditorTabPane().getTabs().remove(tabToClose);
        }
        MainController.getInstance().getRepositoryTabPane().init();
    }

    public void setToDelete(File toDelete) {
        this.toDelete = toDelete;
    }

    public void setCommandStack(CommandStack commandStack) {
        this.commandStack = commandStack;
    }

    public CommandStack getCommandStack() {
        return commandStack;
    }
}
