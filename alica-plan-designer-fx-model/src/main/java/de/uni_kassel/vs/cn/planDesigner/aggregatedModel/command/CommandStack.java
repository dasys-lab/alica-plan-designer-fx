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
        undoStack.push(command);
        redoStack.clear();
        notifyObservers();
    }

    /**
     *
     */
    public void undo() {
        AbstractCommand undone = undoStack.pop();
        undone.undoCommand();
        redoStack.push(undone);
        notifyObservers();
    }

    /**
     * executes first command on redo stack and puts it from there to the undo stack.
     */
    public void redo() {
        AbstractCommand redone = redoStack.pop();
        redone.doCommand();
        undoStack.push(redone);
        notifyObservers();
    }

    /**
     *
     * @return saved
     */
    boolean isCurrentCommandSaved() {
        return undoStack.peek().isSaved();
    }

    public Stack<AbstractCommand> getUndoStack() {
        return undoStack;
    }

    public Stack<AbstractCommand> getRedoStack() {
        return redoStack;
    }
}
