package de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command;

import java.util.Stack;

/**
 * Created by marci on 03.01.17.
 */
public class CommandStack {
    private Stack<Command> undoStack = new Stack<>();
    private Stack<Command> redoStack = new Stack<>();

    public void storeAndExecute(Command command) {
        command.doCommand();
        undoStack.push(command);
        redoStack.clear();
    }

    public void undo() {
        Command undone = undoStack.pop();
        undone.undoCommand();
        redoStack.push(undone);
    }

    public void redo() {
        Command redone = redoStack.pop();
        redone.doCommand();
        undoStack.push(redone);
    }
}
