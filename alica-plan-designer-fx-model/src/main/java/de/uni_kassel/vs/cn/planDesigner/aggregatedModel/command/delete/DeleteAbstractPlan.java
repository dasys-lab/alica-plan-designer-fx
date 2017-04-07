package de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.delete;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.alica.*;
import de.uni_kassel.vs.cn.planDesigner.alica.util.AllAlicaFiles;
import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;
import javafx.util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by marci on 05.04.17.
 */
public class DeleteAbstractPlan extends AbstractCommand<AbstractPlan> {

    public DeleteAbstractPlan(AbstractPlan element) {
        super(element);
    }

    @Override
    public void doCommand() {
        EMFModelUtils
                .getAlicaResourceSet().getResources().forEach(e -> {
            e.getContents().forEach(f -> {
                if (f instanceof State) {
                    ((State) f).getPlans().remove(getElementToEdit());
                }
            });
        });
        EMFModelUtils.getAlicaResourceSet().getResources().remove(getElementToEdit().eResource());
        if (getElementToEdit() instanceof Plan) {
            Pair<Plan, Path> planPathPair = AllAlicaFiles.getInstance().getPlans()
                    .stream()
                    .filter(e -> e.getKey().equals(getElementToEdit()))
                    .findFirst()
                    .get();
            try {
                Files.delete(planPathPair.getValue());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (getElementToEdit() instanceof PlanType) {
            Pair<PlanType, Path> planPathPair = AllAlicaFiles.getInstance().getPlanTypes()
                    .stream()
                    .filter(e -> e.getKey().equals(getElementToEdit()))
                    .findFirst()
                    .get();
            try {
                Files.delete(planPathPair.getValue());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (getElementToEdit() instanceof Behaviour) {
            Pair<Behaviour, Path> planPathPair = AllAlicaFiles.getInstance().getBehaviours()
                    .stream()
                    .filter(e -> e.getKey().equals(getElementToEdit()))
                    .findFirst()
                    .get();
            try {
                Files.delete(planPathPair.getValue());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void undoCommand() {

    }

    @Override
    public String getCommandString() {
        return "Delete " + getElementToEdit().getName();
    }
}
