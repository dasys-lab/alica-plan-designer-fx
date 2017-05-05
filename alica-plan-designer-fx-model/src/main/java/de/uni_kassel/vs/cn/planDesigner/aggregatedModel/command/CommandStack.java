package de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command;

import java.util.Observable;
import java.util.Stack;

/**
 * Created by marci on 03.01.17.
 */
public class CommandStack extends Observable {
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
