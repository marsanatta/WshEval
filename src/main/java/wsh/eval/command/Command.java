package wsh.eval.command;

/**
 * Command to bridge between the Interpreter client and the Interpreter for decoupling and reuse
 */
public interface Command {

    /**
     * Execute the command
     */
    void execute();
}
