package g62227.dev3.oxono.util;


/**
 * command interface from the design pattern command
 */
public interface Command {

    /**
     * Executes the command.
     * This method is used to perform the action associated with the command. It may also be used
     * in redo operations to re-execute previously undone actions.
     */
    void execute();

    /**
     * 'Unexecutes' the command.
     * This method reverses the effect of the command, effectively undoing its action. It can be used
     * for undo operations and also supports redo functionality by allowing the command to be executed again.
     */
    void unexecute();
}
