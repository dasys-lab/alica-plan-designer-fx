package de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command;

import java.util.Stack;

/**
 * Created by marci on 03.01.17.
 */
public class CommandStack {
    private Stack<Command> undoStack = new Stack<>();
    private Stack<Command> redoStack = new Stack<>();

    /**
     *
     * @param command
     */
    public void storeAndExecute(Command command) {
        command.doCommand();
        undoStack.push(command);
        redoStack.clear();
    }

    /**
     *
     */
    public void undo() {
        Command undone = undoStack.pop();
        undone.undoCommand();
        redoStack.push(undone);
    }

    /**
     * executes first command on redo stack and puts it from there to the undo stack.
     */
    public void redo() {
        Command redone = redoStack.pop();
        redone.doCommand();
        undoStack.push(redone);
    }

    /**
     *
     * @return saved
     */
    boolean isCurrentCommandSaved() {
        return undoStack.peek().isSaved();
    }
}
