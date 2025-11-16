package g62227.dev3.oxono.model;

import g62227.dev3.oxono.util.Command;

import java.util.Stack;

/**
 * The  class manages the execution, undoing, and redoing
 * of commands in a command pattern implementation.
 *
 */
public class CommandManager {

    private Stack<Command> undoStack = new Stack<>();
    private Stack<Command> redoStack = new Stack<>();

    /**
     * Executes the given command and adds it to the undo stack.
     * Clears the redo stack to ensure that the redo history is reset
     * after a new command execution.
     *
     * @param command the command to be executed
     */
     void executeCommand(Command command) {
        undoStack.add(command);
        command.execute();
        redoStack.clear();
    }
    /**
     * Undoes the last executed command. The command is removed from
     * the undo stack and its unexecute method is called, then
     * the command is added to the redo stack for potential re-execution.
     */
     void undo() {
        if (!undoStack.empty()) {
            Command command = undoStack.pop();
            command.unexecute();
            redoStack.add(command);
        }


     }


    /**
     * Redoes the last undone command. The command is removed from
     * the redo stack and its execute method is called, then the
     * command is added back to the undo stack for potential undoing again.
     */
     void redo() {
        if (!redoStack.empty()) {
            Command command = redoStack.pop();
            command.execute();
            undoStack.add(command);
        }


    }

    /**
     * method that will verify if the player canUndo
     * @return true if undostack is not empty, false else
     */
     boolean canUndo() {
        return !undoStack.isEmpty();
    }

    /**
     * method that will verify if the user canredo
     * @return true if redostack is not empty, false else
     */
     boolean canRedo() {
        return !redoStack.isEmpty();
    }
}
