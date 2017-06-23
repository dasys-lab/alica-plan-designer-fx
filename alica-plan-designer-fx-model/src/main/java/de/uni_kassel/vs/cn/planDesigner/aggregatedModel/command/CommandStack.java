package de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command;

import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Observable;
import java.util.Optional;
import java.util.Stack;

/**
 * Created by marci on 03.01.17.
 */
public class CommandStack extends Observable {
    private static final Logger LOG = LogManager.getLogger(CommandStack.class);
    private Stack<AbstractCommand> undoStack = new Stack<>();
    private Stack<AbstractCommand> redoStack = new Stack<>();

    /**
     *
     * @param command
     */
    public void storeAndExecute(AbstractCommand command) {
        command.doCommand();
        command.setSaved(false);
        undoStack.push(command);
        redoStack.clear();
        notifyObserversCompound();
    }

    /**
     *
     */
    public void undo() {
        AbstractCommand undone = undoStack.pop();
        undone.undoCommand();
        undone.setSaved(false);
        redoStack.push(undone);
        notifyObserversCompound();
    }

    /**
     * executes first command on redo stack and puts it from there to the undo stack.
     */
    public void redo() {
        AbstractCommand redone = redoStack.pop();
        redone.doCommand();
        redone.setSaved(false);
        undoStack.push(redone);
        notifyObserversCompound();
    }

    private void notifyObserversCompound() {
        this.setChanged();
        notifyObservers();
    }


    public boolean isAbstractPlanInItsCurrentFormSaved(PlanElement abstractPlan) {
        Optional<AbstractCommand> abstractCommand = undoStack
                .stream()
                .filter(e -> e.getAffectedPlan().equals(abstractPlan))
                .findFirst();
        if (abstractCommand.isPresent()) {
            return abstractCommand.get().isSaved();
        } else {
            return true;
        }
    }

    public void setSavedForAbstractPlan(PlanElement abstractPlan) {
        Optional<AbstractCommand> abstractCommand = undoStack
                .stream()
                .filter(e -> e.getAffectedPlan().equals(abstractPlan))
                .findFirst();
        if (abstractCommand.isPresent()) {
            abstractCommand.get().setSaved(true);
        } else {
            throw new RuntimeException("WHAT");
        }
    }

    /**
     *
     * @return saved
     */
    public boolean isCurrentCommandSaved() {
        if (undoStack.empty()) {
            return true;
        } else {
            return undoStack.peek().isSaved();
        }
    }

    public Stack<AbstractCommand> getUndoStack() {
        return undoStack;
    }

    public Stack<AbstractCommand> getRedoStack() {
        return redoStack;
    }
}
